<template>
  <div class="dashboard-container">
    <!-- 顶部标题栏 -->
    <div class="dashboard-header">
      <div class="header-title">东软空气质量环保公众监督平台</div>
      <div class="header-time">{{ currentTime }}</div>
    </div>

    <!-- 主内容区 -->
    <div class="dashboard-content">
      <!-- 左侧面板 -->
      <div class="left-panel">
        <!-- 仪表盘 -->
        <div class="panel-box">
          <div class="gauge-row">
            <GaugeChart title="省会城市网格覆盖率(%)" :value="provinceCoverage" color="#2a9d8f" />
            <GaugeChart title="大城市网格覆盖率(%)" :value="cityCoverage" color="#e63946" />
          </div>
        </div>

        <!-- 饼图 -->
        <div class="panel-box">
          <div class="panel-title">空气质量指数级别分布</div>
          <PieChart :data="pieData" />
        </div>

        <!-- 折线图 -->
        <div class="panel-box">
          <div class="panel-title">12个月内空气质量超标趋势</div>
          <LineChart :data="trendData" />
        </div>
      </div>

      <!-- 中间地图区域 -->
      <div class="center-panel">
        <div class="china-map-wrapper">
          <div class="map-glow"></div>
          <ChinaMap :data="mapData" />

        </div>
        <!-- 底部统计卡片 -->
        <div class="stats-row">
          <div class="stat-card">
            <div class="stat-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="#00d4ff" stroke-width="2">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
              </svg>
            </div>
            <div class="stat-value">{{ aqiCount }}</div>
            <div class="stat-label">空气质量检测总数量</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="#52c41a" stroke-width="2">
                <circle cx="12" cy="12" r="10"></circle>
                <path d="M12 6v6l4 2"></path>
              </svg>
            </div>
            <div class="stat-value" style="color: #52c41a;">{{ aqiGoodCount }}</div>
            <div class="stat-label">空气质量良好数量</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="#f5222d" stroke-width="2">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="12"></line>
                <line x1="12" y1="16" x2="12.01" y2="16"></line>
              </svg>
            </div>
            <div class="stat-value" style="color: #f5222d;">{{ aqiCount - aqiGoodCount }}</div>
            <div class="stat-label">空气质量污染数量</div>
          </div>
        </div>
      </div>

      <!-- 右侧面板 -->
      <div class="right-panel">
        <!-- PM2.5柱状图 -->
        <div class="panel-box">
          <div class="panel-title">悬浮颗粒物(PM2.5)浓度超标累计</div>
          <BarChart :data="pm25Data" color-start="#00d4ff" color-end="#0077b6" />
        </div>

        <!-- SO2柱状图 -->
        <div class="panel-box">
          <div class="panel-title">二氧化硫(SO2)浓度超标累计</div>
          <BarChart :data="so2Data" color-start="#52c41a" color-end="#389e0d" />
        </div>

        <!-- CO柱状图 -->
        <div class="panel-box">
          <div class="panel-title">一氧化碳(CO)浓度超标累计</div>
          <BarChart :data="coData" color-start="#ff7875" color-end="#f5222d" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, onUnmounted } from 'vue'
  import GaugeChart from '@/components/GaugeChart.vue'
  import PieChart from '@/components/PieChart.vue'
  import LineChart from '@/components/LineChart.vue'
  import ChinaMap from '@/components/ChinaMap.vue'
  import BarChart from '@/components/BarChart.vue'
  import {
    listProvinceItemTotalStatis,
    listAqiDistributeTotalStatis,
    listAqiTrendTotalStatis,
    getAqiCount,
    getAqiGoodCount,
    getProvinceCoverage,
    getCityCoverage
  } from '@/api/statistics'

  const currentTime = ref('')
  let timer: number | null = null

  // PM2.5数据
  const pm25Data = ref([
    { name: '京', value: 25 }, { name: '津', value: 35 }, { name: '冀', value: 30 },
    { name: '晋', value: 85 }, { name: '蒙', value: 40 }, { name: '辽', value: 50 },
    { name: '吉', value: 95 }, { name: '黑', value: 60 }, { name: '沪', value: 75 },
    { name: '苏', value: 65 }, { name: '浙', value: 45 }, { name: '皖', value: 55 },
    { name: '闽', value: 40 }, { name: '赣', value: 80 }, { name: '鲁', value: 70 }
  ])

  // SO2数据
  const so2Data = ref([
    { name: '京', value: 35 }, { name: '津', value: 50 }, { name: '冀', value: 30 },
    { name: '晋', value: 90 }, { name: '蒙', value: 45 }, { name: '辽', value: 60 },
    { name: '吉', value: 100 }, { name: '黑', value: 65 }, { name: '沪', value: 80 },
    { name: '苏', value: 70 }, { name: '浙', value: 50 }, { name: '皖', value: 60 },
    { name: '闽', value: 45 }, { name: '赣', value: 85 }, { name: '鲁', value: 75 }
  ])

  // CO数据
  const coData = ref([
    { name: '京', value: 40 }, { name: '津', value: 55 }, { name: '冀', value: 35 },
    { name: '晋', value: 95 }, { name: '蒙', value: 50 }, { name: '辽', value: 65 },
    { name: '吉', value: 105 }, { name: '黑', value: 70 }, { name: '沪', value: 85 },
    { name: '苏', value: 75 }, { name: '浙', value: 55 }, { name: '皖', value: 65 },
    { name: '闽', value: 50 }, { name: '赣', value: 90 }, { name: '鲁', value: 80 }
  ])

  // 统计数据
  const aqiCount = ref(0)
  const aqiGoodCount = ref(0)
  const provinceCoverage = ref(47)
  const cityCoverage = ref(16)
  const pieData = ref < any[] > ([])
  const trendData = ref < { months: string[]; values: number[] } > ({
    months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    values: [120, 132, 101, 134, 90, 230, 210, 180, 150, 120, 100, 80]
  })
  const mapData = ref < any[] > ([])

  // 更新时间
  const updateTime = () => {
    const now = new Date()
    currentTime.value = now.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    }).replace(/\//g, '-')
  }

  const colorMap: Record<string, string> = {
    '优': '#2a9d8f',
    '良': '#457b9d',
    '轻度污染': '#e9c46a',
    '中度污染': '#f4a261',
    '重度污染': '#e63946',
    '严重污染': '#9d4edd'
  }

  const loadStatistics = async () => {
    try {
      const [provinceRes, distributeRes, trendRes]: any = await Promise.all([
        listProvinceItemTotalStatis(),
        listAqiDistributeTotalStatis(),
        listAqiTrendTotalStatis()
      ])
      const list = provinceRes || []
      pm25Data.value = list.map((item: any) => ({ name: item.provinceAbbr, value: item.spmTotal }))
      so2Data.value = list.map((item: any) => ({ name: item.provinceAbbr, value: item.so2Total }))
      coData.value = list.map((item: any) => ({ name: item.provinceAbbr, value: item.coTotal }))

      pieData.value = (distributeRes || []).map((item: any) => ({
        value: item.total,
        name: item.aqiExplain,
        itemStyle: { color: colorMap[item.aqiExplain] || '#999' }
      }))

      trendData.value = {
        months: (trendRes || []).map((item: any) => item.month),
        values: (trendRes || []).map((item: any) => item.total)
      }

      mapData.value = list.map((item: any) => ({
        name: item.provinceName,
        value: item.aqiTotal
      }))
    } catch (error) {
      console.error('加载统计失败', error)
    }
  }

  const loadTopData = async () => {
    try {
      const [countRes, goodRes, provinceRes, cityRes]: any = await Promise.all([
        getAqiCount(),
        getAqiGoodCount(),
        getProvinceCoverage(),
        getCityCoverage()
      ])
      aqiCount.value = Number(countRes) || 0
      aqiGoodCount.value = Number(goodRes) || 0
      provinceCoverage.value = Number(provinceRes) || 0
      cityCoverage.value = Number(cityRes) || 0
    } catch (error) {
      console.error('加载顶部统计失败', error)
    }
  }

  onMounted(() => {
    updateTime()
    timer = window.setInterval(updateTime, 1000)
    loadStatistics()
    loadTopData()
  })

  onUnmounted(() => {
    if (timer) {
      clearInterval(timer)
    }
  })
</script>

<style scoped>
  .dashboard-container {
    width: 100%;
    height: 100vh;
    background: linear-gradient(180deg, #0d1b2a 0%, #1b263b 100%);
    overflow: hidden;
    display: flex;
    flex-direction: column;
  }

  /* 顶部标题栏 */
  .dashboard-header {
    background: linear-gradient(90deg, #0d1b2a 0%, #1b3a4b 50%, #0d1b2a 100%);
    padding: 15px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #2a9d8f;
    position: relative;
    flex-shrink: 0;
  }

  .dashboard-header::before,
  .dashboard-header::after {
    content: '';
    position: absolute;
    bottom: -2px;
    width: 100px;
    height: 2px;
    background: #00d4ff;
  }

  .dashboard-header::before {
    left: 20%;
  }

  .dashboard-header::after {
    right: 20%;
  }

  .header-title {
    font-size: 22px;
    color: #00d4ff;
    font-weight: bold;
    text-shadow: 0 0 10px rgba(0, 212, 255, 0.5);
    letter-spacing: 4px;
  }

  .header-time {
    color: #7fb3d3;
    font-size: 14px;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .header-time::before {
    content: '◷';
    color: #00d4ff;
  }

  /* 主内容区 */
  .dashboard-content {
    padding: 15px;
    display: grid;
    grid-template-columns: 400px 1fr 320px;
    gap: 15px;
    flex: 1;
    overflow: hidden;
  }

  /* 面板样式 */
  .panel-box {
    background: transparent;
    padding: 12px;
    position: relative;
  }

  .panel-title {
    color: #7fb3d3;
    font-size: 13px;
    margin-bottom: 12px;
    text-align: center;
  }

  /* 左侧面板 */
  .left-panel {
    display: flex;
    flex-direction: column;
    gap: 12px;
    overflow-y: auto;
  }

  .gauge-row {
    display: flex;
    gap: 10px;
    justify-content: space-between;
  }

  .gauge-row>* {
    flex: 1;
    min-width: 0;
  }

  /* 中间地图区域 */
  .center-panel {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: relative;
    padding: 10px;
  }

  .china-map-wrapper {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
  }

  .map-glow {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 90%;
    height: 90%;
    background: radial-gradient(ellipse at center, rgba(0, 212, 255, 0.1) 0%, transparent 70%);
    pointer-events: none;
  }

  /* 地图图例 */
  .map-legend {
    position: absolute;
    bottom: 80px;
    left: 20px;
    display: flex;
    flex-direction: column;
    gap: 6px;
  }

  .map-legend-item {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .map-legend-color {
    width: 20px;
    height: 20px;
    border-radius: 4px;
  }

  .map-legend-text {
    font-size: 12px;
    color: #7fb3d3;
  }

  /* 底部统计卡片 */
  .stats-row {
    display: flex;
    justify-content: center;
    gap: 40px;
    margin-top: 20px;
    padding: 0 20px;
  }

  .stat-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
  }

  .stat-icon {
    width: 40px;
    height: 40px;
    background: rgba(42, 157, 143, 0.2);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .stat-icon svg {
    width: 24px;
    height: 24px;
  }

  .stat-value {
    font-size: 32px;
    font-weight: bold;
    color: #00d4ff;
    line-height: 1;
  }

  .stat-label {
    font-size: 12px;
    color: #7fb3d3;
  }

  /* 右侧面板 */
  .right-panel {
    display: flex;
    flex-direction: column;
    gap: 12px;
    overflow-y: auto;
  }

  /* 响应式 */
  @media (max-width: 1400px) {
    .dashboard-content {
      grid-template-columns: 300px 1fr 300px;
    }
  }

  @media (max-width: 1200px) {
    .dashboard-content {
      grid-template-columns: 1fr;
      overflow-y: auto;
    }

    .center-panel {
      order: -1;
      min-height: 400px;
    }
  }
</style>