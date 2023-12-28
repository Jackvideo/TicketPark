<template>
  <el-result icon="success" title="成功" sub-title="请跟随提示进行操作">
    <template #extra>
      <div style="margin-bottom: 20px;">将在{{ sj }}秒后返回</div>
      <el-button type="primary" @click="gotologin">Back</el-button>
    </template>
  </el-result>
</template>

<script>
import { ElMessage } from 'element-plus';
import router from '@/router';
export default {
  name: "Result",
  data() {
    return {
      sj: 5,
      timer: null,
    }
  },
  // beforeRouteEnter(to,from,next){
  // },
  watch: {
    sj: {
      handler(newval, oldval) {
        console.log(newval, oldval);
        if (newval === 0) {
          clearInterval(this.timer)
          this.timer = null
          //跳转主页
          this.$router.push('/login');
        }
      }
    }
  },
  methods: {
    gotologin() {
      this.$router.push('/login')
    }
  },
  mounted() {
    this.timer =  setInterval(()=>{
      this.sj-=1;
    },1000)
  },
  beforeUnmount() {
    clearInterval(this.timer)
  },
  //禁止从别的途径进入
  beforeRouteEnter(to, from, next) {
    if(from.path=='/register'){
      next();
    }else{
      ElMessage({message:'禁止访问',type:'error'})
      router.push("/home")
    }
  },
}


</script>

<style></style>