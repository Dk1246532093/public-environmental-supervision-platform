package com.cqupt.nepmserver.service;
import java.util.List;
import com.cqupt.nepmserver.dto.AfPageRequestDto;
import com.cqupt.nepmserver.dto.PageResponseDto;
import com.cqupt.nepmserver.po.AqiFeedback;
public interface IAqiFeedbackService {
    /// neps工程//
    public int saveAqiFeedback(AqiFeedback aqiFeedback);
    public List<AqiFeedback> listAqiFeedbackByTelId(String telId);
    //nepm工程//
    public PageResponseDto<AqiFeedback> listAqiFeedbackPage(AfPageRequestDto afPageRequestDto);
    public AqiFeedback getAqiFeedbackById(Integer afId);
    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback);
    //nepg工程//
    public List<AqiFeedback> listAqiFeedbackByGmId(AqiFeedback aqiFeedback);
    public int updateAqiFeedbackState(AqiFeedback aqiFeedback);
}
