package com.cqupt.nepmserver.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cqupt.nepmserver.dto.AqiDistributeTotalStatisDto;
import com.cqupt.nepmserver.dto.AqiTrendTotalStatisDto;
import com.cqupt.nepmserver.dto.PageResponseDto;
import com.cqupt.nepmserver.dto.ProvinceItemTotalStatisDto;
import com.cqupt.nepmserver.dto.StatisticsPageRequestDto;
import com.cqupt.nepmserver.po.Statistics;
import com.cqupt.nepmserver.service.IStatisticsService;
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private IStatisticsService statisticsService;
    /**************nepg*************/
    @RequestMapping("/saveStatistics")
    public int saveStatistics(@RequestBody Statistics statistics) {
        return statisticsService.saveStatistics(statistics);
    }
    /**************nepg*************/
    /**********************nepm工程**********************/
    @RequestMapping("/listStatisticsPage")
    public PageResponseDto<Statistics> listStatisticsPage(@RequestBody StatisticsPageRequestDto statisticsPageRequestDto) {
        return statisticsService.listStatisticsPage(statisticsPageRequestDto);
    }
    @RequestMapping("/getStatisticsById")
    public Statistics getStatisticsById(@RequestBody Statistics statistics) {
        return statisticsService.getStatisticsById(statistics.getId());
    }

    /********************* nepm工程 *********************/

    /********************* nepv工程 *********************/
    @RequestMapping("/listProvinceItemTotalStatis")
    public List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis() {
        return statisticsService.listProvinceItemTotalStatis();
    }

    @RequestMapping("/listAqiDistributeTotalStatis")
    public List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis() {
        return statisticsService.listAqiDistributeTotalStatis();
    }

    @RequestMapping("/listAqiTrendTotalStatis")
    public List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis() {
        return statisticsService.listAqiTrendTotalStatis();
    }

    @RequestMapping("/getAqiCount")
    public long getAqiCount() {
        return statisticsService.getAqiCount();
    }

    @RequestMapping("/getAqiGoodCount")
    public long getAqiGoodCount() {
        return statisticsService.getAqiGoodCount();
    }

    @RequestMapping("/getProvinceCoverage")
    public String getProvinceCoverage() {
        return statisticsService.getProvinceCoverage();
    }

    @RequestMapping("/getCityCoverage")
    public String getCityCoverage() {
        return statisticsService.getCityCoverage();
    }


}
