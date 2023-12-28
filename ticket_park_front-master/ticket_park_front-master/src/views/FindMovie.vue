<template>
    <el-header style="--el-header-padding: 0 0px; --el-header-height: 0px;">
        <Header></Header>
    </el-header>
    <h1>电影搜索</h1>
    <div v-if="ishasdata">
        <!-- {{ search }} -->
        <el-row>
            <el-col :span="20" style="display: flex;" class="item-list">
                <div class="item-list">
                    <router-link v-for="item in movielist" :key="item.movieid" class="item" :to="{
                        name: '电影内容',
                        query: {
                            //传入电影id 具体检索
                            movieid: item.movieid
                        }
                    }">
                        <div>
                            <el-image :src="item.imageUrl" class="item-image" lazy></el-image>
                        </div>
                        <p>{{ item.moviename }}</p>
                        <!-- <p>{{ item.startTime }}</p> -->
                    </router-link>
                </div>
            </el-col>
            <el-col :span="4">
                <!-- 预留做别的信息 -->
            </el-col>
        </el-row>
    </div>
    <div v-else>
        <h1>暂未找到相关电影</h1>
    </div>
</template>

<script>
import Header from '@/components/layout/Header.vue'
import store from '@/store';
import api from '@/api';
export default {
    name: 'findmovie',
    components: {
        Header
    },
    data() {
        return {
            //当前页数
            currentPage: 1,
            movielist: [],

        }
    },
    computed: {
        search() {
            return store.state.search;
        },
        ishasdata() {
            if (this.movielist.length > 0)
                return true;
            else
                return false;
        }
    },
    watch: {
        search(newval, oldval) {
            //如果搜索内容变了 就执行api
            //根据传入的检索类型 使用不同的api检索电影
            let type = store.state.search.searchtype;
            //根据电影名称检索
            if (type == 1) {
                this.queryByname();
            } else if (type == 2) {//根据电影类型检索
                this.queryByType();
            } else {
                return;
            }
        }
    },
    mounted() {
        // console.log(store.state.search);
        //根据传入的检索类型 使用不同的api检索电影
        let type = store.state.search.searchtype;
        //根据电影名称检索
        if (type == 1) {
            this.queryByname();
        } else if (type == 2) {//根据电影类型检索
            this.queryByType();
        } else {
            return;
        }
    },
    methods: {
        //根据电影类型检索电影
        queryByType() {
            api.queryByType(this.search.searchcontent, this.currentPage).then(res => {
                console.log(res);
                this.movielist = res.data.data;
            })
        },
        //根据电影内容检索电影
        queryByname() {
            api.queryByname(this.search.searchcontent, this.currentPage).then(res => {
                console.log(res);
                this.movielist = res.data.data;
            })
        }
    }
}
</script>

<style scoped>
/* 电影列表具体 */
.item {
    flex: 1;
    /* 加入这两个后每个item的宽度就生效了 */
    min-width: calc((100% - 10px) / 5);
    max-width: calc((100% - 10px) / 5);
    width: 4500px;
    padding: 15px;
    overflow: hidden;
}

.item:nth-child(3n) {
    /* 去除第3n个的margin-right */
    margin-right: 0;
}


/* 电影图片 */
.item-image {
    width: 160px;
    height: 190px;
}

.item-list {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    /* 替代space-between布局方式 */
    height: 100%;
}

a {
    text-decoration: none;

}
</style>