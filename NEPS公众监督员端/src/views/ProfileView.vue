<template>
  <div class="profile-page">
    <!-- 头部 -->
    <div class="page-header">
      <van-icon name="arrow-left" class="back-btn" @click="goBack" />
      <span class="page-title">个人中心</span>
      <span style="width: 24px"></span>
    </div>

    <!-- 用户信息卡片 -->
    <div class="user-card">
      <div class="user-avatar">
        <van-icon name="user-circle-o" />
      </div>
      <div class="user-info">
        <div class="user-name">{{ userInfo?.name || '用户' }}</div>
        <div class="user-phone">{{ userInfo?.phone || '' }}</div>
      </div>
    </div>

    <!-- 统计信息 -->
    <div class="stats-section">
      <div class="stat-item">
        <div class="stat-num">{{ feedbackCount }}</div>
        <div class="stat-label">我的反馈</div>
      </div>
      <div class="stat-item">
        <div class="stat-num">{{ processedCount }}</div>
        <div class="stat-label">已处理</div>
      </div>
      <div class="stat-item">
        <div class="stat-num">{{ pendingCount }}</div>
        <div class="stat-label">待处理</div>
      </div>
    </div>

    <!-- 功能菜单 -->
    <div class="menu-section">
      <van-cell-group inset>
        <van-cell title="编辑资料" icon="edit" is-link @click="editProfile" />
        <van-cell title="修改密码" icon="lock" is-link @click="changePassword" />
        <van-cell title="关于我们" icon="info-o" is-link @click="aboutUs" />
        <van-cell title="联系客服" icon="service-o" is-link @click="contactService" />
      </van-cell-group>
    </div>

    <!-- 退出登录 -->
    <div class="logout-section">
      <van-button round block type="danger" plain @click="logout">
        退出登录
      </van-button>
    </div>

    <!-- 底部导航 -->
    <van-tabbar v-model="activeTab" route>
      <van-tabbar-item icon="home-o" to="/">首页</van-tabbar-item>
      <van-tabbar-item icon="chart-o" to="/aqi-levels">AQI等级</van-tabbar-item>
      <van-tabbar-item icon="records" to="/history">历史</van-tabbar-item>
      <van-tabbar-item icon="user-o" to="/profile">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { showDialog, showToast } from 'vant'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref(3)

const userInfo = computed(() => userStore.userInfo)

const feedbackCount = ref(12)
const processedCount = ref(8)
const pendingCount = ref(4)

const goBack = () => {
  router.back()
}

const editProfile = () => {
  showToast('编辑资料功能开发中')
}

const changePassword = () => {
  showToast('修改密码功能开发中')
}

const aboutUs = () => {
  showDialog({
    title: '关于我们',
    message: '东软环保公众监督平台致力于改善环境质量，欢迎公众参与监督。',
    confirmButtonText: '知道了'
  })
}

const contactService = () => {
  showDialog({
    title: '联系客服',
    message: '客服电话：400-123-4567\n工作时间：9:00-18:00',
    confirmButtonText: '知道了'
  })
}

const logout = () => {
  showDialog({
    title: '确认退出',
    message: '确定要退出登录吗？',
    showCancelButton: true,
    confirmButtonText: '确认',
    cancelButtonText: '取消'
  }).then(() => {
    userStore.logout()
    router.push('/login')
    showToast('已退出登录')
  }).catch(() => {
    // 取消
  })
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #c8e6c9 0%, #e8f5e9 30%, #b2dfdb 100%);
  padding-bottom: 60px;
}

.page-header {
  display: flex;
  align-items: center;
  padding: 15px;
  background: rgba(255, 255, 255, 0.5);
}

.back-btn {
  font-size: 20px;
  color: #666;
  cursor: pointer;
}

.page-title {
  flex: 1;
  text-align: center;
  font-size: 18px;
  color: #333;
  font-weight: 500;
}

.user-card {
  background: rgba(255, 255, 255, 0.9);
  margin: 15px;
  border-radius: 10px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-avatar {
  width: 60px;
  height: 60px;
  background: #5c9ead;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-avatar .van-icon {
  font-size: 36px;
  color: white;
}

.user-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.user-phone {
  font-size: 14px;
  color: #666;
}

.stats-section {
  display: flex;
  justify-content: space-around;
  background: rgba(255, 255, 255, 0.9);
  margin: 0 15px 15px;
  border-radius: 10px;
  padding: 20px;
}

.stat-item {
  text-align: center;
}

.stat-num {
  font-size: 24px;
  font-weight: bold;
  color: #5c9ead;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 13px;
  color: #666;
}

.menu-section {
  margin: 0 15px;
}

.logout-section {
  margin: 30px 15px;
}

:deep(.van-cell-group--inset) {
  margin: 0;
}

:deep(.van-tabbar) {
  background: rgba(255, 255, 255, 0.95);
}
</style>
