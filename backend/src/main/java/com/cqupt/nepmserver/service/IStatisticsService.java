package com.cqupt.nepmserver.service;
import java.util.List;
import com.cqupt.nepmserver.dto.AqiTrendTotalStatisDto;
import com.cqupt.nepmserver.dto.AqiDistributeTotalStatisDto;
import com.cqupt.nepmserver.dto.StatisticsPageRequestDto;
import com.cqupt.nepmserver.dto.ProvinceItemTotalStatisDto;
import com.cqupt.nepmserver.dto.PageResponseDto;
import com.cqupt.nepmserver.po.Statistics;

public interface IStatisticsService {
    /********************* nepg工程 *********************/
    public int saveStatistics(Statistics statistics);

    /********************* nepg工程 *********************/

    /********************* nepm工程 *********************/
    public PageResponseDto<Statistics> listStatisticsPage(StatisticsPageRequestDto statisticsPageRequestDto);
    public Statistics getStatisticsById(Integer id);

    /********************* nepm工程 *********************/

    /********************* nepv工程 *********************/
    public List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis();
    public List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis();
    public List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis();

    public long getAqiCount();
    public long getAqiGoodCount();
    public String getProvinceCoverage();
    public String getCityCoverage();

}
