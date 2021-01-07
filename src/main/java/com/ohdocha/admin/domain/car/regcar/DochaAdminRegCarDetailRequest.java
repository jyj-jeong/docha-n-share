package com.ohdocha.admin.domain.car.regcar;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
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
	private String calRentPeriod      ; //대여기간

	/* 요금계산기 */
	private String calRentStartDt	  ; //요금계산시작일시
	private String calRentEndDt		  ; //요금계산종료일시

	private String[] deleteCarIdxList;  //차량 삭제시 idx 리스트

}
