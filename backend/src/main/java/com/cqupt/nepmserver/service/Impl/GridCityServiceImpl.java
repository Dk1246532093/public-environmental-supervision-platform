package com.cqupt.nepmserver.service.Impl;
import com.cqupt.nepmserver.service.IGridCityService;
import com.cqupt.nepmserver.mapper.GridCityMapper;
import com.cqupt.nepmserver.po.GridCity;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
@Service
public class GridCityServiceImpl implements IGridCityService{
    @Autowired
    private GridCityMapper gridCityMapper;
    @Override
    public List<GridCity> listGridCityByProvinceId(Integer ProvinceId) {
        QueryWrapper<GridCity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", ProvinceId);
        return gridCityMapper.selectList(queryWrapper);
    }
}
