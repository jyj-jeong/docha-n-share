package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface PaymentInfoService {

    void paymentInfoList(ServiceMessage message);           // 정기결제

    void calculateDateReserveList(ServiceMessage message);  // 정산

}
