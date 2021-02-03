package com.ohdocha.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohdocha.admin.config.ErrorCode;
import com.ohdocha.admin.domain.*;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.mapper.*;
import com.ohdocha.admin.util.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("payment")
@Slf4j
@AllArgsConstructor
@Transactional
public class DochaPaymentServiceImpl implements DochaPaymentService,ErrorCode {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SEPARATION = "/";
    
//    private final Properties properties;
    
//    @Autowired
//    private final DochaPaymentDao dao;
//
//    @Autowired
//    private final DochaCarSearchDao carSearchDao;
//
//    @Autowired
//    private final DochaScheduledDao scheduledDao;
//
//    @Autowired
//    private final DochaUserInfoDao userInfoDao;
//
//    @Autowired
//    private final DochaRentcarDao rentcarDao;
//
//    @Autowired
//    private final DochaAlarmTalkMsgUtil alarmTalk;
//
//    @Autowired
//    private final DochaUserReviewDao userReviewDao;


    /**
     * 일반결제시 아임포트 API 호출 후 결제검증 후 주문저장
     */
    public void paymentOne(DochaMap paramMap, String url, String impKey, String impSecret) throws JsonMappingException, JsonProcessingException, Exception {
        String extensionRmIdx = null;

        // 연장 결제 인 경우
        if (paramMap.get("mode") != null) {
            extensionRmIdx = (String) paramMap.get("extensionRmIdx");
        }

        String rmIdx = KeyMaker.getInsetance().getKeyDeafult("RM");
        String reIdx = KeyMaker.getInsetance().getKeyDeafult("RE");
        String plIdx = KeyMaker.getInsetance().getKeyDeafult("PL");
        String pdIdx = KeyMaker.getInsetance().getKeyDeafult("PD");

        paramMap.put("rmIdx", rmIdx);
        paramMap.put("extensionRmIdx", extensionRmIdx);
        paramMap.put("reIdx", reIdx);
        paramMap.put("plIdx", plIdx);
        paramMap.put("pdIdx", pdIdx);

        //결제검증전문
        String orgMsg = null;
        //결제검증 결과
        Map<String, Object> result = null;
        //결제 중 paydata
        Map<String, Object> payData = null;
        //아임포트 결제 key값을 셋팅
        String impUid = paramMap.getString("impUid");
        //결제검증을 위해 아임포트 AccessToken 발급
        String accessToken = getAccessToken(impKey, impSecret, url);
        //아임포트 결제 검증 호출부분
        try {
            //아임포트 AccessToken, 결제 key값을 전달하여 결제데이터 호출
//            result = getPaymentInfo(impUid, accessToken, url);

            //결제전문 중 결제관련한 데이터를 가져옴
            payData = (Map<String, Object>) result.get("response");

            //결제전문을 JSONString형태로 변환
            ObjectMapper mapper = new ObjectMapper();
            orgMsg = mapper.writeValueAsString(result);
        } catch (Exception e) {
            //에러발생시 로그처리 후 에러 throws
            logger.error("Import Connect Error", e);
            throw e;
        }

        //세션에 저장했던 결제 전 호출된 금액 및 차량정보
//        DochaCarInfoDto resCarInfo = (DochaCarInfoDto) paramMap.get("resCarDto");
        //결제유저정보
//        DochaUserInfoDto userInfo = (DochaUserInfoDto) paramMap.get("user");

        //세션의 일별요금, 보험요금을 불러옴
//        String sessionDailyStandardPay = resCarInfo.getDailyStandardPay();
//        String sessionInsuranceFee = resCarInfo.getInsuranceFee();

//       sessionDailyStandardPay = sessionDailyStandardPay == null ? "0" : sessionDailyStandardPay;
//        sessionInsuranceFee = sessionInsuranceFee == null ? "0" : sessionInsuranceFee;

//        int dailyStandardPay = Integer.parseInt(sessionDailyStandardPay);
//        int insuranceFee = Integer.parseInt(sessionInsuranceFee);

        //결제검증 데이터 중 결제금액 가져옴
        int payment = (int) payData.get("amount");
        //결제검증 데이터 중 승인번호 가져옴
        String applyNum = (String) payData.getOrDefault("apply_num", null);

        //결제금액이 세션금액과 일치하지 않는경우
////		if(payment != dailyStandardPay + insuranceFee) {
////
////			//paylog 저장 후 Exception throws
////			DochaPaymentLogDto payLog = new DochaPaymentLogDto();
////			payLog.setRmIdx(rmIdx);
////			payLog.setApprovalNumber(applyNum);
////			payLog.setPaymentAmount(Integer.toString(payment));
////			payLog.setOrgMsg(orgMsg);
////			payLog.setApprovalYn(applyNum == null ? "N" : "Y");
////			payLog.setPaymentRequestAmount(Integer.toString(dailyStandardPay + insuranceFee));
////			payLog.setPlIdx(plIdx);
////			payLog.setPdIdx(pdIdx);
////			dao.insertPaymentLog(payLog);
////
////			throw new Exception("Payment Amount Check Error");
////		}

        //주문저장처리
//        paymentSave(paramMap, orgMsg, result, payData);

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

