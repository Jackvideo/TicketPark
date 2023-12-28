import { createStore } from "vuex";
const store = createStore({
  state: {
    //状态管理器中定义的数据源
    isLogin: false,
    user: {
      userId: "",
      username: "",
      userpwd: "",
    },
    //用户token
    token: "",
    //判断页面是否被选中
    pagestatus: {
      ishome: false,
      ismovies: false,
      ismyhome: false,
    },
    //搜素类型和搜索内容
    search:{
      searchtype:'',
      searchcontent:'',
    }
    
  },
  mutations: {
    //同步操作
    //登陆
    login(state, user) {
      state.user = user;
      state.isLogin = true;
      //将信息存入本地存储
      localStorage.setItem("loginstate", true);
      // localStorage.setItem("userinfo", JSON.stringify(user));
    },
    //登出
    logout(state) {
      state.user = null;
      state.isLogin = false;
      //将信息存入本地存储
      localStorage.setItem("loginstate", false);
      // localStorage.setItem("userinfo", JSON.stringify({}));
      localStorage.setItem("token",'');
    },
    //设置token
    settoken(state,token){
      // console.log("store存入token:",token);
      state.token = token;
      localStorage.setItem("token", token);
    },
    searchmovie(state,search){
      state.search = search;
    },
    changestatus(state, status) {
      state.pagestatus = status;
    },
  },
  actions: {
    //异步操作
  },
  //getters相当于计算属性
  getters: {},
});

export default store;
