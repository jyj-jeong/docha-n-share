package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface PaymentInfoService {

    void paymentInfoList(ServiceMessage message);           // 정기결제

    void calculateDateReserveList(ServiceMessage message);  // 정산

    void calculateDateReserveInfo(ServiceMessage message);  // 정산 상세

    void calculateDateAndRtIdxReserveInfo(ServiceMessage message);  // 정산 상세

    // 환불

    void reservationRefund(ServiceMessage message) throws Exception;  // 정산 상세


}
