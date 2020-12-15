package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.reserve.reserveInfoMnt.*;
import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminReserveInfoMapper {

    public List<DochaAdminReserveInfoResponse> selectReserveInfoList(DochaAdminReserveInfoRequest reqParam);

    public List<DochaAdminReserveInfoResponse> selectMatchingServiceInfoList(DochaAdminReserveInfoRequest reqParam);

    public List<DochaAdminReserveInfoResponse> selectPaymentInfoList(DochaAdminReserveInfoRequest reqParam);

    public List<DochaAdminReserveInfoDetailResponse> selectPaymentList(DochaAdminReserveInfoRequest reqParam);

    public List<DochaAdminReserveInfoDetailResponse> selectReserveInfo(DochaAdminReserveInfoRequest reqParam);

    public int updateReserveInfo(DochaAdminReserveInfoDetailRequest reqParam);

    public int insertReserveInfo(DochaAdminReserveInfoDetailRequest reqParam);

    public List<DochaMap> reserveInfoCheck(DochaAdminReserveInfoDetailRequest reqParam);

    public DochaAdminReserveInfoResponse selectUserInfo(DochaAdminReserveInfoRequest reqParam);

    public List<DochaRentCompanyDto> selectCompanyList(DochaAdminReserveInfoRequest reqParam);

    public DochaRentCompanyDto selectCompanyInfo(DochaAdminReserveInfoRequest reqParam);

    public List<DochaCarDto> selectCompanyInfoAndCarInfo(DochaAdminReserveInfoRequest reqParam);

    public DochaCarDto selectCarInfo(DochaAdminReserveInfoRequest reqParam);

    public DochaCarDto selectCarInsuranceInfo(DochaAdminReserveInfoRequest reqParam);

}
