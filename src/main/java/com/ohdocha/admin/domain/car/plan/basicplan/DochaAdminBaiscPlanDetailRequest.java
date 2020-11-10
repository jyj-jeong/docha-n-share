package com.ohdocha.admin.domain.car.plan.basicplan;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
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

	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getPyIdx() {
		return pyIdx;
	}
	public void setPyIdx(String pyIdx) {
		this.pyIdx = pyIdx;
	}
	public String getPyTIdx() {
		return pyTIdx;
	}
	public void setPyTIdx(String pyTIdx) {
		this.pyTIdx = pyTIdx;
	}
	public String getPyEtc() {
		return pyEtc;
	}
	public void setPyEtc(String pyEtc) {
		this.pyEtc = pyEtc;
	}
	public String getDailyStandardPay() {
		return dailyStandardPay;
	}
	public void setDailyStandardPay(String dailyStandardPay) {
		this.dailyStandardPay = dailyStandardPay;
	}
	public String getDailyMaxRate() {
		return dailyMaxRate;
	}
	public void setDailyMaxRate(String dailyMaxRate) {
		this.dailyMaxRate = dailyMaxRate;
	}
	public String getMonthlyStandardPay() {
		return monthlyStandardPay;
	}
	public void setMonthlyStandardPay(String monthlyStandardPay) {
		this.monthlyStandardPay = monthlyStandardPay;
	}
	public String getMonthlyMaxRate() {
		return monthlyMaxRate;
	}
	public void setMonthlyMaxRate(String monthlyMaxRate) {
		this.monthlyMaxRate = monthlyMaxRate;
	}
	public String getMonth3Deposit() {
		return month3Deposit;
	}
	public void setMonth3Deposit(String month3Deposit) {
		this.month3Deposit = month3Deposit;
	}
	public String getMonth6Deposit() {
		return month6Deposit;
	}
	public void setMonth6Deposit(String month6Deposit) {
		this.month6Deposit = month6Deposit;
	}
	public String getMonth9Deposit() {
		return month9Deposit;
	}
	public void setMonth9Deposit(String month9Deposit) {
		this.month9Deposit = month9Deposit;
	}
	public String getMonth12Deposit() {
		return month12Deposit;
	}
	public void setMonth12Deposit(String month12Deposit) {
		this.month12Deposit = month12Deposit;
	}
	public String getDeliveryStandardPay() {
		return deliveryStandardPay;
	}
	public void setDeliveryStandardPay(String deliveryStandardPay) {
		this.deliveryStandardPay = deliveryStandardPay;
	}
	public String getDeliveryAddPay() {
		return deliveryAddPay;
	}
	public void setDeliveryAddPay(String deliveryAddPay) {
		this.deliveryAddPay = deliveryAddPay;
	}
	public String getDeliveryMaxRate() {
		return deliveryMaxRate;
	}
	public void setDeliveryMaxRate(String deliveryMaxRate) {
		this.deliveryMaxRate = deliveryMaxRate;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	
}
