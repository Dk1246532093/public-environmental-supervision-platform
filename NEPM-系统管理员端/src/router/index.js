import {createRouter, createWebHashHistory} from 'vue-router'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'
import HelloWorld from '../components/HelloWorld.vue'
import FeedbackList from '../views/feedback/FeedbackList.vue'
import StatisticsList from '../views/feedback/StatisticsList.vue'
import StatisticsInfo from '../views/feedback/StatisticsInfo.vue'
import FeedbackInfo from '../views/feedback/FeedbackInfo.vue'
import AssignFeedback from '../views/feedback/AssignFeedback.vue'
import ProvinceItemTotalStatis from '../views/statistics/ProvinceItemTotalStatis.vue'
import AqiDistributeTotalStatis from '../views/statistics/AqiDistributeTotalStatis.vue'
import AqiTrendTotalStatis from '../views/statistics/AqiTrendTotalStatis.vue'
import OtherTotalStatis from '../views/statistics/OtherTotalStatis.vue'
const routes = [
    {
        path : '/',
        name :'home',
        component : Login
    },
    {
        path: '/login',
        name: 'login',
        component: Login,
    },
    {
        path: '/index',
        component: Index,
        children:[
            { 
                path:'',
                name: 'index',
                component: HelloWorld
            },
            { 
                path:'feedbackList', 
                component: FeedbackList
            },
            { 
                path:'statisticsList',
                component: StatisticsList
            },
            { 
                path:'statisticsInfo',
                component: StatisticsInfo
            },
            {
                path: 'provinceItemTotalStatis',
                component: ProvinceItemTotalStatis
            },{
                path: 'aqiDistributeTotalStatis',
                component: AqiDistributeTotalStatis
            },{
                path: 'aqiTrendTotalStatis',
                component: AqiTrendTotalStatis
            },{
                path: 'otherTotalStatis',
                component: OtherTotalStatis
            },{
                path: 'feedbackInfo',
                component: FeedbackInfo
            },{
                path: 'assignFeedback',
                component: AssignFeedback
            }
    ]
  }
]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router
