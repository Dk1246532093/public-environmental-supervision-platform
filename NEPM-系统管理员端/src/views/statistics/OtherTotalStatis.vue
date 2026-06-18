<template>
    <!-- 导航，指示当前页面位置， -->
    <el-breadcrumb>
        <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>其他数据统计</el-breadcrumb-item>
    </el-breadcrumb>
    <el-descriptions
        class="margin-top"
        title="其他数据统计"
        :column="1"
        border
        style="margin-top: 20px; width: 50%;">
        <el-descriptions-item>
            <template #label>
                <div class = 'cell-item'>空气质量检测总数量</div>
            </template>
            {{ aqiCount }}
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label>
                <div class = 'cell-item'>空气质量检测良好数量</div>
            </template>
            {{ aqiGoodCount }}
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label>
                <div class = 'cell-item'>省网格覆盖率</div>
            </template>
            {{ provinceCoverage }}%
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label>
                <div class = 'cell-item'>大城市网格覆盖率</div>
            </template>
            {{ cityCoverage }}%
        </el-descriptions-item>
    </el-descriptions>
</template>

<script setup>
import { ref, inject } from 'vue'
const axios = inject('axios')
//数据
const aqiCount = ref(0) // 总检测数
const aqiGoodCount = ref(0) // 良好数量
const provinceCoverage = ref(0) // 省份覆盖率
const cityCoverage = ref(0) // 城市覆盖率
// 方法-》初始化方法
const init = () => {
    // 获取总数量
    axios
    .post('statistics/getAqiCount')
    .then(res => {
        aqiCount.value = res.data
    })
    .catch(err => {
        console.log(err)
    })

    // 获取良好数量
    axios
    .post('statistics/getAqiGoodCount')
    .then(res => {
        aqiGoodCount.value = res.data
    })
    .catch(err => {
        console.log(err)
    })

    // 获取省份覆盖率
    axios
    .post('statistics/getProvinceCoverage')
    .then(res => {
        provinceCoverage.value = res.data
    })
    .catch(err => {
        console.log(err)
    })
    // 获取城市覆盖率
    axios
    .post('statistics/getCityCoverage')
    .then(res => {
        cityCoverage.value = res.data
    })
    .catch(err => {
        console.log(err)
    })
}
init()
</script>

<style lang="scss" scoped>

</style>
