package com.ohdocha.admin.controller;

import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.user.DochaAdminDcUserInfoRequest;
import com.ohdocha.admin.service.LoginService;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
@Controller
public class LoginController extends ControllerExtension{

    private final Properties properties;
    private final LoginService loginService;

    @GetMapping(value = "/login")
    public String getLoginView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Object adminObj = request.getSession().getAttribute("LOGIN_SESSION");

        modelMap.addAttribute("serverVersion", properties.getServerVersion());
        modelMap.addAttribute("serverName", properties.getServerName());

        return "login";
    }

    @PostMapping(value = "/api/v1.0/docha.login")
    @ResponseBody
    public Object loginAsync(@RequestBody DochaAdminDcUserInfoRequest userInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request).addData("userInfoRequest", userInfoRequest);
        serviceMessage.addData("userInfoRequest",userInfoRequest);

        loginService.login(serviceMessage);

        DochaMap userInfo = serviceMessage.getObject("userInfo", DochaMap.class);
        request.getSession().setAttribute("LOGIN_SESSION", userInfo);

        return serviceMessage;
    }

    @GetMapping(value = "/api/v1.0/docha.logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().invalidate();
        sendRedirect(response, request.getContextPath() + "/login");

        return "login";
    }
}
