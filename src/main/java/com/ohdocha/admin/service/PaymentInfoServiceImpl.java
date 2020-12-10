package com.ohdocha.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateRequest;
import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateResponse;
import com.ohdocha.admin.domain.reserve.matchingService.DochaAlarmTalkDto;
import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoRequest;
import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoResponse;
import com.ohdocha.admin.domain.reserve.payment.DochaScheduledDto;
import com.ohdocha.admin.domain.reserve.reserveInfoMnt.DochaRentCompanyDto;
import com.ohdocha.admin.mapper.DochaAdminCalculateMapper;
import com.ohdocha.admin.mapper.DochaAdminPaymentInfoMapper;
import com.ohdocha.admin.mapper.DochaAdminRentCompanyInfoMapper;
import com.ohdocha.admin.mapper.DochaAdminScheduledMapper;
import com.ohdocha.admin.util.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentInfoServiceImpl extends ServiceExtension implements PaymentInfoService {

    private Properties properties;
    private DochaAdminPaymentInfoMapper paymentInfoMapper;
    private DochaAdminCalculateMapper calculateMapper;
    private DochaAdminScheduledMapper scheduledMapper;
    private DochaAdminRentCompanyInfoMapper companyInfoMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DochaAlarmTalkMsgUtil alarmTalk;

    @Override
    public void paymentInfoList(ServiceMessage message) {
        DochaAdminPaymentInfoRequest reqParam = message.getObject("reqParam", DochaAdminPaymentInfoRequest.class);

        List<DochaAdminPaymentInfoResponse> paymentInfoList = paymentInfoMapper.selectPaymentInfoList(reqParam);
        if (paymentInfoList.size() != 0) {
            message.addData("code", 200);
            message.addData("paymentInfoList", paymentInfoList);
        } else {
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

    @Override
    public void reservationRefund(ServiceMessage message) throws Exception {
        DochaMap param = new DochaMap();
        param.putAll(message.getObject("reqParam", DochaMap.class));

        Map<String, Object> result = null;
        Map<String, Object> payData = null;

        List<DochaAdminPaymentInfoResponse> reserveInfoList = paymentInfoMapper.selectReserveInfo(param);
        DochaAdminPaymentInfoResponse reserveInfo = reserveInfoList.get(0);

        double cancelAmountDouble = param.getDouble("cancel_request_amount");
        int cancelAmount = (int) Math.floor(cancelAmountDouble);
        param.set("cancel_request_amount", cancelAmount);

        DecimalFormat numberFormat = new DecimalFormat("###,###");
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String reserveDate = reserveInfo.getRegDt();

        String url = properties.getTokenUrl();
        String impKey = properties.getImpKey();
        String impSecret = properties.getImpSecret();

        String accessToken = getAccessToken(impKey, impSecret, url);

        // 헤더에 AccessToken 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        ObjectMapper mapper = new ObjectMapper();
        result = mapper.readValue(connectImport(url + "/payments/cancel", headers, HttpMethod.POST, param), Map.class);
        payData = (Map<String, Object>) result.get("response");
        param.set("receiptUrl", payData.get("receipt_url"));

        paymentInfoMapper.updateCancelReserve(param);
        double totalPayCount = param.getDouble("totalPayCount");
        if (totalPayCount > 1.0) {        // 2회 이상 납부 하는 예약이면 스케쥴도 취소한다.
            paymentCancelSchdule(param, url, impKey, impSecret);
        }


        try {
            // 취소 알림톡발송
            DochaAlarmTalkDto dto = new DochaAlarmTalkDto();

            dto.setBookDate(reserveDate.substring(0, 10) + "(" + TextUtils.getWeekByString(reserveDate.substring(0, 10), "yyyy-MM-dd") + ") " + reserveDate.substring(11, 16)); //예약일
            dto.setCancelDate(nowDate + "(" + TextUtils.getWeekByString(nowDate, "yyyy-MM-dd") + ") " + nowTime); //취소일시
            dto.setRentDate(reserveInfo.getRentStartDay() + "(" + TextUtils.getWeekByString(reserveInfo.getRentStartDay(), "yyyy-MM-dd") + ") " + reserveInfo.getRentStartTime()); //렌트시작일
            dto.setReturnDate(reserveInfo.getRentEndDay() + "(" + TextUtils.getWeekByString(reserveInfo.getRentEndDay(), "yyyy-MM-dd") + ") " + reserveInfo.getRentEndTime()); //렌트종료일
            dto.setCarName(reserveInfo.getModelName() + " " + reserveInfo.getModelDetailName());            // 차량명
            dto.setPayAmount(numberFormat.format(Integer.parseInt(reserveInfo.getSumPaymentAmount())));                                            // 결제금액
            dto.setCancelAmount(numberFormat.format(cancelAmount));                                            // 환불금액
            dto.setPhone(reserveInfo.getFirstDriverContact());                                              //알림톡 전송할 번호

            dto.setTemplateCode(DochaTemplateCodeProvider.A000007.getCode());
            //알림 톡 발송 후 로깅
            HttpResponse<JsonNode> response = alarmTalk.sendAlramTalk(dto);
            if (response.getStatus() == 200) {
                logger.info("AlarmTalk Send Compelite");
                logger.info(response.getBody().toPrettyString());
            } else {
                logger.info("AlarmTalk Send Fail");
                logger.error(response.getBody().toPrettyString());
            }
        } catch (Exception ex) {
            //알림톡 발송을 실패하더라도 오류발생시키지 않고 결제처리 완료를 위해 오류는 catch에서 로깅처리만 함
            logger.error("Error", ex);
        }


        param.put("rtIdx", reserveInfo.getRtIdx());

        // 관리자들 에게 알림톡 전송
        List<DochaRentCompanyDto> rentCompanyDtoList = companyInfoMapper.selectCompanyContactListForAlarmTalk(param);
        for (DochaRentCompanyDto dochaRentCompanyDto : rentCompanyDtoList) {
            try {
                DochaAlarmTalkDto dto = new DochaAlarmTalkDto();

                dto.setBookDate(reserveDate.substring(0, 10) + "(" + TextUtils.getWeekByString(reserveDate.substring(0, 10), "yyyy-MM-dd") + ") " + reserveDate.substring(11, 16)); //예약일
                dto.setCancelDate(nowDate + "(" + TextUtils.getWeekByString(nowDate, "yyyy-MM-dd") + ") " + nowTime); //취소일시
                dto.setRentDate(reserveInfo.getRentStartDay() + "(" + TextUtils.getWeekByString(reserveInfo.getRentStartDay(), "yyyy-MM-dd") + ") " + reserveInfo.getRentStartTime()); //렌트시작일
                dto.setReturnDate(reserveInfo.getRentEndDay() + "(" + TextUtils.getWeekByString(reserveInfo.getRentEndDay(), "yyyy-MM-dd") + ") " + reserveInfo.getRentEndTime()); //렌트종료일
                dto.setCarName(reserveInfo.getModelName() + " " + reserveInfo.getModelDetailName());            // 차량명
                dto.setCarNumber(reserveInfo.getCarNumber());            // 차량번호
                dto.setPayAmount(numberFormat.format(Integer.parseInt(reserveInfo.getSumPaymentAmount())));                                            // 결제금액
                dto.setCancelAmount(numberFormat.format(cancelAmount));                                            // 환불금액
                dto.setPhone(dochaRentCompanyDto.getCompanyContact1());                                              //알림톡 전송할 번호

                dto.setTemplateCode(DochaTemplateCodeProvider.A000020.getCode());

                //알림 톡 발송 후 로깅
                HttpResponse<JsonNode> response = alarmTalk.sendAlramTalk(dto);
                if (response.getStatus() == 200) {
                    logger.info("AlarmTalk Send Compelite");
                    logger.info(response.getBody().toPrettyString());
                } else {
                    logger.info("AlarmTalk Send Fail");
                    logger.error(response.getBody().toPrettyString());
                }
            } catch (Exception ex) {
                //알림톡 발송을 실패하더라도 오류발생시키지 않고 결제처리 완료를 위해 오류는 catch에서 로깅처리만 함
                logger.error("Error", ex);
            }
        }
    }


    private void paymentCancelSchdule(DochaMap paramMap, String url, String impKey, String impSecret) throws Exception {
        String accessToken = getAccessToken(impKey, impSecret, url);

        //헤더에 AccessToken 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        List<DochaScheduledDto> scheduledList = scheduledMapper.selectScheduledListForCancel(paramMap);

        for (int i = 0; i < scheduledList.size(); i++) {
            paramMap.put("merchant_uid", scheduledList.get(i).getMerchantUid());
            paramMap.put("customer_uid", scheduledList.get(i).getCustomerUid());

            ObjectMapper mapper = new ObjectMapper();
            mapper.readValue(connectImport(url + "/subscribe/payments/unschedule", headers, HttpMethod.POST, paramMap), Map.class);

            scheduledMapper.updateCancelScheduleStatus(paramMap);
        }

    }

    private String getAccessToken(String impKey, String impSecret, String url) throws JsonMappingException, JsonProcessingException, Exception {
        Map<String, String> body = new LinkedHashMap<String, String>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //파라미터로 imp_key, imp_secret 설정
        body.put("imp_key", impKey);
        body.put("imp_secret", impSecret);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultMap = mapper.readValue(connectImport(url + "/users/getToken", headers, HttpMethod.POST, body), Map.class);
        Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("response");

        return (String) dataMap.get("access_token");

    }

    private String connectImport(String url, HttpHeaders headers, HttpMethod method, Map body) throws Exception {

        RestTemplate connect = new RestTemplate();

        HttpEntity<Map> entity = new HttpEntity<Map>(body, headers);
        ResponseEntity<String> payResponse = null;
        try {
            payResponse = connect.exchange(url, method, entity, String.class);
        } catch (HttpServerErrorException ex) {

            logger.info("ImportConnect Error");
            logger.info("Error Request Url : " + url);
            logger.info("Error Request Body : " + body);
            logger.info("Error Response : " + ex.getResponseBodyAsString());
            logger.error("Error Request Url : " + url);
            logger.error("Error Request Body : " + body);
            logger.error(ex.getMessage());
            logger.error(ex.getResponseBodyAsString());
            logger.error("Error", ex);

            throw new Exception("Import Connection Error", ex);

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error", e);

            throw new Exception("Import Connection Error");
        }

        String responseBody = payResponse.getBody();

        logger.info("Response Body : " + responseBody);

        return responseBody;
    }

}
