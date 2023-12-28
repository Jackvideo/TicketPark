<template>
  <el-affix>
    <el-page-header @back="goback">
      <template #content>
        <span class="text-large font-600 mr-3"> 登陆 </span>
      </template>
    </el-page-header>
    <el-divider />
  </el-affix>
  <h2>
    <a href="/home">Ticketpark</a>
  </h2>
  <!-- 登陆表单 -->
  <fieldset style="margin-top: 20px;">
    <!-- <div class="login-form"> -->

    <h1>登陆</h1>
    <el-form label-position="right" label-width="45%" class="demo-ruleForm" :model="loginform">
      <el-form-item label="账号">
        <el-input clearable style="width: 200px;" v-model="loginform.username" />
      </el-form-item>

      <el-form-item label="密码">
        <el-input type="password" style="width: 200px;" show-password clearable v-model="loginform.password" />
      </el-form-item>

      <el-button type="primary" @click="submitlogin" class="btn" :disabled="loading">
        登陆
      </el-button>
      <el-button type="primary" @click="$router.push('/register')" class="btn">
        去注册
      </el-button>
    </el-form>
    <!-- </div> -->
  </fieldset>

  <!-- 水印 -->
  <!-- <el-watermark :content="['ticketpark', 'ticketpark Plus']">
    <div style="height: 200px" />
  </el-watermark> -->
</template>

<script>

import store from '@/store'
import { ElMessage } from 'element-plus'
import api from '@/api'
import router from '@/router'

export default {
  name: 'login',
  //数据
  data() {
    return {
      //表单数据
      loginform:
      {
        username: '',
        password: '',

      },
      //判断是否被点击 防止重复点击
      loading: false,
    }
  },
  //对登陆的用户进行拒绝登陆
  beforeRouteEnter(to, from, next) {
    //判断登陆状态
    if (eval(localStorage.getItem("loginstate")) == true) {
      ElMessage({ message: "请先退出当前登录", type: "warning", duration: "1500" })
      router.push("/myhome")
    } else {
      next();
    }
  },
  methods: {
    goback() {
      //回退到首页
      this.$router.push("/home")
    },
    submitlogin() {
      this.loading = true;
      //先判断输入框是否为空
      if (this.loginform.username == '' || this.loginform.password == '') {
        ElMessage({ message: '输入不可以为空', type: 'error', duration: 1500 })
        setTimeout(() => {
          this.loading = false;
        }, 1500)
        return;
      }

      //向后台发送请求 校验用户信息 然后跳转
      api.login(this.loginform.username, this.loginform.password).then(
        response => {
          console.log('请求数据', response.data);
          let code = response.data.code
          //登陆失败
          if (code == -1) {
            ElMessage({ message: response.data.message, type: 'error', duration: 1500 })
            setTimeout(() => {
              this.loading = false;
            }, 1500)
          } else {
            //弹出登陆成功提醒
            ElMessage({ message: '登陆成功', type: 'success', duration: 1500 })
            let token = response.data.data.token;
            // 存入token
            store.commit("settoken", token);
            api.userinfo(token).then(res => {
              // console.log('请求用户信息', res.data.data);
              let user = res.data.data;
              //存入用户信息
              store.commit("login", user);
              let userid = user.id;
              // console.log(userid);
              //把userid存入本地存储  防止一直读取不到
              localStorage.setItem('userid', userid);
            })
            //跳转到首页
            this.$router.push("/home")
          }
        },
        error => {
          ElMessage({ message: error.message, type: 'error', duration: 1500 })
          this.loading = false;
        }
      )


    },


  },



}
</script>

<style scoped>
fieldset {

  /* 表单页面居中，宽度50% ,legend颜色设置，legend圆角*/
  border: 2px solid #DCDFE6;
  border-radius: 8px;
  margin: 0 auto;
  width: 50%;
  text-align: center;
  left: 50%;
}

.btn {
  width: 90px;
}

.login-form {
  width: 500px;
  height: 300px;
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