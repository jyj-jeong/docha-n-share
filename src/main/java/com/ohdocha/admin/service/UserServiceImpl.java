package com.ohdocha.admin.service;

import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.admin.DochaAdminAdminUserInfoRequest;
import com.ohdocha.admin.domain.admin.DochaAdminAdminUserInfoResponse;
import com.ohdocha.admin.domain.authTemplate.DochaAdminAuthTemplateRequest;
import com.ohdocha.admin.domain.authTemplate.DochaAdminAuthTemplateResponse;
import com.ohdocha.admin.domain.menu.DochaAdminMenuTemplateResponse;
import com.ohdocha.admin.domain.rentCompany.*;
import com.ohdocha.admin.domain.user.*;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.mapper.DochaAdminAdminUserInfoMntMapper;
import com.ohdocha.admin.mapper.DochaAdminAuthTemplateMapper;
import com.ohdocha.admin.mapper.DochaAdminRentCompanyInfoMapper;
import com.ohdocha.admin.mapper.DochaAdminUserInfoMntMapper;
import com.ohdocha.admin.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl extends ServiceExtension implements UserService {

    private final DochaAdminUserInfoMntMapper userInfoMntMapper;
    private final DochaAdminRentCompanyInfoMapper rentCompanyInfoMapper;
    private final DochaAdminAdminUserInfoMntMapper adminUserInfoMntMapper;
    private final DochaAdminAuthTemplateMapper adminAuthTemplateMapper;
    private final Properties properties;

    @Override
    public void getIntegratedUserList(ServiceMessage message) {
        DochaAdminUserInfoRequest userInfoRequest;
        if (message.get("userInfoRequest") != null){
            userInfoRequest = message.getObject("userInfoRequest", DochaAdminUserInfoRequest.class);
        }else {
            userInfoRequest = new DochaAdminUserInfoRequest();
        }

        List<DochaAdminUserInfoResponse> userInfoResponseList = userInfoMntMapper.selectUserInfo(userInfoRequest);

        if (userInfoResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result", userInfoResponseList);
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "유저 정보를 불러오는데 실패했습니다.");
        }
    }

    @Override
    public void insertUserDetail(ServiceMessage message) {
        DochaAdminInsertUserInfoRequest insertUserInfoRequest = message.getObject("insertUserInfoRequest", DochaAdminInsertUserInfoRequest.class);

        String urIdx = TextUtils.getKeyDefault("UR");
        insertUserInfoRequest.setUrIdx(urIdx);

        int res = userInfoMntMapper.insertUserInfo(insertUserInfoRequest);

        if (res == 1){
            message.addData("code", 200);
            message.addData("urIdx", insertUserInfoRequest.getUrIdx());
        }else{
            message.addData("code", 400);
            message.addData("errMsg", "회원 저장에 실패했습니다.");
        }
    }

    @Override
    public void getUserDetail(ServiceMessage message) {
        DochaAdminUserInfoRequest userInfoRequest = new DochaAdminUserInfoRequest();
        List<DochaAdminUserInfoDetailResponse> userResponse;

        if (message.getObject("userInfoRequest",DochaAdminUserInfoRequest.class) == null){

            String urIdx = message.getString("urIdx", "");
            userInfoRequest.setUrIdx(urIdx);

        }else{

            userInfoRequest =  message.getObject("userInfoRequest",DochaAdminUserInfoRequest.class);

        }

        userResponse = userInfoMntMapper.selectUserInfoDetail(userInfoRequest);

        if (userResponse.size() != 0){
            message.addData("code", 200);
            message.addData("result", userResponse);
        }else {
            message.addData("code", 400);
        }

    }

    @Override
    public void getUserLicenseInfo(ServiceMessage message) {
        DochaAdminUserInfoUserLicenseInfoRequest userLicenseInfoRequest = new DochaAdminUserInfoUserLicenseInfoRequest();
        List<DochaAdminUserInfoUserLicenseInfoResponse> userLicenseInfo;

        if (message.getObject("userLicenseInfoRequest",DochaAdminUserInfoUserLicenseInfoRequest.class) == null) {

            String urIdx = message.getString("urIdx", "");
            userLicenseInfoRequest.setUrIdx(urIdx);

        }else {

            userLicenseInfoRequest = message.getObject("userLicenseInfoRequest", DochaAdminUserInfoUserLicenseInfoRequest.class);

        }

        userLicenseInfo = userInfoMntMapper.selectLicenseInfo(userLicenseInfoRequest);

        if (userLicenseInfo.size() != 0){
            message.addData("code", 200);
            message.addData("result", userLicenseInfo);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void updateUserInfo(ServiceMessage message) {
        DochaAdminUpdateUserInfoRequest updateUserInfoRequest = message.getObject("updateUserInfoRequest", DochaAdminUpdateUserInfoRequest.class);

        int res = userInfoMntMapper.updateUserInfoDetail(updateUserInfoRequest);

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void updateUserLicenseInfo(ServiceMessage message) {
        DochaAdminUserInfoUserLicenseInfoRequest updateLicenseInfoRequest = message.getObject("updateLicenseInfoRequest", DochaAdminUserInfoUserLicenseInfoRequest.class);

        int res = userInfoMntMapper.updateUserLicenseInfo(updateLicenseInfoRequest);

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void deleteUserInfo(ServiceMessage message) {
        DochaAdminUserInfoRequest userInfoRequest = message.getObject("userInfoRequest", DochaAdminUserInfoRequest.class);

        int res = userInfoMntMapper.deleteUserInfo(userInfoRequest);

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
        }

    }

    @Override
    public void addUserLicenseInfo(ServiceMessage message) {
        DochaAdminUserInfoUserLicenseInfoRequest userLicenseInfo = message.getObject("insertUserLicenseInfo", DochaAdminUserInfoUserLicenseInfoRequest.class);
        int res = 0;
        if (userLicenseInfo.getUlIdx() != null){
            res = userInfoMntMapper.updateUserLicenseInfo(userLicenseInfo);
        }else {
            String ulIdx = TextUtils.getKeyDefault("UL");
            userLicenseInfo.setUlIdx(ulIdx);

            res = userInfoMntMapper.insertUserLicenseInfo(userLicenseInfo);
        }

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void addExtraUserLicenseInfo(ServiceMessage message) {
        DochaAdminUserInfoUserLicenseInfoRequest insertUserLicenseInfo = message.getObject("insertUserLicenseInfo", DochaAdminUserInfoUserLicenseInfoRequest.class);

        String ulIdx = TextUtils.getKeyDefault("UL");
        insertUserLicenseInfo.setUlIdx(ulIdx);

        int res = userInfoMntMapper.insertUserLicenseInfo(insertUserLicenseInfo);

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void addUserLicenseImageInfo(ServiceMessage message) {
        String ulIdx = message.getString("ulIdx", "");
        DochaAdminUserInfoUserLicenseInfoResponse licenseInfoResponse;

        Object uploadImageObj = message.get("uploadImage");
        if (!(uploadImageObj instanceof MultipartFile))
            throw new BadRequestException(IMAGE_NOT_MULTIPART_FILE, IMAGE_NOT_MULTIPART_FILE_MSG);

        MultipartFile uploadImage = (MultipartFile) uploadImageObj;

        if (uploadImage.isEmpty())
            throw new BadRequestException(IMAGE_IS_EMPTY, IMAGE_IS_EMPTY_MSG);

        String uploadImageName = uploadImage.getOriginalFilename();
        if (uploadImageName == null || uploadImageName.isEmpty())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 파일이름이 없습니다.)");

        String uploadImageMime = uploadImage.getContentType();
        if (uploadImageMime == null || uploadImageMime.isEmpty() || !uploadImageMime.contains("image/"))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 MIME 이 올바르지 않습니다.)");

        int extensionIndexOf = uploadImageName.lastIndexOf('.');
        if (extensionIndexOf == -1)
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(확장자가 존재하지 않습니다.)");

        String uploadImageExtension = uploadImageName.substring(extensionIndexOf).replaceAll("\\.", "").toLowerCase();
        if (!properties.getSupportImageExtension().contains(uploadImageExtension))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(지원하지 않는 이미지 확장자 입니다.)");

        long uploadImageSize = uploadImage.getSize();
        if (uploadImageSize > properties.getUploadImageSize())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 크기가 20MB를 초과 합니다.)");

        // 파일 랜덤 UUID 생성 (파일 명 중복시 파일 생성 안됌)
        String saveImgName = UUID.randomUUID().toString();
        File file = new File(properties.getTempFolderPath() + "license/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 해당 면허 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        licenseInfoResponse = userInfoMntMapper.selectLicenseImg(ulIdx);

        // 이미 DB에 img 정보가 있는지 여부
        if (licenseInfoResponse.getLicenseImgName() == null || licenseInfoResponse.getLicenseImgName().equals("")) {
            // 저장된 이미지가 없을 경우
            try {
                // 바로 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        } else {
            // 현재 DB에 이미지가 있으면
            File FileList = new File(properties.getTempFolderPath() + "license/");
            String[] fileList = FileList.list();
            for(int i = 0; i<fileList.length; i++){
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if(FileName.contains(licenseInfoResponse.getLicenseImgName())){
                    File deleteFile = new File(properties.getTempFolderPath() + "license/" + licenseInfoResponse.getLicenseImgName());
                    // path에서 이미 있는 파일을 제거 후
                    deleteFile.delete();
                }
            }
            try {
                // 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        }

        DochaAdminUserInfoUserLicenseInfoRequest licenseInfoRequest = new DochaAdminUserInfoUserLicenseInfoRequest();
        licenseInfoRequest.setUlIdx(ulIdx);
        licenseInfoRequest.setLicenseImageName(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        userInfoMntMapper.updateLicenseImg(licenseInfoRequest);

    }

    @Override
    public void addRentShop(ServiceMessage message) {
        DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest = message.getObject("rentCompanyDetailRequest", DochaAdminRentCompanyDetailRequest.class);

        String rtIdx = TextUtils.getKeyDefault("RT");
        rentCompanyDetailRequest.setRtIdx(rtIdx);

        int res = rentCompanyInfoMapper.insertDcRentCompany(rentCompanyDetailRequest);

        if (res == 1){
            message.addData("code", 200);
            message.addData("rtIdx",rtIdx);
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "회원사 정보 저장에 실패했습니다.");
        }
    }

    @Override
    public void updateDcRentCompany(ServiceMessage message) {
        DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest = message.getObject("rentCompanyDetailRequest", DochaAdminRentCompanyDetailRequest.class);

        int res = rentCompanyInfoMapper.updateDcRentCompany(rentCompanyDetailRequest);

        if (res == 1){
            message.addData("code", 200);
            message.addData("rtIdx",rentCompanyDetailRequest.getRtIdx());
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "회원사 정보 수정에 실패했습니다.");
        }
    }

    @Override
    public void getRentShopDetail(ServiceMessage message) {
        DochaAdminRentCompanyDetailRequest rentCompanyDetailRequest = message.getObject("rentCompanyDetailRequest", DochaAdminRentCompanyDetailRequest.class);

        List<DochaAdminRentCompanyInfoResponse> rentCompanyDetailResponseList = rentCompanyInfoMapper.selectRentCompanyDetailInfo(rentCompanyDetailRequest);

        if (rentCompanyDetailResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result",rentCompanyDetailResponseList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void getRentShopList(ServiceMessage message) {
        DochaMap loginUser = message.getObject("loginUser", DochaMap.class);
        DochaAdminRentCompanyInfoRequest rentCompanyInfoRequest = new DochaAdminRentCompanyInfoRequest();
        if (loginUser.get("userRole").equals("MA")){
            rentCompanyInfoRequest.setRtPIdx(loginUser.getString("rtIdx"));
        }else if (loginUser.get("userRole").equals("MU")){
            rentCompanyInfoRequest.setRtPIdx(loginUser.getString("rtIdx"));
        }

        List<DochaAdminRentCompanyInfoResponse> rentCompanyInfoList = rentCompanyInfoMapper.selectRentCompanyInfo(rentCompanyInfoRequest);

        if (rentCompanyInfoList.size() != 0){
            message.addData("code", 200);
            message.addData("result", rentCompanyInfoList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void getRentShopStaffList(ServiceMessage message) {
        DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest = message.getObject("rentCompanyStaffRequest", DochaAdminDcRentCompanyStaffRequest.class);

        List<DochaAdminDcRentCompanyStaffResponse> rentCompanyStaffResponsesList = rentCompanyInfoMapper.selectrentCompanyStaffList(rentCompanyStaffRequest);

        if (rentCompanyStaffResponsesList.size() != 0){
            message.addData("code", 200);
            message.addData("result", rentCompanyStaffResponsesList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void updateRentCompanyCommission(ServiceMessage message) {
        DochaAdminDcRentCompanyComissionRequest rentCompanyCommissionRequest = message.getObject("rentCompanyCommissionRequest", DochaAdminDcRentCompanyComissionRequest.class);

        //회원사 수수료 정보 유무 확인
        List<DochaAdminDcRentCompanyComissionResponse> rentCompanyCommissionReques = rentCompanyInfoMapper.selectRentCompanyComission(rentCompanyCommissionRequest);
        int rentCompanyCommissionCnt = rentCompanyCommissionReques.size();

        int res = 0;
        if (rentCompanyCommissionCnt == 0){
            res = rentCompanyInfoMapper.insertDcRentCompanyComission(rentCompanyCommissionRequest);
        }else {
            res = rentCompanyInfoMapper.updateDcRentCompanyComission(rentCompanyCommissionRequest);
        }

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void updateRentCompanyTime(ServiceMessage message) {
        DochaAdminDcRentCompanyTimeRequest rentCompanyTimeRequest = message.getObject("rentCompanyTimeRequest", DochaAdminDcRentCompanyTimeRequest.class);

        //회원사 예약정보 유무 확인
        List<DochaAdminDcRentCompanyTimeResponse> rentCompanyTimeResponseList = rentCompanyInfoMapper.selectRentCompanyTime(rentCompanyTimeRequest);
        int rentCompanyTimeCnt = rentCompanyTimeResponseList.size();

        int res = 0;
        if (rentCompanyTimeCnt == 0){
            res = rentCompanyInfoMapper.insertDcRentCompanyTime(rentCompanyTimeRequest);
        }else {
            res = rentCompanyInfoMapper.updateDcRentCompanyTime(rentCompanyTimeRequest);
        }

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void insertDcRentCompanyAblearea(ServiceMessage message) {
        List<DochaAdminDcRentCompanyAbleareaRequest> rentCompanyAbleareaRequest = message.getListObject("rentCompanyAbleareaRequest", DochaAdminDcRentCompanyAbleareaRequest.class);

        int res = rentCompanyInfoMapper.insertDcRentCompanyAblearea(rentCompanyAbleareaRequest);

        if (res > 0){
            message.addData("code", 200);
        }else message.addData("code", 400);
    }

    @Override
    public void selectDcRentCompanyAblearea(ServiceMessage message) {
        DochaAdminDcRentCompanyAbleareaRequest rentCompanyAbleareaRequest = message.getObject("rentCompanyAbleareaRequest", DochaAdminDcRentCompanyAbleareaRequest.class);

        List<DochaAdminDcRentCompanyAbleareaResponse> rentCompanyAbleareaResponseList = rentCompanyInfoMapper.selectDcRentCompanyAblearea(rentCompanyAbleareaRequest);

        if (rentCompanyAbleareaResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result", rentCompanyAbleareaResponseList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void deleteDcRentCompanyAblearea(ServiceMessage message) {
        DochaAdminDcRentCompanyAbleareaRequest rentCompanyAbleareaRequest = message.getObject("rentCompanyAbleareaRequest", DochaAdminDcRentCompanyAbleareaRequest.class);

        int res = rentCompanyInfoMapper.deleteRentCompanyAblearea(rentCompanyAbleareaRequest);

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void insertRentCompanyReserveMinList(ServiceMessage message) {
        DochaAdminDcRentCompanyReserveMinRequest rentCompanyReserveMinRequest = message.getObject("rentCompanyReserveMinRequest", DochaAdminDcRentCompanyReserveMinRequest.class);

        String minIdx = KeyMaker.getInsetance().getKeyDeafult("min");
        rentCompanyReserveMinRequest.setMinIdx(minIdx);

        int res = rentCompanyInfoMapper.insertDcRentCompanyReserveMin(rentCompanyReserveMinRequest);

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void insertRentCompanyStaff(ServiceMessage message) {
        DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest = message.getObject("rentCompanyStaffRequest", DochaAdminDcRentCompanyStaffRequest.class);

        int res = rentCompanyInfoMapper.insertDcRentCompanyStaff(rentCompanyStaffRequest);
        int res2 = rentCompanyInfoMapper.updateDcRentStaffUserinfo(rentCompanyStaffRequest);
        if (res == 1 && res2 == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "담당자 정보 저장에 실패했습니다.");
        }
    }

    @Override
    public void updateRentCompanyStaff(ServiceMessage message) {
        DochaAdminDcRentCompanyStaffRequest rentCompanyStaffRequest = message.getObject("rentCompanyStaffRequest", DochaAdminDcRentCompanyStaffRequest.class);

        int res = rentCompanyInfoMapper.updateDcRentStaff(rentCompanyStaffRequest);

        if (res == 1){
            message.addData("code", 200);
        }else {
            message.addData("code", 400);
            message.addData("errMsg", "담당자 정보 저장에 실패했습니다.");
        }
    }

    @Override
    public void selectRentCompanyReserveMinList(ServiceMessage message) {
        DochaAdminDcRentCompanyReserveMinRequest rentCompanyReserveMinRequest = message.getObject("rentCompanyReserveMinRequest", DochaAdminDcRentCompanyReserveMinRequest.class);

        List<DochaAdminDcRentCompanyReserveMinResponse> rentCompanyReserveMinResponseList = rentCompanyInfoMapper.selectDcRentCompanyReserveMinList(rentCompanyReserveMinRequest);

        if (rentCompanyReserveMinResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result", rentCompanyReserveMinResponseList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void insertRentCompanyHoliday(ServiceMessage message) {
        DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest = message.getObject("rentCompanyHolidayRequest", DochaAdminRentCompanyHolidayRequest.class);

        String holIdx = KeyMaker.getInsetance().getKeyDeafult("hl");
        rentCompanyHolidayRequest.setHolIdx(holIdx);

        int res = rentCompanyInfoMapper.insertRentCompanyHoliday(rentCompanyHolidayRequest);

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void deleteRentCompanyHoliday(ServiceMessage message) {
        DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest = message.getObject("rentCompanyHolidayRequest", DochaAdminRentCompanyHolidayRequest.class);

        int res = rentCompanyInfoMapper.deleteRentCompanyHoliday(rentCompanyHolidayRequest);

        if (res == 1){
            message.addData("code", 200);
        }else message.addData("code", 400);

    }

    @Override
    public void selectRentCompanyHoliday(ServiceMessage message) {
        DochaAdminRentCompanyHolidayRequest rentCompanyHolidayRequest = message.getObject("rentCompanyHolidayRequest", DochaAdminRentCompanyHolidayRequest.class);
        if(rentCompanyHolidayRequest.getHolidayStartDt() == null || rentCompanyHolidayRequest.getHolidayStartDt().equals("")){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = simpleDateFormat.format(new Date());
            rentCompanyHolidayRequest.setHolidayStartDt(today);
        }

        List<DochaAdminRentCompanyHolidayResponse> rentCompanyHolidayResponseList = rentCompanyInfoMapper.selectRentCompanyHoliday(rentCompanyHolidayRequest);

        if (rentCompanyHolidayResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result", rentCompanyHolidayResponseList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void getAdminList(ServiceMessage message) {
        DochaAdminAdminUserInfoRequest adminUserInfoRequest = message.getObject("adminUserInfoRequest", DochaAdminAdminUserInfoRequest.class);

        List<DochaAdminAdminUserInfoResponse> adminUserInfoList = adminUserInfoMntMapper.selectAdminUserInfo(adminUserInfoRequest);

        if (adminUserInfoList.size() != 0){
            message.addData("code", 200);
            message.addData("result", adminUserInfoList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void getAuthTemplates(ServiceMessage message) {
        DochaAdminAuthTemplateRequest adminAuthTemplateRequest = message.getObject("adminAuthTemplateRequest", DochaAdminAuthTemplateRequest.class);

        List<DochaAdminAuthTemplateResponse> authTemplateList = adminAuthTemplateMapper.selectAdminTemplate(adminAuthTemplateRequest);

        if (authTemplateList.size() != 0){
            message.addData("code", 200);
            message.addData("result", authTemplateList);
        }else {
            message.addData("code", 400);
        }
    }

    @Override
    public void insertAuthTemplate(ServiceMessage message) {

    }

    @Override
    public void selectMenuTemplateList(ServiceMessage message) {
        List<DochaAdminMenuTemplateResponse> adminMenuTemplateResponseList = adminUserInfoMntMapper.selectMenuTemplateList();

        if (adminMenuTemplateResponseList.size() != 0){
            message.addData("code", 200);
            message.addData("result", adminMenuTemplateResponseList);
        }else {
            message.addData("code", 400);
        }
    }

}
