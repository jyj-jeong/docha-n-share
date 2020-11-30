package com.ohdocha.admin.domain.reserve.reserveInfoMnt;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("reserveInfoDetailResponse")	
public class DochaAdminReserveInfoDetailResponse extends CommonResponseDto {

	private String rmIdx;
	
	// 예약자 정보
	private String urIdx;
	private String reserveUserName;
	private String reserveUserGender;
	private String reserveUserEmail;
	private String reserveUserContact1;
	private String reserveUserBirthday;

	// 운전자 정보
	private String userFlag;
	// 제1 운전자 정보
	private String ulIdx1;
	private String firstDriverName;
	private String firstDriverGender;
	private String firstDriverContact;
	private String firstDriverBirthDay;
	private String firstDriverLicenseCode;
	private String firstDriverLicenseNumber;
	private String firstDriverExpirationDate;
	private String firstDriverLicenseIsDate;
	// 제2 운전자 정보
	private String ulIdx2;
	private String secondDriverName;
	private String secondDriverGender;
	private String secondDriverContact;
	private String secondDriverBirthDay;
	private String secondDriverLicenseCode;
	private String secondDriverLicenseNumber;
	private String secondDriverExpirationDate;
	private String secondDriverLicenseIsDate;

	// 예약정보
	private String reserveYmdt;
	private String reserveChannel;
	private String landCode;
	private String reserveDate;
	private String reserveStatusCode;
	private String reserveTypeCode;
	private String deliveryTypeCode;
	private String rentStartDay;
	private String rentEndDay;
	private String periodDt;
	private String deliveryAddr;
	private String returnAddr;
	
	// 회원사, 차량정보
	private String rtIdx;
	private String staffName;
	private String staffContact1;
	private String crIdx;
	private String mdIdx;
	private String onselfDamageCover;
	private String personalCover;
	private String propertyDamageCover;
	private String carDamageCover;
	private String insuranceCopayment;
	private String fuelCode;
	
	// 대여금액 정보
	private String rentFee;
	private String insuranceFee;
	private String discountFee;
	private String deliveryFee;
	private String addFee;
	private String paymentTotalAmount;
	private String sumPaymentAmount;
	private String paymentAmount;
	private String refundFee;
	private String miSu;
	
	// 결제정보
	private String pgCode;
	private String paymentTypeCode;
	private String paymentTypeName;
	private String paymentKindCode;
	private String paymentKindName;
	private String approvalNumber;
	private String paymentDate;
	private String etc;
	
	private String flag;
	private String msg;

	private String modDt;

	public String getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(String rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getUrIdx() {
		return urIdx;
	}
	public void setUrIdx(String urIdx) {
		this.urIdx = urIdx;
	}
	public String getReserveUserName() {
		return reserveUserName;
	}
	public void setReserveUserName(String reserveUserName) {
		this.reserveUserName = reserveUserName;
	}
	public String getReserveUserGender() {
		return reserveUserGender;
	}
	public void setReserveUserGender(String reserveUserGender) {
		this.reserveUserGender = reserveUserGender;
	}
	public String getReserveUserEmail() {
		return reserveUserEmail;
	}
	public void setReserveUserEmail(String reserveUserEmail) {
		this.reserveUserEmail = reserveUserEmail;
	}
	public String getReserveUserContact1() {
		return reserveUserContact1;
	}
	public void setReserveUserContact1(String reserveUserContact1) {
		this.reserveUserContact1 = reserveUserContact1;
	}
	public String getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}
	public String getUlIdx1() {
		return ulIdx1;
	}
	public void setUlIdx1(String ulIdx1) {
		this.ulIdx1 = ulIdx1;
	}
	public String getUlIdx2() {
		return ulIdx2;
	}
	public void setUlIdx2(String ulIdx2) {
		this.ulIdx2 = ulIdx2;
	}
	public String getFirstDriverName() {
		return firstDriverName;
	}
	public void setFirstDriverName(String firstDriverName) {
		this.firstDriverName = firstDriverName;
	}
	public String getFirstDriverGender() {
		return firstDriverGender;
	}
	public void setFirstDriverGender(String firstDriverGender) {
		this.firstDriverGender = firstDriverGender;
	}
	public String getFirstDriverContact() {
		return firstDriverContact;
	}
	public void setFirstDriverContact(String firstDriverContact) {
		this.firstDriverContact = firstDriverContact;
	}
	public String getFirstDriverBirthDay() {
		return firstDriverBirthDay;
	}
	public void setFirstDriverBirthDay(String firstDriverBirthDay) {
		this.firstDriverBirthDay = firstDriverBirthDay;
	}
	public String getFirstDriverLicenseCode() {
		return firstDriverLicenseCode;
	}
	public void setFirstDriverLicenseCode(String firstDriverLicenseCode) {
		this.firstDriverLicenseCode = firstDriverLicenseCode;
	}
	public String getFirstDriverLicenseNumber() {
		return firstDriverLicenseNumber;
	}
	public void setFirstDriverLicenseNumber(String firstDriverLicenseNumber) {
		this.firstDriverLicenseNumber = firstDriverLicenseNumber;
	}
	public String getFirstDriverExpirationDate() {
		return firstDriverExpirationDate;
	}
	public void setFirstDriverExpirationDate(String firstDriverExpirationDate) {
		this.firstDriverExpirationDate = firstDriverExpirationDate;
	}
	public String getFirstDriverLicenseIsDate() {
		return firstDriverLicenseIsDate;
	}
	public void setFirstDriverLicenseIsDate(String firstDriverLicenseIsDate) {
		this.firstDriverLicenseIsDate = firstDriverLicenseIsDate;
	}
	public String getSecondDriverName() {
		return secondDriverName;
	}
	public void setSecondDriverName(String secondDriverName) {
		this.secondDriverName = secondDriverName;
	}
	public String getSecondDriverGender() {
		return secondDriverGender;
	}
	public void setSecondDriverGender(String secondDriverGender) {
		this.secondDriverGender = secondDriverGender;
	}
	public String getSecondDriverContact() {
		return secondDriverContact;
	}
	public void setSecondDriverContact(String secondDriverContact) {
		this.secondDriverContact = secondDriverContact;
	}
	public String getSecondDriverBirthDay() {
		return secondDriverBirthDay;
	}
	public void setSecondDriverBirthDay(String secondDriverBirthDay) {
		this.secondDriverBirthDay = secondDriverBirthDay;
	}
	public String getSecondDriverLicenseCode() {
		return secondDriverLicenseCode;
	}
	public void setSecondDriverLicenseCode(String secondDriverLicenseCode) {
		this.secondDriverLicenseCode = secondDriverLicenseCode;
	}
	public String getSecondDriverLicenseNumber() {
		return secondDriverLicenseNumber;
	}
	public void setSecondDriverLicenseNumber(String secondDriverLicenseNumber) {
		this.secondDriverLicenseNumber = secondDriverLicenseNumber;
	}
	public String getSecondDriverExpirationDate() {
		return secondDriverExpirationDate;
	}
	public void setSecondDriverExpirationDate(String secondDriverExpirationDate) {
		this.secondDriverExpirationDate = secondDriverExpirationDate;
	}
	public String getSecondDriverLicenseIsDate() {
		return secondDriverLicenseIsDate;
	}
	public void setSecondDriverLicenseIsDate(String secondDriverLicenseIsDate) {
		this.secondDriverLicenseIsDate = secondDriverLicenseIsDate;
	}
	public String getReserveYmdt() {
		return reserveYmdt;
	}
	public void setReserveYmdt(String reserveYmdt) {
		this.reserveYmdt = reserveYmdt;
	}
	public String getReserveChannel() {
		return reserveChannel;
	}
	public void setReserveChannel(String reserveChannel) {
		this.reserveChannel = reserveChannel;
	}
	public String getLandCode() {
		return landCode;
	}
	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getReserveStatusCode() {
		return reserveStatusCode;
	}
	public void setReserveStatusCode(String reserveStatusCode) {
		this.reserveStatusCode = reserveStatusCode;
	}
	public String getReserveTypeCode() {
		return reserveTypeCode;
	}
	public void setReserveTypeCode(String reserveTypeCode) {
		this.reserveTypeCode = reserveTypeCode;
	}
	public String getDeliveryTypeCode() {
		return deliveryTypeCode;
	}
	public void setDeliveryTypeCode(String deliveryTypeCode) {
		this.deliveryTypeCode = deliveryTypeCode;
	}
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
	public String getPeriodDt() {
		return periodDt;
	}
	public void setPeriodDt(String periodDt) {
		this.periodDt = periodDt;
	}
	public String getDeliveryAddr() {
		return deliveryAddr;
	}
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	public String getReturnAddr() {
		return returnAddr;
	}
	public void setReturnAddr(String returnAddr) {
		this.returnAddr = returnAddr;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffContact1() {
		return staffContact1;
	}
	public void setStaffContact1(String staffContact1) {
		this.staffContact1 = staffContact1;
	}
	public String getCrIdx() {
		return crIdx;
	}
	public void setCrIdx(String crIdx) {
		this.crIdx = crIdx;
	}
	public String getMdIdx() {
		return mdIdx;
	}
	public void setMdIdx(String mdIdx) {
		this.mdIdx = mdIdx;
	}
	public String getOnselfDamageCover() {
		return onselfDamageCover;
	}
	public void setOnselfDamageCover(String onselfDamageCover) {
		this.onselfDamageCover = onselfDamageCover;
	}
	public String getPersonalCover() {
		return personalCover;
	}
	public void setPersonalCover(String personalCover) {
		this.personalCover = personalCover;
	}
	public String getPropertyDamageCover() {
		return propertyDamageCover;
	}
	public void setPropertyDamageCover(String propertyDamageCover) {
		this.propertyDamageCover = propertyDamageCover;
	}
	public String getCarDamageCover() {
		return carDamageCover;
	}
	public void setCarDamageCover(String carDamageCover) {
		this.carDamageCover = carDamageCover;
	}
	public String getInsuranceCopayment() {
		return insuranceCopayment;
	}
	public void setInsuranceCopayment(String insuranceCopayment) {
		this.insuranceCopayment = insuranceCopayment;
	}
	public String getFuelCode() {
		return fuelCode;
	}
	public void setFuelCode(String fuelCode) {
		this.fuelCode = fuelCode;
	}
	public String getRentFee() {
		return rentFee;
	}
	public void setRentFee(String rentFee) {
		this.rentFee = rentFee;
	}
	public String getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public String getDiscountFee() {
		return discountFee;
	}
	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}
	public String getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public String getAddFee() {
		return addFee;
	}
	public void setAddFee(String addFee) {
		this.addFee = addFee;
	}
	public String getPaymentTotalAmount() {
		return paymentTotalAmount;
	}
	public void setPaymentTotalAmount(String paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public String getPgCode() {
		return pgCode;
	}
	public void setPgCode(String pgCode) {
		this.pgCode = pgCode;
	}
	public String getPaymentTypeCode() {
		return paymentTypeCode;
	}
	public void setPaymentTypeCode(String paymentTypeCode) {
		this.paymentTypeCode = paymentTypeCode;
	}
	public String getPaymentKindCode() {
		return paymentKindCode;
	}
	public void setPaymentKindCode(String paymentKindCode) {
		this.paymentKindCode = paymentKindCode;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getPaymentKindName() {
		return paymentKindName;
	}
	public void setPaymentKindName(String paymentKindName) {
		this.paymentKindName = paymentKindName;
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMiSu() {
		return miSu;
	}
	public void setMiSu(String miSu) {
		this.miSu = miSu;
	}

	public String getReserveUserBirthday() {
		return reserveUserBirthday;
	}

	public void setReserveUserBirthday(String reserveUserBirthday) {
		this.reserveUserBirthday = reserveUserBirthday;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public String getSumPaymentAmount() {
		return sumPaymentAmount;
	}

	public void setSumPaymentAmount(String sumPaymentAmount) {
		this.sumPaymentAmount = sumPaymentAmount;
	}
}
