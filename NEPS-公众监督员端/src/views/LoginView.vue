<template>
  <div class="login-page">
    <!-- 头部背景 -->
    <div class="header-bg">
      <div class="earth-container">
        <div class="earth"></div>
        <div class="city-silhouette">
          <div class="building" v-for="i in 7" :key="i"></div>
          <div class="tree" v-for="i in 3" :key="`tree-${i}`"></div>
        </div>
        <div class="birds">
          <span class="bird" v-for="i in 3" :key="i"></span>
        </div>
      </div>
    </div>

    <!-- 登录表单 -->
    <div class="login-container">
      <div class="platform-title">
        <h1>东软环保公众监督平台</h1>
        <p class="subtitle">公众监督员端</p>
      </div>

      <div class="login-form">
        <van-form @submit="onSubmit">
          <van-cell-group inset>
            <van-field
              v-model="phone"
              name="phone"
              placeholder="请输入手机号"
              :rules="[{ required: true, message: '请输入手机号' }]"
            >
              <template #left-icon>
                <van-icon name="phone-o" class="input-icon" />
              </template>
            </van-field>
            <van-field
              v-model="password"
              type="password"
              name="password"
              placeholder="请输入密码"
              :rules="[{ required: true, message: '请输入密码' }]"
            >
              <template #left-icon>
                <van-icon name="lock" class="input-icon" />
              </template>
            </van-field>
          </van-cell-group>

          <div class="register-link">
            <router-link to="/register">还没有账号？立即注册</router-link>
          </div>

          <div class="submit-btn">
            <van-button round block type="primary" native-type="submit" color="#5c9ead">
              登录
            </van-button>
          </div>
        </van-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { showToast } from 'vant'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/supervisor'

const router = useRouter()
const userStore = useUserStore()

const phone = ref('')
const password = ref('')

const onSubmit = async (values: any) => {
  try {
    if (!values.phone || !values.password) {
      showToast('请输入手机号和密码')
      return
    }
    const res: any = await login({
      telId: values.phone,
      password: values.password
    })
    if (res && res.telId) {
      userStore.setToken('token-' + Date.now())
      userStore.setUserInfo({
        id: res.telId,
        phone: res.telId,
        name: res.realName || '用户',
        gender: res.sex === 0 ? '女' : '男',
        idCard: ''
      })
      showToast('登录成功')
      router.push('/')
    } else {
      showToast('手机号或密码错误')
    }
  } catch (error) {
    showToast('登录失败')
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #c8e6c9 0%, #e8f5e9 30%, #b2dfdb 100%);
}

/* 头部背景 */
.header-bg {
  width: 100%;
  height: 200px;
  background: linear-gradient(180deg, #a5d6a7 0%, #81c784 50%, #66bb6a 100%);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.earth-container {
  position: relative;
  width: 180px;
  height: 180px;
}

.earth {
  width: 150px;
  height: 150px;
  background: linear-gradient(135deg, #4caf50 0%, #2e7d32 50%, #1b5e20 100%);
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3), inset -20px -20px 40px rgba(0, 0, 0, 0.2);
}

.earth::before {
  content: '';
  position: absolute;
  width: 60px;
  height: 40px;
  background: #2e7d32;
  border-radius: 50%;
  top: 30px;
  left: 20px;
}

.earth::after {
  content: '';
  position: absolute;
  width: 50px;
  height: 60px;
  background: #2e7d32;
  border-radius: 50%;
  bottom: 30px;
  right: 25px;
}

.city-silhouette {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 80px;
}

.building {
  position: absolute;
  bottom: 0;
  background: #4caf50;
}

.building:nth-child(1) { left: 20px; width: 25px; height: 50px; }
.building:nth-child(2) { left: 50px; width: 20px; height: 70px; }
.building:nth-child(3) { left: 75px; width: 30px; height: 45px; }
.building:nth-child(4) { left: 110px; width: 22px; height: 60px; }
.building:nth-child(5) { right: 80px; width: 28px; height: 55px; }
.building:nth-child(6) { right: 45px; width: 25px; height: 65px; }
.building:nth-child(7) { right: 15px; width: 20px; height: 40px; }

.tree {
  position: absolute;
  bottom: 0;
}

.tree::before {
  content: '';
  position: absolute;
  width: 8px;
  height: 20px;
  background: #5d4037;
  bottom: 0;
}

.tree::after {
  content: '';
  position: absolute;
  width: 30px;
  height: 30px;
  background: #2e7d32;
  border-radius: 50%;
  bottom: 15px;
  left: -11px;
}

.tree:nth-child(8) { left: 10px; }
.tree:nth-child(9) { left: 160px; }
.tree:nth-child(10) { right: 10px; }

.birds {
  position: absolute;
  top: 20px;
  right: 30px;
}

.bird {
  display: inline-block;
  width: 15px;
  height: 8px;
  border: 2px solid #4caf50;
  border-bottom: none;
  border-radius: 50% 50% 0 0;
  margin: 0 3px;
  transform: rotate(-20deg);
}

/* 登录表单 */
.login-container {
  padding: 30px 25px;
}

.platform-title {
  text-align: center;
  margin-bottom: 10px;
}

.platform-title h1 {
  font-size: 24px;
  color: #2e7d32;
  font-weight: bold;
}

.platform-title .subtitle {
  font-size: 14px;
  color: #66bb6a;
  margin-top: 5px;
}

.login-form {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 25px;
  margin-top: 20px;
}

.input-icon {
  color: #999;
  font-size: 20px;
  margin-right: 8px;
}

.register-link {
  text-align: right;
  margin: 15px 0;
}

.register-link a {
  color: #666;
  font-size: 14px;
  text-decoration: none;
}

.submit-btn {
  margin-top: 20px;
}

:deep(.van-field) {
  background: #f9f9f9;
  border-radius: 8px;
  margin-bottom: 15px;
}

:deep(.van-cell-group--inset) {
  margin: 0;
  background: transparent;
}
</style>
