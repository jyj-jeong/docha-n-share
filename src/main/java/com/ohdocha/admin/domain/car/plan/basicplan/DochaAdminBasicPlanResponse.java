package com.ohdocha.admin.domain.car.plan.basicplan;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("basicPlanResponse")
public class DochaAdminBasicPlanResponse extends CommonResponseDto {
	
	private String pyIdx               ;  // 요금idx
	private String pyTIdx               ;  // 요금idx
	private String mdIdx               ;  // 모델상세idx
	private String dailyStandardPay    ;  // 일기본요금
	private String monthlyStandardPay  ;  // 월기본요금
	private String dailyMaxRate        ;  // 일대여최대할인율
	private String monthlyMaxRate      ;  // 월대여최대할인율
	private String month3Deposit       ;  // 3개월보증금
	private String month6Deposit       ;  // 6개월보증금
	private String month9Deposit       ;  // 9개월보증금
	private String month12Deposit      ;  // 12개월보증금
	private String deliveryStandardPay ;  // 배달기본요금
	private String deliveryAddPay      ;  // 배달10KM단위추가요금
	private String deliveryMaxRate     ;  // 배달최대할인율
	private String rtIdx               ;  // 회원사idx
	private String pyEtc              ;  // 비고(제목)
	
	
	private String companyName		   ; //회원사 이름
	private String branchName		   ; //지점 이름
	private int    settingCarCnt       ; //설정 차량 대수
	
	
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
	public String getMdIdx() {
		return mdIdx;
	}
	public void setMdIdx(String mdIdx) {
		this.mdIdx = mdIdx;
	}
	public String getDailyStandardPay() {
		return dailyStandardPay;
	}
	public void setDailyStandardPay(String dailyStandardPay) {
		this.dailyStandardPay = dailyStandardPay;
	}
	public String getMonthlyStandardPay() {
		return monthlyStandardPay;
	}
	public void setMonthlyStandardPay(String monthlyStandardPay) {
		this.monthlyStandardPay = monthlyStandardPay;
	}
	public String getDailyMaxRate() {
		return dailyMaxRate;
	}
	public void setDailyMaxRate(String dailyMaxRate) {
		this.dailyMaxRate = dailyMaxRate;
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
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getPyEtc() {
		return pyEtc;
	}
	public void setPyEtc(String pyEtc) {
		this.pyEtc = pyEtc;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getSettingCarCnt() {
		return settingCarCnt;
	}
	public void setSettingCarCnt(int settingCarCnt) {
		this.settingCarCnt = settingCarCnt;
	}
	
	
	
}
