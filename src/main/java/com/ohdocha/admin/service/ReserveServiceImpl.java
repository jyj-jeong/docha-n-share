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
public class ReserveServiceImpl extends ServiceExtension implements ReserveService {

    private final DochaAdminReserveInfoMapper reserveInfoMapper;

    @Override
    public void getReserveInfoList(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = new DochaAdminReserveInfoRequest();
        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        String userRole = loginUser.getString("userRole");
        String rtIdx = loginUser.getString("rtIdx");

        if (userRole.equals("RA")) {

        } else if (userRole.equals("MA") || userRole.equals("MU")) {
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
        String addr1;
        String addr2;
        String addr3;
        String addr4;

        String setAddr[] = (reserveInfoDetailRequest.getDeliveryAddr()).split(" ");
        if (setAddr.length < 5) {
            addr1 = setAddr[0];
            addr2 = setAddr[0];
            addr3 = setAddr[0];
            addr4 = setAddr[0];
        } else {
            addr1 = setAddr[0];
            addr2 = setAddr[1];
            addr3 = setAddr[2];
            addr4 = setAddr[3];
        }

        int res = 0;

        // 예약 체크
        DochaMap param = new DochaMap();
        param.put("addr1", addr1);
        param.put("addr2", addr2);
        param.put("addr3", addr3);
        param.put("addr4", addr4);
        param.put("crIdx", reserveInfoDetailRequest.getCrIdx());
        param.put("rentStartDay", reserveInfoDetailRequest.getRentStartDay());
        param.put("rentStartTime", reserveInfoDetailRequest.getRentStartTime().substring(0, 2) + reserveInfoDetailRequest.getRentStartTime().substring(3, 5));
        param.put("rentEndDay", reserveInfoDetailRequest.getRentEndDay());
        param.put("rentEndTime", reserveInfoDetailRequest.getRentEndTime().substring(0, 2) + reserveInfoDetailRequest.getRentEndTime().substring(3, 5));

        List<DochaMap> chkReserveInfo = reserveInfoMapper.reserveInfoCheck(param);

        if (chkReserveInfo.size() > 0) {
            if (reserveInfoDetailRequest.getDeliveryTypeCode().equals("OF") || chkReserveInfo.get(0).get("VISIT_ABLE").toString().equals("1"))
                res = reserveInfoMapper.insertReserveInfo(reserveInfoDetailRequest);
            else if (reserveInfoDetailRequest.getDeliveryTypeCode().equals("DL") || chkReserveInfo.get(0).get("DELIVERY_ABLE").toString().equals("1"))
                res = reserveInfoMapper.insertReserveInfo(reserveInfoDetailRequest);
        }

        if (res == 1) {
            message.addData("code", 200);
            message.addData("result", res);
        } else {
            message.addData("code", 400);
            message.addData("result", res);
        }

    }

    @Override
    public void updateReserveInfo(ServiceMessage message) {
        DochaAdminReserveInfoDetailRequest reserveInfoDetailRequest = message.getObject("reserveInfoDetailRequest", DochaAdminReserveInfoDetailRequest.class);

        int res = reserveInfoMapper.updateReserveInfo(reserveInfoDetailRequest);

        if (res == 1) {
            message.addData("code", 200);
            message.addData("result", res);
        } else {
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
