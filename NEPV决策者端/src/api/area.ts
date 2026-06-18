import request from '@/utils/request'

export interface GridProvince {
  provinceId: number
  provinceName: string
  provinceAbbr: string
  remarks: string
}

// 查询所有省份
export const listGridProvinceAll = () => {
  return request.get('/gridProvince/listGridProvinceAll')
}
