package com.cqupt.nepmserver.mapper;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.nepmserver.po.Supervisor;
import org.apache.ibatis.annotations.Select;

public interface SupervisorMapper extends BaseMapper<Supervisor> {
    /*** neps工程****/
    //公众监督员登录
    @Select("select * from supervisor where tel_id = #{telId} and password = #{password}")
    public Supervisor getSupervisorByIdByPass(Supervisor supervisor);

}
