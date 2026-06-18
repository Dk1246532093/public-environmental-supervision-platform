package com.cqupt.nepmserver.dto;
import lombok.Data;


/**
 * 分页查询反馈信息的请求参数的封装
 */
@Data
public class AfPageRequestDto {
    //业务查询查询
    //查询按省份、城市、AQI等级、日期、状态来进行筛选
    private Integer provinceId;
    private Integer cityId;
    private Integer estimatedGrade;
    private String afDate;
    private Integer state;
    //分页查询参数
    private Integer pageNum; //当前页码(从1开始)
    private Integer maxPageNum; //每页显示条数（如10条/页）
    private Integer beginNum; //起始记录号
}
