import request from '@/utils/request'

export interface Statistics {
  provinceId?: number
  cityId?: number
  address?: string
  so2Value?: number
  so2Level?: number
  coValue?: number
  coLevel?: number
  spmValue?: number
  spmLevel?: number
  aqiId?: number
  confirmDate?: string
  confirmTime?: string
  gmId?: number
  fdId?: string
  information?: string
  remarks?: string
}

// 保存统计信息
export const saveStatistics = (data: Statistics) => {
  return request.post('/statistics/saveStatistics', data)
}
