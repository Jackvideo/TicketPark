<template>
  <el-affix>
    <el-page-header @back="goback">
      <template #content>
        <span class="text-large font-600 mr-3"> 电影 </span>
      </template>
    </el-page-header>
    <el-divider />
  </el-affix>
  <div style="margin-top: 20px; display: flex; justify-content: center;  ">
    <el-card class="box-card" style="width: 80%;height: 600px; ">
      <h1 style="text-align: left;">{{ movieinfo.moviename }}</h1>
      <div class="movie-info">
        <div>
          <el-image :src="movieinfo.imageUrl" class="movie-img" :preview-src-list="[movieinfo.imageUrl]"
            hide-on-click-modal>
            <template #error>
              <div class="image-slot">
                <el-image src="https://img95.699pic.com/xsj/0e/mp/ra.jpg%21/fh/300" class="item-image"></el-image>
                <!-- <el-icon><Hide /></el-icon> -->
              </div>
            </template>
          </el-image>
        </div>
        <div class="movie-content">
          <div class="label">影片名 : {{ movieinfo.moviename }}</div>
          <div class="label">上映时间 : {{ movieinfo.startTime }}</div>
          <div class="label">票价 : {{ movieinfo.ticketPrice }}</div>
          <div class="label">简介 : {{ movieinfo.description == null ? '暂无' : movieinfo.description }}</div>
          <div class="label">喜欢数 : {{ movieinfo.likeCount }}</div>
          <div class="label">评论数 : {{ movieinfo.commentCount }}</div>
          <div class="label-like">
            <div style="margin-top: 20px;">
              喜欢 :
            </div>
            <el-image :src="require('@/assets/like_before.png')" class="likeimg" v-show="!islike" @click="likemovie">
            </el-image>
            <el-image :src="require('@/assets/like_after.png')" class="likeimg" v-show="islike" @click="likemovie">
            </el-image>
          </div>
          <el-button class="label" type="primary" @click="createorder" :disabled="userid == ''">点击购票</el-button>
        </div>
      </div>
    </el-card>
  </div>
  <div class="commentlist">
    <!-- 用户发表评论 -->
    <div>
      <div>评论 : </div>
      <textarea v-model="comment" rows="4" cols="60" style="font-size: large;"> </textarea>
      <el-button type="primary" @click="commentmovie" style="margin-left: 50px;margin-bottom: 30px;">发布评论</el-button>
    </div>
    <!-- 展示该电影所有评论 -->
    <div v-for="item in commentlist" :key="item.commentid" class="comment-item">
      <div style="margin-bottom: 10px;font-size: 15px;font-weight: 200; color: red;">用户 : {{ item.username }}</div>
      <div>
        {{ item.comment }}
      </div>

      <div v-show="item.userid == userid" style="text-align: right;color: red;"
        @click="visible = true, commenttodel = item">删除评论
      </div>
      <el-divider style="margin-top: 10px;" />
    </div>
  </div>

  <!-- 删除评论对话框 -->
  <el-dialog v-model="visible" title="是否确认删除" width="30%" draggable="">
    <span>是否确认删除该评论</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="delcomment">
          确认
        </el-button>
        <el-button @click="visible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
  <!-- 创建订单成功对话框 -->
  <el-dialog v-model="orderdialog" title="提示" width="30%" align-center>
    <span>创建订单成功,请前往<a href="/myorder">我的订单</a>页面进行支付</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="orderdialog = false, $router.push('/myorder')">
          确定
        </el-button>
        <el-button @click="orderdialog = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import api from '@/api';
import moment from 'moment';
import { ElMessage } from 'element-plus';
import store from '@/store';
import router from '@/router';

export default {
  name: 'detail',
  data() {
    return {
      movieinfo: {},//电影信息
      islike: false, //是否喜欢
      comment: '', //用户评论
      commentlist: [], //该电影的评论列表
      usernamelist: [],  //用户名列表
      userid: '',  //用户id
      visible: false,  //删除提示框
      commenttodel: {}, //要删除的评论
      orderdialog: false,  //订单对话框
    }
  },
  mounted() {
    //获取传入的movieid 然后传入参数 获取电影具体信息
    let movieid = this.$route.query.movieid;
    // 获取当前电影信息
    this.findone(movieid);
    //获取电影所有评论
    this.getallcomment(movieid);
    if (eval(localStorage.getItem('loginstate')) == true) {
      //获取用户id
      this.userid = store.state.user.id;
    }

  },
  methods: {
    goback() {
      this.$router.back();
    },
    findone(id) {
      api.findone(id).then(res => {
        this.movieinfo = res.data.data;
        this.movieinfo.startTime = moment(this.movieinfo.startTime).format('YYYY-MM-DD HH:mm');
      })
    },
    //电影点赞功能
    likemovie() {
      this.islike = !this.islike;
      //点赞
      if (this.islike == true) {
        api.likemovie(this.movieinfo.movieid).then(res => {
          ElMessage({ message: res.data.message, type: 'success' })
        })
      } //取消点赞
      else {
        api.unlikemovie(this.movieinfo.movieid).then(res => {
          ElMessage({ message: res.data.message, type: 'success' })
        })
      }
      //更新页面
      // setTimeout(() => {
      //   this.findone(this.movieinfo.movieid);
      // }, 1000);
    },
    //获取该电影的所有评论
    getallcomment(id) {
      api.findallcomment(id).then(res => {
        console.log("获取所有评论", res);
        this.commentlist = res.data;
        //对每一个加入name属性
        for (var i = 0; i < this.commentlist.length; i++) {
          let comment = this.commentlist[i];
          api.getName(this.commentlist[i].userid).then(res => {
            //给每个记录添加一个username属性
            comment['username'] = res.data;
            // console.log(comment);
            this.commentlist[i] = comment;
          })
        }
      })
    },
    //评论电影
    commentmovie() {
      //先判断是否登陆
      if (store.state.isLogin == false) {
        ElMessage({ message: '请先登陆', type: 'error' })
        setTimeout(() => {
          router.push('/login')
        }, 1500)

        return;
      }
      //判断评论是否为空
      if (this.comment == '') {
        ElMessage({ message: '输入不可以为空', type: 'error' })
        return;
      }
      //调用api发表评论
      api.createComment(this.movieinfo.movieid, store.state.user.id, this.comment).then(res => {
        // console.log("发表评论",res);
        ElMessage({ message: res.data.message, type: 'success', duration: 1500 })
        setTimeout(() => {
          location.reload()
        }, 1500)
      })
    },
    //删除评论
    delcomment() {
      let comment = this.commenttodel;
      api.deletecomment(this.userid, comment.commentid, comment.movieid, comment.userid, comment.comment).then(res => {
        // console.log(res);
        ElMessage({ message: res.data.message, type: 'success' })
        // this.getallcomment();
        setTimeout(() => {
          location.reload()
        }, 1500)
      })
      this.visible = false;
    },
    //创建购票订单
    createorder() {
      let username = store.state.user.name;
      api.create(this.userid, this.movieinfo.movieid, this.movieinfo.moviename, username, this.movieinfo.ticketPrice).then(res => {
        // console.log(res);
        if (res.data.code == 200) {
          //创建成功跳转支付提示
          this.orderdialog = true;
          ElMessage({ message: '创建订单成功', type: 'success' })
        } else {
          ElMessage({ message: '创建订单失败', type: 'error' })
        }

      })
    }
  },

}
</script>

<style scoped>
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

.movie-info {
  display: flex;
}

.movie-img {
  height: 450px;
  width: 340px;
}

.movie-content {

  margin-left: 100px;
  font-size: large;
  text-align: left;
}

.label {
  margin-bottom: 15px;
}

.label-like {
  margin-bottom: 20px;
  display: flex;

}

.likeimg {
  height: 50px;
  width: 50px;
  margin-left: 20px;
}


.commentlist {
  width: 80%;
  margin-top: 30px;
  margin-left: 150px;
  text-align: left;
}

.comment-item {
  position: relative;
  margin: 0;
  padding: 10px 0 25px;
  overflow: initial;
  font-size: 13px;
}
</style>