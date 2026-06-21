<template>
  <div class="task-list-page">
    <!-- 蓝色科技主题头部 -->
    <div class="header-bg-blue" style="height: 180px;">
      <div class="tech-illustration" style="transform: translate(-50%, -55%) scale(0.85);">
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="platform-icon">📊</div>
        <div class="platform-icon">🔍</div>
        <div class="platform-icon">📱</div>
      </div>
      <div class="wave-bottom">
        <svg viewBox="0 0 375 60" preserveAspectRatio="none">
          <path d="M0,30 Q93.75,60 187.5,30 T375,30 L375,60 L0,60 Z" fill="white" />
          <path d="M0,40 Q93.75,70 187.5,40 T375,40 L375,60 L0,60 Z" fill="rgba(255,255,255,0.5)" />
        </svg>
      </div>
    </div>

    <!-- 页面头部 -->
    <div class="page-header">
      <span class="back-btn" @click="goBack">←</span>
      <span class="page-title-text">反馈任务列表</span>
    </div>

    <!-- 任务列表 -->
    <div class="task-list">
      <template v-if="taskList.length > 0">
        <div class="task-item" v-for="task in taskList" :key="task.id" @click="goToDetail(task.id)">
          <div class="task-level" :class="`level-${task.level}`">{{ getLevelText(task.level) }}</div>
          <div class="task-info">
            <div class="task-location">{{ task.province }} {{ task.city }} {{ task.date }}</div>
            <div class="task-address">{{ task.address }}</div>
          </div>
          <div class="task-action">
            <span>去检测</span>
            <span>🔍</span>
          </div>
        </div>
      </template>
      <div v-else class="empty-state">
        <div class="empty-icon">📋</div>
        <div class="empty-text">暂无反馈任务</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { listAqiFeedbackByGmId } from '@/api/feedback'
  import { listGridProvinceAll, listGridCityByProvinceId } from '@/api/area'
  import type { GridProvince, GridCity } from '@/api/area'

  const router = useRouter()
  const taskList = ref < any[] > ([])
  const provinceMap = ref < Record < number, string>> ({})
  const cityMap = ref < Record < number, string>> ({})

  const levelMap: Record<number, string> = {
    1: '一',
    2: '二',
    3: '三',
    4: '四',
    5: '五',
    6: '六'
  }

  const getLevelText = (level: number) => {
    return levelMap[level] || level.toString()
  }

  const loadTaskList = async () => {
    try {
      const gmInfo = JSON.parse(localStorage.getItem('gmInfo') || '{}')
      const gmId = gmInfo.gmId
      if (!gmId) {
        router.push('/login')
        return
      }

      // 加载省份
      const provinces: any = await listGridProvinceAll()
      provinces?.forEach((item: GridProvince) => {
        provinceMap.value[item.provinceId] = item.provinceName
      })

      // 加载任务列表
      const res: any = await listAqiFeedbackByGmId(gmId)
      const list = res || []

      // 只加载任务中出现的省份的城市
      const provinceIds = new Set(list.map((item: any) => item.provinceId))
      for (const provinceId of provinceIds) {
        const cities: any = await listGridCityByProvinceId(Number(provinceId))
        cities?.forEach((item: GridCity) => {
          cityMap.value[item.cityId] = item.cityName
        })
      }

      taskList.value = list.map((item: any) => ({
        id: item.afId,
        level: item.estimatedGrade,
        province: provinceMap.value[item.provinceId] || '',
        city: cityMap.value[item.cityId] || '',
        date: item.afDate,
        address: item.address
      }))
    } catch (error) {
      alert('加载任务列表失败')
    }
  }

  onMounted(() => {
    loadTaskList()
  })

  const goBack = () => {
    router.back()
  }

  const goToDetail = (id: number) => {
    router.push(`/task-detail/${id}`)
  }
</script>

<style scoped>
  .task-list-page {
    min-height: 100vh;
    background: #f5f5f5;
  }

  /* 蓝色科技主题头部 */
  .header-bg-blue {
    width: 100%;
    background: linear-gradient(180deg, #64b5f6 0%, #42a5f5 50%, #2196f3 100%);
    position: relative;
    overflow: hidden;
  }

  .wave-bottom {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 60px;
  }

  .wave-bottom svg {
    width: 100%;
    height: 100%;
  }

  /* 科技插画 */
  .tech-illustration {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 280px;
    height: 150px;
  }

  .tech-building {
    position: absolute;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
  }

  .tech-building::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 4px;
    background: #1976d2;
    top: 0;
    border-radius: 8px 8px 0 0;
  }

  .tech-building:nth-child(1) {
    width: 50px;
    height: 80px;
    left: 30px;
    bottom: 20px;
  }

  .tech-building:nth-child(2) {
    width: 40px;
    height: 100px;
    left: 90px;
    bottom: 20px;
  }

  .tech-building:nth-child(3) {
    width: 60px;
    height: 70px;
    left: 140px;
    bottom: 20px;
  }

  .tech-building:nth-child(4) {
    width: 45px;
    height: 90px;
    left: 210px;
    bottom: 20px;
  }

  .tech-person {
    position: absolute;
    width: 20px;
    height: 35px;
  }

  .tech-person::before {
    content: '';
    position: absolute;
    width: 10px;
    height: 10px;
    background: #ff7043;
    border-radius: 50%;
    top: 0;
    left: 5px;
  }

  .tech-person::after {
    content: '';
    position: absolute;
    width: 16px;
    height: 20px;
    background: #5c6bc0;
    border-radius: 4px;
    bottom: 0;
    left: 2px;
  }

  .tech-person:nth-child(5) {
    left: 45px;
    bottom: 20px;
  }

  .tech-person:nth-child(6) {
    left: 105px;
    bottom: 20px;
  }

  .tech-person:nth-child(7) {
    left: 160px;
    bottom: 20px;
  }

  .tech-person:nth-child(8) {
    left: 225px;
    bottom: 20px;
  }

  .platform-icon {
    position: absolute;
    width: 40px;
    height: 40px;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
  }

  .platform-icon:nth-child(9) {
    left: 20px;
    top: 30px;
  }

  .platform-icon:nth-child(10) {
    right: 30px;
    top: 40px;
  }

  .platform-icon:nth-child(11) {
    left: 50px;
    top: 80px;
  }

  /* 页面头部 */
  .page-header {
    display: flex;
    align-items: center;
    padding: 15px;
    background: #fff;
    border-bottom: 1px solid #eee;
  }

  .back-btn {
    font-size: 20px;
    color: #666;
    margin-right: 10px;
    cursor: pointer;
  }

  .page-title-text {
    flex: 1;
    text-align: center;
    font-size: 18px;
    color: #333;
    margin-right: 30px;
  }

  /* 任务列表 */
  .task-list {
    padding: 15px;
  }

  .task-item {
    background: #fff;
    border-radius: 10px;
    padding: 15px;
    margin-bottom: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    display: flex;
    align-items: center;
    gap: 12px;
    cursor: pointer;
    transition: transform 0.2s;
  }

  .task-item:active {
    transform: scale(0.98);
  }

  .task-level {
    width: 36px;
    height: 36px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: white;
    font-weight: bold;
    flex-shrink: 0;
  }

  .level-1 {
    background: #4caf50;
  }

  .level-2 {
    background: #8bc34a;
  }

  .level-3 {
    background: #ff9800;
  }

  .level-4 {
    background: #f44336;
  }

  .level-5 {
    background: #9c27b0;
  }

  .task-info {
    flex: 1;
  }

  .task-location {
    font-size: 14px;
    color: #333;
    margin-bottom: 4px;
    font-weight: 500;
  }

  .task-address {
    font-size: 12px;
    color: #999;
  }

  .task-action {
    display: flex;
    align-items: center;
    gap: 4px;
    color: #2196f3;
    font-size: 14px;
  }

  /* 空状态 */
  .empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 80px 20px;
  }

  .empty-icon {
    font-size: 64px;
    margin-bottom: 16px;
    opacity: 0.6;
  }

  .empty-text {
    font-size: 15px;
    color: #999;
  }
</style>