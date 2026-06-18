<!-- //布局组件
element-plus 布局组件
<el-container> 外层容器
<el-header> 顶栏
<el-aside> 侧边栏
<el-main> 主内容区
<el-footer> 底栏 -->
<template>
  <el-container
    style="display: flex; flex-direction: column; width: 100%; height: 100%"
  >
    <el-header style="flex: 0 0 60px; background-color: #409eff">
      <div>
        <el-icon style="font-size: 30px; margin-right: 8px"><Phone /></el-icon>
        <h1>东软环保公众监督平台管理系统</h1>
      </div>
      <div>
        <p>系统管理员：{{admins.adminCode}}</p>
        <el-button type="danger" size="small" @click="out">
          OUT&nbsp;&nbsp;<el-icon><DArrowRight /></el-icon>
        </el-button>
      </div>
    </el-header>
    <el-container style="flex: 1">
      <el-aside width="200px" style="background-color: #79bbff">
        <el-menu
          active-text-color="#ffd04b"
          background-color="#79bbff"
          class="el-menu-vertical-demo"
          default-active="1"
          text-color="#fff"
          router
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><ChatDotRound /></el-icon>
              <span>公众监督数据管理</span>
            </template>
            <el-menu-item index="/index/feedbackList"
              >公众监督数据列表</el-menu-item
            >
            <el-menu-item index="/index/statisticsList"
              >确认AQI数据列表</el-menu-item
            >
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><DocumentCopy /></el-icon>
              <span>统计数据管理</span>
            </template>
            <el-menu-item index="/index/provinceItemTotalStatis"
              >省分组检查统计</el-menu-item
            >
            <el-menu-item index="/index/aqiDistributeTotalStatis"
              >AQI指数分布统计</el-menu-item
            >
            <el-menu-item index="/index/aqiTrendTotalStatis"
              >AQI指数趋势统计</el-menu-item
            >
            <el-menu-item index="/index/otherTotalStatis"
              >其它数据统计</el-menu-item
            >
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
    <el-footer style="flex: 0 0 60px; background-color: #409eff"
      >&copy; 版权所有 东软睿道 www.neusoft.com</el-footer
    >
  </el-container>
</template>

<script>
export default {
  name: 'IndexPage'
}
</script>

<script setup>
import {
  Phone,
  DArrowRight,
  ChatDotRound,
  DocumentCopy,
} from "@element-plus/icons-vue";
import { getSessionStorage, removeSessionStorage } from '../common.js';
import { useRouter } from 'vue-router';

const admins = getSessionStorage('admins') || {};
const router = useRouter();

const out = ()=>{
  removeSessionStorage('admins');
  router.push('/login');
}
</script>
<style scoped>
.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  color: #fff;
  user-select: none;
}
.el-header div {
  display: flex;
  align-items: center;
}
.el-header div h1 {
  font-weight: 500;
  font-size: 24px;
  letter-spacing: 1px;
}
.el-header div p {
  font-size: 16px;
  margin-right: 30px;
}
.el-footer {
  text-align: center;
  line-height: 60px;
  font-size: 14px;
  color: #fff;
}
</style>
