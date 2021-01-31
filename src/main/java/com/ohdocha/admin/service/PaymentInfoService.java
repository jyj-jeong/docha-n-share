package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface PaymentInfoService {

    void paymentInfoList(ServiceMessage message);           // 정기결제

    void calculateDateReserveList(ServiceMessage message);  // 정산

    void calculateDateReserveInfo(ServiceMessage message);  // 정산 상세

    void calculateDateAndRtIdxReserveInfo(ServiceMessage message);  // 정산 상세

    // 환불

    void reservationRefund(ServiceMessage message) throws Exception;  // 정산 상세

    void updateRentCompanySettlementAmount(ServiceMessage serviceMessage);  // 정산 금액 입력 (회원사 단위)

    void updateSettlementAmount(ServiceMessage serviceMessage);   // 정산 금액 입력 (예약건 단위)

}
