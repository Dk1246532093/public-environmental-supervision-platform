package com.cqupt.nepmserver.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("grid_province")
public class GridProvince {
    @TableId
    private Integer provinceId; //省份id
    private String provinceName; //省份名称
    private String provinceAbbr;//省份简称
    private String remarks; //备注信息
}