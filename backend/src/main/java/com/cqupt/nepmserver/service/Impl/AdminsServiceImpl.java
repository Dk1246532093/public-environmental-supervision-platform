package com.cqupt.nepmserver.service.Impl;
import com.cqupt.nepmserver.service.IAdminsService;//业务接口
import com.cqupt.nepmserver.mapper.AdminsMapper; //数据访问接口
import com.cqupt.nepmserver.po.Admins; //实体类
import org.springframework.beans.factory.annotation.Autowired; //自动注入
import org.springframework.stereotype.Service; //标注为服务组件

@Service //标注为服务组件
public class AdminsServiceImpl implements IAdminsService {
    @Autowired //自动注入mapper接口对象
    private AdminsMapper adminsMapper; //数据访问接口对象

    @Override //重写方法
    // 实现管理员登录验证方法
    public Admins getAdminsByCodeByPass(Admins admins) {
        return adminsMapper.getAdminsByCodeByPass(admins);
    }
}
