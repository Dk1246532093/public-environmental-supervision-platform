import request from '@/utils/request'

export interface AqiFeedback {
  afId?: number
  telId?: string
  provinceId?: number
  cityId?: number
  address?: string
  information?: string
  estimatedGrade?: number
  afDate?: string
  afTime?: string
  gmId?: number
  assignDate?: string
  assignTime?: string
  state?: number
  remarks?: string
}

// 根据网格员ID查询反馈列表
export const listAqiFeedbackByGmId = (gmId: number, state = 1) => {
  return request.post('/aqiFeedback/listAqiFeedbackByGmId', { gmId, state })
}

// 根据反馈ID查询详情
export const getAqiFeedbackById = (afId: number) => {
  return request.post('/aqiFeedback/getAqiFeedbackById', { afId })
}

// 更新反馈状态
export const updateAqiFeedbackState = (data: { afId: number; state: number }) => {
  return request.post('/aqiFeedback/updateAqiFeedbackState', data)
}
