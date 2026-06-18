import { createApp } from 'vue'
import axios from 'axios'
import ElementPlus, { ElMessage } from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'
import store from './store'

axios.defaults.baseURL = process.env.VUE_APP_API_BASE_URL || '/api'

router.beforeEach(function (to, from, next) {
    let admins = sessionStorage.getItem('admins')
    if (!(to.path === '/login' || to.path === '/')) {
        if (admins == null) {
            next('/login')
            return
        }
    }
    next()
})

const app = createApp(App)
const myElMessage = (message, type = 'info') => {
    ElMessage({ message, type })
}

app.provide('axios', axios)
app.provide('myElMessage', myElMessage)
app.provide('$axios', axios)
app.provide('$myElMessage', myElMessage)
app.use(store).use(router).use(ElementPlus).mount('#app')
