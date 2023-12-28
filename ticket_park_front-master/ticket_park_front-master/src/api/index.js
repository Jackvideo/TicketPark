import axios from "axios";

// axios.defaults.baseURL = "http://localhost:8088/";
axios.defaults.timeout = 3000; //设置超时时间为3s

export default {
  // ---------------- 登录 注册相关的api ----------------
  login(username, password) {
    return axios({
      url: "http://localhost:8088/tp_user/login",
      method: "post",
      data: {
        username,
        password,
      },
    });
  },
  register(username, password) {
    return axios({
      url: "http://localhost:8088/tp_user/register",
      method: "post",
      data: {
        username,
        password,
      },
    });
  },
  //获取用户信息
  userinfo(token) {
    return axios.get("http://localhost:8088/tp_user/info", {
      params: {
        token
      }
    })
  },
  //登出用户
  logout(token) {
    return axios({
      url: "http://localhost:8088/tp_user/logout",
      method: "post",
      headers: {
        token: token
      }
    })
  },
  //修改用户个人信息
  modifyuser(userid, username, password, balance) {
    return axios({
      url: 'http://localhost:8088/tp_user/modify',
      method: 'post',
      data: {
        userid, username, password, balance
      }
    })
  },

  // 电影相关的api

  //获取所有电影列表
  list() {
    return axios.get("http://localhost:8088/tp_movie/list");
  },

  //获取正在热映电影
  hotmovie() {
    return axios.get("http://localhost:8088/tp_movie/hot");
  },
  //获取即将上映的电影
  futuremovie() {
    return axios.get("http://localhost:8088/tp_movie/future");
  },
  //获取经典影片电影
  epicmovie() {
    return axios.get("http://localhost:8088/tp_movie/epic");
  },

  //根据id获取单个电影信息
  findone(id) {
    return axios.get("http://localhost:8088/tp_movie/" + id);
  },
  //根据电影类型检索电影
  queryByType(type, currentPage) {
    return axios({
      url: 'http://localhost:8088/tp_movie/queryByType',
      method: 'get',
      params: {
        type, currentPage
      }
    })
  },
  //根据电影名称检索电影
  queryByname(moviename, currentPage) {
    return axios({
      url: 'http://localhost:8088/tp_movie/queryByName',
      method: 'get',
      params: {
        moviename, currentPage
      }
    })
  },
  //电影点赞
  likemovie(movieid) {
    return axios({
      url: 'http://localhost:8088/tp_movie/like' + movieid,
      method: 'post',
    })
  },
  //电影取消点赞
  unlikemovie(movieid) {
    return axios({
      url: 'http://localhost:8088/tp_movie/unlike' + movieid,
      method: 'post',
    })
  },
  //用户发表评论
  createComment(movieid, userid, comment) {
    return axios({
      url: 'http://localhost:8088/tp_movie/createComment',
      method: 'post',
      data: {
        movieid, userid, comment
      }
    })
  },
  //获取某一部电影的所有评论
  findallcomment(id) {
    return axios.get('http://localhost:8088/tp_movie/findCommentsByMovieId' + id)
  },
  //根据id获取用户名
  getName(userid) {
    return axios({
      url: 'http://localhost:8088/tp_user/getName',
      method: 'get',
      params: {
        userid
      }
    })
  },
  //删除评论
  deletecomment(id, commentid, movieid, userid, comment) {
    return axios({
      url: 'http://localhost:8088/tp_movie/deleteComment' + id,
      method: 'delete',
      data: {
        commentid, movieid, userid, comment
      }
    })
  },

  // 订单相关api
  //创建订单
  create(userid, movieid, moviename, username, ticketprice) {
    return axios({
      url: 'http://localhost:8088/tp_order/create',
      method: 'post',
      data: {
        userid, movieid, moviename, username, ticketprice
      }
    })
  },
  //根据用户id查询订单
  userorder(id) {
    return axios.get('http://localhost:8088/tp_order/uid=' + id)
  },
  //用户取消订单
  cancelorder(userid, orderid) {
    return axios({
      url: 'http://localhost:8088/tp_user/cancel',
      method: 'post',
      data: {
        userid, orderid
      }
    })
  },
  //支付订单
  payorder(userid, ticketprice, orderid) {
    return axios.post('http://localhost:8088/tp_user/pay', {
      userid, ticketprice, orderid
    })
  },
  //删除订单
  deleteorder(id) {
    return axios.delete('http://localhost:8088/tp_order/' + id)
  }
};
