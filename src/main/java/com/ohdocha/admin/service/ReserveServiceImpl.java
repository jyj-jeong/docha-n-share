package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.reserve.reserveInfoMnt.*;
import com.ohdocha.admin.mapper.DochaAdminReserveInfoMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.KeyMaker;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        long calHour = 0;
        DochaMap param = new DochaMap();

        String rentStartTime = reserveInfoDetailRequest.getRentStartTime().substring(0, 2) + reserveInfoDetailRequest.getRentStartTime().substring(3, 5);
        String rentEndTime = reserveInfoDetailRequest.getRentEndTime().substring(0, 2) + reserveInfoDetailRequest.getRentEndTime().substring(3, 5);
        String rentStartDateTime = reserveInfoDetailRequest.getRentStartDay().substring(0,4) + reserveInfoDetailRequest.getRentStartDay().substring(5,7)
                + reserveInfoDetailRequest.getRentStartDay().substring(8,10) + rentStartTime;
        String rentEndDateTime = reserveInfoDetailRequest.getRentEndDay().substring(0,4) + reserveInfoDetailRequest.getRentEndDay().substring(5,7)
                + reserveInfoDetailRequest.getRentEndDay().substring(8,10) + rentEndTime;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            Date FirstDate = format.parse(rentStartDateTime);
            Date SecondDate = format.parse(rentEndDateTime);

            long calDate = FirstDate.getTime() - SecondDate.getTime();

            calHour = calDate / (60 * 60 * 1000);               // 대여 시간을 hour로 환산.

            calHour = Math.abs(calHour);
            param.put("calHour", calHour);
            if (calHour < 720)
                param.put("longTermYn", "ST");
            else
                param.put("longTermYn", "LT");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String setAddr[] = (reserveInfoDetailRequest.getDeliveryAddr()).split(" ");

        if ( setAddr.length == 1 ) {
            addr1 = setAddr[0];
            addr2 = "";
            addr3 = "";
            addr4 = "";
        } else if ( setAddr.length == 2) {
            addr1 = setAddr[0];
            addr2 = setAddr[1];
            addr3 = "";
            addr4 = "";
        } else if ( setAddr.length == 3) {
            addr1 = setAddr[0];
            addr2 = setAddr[1];
            addr3 = setAddr[2];
            addr4 = "";
        } else {
            addr1 = setAddr[0];
            addr2 = setAddr[1];
            addr3 = setAddr[2];
            addr4 = setAddr[3];
        }
        int res = 0;

        // 예약 체크
        String delAddr1 =  setDeliveryAddr(param.getString("addr1"));
        param.put("addr1", addr1);
        param.put("addr2", addr2);
        param.put("addr3", addr3);
        param.put("addr4", addr4);
        param.put("delAddr1", delAddr1);
        param.put("crIdx", reserveInfoDetailRequest.getCrIdx());
        param.put("rentStartDay", reserveInfoDetailRequest.getRentStartDay());
        param.put("rentStartTime", rentStartTime);
        param.put("rentEndDay", reserveInfoDetailRequest.getRentEndDay());
        param.put("rentEndTime", rentEndTime);

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

    private String setDeliveryAddr (String addr) {
        String deliveryAddr = addr;

        switch (deliveryAddr) {
            case "경남":
                deliveryAddr = "경상남도";
                break;
            case "경북":
                deliveryAddr = "경상북도";
                break;
            case "전남":
                deliveryAddr = "전라남도";
                break;
            case "전북":
                deliveryAddr = "전라북도";
                break;
            case "충남":
                deliveryAddr = "충청남도";
                break;
            case "충북":
                deliveryAddr = "충청북도";
                break;
            default:
                deliveryAddr = "지점방문";
                break;
        }

        return deliveryAddr;
    }
}
