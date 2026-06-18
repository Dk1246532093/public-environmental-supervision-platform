<!-- 指派网格员实现 -->
<template>
    <el-breadcrumb>
        <el-breadcrumb-item>公众监督数据管理</el-breadcrumb-item>
        <el-breadcrumb-item>指派网格员</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 详情信息，复用 -->
    <DataDetails/>
    <div class = 'assign'>
        <!-- 是否异地指派 -->
         <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="是否异地指派">
                <el-switch v-model="isRemote" size = 'small'></el-switch>
            </el-form-item>
         </el-form>
         <!-- 本地指派 -->
        <el-form :inline="true" class="demo-form-inline" v-if = '!isRemote'
        size = 'small'>
        <el-form-item label="指派给">
            <el-select v-model="gmIdLocal">
                <el-option 
                v-for="gridMember in gridMemberLocalArr" 
                :key="gridMember.gmId"
                :label="gridMember.gmName"
                :value="gridMember.gmId" />
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-popconfirm
            title="确定要指派吗?"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @confirm="assignEvent"
            >
            <template #reference>
                <el-button>本地指派</el-button>
            </template>
            </el-popconfirm>
        </el-form-item>
        </el-form>
        <!-- 异地指派：（省，市，网格员的三级联动） -->
        <el-form :inline="true" class="demo-form-inline" v-if = 'isRemote'
        size = 'small'>
            <el-form-item label="省区域">
                <el-select v-model="provinceIdNoLocal" @change="changeProvince">
            <el-option
                :label="province.provinceName"
                :value="province.provinceId"
                v-for="province in provinceArr"
                :key="province.provinceId"
            />
                </el-select>
            </el-form-item>

            <el-form-item label="市区域">
                <el-select v-model="cityIdNoLocal" @change="changeCity">
                <el-option
                    :label="city.cityName"
                    :value="city.cityId"
                    v-for="city in cityArr"
                    :key="city.cityId"
                />
                </el-select>
            </el-form-item>
            <el-form-item label="异地指派给">
                <el-select v-model="gmIdNoLocal">
                <el-option
                    :label="gridMember.gmName"
                    :value="gridMember.gmId"
                    v-for="gridMember in gridMemberNoLocalArr"
                    :key="gridMember.gmId"
                />
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-popconfirm
                title="确定要指派吗?"
                confirm-button-text="确定"
                cancel-button-text="取消"
                @confirm="assignEvent"
                >
                <template #reference>
                    <el-button>异地指派</el-button>
                </template>
                </el-popconfirm>
            </el-form-item>
        </el-form>
    </div>
</template>

<script setup>
import DataDetails from "../../components/DataDetails.vue"
import { ref, inject } from "vue"
import { useRouter, useRoute } from "vue-router"
import { getCurDate, getCurTime } from "../../common.js"
const router = useRouter()
const route = useRoute()
const axios = inject('axios')
const myElMessage = inject('myElMessage')
// 数据
// 是否异地指派
const isRemote = ref(false)
// 本地指派相关
const gmIdLocal = ref(0)
const gridMemberLocalArr = ref([])
// 异地指派相关
const gmIdNoLocal = ref(0)
const gridMemberNoLocalArr = ref([])
const provinceArr = ref([])
const cityArr = ref([])
const provinceIdNoLocal = ref(0)
const cityIdNoLocal = ref(0)
// 方法
//初始化方法
const init = () => {
    // 1.查询本地网格员(根据当前反馈的省、市)
    axios
    .post('gridMember/listGridMemberByProvinceId',{
        provinceId: route.query.provinceId,
        cityId: route.query.cityId,
        state: 0 // 未指派
    })
    .then(res => {
        gridMemberLocalArr.value = res.data
        if (gridMemberLocalArr.value.length > 0) {
            //默认选中第一个网格员
            gmIdLocal.value = gridMemberLocalArr.value[0].gmId
        }

    })
    .catch(err => {
        console.log(err)
    })
    // 2.查询所有省（用于异地指派）
    axios
    .post('gridProvince/listGridProvinceAll')
    .then(res => {
        provinceArr.value = res.data
        //默认选中第一个省
        if (provinceArr.value.length > 0) {
            provinceIdNoLocal.value = provinceArr.value[0].provinceId
            // 根据默认省查询市
            listGridCityByProvinceId()
        }
            })
        .catch(err => {
            console.log(err)
        })
}
init()
// 根据省查询市
const listGridCityByProvinceId = () => {
    axios
    .post('gridCity/listGridCityByProvinceId',{
        provinceId: provinceIdNoLocal.value
    })
    .then(res => {
        cityArr.value = res.data
        //默认选中第一个市
        if (cityArr.value.length > 0) {
            cityIdNoLocal.value = cityArr.value[0].cityId
            // 根据默认省、市查询网格员
            listGridMemberNoLocal()
        } else {
            cityIdNoLocal.value = 0
            gridMemberNoLocalArr.value = []
            gmIdNoLocal.value = 0
        }
        })
        .catch(err => {
            console.log(err)
        })   
}
// 查询异地异地网格员（根据选择的省和市）
const listGridMemberNoLocal = () => {
    axios
    .post('gridMember/listGridMemberByProvinceId',{
        provinceId: provinceIdNoLocal.value,
        cityId: cityIdNoLocal.value,
        state: 0 // 未指派
    })
    .then(res => {
        gridMemberNoLocalArr.value = res.data
        if (gridMemberNoLocalArr.value.length > 0) {
            //默认选中第一个网格员
            gmIdNoLocal.value = gridMemberNoLocalArr.value[0].gmId
        }else{
            gmIdNoLocal.value = 0
        }
    })
    .catch(err => {
        console.log(err)
    })
}
// 省下拉变化事件
const changeProvince = () => {
    listGridCityByProvinceId()
}
// 市下拉变化事件
const changeCity = () => {
    listGridMemberNoLocal()
}
// 提交指派（本地或者异地）
const assignEvent = () => {
    let gmId = isRemote.value ? gmIdNoLocal.value : gmIdLocal.value
  if (gmId == 0) {
     myElMessage('请选择一个网格员！', 'warning')
    return
  }
  axios
  .post('aqiFeedback/updateAqiFeedbackAssign',{
    gmId: gmId,
    assignDate: getCurDate(),
    assignTime: getCurTime(),
    state: 1,
    afId: route.query.afId
})
.then((response) => {
      if (response.data == 1) {
        router.push('/index/feedbackList')   // 指派成功返回列表页
      } else {
        myElMessage('指派失败！', 'error')
      }
    })
    .catch((error) => {
      console.log(error)
    })
}


</script>

<style lang="scss" scoped>

</style>
