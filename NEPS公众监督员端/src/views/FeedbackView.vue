<template>
  <div class="feedback-detail-page">
    <van-nav-bar
      title="反馈详情"
      left-arrow
      @click-left="goBack"
    />
    
    <div class="content">
      <p>反馈详情页面</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.back()
}
</script>

<style scoped>
.feedback-detail-page {
  min-height: 100vh;
  background: #f5f5f5;
}

.content {
  padding: 20px;
}
</style>
