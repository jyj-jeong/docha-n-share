package com.ohdocha.admin.controller;

import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.common.DochaAdminAddressInfoRequest;
import com.ohdocha.admin.domain.common.DochaAdminCommentRequest;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeRequest;
import com.ohdocha.admin.service.MainService;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@Controller
public class MainController extends ControllerExtension {

    private final Properties properties;
    private final MainService mainService;

    @RequestMapping(value = "/")
    public ModelAndView home(ModelAndView mv, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        DochaMap loginUser = (DochaMap) request.getSession().getAttribute("LOGIN_SESSION");
        serviceMessage.addData("loginUser", loginUser);

        mainService.summaryRentCompanyInfo(serviceMessage);

        mv.addAllObjects(serviceMessage);
        mv.setViewName("index");

        return mv;
    }

    /* 공통 코드 리스트 */
    @PostMapping(value = "/api/v1.0/commonCodeInfo.json")
    @ResponseBody
    public Object userInfoDetail(@RequestBody DochaAdminCommonCodeRequest commonCodeRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("commonCodeRequest", commonCodeRequest);

        mainService.selectCommonCodeInfo(serviceMessage);

        return serviceMessage.get("result");
    }

    /* 지역 (광역시/도) 리스트 */
    @PostMapping(value = "/api/v1.0/addressDivision.json")
    @ResponseBody
    public Object addressDivision(@RequestBody DochaAdminAddressInfoRequest addressInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("addressInfoRequest", addressInfoRequest);

        mainService.selectAddressDivisionInfo(serviceMessage);

        return serviceMessage.get("result");
    }

    /* 지역 (광역시/도) 리스트 */
    @PostMapping(value = "/api/v1.0/addressDetailDivision.json")
    @ResponseBody
    public Object addressSetailDivision(@RequestBody DochaAdminAddressInfoRequest addressInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("addressInfoRequest", addressInfoRequest);

        mainService.selectAddressDetailDivisionInfo(serviceMessage);

        return serviceMessage.get("result");
    }

    /* 댓글 등록 */
    @PostMapping(value = "/api/v1.0/insertComment.json")
    @ResponseBody
    public Object insertComment(@RequestBody DochaAdminCommentRequest commentRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("commentRequest", commentRequest);

        mainService.insertComment(serviceMessage);

        return serviceMessage;
    }

}
