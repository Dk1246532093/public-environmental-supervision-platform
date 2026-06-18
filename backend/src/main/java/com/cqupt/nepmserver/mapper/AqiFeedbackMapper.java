package com.cqupt.nepmserver.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;//继承basemapper
import com.cqupt.nepmserver.po.AqiFeedback;//引入实体类
import com.cqupt.nepmserver.dto.AfPageRequestDto;//引入DTO
import java.util.List;
public interface AqiFeedbackMapper extends BaseMapper<AqiFeedback> {
    //继承之后自动又有方法
    //selectById(),insert(),updateById(),deleteById(),selectList()
    //neps,监督员；nepm管理员；nepg网格员;
    /********************neps工程**************/
    //按监督员查询（TelID）
    public List<AqiFeedback> listAqiFeedbackByTelId(String TelId);

    /********************nepg工程**************/
    //按网格员查询（ID）
    public List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback);
    /********************nepm工程**************/
    //获取总数
    public Integer getAqiFeedbackCount(AfPageRequestDto afPageRequestDto);
    //分页查询
    public List<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto afPageRequestDto);
    //根据ID查询详情
    public AqiFeedback getAqiFeedbackById(Integer afId);

}
