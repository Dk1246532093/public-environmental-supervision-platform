package com.cqupt.nepmserver.service;
import com.cqupt.nepmserver.po.Supervisor;
public interface ISupervisorService {
    //1.监督员登录验证
    //2/监督员注册
    //3.监督员手机号查重（判定是否以及注册）
    public Supervisor getSupervisorByIdByPass(Supervisor supervisor);
    public int saveSupervisor(Supervisor supervisor);
    public Supervisor getSupervisorById(String telId);

}
