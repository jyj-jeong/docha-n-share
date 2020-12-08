package com.ohdocha.admin.domain.car.plan.basicplan;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("basicPlanDetailResponse")
public class DochaAdminBasicPlanDetailResponse extends CommonResponseDto {
	
	
	private String companyName		   ; //회원사 이름
	private String branchName		   ; //지점 이름
	private int    settingCarCnt       ; //설정 차량 대수
	
	private String pyIdx               ; //요금idx
	private String pyTIdx               ; //요금idx
	private String rtIdx               ; //회원사idx
	private String crIdx               ; //차량 리스트
	private String mdIdx               ; //모델상세idx
	private String dailyStandardPay    ; //일기본요금
	private String monthlyStandardPay  ; //월기본요금
	private String dailyMaxRate        ; //일대여최대할인율
	private String monthlyMaxRate      ; //월대여최대할인율
	private String month3Deposit       ; //3개월보증금
	private String month6Deposit       ; //6개월보증금
	private String month9Deposit       ; //9개월보증금
	private String month12Deposit      ; //12개월보증금
	private String deliveryStandardPay ; //배달기본요금
	private String deliveryAddPay      ; //배달10KM단위추가요금
	private String deliveryMaxRate     ; //배달최대할인율
	private String pyEtc	           ; //비고(제목)
	
	
}
