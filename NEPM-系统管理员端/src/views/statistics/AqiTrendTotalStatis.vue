<template>
    <!-- 导航，指示当前页面位置， -->
    <el-breadcrumb>
        <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>AQI空气质量指数超标趋势统计</el-breadcrumb-item>
    </el-breadcrumb>
    <el-alert title="AQI空气质量指数超标趋势统计" type="success"
    effect="dark"
    :closable="false"
    style="width: 80%; margin: 20px 0"
    ></el-alert>
    <!-- 统计表格区域 -->
    <div class = 'table-box'>
        <el-table
        :data="aqiTrendTotalArr"
        :cell-style="{ padding: '4px' }"
        empty-text="无数据"
        style="width: 100%">
        <!-- 序号： 使用scope.$index自动生成行号 -->
        <el-table-column label="序号">
            <template #default="scope">
                {{ scope.$index + 1 }}
            </template>
        </el-table-column>
        <el-table-column prop="month" label="月份"></el-table-column>
        <el-table-column prop="total" label="AQI空气指数指数超标数量"></el-table-column>
        </el-table>
    </div>
</template>

<script setup>
import { ref, inject } from "vue"

const axios = inject("axios")
const aqiTrendTotalArr = ref([])
const init = () => {
    axios
    .post('statistics/listAqiTrendTotalStatis')
    .then(res => {
        aqiTrendTotalArr.value = res.data
    })
    .catch(err => {
        console.log(err)
    })
}
init()
</script>

<style scoped>
.table-box {
  width: 80%;
  height: 450px;
  overflow: auto;
}
</style>