package com.ohdocha.admin.domain.reserve.payment;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("reserveInfoResponse")
public class DochaAdminPaymentInfoResponse extends CommonResponseDto {
	

	private String quIdx                ;
	private String rmIdx                ;
	private String qrIdx                ;
	private String rtIdx                ;
	private String crIdx                ;
	private String pyIdx                ;
	private String quoteCode            ;
	private String quoteStatus          ;
	private String longTermYn           ;
	private String carTypeCodeList      ;
	private String rentStartDay         ;
	private String rentEndDay           ;
	private String rentStartTime        ;
	private String rentEndTime          ;
	private String deliveryTypeCode     ;
	private String deliveryTypeValue    ;
	private String deliveryAddr         ;
	private String returnTypeCode       ;
	private String returnAddr           ;
	private String companyName          ;
	private String branchName           ;
	private String urIdx                ;
	private String regId                ;
	private String regDt                ;
	private String companyRegDt         ;
	private String modId                ;
	private String modDt                ;
	private String delYn                ;
	private String userId               ;
	private String userName             ;
	private String userBirthday         ;
	private String userGender           ;
	private String userContact1         ;
	private String userContact2         ;
	private String rentFee              ;
	private String paymentAmount        ;
	private String mdIdx                ;
	private String modelName            ;
	private String modelDetailName      ;
	private String fuelCode             ;
	private String transmissionCode     ;
	private String driveTypeCode        ;
	private String carTypeListValue 	;
	private String driveLicenseCode     ;
	private String manufacturerCode     ;
	private String colorName            ;
	private String fuelName             ;
	private String displacement         ;
	private String year                 ;
	private String mileage              ;
	private String maximumPassenger     ;
	private String shortTermFee         ;
	private String longTermFee          ;
	private String longTermDeposit      ;
	private String carStatusCode        ;
	private String reserveAbleYn        ;
	private String carRegDt             ;
	private String carNumber            ;
	private String carChassisNumber     ;
	private String imgIdx               ;
	private String carDriveLimit        ;
	private String ageLimit             ;
	private String garageAddr           ;
	private String carEtc               ;
	private String manufacturerName     ;
	private String insuranceFee         ;
	private String carDamageCover       ;
	private String onselfDamageCover    ;
	private String personalCover        ;
	private String propertyDamageCover  ;
	private String driveCareerLimit     ;
	private String insuranceCopayment   ;
	private String carDeposit           ;
	private String approvalNumber       ;
	private String monthlyFee           ;
	private String dailyFee             ;
	private String qrCount              ;
	private String companyCount;
	private String paymentTotalAmount	;
	
	private String reserveTypeCode;
	private String reserveStatusCode;
	private String reserveStatusCodeValue;
	private String longtermYn;
	private String reserveUserName;
	private String reserveUserEmail;
	private String reserveUserContact1;
	private String reserveUserBirthday;
	private String reserveUserGender;
	private String cartypeCode;
	private String reserveDate;
	private String paymentDate;
	private String disCountFee;
	private String cancelFee;
	private String cancelAmount;
	private String cancelReason;
	private String reserveChannel;
	private String rtPIdx;
	private String companyZipcode;
	private String companyAddress;
	private String companyAddressDetail;
	private String lat;
	private String lng;
	private String establishedDate;
	private String companyRegistrationNumber;
	private String companyRegistrationImg;
	private String accountBank;
	private String accountNumber;
	private String accountHolder;
	private String accountImgIdx;
	private String longtermRentYn;
	private String shorttermRentYn;
	private String allianceStatus;
	private String branchAbleYn;
	private String carCount;
	private String companyContact1;
	private String companyContact2;
	private String alarmYn;
	
	private String pgCode;
	private String paymentTypeCode;
	private String paymentKindCode;
	
	private String dailyStandardPay;
	private String monthlyStandardPay;
	private String longtermDeposit;
	private String dailyMaxRate;
	private String monthlyMaxRate;
	private String month3Deposit;
	private String month6Depositmonth9Deposit;
	private String month12Deposit;
	private String deliveryStandardPay;
	private String deliveryAddPay;
	private String deliveryMaxRate;
	private String dailyYn;
	private String optionDetailCode;
	private String acDt;
	private String acPay;

	// 제1 운전자 정보
	private String firstDriverName;
	private String firstDriverGender;
	private String firstDriverContact;
	private String firstDriverBirthDay;
	private String firstDriverLicenseCode;
	private String firstDriverLicenseNumber;
	private String firstDriverExpirationDate;
	private String firstDriverLicenseIsDate;
	
	// 제2 운전자 정보
	private String secondDriverName;
	private String secondDriverGender;
	private String secondDriverContact;
	private String secondDriverBirthDay;
	private String secondDriverLicenseCode;
	private String secondDriverLicenseNumber;
	private String secondDriverExpirationDate;
	private String secondDriverLicenseIsDate;
	
	private String rentTotalFee;
	private String deliveryFee;
	private String addFee;
	private String repatmentFee;
	
	private String payAvg;
	
	private String payCount;
	private String totalPayCount;
	private String sumPaymentAmount;
	private String nextPaymentDay;
	

	public String getQuIdx() {
		return quIdx;
	}
	public void setQuIdx(String quIdx) {
		this.quIdx = quIdx;
	}
	public String getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(String rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getQrIdx() {
		return qrIdx;
	}
	public void setQrIdx(String qrIdx) {
		this.qrIdx = qrIdx;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getCrIdx() {
		return crIdx;
	}
	public void setCrIdx(String crIdx) {
		this.crIdx = crIdx;
	}
	public String getPyIdx() {
		return pyIdx;
	}
	public void setPyIdx(String pyIdx) {
		this.pyIdx = pyIdx;
	}
	public String getQuoteCode() {
		return quoteCode;
	}
	public void setQuoteCode(String quoteCode) {
		this.quoteCode = quoteCode;
	}
	public String getQuoteStatus() {
		return quoteStatus;
	}
	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}
	public String getLongTermYn() {
		return longTermYn;
	}
	public void setLongTermYn(String longTermYn) {
		this.longTermYn = longTermYn;
	}
	public String getCarTypeCodeList() {
		return carTypeCodeList;
	}
	public void setCarTypeCodeList(String carTypeCodeList) {
		this.carTypeCodeList = carTypeCodeList;
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
	public String getDeliveryTypeCode() {
		return deliveryTypeCode;
	}
	public void setDeliveryTypeCode(String deliveryTypeCode) {
		this.deliveryTypeCode = deliveryTypeCode;
	}
	public String getDeliveryTypeValue() {
		return deliveryTypeValue;
	}
	public void setDeliveryTypeValue(String deliveryTypeValue) {
		this.deliveryTypeValue = deliveryTypeValue;
	}
	public String getDeliveryAddr() {
		return deliveryAddr;
	}
	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	public String getReturnTypeCode() {
		return returnTypeCode;
	}
	public void setReturnTypeCode(String returnTypeCode) {
		this.returnTypeCode = returnTypeCode;
	}
	public String getReturnAddr() {
		return returnAddr;
	}
	public void setReturnAddr(String returnAddr) {
		this.returnAddr = returnAddr;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUrIdx() {
		return urIdx;
	}
	public void setUrIdx(String urIdx) {
		this.urIdx = urIdx;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getCompanyRegDt() {
		return companyRegDt;
	}
	public void setCompanyRegDt(String companyRegDt) {
		this.companyRegDt = companyRegDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getuserBirthday() {
		return userBirthday;
	}
	public void setuserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserContact1() {
		return userContact1;
	}
	public void setUserContact1(String userContact1) {
		this.userContact1 = userContact1;
	}
	public String getUserContact2() {
		return userContact2;
	}
	public void setUserContact2(String userContact2) {
		this.userContact2 = userContact2;
	}
	public String getRentFee() {
		return rentFee;
	}
	public void setRentFee(String rentFee) {
		this.rentFee = rentFee;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getMdIdx() {
		return mdIdx;
	}
	public void setMdIdx(String mdIdx) {
		this.mdIdx = mdIdx;
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
	public String getFuelCode() {
		return fuelCode;
	}
	public void setFuelCode(String fuelCode) {
		this.fuelCode = fuelCode;
	}
	public String getTransmissionCode() {
		return transmissionCode;
	}
	public void setTransmissionCode(String transmissionCode) {
		this.transmissionCode = transmissionCode;
	}
	public String getDriveTypeCode() {
		return driveTypeCode;
	}
	public void setDriveTypeCode(String driveTypeCode) {
		this.driveTypeCode = driveTypeCode;
	}
	public String getCarTypeListValue() {
		return carTypeListValue;
	}
	public void setCarTypeListValue(String carTypeListValue) {
		this.carTypeListValue = carTypeListValue;
	}
	public String getDriveLicenseCode() {
		return driveLicenseCode;
	}
	public void setDriveLicenseCode(String driveLicenseCode) {
		this.driveLicenseCode = driveLicenseCode;
	}
	public String getManufacturerCode() {
		return manufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getFuelName() {
		return fuelName;
	}
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getMaximumPassenger() {
		return maximumPassenger;
	}
	public void setMaximumPassenger(String maximumPassenger) {
		this.maximumPassenger = maximumPassenger;
	}
	public String getShortTermFee() {
		return shortTermFee;
	}
	public void setShortTermFee(String shortTermFee) {
		this.shortTermFee = shortTermFee;
	}
	public String getLongTermFee() {
		return longTermFee;
	}
	public void setLongTermFee(String longTermFee) {
		this.longTermFee = longTermFee;
	}
	public String getLongTermDeposit() {
		return longTermDeposit;
	}
	public void setLongTermDeposit(String longTermDeposit) {
		this.longTermDeposit = longTermDeposit;
	}
	public String getCarStatusCode() {
		return carStatusCode;
	}
	public void setCarStatusCode(String carStatusCode) {
		this.carStatusCode = carStatusCode;
	}
	public String getReserveAbleYn() {
		return reserveAbleYn;
	}
	public void setReserveAbleYn(String reserveAbleYn) {
		this.reserveAbleYn = reserveAbleYn;
	}
	public String getCarRegDt() {
		return carRegDt;
	}
	public void setCarRegDt(String carRegDt) {
		this.carRegDt = carRegDt;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarChassisNumber() {
		return carChassisNumber;
	}
	public void setCarChassisNumber(String carChassisNumber) {
		this.carChassisNumber = carChassisNumber;
	}
	public String getImgIdx() {
		return imgIdx;
	}
	public void setImgIdx(String imgIdx) {
		this.imgIdx = imgIdx;
	}
	public String getCarDriveLimit() {
		return carDriveLimit;
	}
	public void setCarDriveLimit(String carDriveLimit) {
		this.carDriveLimit = carDriveLimit;
	}
	public String getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}
	public String getGarageAddr() {
		return garageAddr;
	}
	public void setGarageAddr(String garageAddr) {
		this.garageAddr = garageAddr;
	}
	public String getCarEtc() {
		return carEtc;
	}
	public void setCarEtc(String carEtc) {
		this.carEtc = carEtc;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getInsuranceFee() {
		return insuranceFee;
	}
	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}
	public String getCarDamageCover() {
		return carDamageCover;
	}
	public void setCarDamageCover(String carDamageCover) {
		this.carDamageCover = carDamageCover;
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
	public String getDriveCareerLimit() {
		return driveCareerLimit;
	}
	public void setDriveCareerLimit(String driveCareerLimit) {
		this.driveCareerLimit = driveCareerLimit;
	}
	public String getInsuranceCopayment() {
		return insuranceCopayment;
	}
	public void setInsuranceCopayment(String insuranceCopayment) {
		this.insuranceCopayment = insuranceCopayment;
	}
	public String getCarDeposit() {
		return carDeposit;
	}
	public void setCarDeposit(String carDeposit) {
		this.carDeposit = carDeposit;
	}
	public String getApprovalNumber() {
		return approvalNumber;
	}
	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}
	public String getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(String monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	public String getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(String dailyFee) {
		this.dailyFee = dailyFee;
	}
	public String getQrCount() {
		return qrCount;
	}
	public void setQrCount(String qrCount) {
		this.qrCount = qrCount;
	}
	public String getReserveTypeCode() {
		return reserveTypeCode;
	}
	public void setReserveTypeCode(String reserveTypeCode) {
		this.reserveTypeCode = reserveTypeCode;
	}
	public String getReserveStatusCode() {
		return reserveStatusCode;
	}
	public void setReserveStatusCode(String reserveStatusCode) {
		this.reserveStatusCode = reserveStatusCode;
	}
	public String getReserveStatusCodeValue() {
		return reserveStatusCodeValue;
	}
	public void setReserveStatusCodeValue(String reserveStatusCodeValue) {
		this.reserveStatusCodeValue = reserveStatusCodeValue;
	}
	public String getLongtermYn() {
		return longtermYn;
	}
	public void setLongtermYn(String longtermYn) {
		this.longtermYn = longtermYn;
	}
	public String getReserveUserName() {
		return reserveUserName;
	}
	public void setReserveUserName(String reserveUserName) {
		this.reserveUserName = reserveUserName;
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
	public String getreserveUserBirthday() {
		return reserveUserBirthday;
	}
	public void setreserveUserBirthday(String reserveUserBirthday) {
		this.reserveUserBirthday = reserveUserBirthday;
	}
	public String getReserveUserGender() {
		return reserveUserGender;
	}
	public void setReserveUserGender(String reserveUserGender) {
		this.reserveUserGender = reserveUserGender;
	}
	public String getCartypeCode() {
		return cartypeCode;
	}
	public void setCartypeCode(String cartypeCode) {
		this.cartypeCode = cartypeCode;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getDisCountFee() {
		return disCountFee;
	}
	public void setDisCountFee(String disCountFee) {
		this.disCountFee = disCountFee;
	}
	public String getCancelFee() {
		return cancelFee;
	}
	public void setCancelFee(String cancelFee) {
		this.cancelFee = cancelFee;
	}
	public String getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(String cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getReserveChannel() {
		return reserveChannel;
	}
	public void setReserveChannel(String reserveChannel) {
		this.reserveChannel = reserveChannel;
	}
	public String getRtPIdx() {
		return rtPIdx;
	}
	public void setRtPIdx(String rtPIdx) {
		this.rtPIdx = rtPIdx;
	}
	public String getCompanyZipcode() {
		return companyZipcode;
	}
	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyAddressDetail() {
		return companyAddressDetail;
	}
	public void setCompanyAddressDetail(String companyAddressDetail) {
		this.companyAddressDetail = companyAddressDetail;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getEstablishedDate() {
		return establishedDate;
	}
	public void setEstablishedDate(String establishedDate) {
		this.establishedDate = establishedDate;
	}
	public String getCompanyRegistrationNumber() {
		return companyRegistrationNumber;
	}
	public void setCompanyRegistrationNumber(String companyRegistrationNumber) {
		this.companyRegistrationNumber = companyRegistrationNumber;
	}
	public String getCompanyRegistrationImg() {
		return companyRegistrationImg;
	}
	public void setCompanyRegistrationImg(String companyRegistrationImg) {
		this.companyRegistrationImg = companyRegistrationImg;
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
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public String getAccountImgIdx() {
		return accountImgIdx;
	}
	public void setAccountImgIdx(String accountImgIdx) {
		this.accountImgIdx = accountImgIdx;
	}
	public String getLongtermRentYn() {
		return longtermRentYn;
	}
	public void setLongtermRentYn(String longtermRentYn) {
		this.longtermRentYn = longtermRentYn;
	}
	public String getShorttermRentYn() {
		return shorttermRentYn;
	}
	public void setShorttermRentYn(String shorttermRentYn) {
		this.shorttermRentYn = shorttermRentYn;
	}
	public String getAllianceStatus() {
		return allianceStatus;
	}
	public void setAllianceStatus(String allianceStatus) {
		this.allianceStatus = allianceStatus;
	}
	public String getBranchAbleYn() {
		return branchAbleYn;
	}
	public void setBranchAbleYn(String branchAbleYn) {
		this.branchAbleYn = branchAbleYn;
	}
	public String getCarCount() {
		return carCount;
	}
	public void setCarCount(String carCount) {
		this.carCount = carCount;
	}
	public String getCompanyContact1() {
		return companyContact1;
	}
	public void setCompanyContact1(String companyContact1) {
		this.companyContact1 = companyContact1;
	}
	public String getCompanyContact2() {
		return companyContact2;
	}
	public void setCompanyContact2(String companyContact2) {
		this.companyContact2 = companyContact2;
	}
	public String getAlarmYn() {
		return alarmYn;
	}
	public void setAlarmYn(String alarmYn) {
		this.alarmYn = alarmYn;
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
	public String getLongtermDeposit() {
		return longtermDeposit;
	}
	public void setLongtermDeposit(String longtermDeposit) {
		this.longtermDeposit = longtermDeposit;
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
	public String getMonth6Depositmonth9Deposit() {
		return month6Depositmonth9Deposit;
	}
	public void setMonth6Depositmonth9Deposit(String month6Depositmonth9Deposit) {
		this.month6Depositmonth9Deposit = month6Depositmonth9Deposit;
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
	public String getDailyYn() {
		return dailyYn;
	}
	public void setDailyYn(String dailyYn) {
		this.dailyYn = dailyYn;
	}
	public String getOptionDetailCode() {
		return optionDetailCode;
	}
	public void setOptionDetailCode(String optionDetailCode) {
		this.optionDetailCode = optionDetailCode;
	}
	public String getAcDt() {
		return acDt;
	}
	public void setAcDt(String acDt) {
		this.acDt = acDt;
	}
	public String getAcPay() {
		return acPay;
	}
	public void setAcPay(String acPay) {
		this.acPay = acPay;
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
	public String getRentTotalFee() {
		return rentTotalFee;
	}
	public void setRentTotalFee(String rentTotalFee) {
		this.rentTotalFee = rentTotalFee;
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
	public String getRepatmentFee() {
		return repatmentFee;
	}
	public void setRepatmentFee(String repatmentFee) {
		this.repatmentFee = repatmentFee;
	}
	public String getCompanyCount() {
		return companyCount;
	}
	public void setCompanyCount(String companyCount) {
		this.companyCount = companyCount;
	}
	public String getPayAvg() {
		return payAvg;
	}
	public void setPayAvg(String payAvg) {
		this.payAvg = payAvg;
	}
	public String getPayCount() {
		return payCount;
	}
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}
	public String getSumPaymentAmount() {
		return sumPaymentAmount;
	}
	public void setSumPaymentAmount(String sumPaymentAmount) {
		this.sumPaymentAmount = sumPaymentAmount;
	}
	public String getNextPaymentDay() {
		return nextPaymentDay;
	}
	public void setNextPaymentDay(String nextPaymentDay) {
		this.nextPaymentDay = nextPaymentDay;
	}
	public String getPaymentTotalAmount() {
		return paymentTotalAmount;
	}
	public void setPaymentTotalAmount(String paymentTotalAmount) {
		this.paymentTotalAmount = paymentTotalAmount;
	}
}                         