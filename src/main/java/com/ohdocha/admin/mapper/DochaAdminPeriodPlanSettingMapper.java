package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingDetailRequest;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingDetailResponse;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingRequest;
import com.ohdocha.admin.domain.car.plan.periodplansetting.DochaAdminPeriodPlanSettingResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminPeriodPlanSettingMapper {

	//차량-요금제-기간요금설정 list조회
	public List<DochaAdminPeriodPlanSettingResponse> selectPeriodPlanInfo(DochaAdminPeriodPlanSettingRequest reqParam);

	//차량-요금제-기간요금설정 상세
	public List<DochaAdminPeriodPlanSettingDetailResponse> selectPeriodPlanDetail(DochaAdminPeriodPlanSettingDetailRequest reqParam);


	//차량-요금제-기간요금설정 update
	public int updatePlanSettingDetail(DochaAdminPeriodPlanSettingDetailRequest reqParam);

	//차량-요금제-기간요금설정 insert
	public int insertPlanSettingDetail(DochaAdminPeriodPlanSettingDetailRequest reqParam);

	// 기간 요금제 삭제
	int deleteperiodPlan(DochaAdminPeriodPlanSettingDetailRequest dochaAdminPeriodPlanSettingDetailRequest);

}
