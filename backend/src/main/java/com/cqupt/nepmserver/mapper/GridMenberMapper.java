package com.cqupt.nepmserver.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.nepmserver.po.GridMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GridMenberMapper extends BaseMapper<GridMember> {
    /*** nepg网格员端工程******/
    //网格员登录验证（工号+密码）
    @Select("select * from grid_member where gm_code=#{gmCode} and password=#{password}")
    public GridMember getGirdMemberByCodeByPass(@Param("gmCode") String gmCode, @Param("password") String password);
}
