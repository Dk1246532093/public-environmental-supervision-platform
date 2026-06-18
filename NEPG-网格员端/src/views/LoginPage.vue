<template>
  <div class="login-page">
    <!-- 蓝色科技主题头部 -->
    <div class="header-bg-blue">
      <div class="tech-illustration">
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-building"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="tech-person"></div>
        <div class="platform-icon">📊</div>
        <div class="platform-icon">🔍</div>
        <div class="platform-icon">📱</div>
      </div>
      <div class="wave-bottom">
        <svg viewBox="0 0 375 60" preserveAspectRatio="none">
          <path d="M0,30 Q93.75,60 187.5,30 T375,30 L375,60 L0,60 Z" fill="white" />
          <path d="M0,40 Q93.75,70 187.5,40 T375,40 L375,60 L0,60 Z" fill="rgba(255,255,255,0.5)" />
        </svg>
      </div>
    </div>

    <div class="login-container">
      <div class="platform-title-blue">
        <h1>东软环保公众平台-网格员端</h1>
      </div>
      <div class="login-form-blue">
        <div class="input-group-blue">
          <span class="input-icon-blue">👤</span>
          <input type="text" v-model="loginForm.code" placeholder="请输入登录编码">
        </div>
        <div class="input-group-blue">
          <span class="input-icon-blue">🔒</span>
          <input type="password" v-model="loginForm.password" placeholder="请输入密码">
        </div>
        <button class="btn-blue" @click="handleLogin">登录</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { reactive } from 'vue'
  import { useRouter } from 'vue-router'
  import { login } from '@/api/gridMember'

  const router = useRouter()

  const loginForm = reactive({
    code: '',
    password: ''
  })

  const handleLogin = async () => {
    if (!loginForm.code || !loginForm.password) {
      alert('请输入登录编码和密码')
      return
    }
    try {
      const res: any = await login({
        gmCode: loginForm.code,
        password: loginForm.password
      })
      if (res && res.gmId) {
        localStorage.setItem('token', 'token-' + Date.now())
        localStorage.setItem('gmInfo', JSON.stringify(res))
        alert('登录成功')
        router.push('/task-list')
      } else {
        alert('登录编码或密码错误')
      }
    } catch (error) {
      alert('登录失败')
    }
  }
</script>

<style scoped>
  .login-page {
    min-height: 100vh;
    background: #fff;
  }

  /* 蓝色科技主题头部 */
  .header-bg-blue {
    width: 100%;
    height: 280px;
    background: linear-gradient(180deg, #64b5f6 0%, #42a5f5 50%, #2196f3 100%);
    position: relative;
    overflow: hidden;
  }

  .wave-bottom {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 60px;
  }

  .wave-bottom svg {
    width: 100%;
    height: 100%;
  }

  /* 科技插画 */
  .tech-illustration {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 280px;
    height: 150px;
  }

  .tech-building {
    position: absolute;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
  }

  .tech-building::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 4px;
    background: #1976d2;
    top: 0;
    border-radius: 8px 8px 0 0;
  }

  .tech-building:nth-child(1) {
    width: 50px;
    height: 80px;
    left: 30px;
    bottom: 20px;
  }

  .tech-building:nth-child(2) {
    width: 40px;
    height: 100px;
    left: 90px;
    bottom: 20px;
  }

  .tech-building:nth-child(3) {
    width: 60px;
    height: 70px;
    left: 140px;
    bottom: 20px;
  }

  .tech-building:nth-child(4) {
    width: 45px;
    height: 90px;
    left: 210px;
    bottom: 20px;
  }

  .tech-person {
    position: absolute;
    width: 20px;
    height: 35px;
  }

  .tech-person::before {
    content: '';
    position: absolute;
    width: 10px;
    height: 10px;
    background: #ff7043;
    border-radius: 50%;
    top: 0;
    left: 5px;
  }

  .tech-person::after {
    content: '';
    position: absolute;
    width: 16px;
    height: 20px;
    background: #5c6bc0;
    border-radius: 4px;
    bottom: 0;
    left: 2px;
  }

  .tech-person:nth-child(5) {
    left: 45px;
    bottom: 20px;
  }

  .tech-person:nth-child(6) {
    left: 105px;
    bottom: 20px;
  }

  .tech-person:nth-child(7) {
    left: 160px;
    bottom: 20px;
  }

  .tech-person:nth-child(8) {
    left: 225px;
    bottom: 20px;
  }

  .platform-icon {
    position: absolute;
    width: 40px;
    height: 40px;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
  }

  .platform-icon:nth-child(9) {
    left: 20px;
    top: 30px;
  }

  .platform-icon:nth-child(10) {
    right: 30px;
    top: 40px;
  }

  .platform-icon:nth-child(11) {
    left: 50px;
    top: 80px;
  }

  /* 登录页面 */
  .login-container {
    padding: 30px 30px;
  }

  .platform-title-blue {
    text-align: center;
    margin-bottom: 30px;
  }

  .platform-title-blue h1 {
    font-size: 20px;
    color: #2196f3;
    font-weight: bold;
  }

  .login-form-blue {
    margin-top: 20px;
  }

  .input-group-blue {
    margin-bottom: 20px;
    position: relative;
  }

  .input-group-blue input {
    width: 100%;
    padding: 15px 15px 15px 45px;
    border: 1px solid #e0e0e0;
    border-radius: 25px;
    font-size: 14px;
    background: #f5f5f5;
    outline: none;
  }

  .input-group-blue input:focus {
    border-color: #2196f3;
    background: #fff;
  }

  .input-icon-blue {
    position: absolute;
    left: 18px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 16px;
    color: #2196f3;
  }

  .btn-blue {
    width: 100%;
    padding: 15px;
    background: linear-gradient(90deg, #42a5f5, #2196f3);
    color: white;
    border: none;
    border-radius: 25px;
    font-size: 16px;
    cursor: pointer;
    margin-top: 20px;
    transition: opacity 0.3s;
  }

  .btn-blue:active {
    opacity: 0.8;
  }
</style>