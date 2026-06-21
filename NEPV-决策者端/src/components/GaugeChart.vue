<template>
  <div class="gauge-container">
    <div class="gauge-title">{{ title }}</div>
    <div ref="chartRef" class="gauge-chart"></div>
  </div>
</template>

<script setup lang="ts">
  import { ref, onMounted, onUnmounted, watch } from 'vue'
  import * as echarts from 'echarts'

  interface Props {
    title: string
    value: number
    color: string
  }

  const props = defineProps < Props > ()

  const chartRef = ref < HTMLElement > ()
  let chartInstance: echarts.ECharts | null = null

  const initChart = () => {
    if (!chartRef.value) return

    chartInstance = echarts.init(chartRef.value)

    const option: echarts.EChartsOption = {
      series: [
        {
          type: 'gauge',
          startAngle: 225,
          endAngle: -45,
          min: 0,
          max: 100,
          splitNumber: 10,
          progress: {
            show: true,
            width: 5,
            itemStyle: {
              color: props.color
            }
          },
          axisLine: {
            lineStyle: {
              width: 5,
              color: [[1, 'rgba(255,255,255,0.1)']]
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            distance: -10,
            color: '#7fb3d3',
            fontSize: 12
          },
          anchor: {
            show: true,
            showAbove: true,
            size: 20,
            itemStyle: {
              color: props.color,
              borderWidth: 5,
              borderColor: 'rgba(255,255,255,0.3)'
            }
          },
          pointer: {
            show: true,
            length: '60%',
            width: 4,
            itemStyle: {
              color: props.color
            }
          },
          title: {
            show: false
          },
          detail: {
            valueAnimation: true,
            fontSize: 18,
            fontWeight: 'bold',
            color: props.color,
            offsetCenter: [0, '75%'],
            formatter: '{value}'
          },
          data: [
            {
              value: props.value
            }
          ]
        }
      ]
    }

    chartInstance.setOption(option)
  }

  const updateChart = () => {
    if (!chartInstance) return
    chartInstance.setOption({
      series: [
        {
          progress: {
            itemStyle: {
              color: props.color
            }
          },
          anchor: {
            itemStyle: {
              color: props.color
            }
          },
          pointer: {
            itemStyle: {
              color: props.color
            }
          },
          detail: {
            color: props.color
          },
          data: [{ value: props.value }]
        }
      ]
    })
  }

  const handleResize = () => {
    chartInstance?.resize()
  }

  onMounted(() => {
    initChart()
    window.addEventListener('resize', handleResize)
  })

  onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
    chartInstance?.dispose()
  })

  watch(() => props.value, updateChart)
  watch(() => props.color, updateChart)
</script>

<style scoped>
  .gauge-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    min-width: 140px;
  }

  .gauge-title {
    font-size: 12px;
    color: #7fb3d3;
    margin-bottom: 8px;
    text-align: center;
    white-space: nowrap;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .gauge-chart {
    width: 100%;
    height: 180px;
  }
</style>