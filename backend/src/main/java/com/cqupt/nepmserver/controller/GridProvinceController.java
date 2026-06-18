package com.cqupt.nepmserver.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqupt.nepmserver.service.IGridProvinceService;
import com.cqupt.nepmserver.po.GridProvince;
import java.util.List;
@RestController
@RequestMapping("/gridProvince")
public class GridProvinceController {
    @Autowired
    private IGridProvinceService gridProvinceService;;
    //查询全省信息接口
    @RequestMapping("/listGridProvinceAll")
    public List<GridProvince> listGridCityProvinceAll() {
        return gridProvinceService.listGridCityProvinceAll();
    }
}
