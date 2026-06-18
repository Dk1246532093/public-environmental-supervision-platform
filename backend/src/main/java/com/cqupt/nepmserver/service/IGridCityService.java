package com.cqupt.nepmserver.service;
import com.cqupt.nepmserver.po.GridCity;
import java.util.List;
public interface IGridCityService {
    public List<GridCity> listGridCityByProvinceId(Integer ProvinceId);
}
