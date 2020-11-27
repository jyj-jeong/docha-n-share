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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class MainServiceImpl extends ServiceExtension implements MainService {

    private final DochaAdminCommonCodeMapper commonCodeMapper;
    private final DochaAdminDashboardMapper dashboardMapper;

    @Override
    public void summaryRentCompanyInfo(ServiceMessage message) {

        /* 메인화면 카운트 */
        Integer calcDailySales = dashboardMapper.calcDailySales();
        int calcMonthlySales = dashboardMapper.calcMonthlySales();

        int cntDailyReserve = dashboardMapper.cntDailyReserve();
        int cntMontlyyReserve = dashboardMapper.cntMontlyyReserve();

        // todo 누적월차

        int cntDailyCancel = dashboardMapper.cntDailyCancel();
        int cntMonthlyCancel = dashboardMapper.cntMonthlyCancel();

        int cntQnA = dashboardMapper.cntQnA();

        message.addData("calcDailySales", calcDailySales);
        message.addData("calcMonthlySales", calcMonthlySales);

        message.addData("cntDailyReserve", cntDailyReserve);
        message.addData("cntMontlyyReserve", cntMontlyyReserve);

        message.addData("cntDailyCancel", cntDailyCancel);
        message.addData("cntMonthlyCancel", cntMonthlyCancel);

        message.addData("cntQnA", cntQnA);


        /* 메인화면 그래프 */
        DochaMap reqParam = new DochaMap();

        LocalDateTime baseDateTime = LocalDateTime.now();
        LocalDateTime beforeOneWeek = baseDateTime.minusWeeks(1);

        String startTime = beforeOneWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = baseDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        reqParam.put("startTime", startTime);
        reqParam.put("endTime", endTime);

        List<Map<String,Object>> salesGraphList = dashboardMapper.salesGraph(reqParam);
        LinkedHashMap<Integer, Integer> salesGraphDatas = new LinkedHashMap<>();

        for (Map<String, Object> data : salesGraphList) {
            int amount = (int)((double)(data.get("amount")));
            int salesCount = (int)((long)(data.get("salesCount")));

            salesGraphDatas.put(amount, salesCount);
        }

        message.addData("amountList", salesGraphDatas.keySet());
        message.addData("salesCountList", salesGraphDatas.values().toArray());

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
