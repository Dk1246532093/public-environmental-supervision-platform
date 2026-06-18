import request from '@/utils/request'

export interface GridMember {
  gmId?: number
  gmName?: string
  gmCode?: string
  password?: string
  provinceId?: number
  cityId?: number
  tel?: string
  state?: number
  remarks?: string
}

// 网格员登录
export const login = (data: { gmCode: string; password: string }) => {
  return request.post('/gridMember/getGirdMemberByCodeByPass', data)
}
