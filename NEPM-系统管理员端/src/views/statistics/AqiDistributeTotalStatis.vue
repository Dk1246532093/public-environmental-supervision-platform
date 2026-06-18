<template>
  <el-breadcrumb>
    <el-breadcrumb-item>统计数据管理</el-breadcrumb-item>
    <el-breadcrumb-item>AQI空气质量指数级别分布统计</el-breadcrumb-item>
  </el-breadcrumb>
  <el-alert
    title="AQI空气质量指数级别分布统计"
    type="success"
    effect="dark"
    :closable="false"
    style="width: 80%; margin: 20px 0"
  />
  <div class="table-box">
    <el-table
      :data="aqiDistributeTotalArr"
      :cell-style="{ padding: '4px' }"
      empty-text="无数据"
      style="width: 100%"
    >
      <el-table-column prop="aqiId" label="AQI指数等级" />
      <el-table-column prop="aqiExplain" label="AQI指数等级描述" />
      <el-table-column prop="total" label="AQI指数等级分布统计" />
    </el-table>
  </div>
</template>

<script setup>
import { ref, inject } from "vue";

const axios = inject("axios");

const aqiDistributeTotalArr = ref([]);

const init = () => {
  axios
    .post("statistics/listAqiDistributeTotalStatis")
    .then((response) => {
      aqiDistributeTotalArr.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
};
init();
</script>

<style scoped>
.table-box {
  width: 80%;
  height: 450px;
  overflow: auto;
}
</style>