<template>
  <div class="sub-nav">
          <ul class="navbar">
            <li>
              <router-link :to="{
                name:'电影列表',
                query:{
                    type:1
                }
              }"  >
                正在热映
              </router-link>
            </li>
            <li>
                <router-link :to="{
                name:'电影列表',
                query:{
                    type:2
                }
              }" >
                即将上映
              </router-link>
            </li>
            <li>
                <router-link :to="{
                name:'电影列表',
                query:{
                    type:3
                }
              }"  >
                经典影片
              </router-link>
            </li>
          </ul>
        </div>
</template>

<script>
export default {
    name:'subnav'
}
</script>

<style scoped>
.sub-nav {
  background-color: #47464a;
  height: 60px;
  /* width: 100%; */
  /* min-width: 1200px; */
  text-align: center;
  display: block;
  margin-top: 5px;

}

.navbar {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  height: 60px;
  margin: 29px
}

.navbar li {
  margin: 20px;
  font-size: larger;
  width: 130px;
  
  /* 隐藏前面的点 */
  list-style: none;
}
a{
    text-decoration: none;
    color: blanchedalmond;
}
</style>