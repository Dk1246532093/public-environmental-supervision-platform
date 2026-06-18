package com.cqupt.nepmserver.service;
import com.cqupt.nepmserver.po.GridMember;
import java.util.List;
public interface IGridMemberService {
    //nepm工程--》管理员端按照省份查询网格员的列表（方便后期分配）
    public List<GridMember> listGridMemberByProvinceId(GridMember gridMember);
    // nepg网格员端工程--》网格员登录验证（工号+密码）
    public GridMember getGirdMemberByCodeByPass(GridMember gridMember);
}
