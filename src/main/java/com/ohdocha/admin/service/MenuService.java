package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface MenuService {

    void getMenuList(ServiceMessage serviceMessage);

    void getMainList(ServiceMessage serviceMessage);

    void getMainImg(ServiceMessage serviceMessage);

    void insertMain(ServiceMessage serviceMessage);

    void uploadMainImage(ServiceMessage serviceMessage);

    void deleteMainImg(ServiceMessage serviceMessage);

    void getQuestionList(ServiceMessage serviceMessage);

    void getQuestionDetail(ServiceMessage serviceMessage);

    void getNoticeList(ServiceMessage serviceMessage);

    void getNoticeDetail(ServiceMessage serviceMessage);

    void updateAnswer(ServiceMessage serviceMessage);

    void insertNotice(ServiceMessage serviceMessage);

    void deleteNotice(ServiceMessage serviceMessage);

    void uploadQuestionImage(ServiceMessage serviceMessage);

    void uploadNoticeImage(ServiceMessage serviceMessage);

    void getEventList(ServiceMessage serviceMessage);

    void insertEvent(ServiceMessage serviceMessage);

    void uploadEventImage(ServiceMessage serviceMessage);

    void getEventDetail(ServiceMessage serviceMessage);

    void deleteEvent(ServiceMessage serviceMessage);

    void uploadEventListImage(ServiceMessage serviceMessage);

    void uploadEventBannerImage(ServiceMessage serviceMessage);
}
