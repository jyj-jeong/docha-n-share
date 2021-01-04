package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.menu.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminMenuMapper {

    public List<DochaAdminMenuResponse> selectMenuInfoList(DochaAdminMenuRequest reqParam);

    public int insertMenuTemplate(DochaAdminMenuTemplateRequest reqParam);

    public List<DochaAdminMenuTemplateResponse> selectMenuTemplateList(DochaAdminMenuTemplateRequest reqParam);

    public List<DochaAdminMenuResponse> selectMenuInfoAll();


    List<DochaAdminMainImgResponse> selectMainImgList(DochaAdminMainImgRequest mainImgRequest);

    int insertMainImg(DochaAdminMainImgRequest mainImgRequest);

    int updateMainImg(DochaAdminMainImgRequest mainImgRequest);

    int deleteMainImg(DochaAdminMainImgRequest mainImgRequest);

    List<DochaAdminQuestionResponse> selectQuestionList(DochaAdminQuestionRequest questionRequest);

    List<DochaAdminNoticeResponse> selectNoticeList(DochaAdminNoticeRequest noticeRequest);

    List<DochaAdminEventResponse> selectEventList(DochaAdminEventRequest questionRequest);

    int updateAnswer(DochaAdminQuestionRequest questionRequest);

    int insertNotice(DochaAdminNoticeRequest noticeRequest);

    int updateNotice(DochaAdminNoticeRequest noticeRequest);

    int deleteNotice(DochaAdminNoticeRequest noticeRequest);

    int insertEvent(DochaAdminEventRequest eventRequest);

    int updateEvent(DochaAdminEventRequest eventRequest);

    int deleteEvent(DochaAdminEventRequest eventRequest);

    List<DochaAdminFAQResponse> selectFAQList(DochaAdminFAQRequest faqRequest);

    int insertFAQ(DochaAdminFAQRequest faqRequest);

    int updateFAQ(DochaAdminFAQRequest faqRequest);

    int deleteFAQ(DochaAdminFAQRequest faqRequest);
}
