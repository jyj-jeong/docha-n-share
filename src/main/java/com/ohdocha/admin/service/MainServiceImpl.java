package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.common.DochaAdminAddressInfoRequest;
import com.ohdocha.admin.domain.common.DochaAdminAddressInfoResponse;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeRequest;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeResponse;
import com.ohdocha.admin.mapper.DochaAdminCommonCodeMapper;
import com.ohdocha.admin.mapper.DochaAdminDashboardMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class MainServiceImpl extends ServiceExtension implements MainService {

    private final DochaAdminCommonCodeMapper commonCodeMapper;
    private final DochaAdminDashboardMapper dashboardMapper;

    @Override
    public void summaryRentCompanyInfo(ServiceMessage message) {
        DochaMap reqParam = new DochaMap();

        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        String userRole = loginUser.getString("userRole");
        String rtIdx = loginUser.getString("rtIdx");

        if(userRole.equals("MA") || userRole.equals("MU")){
            reqParam.put("rtIdx", rtIdx);
        }

        // region [ 메인화면 카운트 ]
        Integer calcDailySales = dashboardMapper.calcDailySales(reqParam);
        Integer calcMonthlySales = dashboardMapper.calcMonthlySales(reqParam);

        Integer cntDailyReserve = dashboardMapper.cntDailyReserve(reqParam);
        Integer cntMonthlyReserve = dashboardMapper.cntMonthlyReserve(reqParam);

        Integer cntDailyRegularRent = dashboardMapper.cntDailyRegularRent(reqParam);
        Integer cntMonthlyRegularRent = dashboardMapper.cntMonthlyRegularRent(reqParam);

        Integer cntDailyCancel = dashboardMapper.cntDailyCancel(reqParam);
        Integer cntMonthlyCancel = dashboardMapper.cntMonthlyCancel(reqParam);

        Integer cntQnA = dashboardMapper.cntQnA(reqParam);

        message.addData("calcDailySales", calcDailySales);
        message.addData("calcMonthlySales", calcMonthlySales);

        message.addData("cntDailyReserve", cntDailyReserve);
        message.addData("cntMonthlyReserve", cntMonthlyReserve);

        message.addData("cntDailyRegularRent", cntDailyRegularRent);
        message.addData("cntMonthlyRegularRent", cntMonthlyRegularRent);

        message.addData("cntDailyCancel", cntDailyCancel);
        message.addData("cntMonthlyCancel", cntMonthlyCancel);

        message.addData("cntQnA", cntQnA);
        // endregion

        // region [ 그래프 ]

        // 일매출 그래프프
        LocalDateTime baseDateTime = LocalDateTime.now();

        List<Map<String,Object>> salesGraphList = new ArrayList<>();

        for (int i=7; i>=0; i--){
            LocalDateTime localDateTime = baseDateTime.minusDays(i);

            String startTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            reqParam.put("startTime", startTime);

            Map<String,Object> salesDatas = dashboardMapper.salesGraph(reqParam);
            if (salesDatas == null){
                Map<String,Object> nullData = new HashMap<>();
                nullData.put("amount", 0);
                nullData.put("salesCount", 0);

                salesGraphList.add(nullData);
            }else {
                salesGraphList.add(salesDatas);
            }

        }

        List<Integer> dailyAmountList = new ArrayList<>();
        List<Integer> dailySalesCountList = new ArrayList<>();
        for (Map<String, Object> data : salesGraphList) {
            int amount = (int)((double)(data.get("amount")));
            int salesCount = (int)((long)(data.get("salesCount")));

            dailyAmountList.add(amount);
            dailySalesCountList.add(salesCount);

        }

        message.addData("dailyAmountList", dailyAmountList);
        message.addData("dailySalesCountList", dailySalesCountList);

        // 월매출 그래프프
        List<Map<String,Object>> monthlySalesGraphList = new ArrayList<>();
        List<String> monthlyDate = new ArrayList<>();

        for (int i=7; i>=0; i--){
            LocalDateTime localDateTime = baseDateTime.minusMonths(i);

            String startTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            monthlyDate.add(startTime);

            reqParam.put("startTime", startTime);

            Map<String,Object> monthlySalesDatas = dashboardMapper.monthlySalesGraph(reqParam);
            if (monthlySalesDatas == null){
                Map<String, Object> nullData = new HashMap<>();
                nullData.put("amount", 0.0);
                nullData.put("salesCount", 0L);

                monthlySalesGraphList.add(nullData);
            }else {
                monthlySalesGraphList.add(monthlySalesDatas);
            }
        }

        List<Integer> monthlyAmountList = new ArrayList<>();
        List<Integer> monthlySalesCountList = new ArrayList<>();

        for (Map<String, Object> data : monthlySalesGraphList) {
            int amount = (int)((double)(data.get("amount")));
            int salesCount = (int)((long)(data.get("salesCount")));

            monthlyAmountList.add(amount);
            monthlySalesCountList.add(salesCount);
        }

        message.addData("monthlyDate", monthlyDate);
        message.addData("monthlyAmountList",monthlyAmountList);
        message.addData("monthlySalesCountList", monthlySalesCountList);

        // 신규회원 그래프
        List<Map<String,Object>> newUserGraphList = new ArrayList<>();

        for (int i=7; i>=0; i--){
            LocalDateTime localDateTime = baseDateTime.minusDays(i);

            String startTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            reqParam.put("startTime", startTime);

            Map<String,Object> newUserDatas = dashboardMapper.newUserGraph(reqParam);
            if (newUserDatas == null){
                Map<String, Object> nullData = new HashMap<>();
                nullData.put("newUser", 0);

                newUserGraphList.add(nullData);
            }else {
                newUserGraphList.add(newUserDatas);
            }
        }

        List<Integer> newUserList = new ArrayList<>();

        for (Map<String, Object> data : newUserGraphList) {
            int userCount = (int)((long)(data.get("newUser")));

            newUserList.add(userCount);
        }
        message.addData("newUserList", newUserList);

        // 취소 그래프
        List<Map<String,Object>> cancelGraphList = new ArrayList<>();

        for (int i=7; i>=0; i--){
            LocalDateTime localDateTime = baseDateTime.minusDays(i);

            String startTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            reqParam.put("startTime", startTime);

            Map<String,Object> cancelDatas = dashboardMapper.cancelGraph(reqParam);
            if (cancelDatas == null){
                Map<String, Object> nullData = new HashMap<>();
                nullData.put("cancelCount", 0);

                cancelGraphList.add(nullData);
            }else {
                cancelGraphList.add(cancelDatas);
            }
        }

        List<Integer> cancelList = new ArrayList<>();

        for (Map<String, Object> data : cancelGraphList) {
            int cancelCount = (int)((long)(data.get("cancelCount")));

            cancelList.add(cancelCount);
        }
        message.addData("cancelList", cancelList);
        // endregion

    }

    @Override
    public void selectCommonCodeInfo(ServiceMessage message) {
        DochaAdminCommonCodeRequest commonCodeRequest = message.getObject("commonCodeRequest", DochaAdminCommonCodeRequest.class);

        List<DochaAdminCommonCodeResponse> commonCodeResponseList = commonCodeMapper.selectCommonCodeInfo(commonCodeRequest);

        message.addData("result", commonCodeResponseList);
    }

    @Override
    public void selectAddressDivisionInfo(ServiceMessage message) {
        DochaAdminAddressInfoRequest addressInfoRequest = message.getObject("addressInfoRequest", DochaAdminAddressInfoRequest.class);

        List<DochaAdminAddressInfoResponse> addressInfoResponseList = commonCodeMapper.selectAddressInfo(addressInfoRequest);

        message.addData("result", addressInfoResponseList);
    }

    @Override
    public void selectAddressDetailDivisionInfo(ServiceMessage message) {
        DochaAdminAddressInfoRequest addressInfoRequest = message.getObject("addressInfoRequest", DochaAdminAddressInfoRequest.class);

        List<DochaAdminAddressInfoResponse> addressInfoResponseList = commonCodeMapper.selectAddressDetailInfo(addressInfoRequest);

        message.addData("result", addressInfoResponseList);
    }
}
