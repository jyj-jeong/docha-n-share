package com.ohdocha.admin.domain.reserve.payment;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
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
	private String longtermYn           ;
	private String carTypeCodeList      ;
	private String rentStartDay         ;
	private String rentEndDay           ;
	private String rentStartTime        ;
	private String rentEndTime          ;
	private String periodDt				;
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

}                         