package com.cqupt.nepmserver.service.Impl;
import com.cqupt.nepmserver.service.IGridMemberService;
import com.cqupt.nepmserver.mapper.GridMenberMapper;
import com.cqupt.nepmserver.po.GridMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
@Service
public class GridMemberServiceImpl implements IGridMemberService {
    @Autowired
    private GridMenberMapper gridMemberMapper;

    //nepm工程--》管理员端按照省份查询网格员的列表（方便后期分配）
    @Override
    public List<GridMember> listGridMemberByProvinceId(GridMember gridMember){
        QueryWrapper<GridMember> queryWrapper = new QueryWrapper<>();
        // 查询条件：省份ID
        queryWrapper.eq("province_id",gridMember.getProvinceId());
        //设置查询条件：城市ID
        queryWrapper.eq("city_id",gridMember.getCityId());
        //设置查询条件：网格员状态
        queryWrapper.eq("state",gridMember.getState());

        return gridMemberMapper.selectList(queryWrapper);
    }
    // nepg网格员端工程--》网格员登录验证（工号+密码）
    @Override
    public GridMember getGirdMemberByCodeByPass(GridMember gridMember){
        String gmCode = gridMember.getGmCode();
        String password = gridMember.getPassword();
        return gridMemberMapper.getGirdMemberByCodeByPass(gmCode,password);
    }
}
