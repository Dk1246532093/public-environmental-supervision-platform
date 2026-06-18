package com.cqupt.nepmserver.dto;
//各省份多个项污染物统计（柱状图）
import lombok.Data;
@Data
public class ProvinceItemTotalStatisDto {
    private Integer provinceId;//省份ID
    private String provinceName;//省份名称
    private String provinceAbbr;//省份简称
    private Integer so2Total;//二氧化硫总量
    private Integer coTotal;//一氧化碳总量
    private Integer spmTotal;//颗粒物总量
    private Integer aqiTotal;//AQI总量
}
