package com.cqupt.nepmserver.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.cqupt.nepmserver.po.Statistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.nepmserver.dto.StatisticsPageRequestDto;
import com.cqupt.nepmserver.dto.AqiTrendTotalStatisDto;
import com.cqupt.nepmserver.dto.ProvinceItemTotalStatisDto;
import com.cqupt.nepmserver.dto.AqiDistributeTotalStatisDto;

public interface StatisticsMapper extends BaseMapper<Statistics> {
    /************************nepm工程（管理员端）*************************/
    // 获取统计记录总数(用于分页)
    public int getStatisticsCount(StatisticsPageRequestDto statisticsPageRequestDto);
    // 获取统计记录分页数据
    public List<Statistics> listStatisticsPage(StatisticsPageRequestDto statisticsPageRequestDto);
    //根据ID查询单条统计记录详情
    public Statistics getStatisticsById(Integer id);
    /************************nepm工程（）*************************/
    /***************nepv工程（可视化端）*******************/
    //省分检查数据统计
    public List<ProvinceItemTotalStatisDto> listProvinceItemTotalStatis();
    //AQI指数级别分布统计
    public List<AqiDistributeTotalStatisDto> listAqiDistributeTotalStatis();
    //按照月份来统计AQI指数趋势统计
    public List<AqiTrendTotalStatisDto> listAqiTrendTotalStatis(@Param("monthList") List<String> monthList);
    /****************nepv工程*******************/

}
