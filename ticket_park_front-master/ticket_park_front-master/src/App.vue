<template>
  <div id="app" class="app">
    <div>
      <router-view :key="$route.fullPath"></router-view>
    </div>
  </div>
</template>

<script>
import api from './api';
import store from './store'


export default {

  name: 'App',
  components: {

  },
  data() {
    return {

    }
  },
  //初次加载时 从本地存储中加载信息存入vuex中
  created() {
    let loginstate = eval(localStorage.getItem('loginstate'));
    // let user = JSON.parse(localStorage.getItem('userinfo'));
    store.state.isLogin = loginstate;
    // store.state.user = user;
    store.state.token = localStorage.getItem("token");
    //根据token获取user
    api.userinfo(store.state.token).then(res => {
      store.state.user = res.data.data;
      // console.log(res.data.data);
    })
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;

}

body {
  margin: 0px;
}
</style>
