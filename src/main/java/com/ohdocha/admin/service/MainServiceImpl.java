package com.ohdocha.admin.service;

import com.ohdocha.admin.domain.common.DochaAdminAddressInfoRequest;
import com.ohdocha.admin.domain.common.DochaAdminAddressInfoResponse;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeRequest;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeResponse;
import com.ohdocha.admin.domain.user.DochaAdminDcUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminDcUserInfoResponse;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.mapper.DochaAdminCommonCodeMapper;
import com.ohdocha.admin.mapper.DochaAdminDashboardMapper;
import com.ohdocha.admin.mapper.DochaAdminLoginMapper;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import com.ohdocha.admin.util.TextUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MainServiceImpl extends ServiceExtension implements MainService {

    private final DochaAdminCommonCodeMapper commonCodeMapper;
    private final DochaAdminDashboardMapper dashboardMapper;

    @Override
    public void summaryRentCompanyInfo(ServiceMessage message) {
        // todo admin 권한 별 count
        DochaMap resData = new DochaMap();
        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);

        if (loginUser.get("userRole").equals("RA")){
            List<DochaMap> dashboardData = dashboardMapper.selectDochaDashboardList(loginUser);

            message.addData("result", resData);
        }
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
