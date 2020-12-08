package com.ohdocha.admin.domain.car.plan.periodplansetting;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("periodPlanRequest")
public class DochaAdminPeriodPlanSettingRequest {
	
	private String companyName 				; // 회사명
	private String branchName 				; // 지점명
	private String perIdx                   ; // 기간요금idx
	private String crIdx                    ; // 차량idx
	private String rtIdx                    ; // 회원사idx
	private String periodStartDt            ; // 기간시작일
	private String periodEndDt              ; // 기간종료일
	private String discountExtrachargeCode  ; // 할인할증구분
	private String periodPay                ; // 기간요금
	private String periodEtc                ; // 내용
	private String regId                    ; // 등록자
	private String regDt                    ; // 등록일시
	private String modId                    ; // 수정자
	private String modDt                    ; // 수정일시
	private String delYn				    ; // 삭제여부
}
