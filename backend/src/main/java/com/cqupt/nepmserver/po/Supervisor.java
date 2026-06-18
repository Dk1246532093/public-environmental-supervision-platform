package com.cqupt.nepmserver.po;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@TableName("supervisor")
public class Supervisor {
    @TableId(value = "tel_id")
    private String telId; //电话号码
    private String password; //
    private String realName; //真实姓名
    private String birthday; //出生日期
    private Integer sex;
    private String remarks; //备注信息
}