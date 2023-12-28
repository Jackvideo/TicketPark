<template>
    <el-affix>
        <el-page-header @back="$router.push('/')">
            <template #content>
                <span class="text-large font-600 mr-3"> 订单 </span>
            </template>
        </el-page-header>
        <el-divider />
    </el-affix>
    <el-container style="font-size: 20;">
        <el-main>
            <div class="user-profile-nav">
                <h1> 个人中心</h1>
                <a class="info" href="/myhome">基本信息</a>
                <a class="orders" href="/myorder">我的订单</a>
            </div>

            <div class="order-info">
                <h1>订单列表</h1>
                <div class="orderlist">
                    <el-card class="box-card" v-for="item in orderlist" :key="item.orderid">
                        <!-- {{ item }} -->
                        <div style="margin-bottom: 5px;">订单编号: {{ item.orderid }}</div>
                        <div style="margin-bottom: 5px;">电影名称: {{ item.moviename }}</div>
                        <div style="margin-bottom: 5px;">下单时间: {{ item.ordertime }}</div>
                        <div style="margin-bottom: 5px;">票价: {{ item.ticketprice }}</div>
                        <div>订单状态: {{ item.orderstate }}</div>
                        <div style="margin-top: 15px;">
                            <el-button type="primary" @click="currentorder = item, paydialog = true"
                                :disabled="item.orderstate == 'CANCELED'||item.orderstate=='FINISHED'">去支付</el-button>

                            <el-popconfirm title="确定要取消订单吗" @confirm="cancelorder(item)">
                                <template #reference>
                                    <el-button :disabled="item.orderstate == 'CANCELED'||item.orderstate=='FINISHED'">
                                        取消订单</el-button>
                                </template>
                            </el-popconfirm>
                            <el-button v-show="item.orderstate == 'CANCELED'" type="danger"
                                @click="ordertodel = item, deletedialog = true">
                                删除订单</el-button>
                        </div>
                    </el-card>
                </div>
            </div>
        </el-main>
    </el-container>

    <!-- 删除订单弹窗界面 -->
    <el-dialog v-model="deletedialog" title="删除订单" width="30%" draggable>
        <span>请确认是否删除当前订单</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="deletedialog = false">取消</el-button>
                <el-button type="primary" @click="deleteorder">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>

    <!-- 支付弹窗界面 -->
    <el-dialog v-model="paydialog" title="确认支付" width="30%" draggable>
        <div>当前票价 : {{ currentorder.ticketprice }}</div>
        <span>请确认支付</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="paydialog = false">取消</el-button>
                <el-button type="primary" @click="payorder">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
import api from '@/api'
import store from '@/store';
import router from '@/router';
import { ElMessage } from 'element-plus';
export default {
    name: 'order',
    data() {
        return {
            userid: '',//用户id
            orderlist: [],   //订单列表
            paydialog: false,    //支付弹窗
            deletedialog: false, //删除弹窗
            currentorder: {},    //当前选中的订单
            ordertodel: {},  //当前选中删除的订单
        }
    },
    mounted() {
        // console.log(store.state);
        this.userid = store.state.user.id
        // 防止获取失败 从 本地存储中再获取一遍
        if(this.userid==undefined){
            this.userid = localStorage.getItem('userid')
        }
        
        //获取用户订单
        this.userorder(this.userid);
    },
    methods: {
        //根据id获取用户订单
        userorder(id) {
            api.userorder(id).then(res => {
                // console.log(res);
                this.orderlist = res.data;
            })
        },
        //取消订单
        cancelorder(order) {
            console.log(order);
            api.cancelorder(this.userid, order.orderid).then(res => {
                // console.log(res);
                ElMessage({ message: "取消订单成功", type: "success", duration: "1500" })
                //重新加载页面
                setTimeout(() => {
                    //重新加载页面
                    location.reload()
                }, 1500);
            })
        },
        //用户支付订单
        payorder() {
            api.payorder(this.userid, this.currentorder.ticketprice,this.currentorder.orderid).then(res => {
                // console.log(res);
                let code = res.data.code;
                //支付成功
                if (code == 200) {
                    ElMessage({ message: res.data.message, type: "success", duration: "1500" })
                    //关闭弹窗
                    this.paydialog = false;
                    //更新用户信息

                    //重新加载页面
                    setTimeout(() => {
                        //重新加载页面
                        location.reload()
                    }, 1500);
                } else {
                    ElMessage({ message: res.data.message, type: "error", duration: "1500" })
                    //关闭弹窗
                    this.paydialog = false;
                    return
                }

            })

        },
        //删除订单
        deleteorder() {
            //传入orderid删除
            api.deleteorder(this.ordertodel.orderid).then(res => {
                // console.log(res.data);
                //关闭弹窗
                this.deletedialog = false;
                ElMessage({ message: '删除订单成功', type: 'success', duration: 1500 })
                setTimeout(() => {
                    //重新加载页面
                    location.reload()
                }, 1500);
            })
        }
    },
    //对没有登陆的用户进行拒绝访问
    beforeRouteEnter(to, from, next) {
        //判断登陆状态
        if (eval(localStorage.getItem("loginstate")) == false) {
            ElMessage({ message: "请先登录", type: "warning", duration: "1500" })
            router.push("/login")
        } else {
            next();
        }
    }
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
    width: 200px;
    bottom: 0%;
    background-color: #f4f3f4;
    text-align: center;
    border-right: 1px solid #e1e1e1;
    height: 100%;
}

.box-card {
    width: 1000px;
    margin-bottom: 30px;

    text-align: left;
}

.order-info {
    margin-left: 200px;
    position: absolute;
    left: 10%;

}
</style>