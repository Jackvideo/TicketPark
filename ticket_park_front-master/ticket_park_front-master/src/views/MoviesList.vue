<template>
    <div style="width: 100%;">
        <h1>{{ title }}</h1>
        <el-row v-if="ishasdata">
            <el-col :span="20" style="display: flex;" class="item-list">
                <div class="item-list">
                    <router-link v-for="item in filmlist" :key="item.movieid" class="item" :to="{
                        name: '电影内容',
                        query: {
                            //传入电影id 具体检索
                            movieid: item.movieid
                        }
                    }">
                        <div>
                            <el-image :src="item.imageUrl" class="item-image" lazy>
                                <template #error>
                                    <div class="image-slot">
                                        <el-image src="https://img95.699pic.com/xsj/0e/mp/ra.jpg%21/fh/300"
                                            class="item-image"></el-image>
                                        <!-- <el-icon><Hide /></el-icon> -->
                                    </div>
                                </template>
                            </el-image>
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
        <div v-else>

        </div>
        <!-- 回到顶部 -->
        <el-backtop :bottom="100">
            <div style="
        height: 100%;
        width: 100%;
        background-color: var(--el-bg-color-overlay);
        box-shadow: var(--el-box-shadow-lighter);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      ">
                UP
            </div>
        </el-backtop>
    </div>
</template>

<script>
import api from '@/api';

export default {
    name: 'MovieList',
    mounted() {
        let type = this.$route.query.type;
        if (type == 2) {
            this.title = '即将上映'
            api.futuremovie().then(res => {
                console.log(res);
                this.filmlist = res.data;
            })
        }
        else if (type == 3) {
            this.title = '经典影片'
            api.epicmovie().then(res => {
                // console.log(res);
                this.filmlist = res.data;
            })
        }
        else {
            this.title = '正在热映'
            api.hotmovie().then(res => {
                console.log(res);
                this.filmlist = res.data;
            })
        }

        //获取所有电影列表
        // this.getmovielist();

    },
    data() {
        return {
            // 标题
            title: '',
            filmlist: []    //电影列表

        }
    },
    computed: {
        ishasdata() {
            if (this.filmlist.length > 0)
                return true
            else
                return false
        }
    },
    methods: {
        //获取所有电影列表
        getmovielist() {
            api.list().then(res => {
                // console.log(res);
                this.filmlist = res.data;
            })
        }
    },
}
</script>

<style scoped>
/* 电影列表具体 */
.item {
    flex: 1;
    /* 加入这两个后每个item的宽度就生效了 */
    min-width: calc((100% - 10px) / 5);
    max-width: calc((100% - 20px) / 5);
    width: 450px;
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