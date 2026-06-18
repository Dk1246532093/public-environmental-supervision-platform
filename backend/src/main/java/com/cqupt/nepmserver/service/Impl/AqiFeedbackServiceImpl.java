package com.cqupt.nepmserver.service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;//更新条件构造启
import com.cqupt.nepmserver.dto.AfPageRequestDto;
import com.cqupt.nepmserver.dto.PageResponseDto;//分页响应DTO
import com.cqupt.nepmserver.mapper.AqiFeedbackMapper;//mapper层接口

import com.cqupt.nepmserver.po.AqiFeedback;  //实体类
import com.cqupt.nepmserver.service.IAqiFeedbackService;
@Service
public class AqiFeedbackServiceImpl implements IAqiFeedbackService{
    @Autowired
    private AqiFeedbackMapper aqiFeedbackMapper;
    //************************neps工程************************//
    //保存公众提交的反馈

    /**
     * 保存公众提交的空气质量反馈
     *
     * @param aqiFeedback 空气质量反馈实体
     * @return 影响行数
     */
    @Override
    public int saveAqiFeedback(AqiFeedback aqiFeedback) {
        //直接调用basemappper的insert方法
        return aqiFeedbackMapper.insert(aqiFeedback);
    }
    /**
     * 根据用户电话ID查询空气质量反馈列表
     *
     * @param telId 用户电话ID
     * @return 空气质量反馈列表
     */
    @Override
    public List<AqiFeedback> listAqiFeedbackByTelId(String telId) {
        return aqiFeedbackMapper.listAqiFeedbackByTelId(telId);
    }
    //*****************************nepm工程*****************//
    //分页查询---核心业务逻辑
    @Override
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto request) {
        //调用mapper层的方法，传入参数
        PageResponseDto<AqiFeedback> pageResponse = new PageResponseDto<>();
        //(1)获取总记录数
        int totalRow = aqiFeedbackMapper.getAqiFeedbackCount(request);
        pageResponse.setTotalRow(totalRow);
        //(2)如果没有数据，直接返回空结果
        if(totalRow==0){
            return pageResponse;
        }
        //（3）计算总页数
        int totalPageNum = 0;
        if(totalRow%request.getMaxPageNum()==0){
            totalPageNum = totalRow/request.getMaxPageNum();
        }else{
            totalPageNum = totalRow/request.getMaxPageNum()+1;
        }
        pageResponse.setTotalPageNum(totalPageNum);
        //（4）计算上一页，下一页
        int pageNum = request.getPageNum();
        pageResponse.setPreNum(pageNum);
        pageResponse.setNextNum(pageNum);
        if(pageNum>1){
            pageResponse.setPreNum(pageNum-1);
        }
        if(pageNum<totalPageNum){
            pageResponse.setNextNum(pageNum+1);
        }
        //计算起始位置，（(pageNum-1)*每页条数）
        request.setBeginNum((pageNum-1)*request.getMaxPageNum());
        //（5）查询业务数据
        List<AqiFeedback> list = aqiFeedbackMapper.listAqiFeedbackPage(request);
        //（6）封装数据返回
        pageResponse.setPageNum(pageNum);
        pageResponse.setMaxPageNum(request.getMaxPageNum());
        pageResponse.setList(list);
        return pageResponse;
    }
    //根据ID查询详情
    @Override
    public AqiFeedback getAqiFeedbackById(Integer afId) {
        return aqiFeedbackMapper.getAqiFeedbackById(afId);
    }
    //分配反馈给网络员（更新gm_id和分配时间）
    @Override
    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        //upddatwrapper:mybatis-plus的更新条件构造器
        UpdateWrapper<AqiFeedback> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("gm_id",aqiFeedback.getGmId()); //设置更新的字段
        updateWrapper.set("assign_time",aqiFeedback.getAssignTime());
        updateWrapper.set("assign_date",aqiFeedback.getAssignDate());
        updateWrapper.set("state",aqiFeedback.getState());
        updateWrapper.eq("af_id",aqiFeedback.getAfId()); //设置更新的条件
        return aqiFeedbackMapper.update(aqiFeedback,updateWrapper);
    }
    //********************nepg工程*****/
    //根据网格员ID查询待办反馈
    @Override
    public List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.listAqiFeedbackByGmId(aqiFeedback);
    }
    //更新反馈状态
    @Override
    public int updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        UpdateWrapper<AqiFeedback> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state",aqiFeedback.getState());
        updateWrapper.eq("af_id",aqiFeedback.getAfId());
        return aqiFeedbackMapper.update(null,updateWrapper);
    }

}
