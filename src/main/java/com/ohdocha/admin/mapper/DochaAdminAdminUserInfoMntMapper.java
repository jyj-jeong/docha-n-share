package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.admin.DochaAdminAdminUserInfoRequest;
import com.ohdocha.admin.domain.admin.DochaAdminAdminUserInfoResponse;
import com.ohdocha.admin.domain.menu.DochaAdminMenuTemplateResponse;
import com.ohdocha.admin.domain.user.DochaAdminUserInfoRequest;
import com.ohdocha.admin.domain.user.DochaAdminUserInfoResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminAdminUserInfoMntMapper {

	/*
	 * 관리자 리스트 조회
	 * */
	public List<DochaAdminUserInfoResponse> selectAdminUserInfo(DochaAdminUserInfoRequest reqParam);


	/*
	 * 관리자 상세
	 * */
	public DochaAdminAdminUserInfoResponse selectAdminUserInfoDetail(DochaAdminAdminUserInfoRequest reqParam);


	/*
	 * 관리자 업데이트
	 * */
	public int updateAdminUserInfoDetail(DochaAdminAdminUserInfoRequest reqParam);


	/*
	 * 관리자 신규 저장
	 * */
	public int insertAdminUserInfo(DochaAdminAdminUserInfoRequest reqParam);


	/*
	 * 관리자 메뉴템플릿 권한 조회
	 * */
	public List<DochaAdminMenuTemplateResponse> selectMenuTemplateList();

}
