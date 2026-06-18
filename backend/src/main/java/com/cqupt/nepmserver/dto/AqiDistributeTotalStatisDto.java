package com.cqupt.nepmserver.dto;
import lombok.Data;

// AQI等级分布统计（饼图）
@Data
public class AqiDistributeTotalStatisDto {
    private Integer aqiId;
    private String aqiExplain;
    private Integer total;
}
