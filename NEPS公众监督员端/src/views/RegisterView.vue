<template>
  <div class="register-page">
    <!-- 头部 -->
    <div class="register-header">
      <van-icon name="arrow-left" class="back-btn" @click="goBack" />
      <span class="register-title">用户注册</span>
      <span style="width: 24px"></span>
    </div>

    <!-- 标语 -->
    <div class="slogan">
      <span>绿水青山就是金山银山</span>
    </div>

    <!-- 注册表单 -->
    <div class="register-form">
      <van-form @submit="onSubmit">
        <div class="form-row">
          <span class="form-label">姓名</span>
          <van-field v-model="form.name" placeholder="请输入姓名" :rules="[{ required: true, message: '请输入姓名' }]"
            class="form-input" />
        </div>

        <div class="form-row">
          <span class="form-label">性别</span>
          <van-radio-group v-model="form.gender" direction="horizontal" class="radio-group">
            <van-radio name="男">男</van-radio>
            <van-radio name="女">女</van-radio>
          </van-radio-group>
        </div>

        <div class="form-row">
          <span class="form-label">手机号</span>
          <van-field v-model="form.phone" placeholder="请输入手机号" :rules="[{ required: true, message: '请输入手机号' }]"
            class="form-input" />
        </div>

        <div class="form-row">
          <span class="form-label">身份证号</span>
          <van-field v-model="form.idCard" placeholder="请输入身份证号" :rules="[{ required: true, message: '请输入身份证号' }]"
            class="form-input" />
        </div>

        <div class="form-row">
          <span class="form-label">密码</span>
          <van-field v-model="form.password" type="password" placeholder="请输入密码"
            :rules="[{ required: true, message: '请输入密码' }]" class="form-input" />
        </div>

        <div class="form-row">
          <span class="form-label">确认密码</span>
          <van-field v-model="form.confirmPassword" type="password" placeholder="请再次输入密码"
            :rules="[{ required: true, message: '请确认密码' }]" class="form-input" />
        </div>

        <div class="agreement">
          <van-checkbox v-model="agreed">
            我已阅读并同意
            <a href="#">《用户协议》</a>
            和
            <a href="#">《隐私政策》</a>
          </van-checkbox>
        </div>

        <div class="submit-btn">
          <van-button round block type="primary" native-type="submit" color="#5c9ead">
            注册
          </van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { showToast } from 'vant'
  import { register } from '@/api/supervisor'

  const router = useRouter()

  const form = ref({
    name: '',
    gender: '男',
    phone: '',
    idCard: '',
    password: '',
    confirmPassword: ''
  })

  const agreed = ref(false)

  const goBack = () => {
    router.back()
  }

  const onSubmit = async () => {
    if (!agreed.value) {
      showToast('请同意用户协议')
      return
    }
    if (form.value.password !== form.value.confirmPassword) {
      showToast('两次密码不一致')
      return
    }
    try {
      const res: any = await register({
        telId: form.value.phone,
        password: form.value.password,
        realName: form.value.name,
        sex: form.value.gender === '男' ? 1 : 0,
        birthday: '1990-01-01',
        remarks: form.value.idCard
      })
      if (res === 1) {
        showToast('注册成功')
        router.push('/login')
      } else {
        showToast('注册失败，手机号可能已存在')
      }
    } catch (error) {
      showToast('注册失败')
    }
  }
</script>

<style scoped>
  .register-page {
    min-height: 100vh;
    background: linear-gradient(180deg, #c8e6c9 0%, #e8f5e9 30%, #b2dfdb 100%);
  }

  .register-header {
    display: flex;
    align-items: center;
    padding: 15px;
    background: rgba(255, 255, 255, 0.5);
  }

  .back-btn {
    font-size: 20px;
    color: #666;
    cursor: pointer;
  }

  .register-title {
    flex: 1;
    text-align: center;
    font-size: 18px;
    color: #333;
    font-weight: 500;
  }

  .slogan {
    text-align: center;
    margin: 15px 0;
  }

  .slogan span {
    display: inline-block;
    background: linear-gradient(90deg, #ff9800, #f57c00);
    color: white;
    padding: 5px 20px;
    border-radius: 20px;
    font-size: 14px;
  }

  .register-form {
    padding: 0 25px;
  }

  .form-row {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
  }

  .form-label {
    width: 80px;
    font-size: 14px;
    color: #333;
    flex-shrink: 0;
  }

  .form-input {
    flex: 1;
  }

  .radio-group {
    flex: 1;
  }

  .agreement {
    margin: 20px 0;
    font-size: 12px;
  }

  .agreement a {
    color: #5c9ead;
  }

  .submit-btn {
    margin-top: 30px;
  }

  :deep(.van-field) {
    background: rgba(255, 255, 255, 0.9);
    border-radius: 5px;
  }

  :deep(.van-field__control) {
    font-size: 14px;
  }
</style>