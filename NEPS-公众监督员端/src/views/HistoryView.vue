<template>
  <div class="history-page">
    <!-- 头部 -->
    <div class="page-header">
      <van-icon name="arrow-left" class="back-btn" @click="goBack" />
      <span class="page-title">反馈历史</span>
      <span style="width: 24px"></span>
    </div>

    <!-- 反馈列表 -->
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
        <div class="feedback-list">
          <div v-for="(item, index) in feedbackList" :key="index" class="feedback-item">
            <div class="feedback-header">
              <span class="feedback-time">{{ item.time }}</span>
              <span :class="['feedback-status', item.status]">
                {{ getStatusText(item.status) }}
              </span>
            </div>
            <div class="feedback-content">
              <div class="feedback-location">
                <van-icon name="location-o" />
                {{ item.province }} {{ item.city }} {{ item.address }}
              </div>
              <div class="feedback-aqi">
                <span :class="['aqi-tag', `level-${item.aqiLevel}`]">
                  {{ item.aqiLabel }}
                </span>
              </div>
              <div class="feedback-desc">{{ item.description }}</div>
              <div v-if="item.images.length > 0" class="feedback-images">
                <van-image v-for="(img, idx) in item.images" :key="idx" :src="img" width="80" height="80" fit="cover"
                  radius="4" />
              </div>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { showToast } from 'vant'
  import { useUserStore } from '@/stores/user'
  import { listAqiFeedbackByTelId } from '@/api/feedback'
  import { listGridProvinceAll, listGridCityByProvinceId } from '@/api/area'
  import type { GridProvince, GridCity } from '@/api/area'

  const router = useRouter()
  const userStore = useUserStore()

  const loading = ref(false)
  const finished = ref(false)
  const refreshing = ref(false)
  const feedbackList = ref < any[] > ([])
  const provinceMap = ref < Record < number, string>> ({})
  const cityMap = ref < Record < number, string>> ({})

  const aqiLabelMap: Record<number, string> = {
    1: '优',
    2: '良',
    3: '轻度污染',
    4: '中度污染',
    5: '重度污染',
    6: '严重污染'
  }

  const statusMap: Record<number, string> = {
    0: '待处理',
    1: '处理中',
    2: '已处理'
  }

  const getStatusText = (status: number) => {
    return statusMap[status] || '未知'
  }

  const loadAreaData = async () => {
    try {
      const provinces: any = await listGridProvinceAll()
      provinces?.forEach((item: GridProvince) => {
        provinceMap.value[item.provinceId] = item.provinceName
      })
      for (const provinceId in provinceMap.value) {
        const cities: any = await listGridCityByProvinceId(Number(provinceId))
        cities?.forEach((item: GridCity) => {
          cityMap.value[item.cityId] = item.cityName
        })
      }
    } catch (error) {
      console.error('加载地区数据失败', error)
    }
  }

  const loadFeedbackList = async () => {
    try {
      const telId = userStore.userInfo?.phone
      if (!telId) {
        finished.value = true
        return
      }
      const res: any = await listAqiFeedbackByTelId(telId)
      feedbackList.value = (res || []).map((item: any) => ({
        time: `${item.afDate} ${item.afTime}`,
        status: item.state,
        province: provinceMap.value[item.provinceId] || '',
        city: cityMap.value[item.cityId] || '',
        address: item.address,
        aqiLevel: item.estimatedGrade,
        aqiLabel: aqiLabelMap[item.estimatedGrade] || '',
        description: item.information,
        images: []
      }))
    } catch (error) {
      showToast('加载历史记录失败')
    }
  }

  const onLoad = async () => {
    await loadFeedbackList()
    loading.value = false
    finished.value = true
  }

  const onRefresh = async () => {
    await loadFeedbackList()
    refreshing.value = false
  }

  onMounted(() => {
    loadAreaData().then(() => {
      loadFeedbackList()
    })
  })

  const goBack = () => {
    router.back()
  }
</script>

<style scoped>
  .history-page {
    min-height: 100vh;
    background: linear-gradient(180deg, #c8e6c9 0%, #e8f5e9 30%, #b2dfdb 100%);
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

  .feedback-list {
    padding: 15px;
  }

  .feedback-item {
    background: rgba(255, 255, 255, 0.9);
    border-radius: 10px;
    padding: 15px;
    margin-bottom: 15px;
  }

  .feedback-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
  }

  .feedback-time {
    font-size: 13px;
    color: #999;
  }

  .feedback-status {
    padding: 2px 10px;
    border-radius: 10px;
    font-size: 12px;
  }

  .feedback-status.pending {
    background: #fff3e0;
    color: #ff9800;
  }

  .feedback-status.processing {
    background: #e3f2fd;
    color: #2196f3;
  }

  .feedback-status.processed {
    background: #e8f5e9;
    color: #4caf50;
  }

  .feedback-location {
    font-size: 14px;
    color: #666;
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    gap: 4px;
  }

  .feedback-aqi {
    margin-bottom: 8px;
  }

  .aqi-tag {
    padding: 2px 10px;
    border-radius: 4px;
    font-size: 12px;
    color: white;
  }

  .level-1 {
    background: #4caf50;
  }

  .level-2 {
    background: #8bc34a;
  }

  .level-3 {
    background: #ffeb3b;
    color: #333;
  }

  .level-4 {
    background: #ff9800;
  }

  .level-5 {
    background: #f44336;
  }

  .level-6 {
    background: #9c27b0;
  }

  .feedback-desc {
    font-size: 14px;
    color: #333;
    line-height: 1.5;
    margin-bottom: 10px;
  }

  .feedback-images {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
  }
</style>