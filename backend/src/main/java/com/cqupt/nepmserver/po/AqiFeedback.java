package com.cqupt.nepmserver.po;
import com.baomidou.mybatisplus.annotation.TableName; //导入mybatisplus注解
import com.baomidou.mybatisplus.annotation.TableId; //导入mybatisplus注解
import com.baomidou.mybatisplus.annotation.TableField; //导入mybatisplus注解
import com.baomidou.mybatisplus.annotation.IdType; //导入mybatisplus注解
import lombok.Data; //导入lombok注解
@Data
@TableName("aqi_feedback") //指定数据库表名
public class AqiFeedback {
    @TableId(value = "af_id", type = IdType.AUTO)
    private Integer afId; //反馈id
    private String telId; //电话号码
    private Integer provinceId; //省份id
    private Integer cityId; //城市id
    private String address; //地址信息
    private String information; //反馈信息
    private Integer estimatedGrade; //估计等级
    private String afDate; //反馈日期
    private String afTime; //反馈时间
    private Integer gmId; //网格员id
    private String assignDate; //分配日期
    private String assignTime; //分配时间
    private Integer state; //状态
    private String remarks; //备注信息
    @TableField(exist = false) /// 定义的是POJO非数据库字段
    private Supervisor supervisor; //监督员信息
    @TableField(exist = false)
    private GridProvince gridProvince; //省份信息
    @TableField(exist = false)
    private GridCity gridCity; //城市信息
    @TableField(exist = false)
    private Aqi aqi; //AQI等级信息
    @TableField(exist = false)
    private GridMember gridMember; //网格员信息
}
