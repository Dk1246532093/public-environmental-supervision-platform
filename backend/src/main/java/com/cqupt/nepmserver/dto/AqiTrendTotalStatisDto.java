package com.cqupt.nepmserver.dto;
// AQI趋势统计（折线图）
// 请求+响应DTO
import lombok.Data;
@Data
public class AqiTrendTotalStatisDto {
    private String month;
    private Integer total;
}
