package com.ohdocha.admin.controller;

import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateRequest;
import com.ohdocha.admin.service.PaymentInfoService;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class PaymentInfoController extends ControllerExtension {

    private final PaymentInfoService paymentInfoService;

    /* 정산 리스트 */
    @GetMapping(value = "/settlement")
    public String paymentInfoList(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        DochaAdminCalculateRequest calculateRequest = new DochaAdminCalculateRequest();

        DochaMap loginUser = getLoginUser(request);
        if (loginUser.get("userRole").equals("MA") || loginUser.get("userRole").equals("MU")) {
            calculateRequest.setRtIdx(loginUser.getString("rtIdx"));
        }

        serviceMessage.addData("calculateRequest", calculateRequest);
        paymentInfoService.calculateDateReserveList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "calculate/payment_list";
    }

    /* 정산 상세화면 (날짜별) */
    @GetMapping(value = "/settlement/{reserveDate}")
    public String paymentDetail(@PathVariable String reserveDate, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        DochaAdminCalculateRequest calculateRequest = new DochaAdminCalculateRequest();
        calculateRequest.setAccountExpDt(reserveDate);

        DochaMap loginUser = getLoginUser(request);
        if (loginUser.get("userRole").equals("MA") || loginUser.get("userRole").equals("MU")) {
            calculateRequest.setRtIdx(loginUser.getString("rtIdx"));
        }

        serviceMessage.addData("calculateRequest", calculateRequest);
        paymentInfoService.calculateDateReserveInfo(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "calculate/payment_detail";
    }

    /* 정산 상세화면 (날짜 > 회원사) */
    @PostMapping(value = "/settlement/rentCompanySettlement.json")
    @ResponseBody
    public Object rentCompanySettlement(@RequestBody DochaAdminCalculateRequest calculateRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("calculateRequest", calculateRequest);

        paymentInfoService.calculateDateAndRtIdxReserveInfo(serviceMessage);

        return serviceMessage;
    }

    /* 정산 금액 입력 (회원사 단위 입력)*/
    @PostMapping(value = "/api/v1.0/saveRentCompanySettlementAmount.do")
    @ResponseBody
    public Object insertRentCompanySettlementAmount(@RequestBody DochaAdminCalculateRequest calculateRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("calculateRequest", calculateRequest);

        paymentInfoService.updateRentCompanySettlementAmount(serviceMessage);

        return serviceMessage;
    }

    /* 정산 금액 입력 (예약 건 단위 입력) */
    @PostMapping(value = "/api/v1.0/saveSettlementAmount.do")
    @ResponseBody
    public Object insertSettlementAmount(@RequestBody DochaAdminCalculateRequest calculateRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("calculateRequest", calculateRequest);

        paymentInfoService.updateSettlementAmount(serviceMessage);

        return serviceMessage;
    }

}