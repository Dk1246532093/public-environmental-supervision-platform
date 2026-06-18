package com.cqupt.nepmserver.service.Impl;
import java.util.List;
import com.cqupt.nepmserver.service.IGridProvinceService;
import com.cqupt.nepmserver.po.GridProvince;
import org.springframework.stereotype.Service;
import com.cqupt.nepmserver.mapper.GridProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class GridProvinceServiceImpl implements IGridProvinceService {
    @Autowired
    private GridProvinceMapper gridProvinceMapper;

    @Override
    public List<GridProvince> listGridCityProvinceAll() {
        return gridProvinceMapper.selectList(null);
    }
}
