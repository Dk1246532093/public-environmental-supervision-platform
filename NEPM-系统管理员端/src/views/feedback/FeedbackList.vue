<template>
    <!-- 导航，指示当前页面位置， -->
    <el-breadcrumb>
        <el-breadcrumb-item>公众监督数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>公众监督数据列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 筛选表单：省、市、预估等级、反馈日期、状态（未指派/已指派） -->
    <el-form :model="queryForm" 
        :inline = 'true'
        size = 'small'
        style="margin-top: 20px"
        class="demo-form-inline">
    <!-- 省区域 -->
    <el-form-item label="省区域">
        <el-select v-model="queryForm.provinceId" 
        @change = 'changeProvince' 
        style = "width: 100px">
        <el-option :label = "province.provinceName" 
        :value = "province.provinceId"
        v-for = 'province in provinceArr'
        :key="province.provinceId"></el-option>
    </el-select>
    </el-form-item>
    <!-- 城市区域 -->
    <el-form-item label="市区域">
        <el-select v-model="queryForm.cityId" 
        style = "width: 100px">
        <el-option :label = "city.cityName" 
        :value = "city.cityId"
        v-for = 'city in cityArr'
        :key="city.cityId"></el-option>
    </el-select>
    </el-form-item>
    <!-- 空气质量预估等级 -->
    <el-form-item label="预估等级">
        <el-select v-model="queryForm.estimatedGrade" 
        style = "width: 80px">
        <el-option :label = "aqi.chineseExplain" 
        :value = "aqi.aqiId"
        v-for = "aqi in aqiArr"
        :key="aqi.aqiId"></el-option>
        </el-select>
    </el-form-item>
    <!-- 反馈日期 -->
     <el-form-item label="反馈日期">
        <el-date-picker v-model="queryForm.afDate" 
        type = 'date'
        style = 'width:100px'
        placeholder = "反馈日期"
        format="YYYY/MM/DD"
        value-format="YYYY-MM-DD">
        </el-date-picker>
     </el-form-item>
     <el-form-item>
        <el-button type = 'danger' @click = 'clear'>清空</el-button>
        <el-button type = 'primary' @click = 'query'>查询</el-button>
     </el-form-item>
     <el-form-item label = ''>
        <!-- 指派状态单选框 -->
        <el-radio-group v-model="queryForm.state">
            <el-radio :label="0">未指派</el-radio>
            <el-radio :label="1">已指派</el-radio>
        </el-radio-group>
     </el-form-item>
     </el-form>
     <!-- 数据表格 -->
    <el-table
        :data = 'pageResponseDto.list'
        style = 'width: 100%'
        :cell-style="{ padding: '4px' }"
        empty-text="无数据">
        <el-table-column prop="afId" label="编号" width="90" />
        <el-table-column prop="supervisor.realName" label="反馈者姓名" />
        <el-table-column prop="gridProvince.provinceName" label="所在省" />
        <el-table-column prop="gridCity.cityName" label="所在市" />
        <!-- 污染等级列，根据 aqi.color 动态设置文字颜色 -->
        <el-table-column prop="estimatedGrade" label="预估污染等级">
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
         <el-table-column prop="afDate" label="反馈日期" />
        <el-table-column prop="afTime" label="反馈时间" />
        <!-- 操作列：查看详情图标，根据 state 显示指派或重新指派图标 -->
        <el-table-column label="操作" width="120">
        <template #default="scope">
            <el-button link type="primary" size="small" @click="detailsEvent(scope.row.afId)">
            <el-icon title="查看详情" style="font-size: 18px"><Tickets /></el-icon>
            </el-button>
            <el-button link type="danger" size="small" @click="assignEvent(scope.row)" v-if="queryForm.state == 0">
            <el-icon title="指派网格员" style="font-size: 18px"><Pointer /></el-icon>
            </el-button>
            <el-button link type="danger" size="small" @click="assignEvent(scope.row)" v-if="queryForm.state == 1">
            <el-icon title="重新指派" style="font-size: 18px"><Edit /></el-icon>
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
import {Tickets, Pointer, Edit } from "@element-plus/icons-vue"
import {reactive,ref, inject, watch} from "vue"
import {useRouter} from "vue-router"
import {useStore} from "vuex"
//
const store = useStore()
const router = useRouter()
const axios = inject('axios')
// 查询表单对象
const queryForm = reactive({
    provinceId: 0,
    cityId: 0,
    estimatedGrade: 0,
    afDate: '',
    state: 0,// 0 未指派 1 已指派
})
//当前页码，与分页组件双向绑定
const pageNum = ref(1)
// 监听页码变化，重新获取对应页码的数据
watch(pageNum,(newvalue)=>{
    listAqiFeedbackPage(newvalue)
})
// 下拉列表数据
const provinceArr = ref([]) // 省份列表数据
const cityArr = ref([]) // 城市列表数据
const aqiArr = ref([])// 空气质量等级列表数据
const pageResponseDto = ref({
    list: [],
    totalRow: 0,
    maxPageNum: store.state.maxPageNum
})// 分页响应数据
// API调用函数
// 分页查询反馈列表
const listAqiFeedbackPage = (pageNum) => {
    let param = {
        provinceId: queryForm.provinceId,
        cityId: queryForm.cityId,
        estimatedGrade: queryForm.estimatedGrade,
        afDate: queryForm.afDate,
        state: queryForm.state,
        pageNum: pageNum,
        maxPageNum: store.state.maxPageNum // Vuex中配置的分页最大值
    }
    axios
    .post('aqiFeedback/listAqiFeedbackPage',param)
    .then(res=>{
        pageResponseDto.value = res.data
        console.log(pageResponseDto.value)
    })
    .catch(err=>{
        console.log(err)
    })
}
// 查询所有省
const listGridProvinceAll = () => {
    axios
    .post('gridProvince/listGridProvinceAll')
    .then(res=>{
        provinceArr.value = res.data
        // 默认按照第一个省查询市
        listGridCityByProvinceId(provinceArr.value[0].provinceId)
        // 在下拉选项最前面添加'全部'选项
        provinceArr.value.unshift({
            provinceId: 0,
            provinceName: '--全部--'
        })
    })
    .catch(err=>{
        console.log(err)
    })
}
// 根据省ID查询市列表
const listGridCityByProvinceId = (provinceId) => {
    axios
    .post('gridCity/listGridCityByProvinceId',{provinceId: provinceId})
    .then(res=>{
        cityArr.value = res.data
        // 在下拉选项最前面添加'全部'选项
        cityArr.value.unshift({
            cityId: 0,
            cityName: '--全部--'
        })
    })
    .catch(err=>{
        console.log(err)
    })
}
// 查询所有AQI等级
const listAqiAll = () => {
    axios
    .post('aqi/listAqiAll')
    .then(res=>{
        aqiArr.value = res.data
        // 在下拉选项最前面添加'全部'选项
        aqiArr.value.unshift({
            aqiId: 0,
            chineseExplain: '--全部--'
        })
    })
    .catch(err=>{
        console.log(err)
    })
}
//页面初始化：加载省、AQI等级、列表书
const init = () => {
    listGridProvinceAll()
    listAqiAll()
    listAqiFeedbackPage(1)
}
init()
// 清空筛选条件
const clear = () => {
    queryForm.provinceId = 0
    queryForm.cityId = 0
    queryForm.estimatedGrade = 0
    queryForm.afDate = ''
    queryForm.state = 0
}
//查询按钮：重新获取第一页数据
const query = () => {
    pageNum.value = 1
    listAqiFeedbackPage(1)
}
// 省下拉变化，重新加载市列表
const changeProvince = () => {
    listGridCityByProvinceId(queryForm.provinceId)
}
// 跳转到详情页面
const detailsEvent = (afId) => {
    router.push({
        path: '/index/feedbackInfo',
        query: {afId: afId}
    })
}
// 跳转到指派页（或者重新指派）
const assignEvent = (af) => {
    router.push({
        path: '/index/assignFeedback',
        query: {
            afId: af.afId,
            provinceId: af.provinceId,
            cityId: af.cityId,
        }
    })
}

</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
}
</style>
