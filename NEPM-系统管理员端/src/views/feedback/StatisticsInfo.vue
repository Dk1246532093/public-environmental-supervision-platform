<template>
  <el-breadcrumb>
    <el-breadcrumb-item>确认AQI数据管理</el-breadcrumb-item>
    <el-breadcrumb-item>确认AQI数据详情</el-breadcrumb-item>
  </el-breadcrumb>
  <el-descriptions
    class="margin-top"
    title="确认AQI数据详情"
    :column="1"
    border
    style="margin-top: 20px;"
  >
    <template #extra>
      <el-button type="primary" @click="back">返回</el-button>
    </template>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          确认AQI数据编号
        </div>
      </template>
      {{statistics.id}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          确认信息所在地址
        </div>
      </template>
      <el-tag size="small">{{statistics.gridProvince.provinceName}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.gridCity.cityName}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.address}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          确认AQI等级
        </div>
      </template>
      <el-tag size="small">{{statistics.aqi.chineseExplain}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.aqi.aqiExplain}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          确认日期时间
        </div>
      </template>
      <el-tag size="small">{{statistics.confirmDate}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.confirmTime}}</el-tag>&nbsp;
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          网格员信息
        </div>
      </template>
      <el-tag size="small">{{statistics.gridMember.gmName}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.gridMember.tel}}</el-tag>&nbsp;
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          公众监督员信息
        </div>
      </template>
      <el-tag size="small">{{statistics.supervisor.realName}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.supervisor.sex==1?'男':'女'}}</el-tag>&nbsp;
      <el-tag size="small">{{statistics.supervisor.birthday}}</el-tag>
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          公众监督员反馈信息描述
        </div>
      </template>
      {{statistics.information}}
    </el-descriptions-item>
  </el-descriptions>
</template>

<script setup>
import { ref, inject } from "vue";
import { useRouter, useRoute } from "vue-router";

const route = useRoute();
const router = useRouter();
const axios = inject('axios');

const statistics = ref({
  supervisor:{},
  gridProvince: {},
  gridCity: {},
  aqi: {},
  gridMember: {}
});

const init = ()=>{
  axios
    .post("statistics/getStatisticsById", {
      id: route.query.id
    })
    .then((response) => {
      statistics.value = response.data;
    })
    .catch((error) => {
      console.log(error);
    });
}
init();

const back = ()=>{
  router.go(-1);
}
</script>

<style>

</style>