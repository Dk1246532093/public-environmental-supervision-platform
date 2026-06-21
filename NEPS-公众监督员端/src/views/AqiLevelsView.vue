<template>
  <div class="aqi-levels-page">
    <!-- 头部 -->
    <div class="page-header">
      <van-icon name="arrow-left" class="back-btn" @click="goBack" />
      <span class="page-title">AQI等级说明</span>
      <span style="width: 24px"></span>
    </div>

    <!-- AQI等级列表 -->
    <div class="aqi-levels">
      <div v-for="level in aqiLevels" :key="level.value" class="aqi-item">
        <div :class="['aqi-level-num', `level-${level.value}`]">
          {{ level.value }}
        </div>
        <div class="aqi-info">
          <div class="aqi-name">{{ level.label }}</div>
          <div class="aqi-range">AQI指数: {{ level.range }}</div>
          <div class="aqi-desc">{{ level.description }}</div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { showToast } from 'vant'
  import { listAqiAll } from '@/api/aqi'

  const router = useRouter()

  const aqiLevels = ref < any[] > ([
    { value: 1, label: '优', range: '0-50', description: '空气质量令人满意，基本无空气污染，各类人群可正常活动。' },
    { value: 2, label: '良', range: '51-100', description: '空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响。' },
    { value: 3, label: '轻度污染', range: '101-150', description: '易感人群症状有轻度加剧，健康人群出现刺激症状。建议儿童、老年人及心脏病、呼吸系统疾病患者减少长时间、高强度的户外锻炼。' },
    { value: 4, label: '中度污染', range: '151-200', description: '进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响。建议疾病患者避免长时间、高强度的户外锻炼，一般人群适量减少户外运动。' },
    { value: 5, label: '重度污染', range: '201-300', description: '心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状。建议儿童、老年人和病人应停留在室内，停止户外运动，一般人群减少户外运动。' },
    { value: 6, label: '严重污染', range: '>300', description: '健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病。建议儿童、老年人和病人应停留在室内，避免体力消耗，一般人群应避免户外活动。' }
  ])

  const loadAqiLevels = async () => {
    try {
      const res: any = await listAqiAll()
      if (res && res.length > 0) {
        aqiLevels.value = res.map((item: any) => ({
          value: item.aqiId,
          label: item.aqiExplain,
          range: `${item.spmMin}-${item.spmMax}`,
          description: item.takeSteps
        }))
      }
    } catch (error) {
      showToast('加载AQI等级失败')
    }
  }

  onMounted(() => {
    loadAqiLevels()
  })

  const goBack = () => {
    router.back()
  }
</script>

<style scoped>
  .aqi-levels-page {
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

  .aqi-levels {
    background: rgba(255, 255, 255, 0.9);
    margin: 15px;
    border-radius: 10px;
    padding: 15px;
  }

  .aqi-item {
    display: flex;
    align-items: flex-start;
    margin-bottom: 15px;
    padding-bottom: 15px;
    border-bottom: 1px solid #eee;
  }

  .aqi-item:last-child {
    margin-bottom: 0;
    padding-bottom: 0;
    border-bottom: none;
  }

  .aqi-level-num {
    width: 28px;
    height: 28px;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: white;
    margin-right: 12px;
    flex-shrink: 0;
    font-weight: bold;
  }

  .level-1 {
    background: #4caf50;
  }

  .level-2 {
    background: #8bc34a;
  }

  .level-3 {
    background: #ffeb3b;
    color: #333 !important;
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

  .aqi-info {
    flex: 1;
  }

  .aqi-name {
    font-size: 16px;
    font-weight: bold;
    color: #333;
    margin-bottom: 4px;
  }

  .aqi-range {
    font-size: 13px;
    color: #666;
    margin-bottom: 6px;
  }

  .aqi-desc {
    font-size: 12px;
    color: #999;
    line-height: 1.5;
  }
</style>