package com.ohdocha.admin.domain.reserve.reserveInfoMnt;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
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
	private String nextPaymentDay;
	private String etc;
	private String merchantUid; //결제id
	
	private String flag;
	private String msg;

	private String modDt;

}
