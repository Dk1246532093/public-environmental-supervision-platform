package com.cqupt.nepmserver.po;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName; //导入mybatisplus注解
@Data //导入lombok注解，自动生成get和set方法
@TableName("aqi") //指定数据库表名
public class Aqi {
    @TableId
    private Integer aqiId; //AQI id
    private String chineseExplain; //中文说明
    private String aqiExplain; //英文说明
    private String color; //颜色
    private String healthImpact; //健康建议
    private String takeSteps; //应对措施
    private Integer so2Min; //二氧化硫最小值
    private Integer so2Max; //二氧化硫最大值
    private Integer coMin; //一氧化碳最小值
    private Integer coMax; //一氧化碳最大值
    private Integer spmMin; //可吸入颗粒物最小值
    private Integer spmMax; //可吸入颗粒物最大值
    private String remarks; //备注信息
}
