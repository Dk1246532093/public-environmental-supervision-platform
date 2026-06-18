import request from '@/utils/request'

export interface Supervisor {
  telId: string
  password: string
  realName?: string
  birthday?: string
  sex?: number
  remarks?: string
}

// 公众监督员登录
export const login = (data: { telId: string; password: string }) => {
  return request.post('/supervisor/getSupervisorByIdByPass', data)
}

// 公众监督员注册
export const register = (data: Supervisor) => {
  return request.post('/supervisor/saveSupervisor', data)
}

// 根据手机号查询公众监督员
export const getSupervisorById = (telId: string) => {
  return request.post('/supervisor/getSupervisorById', { telId })
}
