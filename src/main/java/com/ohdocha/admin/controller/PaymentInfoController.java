package com.ohdocha.admin.controller;

import com.ohdocha.admin.service.CarService;
import com.ohdocha.admin.service.PaymentInfoService;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping(value = "/settlement")
public class PaymentInfoController extends ControllerExtension {
    private final PaymentInfoService paymentInfoService;

    /* 정산 리스트 */
    @GetMapping(value = "")
    public String paymentInfoList(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        paymentInfoService.paymentInfoList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "reservation/payment";
    }

}