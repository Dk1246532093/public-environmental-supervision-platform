package com.cqupt.nepmserver.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.cqupt.nepmserver.service.IAqiService;
import java.util.List;
import com.cqupt.nepmserver.po.Aqi;
@RestController
@RequestMapping("/aqi")
public class AqiController {
    @Autowired
    private IAqiService aqiService;
    @RequestMapping("/listAqiAll")
    public List<Aqi> listAqiAll() {
        return aqiService.listAqiAll();
    }
}
