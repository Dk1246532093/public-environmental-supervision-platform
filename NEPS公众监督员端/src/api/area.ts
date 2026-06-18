import request from '@/utils/request'

export interface GridProvince {
  provinceId: number
  provinceName: string
  provinceAbbr: string
  remarks: string
}

export interface GridCity {
  cityId: number
  cityName: string
  provinceId: number
  remarks: string
}

// 查询所有省份
export const listGridProvinceAll = () => {
  return request.get('/gridProvince/listGridProvinceAll')
}

// 根据省份ID查询城市
export const listGridCityByProvinceId = (provinceId: number) => {
  return request.post('/gridCity/listGridCityByProvinceId', { provinceId })
}
