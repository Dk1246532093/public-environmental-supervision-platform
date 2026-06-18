<!-- 确认数据列表 -->
<template>
    <!-- 导航，指示当前页面位置， -->
    <el-breadcrumb>
        <el-breadcrumb-item>确认AQI数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>确认AQI数据列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 筛选表单：省、市，确认日期 -->
    <el-form 
        :inline="true"
        :model="queryForm"
        size="small"
        style="margin-top: 20px"
        class="demo-form-inline">

        <el-form-item label="省区域">
            <el-select v-model="queryForm.provinceId" @change="changeProvince"
            style=" width: 100px">
            <el-option
                :label="province.provinceName"
                :value="province.provinceId"
                v-for="province in provinceArr"
                :key="province.provinceId"
                />
            </el-select>
        </el-form-item>

        <el-form-item label="市区域">
            <el-select v-model="queryForm.cityId"
            style=" width: 100px">
            <el-option
                :label="city.cityName"
                :value="city.cityId"
                v-for="city in cityArr"
                :key="city.cityId">
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="确认日期">
            <el-date-picker
            v-model="queryForm.confirmDate"
            type="date"
            style="width: 100px"
            placeholder="确认日期"
            format="YYYY/MM/DD"
            value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item>
            <el-button type="danger" @click="clear">清空</el-button>
            <el-button type="primary" @click="query">查询</el-button>
        </el-form-item>
    </el-form>
    <!-- 表格展示数据 -->
     <el-table :data="pageResponseDto.list" style="width: 100%"
     :cell-style="{ padding: '4px' }"
     empty-text="无数据">
        <el-table-column prop="id" label="编号" width = '90'/>
        <el-table-column prop="gridProvince.provinceName" label="所在省"/>
        <el-table-column prop="gridCity.cityName" label="所在市"/>
        <!-- 动态颜色渲染单元格 -->
        <el-table-column label="AQI等级">
            <template #default="scope">
            <p
            :style="{
                color: scope.row.aqi.color,
                fontSize: '14px',
                fontWeight: '700',
                textAlign: 'center',
            }"
            >
            {{ scope.row.aqi.aqiExplain }} ({{ scope.row.aqi.chineseExplain }})
            </p>
        </template>
        </el-table-column>
        <el-table-column prop="confirmDate" label="确认日期"/>
        <el-table-column prop="confirmTime" label="确认时间"/>
        <el-table-column prop="gridMember.gmName" label="网格员" />
        <el-table-column prop="supervisor.realName" label="反馈者" />
        <el-table-column label="操作" width="120">
        <template #default="scope">
            <el-button link type="primary" size="small" @click="detailsEvent(scope.row.id)">
            <el-icon title="查看详情" style="font-size: 18px"><Tickets /></el-icon>
            </el-button>
        </template>
        </el-table-column>
     </el-table>
     <!-- 分页组件 -->
    <div class="pagination">
        <el-pagination
        background
        small
        layout="prev, pager, next, total"
        :total="pageResponseDto.totalRow"
        :page-size="pageResponseDto.maxPageNum"
        style="margin-top: 20px"
        v-model:current-page="pageNum"
        />
    </div>
</template>

<script setup>
import { Tickets } from "@element-plus/icons-vue"
import { reactive, ref, inject, watch } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

const store = useStore()
const router = useRouter()
const axios = inject("axios")
const queryForm = reactive({
    provinceId: 0,
    cityId: 0,
    confirmDate: ''
})
const pageNum = ref(1)
watch(pageNum,(newVal)=>{
    listStatisticsPage(newVal)

})
// 查询省份列表
const provinceArr = ref([])
const cityArr = ref([])
const pageResponseDto = ref({
    list: [],
    totalRow: 0,
    maxPageNum: store.state.maxPageNum
})
// 分页查询确认数据
const listStatisticsPage = async (pageNum) => {
    let param = {
    provinceId: queryForm.provinceId,
    cityId: queryForm.cityId,
    confirmDate: queryForm.confirmDate,
    pageNum: pageNum,
    maxPageNum: store.state.maxPageNum,
  }
  axios
    .post("statistics/listStatisticsPage", param)
    .then((response) => {
      pageResponseDto.value = response.data
      console.log(pageResponseDto.value)
    })
    .catch((error) => {
      console.log(error)
    })
}
// 查询省份列表
const listGridProvinceAll = () => {
  axios
    .post("gridProvince/listGridProvinceAll")
    .then((response) => {
      provinceArr.value = response.data
      if (provinceArr.value.length > 0) {
        listGridCityByProvinceId(provinceArr.value[0].provinceId)
      }
      provinceArr.value.unshift({
        provinceId: 0,
        provinceName: "-- 全部 --",
      })
    })
    .catch((error) => {
      console.log(error)
    })
}
// 查询城市列表
const listGridCityByProvinceId = (provinceId) => {
  axios
    .post("gridCity/listGridCityByProvinceId", {
      provinceId: provinceId,
    })
    .then((response) => {
      cityArr.value = response.data
      cityArr.value.unshift({
        cityId: 0,
        cityName: "-- 全部 --",
      })
    })
    .catch((error) => {
      console.log(error)
    })
}
// 初始化方法
const init = () => {
  listGridProvinceAll()
  listStatisticsPage(1)
}
init()
// 清除查询条件
const clear = () => {
    queryForm.provinceId = 0
    queryForm.cityId = 0
    queryForm.confirmDate = ''
}
const query = () => {
    pageNum.value = 1
    listStatisticsPage(1)
}
// 修改省份下拉框
const changeProvince = () => {
    listGridCityByProvinceId(queryForm.provinceId)
}
const detailsEvent = (id) => {
    router.push({ path: "/index/statisticsInfo", query: { id: id } })
}

</script>

<style lang="scss" scoped>

</style>
