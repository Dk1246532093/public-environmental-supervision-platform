import request from '@/utils/request'

export interface AqiFeedback {
  afId?: number
  telId: string
  provinceId: number
  cityId: number
  address: string
  information: string
  estimatedGrade: number
  afDate: string
  afTime: string
  gmId?: number
  assignDate?: string
  assignTime?: string
  state?: number
  remarks?: string
}

// 提交反馈
export const saveAqiFeedback = (data: AqiFeedback) => {
  return request.post('/aqiFeedback/saveAqiFeedback', data)
}

// 根据手机号查询反馈列表
export const listAqiFeedbackByTelId = (telId: string) => {
  return request.post('/aqiFeedback/listAqiFeedbackByTelId', { telId })
}
