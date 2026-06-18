<template>
    <!-- 展示公众监督反馈信息详情 -->
    <el-descriptions 
    class = 'margin-top'
    title="公众监督数据详情"
    :column = '1'
    border
    style = 'margin-top: 20px;'>
    <!-- 返回按钮 -->
    <template #extra>
        <el-button type="primary" @click = 'back'>返回</el-button>
    </template>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>公众监督反馈信息编号</div>
        </template>
        {{aqiFeedback.afId}}
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>反馈者信息</div>
        </template>
        <el-tag size="small">{{ aqiFeedback.supervisor.realName }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.supervisor.sex == 1 ? '男' : '女' }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.supervisor.birthday }}</el-tag>
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>反馈者联系电话</div>
        </template>
        {{aqiFeedback.telId}}
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>反馈信息所在地址</div>
        </template>
        <el-tag size="small">{{ aqiFeedback.gridProvince.provinceName }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.gridCity.cityName }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.address }}</el-tag>
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>反馈信息描述</div>
        </template>
        {{aqiFeedback.information}}
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>预估等级</div>
        </template>
        <el-tag size="small">{{ aqiFeedback.aqi.chineseExplain }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.aqi.aqiExplain }}</el-tag>&nbsp;
    </el-descriptions-item>

    <el-descriptions-item>
        <template #label>
        <div class = 'cell-item'>反馈日期时间</div>
        </template>
        <el-tag size="small">{{ aqiFeedback.afDate }}</el-tag>&nbsp;
        <el-tag size="small">{{ aqiFeedback.afTime }}</el-tag>&nbsp;
    </el-descriptions-item>
    </el-descriptions>
    <!-- 如果已经指派，显示网格员信息 -->
    <el-alert
    :title="'已指派网格员：' + aqiFeedback.gridMember.gmName + '（电话号码：' + aqiFeedback.gridMember.tel + '）'"
    type="success"
    center
    show-icon
    style="margin-top: 20px;"
    v-if="aqiFeedback.state == 1"/>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useRouter,useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const axios = inject('axios')
// 定义反馈详情数据对象，
const aqiFeedback = ref({
    supervisor:{},
    gridProvince:{},
    gridCity:{},
    aqi:{},
    gridMember: {}
})
// 根据路由参数afId获取反馈详情
const init = ()=>{
    axios
    .post('aqiFeedback/getAqiFeedbackById',{afId:route.query.afId})
    .then(res=>{
        aqiFeedback.value = res.data
    })
    .catch(err=>{
        console.log(err)
    })
}
init()
// 返回上一页
const back = ()=>{
    router.go(-1)
}


</script>

<style lang="scss" scoped>

</style>
