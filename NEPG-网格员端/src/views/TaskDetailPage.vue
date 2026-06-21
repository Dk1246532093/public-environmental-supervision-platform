<template>
  <div class="task-detail-page">
    <!-- 蓝色科技主题头部 -->
    <div class="header-bg-blue" style="height: 160px;">
      <div class="tech-illustration" style="transform: translate(-50%, -60%) scale(0.75);">
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
      <span class="page-title-text">反馈任务详细信息</span>
      <span class="menu-btn">☰</span>
    </div>

    <div class="detail-content">
      <!-- 基本信息 -->
      <div class="detail-section">
        <div class="detail-row">
          <span class="detail-label">反馈用户</span>
          <span class="detail-value">{{ feedbackDetail.telId || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">网格</span>
          <span class="detail-value">{{ provinceName }} {{ cityName }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">详细地址</span>
          <span class="detail-value">{{ feedbackDetail.address || '-' }}</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">预估等级</span>
          <span class="detail-value">{{ levelTextMap[feedbackDetail.estimatedGrade] || '-' }}级</span>
        </div>
        <div class="detail-row">
          <span class="detail-label">信息描述</span>
          <span class="detail-value">{{ feedbackDetail.information || '-' }}</span>
        </div>
      </div>

      <!-- 检测数据输入 -->
      <div class="detail-section">
        <div class="input-measure">
          <label>SO₂浓度</label>
          <input type="text" v-model="measureData.so2" placeholder="请输入SO₂浓度">
          <span class="unit">ug/m³</span>
          <span :class="['level-tag', `level-${so2Level}`]">{{ levelTextMap[so2Level] }}</span>
        </div>
        <div class="input-measure">
          <label>CO浓度</label>
          <input type="text" v-model="measureData.co" placeholder="请输入CO浓度">
          <span class="unit">ug/m³</span>
          <span :class="['level-tag', `level-${coLevel}`]">{{ levelTextMap[coLevel] }}</span>
        </div>
        <div class="input-measure">
          <label>PM2.5浓度</label>
          <input type="text" v-model="measureData.pm25" placeholder="请输入PM2.5浓度">
          <span class="unit">ug/m³</span>
          <span :class="['level-tag', `level-${pm25Level}`]">{{ levelTextMap[pm25Level] }}</span>
        </div>
      </div>

      <!-- 结果展示 -->
      <div class="result-banner" :style="{ background: aqiColor }">
        空气质量等级: {{ levelTextMap[finalLevel] }}级 {{ aqiName }}
      </div>

      <!-- 提交按钮 -->
      <button class="btn-submit" @click="handleSubmit">提交检测结果</button>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { reactive, ref, computed, onMounted } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { getAqiFeedbackById, updateAqiFeedbackState } from '@/api/feedback'
  import { saveStatistics } from '@/api/statistics'
  import { listAqiAll } from '@/api/aqi'
  import { listGridProvinceAll, listGridCityByProvinceId } from '@/api/area'
  import type { GridProvince, GridCity } from '@/api/area'

  const router = useRouter()
  const route = useRoute()

  const feedbackDetail = ref < any > ({})
  const provinceName = ref('')
  const cityName = ref('')
  const aqiList = ref < any[] > ([])

  // 检测数据
  const measureData = reactive({
    so2: '',
    co: '',
    pm25: ''
  })

  const levelTextMap: Record<number, string> = {
    1: '一',
    2: '二',
    3: '三',
    4: '四',
    5: '五',
    6: '六'
  }

  const getLevel = (value: number, type: 'so2' | 'co' | 'spm') => {
    for (const aqi of aqiList.value) {
      let min = 0, max = 0
      if (type === 'so2') {
        min = aqi.so2Min
        max = aqi.so2Max
      } else if (type === 'co') {
        min = aqi.coMin
        max = aqi.coMax
      } else {
        min = aqi.spmMin
        max = aqi.spmMax
      }
      if (value >= min && value <= max) {
        return aqi.aqiId
      }
    }
    return 1
  }

  const so2Level = computed(() => measureData.so2 ? getLevel(Number(measureData.so2), 'so2') : 1)
  const coLevel = computed(() => measureData.co ? getLevel(Number(measureData.co), 'co') : 1)
  const pm25Level = computed(() => measureData.pm25 ? getLevel(Number(measureData.pm25), 'spm') : 1)
  const finalLevel = computed(() => Math.max(so2Level.value, coLevel.value, pm25Level.value))
  const aqiName = computed(() => aqiList.value.find((aqi: any) => aqi.aqiId === finalLevel.value)?.chineseExplain || '')
  const aqiColor = computed(() => {
    const colorMap: Record<number, string> = {
      1: '#4caf50',
      2: '#8bc34a',
      3: '#ff9800',
      4: '#f44336',
      5: '#9c27b0',
      6: '#795548'
    }
    return colorMap[finalLevel.value] || '#f44336'
  })

  const loadAqiList = async () => {
    try {
      const res: any = await listAqiAll()
      aqiList.value = res || []
    } catch (error) {
      console.error('加载AQI等级失败', error)
    }
  }

  const loadAreaData = async () => {
    try {
      const provinces: any = await listGridProvinceAll()
      const provinceMap: Record<number, string> = {}
      provinces?.forEach((item: GridProvince) => {
        provinceMap[item.provinceId] = item.provinceName
      })
      provinceName.value = provinceMap[feedbackDetail.value.provinceId] || ''

      const cities: any = await listGridCityByProvinceId(feedbackDetail.value.provinceId)
      const cityMap: Record<number, string> = {}
      cities?.forEach((item: GridCity) => {
        cityMap[item.cityId] = item.cityName
      })
      cityName.value = cityMap[feedbackDetail.value.cityId] || ''
    } catch (error) {
      console.error('加载地区数据失败', error)
    }
  }

  const loadFeedbackDetail = async () => {
    try {
      const afId = Number(route.params.id)
      const res: any = await getAqiFeedbackById(afId)
      feedbackDetail.value = res || {}
      await loadAreaData()
    } catch (error) {
      alert('加载任务详情失败')
    }
  }

  onMounted(() => {
    loadAqiList()
    loadFeedbackDetail()
  })

  const goBack = () => {
    router.back()
  }

  const handleSubmit = async () => {
    if (!measureData.so2 || !measureData.co || !measureData.pm25) {
      alert('请填写完整的检测数据')
      return
    }
    try {
      const so2Value = Number(measureData.so2)
      const coValue = Number(measureData.co)
      const spmValue = Number(measureData.pm25)
      const so2Level = getLevel(so2Value, 'so2')
      const coLevel = getLevel(coValue, 'co')
      const spmLevel = getLevel(spmValue, 'spm')
      const aqiId = Math.max(so2Level, coLevel, spmLevel)

      const gmInfo = JSON.parse(localStorage.getItem('gmInfo') || '{}')
      const now = new Date()

      await saveStatistics({
        provinceId: feedbackDetail.value.provinceId,
        cityId: feedbackDetail.value.cityId,
        address: feedbackDetail.value.address,
        so2Value,
        so2Level,
        coValue,
        coLevel,
        spmValue,
        spmLevel,
        aqiId,
        confirmDate: now.toISOString().split('T')[0],
        confirmTime: now.toTimeString().split(' ')[0],
        gmId: gmInfo.gmId,
        fdId: feedbackDetail.value.telId,
        information: feedbackDetail.value.information
      })

      await updateAqiFeedbackState({
        afId: feedbackDetail.value.afId,
        state: 2
      })

      alert('检测结果提交成功！')
      router.push('/task-list')
    } catch (error) {
      alert('提交失败')
    }
  }
</script>

<style scoped>
  .task-detail-page {
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
  }

  .menu-btn {
    font-size: 20px;
    color: #666;
    cursor: pointer;
  }

  /* 详情内容 */
  .detail-content {
    padding: 20px;
  }

  .detail-section {
    margin-bottom: 20px;
    background: #fff;
    padding: 15px;
    border-radius: 10px;
  }

  .detail-row {
    display: flex;
    margin-bottom: 12px;
    align-items: center;
  }

  .detail-row:last-child {
    margin-bottom: 0;
  }

  .detail-label {
    width: 80px;
    font-size: 14px;
    color: #666;
    flex-shrink: 0;
  }

  .detail-value {
    flex: 1;
    font-size: 14px;
    color: #333;
  }

  .input-measure {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 12px;
  }

  .input-measure:last-child {
    margin-bottom: 0;
  }

  .input-measure label {
    width: 70px;
    font-size: 14px;
    color: #333;
    flex-shrink: 0;
  }

  .input-measure input {
    flex: 1;
    padding: 8px 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
    outline: none;
  }

  .input-measure input:focus {
    border-color: #2196f3;
  }

  .input-measure .unit {
    font-size: 12px;
    color: #999;
    width: 50px;
    flex-shrink: 0;
  }

  .input-measure .level-tag {
    width: 30px;
    height: 24px;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    color: white;
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

  .result-banner {
    background: #f44336;
    color: white;
    padding: 12px;
    border-radius: 8px;
    text-align: center;
    margin: 20px 0;
    font-size: 14px;
  }

  .btn-submit {
    width: 100%;
    padding: 15px;
    background: #2196f3;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 16px;
    cursor: pointer;
    transition: opacity 0.3s;
  }

  .btn-submit:active {
    opacity: 0.8;
  }
</style>