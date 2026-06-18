package com.cqupt.nepmserver.dto;
import lombok.Data;
import java.util.List;

/**
 * 共通的分页查询返回对象（通用模板，可以用于所有分页接口）
 */
@Data
public class PageResponseDto<T> {
    private Integer totalRow; //总记录数
    private Integer totalPageNum; //总页数
    private Integer preNum; //上一页页码
    private Integer nextNum; //下一页页码
    private Integer pageNum; //当前页码
    private Integer maxPageNum; //每页显示条数
    private Integer beginNum; //起始记录号
    private List<T> list; //业务数据列表
}
