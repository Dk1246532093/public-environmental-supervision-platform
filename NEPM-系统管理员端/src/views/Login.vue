<template>
    <!-- element-plus，卡片组件，用于登录框 -->
    <el-card class="box-card">
        <template #header>
            <div class='card-header'>
                <span>登录</span>
            </div>
        </template>
        <div>
            <!-- 表单：登录编码和密码 -->
            <el-form :model='adminsForm' label-width='80px'>
                <el-form-item label='登录编码'>
                    <el-input v-model='adminsForm.adminCode' placeholder='登录编码'></el-input>
                </el-form-item>
                <el-form-item label='登录密码'>
                    <el-input v-model='adminsForm.password' show-password placeholder='登录密码'></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type='primary' @click='login'>登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: 'LoginPage'
    }
</script>

<script setup>
    import { reactive, inject } from 'vue'
    import { useRouter } from 'vue-router'
    import { setSessionStorage } from '@/common.js'
    const myElMessage = inject('myElMessage')
    const axios = inject('axios')
    const router = useRouter()
    //数据定义
    const adminsForm = reactive({
        adminCode: '',
        password: ''
    })
    //方法定义
    //登录验证：非空验证，调用后端接口，成功存储管理员信息并跳转到首页，失败给出提示
    const login = () => {
        if (adminsForm.adminCode == '' || adminsForm.password == '') {
            myElMessage('登录编码或登录密码不能为空', 'warning')
            return
        }
        axios
            .post('admins/getAdminsByCodeByPass', null, {
                params: {
                    adminCode: adminsForm.adminCode,
                    password: adminsForm.password
                }
            })
            .then(res => {
                if (res.data != '') {
                    setSessionStorage('admins', res.data)
                    router.push('/index')// 跳转到首页
                } else {
                    myElMessage('登录编码或登录密码不正确', 'error')
                }
            })
            .catch(err => {
                console.log(err)
            })
    }

</script>

<style scoped>
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .box-card {
        width: 400px;
        margin: 0 auto;
        margin-top: 150px;
    }
</style>