package com.cqupt.nepmserver.service;
import com.cqupt.nepmserver.po.Admins;
public interface IAdminsService {
    // 管理员登录验证方法
    public Admins getAdminsByCodeByPass(Admins admins);
}
