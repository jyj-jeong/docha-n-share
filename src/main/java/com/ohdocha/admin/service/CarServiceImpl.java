package com.ohdocha.admin.service;

import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelDetailRequest;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelDetailResponse;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelRequest;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelResponse;
import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBaiscPlanDetailRequest;
import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBaiscPlanRequest;
import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBasicPlanDetailResponse;
import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBasicPlanResponse;
import com.ohdocha.admin.domain.car.plan.insuranceTemplate.DochaAdminInsuranceTemplateDetailRequest;
import com.ohdocha.admin.domain.car.plan.insuranceTemplate.DochaAdminInsuranceTemplateRequest;
import com.ohdocha.admin.domain.car.plan.insuranceTemplate.DochaAdminInsuranceTemplateResponse;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingDetailRequest;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingDetailResponse;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingRequest;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingResponse;
import com.ohdocha.admin.domain.car.property.DochaAdminCarPropertyRequest;
import com.ohdocha.admin.domain.car.property.DochaAdminCarPropertyResponse;
import com.ohdocha.admin.domain.car.regcar.*;
import com.ohdocha.admin.domain.rentCompany.DochaHolidayDto;
import com.ohdocha.admin.domain.reserve.payment.DochaPaymentPeriodDto;
import com.ohdocha.admin.domain.reserve.reserveInfoMnt.DochaAdminReserveInfoRequest;
import com.ohdocha.admin.domain.reserve.reserveInfoMnt.DochaRentCompanyDto;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.mapper.*;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.FileHelper;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class CarServiceImpl extends ServiceExtension implements CarService {

    private Properties properties;

    private final DochaAdminCarModelMapper carModelMapper;
    private final DochaAdminRegCarMapper regCarMapper;
    private final DochaAdminReserveInfoMapper reserveInfoMapper;
    private final DochaAdminInsuranceTemplateMapper insuranceTemplateMapper;
    private final DochaAdminBasicPlanMapper basicPlanMapper;
    private final DochaAdminCarPropertyMapper propertyMapper;
    private final DochaAdminPeriodPlanSettingMapper periodPlanSettingMapper;


    // region [ 등록차량 ]
    /* 등록차량 리스트 */
    @Override
    public void regCarList(ServiceMessage message) {
        String rtIdx = message.getString("rtIdx");

        DochaAdminRegCarRequest regCarRequest = new DochaAdminRegCarRequest();

        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        if (!loginUser.getString("userRole").equals("RA")){
            regCarRequest.setRtIdx(rtIdx);
        }

        List<DochaAdminRegCarResponse> responseDto = regCarMapper.selectRegCarInfo(regCarRequest);

        message.addData("carRegList", responseDto);
    }

    /* 등록차량 추가 */
    @Override
    public void regCarAdd(ServiceMessage message) {
        DochaAdminRegCarDetailRequest regCarDetailRequest = message.getObject("regCarDetailRequest", DochaAdminRegCarDetailRequest.class);

        regCarDetailRequest.setCrIdx("CR" + createIdx());

        int res = regCarMapper.insertDcCarInfo(regCarDetailRequest);

        DochaAdminCarModelDetailResponse carModelResponse = carModelMapper.selectCarModelImg(regCarDetailRequest.getMdIdx());

        regCarDetailRequest.setImgIdx(carModelResponse.getImgIdx());
        regCarDetailRequest.setCartypeCode(carModelResponse.getCartypeCode());
        regCarDetailRequest.setManufacturerCode(carModelResponse.getManufacturerCode());

        regCarMapper.updateRegCarImg(regCarDetailRequest);

        message.addData("res", res);
        message.addData("crIdx", regCarDetailRequest.getCrIdx());
    }

    @Override
    public void insertRegCarOption(ServiceMessage message) {
        List<DochaAdminDcCarInfoOption> carInfoOptionList = message.getListObject("carInfoOptionList", DochaAdminDcCarInfoOption.class);
        DochaAdminRegCarDetailRequest carPropertyRequest = new DochaAdminRegCarDetailRequest();
        carPropertyRequest.setCrIdx(carInfoOptionList.get(0).getCrIdx());

        int deleteRes = regCarMapper.deleteRegCarDetailOption(carPropertyRequest);

        for (DochaAdminDcCarInfoOption carInfoOption : carInfoOptionList) {

            int res = regCarMapper.insertRegCarInfoOption(carInfoOption);
        }

    }

    /* 등록차량 요금제 추가 */
    @Override
    public void insertRegCarPayment(ServiceMessage message) {
        DochaAdminBaiscPlanDetailRequest paymentInfoRequest = message.getObject("paymentInfoRequest", DochaAdminBaiscPlanDetailRequest.class);

        int res = regCarMapper.insertRegCarPayment(paymentInfoRequest);

        message.addData("res", res);
        message.addData("crIdx", paymentInfoRequest.getCrIdx());
    }

    /* 등록차량 상세 */
    @Override
    public void regCarDetail(ServiceMessage message) {
        DochaAdminRegCarDetailRequest regCarDetailRequest = message.getObject("regCarDetailRequest", DochaAdminRegCarDetailRequest.class);

        List<DochaAdminRegCarDetailResponse> regCarDetailResponseList = regCarMapper.selectRegCarDetail(regCarDetailRequest);
        List<DochaAdminDcCarInfoOption> regCarOptionList = regCarMapper.selectRegCarDetailOption(regCarDetailRequest);

        message.addData("result", regCarDetailResponseList);
        message.addData("regCarOptionList", regCarOptionList);
    }

    /* 등록차량 요금 계산 */
    @Override
    public void selectReserveAmt(ServiceMessage message) throws ParseException {
        DochaAdminRegCarDetailRequest regCarDetailRequest = message.getObject("regCarDetailRequest", DochaAdminRegCarDetailRequest.class);

        DochaMap reqParam = new DochaMap();
        long calDateDays = 0;
        long calMinute = 0;
        long calDays = 0;
        long roundDays = 0;
        long remainMinute = 0;
        String rentFee;
        String disRentFee;
        String mmRentFee;
        String mmLastRentFee;
        String insuranceFee;

        String rentStartDay = regCarDetailRequest.getCalRentStartDt();
        String rentEndDay = regCarDetailRequest.getCalRentEndDt();
        reqParam.set("rentStartDay", rentStartDay.substring(0,10));
        reqParam.set("rentEndDay", rentEndDay.substring(0,10));
        reqParam.set("crIdx", regCarDetailRequest.getCrIdx());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date firstDate = format.parse(rentStartDay);
        Date secondDate = format.parse(rentEndDay);

        long calDate = firstDate.getTime() - secondDate.getTime();

        calDateDays = calDate / (24 * 60 * 60 * 1000);

        calDateDays = Math.abs(calDateDays);

        if (calDateDays >= 30) {
            int totalDay = (int) Math.ceil(calDateDays);        // 총 일 수
            String dailyStandardPay = regCarDetailRequest.getDailyStandardPay();    // 해당 차량의 일요금
            String monthlyStandardPay = regCarDetailRequest.getMonthlyStandardPay();    // 해당 차량의 월요금


            if (dailyStandardPay.isEmpty()) {
                dailyStandardPay = "0";
            }
            if (monthlyStandardPay.isEmpty()) {
                monthlyStandardPay = "0";
            }

            double calculateDay = Integer.parseInt(dailyStandardPay);   // 계산용 일요금
            double calculateMonth = Integer.parseInt(monthlyStandardPay);    // 계산용 월요금
            double calculRentFee;    // 계산용 총요금
            double calculTotal;    // 계산용 총요금

            double insuranceCopayment = Double.parseDouble(regCarDetailRequest.getInsuranceCopayment());
            double monthlyMaxRate = Double.parseDouble(regCarDetailRequest.getMonthlyMaxRate());
            int monthly = totalDay / 30;
            int days = totalDay % 30;
            double rate = 0.009090909091 * monthly;

            calculateMonth = calculateMonth * monthly;
            calculateMonth = Math.floor(((calculateMonth / 100) * 100) / monthly);
            calculateMonth = Math.round(calculateMonth / 100) * 100.0;
            calculateDay = Math.round(calculateDay / 100) * 100.0;
            calculTotal = calculateDay + (calculateMonth * monthly);

            calculRentFee = calculateMonth * monthly + calculateDay * days;
            if (calculRentFee > calculateMonth * monthly + 1) {
                calculRentFee = calculateMonth * (monthly + 1);
            }


            // 2달 미만은 할인 없이 월 + 일
            if (monthly < 2) {
                // 일대여 요금이 작성되지 않았을 경우
                if (dailyStandardPay.isEmpty()) {
                    calculateDay = calculateMonth * 0.1 * days;
                }
                calculateDay = calculateDay * days;

                // 일요금이 월요금을 넘을 경우
                if (calculateDay > calculateMonth) {
                    calculateDay = calculateMonth;
                }

            } else {
                if (rate < monthlyMaxRate / 100)
                    calculateMonth = calculateMonth - (0.009090909091 * calculateMonth * (monthly - 1));
                else if (rate >= monthlyMaxRate / 100 && monthlyMaxRate != 0)
                    calculateMonth = calculateMonth - (monthlyMaxRate / 100 * calculateMonth);

                calculateMonth = Math.round(calculateMonth * 100) / 100.0;

                if (monthly < 6) {
                    if (dailyStandardPay.isEmpty()) {
                        calculateDay = calculateMonth * 0.1 * days;
                    } else {
                        calculateDay = calculateDay * days;
                    }
                    if (calculateDay > calculateMonth) {
                        calculateDay = calculateMonth;
                    }
                } else {
                    calculateDay = calculateMonth * 1 / 30 * days;
                }
            }

            rentFee = Integer.toString((int) calculTotal);

            calculateMonth = calculateMonth * monthly;
            calculateMonth = Math.floor(((calculateMonth / 100) * 100) / monthly);
            calculateMonth = Math.round(calculateMonth / 100) * 100.0;
            calculateDay = Math.round(calculateDay / 100) * 100.0;
            calculTotal = calculateDay + (calculateMonth * monthly);

            insuranceCopayment = 0;


            rentFee = Integer.toString((int) calculRentFee);
            mmRentFee = Integer.toString((int) calculateMonth);
            mmLastRentFee = Integer.toString((int) calculateDay);
            disRentFee = Integer.toString((int) calculTotal);
            insuranceFee = Integer.toString((int) insuranceCopayment);

            DochaMap dochaMap = new DochaMap();
            dochaMap.set("rentFee",rentFee);
            dochaMap.set("mmRentFee",mmRentFee);
            dochaMap.set("mmLastRentFee",mmLastRentFee);
            dochaMap.set("disRentFee",disRentFee);
            dochaMap.set("insuranceFee",insuranceFee);

            message.addData("result", dochaMap);


        }else {

            calMinute = calDate / (60 * 1000);
            calMinute = Math.abs(calMinute);            // 총 분 수로 변경
            calDays = calMinute / 1440;                 // 시, 분을 뺀 일 수
            remainMinute = calMinute % 1440;            // 일을 빼고 난 후 남은 분  [ 총 시간 : calDays + remainMinute ]

            System.out.println("일 수 " + calDays);
            System.out.println("남은 분" + remainMinute);
            roundDays = Math.abs(calDate / (24 * 60 * 60 * 1000));        // 보험 계산에 사용할 1분이라도 초과되면 하루가 증가하는 roundDays


            roundDays = Math.abs(roundDays);
            // 10분이라도 초과 되면 하루가 증가한다. ( 소수점이 있는 경우 )
            double decimalcalDate = Math.abs((double) calDate / (24 * 60 * 60 * 1000));
            if (decimalcalDate > roundDays) {
                roundDays++;
            }
            System.out.println("round 일 수 : " + roundDays);



            double dailyMaxRate = Double.parseDouble(regCarDetailRequest.getDailyMaxRate());

            int totalDay = (int) calDays;                           // 일 수
            double disPer = (calMinute - 1440) / 30;                // 할인율. 1일 이후부터 시작.
            disPer = disPer * 0.02976;
            disPer = Math.round(disPer * 100) / 100.0;

            // 총 대여일이 8일(192시간) 이상일 경우 할인율은 10%
            if (totalDay >= 8 || (calMinute / 60) >= 192 || disPer >= dailyMaxRate) {
                disPer = dailyMaxRate;
            }

            String dailyStandardPay = regCarDetailRequest.getDailyStandardPay();    // 해당 차량의 일요금
            String monthlyStandardPay = regCarDetailRequest.getMonthlyStandardPay();    // 해당 차량의 월요금

            if (dailyStandardPay.isEmpty()) {
                dailyStandardPay = "0";
            }
            if (monthlyStandardPay.isEmpty()) {
                monthlyStandardPay = "0";
            }

            double calculateMinute;   // 계산용 일요금
            double calculateDay = Integer.parseInt(dailyStandardPay);   // 계산용 일요금
            double calculateMonth = Integer.parseInt(monthlyStandardPay);    // 계산용 월요금
            double calculRentFee;    // 계산용 총요금
            double calculTotal;    // 계산용 총요금

            double insuranceCopayment = Integer.parseInt(regCarDetailRequest.getInsuranceCopayment());

            // 중간 분 요금 ( 30분 당 하루 요금의 1/10 요금을 적용 )
            calculateMinute = remainMinute / 30 * (calculateDay / 20);

            // 1~29분 사이의 잔여 분이 있으면 무조건 30분의 요금을 한번 추가.
            if (remainMinute % 30 > 0) {
                calculateMinute = calculateMinute + (calculateDay / 20);
            }

            // 남은 분 요금이 일 요금을 넘으면 일 요금으로.
            if (calculateMinute >= calculateDay) {
                calculateMinute = calculateDay;
            }

            // 중간 일 요금
            calculateDay = calculateDay * totalDay;
            calculateDay = Math.ceil(calculateDay / 100) * 100;

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(rentStartDay, dateTimeFormatter);
            LocalDateTime startPlusDayOnTime = LocalDateTime.parse(rentStartDay.substring(0, 10) + " 00:00", dateTimeFormatter);
            startPlusDayOnTime = startPlusDayOnTime.plusDays(1);
            LocalDateTime endDayOnTime = LocalDateTime.parse(rentEndDay.substring(0, 10) + " 00:00", dateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(rentEndDay, dateTimeFormatter);
            String startDateString = null;

            double addPay = 0.0;    // 계산용 총요금
            long startDayCount = 0;    // 시작 날 사이클
            long endDayCount = 0;    // 종료 날 사이클
            long middleCycleCount = 0;    // 중간 날 사이클 ( 30분 당 +1 )
            long middleDayCount = 0;    // 중간 날 사이클 ( 30분 당 +1 )
            long totalAddCount = 0;    // 중간 날 사이클 ( 30분 당 +1 )
            int addCheck = 0;       // 공휴일, 주말 중복 체크

            while (startPlusDayOnTime.isAfter(startTime)) {
                DayOfWeek dayOfWeek = startTime.getDayOfWeek();
                if ((dayOfWeek == DayOfWeek.FRIDAY && startTime.getHour() >= 12) || dayOfWeek == DayOfWeek.SATURDAY || (dayOfWeek == DayOfWeek.SUNDAY && startTime.getHour() <= 12)) {
                    startDayCount++;
                } else {
                    startDateString = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    reqParam.put("holidayTime", startDateString);
                    List<DochaHolidayDto> holydayList = regCarMapper.selectHolidayList(reqParam);
                    if (holydayList.size() > 0) {
                        startDayCount++;
                    }
                }
                startTime = startTime.plusMinutes(30);
            }

            while (endDayOnTime.isAfter(startTime)) {
                DayOfWeek dayOfWeek = startTime.getDayOfWeek();
                if ((dayOfWeek == DayOfWeek.FRIDAY && startTime.getHour() >= 12) || dayOfWeek == DayOfWeek.SATURDAY || (dayOfWeek == DayOfWeek.SUNDAY && startTime.getHour() <= 12)) {
                    middleCycleCount++;
                } else {
                    startDateString = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    reqParam.put("holidayTime", startDateString);
                    List<DochaHolidayDto> holydayList = regCarMapper.selectHolidayList(reqParam);
                    if (holydayList.size() > 0) {
                        middleCycleCount++;
                    }
                }
                startTime = startTime.plusMinutes(30);
            }

            while (endTime.isAfter(startTime)) {
                DayOfWeek dayOfWeek = startTime.getDayOfWeek();
                if ((dayOfWeek == DayOfWeek.FRIDAY && startTime.getHour() >= 12) || dayOfWeek == DayOfWeek.SATURDAY || (dayOfWeek == DayOfWeek.SUNDAY && startTime.getHour() <= 12)) {
                    endDayCount++;
                } else {
                    startDateString = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    reqParam.put("holidayTime", startDateString);
                    List<DochaHolidayDto> holydayList = regCarMapper.selectHolidayList(reqParam);
                    if (holydayList.size() > 0) {
                        endDayCount++;
                    }
                }
                startTime = startTime.plusMinutes(30);
            }

            while (endDayOnTime.isAfter(startPlusDayOnTime)) {
                DayOfWeek dayOfWeek = startPlusDayOnTime.getDayOfWeek();
                if ((dayOfWeek == DayOfWeek.FRIDAY && startPlusDayOnTime.getHour() >= 12) || dayOfWeek == DayOfWeek.SATURDAY || (dayOfWeek == DayOfWeek.SUNDAY && startPlusDayOnTime.getHour() <= 12)) {
                    middleDayCount++;
                } else {
                    startDateString = startPlusDayOnTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    reqParam.put("holidayTime", startDateString);
                    List<DochaHolidayDto> holydayList = regCarMapper.selectHolidayList(reqParam);
                    if (holydayList.size() > 0) {
                        middleDayCount++;
                    }
                }
                startPlusDayOnTime = startPlusDayOnTime.plusDays(1);
            }

            if (startDayCount >= 20)
                startDayCount = 20;

            if (middleCycleCount >= middleDayCount * 20)
                middleCycleCount = middleDayCount * 20;

            if (calDays > 2 && startDayCount > 0 && endDayCount > 0 || calDays <=2 && startDayCount > 0 && endDayCount > 0) {
                middleCycleCount = (middleDayCount - 1) * 20;
            }

            if (middleCycleCount < 0)
                middleCycleCount = 0;

            if (endDayCount >= 20)
                endDayCount = 20;

            totalAddCount = startDayCount + middleCycleCount + endDayCount;

            if (totalAddCount > calDays * 20) {
                totalAddCount = calDays * 20;
            }

            // 할증 요금 = addPay
            addPay = totalAddCount * Integer.parseInt(dailyStandardPay) * 0.15 * 0.05;
            if (addPay < 0) {
                addPay = 0;
            }


            // 기간 요금제에 따른 할증 / 할인 계산
            double periodPay = 0.0;    // 기간 요금제 요금
            List<DochaPaymentPeriodDto> periodPaymentList = regCarMapper.selectPeriodPaymentList(reqParam);
            // 해당 차량에 대한 기간 요금제 정보가 있으면
            if (periodPaymentList.size() > 0) {
                // 1일 씩 더하면서 비교 할 때 시간 차이로 포함이 안 되는 경우를 방지.
                String periodRentStartDay = rentStartDay.substring(0, 8) + "0000";
                String periodRentEndDay = rentEndDay.substring(0, 8) + "2359";
                LocalDateTime periodStartTime = LocalDateTime.parse(periodRentStartDay, dateTimeFormatter);
                LocalDateTime periodEndTime = LocalDateTime.parse(periodRentEndDay, dateTimeFormatter);

                // 시작 날짜 ~ 종료 날짜 사이를 하루씩 늘리면서 해당되는 값이 있는지 가져옴
                while (periodEndTime.isAfter(periodStartTime)) {
                    reqParam.put("periodRentStartDay", periodStartTime);
                    List<DochaPaymentPeriodDto> periodPaymentListOnDaily = regCarMapper.selectPeriodPaymentListOnDaily(reqParam);
                    if (periodPaymentListOnDaily.size() > 0) {
                        for (int i = 0; i < periodPaymentListOnDaily.size(); i++) {
                            if (periodPaymentListOnDaily.get(i).getDiscountExtrachargeCode().equals("할증"))
                                // 할증이면 금액 추가
                                periodPay += Integer.parseInt(periodPaymentListOnDaily.get(i).getPeriodPay());
                            else
                                // 할인 일 시 요금을 뺀다
                                periodPay -= Integer.parseInt(periodPaymentListOnDaily.get(i).getPeriodPay());
                        }
                    }
                    periodStartTime = periodStartTime.plusDays(1);
                }
            }


            // 총 요금 = 일요금 + 분요금 * 할인율
            calculTotal = calculateDay * (100 - disPer) / 100 + calculateMinute + addPay + periodPay;
            calculRentFee = calculateDay + calculateMinute + addPay + periodPay;
            calculRentFee = Math.ceil(calculRentFee / 100) * 100.0;
            if (calculRentFee >= calculateMonth) {
                calculRentFee = calculateMonth;
            }


            // TOTAL 요금이 월요금을 넘으면 월요금으로.
            if (calculTotal >= calculateMonth) {
                calculTotal = calculateMonth;
            }

            calculTotal = Math.ceil(calculTotal / 100) * 100.0;

            insuranceCopayment = Math.ceil(insuranceCopayment * roundDays / 100) * 100.0;

            rentFee = Integer.toString((int) calculRentFee);
            mmRentFee = Integer.toString(0);
            mmLastRentFee = Integer.toString(0);
            disRentFee = Integer.toString((int) calculTotal);
            insuranceFee = Integer.toString((int) insuranceCopayment);


            DochaMap dochaMap = new DochaMap();
            dochaMap.set("rentFee", rentFee);
            dochaMap.set("totalPaymentAmount",  rentFee + insuranceFee);
            dochaMap.set("mmRentFee", mmRentFee);
            dochaMap.set("mmLastRentFee", mmLastRentFee);
            dochaMap.set("disRentFee", disRentFee);
            dochaMap.set("insuranceFee", insuranceFee);
            dochaMap.set("mmRentAmt", "0");
            dochaMap.set("addPay", addPay);

            message.addData("result", dochaMap);
        }

    }

    /* 등록차량 수정 ( 차량 부분 ) */
    @Override
    public void updateDcCarInfo(ServiceMessage message) {
        DochaAdminRegCarDetailRequest regCarRequest = message.getObject("regCarRequest", DochaAdminRegCarDetailRequest.class);

        int res = regCarMapper.updateDcCarInfo(regCarRequest);

        message.addData("res", res);

    }

    /* 등록차량 수정 ( 보험 부분 ) */
    @Override
    public void updateDcInsuranceInfo(ServiceMessage message) {
        int res;
        DochaAdminInsuranceTemplateDetailRequest insuranceTemplateDetailRequest = message.getObject("insuranceTemplateRequest", DochaAdminInsuranceTemplateDetailRequest.class);

        // 보험 테이블에 해당 차량이 있는지 확인
        int countRegCar = regCarMapper.countRegCarInsuranceInfo(insuranceTemplateDetailRequest);

        // 차량이 있으면 update
        if (countRegCar > 0 ) {
            res = regCarMapper.updateRegCarInsuranceInfo(insuranceTemplateDetailRequest);
            // 차량이 없으면 insert
        } else {
            res = regCarMapper.insertRegCarInsurance(insuranceTemplateDetailRequest);
        }

        message.addData("res", res);
        message.addData("crIdx", insuranceTemplateDetailRequest.getCrIdx());
    }

    /* 등록차량 수정 ( 요금제 부분 ) */
    @Override
    public void updateDcPaymentInfo(ServiceMessage message) {
        int res;
        DochaAdminBaiscPlanDetailRequest basicPlanDetailRequest = message.getObject("basicPlanDetailRequest", DochaAdminBaiscPlanDetailRequest.class);

        // 요금 테이블에 해당 차량이 있는지 확인
        int countRegCar = regCarMapper.countRegCarPaymentInfo(basicPlanDetailRequest);

        // 차량이 있으면 update
        if (countRegCar > 0 ) {
            res = regCarMapper.updateRegCarPaymentInfo(basicPlanDetailRequest);
            // 차량이 없으면 insert
        } else {
            res = regCarMapper.insertRegCarPayment(basicPlanDetailRequest);
        }

        message.addData("res", res);
        message.addData("crIdx", basicPlanDetailRequest.getCrIdx());
    }

    /* 차량 일대여, 월대여 설정 */
    @Override
    public void insertRentAble(ServiceMessage message) {
        DochaAdminRegCarDetailRequest carDetailRequest = message.getObject("carDetailRequest", DochaAdminRegCarDetailRequest.class);

        int res = regCarMapper.updateDcCarInfo(carDetailRequest);

        if (res == 1){
            message.addData("code", 200);
        }else{
            message.addData("code", 400);
            message.addData("errMsg", "설정에 실패했습니다.");
        }
    }

    @Override
    public void selectRentCompanyCarList(ServiceMessage message) {
        DochaAdminRegCarDetailRequest regCarDetailRequest = message.getObject("regCarDetailRequest", DochaAdminRegCarDetailRequest.class);

        String[] carTypeCodes = {"경차", "소형", "중형", "대형", "SUV", "승합"};
        Map<Integer, Object> rentCompanyCarList = new HashMap<>();

        for (int i = 0; i < carTypeCodes.length; i++) {
            regCarDetailRequest.setCartypeCode(carTypeCodes[i]);

            List<DochaAdminRegCarDetailResponse> carList = regCarMapper.selectRentCompanyCarList(regCarDetailRequest);

            rentCompanyCarList.put(i , carList);
        }

        message.addData("rentCompanyCarList", rentCompanyCarList);
    }

    @Override
    public void deleteCarMpdelInfo(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        int res = carModelMapper.deleteCarModelInfo(carModelDetailRequest);

        message.addData("res", res);
    }

    //region [ 등록차량 옵션 선택]
    /* 회사 옵션 선택 */
    @Override
    public void companyList(ServiceMessage message) {
        DochaAdminReserveInfoRequest reserveInfoRequest = message.getObject("reserveInfoRequest", DochaAdminReserveInfoRequest.class);

        List<DochaRentCompanyDto> rentCompanyDtoList = reserveInfoMapper.selectCompanyList(reserveInfoRequest);

        message.addData("result", rentCompanyDtoList);

    }

    /* 연식에 따른 차종 불러오기 */
    @Override
    public void selectYearForCarModelSelectBox(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        List<DochaAdminCarModelDetailResponse> carModelDetailResponseList = carModelMapper.selectYearForCarModelSelectBox(carModelDetailRequest);

        message.addData("result", carModelDetailResponseList);

    }

    @Override
    public void deleteCarInfo(ServiceMessage serviceMessage) {
        DochaAdminRegCarDetailRequest deleteCarRequest = serviceMessage.getObject("deleteCarRequest", DochaAdminRegCarDetailRequest.class);

        int res = 0;

        for (String crIdx : deleteCarRequest.getDeleteCarIdxList()){
            deleteCarRequest.setCrIdx(crIdx);

            res = regCarMapper.updateDcCarInfo(deleteCarRequest);
        }

        serviceMessage.addData("res", res);
    }

    /* 차종 옵션 선택 */
    @Override
    public void selectCarModelForSelectBox(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        List<DochaAdminCarModelDetailResponse> carModelDetailResponseList = carModelMapper.selectCarModelForSelectBox(carModelDetailRequest);

        message.addData("result", carModelDetailResponseList);

    }

    /* 차종상세 옵션 선택 */
    @Override
    public void selectCarModelDetailForSelectBox(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        List<DochaAdminCarModelDetailResponse> carModelDetailResponseList = carModelMapper.selectCarModelDetailForSelectBox(carModelDetailRequest);

        message.addData("result", carModelDetailResponseList);
    }

    /* 보험 템플릿 선택 */
    @Override
    public void insuranceTemplateinfoDetail(ServiceMessage message) {
        DochaAdminInsuranceTemplateRequest templateRequest = message.getObject("templateRequest", DochaAdminInsuranceTemplateRequest.class);

        List<DochaAdminInsuranceTemplateResponse> responseDto = insuranceTemplateMapper.insuranceTemplateinfoDetail(templateRequest);

        message.addData("result", responseDto);

    }

    //endregion
    //endregion


    //region [ 차량 모델 ]
    /* 차량모델 등록 */
    @Override
    public void insertCarModelInfo(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);
        String[] modelDetailNameList = carModelDetailRequest.getModelDetailNameList();

        int res = 0;

        if (modelDetailNameList.length != 0 ){
            for (String modelDetail : modelDetailNameList){
                carModelDetailRequest.setModelDetailName(modelDetail);

                res = carModelMapper.insertCarModelInfo(carModelDetailRequest);
            }
        }

        message.addData("res", res);
        message.addData("mdIdx", carModelDetailRequest.getMdIdx());
    }

    @Override
    public void uploadCarImage(ServiceMessage message) {
        int mdIdx = message.getInt("mdIdx", 0);
        DochaAdminCarModelDetailResponse carModelResponse;

        Object uploadImageObj = message.get("uploadImage");
        if (!(uploadImageObj instanceof MultipartFile))
            throw new BadRequestException(IMAGE_NOT_MULTIPART_FILE, IMAGE_NOT_MULTIPART_FILE_MSG);

        MultipartFile uploadImage = (MultipartFile) uploadImageObj;

        if (uploadImage.isEmpty())
            throw new BadRequestException(IMAGE_IS_EMPTY, IMAGE_IS_EMPTY_MSG);

        String uploadImageName = uploadImage.getOriginalFilename();
        if (uploadImageName == null || uploadImageName.isEmpty())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 파일이름이 없습니다.)");

        String uploadImageMime = uploadImage.getContentType();
        if (uploadImageMime == null || uploadImageMime.isEmpty() || !uploadImageMime.contains("image/"))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 MIME 이 올바르지 않습니다.)");

        int extensionIndexOf = uploadImageName.lastIndexOf('.');
        if (extensionIndexOf == -1)
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(확장자가 존재하지 않습니다.)");

        String uploadImageExtension = uploadImageName.substring(extensionIndexOf).replaceAll("\\.", "").toLowerCase();
        if (!properties.getSupportImageExtension().contains(uploadImageExtension))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(지원하지 않는 이미지 확장자 입니다.)");

        long uploadImageSize = uploadImage.getSize();
        if (uploadImageSize > properties.getUploadImageSize())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 크기가 20MB를 초과 합니다.)");

        // 파일 랜덤 UUID 생성 (파일 명 중복시 파일 생성 안됌)
        String saveImgName = UUID.randomUUID().toString();
        File file = new File(properties.getTempFolderPath() + "car/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 차량모델 조회
        DochaAdminCarModelDetailRequest carModelDetailRequest = new DochaAdminCarModelDetailRequest();
        carModelDetailRequest.setMdIdx(mdIdx);

        List<DochaAdminCarModelDetailResponse> carModelDetailResponses = carModelMapper.selectCarModelDetail(carModelDetailRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        carModelResponse = carModelMapper.selectCarModelImg(mdIdx);

        // 이미 DB에 img 정보가 있는지 여부
        if (carModelResponse.getImgIdx() == null || carModelResponse.getImgIdx().equals("")) {
            // 저장된 이미지가 없을 경우
            try {
                // 바로 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        } else {
            // 현재 DB에 이미지가 있으면
            File FileList = new File(properties.getTempFolderPath() + "car/");
            String[] fileList = FileList.list();
            for(int i = 0; i < fileList.length; i++){
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if(FileName.contains(carModelResponse.getImgIdx())){
                    File deleteFile = new File(properties.getTempFolderPath() + "car/" + carModelResponse.getImgIdx());
                    // path에서 이미 있는 파일을 제거 후
                    deleteFile.delete();
                }
            }
            try {
                // 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        }


        for (DochaAdminCarModelDetailResponse carModel: carModelDetailResponses){

            carModelResponse = carModel;

            DochaAdminCarModelDetailRequest modelDetailRequest = new DochaAdminCarModelDetailRequest();
            DochaAdminRegCarDetailRequest regCarDetailRequest = new DochaAdminRegCarDetailRequest();
            // 저장 할 mdIdx
            modelDetailRequest.setMdIdx(carModelResponse.getMdIdx());
            // 새로운 파일 명
            modelDetailRequest.setImgIdx(saveImgName + "." + uploadImageExtension);

            // 파일을 path에 저장 후, DB에 파일 명 저장
            carModelMapper.updateCarModelImg(modelDetailRequest);

            // 미리 등록되어있던 차량들의 이미지도 수정
            regCarDetailRequest.setImgIdx(saveImgName + "." + uploadImageExtension);
            regCarDetailRequest.setMdIdx(modelDetailRequest.getMdIdx());
            regCarMapper.updateRegCarImgByMdIdx(regCarDetailRequest);
        }
    }

    /* 차량모델 리스트 */
    @Override
    public void getCarModelList(ServiceMessage message) {
        DochaAdminCarModelRequest carModelRequest = new DochaAdminCarModelRequest();

        List<DochaAdminCarModelResponse> responseDto = carModelMapper.selectCarModelInfo(carModelRequest);

        message.addData("carModelInfoList", responseDto);

    }

    /* 차량모델 상세 */
    @Override
    public void selectCarModelDetail(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        List<DochaAdminCarModelDetailResponse> carModelDetailResponseList = carModelMapper.selectCarModelDetail(carModelDetailRequest);

        message.addData("result", carModelDetailResponseList);
    }

    /* 차량모델 수정 */
    @Override
    public void updateCarModelInfo(ServiceMessage message) {
        DochaAdminCarModelDetailRequest carModelDetailRequest = message.getObject("carModelDetailRequest", DochaAdminCarModelDetailRequest.class);

        String[] modelDetailNameList = carModelDetailRequest.getModelDetailNameList();

        int res = 0;

        if (modelDetailNameList.length != 0 ){
            for(String modelDetail : modelDetailNameList){
                carModelDetailRequest.setModelDetailName(modelDetail);

                res = carModelMapper.updateCarModelInfo(carModelDetailRequest);

                if (res == 0){
                    res = carModelMapper.insertCarModelInfo(carModelDetailRequest);
                }
            }
        }

        message.addData("res", res);
    }
    // endregion


    //region [ 차량 속성 ]
    /* 차량속성 추가 : 국가 */
    @Override
    public void insertCarPropertyCountry(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CN")
                .pCode("CN")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    /* 차량 속성 리스트 : 국가 */
    @Override
    public void carCountryProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarCountryPropertyInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);

    }

    /* 차량속성 추가 : 제조사 */
    @Override
    public void insertCarPropertyManufacturer(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CR")
                .pCode("MF")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    /* 차량 속성 리스트 : 제조사 */
    @Override
    public void carManufacturerProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarCountryManufacturerInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);

    }

    /* 차량속성 추가 : 등급 */
    @Override
    public void insertCarPropertyCarType(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CR")
                .pCode("CTY")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    /* 차량 속성 리스트 : 등급 */
    @Override
    public void carCarTypeProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarTypePropertyInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);

    }

    /* 차량속성 추가 : 옵션 */
    @Override
    public void insertCarPropertyOption(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CR")
                .pCode("OT")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    /* 차량 속성 리스트 : 옵션 */
    @Override
    public void carOptionProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarOptionPropertyInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);
    }

    /* 차량속성 추가 : 연료 */
    @Override
    public void insertCarPropertyFuel(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CR")
                .pCode("FL")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    @Override
    public void carColorProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarColorPropertyInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);
    }

    @Override
    public void insertCarPropertyColor(ServiceMessage message) {
        String value = message.getString("value", "");
        DochaAdminCarPropertyRequest carPropertyRequest = DochaAdminCarPropertyRequest.builder()
                .rtCode("CR")
                .pCode("CL")
                .code(value)
                .build();

        int res = propertyMapper.insertCarProperty(carPropertyRequest);

        message.addData("res", res);
        message.addData("mdIdx", carPropertyRequest.getCodeIdx());
    }

    /* 차량 속성 리스트 : 연료 */
    @Override
    public void carFuelProperty(ServiceMessage message) {
        DochaAdminCarPropertyRequest carPropertyRequest = message.getObject("carPropertyRequest", DochaAdminCarPropertyRequest.class);

        List<DochaAdminCarPropertyResponse> carPropertyResponseList = propertyMapper.selectCarFuelPropertyInfo(carPropertyRequest);

        message.addData("propertyList", carPropertyResponseList);
    }

    /* 속성 삭제 */
    @Override
    public void deleteProperty(ServiceMessage message) {
        String codeIdx = message.getString("codeIdx");

        int res = propertyMapper.deleteProperty(codeIdx);
    }
    //endregion


    //region [ 요금제 ]
    /* 기간 요금제 등록 */
    @Override
    public void insertPlanSettingDetail(ServiceMessage message) {
        int res = 1;
        DochaAdminPeriodPlanSettingDetailRequest periodPlanSettingDetailRequest = message.getObject("periodPlanSettingDetailRequest", DochaAdminPeriodPlanSettingDetailRequest.class);

        res = periodPlanSettingMapper.insertPlanSettingDetail(periodPlanSettingDetailRequest);

        message.addData("res", res);
    }

    /* 기간 요금제 리스트  */
    @Override
    public void getPeriodPlanList(ServiceMessage message) {
        DochaAdminPeriodPlanSettingRequest reqParam = new DochaAdminPeriodPlanSettingRequest();

        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        if (!loginUser.getString("userRole").equals("RA")){
            reqParam.setRtIdx(loginUser.getString("rtIdx"));
        }

        List<DochaAdminPeriodPlanSettingResponse> responseDto = periodPlanSettingMapper.selectPeriodPlanInfo(reqParam);

        message.addData("periodList", responseDto);
    }

    /* 기간 요금제 상세 */
    @Override
    public void selectPeriodPlanDetail(ServiceMessage message) {
        DochaAdminPeriodPlanSettingDetailRequest periodPlanSettingDetailRequest = message.getObject("periodPlanSettingDetailRequest", DochaAdminPeriodPlanSettingDetailRequest.class);

        List<DochaAdminPeriodPlanSettingDetailResponse> periodPlanSettingDetailResponseList = periodPlanSettingMapper.selectPeriodPlanDetail(periodPlanSettingDetailRequest);

        message.addData("result", periodPlanSettingDetailResponseList);
    }

    /* 기간 요금제 수정 */
    @Override
    public void updatePeriodPlan(ServiceMessage message) {
        DochaAdminPeriodPlanSettingDetailRequest periodPlanSettingDetailRequest = message.getObject("periodPlanSettingDetailRequest", DochaAdminPeriodPlanSettingDetailRequest.class);

        int res = periodPlanSettingMapper.updatePlanSettingDetail(periodPlanSettingDetailRequest);

        message.addData("res", res);
    }

    /* 기본 요금제 등록 */
    @Override
    public void insertBasicPlanInfo(ServiceMessage message) {
        DochaAdminBaiscPlanDetailRequest baiscPlanDetailRequest = message.getObject("baiscPlanDetailRequest", DochaAdminBaiscPlanDetailRequest.class);

        // 템플릿 등록
        int res = basicPlanMapper.insertBasicPlanInfo(baiscPlanDetailRequest);

        if (res == 1){
            if (!baiscPlanDetailRequest.getCrIdx().equals("")){
                String[] crIdxList = baiscPlanDetailRequest.getCrIdx().split(" ");

                for (String crIdx : crIdxList){
                    baiscPlanDetailRequest.setCrIdx(crIdx);

                    // 선택한 차량의 요금 정보 update
                    res = regCarMapper.updateRegCarPaymentInfo(baiscPlanDetailRequest);

                    // 차량의 요금정보가 기존에 없는 경우 insert
                    if (res == 0){
                        regCarMapper.insertRegCarPayment(baiscPlanDetailRequest);
                    }
                }
            }
            message.addData("res", res);

        }else {
            message.addData("res", res);
        }
    }

    /* 기본 요금제 리스트 */
    @Override
    public void getBasicPlanList(ServiceMessage message) {
        DochaAdminBaiscPlanRequest baiscPlanRequest = new DochaAdminBaiscPlanRequest();

        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        if (!loginUser.getString("userRole").equals("RA")){
            baiscPlanRequest.setRtIdx(loginUser.getString("rtIdx"));
        }

        List<DochaAdminBasicPlanResponse> basicPlanResponseList = basicPlanMapper.selectBasicPlan(baiscPlanRequest);

        message.addData("basicPlanList", basicPlanResponseList);
    }

    /* 기본 요금제 상세 */
    @Override
    public void selectbasicPlanDetail(ServiceMessage message) {
        DochaAdminBaiscPlanDetailRequest baiscPlanDetailRequest = message.getObject("baiscPlanDetailRequest", DochaAdminBaiscPlanDetailRequest.class);

        List<DochaAdminBasicPlanDetailResponse> basicPlanDetailResponseList = basicPlanMapper.selectBasicPlanDetail(baiscPlanDetailRequest);

        message.addData("result", basicPlanDetailResponseList);
    }

    /* 기본 요금제 수정 */
    @Override
    public void updateBasicPlanInfo(ServiceMessage message) {
        DochaAdminBaiscPlanDetailRequest baiscPlanDetailRequest = message.getObject("baiscPlanDetailRequest", DochaAdminBaiscPlanDetailRequest.class);

        int res = basicPlanMapper.updateBasicPlanInfo(baiscPlanDetailRequest);

        if (res == 1){
            if (!baiscPlanDetailRequest.getCrIdx().equals("")) {

                String[] crIdxList = baiscPlanDetailRequest.getCrIdx().split(" ");

                for (String crIdx : crIdxList) {
                    baiscPlanDetailRequest.setCrIdx(crIdx);

                    res = regCarMapper.updateRegCarPaymentInfo(baiscPlanDetailRequest);

                    if (res == 0) {
                        regCarMapper.insertRegCarPayment(baiscPlanDetailRequest);
                    }
                }
            }
        }

    }


    /* 보험템플릿 등록 */
    @Override
    public void insertInsuranceTemplate(ServiceMessage message) {
        DochaAdminInsuranceTemplateDetailRequest insuranceTemplateDetailRequest = message.getObject("insuranceTemplateDetailRequest", DochaAdminInsuranceTemplateDetailRequest.class);

        int res = insuranceTemplateMapper.insertInsuranceTemplate(insuranceTemplateDetailRequest);

        if (res == 1){
            if (!insuranceTemplateDetailRequest.getCrIdx().equals("")){
                String[] crIdxList = insuranceTemplateDetailRequest.getCrIdx().split(" ");

                for (String crIdx : crIdxList){
                    insuranceTemplateDetailRequest.setCrIdx(crIdx);

                    // 선택한 차량의 보험 정보 update
                    res = regCarMapper.updateRegCarInsuranceInfo(insuranceTemplateDetailRequest);

                    // 차량의 요금정보가 기존에 없는 경우 insert
                    if (res == 0){
                        regCarMapper.insertRegCarInsurance(insuranceTemplateDetailRequest);
                    }

                }

            }
            message.addData("res", res);
        }else {
            message.addData("res", res);

        }

    }

    /* 보험템플릿 리스트 */
    @Override
    public void getInsuranceTemplateList(ServiceMessage message) {
        DochaAdminInsuranceTemplateRequest insuranceTemplateRequest = new DochaAdminInsuranceTemplateRequest();

        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        if (!loginUser.getString("userRole").equals("RA")){
            insuranceTemplateRequest.setRtIdx(loginUser.getString("rtIdx"));
        }

        List<DochaAdminInsuranceTemplateResponse> insuranceTemplateResponseList = insuranceTemplateMapper.selectInsuranceTemplateInfo(insuranceTemplateRequest);

        message.addData("insuranceList", insuranceTemplateResponseList);
    }

    /* 기본 요금제 상세 */
    @Override
    public void InsuranceTemplateDetail(ServiceMessage message) {
        DochaAdminInsuranceTemplateRequest insuranceTemplateRequest = message.getObject("insuranceTemplateRequest", DochaAdminInsuranceTemplateRequest.class);

        List<DochaAdminInsuranceTemplateResponse> basicPlanDetailResponseList = insuranceTemplateMapper.insuranceTemplateinfoDetail(insuranceTemplateRequest);

        message.addData("result", basicPlanDetailResponseList);
    }

    /* 보험 템플릿 수정 */
    @Override
    public void updateInsuranceTemplate(ServiceMessage message) {
        DochaAdminInsuranceTemplateDetailRequest insuranceTemplateDetailRequest = message.getObject("insuranceTemplateDetailRequest", DochaAdminInsuranceTemplateDetailRequest.class);

        int res = insuranceTemplateMapper.updateInsuranceTemplate(insuranceTemplateDetailRequest);

        if (res == 1){
            if (!insuranceTemplateDetailRequest.getCrIdx().equals("")){
                String[] crIdxList = insuranceTemplateDetailRequest.getCrIdx().split(" ");

                for (String crIdx : crIdxList){
                    insuranceTemplateDetailRequest.setCrIdx(crIdx);

                    res = regCarMapper.updateRegCarInsuranceInfo(insuranceTemplateDetailRequest);

                    if(res == 0){
                        regCarMapper.insertRegCarInsurance(insuranceTemplateDetailRequest);
                    }
                }
            }
        }

        message.addData("res", res);
    }


    /* 등록차량 상세 옵션 */
    @Override
    public void selectRegCarDetailOption(ServiceMessage message) {
        DochaAdminRegCarDetailRequest reqParam = message.getObject("reqParam", DochaAdminRegCarDetailRequest.class);

        List<DochaAdminDcCarInfoOption> regCarMapperList = regCarMapper.selectRegCarDetailOption(reqParam);

        message.addData("result", regCarMapperList);
    }

}
