<template>
  <div class="home-page">
    <!-- 头部 -->
    <div class="main-header">
      <h1>东软环保公众监督平台</h1>
      <div class="slogan-banner">绿水青山就是金山银山</div>
      <div class="history-link" @click="goToHistory">📋 历史记录</div>
    </div>

    <!-- 反馈表单 -->
    <div class="form-section">
      <div class="form-item">
        <label>所在省</label>
        <div class="select-wrapper">
          <van-field v-model="form.province" placeholder="请选择省份" readonly @click="showProvincePicker = true" />
        </div>
      </div>

      <div class="form-item">
        <label>所在市</label>
        <div class="select-wrapper">
          <van-field v-model="form.city" placeholder="请选择城市" readonly @click="showCityPicker = true" />
        </div>
      </div>

      <div class="form-item">
        <label>详细地址</label>
        <van-field v-model="form.address" placeholder="请输入详细地址" type="textarea" rows="2" />
      </div>

      <div class="form-item">
        <label>预估AQI等级</label>
        <div class="aqi-selector" @click="showAqiPicker = true">
          <span v-if="form.aqiLevel" :class="['aqi-tag', `level-${form.aqiLevel}`]">
            {{ getAqiLabel(form.aqiLevel) }}
          </span>
          <span v-else class="placeholder">请选择AQI等级</span>
          <van-icon name="arrow" class="arrow-icon" />
        </div>
      </div>

      <div class="form-item">
        <label>反馈描述</label>
        <van-field v-model="form.description" placeholder="请描述您观察到的环境问题..." type="textarea" rows="4" />
      </div>

      <div class="form-item">
        <label>上传图片</label>
        <van-uploader v-model="form.images" multiple :max-count="6" upload-text="上传图片" />
      </div>

      <div class="submit-section">
        <van-button round block type="primary" color="#5c9ead" @click="submitFeedback">
          提交反馈
        </van-button>
      </div>
    </div>

    <!-- 省份选择器 -->
    <van-popup v-model:show="showProvincePicker" position="bottom">
      <van-picker :columns="provinceColumns" @confirm="onProvinceConfirm" @cancel="showProvincePicker = false"
        title="选择省份" />
    </van-popup>

    <!-- 城市选择器 -->
    <van-popup v-model:show="showCityPicker" position="bottom">
      <van-picker :columns="cityColumns" @confirm="onCityConfirm" @cancel="showCityPicker = false" title="选择城市" />
    </van-popup>

    <!-- AQI等级选择器 -->
    <van-popup v-model:show="showAqiPicker" position="bottom">
      <div class="aqi-picker-content">
        <div class="aqi-picker-header">
          <span>选择AQI等级</span>
          <van-icon name="cross" @click="showAqiPicker = false" />
        </div>
        <div class="aqi-list">
          <div v-for="level in aqiLevels" :key="level.value" :class="['aqi-item', `level-${level.value}`]"
            @click="selectAqi(level.value)">
            <span class="aqi-num">{{ level.value }}</span>
            <span class="aqi-name">{{ level.label }}</span>
            <span class="aqi-range">{{ level.range }}</span>
          </div>
        </div>
      </div>
    </van-popup>
  </div>
</template>

<script setup lang="ts">
  import { ref, computed, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { showToast } from 'vant'
  import { useUserStore } from '@/stores/user'
  import { listGridProvinceAll, listGridCityByProvinceId } from '@/api/area'
  import { listAqiAll } from '@/api/aqi'
  import { saveAqiFeedback } from '@/api/feedback'
  import type { GridProvince, GridCity } from '@/api/area'

  const userStore = useUserStore()
  const router = useRouter()

  const form = ref({
    province: '',
    provinceId: 0,
    city: '',
    cityId: 0,
    address: '',
    aqiLevel: null as number | null,
    description: '',
    images: []
  })

  const showProvincePicker = ref(false)
  const showCityPicker = ref(false)
  const showAqiPicker = ref(false)

  const provinceList = ref < GridProvince[] > ([])
  const cityList = ref < GridCity[] > ([])
  const aqiLevelList = ref < any[] > ([])

  const provinceColumns = computed(() => provinceList.value.map(item => ({ text: item.provinceName, value: item.provinceId })))
  const cityColumns = computed(() => cityList.value.map(item => ({ text: item.cityName, value: item.cityId })))

  const aqiLevels = computed(() => {
    if (aqiLevelList.value.length > 0) {
      return aqiLevelList.value.map((item: any) => ({
        value: item.aqiId,
        label: item.aqiExplain,
        range: `${item.spmMin}-${item.spmMax}`
      }))
    }
    return [
      { value: 1, label: '优', range: '0-50' },
      { value: 2, label: '良', range: '51-100' },
      { value: 3, label: '轻度污染', range: '101-150' },
      { value: 4, label: '中度污染', range: '151-200' },
      { value: 5, label: '重度污染', range: '201-300' },
      { value: 6, label: '严重污染', range: '>300' }
    ]
  })

  const getAqiLabel = (level: number) => {
    const item = aqiLevels.value.find((l: any) => l.value === level)
    return item ? item.label : ''
  }

  const loadProvinces = async () => {
    try {
      const res: any = await listGridProvinceAll()
      provinceList.value = res || []
    } catch (error) {
      showToast('加载省份失败')
    }
  }

  const loadCities = async (provinceId: number) => {
    try {
      const res: any = await listGridCityByProvinceId(provinceId)
      cityList.value = res || []
    } catch (error) {
      showToast('加载城市失败')
    }
  }

  const loadAqiLevels = async () => {
    try {
      const res: any = await listAqiAll()
      aqiLevelList.value = res || []
    } catch (error) {
      showToast('加载AQI等级失败')
    }
  }

  const onProvinceConfirm = ({ selectedOptions }: any) => {
    const option = selectedOptions[0]
    if (option) {
      form.value.province = option.text
      form.value.provinceId = option.value
      form.value.city = ''
      form.value.cityId = 0
      loadCities(option.value)
    }
    showProvincePicker.value = false
  }

  const onCityConfirm = ({ selectedOptions }: any) => {
    const option = selectedOptions[0]
    if (option) {
      form.value.city = option.text
      form.value.cityId = option.value
    }
    showCityPicker.value = false
  }

  const selectAqi = (level: number) => {
    form.value.aqiLevel = level
    showAqiPicker.value = false
  }

  const goToHistory = () => {
    router.push('/history')
  }

  const submitFeedback = async () => {
    if (!form.value.provinceId || !form.value.cityId) {
      showToast('请选择所在省市')
      return
    }
    if (!form.value.address) {
      showToast('请输入详细地址')
      return
    }
    if (!form.value.aqiLevel) {
      showToast('请选择AQI等级')
      return
    }
    if (!form.value.description) {
      showToast('请输入反馈描述')
      return
    }
    try {
      const now = new Date()
      const res: any = await saveAqiFeedback({
        telId: userStore.userInfo?.phone || '',
        provinceId: form.value.provinceId,
        cityId: form.value.cityId,
        address: form.value.address,
        information: form.value.description,
        estimatedGrade: form.value.aqiLevel,
        afDate: now.toISOString().split('T')[0],
        afTime: now.toTimeString().split(' ')[0]
      })
      if (res === 1) {
        showToast('提交成功')
        form.value.address = ''
        form.value.description = ''
        form.value.aqiLevel = null
      } else {
        showToast('提交失败')
      }
    } catch (error) {
      showToast('提交失败')
    }
  }

  onMounted(() => {
    loadProvinces()
    loadAqiLevels()
  })
</script>

<style scoped>
  .home-page {
    min-height: 100vh;
    background: linear-gradient(180deg, #c8e6c9 0%, #e8f5e9 30%, #b2dfdb 100%);
  }

  .main-header {
    text-align: center;
    padding: 15px;
    background: rgba(255, 255, 255, 0.3);
    position: relative;
  }

  .history-link {
    position: absolute;
    right: 15px;
    top: 15px;
    font-size: 13px;
    color: #2e7d32;
    background: rgba(255, 255, 255, 0.7);
    padding: 4px 10px;
    border-radius: 12px;
    cursor: pointer;
  }

  .main-header h1 {
    font-size: 18px;
    color: #2e7d32;
    font-weight: bold;
  }

  .slogan-banner {
    background: linear-gradient(90deg, #ff9800, #f57c00);
    color: white;
    padding: 6px 20px;
    border-radius: 20px;
    display: inline-block;
    font-size: 13px;
    margin-top: 10px;
  }

  .form-section {
    padding: 20px;
  }

  .form-item {
    margin-bottom: 20px;
  }

  .form-item label {
    display: block;
    font-size: 14px;
    color: #333;
    margin-bottom: 8px;
    font-weight: 500;
  }

  .select-wrapper {
    position: relative;
  }

  .aqi-selector {
    background: white;
    padding: 12px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    cursor: pointer;
  }

  .aqi-tag {
    padding: 4px 12px;
    border-radius: 4px;
    color: white;
    font-size: 14px;
  }

  .level-1 {
    background: #4caf50;
  }

  .level-2 {
    background: #8bc34a;
  }

  .level-3 {
    background: #ffeb3b;
    color: #333;
  }

  .level-4 {
    background: #ff9800;
  }

  .level-5 {
    background: #f44336;
  }

  .level-6 {
    background: #9c27b0;
  }

  .placeholder {
    color: #999;
    font-size: 14px;
  }

  .arrow-icon {
    color: #999;
  }

  .submit-section {
    margin-top: 30px;
  }

  .aqi-picker-content {
    background: white;
    border-radius: 12px 12px 0 0;
  }

  .aqi-picker-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px;
    border-bottom: 1px solid #eee;
    font-size: 16px;
    font-weight: 500;
  }

  .aqi-list {
    padding: 10px 20px 30px;
  }

  .aqi-item {
    display: flex;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #f5f5f5;
  }

  .aqi-num {
    width: 24px;
    height: 24px;
    border-radius: 4px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 12px;
    color: white;
    margin-right: 12px;
  }

  .aqi-name {
    flex: 1;
    font-size: 14px;
    color: #333;
  }

  .aqi-range {
    font-size: 12px;
    color: #999;
  }

  :deep(.van-field) {
    background: white;
    border-radius: 8px;
  }
</style>