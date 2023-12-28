<template>
  <div>
    <el-affix>
      <el-page-header @back="goback">
        <template #content>
          <span class="text-large font-600 mr-3"> 注册 </span>
        </template>
      </el-page-header>
      <el-divider />
    </el-affix>
  </div>
  <h2>
    <a href="/home" >Ticketpark</a>
  </h2>
  
  <fieldset style="margin-top: 20px;">

    <h1 style="text-align: center;">注册</h1>
    <el-form :model="registerForm" :rules="formRules" ref="registerFormRef" label-position="right" label-width="42%"
      status-icon>
      <el-form-item label="账户" prop="username">
        <el-input v-model="registerForm.username" placeholder="请输入用户名" style="width: 200px;" clearable />
      </el-form-item>

      <el-form-item label="密码" prop="password">
        <el-input v-model="registerForm.password" placeholder="请输入密码" type="password" show-password
          style="width: 200px;" />
      </el-form-item>

      <el-form-item label="确认密码" prop="checkpwd">
        <el-input v-model="registerForm.checkpwd" placeholder="请再次确认密码" type="password" show-password
          style="width: 200px;" />
      </el-form-item>

      <el-form-item label-width="40%" prop="agree">
        <el-checkbox size="large" v-model="registerForm.agree">
          我已同意<a href="http://dwww.baidu.com/duty/yinsiquan-policy.html">隐私条款和服务条款</a>
        </el-checkbox>
      </el-form-item>

      <el-button size="large" class="subBtn" @click="register(registerFormRef)" type="primary"
        :disabled="loading">点击注册</el-button>
      <el-button size="large" class="subBtn" @click="$router.push('/login')" type="primary">返回登陆</el-button>

    </el-form>
  </fieldset>
  <!-- 水印 -->
  <el-watermark :content="['ticketpark', 'ticketpark Plus']">
    <div style="height: 200px" />
  </el-watermark>
</template>

<script>
import store from '@/store';
import { reactive, ref } from "vue";
import { ElMessage } from 'element-plus'
import router from '@/router';
import api from '@/api';

export default {
  name: 'register',

  setup() {
    const registerForm = reactive({
      username: '',
      password: '',
      checkpwd: '',
      agree: false
    });
    //判断是否被点击 防止重复点击
    let loading = ref(false)

    // const validatorPwd = (rule, value, callback) => {
    //     // 检验密码强度
    //     if (/\d/.test(value) && /[a-z]/.test(value) && /[A-Z]/.test(value)) return callback();
    //     callback( new Error("密码强度较弱，请输入带有 大写字母、小写字母、数字三种字符组合的密码！"));
    // };
    //检验两次密码是否一致
    const checkpassword = (rule, value, callback) => {
      if (value != registerForm.password) {
        callback(new Error("两次输入的密码不一样"))
      } else {
        return callback();
      }
    }
    //校验id 必须包含数字和英文
    const checkid = (rule, value, callback) => {
      if (/\d/.test(value) && /[a-z]/.test(value)) return callback();
      if (/[\u4e00-\u9fa5]/.test(value)) { callback(new Error("请不要输入中文字符")) };
      callback(new Error("用户名必须包含字母和数字"));
    }
    const formRules = {
      username: [
        // required - 是否必填
        // message - 校验不通过的提示信息
        // trigger - 校验的触发方式【blur - 失去焦点触发；change - v-model绑定的值改变触发】
        { required: true, message: '用户名不能为空', trigger: 'blur' },
        { min: 2, max: 10, message: '用户名长度为 2~10 个字符', trigger: 'blur' },
        { validator: checkid, trigger: 'blur' }
      ],
      //校验密码
      password: [
        { required: true, message: '密码不能为空', trigger: 'blur' },
        // min - 最小长度
        // max - 最大长度
        { min: 6, max: 20, message: '密码长度为 6~20 个字符', trigger: 'blur' },
        // { validator: validatorPwd, triger: 'blur' }
      ],
      //再次确认密码
      checkpwd: [
        { required: true, message: '密码不能为空', trigger: 'blur' },
        { validator: checkpassword, triger: 'blur' }
      ],
      agree: [
        // 自定义校验规则
        {
          validator: (rule, value, callback) => {
            if (!value)
              return callback(new Error('请勾选同意协议！'));
            callback();
          },
          triger: 'change'
        }
      ]
    };

    // TODO 表单整体校验 + 登录
    const registerFormRef = ref(null);
    const register = () => {
      loading.value = true
      registerFormRef.value.validate((valid) => {
        if (valid) {
          //发送注册请求给后台
          api.register(registerForm.username, registerForm.password).then(response => {
            console.log(response.data);
            //返回状态码=1 说明注册失败
            if (response.data.code == 200) {
              ElMessage({ message: '注册成功', type: 'success', duration: 1500 })
              router.push({
                name: 'result', query: {
                  msg: 'success',
                  title: '成功'
                }
              });
            }
            else {
              ElMessage({ message: '该用户名已被注册', type: 'error', duration: 1500 })
            }
          }, error => {
            //弹出提示框
            ElMessage({ message:error.message, type: 'error', duration: 1500 })
          });
        } else {
          //校验不通过
          ElMessage({ message: '注册失败', type: 'error', duration: 1500 })

        }
      });
      setTimeout(() => {
        loading.value = false
      }, 1500)
    };
    //跳转到注册成功页面
    function submitsuccess() {
      this.$router.push({
        name: 'result',
        query: {
          msg: 'success',
          title: '成功'
        }
      })
    };
    function goback() {
      router.push("/login")
    }

    return {
      registerForm, register, formRules, registerFormRef, loading, submitsuccess, goback
    }
  },

}
</script>

<style scoped>
fieldset {

  /* 表单页面居中，宽度50% ,legend颜色设置，legend圆角*/
  border: 2px solid #DCDFE6;
  border-radius: 8px;
  margin: 0 auto;
  width: 50%;
  text-align: center;
  left: 50%;
}

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
</style>