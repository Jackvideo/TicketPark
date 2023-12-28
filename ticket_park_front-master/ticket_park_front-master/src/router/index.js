import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import Movies from "../views/Movies.vue";
import Result from "../views/Result.vue";
import Register from "../views/Register.vue";
import Myhome from "../views/Myhome.vue";
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: { title: "主页" },
  },
  {
    path: "/home",
    name: "Home2",
    component: Home,
    meta: { title: "主页" },
  },
  {
    path: "/login",
    name: "login",
    component: Login,
    meta: { title: "登陆" },
  },
  {
    path: "/register",
    name: "register",
    component: Register,
    meta: { title: "注册" },
  },
  {
    path: "/movies",
    name: "movies",
    component: Movies,
    meta: { title: "电影" },
    children:[
      {
        path:'list',
        name:'电影列表',
        component:()=>import("../views/MoviesList")
      }
    ]
  },
  {
    path: "/result",
    name: "result",
    component: Result,
    // meta:{'title':'结果'}
  },
  {
    path: "/myhome",
    name: "Myhome",
    component: Myhome,
    meta: { title: "我的主页" },
  },
  //404页面捕获
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    meta: { title: "错误" },
    component: () => import("../views/404.vue"),
  },
  //电影内容页面
  {
    path:'/movies/detail',
    name:'电影内容',
    meta:{title:"电影"},
    component:()=>import("../views/MovieDetail")
  },
  //搜索电影展示
  {
    path:'/movies/findmovie',
    name:'电影搜索',
    meta:{title:"电影搜索"},
    component:()=>import("../views/FindMovie")
  },
  //订单页面
  {
    path:'/myorder',
    name:'订单',
    meta:{title:"订单"},
    component:()=>import("../views/Order")
  }

];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

//增加全局路由守卫
// router.beforeEach((to,from,next)=>{
//   if(to.path==='/movies'){
//     let token = localStorage.getItem('testtoken')
//     if(token==='test'){
//       next()
//     }else{
//       window.alert('请先登录')
//     }

//   }
// })

// 页面标题
router.afterEach((to, from) => {
  // console.log(to,from);
  let title = to.meta.title;
  if (title == null) {
    title = to.query.title;
  }

  document.title = title;
});

export default router;
