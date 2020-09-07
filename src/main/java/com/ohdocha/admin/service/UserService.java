package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface UserService {

    /* 통합 회원 */

    void getIntegratedUserList(ServiceMessage message);             // 통합회원 리스트 조회

    void insertUserDetail(ServiceMessage message);                  // 회원 등록

    void getUserDetail(ServiceMessage message);                     // 회원 조회

    void updateUserInfo(ServiceMessage message);                    // 회원 수정

    void deleteUserInfo(ServiceMessage message);                    // 회원 삭제

    void addUserLicenseInfo(ServiceMessage message);                // 회원 면허정보 추가

    void getUserLicenseInfo(ServiceMessage message);                // 회원 면허정보 조회

    void updateUserLicenseInfo(ServiceMessage message);             // 회원 면허정보 수정

    /* 회원사 */

    void getRentShopList(ServiceMessage message);                   // 회원사 리스트 조회

    void addRentShop(ServiceMessage message);                       // 회원사 등록

    void getRentShopStaffList(ServiceMessage message);              // 회원사 직원 리스트 조회

    void insertRentCompanyStaff(ServiceMessage message);            // 회원사 직원 등록

    void selectRentCompanyReserveMinList(ServiceMessage message);   // 회원사별 최소 예약시간 정보 조회

    void selectRentCompanyHoliday(ServiceMessage message);          // 회원사 휴무일정보 조회

    /* 관리자 */

    void getAdminList(ServiceMessage message);                      // 관리자 리스트 조회

    void getAuthTemplates(ServiceMessage message);                  // 권한 템플릿 리스트 조회

}
