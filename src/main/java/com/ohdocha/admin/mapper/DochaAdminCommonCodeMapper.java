package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.common.DochaAdminAddressInfoRequest;
import com.ohdocha.admin.domain.common.DochaAdminAddressInfoResponse;
import com.ohdocha.admin.domain.common.DochaAdminCommentRequest;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeRequest;
import com.ohdocha.admin.domain.common.code.DochaAdminCommonCodeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminCommonCodeMapper {

	/*
	 * 공통코드 list 조회
	 * */
	public List<DochaAdminCommonCodeResponse> selectCommonCodeInfo(DochaAdminCommonCodeRequest reqParam);


	/*
	 * 지역 구분 list 조회
	 * */
	public List<DochaAdminAddressInfoResponse> selectAddressInfo(DochaAdminAddressInfoRequest reqParam);

	/*
	 * 지역 상세 구분 list 조회
	 * */
	public List<DochaAdminAddressInfoResponse> selectAddressDetailInfo(DochaAdminAddressInfoRequest reqParam);

	/*
	 * 공통코드 Update
	 * */
	public int updateCommonCodeInfo(DochaAdminCommonCodeRequest reqParam);

	/*
	 * 공통코드 Update
	 * */
	public int insertCommonCodeInfo(DochaAdminCommonCodeRequest reqParam);

    int insertComment(DochaAdminCommentRequest commentRequest);
}
