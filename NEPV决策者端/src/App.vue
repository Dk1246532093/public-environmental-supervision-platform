<template>
  <DashboardView />
</template>

<script setup lang="ts">
import DashboardView from './views/DashboardView.vue'
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
}

body {
  background: #0a1628;
}
</style>
