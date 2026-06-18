package com.cqupt.nepmserver.controller;
import org.springframework.web.bind.annotation.RestController;//Rest风格控制器
import org.springframework.web.bind.annotation.RequestMapping;//请求映射注解
import org.springframework.beans.factory.annotation.Autowired;//自动注入
import org.springframework.web.bind.annotation.RequestParam;// 请求参数注解
import com.cqupt.nepmserver.service.IAdminsService; //业务接口
import com.cqupt.nepmserver.po.Admins; //实体类
@RestController //标注为控制器-->返回JSON不是视图
@RequestMapping("/admins") //请求映射路径
public class AdminsController {
    @Autowired //自动注入
    private IAdminsService adminsService; //业务接口对象

   //管理员登录接口
    @RequestMapping("/getAdminsByCodeByPass") //请求映射路径
    public Admins getAdminsByCodeByPass(@RequestParam("adminCode") String adminCode,
                                        @RequestParam("password") String password) {
        //创建查询条件对象
        Admins admins = new Admins();
        admins.setAdminCode(adminCode);
        admins.setPassword(password);
        //调用service层处理业务
        return adminsService.getAdminsByCodeByPass(admins);
    }
}

