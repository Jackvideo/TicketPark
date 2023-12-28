<template>
  <div class="common-layout">
    <el-affix>
    <el-page-header @back="$router.push('/')">
      <template #content>
        <span class="text-large font-600 mr-3"> 我的主页 </span>
      </template>
    </el-page-header>
    <el-divider />
  </el-affix>
    <el-container>
      <!-- <el-aside width="100px">Aside</el-aside> -->
      <el-main class="el-main">
        <div class="user-profile-nav">
          <h1> 个人中心</h1>
          <a class="info" href="/myhome">基本信息</a>
          <a class="orders" href="/myorder">我的订单</a>
        </div>

        <div class="profile-container">
          <div class="profile-title"> 基本信息 </div>
          <div class="avatar-container">
            <el-image :src="url" :fit="fit">
            </el-image>
            
          </div>
          <el-form class="userexinfo" label-position="left" label-width="80px">
            <el-form-item label="用户ID:" class="userinfoitem">
              <el-input v-model="user.id" placeholder="请输入ID" style="width: 200px;" clearable disabled />
            </el-form-item>
            <el-form-item label="用户姓名:" class="userinfoitem">
              <el-input v-model="user.name" placeholder="请输入姓名" style="width: 200px;" clearable :disabled="!isedit"/>
            </el-form-item>
            <el-form-item label="用户余额:" class="userinfoitem">
              <div>{{ user.balance==null?0:user.balance }}</div>
            </el-form-item>
            
            <!-- <el-form-item label="用户密码:" class="userinfoitem">
              <el-input v-model="user.password" type="password" show-password style="width: 200px;" clearable :disabled="!isedit"/>
            </el-form-item> -->
            <!-- <el-form-item label="用户手机:" class="userinfoitem">
              <el-input v-model="user.phone" placeholder="请输入手机号" style="width: 200px;" clearable />
            </el-form-item> -->

            <div class="userinfoitem" style="padding-left: 0%; ">
              <el-button @click="isedit=true">编辑信息</el-button>
              <el-button style="background-color: pink;" @click="modifyuser" :disabled="!isedit">保存信息</el-button>
              <el-button @click="dialogVisible = true" type="primary">退出登陆</el-button>
              <!-- <el-button @click="getuserinfo" type="primary">获取用户信息</el-button> -->
            </div>
            <!-- <h1>密码:{{ user.userpwd }}</h1> -->
            <!-- <h1>注册时间:{{  user.create_time}}</h1>
        <h1>最近登陆时间:{{ user.update_time }}</h1> -->
            <!-- 弹出对话框 -->
            <el-dialog v-model="dialogVisible" title="退出登陆" width="30%">
              <span>确认是否退出登陆</span>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogVisible = false">取消</el-button>
                  <el-button type="primary" @click="logout">
                    确认
                  </el-button>
                </span>
              </template>
            </el-dialog>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import store from '@/store';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import router from '@/router';
import api from '@/api';

export default {
  name: "Myhome",
  data() {
    return {
      user: {},
      dialogVisible: false,
      //头像链接
      url: 'https://ss0.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3498215567,1247959937&fm=253&gp=0.jpg',
      isedit:false, //编辑个人信息
    }
  },
  mounted() {
    //通过token请求 获取用户个人信息
    this.getuserinfo();
    // this.user = store.state.user;
    //改变主页选择状态
    store.commit('changestatus', {
      'ishome': false,
      'ismovies': false,
      "ismyhome": true,
    })

  },
  methods: {
    //通过本地存储的token获取用户个人信息
    getuserinfo(){
      let token = store.state.token;
      api.userinfo(token).then(res=>{
        console.log("请求用户信息:",res.data.data);
        this.user = res.data.data
      })
    },

    //退出登陆
    logout() {
      //vuex清除用户个人信息 清除本地存储
      store.commit('logout');
      //调用api清除redis中的token
      api.logout(store.state.token).then(res=>{
        console.log(res.data);
      })
      //弹出加载框
      const loadingInstance = ElLoading.service({ fullscreen: true, lock: true, text: '退出登陆', })
      setTimeout(() => {
        loadingInstance.close()
      }, 500)
      ElMessage({ message: "退出登陆成功", type: "success", duration: 1500 })
      this.$router.push('/home')
    },
    //用户修改个人信息
    modifyuser(){
      api.modifyuser(this.user.id,this.user.name,this.user.password,this.user.balance).then(res=>{
        console.log(res.data);
        if(res.data.code==200){
          ElMessage({message:res.data.message,type:'success'})
          //成功后重新更新用户信息 先强制重新登陆
          ElMessage({message:'请重新登陆',type:'info'})
          this.logout();
        }else{
          ElMessage({message:res.data.message,type:'error'})
        }
      })
      //禁用
      this.isedit = false;
    },
  },
  //对没有登陆的用户进行拒绝访问
  beforeRouteEnter(to, from, next) {
     //判断登陆状态
    if(eval(localStorage.getItem("loginstate"))==false){
      ElMessage({message:"请先登录",type:"warning",duration:"1500"})
      router.push("/login")
    }else{
      next();
    }
  }

}
</script>

<style scoped>
.orders {
  display: block;
  font-weight: 400;
  color: #333;
  height: 40px;
  width: 202px;
  margin-left: -1px;
  line-height: 40px;
  font-size: 18px;
}

.info {
  display: block;
  font-weight: 400;
  color: #333;
  height: 40px;
  width: 202px;
  margin-left: -1px;
  line-height: 40px;
  font-size: 18px;
}

.user-profile-nav {
  position: absolute;
  top: 50px;
  bottom: 0;
  width: 200px;
  background-color: #f4f3f4;
  text-align: center;
  border-right: 1px solid #e1e1e1;
}

.userinfoitem {
  position: relative;
  margin: 20px 0;
  padding-left: 90px;
  height: 30px;
  line-height: 30px;
  font-size: 14px;
}


.userexinfo {
  float: left;
  width: 495px;
  margin: 40px;
}

.profile-title {
  padding: 26px 0;
  color: #ec443f;
  font-size: 18px;
  border-bottom: 1px solid #e1e1e1;
  margin-bottom: 30px
}

.el-main {
  padding: 0px 18px;

}

.profile-container {
  float: left;
  margin-left: 300px;
  padding-left: 40px;
  width: 922px;
  
}

.avatar-container {
  text-align: center;
  float: left;
  color: #333;
  background: #fff;
  width: 270px;
}


/* 分割线样式 */
.el-divider--horizontal {
  margin: 0%;
}

.el-page-header {
  background-color: antiquewhite;
  height: 40px;
  display: flex;

}

.el-page-header__title {
  font-size: larger;
}

.el-page-header__left {

  margin: 10px;
}

.el-page-header__icon {
  font-size: larger;
  width: 1em;
}
</style>