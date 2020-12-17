package com.ohdocha.admin.domain.car.regcar;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
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

	private String regDt;


}                                         
