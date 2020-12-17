package com.ohdocha.admin.domain.car.plan.insuranceTemplate;

import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.text.DecimalFormat;

@Data
@Getter
@Setter
@Alias("insuranceTemplateResponse")
public class DochaAdminInsuranceTemplateResponse extends CommonResponseDto {
	
	private String ciIdx                 ;   //보험정보idx
	private String ciTIdx                 ;   //보험정보idx
	private String crIdx                 ;   //차량idx
	private String insuranceFee          ;   //보험요금
	private String carDamageCover        ;   //자차보상금액
	private String onselfDamageCover     ;   //자손보상금액
	private String personalCover         ;   //대인보상금액
	private String propertyDamageCover   ;   //대물보상금액
	private String insuranceCopayment    ;   //고객부담금
	private String ageLimit              ;   //연령제한(장기,단기)
	private String driveCareerLimit      ;   //운전경력제한
	private String regId                 ;   //등록자
	private String regDt                 ;   //등록일시
	private String modId                 ;   //수정자
	private String modDt                 ;   //수정일시
	private String delYn                 ;   //삭제여부
	private String rtIdx                 ;   //회원사idx
	private String carDamageCover2       ;   //자차보상금액2
	private String insuranceCopayment2   ;   //고객부담금2
	private String carDamageCover3       ;   //자차보상금액3
	private String insuranceCopayment3   ;   //고객부담금3
	private String carDamageCover4       ;   //자차보상금액4
	private String insuranceCopayment4   ;   //고객부담금4
	private String carDamage1Yn          ;   //자차1사용유무
	private String carDamage2Yn          ;   //자차2사용유무
	private String carDamage3Yn          ;   //자차3사용유무
	private String carDamage4Yn          ;   //자차4사용유무
	private String ciEtc                 ;   //비고(템플릿제목)

	private String companyName		     ;  //회원사 이름
	private String branchName		     ;  //지점 이름
	private int    settingCarCnt         ;  //설정 차량 대수

	public boolean numberCheck(String value){
		try {
			Double.parseDouble(value);
			return true;
		}catch (NumberFormatException e){
			return false;
		}
	}
}
