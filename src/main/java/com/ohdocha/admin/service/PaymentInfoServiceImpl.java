package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateRequest;
import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateResponse;
import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoRequest;
import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoResponse;
import com.ohdocha.admin.mapper.DochaAdminCalculateMapper;
import com.ohdocha.admin.mapper.DochaAdminPaymentInfoMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentInfoServiceImpl extends ServiceExtension implements PaymentInfoService {

    private DochaAdminPaymentInfoMapper paymentInfoMapper;
    private DochaAdminCalculateMapper calculateMapper;

    @Override
    public void paymentInfoList(ServiceMessage message) {
        DochaAdminPaymentInfoRequest reqParam = message.getObject("reqParam", DochaAdminPaymentInfoRequest.class);

        List<DochaAdminPaymentInfoResponse> paymentInfoList = paymentInfoMapper.selectPaymentInfoList(reqParam);
        if (paymentInfoList.size() != 0 ){
            message.addData("code", 200);
            message.addData("paymentInfoList", paymentInfoList);
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "정기결제 내역이 존재하지 않습니다.");
        }

    }

    @Override
    public void calculateDateReserveList(ServiceMessage message) {
        DochaAdminCalculateRequest calculateRequest = message.getObject("calculateRequest", DochaAdminCalculateRequest.class);

        List<DochaAdminCalculateResponse> calculateResponseList = calculateMapper.selectCalculateDateReserveList(calculateRequest);

        message.addData("calculateResponseList", calculateResponseList);
    }

    @Override
    public void calculateDateReserveInfo(ServiceMessage message) {
        DochaAdminCalculateRequest calculateRequest = message.getObject("calculateRequest", DochaAdminCalculateRequest.class);

        List<DochaAdminCalculateResponse> calculateResponseList = calculateMapper.selectCalculateDateReserveInfo(calculateRequest);

        message.addData("calculateResponseList", calculateResponseList);

    }

    @Override
    public void calculateDateAndRtIdxReserveInfo(ServiceMessage message) {
        DochaAdminCalculateRequest calculateRequest = message.getObject("calculateRequest", DochaAdminCalculateRequest.class);

        List<DochaAdminCalculateResponse> calculateRentCompanyResponseList = calculateMapper.selectCalculateDateRentCompanyReserveInfo(calculateRequest);

        message.addData("result", calculateRentCompanyResponseList);
    }
}
