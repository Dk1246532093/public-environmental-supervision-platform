<template>
    <!-- 导航，指示当前页面位置， -->
    <el-breadcrumb>
        <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>省分组分项检查统计</el-breadcrumb-item>
    </el-breadcrumb>
    <el-alert title="省分组分项检查统计" type="success"
    effect="dark"
    :closable="false"
    style="width: 80%; margin: 20px 0"
    ></el-alert>
    <!-- 统计表格区域 -->
    <div class = 'table-box'>
        <el-table
        :data="provinceItemTotalArr"
        :cell-style="{ padding: '4px' }"
        empty-text="无数据"
        style="width: 100%">
        <el-table-column prop="provinceId" label="省编号" width="90"></el-table-column>
        <el-table-column prop="provinceAbbr" label="省简称"></el-table-column>
        <el-table-column prop="provinceName" label="省名称"></el-table-column>
        <el-table-column prop="so2Total" label="SO2超标累计"></el-table-column>
        <el-table-column prop="coTotal" label="CO超标累计"></el-table-column>
        <el-table-column prop="PM25Total" label="PM2.5超标累计"></el-table-column>
        <el-table-column prop="aqiTotal" label="AQI超标累计" />
        </el-table>
    </div>
</template>

<script setup>
import { ref, inject } from 'vue'
const axios = inject('axios')
// 定义数据
const provinceItemTotalArr = ref([])
// 初始化方法
const init = () => {
    axios
    .post('statistics/listProvinceItemTotalStatis')
    .then(res => {
        provinceItemTotalArr.value = res.data
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
