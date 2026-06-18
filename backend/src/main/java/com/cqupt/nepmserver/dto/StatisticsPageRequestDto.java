package com.cqupt.nepmserver.dto;
import lombok.Data;
// 统计记录分页查询
@Data
public class StatisticsPageRequestDto {
    //业务查询条件的请求参数
    private Integer provinceId;
    private Integer cityId;
    private String confirmDate;
    //分页请求的参数
    private Integer pageNum;//当前页
    private Integer maxPageNum;//每页最大数
    private Integer beginNum;//开始查询记录数
}
