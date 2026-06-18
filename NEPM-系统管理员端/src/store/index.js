/**
 * vuex状态管理
 * 作用：
 * 1.集中管理共享状态
 * 2.实现组件之间的通信
 * 3. 数据持久化（结合localStorage）
 */
import { createStore } from 'vuex'
export default createStore({
    //state:存储状态数据
    state: {
        maxPageNum: 10 //最大页码数,所有页面共享，最多都是10条数据
    },
    // getters:计算属性
    getters: {
        // 获取分页配置
        pageSize: state => state.maxPageNum,
    },
    mutations: {},
    actions: {},
    modules: {},
})