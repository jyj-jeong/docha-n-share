package com.ohdocha.admin.domain.calculateMnt;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("calculateResponse")
public class DochaAdminCalculateResponse extends CommonResponseDto {

	private String rowNum;
	private String totalRowCount;
	private String rmIdx;
	private String pdIdx;
	//private String rentGbnDt;
	private String accountExpDt;
	private String totalFee;
	private String totalAmount;
	private String dochaDisFee;
	private String accountExpAmt;
	private String successAmount;
	private String onMissFee;
	private String disCountFee;
	private String companyCount;
	private String rmCount;
	
	//COMPANY INFO
	private String reserveCount;
	private String companyName;
	private String branchName;
	private String companyRegistrationName;
	private String successFee;
	private String accountBank;
	private String accountNumber;
	private String paymentAmount;
	
	//RESERVE INFO
	private String rentStartDay;
	private String rentEndDay;
	private String rentStartTime;
	private String rentEndTime;
	private String userName;
    
	//CAR INFO
	private String modelName;
	private String modelDetailName;
	private String carNumber;
	private String year;
	private String rtIdx;
	private String fuelCode;
	private String fuelName;
	
	
	public String getRentStartDay() {
		return rentStartDay;
	}
	public void setRentStartDay(String rentStartDay) {
		this.rentStartDay = rentStartDay;
	}
	public String getRentEndDay() {
		return rentEndDay;
	}
	public void setRentEndDay(String rentEndDay) {
		this.rentEndDay = rentEndDay;
	}
	public String getRentStartTime() {
		return rentStartTime;
	}
	public void setRentStartTime(String rentStartTime) {
		this.rentStartTime = rentStartTime;
	}
	public String getRentEndTime() {
		return rentEndTime;
	}
	public void setRentEndTime(String rentEndTime) {
		this.rentEndTime = rentEndTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public String getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(String totalRowCount) {
		this.totalRowCount = totalRowCount;
	}


	public String getPdIdx() {
		return pdIdx;
	}
	public void setPdIdx(String pdIdx) {
		this.pdIdx = pdIdx;
	}
	public String getAccountExpDt() {
		return accountExpDt;
	}
	public void setAccountExpDt(String accountExpDt) {
		this.accountExpDt = accountExpDt;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getAccountExpAmt() {
		return accountExpAmt;
	}
	public void setAccountExpAmt(String accountExpAmt) {
		this.accountExpAmt = accountExpAmt;
	}
	public String getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDisCountFee() {
		return disCountFee;
	}
	public void setDisCountFee(String disCountFee) {
		this.disCountFee = disCountFee;
	}
	public String getDochaDisFee() {
		return dochaDisFee;
	}
	public void setDochaDisFee(String dochaDisFee) {
		this.dochaDisFee = dochaDisFee;
	}
	public String getOnMissFee() {
		return onMissFee;
	}
	public void setOnMissFee(String onMissFee) {
		this.onMissFee = onMissFee;
	}
	
	
	public String getReserveCount() {
		return reserveCount;
	}
	public void setReserveCount(String reserveCount) {
		this.reserveCount = reserveCount;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSuccessFee() {
		return successFee;
	}
	public void setSuccessFee(String successFee) {
		this.successFee = successFee;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCompanyCount() {
		return companyCount;
	}
	public void setCompanyCount(String companyCount) {
		this.companyCount = companyCount;
	}

	public String getRmCount() {
		return rmCount;
	}
	public void setRmCount(String rmCount) {
		this.rmCount = rmCount;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(String rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCompanyRegistrationName() {
		return companyRegistrationName;
	}
	public void setCompanyRegistrationName(String companyRegistrationName) {
		this.companyRegistrationName = companyRegistrationName;
	}
	public String getModelDetailName() {
		return modelDetailName;
	}
	public void setModelDetailName(String modelDetailName) {
		this.modelDetailName = modelDetailName;
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
	
	
	
}
