package com.cqupt.nepmserver.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqupt.nepmserver.service.IGridCityService;
import com.cqupt.nepmserver.po.GridCity;
import java.util.List;
@RestController
@RequestMapping("/gridCity")
public class GridCityController {
    @Autowired
    private IGridCityService gridCityService;
    @RequestMapping("/listGridCityByProvinceId")
    public List<GridCity> listGridCityByProvinceId(@RequestBody
                                                   GridCity gridCity) {
        return gridCityService.listGridCityByProvinceId(gridCity.getProvinceId());
    }
}
