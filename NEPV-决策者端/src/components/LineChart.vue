<template>
  <div ref="chartRef" class="line-chart"></div>
</template>

<script setup lang="ts">
  import { ref, onMounted, onUnmounted, watch } from 'vue'
  import * as echarts from 'echarts'

  interface Props {
    data?: {
      months: string[]
      values: number[]
    }
  }

  const props = defineProps < Props > ()

  const chartRef = ref < HTMLElement > ()
  let chartInstance: echarts.ECharts | null = null

  const initChart = () => {
    if (!chartRef.value) return

    chartInstance = echarts.init(chartRef.value)

    const data = props.data?.values || [120, 132, 101, 134, 90, 230, 210, 180, 150, 120, 100, 80]
    const months = props.data?.months || ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']

    const option: echarts.EChartsOption = {
      grid: {
        top: 10,
        left: 10,
        right: 10,
        bottom: 20,
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: months,
        axisLine: {
          lineStyle: { color: '#2a9d8f' }
        },
        axisLabel: {
          color: '#7fb3d3',
          fontSize: 9
        }
      },
      yAxis: {
        type: 'value',
        axisLine: {
          lineStyle: { color: '#2a9d8f' }
        },
        axisLabel: {
          color: '#7fb3d3',
          fontSize: 9
        },
        splitLine: {
          lineStyle: {
            color: '#1b3a4b',
            type: 'dashed'
          }
        }
      },
      series: [
        {
          name: '超标次数',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 6,
          sampling: 'average',
          itemStyle: {
            color: '#00d4ff'
          },
          lineStyle: {
            width: 2,
            color: '#00d4ff'
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: 'rgba(0, 212, 255, 0.3)' },
              { offset: 1, color: 'rgba(0, 212, 255, 0.05)' }
            ])
          },
          data: data
        }
      ]
    }

    chartInstance.setOption(option)
  }

  const handleResize = () => {
    chartInstance?.resize()
  }

  const updateChart = () => {
    if (!chartInstance) return
    chartInstance.setOption({
      xAxis: {
        data: props.data?.months || ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      },
      series: [{
        data: props.data?.values || [120, 132, 101, 134, 90, 230, 210, 180, 150, 120, 100, 80]
      }]
    })
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
  .line-chart {
    width: 100%;
    height: 120px;
  }
</style>