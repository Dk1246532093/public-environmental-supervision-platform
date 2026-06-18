import request from '@/utils/request'

export interface Aqi {
  aqiId: number
  chineseExplain: string
  aqiExplain: string
  color: string
  healthImpact: string
  takeSteps: string
  so2Min: number
  so2Max: number
  coMin: number
  coMax: number
  spmMin: number
  spmMax: number
  remarks: string
}

// 查询所有AQI等级
export const listAqiAll = () => {
  return request.get('/aqi/listAqiAll')
}
