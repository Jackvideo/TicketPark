<template>
  <div class="common-layout">
    <el-container>
      <el-header style="--el-header-padding: 0 0px; --el-header-height: 0px;">
        <Header></Header>
      </el-header>
      <div style="margin-top: 50px;">
        <h2>欢迎使用ticketpark电影购票系统</h2>
      </div>
      <el-row>
        <el-col :span="8">
          <h1>最近热映</h1>
          <div class="block">
            <!-- <span class="demonstration">最近热映</span> -->
            <el-carousel trigger="click" height="350px" class="recommend">
              <el-carousel-item v-for="item in infolist" :key="item.id">
                <el-image :src="item.picurl" fit="contain" style="height: 300px;margin-top: 10px;"></el-image>
                <!-- <h3 class="small">{{ item.name }}</h3> -->
                <div>{{ item.name }}</div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-col>
        <el-col :span="12">
          <h1 style="color: red;">全部电影</h1>
          <div class="item-list">

            <router-link v-for="item in movielist" :key="item.movieid" class="item" :to="{
              name: '电影内容',
              query: {
                //传入电影id 具体检索
                movieid: item.movieid
              }
            }">
              <div>
                <el-image :src="item.imageUrl" class="item-image" lazy>
                  <!-- 图片加载失败时 -->
                  <template #error>
                    <div class="image-slot">
                      <el-image src="https://img95.699pic.com/xsj/0e/mp/ra.jpg%21/fh/300" class="item-image"></el-image>
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
          <h1>今日票房</h1>

        </el-col>
      </el-row>


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
    </el-container>
  </div>
</template>

<script>
import store from '@/store'
import Header from '@/components/layout/Header.vue'
import api from '@/api'
export default {
  name: 'home',
  components: {
    Header
  },
  data() {
    return {
      // 最近热映展示
      infolist: [
        {
          id: 1,
          picurl: 'https://p0.pipi.cn/mmdb/fb73862f71f33887a987a9a783320257cceb3.jpg?imageView2/1/w/464/h/644',
          name: '名侦探柯南：黑铁的鱼影'
        },
        {
          id: 2,
          picurl: 'https://p0.pipi.cn/mmdb/fb73865111ef2a3139c9fdffb6b1c2b0210e0.jpg?imageView2/1/w/464/h/644',
          name: '蜡笔小新：新次元！超能力大决战'
        },
        {
          id: 3,
          picurl: 'https://p0.pipi.cn/mmdb/fb73862f06d923537c0e1350dd782507d890f.jpg?imageView2/1/w/464/h/644',
          name: '三大队'
        },
        {
          id: 4,
          picurl: 'https://p0.pipi.cn/mmdb/fb7386517a33382c957a3580cf8bda495e186.jpg?imageView2/1/w/464/h/644',
          name: '一闪一闪亮星星'
        },
      ],
      movielist: [], //电影列表
    }
  },
  mounted() {
    store.commit('changestatus', {
      'ishome': true,
      'ismovies': false,
      "ismyhome": false,
    })
    api.list().then(res => {
      console.log(res);
      this.movielist = res.data;
    })
  }
}
</script>

<style scoped>
/* .recommend{
  
} */


.block {
  width: 60%;
  margin-left: 20%;
  margin-top: 20px;
}

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