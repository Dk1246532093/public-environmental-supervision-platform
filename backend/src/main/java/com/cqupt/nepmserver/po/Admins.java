package com.cqupt.nepmserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName; //导入mybatisplus注解
import lombok.Data;
@Data //导入lombok注解，自动生成get和set方法
@TableName("admins") //指定数据库表名
public class Admins {
    @TableId
    private Integer adminId;//管理员id
    private String adminCode;//管理员账号
    private String password; //密码
    private String remarks; //备注信息
}
