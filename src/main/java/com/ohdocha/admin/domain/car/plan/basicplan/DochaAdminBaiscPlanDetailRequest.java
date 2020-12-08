package com.ohdocha.admin.domain.car.plan.basicplan;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Getter
@Setter
@Alias("basicPlanDetailRequest")
public class DochaAdminBaiscPlanDetailRequest extends CommonRequestDto {
	
	private String rtIdx;				//회원사 idx
	private String pyIdx;				//요금제번호
	private String pyTIdx;				//요금제번호
	private String pyEtc;				//제목
	private String dailyStandardPay;	//일 기본요금
	private String dailyMaxRate;		//일대여 최대 할인율
	private String monthlyStandardPay;	//월 기본 요금
	private String monthlyMaxRate;		//월대여 최대 할인율
	private String month3Deposit;		//3개월 보증금
	private String month6Deposit;		//6개월 보증금
	private String month9Deposit;		//9개월 보증금
	private String month12Deposit;		//12개월 보증금
	private String deliveryStandardPay;	//배달기본요금
	private String deliveryAddPay;		//배달10KM단위추가요금
	private String deliveryMaxRate;		//배달최대할인율
	private String regId;				//작성자 idx
	private String modId;				//수정자 idx
	private String crIdx;				//차량 idx

	
}
