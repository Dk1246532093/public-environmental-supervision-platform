<template>
  <div class="pie-chart-container">
    <div class="pie-legend">
      <div class="legend-item" v-for="(item, index) in legendData" :key="index">
        <div class="legend-color" :style="{ background: item.color }"></div>
        <span>{{ item.name }}</span>
      </div>
    </div>
    <div ref="chartRef" class="pie-chart"></div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, onUnmounted, watch } from 'vue'
  import * as echarts from 'echarts'

  interface Props {
    data?: any[]
  }

  const props = defineProps < Props > ()

  const chartRef = ref < HTMLElement > ()
  let chartInstance: echarts.ECharts | null = null

  const defaultData = [
    { value: 335, name: '优', itemStyle: { color: '#2a9d8f' } },
    { value: 310, name: '良', itemStyle: { color: '#457b9d' } },
    { value: 234, name: '轻度', itemStyle: { color: '#e9c46a' } },
    { value: 135, name: '中度', itemStyle: { color: '#f4a261' } },
    { value: 148, name: '重度', itemStyle: { color: '#e63946' } },
    { value: 38, name: '严重', itemStyle: { color: '#9d4edd' } }
  ]

  const legendData = [
    { name: '优', color: '#2a9d8f' },
    { name: '良', color: '#457b9d' },
    { name: '轻度', color: '#e9c46a' },
    { name: '中度', color: '#f4a261' },
    { name: '重度', color: '#e63946' },
    { name: '严重', color: '#9d4edd' }
  ]

  const initChart = () => {
    if (!chartRef.value) return

    chartInstance = echarts.init(chartRef.value)

    const option: echarts.EChartsOption = {
      tooltip: {
        trigger: 'item',
        backgroundColor: 'rgba(13, 27, 42, 0.9)',
        borderColor: '#2a9d8f',
        textStyle: {
          color: '#fff'
        }
      },
      series: [
        {
          name: 'AQI级别',
          type: 'pie',
          radius: '70%',
          center: ['50%', '50%'],
          data: props.data && props.data.length > 0 ? props.data : defaultData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          label: {
            show: false
          }
        }
      ]
    }

    chartInstance.setOption(option)
  }

  const updateChart = () => {
    if (!chartInstance) return
    chartInstance.setOption({
      series: [{
        data: props.data && props.data.length > 0 ? props.data : defaultData
      }]
    })
  }

  const handleResize = () => {
    chartInstance?.resize()
  }

  watch(() => props.data, updateChart, { deep: true })

  onMounted(() => {
    initChart()
    window.addEventListener('resize', handleResize)
  })

  onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
    chartInstance?.dispose()
  })
</script>

<style scoped>
  .pie-chart-container {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 20px;
    height: 100%;
    width: 100%;
  }

  .pie-legend {
    display: flex;
    flex-direction: column;
    gap: 8px;
    flex-shrink: 0;
  }

  .legend-item {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 12px;
    color: #7fb3d3;
  }

  .legend-color {
    width: 14px;
    height: 14px;
    border-radius: 3px;
  }

  .pie-chart {
    flex: 1;
    height: 100%;
    min-width: 120px;
  }
</style>