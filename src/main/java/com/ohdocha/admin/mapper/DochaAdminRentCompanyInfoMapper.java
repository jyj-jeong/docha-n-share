package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.rentCompany.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminRentCompanyInfoMapper {

    // 회원사 정보
    public List<DochaAdminRentCompanyInfoResponse> selectRentCompanyInfo(DochaAdminRentCompanyInfoRequest reqParam);

    public List<DochaAdminRentCompanyInfoResponse> selectRentCompanyDetailInfo(DochaAdminRentCompanyDetailRequest reqParam);

    public int updateDcRentCompany(DochaAdminRentCompanyDetailRequest reqParam);

    public int insertDcRentCompany(DochaAdminRentCompanyDetailRequest reqParam);

    // 회원사 - 직원정보
    public List<DochaAdminDcRentCompanyStaffResponse> selectrentCompanyStaffList(DochaAdminDcRentCompanyStaffRequest reqParam);

    public int updateDcRentStaff(DochaAdminDcRentCompanyStaffRequest reqParam);

    public int insertDcRentCompanyStaff(DochaAdminDcRentCompanyStaffRequest reqParam);

    public int updateDcRentStaffUserinfo(DochaAdminDcRentCompanyStaffRequest reqParam);

    // 회원사 - 수수료정보
    public List<DochaAdminDcRentCompanyComissionResponse> selectRentCompanyComission(DochaAdminDcRentCompanyComissionRequest reqParam);

    public int updateDcRentCompanyComission(DochaAdminDcRentCompanyComissionRequest reqParam);

    public int insertDcRentCompanyComission(DochaAdminDcRentCompanyComissionRequest reqParam);

    // 회원사 - 예약정보(회원사 영업, 운영시간 체크 정보)
    public List<DochaAdminDcRentCompanyTimeResponse> selectRentCompanyTime(DochaAdminDcRentCompanyTimeRequest reqParam);

    public int updateDcRentCompanyTime(DochaAdminDcRentCompanyTimeRequest reqParam);

    public int insertDcRentCompanyTime(DochaAdminDcRentCompanyTimeRequest reqParam);

    // 회원사 - 특정일자 예약일자 FR~TO, 예약 최소시간 정보
    public List<DochaAdminDcRentCompanyReserveMinResponse> selectDcRentCompanyReserveMinList(DochaAdminDcRentCompanyReserveMinRequest reqParam);

    public int insertDcRentCompanyReserveMin(DochaAdminDcRentCompanyReserveMinRequest reqParam);

    public int updateDcRentCompanyReserveMin(DochaAdminDcRentCompanyReserveMinRequest reqParam);

    // 회원사 - 휴무일 정보
    public List<DochaAdminRentCompanyHolidayResponse> selectRentCompanyHoliday(DochaAdminRentCompanyHolidayRequest reqParam);

    public int insertRentCompanyHoliday(DochaAdminRentCompanyHolidayRequest reqParam);

    public int deleteRentCompanyHoliday(DochaAdminRentCompanyHolidayRequest reqParam);

    public int updateRentCompanyHoliday(DochaAdminRentCompanyHolidayRequest reqParam);

    // 회원사 - 장단기 배달지역
    public List<DochaAdminDcRentCompanyAbleareaResponse> selectDcRentCompanyAblearea(DochaAdminDcRentCompanyAbleareaRequest reqParam);

    public int insertDcRentCompanyAblearea(List reqParam);

    public int updateDcRentCompanyAblearea(DochaAdminDcRentCompanyAbleareaRequest reqParam);


}


