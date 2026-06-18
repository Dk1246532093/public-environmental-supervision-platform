package com.cqupt.nepmserver.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.cqupt.nepmserver.service.IGridMemberService;
import com.cqupt.nepmserver.po.GridMember;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@RestController
@RequestMapping("/gridMember")
public class GridMemberController {
    @Autowired
    private IGridMemberService gridMemberService;
    //nepm工程--》管理员端按照省份查询网格员的列表（方便后期分配）
    @RequestMapping("/listGridMemberByProvinceId")
    public List<GridMember> listGridMemberByProvinceId(@RequestBody GridMember gridMember){
        return gridMemberService.listGridMemberByProvinceId(gridMember);
    }
    // nepg网格员端工程--》网格员登录验证（工号+密码）
    @RequestMapping("/getGirdMemberByCodeByPass")
    public GridMember getGirdMemberByCodeByPass(@RequestBody GridMember gridMember){
        return gridMemberService.getGirdMemberByCodeByPass(gridMember);
    }
}
