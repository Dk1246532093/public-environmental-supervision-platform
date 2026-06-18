package com.cqupt.nepmserver.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqupt.nepmserver.service.ISupervisorService;
import com.cqupt.nepmserver.po.Supervisor;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {
    @Autowired
    private ISupervisorService supervisorService;

    // 登录接口
    @RequestMapping("/getSupervisorByIdByPass")
    public Supervisor getSupervisorByIdByPass(@RequestBody Supervisor supervisor) {
        return supervisorService.getSupervisorByIdByPass(supervisor);
    }

    // 注册接口
    @RequestMapping("/saveSupervisor")
    public int saveSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.saveSupervisor(supervisor);
    }
    //验证手机号是否被注册
    @RequestMapping("/getSupervisorById")
    public Supervisor getSupervisorById(@RequestBody Supervisor supervisor) {
        return supervisorService.getSupervisorById(supervisor.getTelId());
    }
}
