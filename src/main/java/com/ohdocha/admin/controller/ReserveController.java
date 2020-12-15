package com.ohdocha.admin.controller;

import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoRequest;
import com.ohdocha.admin.domain.reserve.reserveInfoMnt.DochaAdminReserveInfoDetailRequest;
import com.ohdocha.admin.domain.reserve.reserveInfoMnt.DochaAdminReserveInfoRequest;
import com.ohdocha.admin.service.PaymentInfoService;
import com.ohdocha.admin.service.ReserveService;
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
@Controller
@AllArgsConstructor
public class ReserveController extends ControllerExtension{

    private final ReserveService reserveService;
    private final PaymentInfoService paymentInfoService;

    // region [ 예약 관리 ]

    /* 예약 리스트 화면 */
    @GetMapping(value = "/reserve")
    public String reserveInfoListView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("loginUser", request.getSession().getAttribute("LOGIN_SESSION"));

        reserveService.getReserveInfoList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/reservation_list";
    }

    /* 예약 리스트 조회 */
    @PostMapping(value = "/api/v1.0/reserveInfoList.json")
    @ResponseBody
    public Object reserveInfoList(@RequestBody DochaAdminReserveInfoRequest reserveInfoRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reserveInfoRequest",reserveInfoRequest);

        reserveService.getReserveInfoList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 예약 등록 */
    @PostMapping(value = "/api/v1.0/insertReserveInfo.do")
    @ResponseBody
    public Object registerReserveInfo(@RequestBody DochaAdminReserveInfoDetailRequest reserveInfoDetailRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reserveInfoDetailRequest",reserveInfoDetailRequest);

        reserveService.addReserveInfo(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 예약 수정 */
    @PostMapping(value = "/api/v1.0/updateReserveInfo.do")
    @ResponseBody
    public Object updateReserveInfo(@RequestBody DochaAdminReserveInfoDetailRequest reserveInfoDetailRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reserveInfoDetailRequest",reserveInfoDetailRequest);

        reserveService.updateReserveInfo(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 예약 조회 */
    @PostMapping(value = "/api/v1.0/reserveInfo.json")
    @ResponseBody
    public Object reserveInfo(@RequestBody DochaAdminReserveInfoRequest reserveInfoRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reserveInfoRequest",reserveInfoRequest);

        reserveService.getReserveInfo(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 회사, 차량 정보 조회 */
    @PostMapping("/api/v1.0/selectCompanyInfoAndCarInfo.json")
    @ResponseBody
    public Object selectCompanyInfoAndCarInfo(@RequestBody DochaAdminReserveInfoRequest reserveInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reserveInfoRequest", reserveInfoRequest);

        reserveService.selectCompanyInfo(serviceMessage);
        reserveService.selectCompanyInfoAndCarInfo(serviceMessage);

        return serviceMessage;
    }

    /* 예약 등록 화면 */
    @GetMapping(value = "/reserve/register")
    public String reserveInfoView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request)
                .addData("CRUD", "insert");

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/reservation_register";
    }

    /* 예약 상세 화면 */
    @GetMapping(value = "/reserve/detail/{rmIdx}")
    public String reserveInfoView(@PathVariable String rmIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rmIdx", rmIdx);

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/reservation_detail";
    }

    // endregion

    // region [ 역경매 ]

    /* 예약 상세 화면 */
    @GetMapping(value = "/reverse")
    public String reverseAuction(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/reverse_auction";
    }

    // endregion

    // region [ 정기결제 ]

    /* 정기결제 리스트 화면 */
    @GetMapping(value = "/reserve/payment")
    public String regularPayment(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reqParam", new DochaAdminPaymentInfoRequest());

        paymentInfoService.paymentInfoList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/regular_payment_list";
    }

    // endregion

    /* 취소요청 - 환불 */
    @PostMapping(value = "/payments/cancel")
    @ResponseBody
    public Object paymentsCancel(@RequestBody Map<String, Object> reqParam, ModelAndView mv, HttpServletRequest request, Authentication authentication) throws Exception {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("reqParam", reqParam);

        paymentInfoService.reservationRefund(serviceMessage);

        return serviceMessage;
    }

}
