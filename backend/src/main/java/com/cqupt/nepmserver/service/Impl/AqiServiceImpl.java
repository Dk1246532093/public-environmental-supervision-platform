package com.cqupt.nepmserver.service.Impl;

import java.util.List;
import com.cqupt.nepmserver.mapper.AqiMapper;
import com.cqupt.nepmserver.po.Aqi;
import com.cqupt.nepmserver.service.IAqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
@Service

public class AqiServiceImpl implements IAqiService {
    @Autowired
    private AqiMapper aqiMapper;

    @Override
    public List<Aqi> listAqiAll() {
        QueryWrapper<Aqi> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("aqi_id");
        return aqiMapper.selectList(queryWrapper);
    }
}
