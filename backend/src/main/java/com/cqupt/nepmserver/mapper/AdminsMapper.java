package com.cqupt.nepmserver.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;//导入mybatis的BaseMapper
import org.apache.ibatis.annotations.Select;//导入mybatis的注解
import com.cqupt.nepmserver.po.Admins; //导入Admins实体类

public interface AdminsMapper extends BaseMapper<Admins> {
//    /******nepm工程*********/
    @Select("select * from admins where admin_code = #{adminCode} and password=#{password}")
    public Admins getAdminsByCodeByPass(Admins admins);
}
