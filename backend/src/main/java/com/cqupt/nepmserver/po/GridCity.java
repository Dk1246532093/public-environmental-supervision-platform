package com.cqupt.nepmserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grid_city")
public class GridCity {
    @TableId
    private Integer cityId; //城市id
    private String cityName; //城市名称
    private Integer provinceId; //省份id
    private String remarks; //备注信息
}