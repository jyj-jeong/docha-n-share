package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.user.DochaAdminDcUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminDcUserInfoResponse;
import com.ohdocha.admin.mapper.DochaAdminLoginMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class LoginServiceImpl extends ServiceExtension implements LoginService{

    private final DochaAdminLoginMapper loginMapper;

    @Override
    public void login(ServiceMessage message) {
        DochaAdminDcUserInfoRequest userInfoRequest = message.getObject("userInfoRequest", DochaAdminDcUserInfoRequest.class);

        DochaAdminDcUserInfoResponse userInfoResponse = loginMapper.chkLoginUserInfo(userInfoRequest);

        if (userInfoResponse != null) {

            String userRole = userInfoResponse.getUserRole();

            if (userRole.equals("RA")) {

                DochaMap userInfo = new DochaMap();
                userInfo.set("urIdx", userInfoResponse.getUrIdx());
                userInfo.set("userName", userInfoResponse.getUserName());
                userInfo.set("userRole", userInfoResponse.getUserRole());
                userInfo.set("rtIdx", userInfoResponse.getRtIdx());
                userInfo.set("companyName", userInfoResponse.getCompanyName());
                userInfo.set("branchName", userInfoResponse.getBranchName());

                message.addData("userInfo", userInfo);
                message.addData("code", 200);

            } else if (userRole.equals("MA") || userRole.equals("MU") || userRole.equals("RU")) {

                if (userInfoResponse.getAccessYn().equals("Y") && userInfoResponse.getUseYn() == 1){

                    DochaMap userInfo = new DochaMap();
                    userInfo.set("urIdx", userInfoResponse.getUrIdx());
                    userInfo.set("userName", userInfoResponse.getUserName());
                    userInfo.set("userRole", userInfoResponse.getUserRole());
                    userInfo.set("rtIdx", userInfoResponse.getRtIdx());
                    userInfo.set("companyName", userInfoResponse.getCompanyName());
                    userInfo.set("branchName", userInfoResponse.getBranchName());

                    message.addData("userInfo", userInfo);
                    message.addData("code", 200);

                }else {
                    message.addData("code", 400);
                    message.addData("errMsg", "승인된 회원사가 아닙니다.");
                }

            } else {
                message.addData("code", 400);
                message.addData("errMsg", "승인된 관리자 계정이 아닙니다.");
            }
        } else {
            message.addData("code", "400");
            message.addData("errMsg", "로그인에 실패하였습니다. \n 아이디와 비밀번호를 확인해주세요.");
        }
    }
}
