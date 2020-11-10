package com.ohdocha.admin.domain.car.plan.insuranceTemplate;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
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
	public String getCiIdx() {
		return ciIdx;
	}
	public void setCiIdx(String ciIdx) {
		this.ciIdx = ciIdx;
	}
	public String getCrIdx() {
		return crIdx;
	}
	public void setCrIdx(String crIdx) {
		this.crIdx = crIdx;
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
	public String getInsuranceCopayment() {
		return insuranceCopayment;
	}
	public void setInsuranceCopayment(String insuranceCopayment) {
		this.insuranceCopayment = insuranceCopayment;
	}
	public String getAgeLimit() {
		return ageLimit;
	}
	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}
	public String getDriveCareerLimit() {
		return driveCareerLimit;
	}
	public void setDriveCareerLimit(String driveCareerLimit) {
		this.driveCareerLimit = driveCareerLimit;
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
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getCarDamageCover2() {
		return carDamageCover2;
	}
	public void setCarDamageCover2(String carDamageCover2) {
		this.carDamageCover2 = carDamageCover2;
	}
	public String getInsuranceCopayment2() {
		return insuranceCopayment2;
	}
	public void setInsuranceCopayment2(String insuranceCopayment2) {
		this.insuranceCopayment2 = insuranceCopayment2;
	}
	public String getCarDamageCover3() {
		return carDamageCover3;
	}
	public void setCarDamageCover3(String carDamageCover3) {
		this.carDamageCover3 = carDamageCover3;
	}
	public String getInsuranceCopayment3() {
		return insuranceCopayment3;
	}
	public void setInsuranceCopayment3(String insuranceCopayment3) {
		this.insuranceCopayment3 = insuranceCopayment3;
	}
	public String getCarDamageCover4() {
		return carDamageCover4;
	}
	public void setCarDamageCover4(String carDamageCover4) {
		this.carDamageCover4 = carDamageCover4;
	}
	public String getInsuranceCopayment4() {
		return insuranceCopayment4;
	}
	public void setInsuranceCopayment4(String insuranceCopayment4) {
		this.insuranceCopayment4 = insuranceCopayment4;
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
	public String getCiEtc() {
		return ciEtc;
	}
	public void setCiEtc(String ciEtc) {
		this.ciEtc = ciEtc;
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
	public int getSettingCarCnt() {
		return settingCarCnt;
	}
	public void setSettingCarCnt(int settingCarCnt) {
		this.settingCarCnt = settingCarCnt;
	}
	public String getCiTIdx() {
		return ciTIdx;
	}
	public void setCiTIdx(String ciTIdx) {
		this.ciTIdx = ciTIdx;
	}

	
	
}
