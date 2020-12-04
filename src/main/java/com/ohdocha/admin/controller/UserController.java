package com.ohdocha.admin.controller;

import com.ohdocha.admin.domain.authTemplate.DochaAdminAuthTemplateRequest;
import com.ohdocha.admin.domain.rentCompany.*;
import com.ohdocha.admin.domain.user.DochaAdminInsertUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminUpdateUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminUserInfoUserLicenseInfoRequest;
import com.ohdocha.admin.service.UserService;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class UserController extends ControllerExtension {

    private final UserService userService;

    // region [ 통합 회원 ]

    /* 통합 회원 리스트 화면 */
    @GetMapping(value = "/mem")
    public String IntegratedUserInfoList(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        userService.getIntegratedUserList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/integrated_user_list";
    }

    /* 통합 회원 리스트 */
    @PostMapping(value = "/api/v1.0/userInfoList.json")
    @ResponseBody
    public Object userInfoList(@RequestBody DochaAdminUserInfoRequest userInfoRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("userInfoRequest",userInfoRequest);

        userService.getIntegratedUserList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 회원 상세 페이지 */
    @GetMapping(value = "/mem/detail/{urIdx}")
    public String userInfoDetail(@PathVariable String urIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("urIdx", urIdx);
        serviceMessage.addData("CRUD", "modify");

        // 회원 기본정보
        userService.getUserDetail(serviceMessage);
        // 회원 면허정보
        userService.getUserLicenseInfo(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/integrated_user_detail";
    }

    /* 회원 등록 화면 */
    @GetMapping(value = "/mem/register")
    public String userInfoDetailRegisterView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("CRUD","insert");

        modelMap.addAllAttributes(serviceMessage);
        return "user/integrated_user_detail";
    }

    /* 회원 등록 */
    @PostMapping(value = "/api/v1.0/insertUserInfo.do")
    @ResponseBody
    public Object userInfoDetailRegister(@RequestBody DochaAdminInsertUserInfoRequest insertUserInfoRequest , HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("insertUserInfoRequest",insertUserInfoRequest);

        userService.insertUserDetail(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return serviceMessage;
    }

    /* 회원 상세 */
    @PostMapping(value = "/api/v1.0/userInfoListDetail.json")
    @ResponseBody
    public Object userInfoDetail(@RequestBody DochaAdminUserInfoRequest userInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("userInfoRequest", userInfoRequest);

        userService.getUserDetail(serviceMessage);

        return serviceMessage;
    }

    /* 회원 정보 수정 */
    @PostMapping(value = "/api/v1.0/updateUserInfo.do")
    @ResponseBody
    public Object userInfoUpdate(@RequestBody DochaAdminUpdateUserInfoRequest userInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("updateUserInfoRequest", userInfoRequest);

        userService.updateUserInfo(serviceMessage);

        return serviceMessage;
    }

    /* 회원 정보 삭제 */
    @PostMapping(value = "/api/v1.0/userInfoDelete.do")
    @ResponseBody
    public Object userInfoDelete(@RequestBody DochaAdminUserInfoRequest userInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("userInfoRequest", userInfoRequest);

        userService.deleteUserInfo(serviceMessage);

        return serviceMessage;
    }

    /* 회원 면허정보 등록 */
    @PostMapping(value = "/api/v1.0/insertUserLicenseInfo.do")
    @ResponseBody
    public Object insertUserLicenseInfo(@RequestBody DochaAdminUserInfoUserLicenseInfoRequest insertUserLicenseInfo, HttpServletRequest request) {

        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("insertUserLicenseInfo", insertUserLicenseInfo);

        userService.addUserLicenseInfo(serviceMessage);

        return serviceMessage;
    }

    /* 회원 면허정보 2 등록 */
    @PostMapping(value = "/api/v1.0/insertAdditionalLicenseInfo.do")
    @ResponseBody
    public Object insertAdditionalLicenseInfo(@RequestBody DochaAdminUserInfoUserLicenseInfoRequest insertUserLicenseInfo, HttpServletRequest request) {

        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("insertUserLicenseInfo", insertUserLicenseInfo);

        userService.addUserLicenseInfo(serviceMessage);

        return serviceMessage;
    }

    /* 면허 사진 등록 */
    @PostMapping(value = "/api/v1.0/uploadLicenseImage.do")
    @ResponseBody
    public Object uploadCarImage(@RequestParam("image") MultipartFile uploadImage, String ulIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("ulIdx", ulIdx);

        userService.addUserLicenseImageInfo(serviceMessage);

        return serviceMessage;
    }

    /* 회원 면허정보 조회 */
    @PostMapping(value = "/api/v1.0/userLicenseInfo.json")
    @ResponseBody
    public Object UserLicenseInfo(@RequestBody DochaAdminUserInfoUserLicenseInfoRequest userLicenseInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("userLicenseInfoRequest", userLicenseInfoRequest);

        userService.getUserLicenseInfo(serviceMessage);

        return serviceMessage;
    }

    /* 회원 면허정보 수정 */
    @PostMapping(value = "/api/v1.0/updateUserLicenseInfo.do")
    @ResponseBody
    public Object userLicenseUpdate(@RequestBody DochaAdminUserInfoUserLicenseInfoRequest licenseInfoRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("updateLicenseInfoRequest", licenseInfoRequest);

        userService.updateUserLicenseInfo(serviceMessage);

        return serviceMessage;
    }

    // endregion

    // region [ 회원사 ]

    /* 회원사 리스트 */
    @GetMapping(value = "/mem/rentshop")
    public String rentShopList(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        DochaMap loginUser = (DochaMap) request.getSession().getAttribute("LOGIN_SESSION");

        serviceMessage.addData("loginUser", loginUser);
        userService.getRentShopList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/rentshop_list";
    }

    /* 회원사 등록 화면 */
    @GetMapping(value = "/mem/rentshop/register")
    public String rentShopRegister(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("CRUD", "insert");

        modelMap.addAllAttributes(serviceMessage);
        return "user/rentshop_detail";
    }

    /* 회원사 등록 */
    @PostMapping(value = "/api/v1.0/insertDcRentCompany.do")
    @ResponseBody
    public Object rentShopRegister(@RequestBody DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyDetailRequest", rentCompanyDetailRequest);

        userService.addRentShop(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 수정 */
    @PostMapping(value = "/api/v1.0/updateDcRentCompany.do")
    @ResponseBody
    public Object updateDcRentCompany(@RequestBody DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyDetailRequest", rentCompanyDetailRequest);

        userService.updateDcRentCompany(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 상세 조회 */
    @PostMapping(value = "/api/v1.0/rentCompanyDetailInfo.json")
    @ResponseBody
    public Object rentCompanyDetailInfo(@RequestBody DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyDetailRequest", rentCompanyDetailRequest);

        userService.getRentShopDetail(serviceMessage);

        return serviceMessage;
    }


    /* 회원사 상세 페이지 */
    @GetMapping(value = "/mem/rentshop/{rtIdx}")
    public String rentShopDetail(@PathVariable String rtIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rtIdx", rtIdx);
        serviceMessage.addData("CRUD", "modify");

        modelMap.addAllAttributes(serviceMessage);
        return "user/rentshop_detail";
    }

    /* 회원사 직원 조회 */
    @PostMapping(value = "/api/v1.0/rentCompanyStaffList.json")
    @ResponseBody
    public Object rentShopStaffList(@RequestBody DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyStaffRequest", rentCompanyStaffRequest);

        userService.getRentShopStaffList(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 직원 추가 */
    @PostMapping(value = "/api/v1.0/insertDcRentCompanyStaff.do")
    @ResponseBody
    public Object insertRentCompanyStaff(@RequestBody DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyStaffRequest", rentCompanyStaffRequest);

        userService.insertRentCompanyStaff(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 직원 수정 */
    @PostMapping(value = "/api/v1.0/updateDcRentCompanyStaff.do")
    @ResponseBody
    public Object updateRentCompanyStaff(@RequestBody DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyStaffRequest", rentCompanyStaffRequest);

        userService.updateRentCompanyStaff(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 수수료 정보 추가 */
    @PostMapping(value = "/api/v1.0/updateDcRentCompanyCommission.do")
    @ResponseBody
    public Object updateRentCompanyCommission(@RequestBody DochaAdminDcRentCompanyComissionRequest rentCompanyCommissionRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyCommissionRequest", rentCompanyCommissionRequest);

        userService.updateRentCompanyCommission(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 영업시간 추가 */
    @PostMapping(value = "/api/v1.0/updateDcRentCompanyTime.do")
    @ResponseBody
    public Object updateRentCompanyTime(@RequestBody DochaAdminDcRentCompanyTimeRequest rentCompanyTimeRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyTimeRequest", rentCompanyTimeRequest);

        userService.updateRentCompanyTime(serviceMessage);

        return serviceMessage;

    }

    /* 예약정보 특정 시간 추가 */
    @PostMapping(value = "/api/v1.0/insertDcRentCompanyReserveMin.do")
    @ResponseBody
    public Object insertDcRentCompanyReserveMin(@RequestBody DochaAdminDcRentCompanyReserveMinRequest rentCompanyReserveMinRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyReserveMinRequest", rentCompanyReserveMinRequest);

        userService.insertRentCompanyReserveMinList(serviceMessage);

        return serviceMessage;
    }


    /* 예약정보 특정 시간 리스트 */
    @PostMapping(value = "/api/v1.0/rentCompanyReserveMinList.json")
    @ResponseBody
    public Object rentCompanyReserveMinList(@RequestBody DochaAdminDcRentCompanyReserveMinRequest rentCompanyReserveMinRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyReserveMinRequest", rentCompanyReserveMinRequest);

        userService.selectRentCompanyReserveMinList(serviceMessage);

        return serviceMessage;
    }

    /* 회원사 배달지역 추가 */
    @PostMapping(value = "/api/v1.0/insertDcRentCompanyAblearea.do")
    @ResponseBody
    public Object insertDcRentCompanyAblearea(@RequestBody List<DochaAdminDcRentCompanyAbleareaRequest> rentCompanyAbleareaRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyAbleareaRequest", rentCompanyAbleareaRequest);

        userService.insertDcRentCompanyAblearea(serviceMessage);

        return serviceMessage;

    }

    /* 회원사 배달지역 조회 */
    @PostMapping(value = "/api/v1.0/selectDcRentCompanyAblearea.json")
    @ResponseBody
    public Object selectDcRentCompanyAblearea(@RequestBody DochaAdminDcRentCompanyAbleareaRequest rentCompanyAbleareaRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyAbleareaRequest", rentCompanyAbleareaRequest);

        userService.selectDcRentCompanyAblearea(serviceMessage);

        return serviceMessage;

    }

    /* 회원사 배달지역 삭제 */
    @PostMapping(value = "/api/v1.0/deleteDcRentCompanyAblearea.do")
    @ResponseBody
    public Object deleteDcRentCompanyAblearea(@RequestBody DochaAdminDcRentCompanyAbleareaRequest rentCompanyAbleareaRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyAbleareaRequest", rentCompanyAbleareaRequest);

        userService.deleteDcRentCompanyAblearea(serviceMessage);

        return serviceMessage;

    }

    /* 회원사 전체 휴일 등록*/
    @GetMapping(value = "/mem/rentshop/hoilday")
    public String rentShopRegisterHoildayView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        DochaAdminRentCompanyHolidayRequest holidayRequest = new DochaAdminRentCompanyHolidayRequest();

        holidayRequest.setRtIdx("all");
        serviceMessage.addData("rentCompanyHolidayRequest", holidayRequest);

        userService.selectRentCompanyHoliday(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/holiday_registration";
    }

    /* 휴무일 리스트 추가 */
    @PostMapping(value = "/api/v1.0/insertRentCompanyHoliday.do")
    @ResponseBody
    public Object insertRentCompanyHoliday(@RequestBody DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyHolidayRequest", rentCompanyHolidayRequest);

        userService.insertRentCompanyHoliday(serviceMessage);

        return serviceMessage;
    }

    /* 휴무일 삭제 */
    @PostMapping(value = "/api/v1.0/deleteRentCompanyHoliday.do")
    @ResponseBody
    public Object deleteRentCompanyHoliday(@RequestBody DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyHolidayRequest", rentCompanyHolidayRequest);

        userService.deleteRentCompanyHoliday(serviceMessage);

        return serviceMessage;
    }

    /* 휴무일 리스트 조회 */
    @PostMapping(value = "/api/v1.0/selectRentCompanyHoliday.json")
    @ResponseBody
    public Object selectRentCompanyHoliday(@RequestBody DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("rentCompanyHolidayRequest", rentCompanyHolidayRequest);

        userService.selectRentCompanyHoliday(serviceMessage);

        return serviceMessage;
    }

    // endregion

    // region [ 관리자 ]

    /* 관리자 리스트 */
    @GetMapping(value = "/mem/admin")
    public String adminList(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        userService.getAdminList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/admin_list";
    }

    /* 관리자 등록 화면 */
    @GetMapping(value = "/mem/admin/register")
    public String registerAdminView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "user/admin_detail";
    }

    /* 권한 템플릿 리스트 추가 화면 */
    @GetMapping(value = "/mem/template/register")
    public String registerAuthTemplate(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "user/auth_template_detail";
    }

    /* 권한 템플릿 리스트 */
    @GetMapping(value = "/mem/template")
    public String authTemplates(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        userService.getAuthTemplates(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "user/auth_template_list";
    }

    /* 권한 템플릿 추가 */
    @PostMapping(value = "/api/v1.0/insertAuthTemplate.do")
    @ResponseBody
    public Object insertAuthTemplates(@RequestBody DochaAdminAuthTemplateRequest authTemplateRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("authTemplateRequest",authTemplateRequest);

        userService.insertAuthTemplate(serviceMessage);

        return serviceMessage;
    }

    /* 관리자 메뉴 조회 */
    @PostMapping(value = "/api/v1.0/insertAdminMenuTemplate.do")
    @ResponseBody
    public Object insertAdminMenuTemplate(HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        userService.selectMenuTemplateList(serviceMessage);

        return serviceMessage;
    }

    /* 관리자 메뉴 조회 */
    @PostMapping(value = "/api/v1.0/adminMenuTemplate.json")
    @ResponseBody
    public Object adminMenuTemplate(HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        userService.selectMenuTemplateList(serviceMessage);

        return serviceMessage;
    }
    // endregion
}
