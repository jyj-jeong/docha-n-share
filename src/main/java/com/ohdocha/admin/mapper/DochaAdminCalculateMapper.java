package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateRequest;
import com.ohdocha.admin.domain.calculateMnt.DochaAdminCalculateResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminCalculateMapper {

	public List<DochaAdminCalculateResponse> selectCalculateInfoList(DochaAdminCalculateRequest reqParam);
	public List<DochaAdminCalculateResponse> selectCalculateDateCompanyList(DochaAdminCalculateRequest reqParam);
	public List<DochaAdminCalculateResponse> selectCalculateDateReserveList(DochaAdminCalculateRequest reqParam);
	public List<DochaAdminCalculateResponse> selectCalculateDateReserveInfo(DochaAdminCalculateRequest reqParam);
	public List<DochaAdminCalculateResponse> selectCalculateDateRentCompanyReserveInfo(DochaAdminCalculateRequest reqParam);
	public List<DochaAdminCalculateResponse> selectCalculateAccountsExpectedInfo(DochaAdminCalculateRequest reqParam);
	public int insertRentAccounts(DochaAdminCalculateRequest reqParam);

}
