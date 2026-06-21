import request from '@/utils/request'

// 省分组分项统计
export const listProvinceItemTotalStatis = () => {
  return request.post('/statistics/listProvinceItemTotalStatis', {})
}

// AQI等级分布统计
export const listAqiDistributeTotalStatis = () => {
  return request.post('/statistics/listAqiDistributeTotalStatis', {})
}

// AQI趋势统计
export const listAqiTrendTotalStatis = () => {
  return request.post('/statistics/listAqiTrendTotalStatis', {})
}

// 获取AQI检测总数
export const getAqiCount = () => {
  return request.post('/statistics/getAqiCount', {})
}

// 获取AQI良好数量
export const getAqiGoodCount = () => {
  return request.post('/statistics/getAqiGoodCount', {})
}

// 获取省份覆盖率
export const getProvinceCoverage = () => {
  return request.post('/statistics/getProvinceCoverage', {})
}

// 获取城市覆盖率
export const getCityCoverage = () => {
  return request.post('/statistics/getCityCoverage', {})
}
