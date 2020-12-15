package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.reserve.reserveInfoMnt.*;
import com.ohdocha.admin.mapper.DochaAdminReserveInfoMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.KeyMaker;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReserveServiceImpl extends ServiceExtension implements ReserveService{

    private final DochaAdminReserveInfoMapper reserveInfoMapper;

    @Override
    public void getReserveInfoList(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = new DochaAdminReserveInfoRequest();
        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        String userRole = loginUser.getString("userRole");
        String rtIdx = loginUser.getString("rtIdx");

        if (userRole.equals("RA")){

        }else if (userRole.equals("MA") || userRole.equals("MU")){
            reserveInfoRequest.setRtIdx(rtIdx);
        }

        List<DochaAdminReserveInfoResponse> reserveInfoResponseList = reserveInfoMapper.selectReserveInfoList(reserveInfoRequest);

        message.addData("result", reserveInfoResponseList);
    }

    @Override
    public void addReserveInfo(ServiceMessage message) {
        DochaAdminReserveInfoDetailRequest reserveInfoDetailRequest = message.getObject("reserveInfoDetailRequest", DochaAdminReserveInfoDetailRequest.class);
        String rmIdx = KeyMaker.getInsetance().getKeyDeafult("RM");
        reserveInfoDetailRequest.setRmIdx(rmIdx);

        int res;

        // 예약 체크
        List<DochaAdminReserveInfoDetailResponse> chkReserveInfo = reserveInfoMapper.reserveInfoCheck(reserveInfoDetailRequest);

        res = reserveInfoMapper.insertReserveInfo(reserveInfoDetailRequest);


        if (res == 1){
            message.addData("code", 200);
            message.addData("result", res);
        }else {
            message.addData("code", 400);
            message.addData("result", res);
        }

    }

    @Override
    public void updateReserveInfo(ServiceMessage message) {
        DochaAdminReserveInfoDetailRequest reserveInfoDetailRequest = message.getObject("reserveInfoDetailRequest", DochaAdminReserveInfoDetailRequest.class);

        int res = reserveInfoMapper.updateReserveInfo(reserveInfoDetailRequest);

        if (res == 1){
            message.addData("code", 200);
            message.addData("result", res);
        }else {
            message.addData("code", 400);
            message.addData("result", res);
        }

    }

    @Override
    public void getReserveInfo(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = message.getObject("reserveInfoRequest", DochaAdminReserveInfoRequest.class);

        List<DochaAdminReserveInfoDetailResponse> reserveInfoDetailResponseList = reserveInfoMapper.selectReserveInfo(reserveInfoRequest);
        List<DochaAdminReserveInfoDetailResponse> reservePaymentResponseList = reserveInfoMapper.selectPaymentList(reserveInfoRequest);

        message.addData("result", reserveInfoDetailResponseList);
        message.addData("paymentList", reservePaymentResponseList);
    }

    @Override
    public void selectCompanyInfo(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = message.getObject("reserveInfoRequest", DochaAdminReserveInfoRequest.class);

        DochaRentCompanyDto rentCompanyDto = reserveInfoMapper.selectCompanyInfo(reserveInfoRequest);

        message.addData("companyInfo", rentCompanyDto);
    }

    @Override
    public void selectCompanyInfoAndCarInfo(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = message.getObject("reserveInfoRequest", DochaAdminReserveInfoRequest.class);

        List<DochaCarDto> carDtoList = reserveInfoMapper.selectCompanyInfoAndCarInfo(reserveInfoRequest);

        message.addData("carList", carDtoList);
    }
}
