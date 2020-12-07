package com.ohdocha.admin.domain.car.regcar;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("regCarDetailRequest")
public class DochaAdminRegCarDetailRequest extends CommonRequestDto {
	
	//DC_CAR_INRO
	private String crIdx              ; //차량idx
	private String rtIdx              ; //제휴사idx
    private String companyName        ; //회사명
    private String branchName         ; //지점명
	private String year               ; //연식
	private int mdIdx              ; //모델idx
	private String modelName    	  ; //모델명
	private String modelDetailName    ; //모델상세명
	private String fuelCode           ; //연료구분code
	private String carRegDt           ; //차량등록일
	private String colorName          ; //색상
	private String carNumber          ; //차량번호
	private String carChassisNumber   ; //차대번호
	private String mileage            ; //주행거리
	private String reserveAbleYn      ; //차량예약가능여부code
	private String delYn              ; //차량삭제여부
	/* 차량 추가 정보 */
	private String transmissionCode   ; //변속기구분code
	private String driveTypeCode      ; //구동방식구분code
	private String cartypeCode        ; //차종code
	private String driveLicenseCode   ; //면허구분code
	private String manufacturerCode   ; //제조사code
	private String displacement       ; //배기량
	private String maximumPassenger   ; //승차인원
	private String imgIdx             ; //이미지idx
	private String carDriveLimit      ; //주행거리제한
	private String ageLimit           ; //대여연령제한(장기,단기)
	private String garageAddr         ; //차고지주소
	private String carEtc             ; //비고
	/* 보험정보 */
	private String ciIdx              ; //보험정보idx
	private String personalCover      ; //대인보상금액
	private String propertyDamageCover; //대물보상금액
	private String onselfDamageCover  ; //자손보상금액
	private String insuranceCopayment ; //고객부담금(보험료)
	private String carDamageCover     ; //자차보상금(고객부담금)
	private String insuranceCopayment2; //고객부담금2(보험료)
	private String carDamageCover2    ; //자차보상금2(고객부담금)
	private String insuranceCopayment3; //고객부담금3(보험료)
	private String carDamageCover3    ; //자차보상금3(고객부담금)
	private String insuranceCopayment4; //고객부담금4(보험료)
	private String carDamageCover4    ; //자차보상금4(고객부담금)
	private String carDamage1Yn       ; //자차1사용유무
	private String carDamage2Yn       ; //자차2사용유무
	private String carDamage3Yn       ; //자차3사용유무
	private String carDamage4Yn       ; //자차4사용유무
	private String carDeposit		  ; //월별 보증듬
	
	private String pyIdx              ; //요금idx
	private String dailyStandardPay   ; //일기본요금
	private String dailyMaxRate       ; //일대여최대할인율
	private String monthlyStandardPay ; //월기본요금
	private String monthlyMaxRate     ; //월대여최대할인율
	private String month3Deposit      ; //3개월보증금
	private String month6Deposit      ; //6개월보증금
	private String month9Deposit      ; //9개월보증금
	private String month12Deposit     ; //12개월보증금
	private String deliveryStandardPay; //배달기본요금
	private String deliveryAddPay     ; //배달10KM단위추가요금
	private String deliveryMaxRate    ; //배달최대할인율
	private String dailyYn            ; //일대여사용유무
	private String monthlyYn          ; //월대여사용유무
	private String closedvehicleYn    ; //휴차일사용유무
	private String perIdx	          ; //기간요금idx

	private String regId              ; //등록자
	private String regDt              ; //등록일시
	private String modId              ; //수정자
	private String modDt              ; //수정일시

	private String suspendStartDt     ; //휴차시작일
	private String suspendEndDt       ; //휴차종료일
	
	/* 요금계산기 */
	private String calRentStartDt	  ; //요금계산시작일시
	private String calRentEndDt		  ; //요금계산종료일시
	
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getMdIdx() {
		return mdIdx;
	}
	public void setMdIdx(int mdIdx) {
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
	public String getCarRegDt() {
		return carRegDt;
	}
	public void setCarRegDt(String carRegDt) {
		this.carRegDt = carRegDt;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
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
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getReserveAbleYn() {
		return reserveAbleYn;
	}
	public void setReserveAbleYn(String reserveAbleYn) {
		this.reserveAbleYn = reserveAbleYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
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
	public String getCartypeCode() {
		return cartypeCode;
	}
	public void setCartypeCode(String cartypeCode) {
		this.cartypeCode = cartypeCode;
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
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getMaximumPassenger() {
		return maximumPassenger;
	}
	public void setMaximumPassenger(String maximumPassenger) {
		this.maximumPassenger = maximumPassenger;
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
	public String getCiIdx() {
		return ciIdx;
	}
	public void setCiIdx(String ciIdx) {
		this.ciIdx = ciIdx;
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
	public String getOnselfDamageCover() {
		return onselfDamageCover;
	}
	public void setOnselfDamageCover(String onselfDamageCover) {
		this.onselfDamageCover = onselfDamageCover;
	}
	public String getInsuranceCopayment() {
		return insuranceCopayment;
	}
	public void setInsuranceCopayment(String insuranceCopayment) {
		this.insuranceCopayment = insuranceCopayment;
	}
	public String getCarDamageCover() {
		return carDamageCover;
	}
	public void setCarDamageCover(String carDamageCover) {
		this.carDamageCover = carDamageCover;
	}
	public String getInsuranceCopayment2() {
		return insuranceCopayment2;
	}
	public void setInsuranceCopayment2(String insuranceCopayment2) {
		this.insuranceCopayment2 = insuranceCopayment2;
	}
	public String getCarDamageCover2() {
		return carDamageCover2;
	}
	public void setCarDamageCover2(String carDamageCover2) {
		this.carDamageCover2 = carDamageCover2;
	}
	public String getInsuranceCopayment3() {
		return insuranceCopayment3;
	}
	public void setInsuranceCopayment3(String insuranceCopayment3) {
		this.insuranceCopayment3 = insuranceCopayment3;
	}
	public String getCarDamageCover3() {
		return carDamageCover3;
	}
	public void setCarDamageCover3(String carDamageCover3) {
		this.carDamageCover3 = carDamageCover3;
	}
	public String getInsuranceCopayment4() {
		return insuranceCopayment4;
	}
	public void setInsuranceCopayment4(String insuranceCopayment4) {
		this.insuranceCopayment4 = insuranceCopayment4;
	}
	public String getCarDamageCover4() {
		return carDamageCover4;
	}
	public void setCarDamageCover4(String carDamageCover4) {
		this.carDamageCover4 = carDamageCover4;
	}
	public String getCarDamage1Yn() {
		return carDamage1Yn;
	}
	public void setCarDamage1Yn(String carDamage1Yn) {
		this.carDamage1Yn = carDamage1Yn;
	}
	public String getCarDamage2Yn() {
		return carDamage2Yn;
	}
	public void setCarDamage2Yn(String carDamage2Yn) {
		this.carDamage2Yn = carDamage2Yn;
	}
	public String getCarDamage3Yn() {
		return carDamage3Yn;
	}
	public void setCarDamage3Yn(String carDamage3Yn) {
		this.carDamage3Yn = carDamage3Yn;
	}
	public String getCarDamage4Yn() {
		return carDamage4Yn;
	}
	public void setCarDamage4Yn(String carDamage4Yn) {
		this.carDamage4Yn = carDamage4Yn;
	}
	public String getCarDeposit() {
		return carDeposit;
	}
	public void setCarDeposit(String carDeposit) {
		this.carDeposit = carDeposit;
	}
	public String getPyIdx() {
		return pyIdx;
	}
	public void setPyIdx(String pyIdx) {
		this.pyIdx = pyIdx;
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
	public String getClosedvehicleYn() {
		return closedvehicleYn;
	}
	public void setClosedvehicleYn(String closedvehicleYn) {
		this.closedvehicleYn = closedvehicleYn;
	}
	public String getPerIdx() {
		return perIdx;
	}
	public void setPerIdx(String perIdx) {
		this.perIdx = perIdx;
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
	public String getCalRentStartDt() {
		return calRentStartDt;
	}
	public void setCalRentStartDt(String calRentStartDt) {
		this.calRentStartDt = calRentStartDt;
	}
	public String getCalRentEndDt() {
		return calRentEndDt;
	}
	public void setCalRentEndDt(String calRentEndDt) {
		this.calRentEndDt = calRentEndDt;
	}
	public String getSuspendStartDt() {
		return suspendStartDt;
	}
	public void setSuspendStartDt(String suspendStartDt) {
		this.suspendStartDt = suspendStartDt;
	}
	public String getSuspendEndDt() {
		return suspendEndDt;
	}
	public void setSuspendEndDt(String suspendEndDt) {
		this.suspendEndDt = suspendEndDt;
	}
	

	
}
