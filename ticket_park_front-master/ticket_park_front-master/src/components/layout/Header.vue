<template>
    <el-row class="header">

        <el-col :span="3" :pull="1">
            <el-link type="primary" :underline="false" href="/" class="noselected">
                <el-avatar :size="50" src="https://upload.shejihz.com/2019/04/94048de477bb3a72dcef8abec4ce20dc.jpg" />
            </el-link>
        </el-col>

        <el-col :span="2">
            <el-link type="primary" :underline="false" @click="gotohome"
                :class="{ viewselected: ishome, viewnoselected: !ishome }">
                首页
            </el-link>
        </el-col>
        <el-col :span="2">
            <el-link type="primary" :underline="false" @click="gotomovies"
                :class="{ viewselected: ismovies, viewnoselected: !ismovies }">
                电影
            </el-link>
        </el-col>
        <el-col :span="6" class="search">
            <div class="mt-4">
                <el-input v-model="searchcontent" placeholder="请输入" class="input-with-select" size="large">
                    <template #prepend>
                        <el-select v-model="selecttype" placeholder="请选择" style="width: 115px" size="large">
                            <el-option label="电影名称" value="1" />
                            <el-option label="类型" value="2" />
                            <!-- <el-option label="Tel" value="3" /> -->
                        </el-select>
                    </template>
                    <template #append>
                        <el-button icon="Search" @click="searchmovie" />
                    </template>
                </el-input>
            </div>
        </el-col>
        <el-col :span="4">

        </el-col>
        <el-col :span="4" style="padding-right: 0%;" :push="1">
            <div v-if="isuserlogin">
                <el-link :underline="false" @click="gotomyhome"
                    :class="{ viewselected: ismyhome, viewnoselected: !ismyhome }">
                    我的主页
                </el-link>
            </div>
            <div v-else>
                <el-link :underline="false" @click="gotologin" class="viewnoselected">
                    注册/登录
                </el-link>
            </div>
        </el-col>
    </el-row>
</template>

<script>
import store from '@/store'
import { ElMessage } from 'element-plus';

export default {
    name: 'Header',
    //数据
    data() {
        return {
            isuserlogin: false,
            selecttype: '',  //选中的类型
            searchcontent: '',   //搜索框内容
        }
    },
    mounted() {
        //从本地存储中获取状态
        this.isuserlogin = eval(localStorage.getItem("loginstate"));
        console.log(this.isuserlogin);
        // this.selecttype = store.state.search.searchtype;
        // this.searchcontent = store.state.search.searchcontent;
    },
    // watch:{
        
    // },
    computed: {
        ishome() {
            return store.state.pagestatus.ishome
        },
        ismovies() {
            return store.state.pagestatus.ismovies
        },
        ismyhome() {
            return store.state.pagestatus.ismyhome
        },
    },
    methods: {
        //跳转到主页
        gotohome() {
            this.$router.push('/home')
        },
        //跳转到登录页
        gotologin() {
            this.$router.push('/login')
        },
        //跳转到电影页面
        gotomovies() {
            this.$router.push('/movies/list?type=1')
        },
        //跳转到我的主页
        gotomyhome() {
            this.$router.push('/myhome')
        },
        //搜索电影
        searchmovie() {
            //先获取搜索内容 和选择类型
            let type = this.selecttype;
            let content = this.searchcontent;
            console.log("类型", type, "搜索内容", content);
            //判断是否为空
            if (type == '') {
                ElMessage({ message: '请选择搜索类型', type: 'warning' })
                return;
            }
            else if (content == '') {
                ElMessage({ message: '输入不可以为空', type: 'warning' })
                return;
            }
            //切换页面头部状态
            store.commit('changestatus', {
                'ishome': false,
                'ismovies': false,
                "ismyhome": false,
            })
            //存入搜搜类型和内容
            store.commit('searchmovie',{
                'searchtype':type,
                'searchcontent':content
            })
            //跳转
            this.$router.push("/movies/findmovie");
            
        }
    },


}
</script>

<style scoped>
.header {
    position: fixed;
    top: 0;
    z-index: 999;
    width: 100%;
    min-width: 1200px;
    background-color: #fff;
    border-bottom: 1px solid #d8d8d8;
    /* font-size: 40px; */
    height: 60px;
}

.viewselected {
    background-color: #ef4238;
    height: 60px;
    width: 100%;
    font-weight: bolder;
    font-size: 20px;
}

.viewnoselected {
    height: 60px;
    font-size: 20px;
}

.search {
    margin-left: 140px;
    display: flex;
    align-items: center;
}
</style>