package com.cqupt.nepmserver.service.Impl;
import com.cqupt.nepmserver.service.ISupervisorService;
import com.cqupt.nepmserver.po.Supervisor;
import com.cqupt.nepmserver.mapper.SupervisorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SupervisorServiceImpl implements ISupervisorService {
    /**nepg公众监督员模块***/
    @Autowired
    private SupervisorMapper supervisorMapper;
    @Override
    public Supervisor getSupervisorByIdByPass(Supervisor supervisor) {
        return supervisorMapper.getSupervisorByIdByPass(supervisor);
    }
    @Override
    public int saveSupervisor(Supervisor supervisor) {
        return supervisorMapper.insert(supervisor);
    }
    @Override
    public Supervisor getSupervisorById(String telId) {
        return supervisorMapper.selectById(telId);
    }
}
