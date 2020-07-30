package com.ohdocha.admin.config;

import com.ohdocha.admin.domain.AdminInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@AllArgsConstructor
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            AdminInfoDto memberSession = (AdminInfoDto) request.getSession().getAttribute("LOGIN_SESSION");
            if (memberSession == null) {
                sendRedirect(request, response);
                return false;
            }
        } catch (Exception e) {
            sendRedirect(request, response);
            return false;
        }

        return true;
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (Exception ignored) {

        }
    }

}
