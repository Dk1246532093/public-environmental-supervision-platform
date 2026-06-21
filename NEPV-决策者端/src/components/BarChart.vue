<template>
  <div ref="chartRef" class="bar-chart"></div>
</template>

<script setup lang="ts">
  import { ref, onMounted, onUnmounted, watch } from 'vue'
  import * as echarts from 'echarts'

  interface BarData {
    name: string
    value: number
  }

  interface Props {
    data: BarData[]
    colorStart: string
    colorEnd: string
  }

  const props = defineProps < Props > ()

  const chartRef = ref < HTMLElement > ()
  let chartInstance: echarts.ECharts | null = null

  const initChart = () => {
    if (!chartRef.value) return

    chartInstance = echarts.init(chartRef.value)

    const names = props.data.map(item => item.name)
    const values = props.data.map(item => item.value)

    const option: echarts.EChartsOption = {
      grid: {
        top: 10,
        left: 35,
        right: 5,
        bottom: 20,
        containLabel: false
      },
      xAxis: {
        type: 'category',
        data: names,
        axisLine: {
          lineStyle: { color: '#2a9d8f' }
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          color: '#7fb3d3',
          fontSize: 9,
          interval: 0
        }
      },
      yAxis: {
        type: 'value',
        show: true,
        axisLine: {
          show: true,
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
          type: 'bar',
          data: values,
          barWidth: 8,
          itemStyle: {
            borderRadius: [2, 2, 0, 0],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: props.colorStart },
              { offset: 1, color: props.colorEnd }
            ])
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowColor: props.colorStart
            }
          }
        }
      ]
    }

    chartInstance.setOption(option)
  }

  const handleResize = () => {
    chartInstance?.resize()
  }

  watch(() => props.data, () => {
    initChart()
  }, { deep: true })

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
  .bar-chart {
    width: 100%;
    height: 130px;
  }
</style>