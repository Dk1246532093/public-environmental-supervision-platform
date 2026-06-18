import request from '@/utils/request'

// 查询所有AQI等级
export const listAqiAll = () => {
  return request.get('/aqi/listAqiAll')
}
