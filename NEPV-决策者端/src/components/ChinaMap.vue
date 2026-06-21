<template>
    <div ref="chartRef" class="china-map"></div>
</template>

<script setup lang="ts">
    import { ref, onMounted, onUnmounted, watch } from 'vue'
    import * as echarts from 'echarts'
    import chinaGeoJSON from '../assets/china_map.json'

    interface Props {
        data?: any[]
    }

    const props = defineProps < Props > ()

    const chartRef = ref < HTMLElement > ()
    let chartInstance: echarts.ECharts | null = null

    // 各省AQI数据
    const defaultProvinceData = [
        { name: '北京市', value: 85 },
        { name: '天津市', value: 72 },
        { name: '河北省', value: 95 },
        { name: '山西省', value: 88 },
        { name: '内蒙古自治区', value: 65 },
        { name: '辽宁省', value: 78 },
        { name: '吉林省', value: 55 },
        { name: '黑龙江省', value: 62 },
        { name: '上海市', value: 75 },
        { name: '江苏省', value: 82 },
        { name: '浙江省', value: 68 },
        { name: '安徽省', value: 71 },
        { name: '福建省', value: 58 },
        { name: '江西省', value: 64 },
        { name: '山东省', value: 91 },
        { name: '河南省', value: 87 },
        { name: '湖北省', value: 76 },
        { name: '湖南省', value: 69 },
        { name: '广东省', value: 52 },
        { name: '广西壮族自治区', value: 61 },
        { name: '海南省', value: 45 },
        { name: '重庆市', value: 74 },
        { name: '四川省', value: 79 },
        { name: '贵州省', value: 67 },
        { name: '云南省', value: 48 },
        { name: '西藏自治区', value: 35 },
        { name: '陕西省', value: 83 },
        { name: '甘肃省', value: 73 },
        { name: '青海省', value: 42 },
        { name: '宁夏回族自治区', value: 77 },
        { name: '新疆维吾尔自治区', value: 56 },
        { name: '台湾省', value: 51 },
        { name: '香港特别行政区', value: 46 },
        { name: '澳门特别行政区', value: 44 }
    ]

    const initChart = () => {
        if (!chartRef.value) return

        // 注册中国地图
        echarts.registerMap('china', chinaGeoJSON as any)

        chartInstance = echarts.init(chartRef.value)

        const option: echarts.EChartsOption = {
            backgroundColor: 'transparent',
            tooltip: {
                trigger: 'item',
                backgroundColor: 'rgba(13, 27, 42, 0.9)',
                borderColor: '#2a9d8f',
                textStyle: {
                    color: '#fff'
                },
                formatter: (params: any) => {
                    const levelMap: Record<number, string> = {
                        1: '优',
                        2: '良',
                        3: '轻度污染',
                        4: '中度污染',
                        5: '重度污染',
                        6: '严重污染'
                    }
                    const value = Number(params.value) || 0
                    const levelName = levelMap[value] || '未知'
                    return `${params.name}<br/>AQI等级：${levelName}`
                }
            },
            visualMap: {
                type: 'piecewise',
                left: 'left',
                top: 'bottom',
                itemWidth: 20,
                itemHeight: 14,
                textStyle: {
                    color: '#7fb3d3'
                },
                pieces: [
                    { min: 1, max: 1, label: '优', color: '#02E300' },
                    { min: 2, max: 2, label: '良', color: '#FFFF00' },
                    { min: 3, max: 3, label: '轻度污染', color: '#FF7E00' },
                    { min: 4, max: 4, label: '中度污染', color: '#FE0000' },
                    { min: 5, max: 5, label: '重度污染', color: '#98004B' },
                    { min: 6, max: 6, label: '严重污染', color: '#7E0123' }
                ]
            },

            series: [
                {
                    name: 'AQI指数',
                    type: 'map',
                    map: 'china',
                    roam: true,
                    zoom: 1.2,
                    label: {
                        show: true,
                        color: '#fff',
                        fontSize: 9
                    },
                    itemStyle: {
                        areaColor: '#1b3a4b',
                        borderColor: '#2a9d8f',
                        borderWidth: 1
                    },
                    emphasis: {
                        label: {
                            show: true,
                            color: '#00d4ff'
                        },
                        itemStyle: {
                            areaColor: '#2a9d8f',
                            shadowBlur: 10,
                            shadowColor: 'rgba(0, 212, 255, 0.5)'
                        }
                    },
                    data: props.data && props.data.length > 0 ? props.data : defaultProvinceData
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
            series: [{
                data: props.data && props.data.length > 0 ? props.data : defaultProvinceData
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
    .china-map {
        width: 100%;
        height: 100%;
        min-height: 450px;
    }
</style>