<template>
  <router-view></router-view>
</template>

<script setup>
import axios from 'axios'
import {provide} from 'vue'
import {ElMessage} from 'element-plus'
// 设置一下全局的axios请求
axios.defaults.baseURL = 'http://localhost:8080/nepm/'
//封装共通消息提示框
const myElMessage = (message,type) => {
  ElMessage({
    message: message,
    type: type,
    showClose: true
  })
}
provide('myElMessage', myElMessage)
provide('axios', axios)

</script>

<style>
/********************** CSS重置 *********************/
html,
body,
div,
span,
h1,
h2,
h3,
h4,
h5,
h6,
ul,
ol,
li,
p {
  margin: 0;
  padding: 0;
}
html,
body,
#app {
  width: 100%;
  height: 100%;
  font-family: "微软雅黑";
}
ul,
ol {
  list-style: none;
}
a {
  text-decoration: none;
}
</style>