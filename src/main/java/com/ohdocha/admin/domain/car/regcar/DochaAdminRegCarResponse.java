package com.ohdocha.admin.domain.car.regcar;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("regCarResponse")
public class DochaAdminRegCarResponse extends CommonResponseDto {

	//DC_CAR_INRO
	private String crIdx              	; //차량idx
	private String rtIdx              	; //제휴사idx
	private String companyName        	; //제휴사명
	private String branchName         	; //지점명
	private String modelName          	; //모델명
	private String modelDetailName    ; //모델상세명
	private String carNumber          	; //차량번호
	private String fuelCode           ; //연료구분code
	private String fuelName           ; //연료구분명
	private String mileage            	; //주행거리
	private String year               	; //연식
	private String dailyStandardPay   	; //일기본요금
	private String monthlyStandardPay 	; //월기본요금
	private String reserveStatus		; //예약상태
	
	private String dailyYn				; //일대여 사용유무
	private String monthlyYn			; //월대여 사용유무
	private Integer suspendCnt			; //휴차일 사용유무

	public String getCrIdx() {
		return crIdx;
	}
	public void setCrIdx(String crIdx) {
		this.crIdx = crIdx;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
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
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDetailName() {
		return modelDetailName;
	}
	public void setModelDetailName(String modelDetailName) {
		this.modelDetailName = modelDetailName;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getFuelCode() {
		return fuelCode;
	}
	public void setFuelCode(String fuelCode) {
		this.fuelCode = fuelCode;
	}
	public String getFuelName() {
		return fuelName;
	}
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getReserveStatus() {
		return reserveStatus;
	}
	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}
	public String getDailyYn() {
		return dailyYn;
	}
	public void setDailyYn(String dailyYn) {
		this.dailyYn = dailyYn;
	}
	public String getMonthlyYn() {
		return monthlyYn;
	}
	public void setMonthlyYn(String monthlyYn) {
		this.monthlyYn = monthlyYn;
	}
	public Integer getSuspendCnt() {
		return suspendCnt;
	}
	public void setSuspendCnt(Integer suspendCnt) {
		this.suspendCnt = suspendCnt;
	}


	
}                                         
