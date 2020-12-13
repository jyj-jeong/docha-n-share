package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface MenuService {

    void getMenuList(ServiceMessage serviceMessage);

    void getQuestionList(ServiceMessage serviceMessage);

    void getQuestionDetail(ServiceMessage serviceMessage);

    void getNoticeList(ServiceMessage serviceMessage);

    void getNoticeDetail(ServiceMessage serviceMessage);

    void updateAnswer(ServiceMessage serviceMessage);

    void insertNotice(ServiceMessage serviceMessage);

    void deleteNotice(ServiceMessage serviceMessage);

    void uploadQuestionImage(ServiceMessage serviceMessage);

    void uploadNoticeImage(ServiceMessage serviceMessage);
}
