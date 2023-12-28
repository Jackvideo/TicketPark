<template>
  <div class="common-layout">
        <div >
          <Header></Header>
        </div>
      <div style="margin-top: 50px;">
        <Subnav></Subnav>
        <el-row>
          <router-view></router-view>
        </el-row>
      </div>
  </div>
</template>

<script>
import store from '@/store';
import Header from '@/components/layout/Header.vue';
import Subnav from '@/components/layout/Subnav.vue';
export default {
  name: 'Movies',
  components: {
    Header,Subnav
  },
  mounted() {
    store.commit('changestatus', {
      'ishome': false,
      'ismovies': true,
      "ismyhome": false,
    })
  },
  data() {
    return {
    }
  }

}
</script>

<style  scoped>


/* 电影列表具体 */
.item {
  flex: 1;
  /* 加入这两个后每个item的宽度就生效了 */
  min-width: calc((100% - 10px) / 5);
  max-width: calc((100% - 10px) / 5);
  width: 200px;
  padding: 15px;
}

.item:nth-child(3n) {
  /* 去除第3n个的margin-right */
  margin-right: 0;
}

/* .item {
  
	
  width: 150px;
  margin: 20px;
 
} */

/* 电影图片 */
.item-image {
  width: 115px;
  height: 172.5px;
}

.item-list {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  /* 替代space-between布局方式 */
  height: 100%;

}
</style>

