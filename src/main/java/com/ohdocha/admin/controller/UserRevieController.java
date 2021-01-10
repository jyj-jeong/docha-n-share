package com.ohdocha.admin.controller;



import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ohdocha.admin.service.MenuService;
import com.ohdocha.admin.service.UserReviewService;
import com.ohdocha.admin.util.ServiceMessage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/site/review")
public class UserRevieController extends ControllerExtension {
	
	private final UserReviewService userReviewService;

	/**
	 * 이용후기페이지
	 * 
	 * @MethodName : getUserReviewList
	 * @Date : 2021. 01. 10
	 * @author : <a href="">wanseok, park</a>
	 * @description :
	 * @param model
	*/
    @GetMapping(value = "/main")
    public String main(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        userReviewService.selectUserReviewList(serviceMessage);
        modelMap.addAllAttributes(serviceMessage);
        return "site/site_review";
    }
    
	/**
	 * 이용후기 정보 조회
	 * 
	 * @MethodName : getUserReviewInfo
	 * @Date : 2021. 01. 10
	 * @author : <a href="">wanseok, park</a>
	 * @description :
	 * @param model
	*/
    @GetMapping(value = "/{rvIdx}")
    public String getUserReviewInfoJson(@PathVariable String rvIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rvIdx",rvIdx);
        userReviewService.selectUserReviewInfo(serviceMessage);
        modelMap.addAllAttributes(serviceMessage);
        return "/site/site_review_detail";
    }

}
