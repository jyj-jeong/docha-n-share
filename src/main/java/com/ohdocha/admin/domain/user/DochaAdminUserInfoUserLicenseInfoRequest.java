package com.ohdocha.admin.domain.user;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Alias("userLicenseInfoRequest")
public class DochaAdminUserInfoUserLicenseInfoRequest extends CommonRequestDto {
	
	private String urIdx 			 ; 			// 회원 IDX
    private String ulIdx             ;          // 면허정보IDX
    private String licenseNumber     ;          // 면허번호
    private String licenseCode       ;          // 면허종류CODE
    private String licenseExpiration ;          // 갱신기간
    private String licenseIssueDt    ;          // 발급일
    private String licenseOwnerName  ;          // 면허소유자명
    private String licenseLocation   ;          // 면허지역
	private String licenseImageName  ;          // 면허사진
    private String licenseNumber2    ;          // 면허번호2
    private String licenseUserBirthday;         // 생년월일
    private String licenseUserName   ;          // 회원이름
    private String regDt			 ;		    // 등록일
    private String regId			 ;          // 등록자
    private String modDt			 ;          // 수정일
    private String modId			 ;          // 수정자
    
	public String getUlIdx() {
		return ulIdx;
	}
	public void setUlIdx(String ulIdx) {
		this.ulIdx = ulIdx;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getLicenseCode() {
		return licenseCode;
	}
	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}
	public String getLicenseLocation() {
		return licenseLocation;
	}
	public void setLicenseLocation(String licenseLocation) {
		this.licenseLocation = licenseLocation;
	}
	public String getLicenseExpiration() {
		return licenseExpiration;
	}
	public void setLicenseExpiration(String licenseExpiration) {
		this.licenseExpiration = licenseExpiration;
	}
	public String getLicenseIssueDt() {
		return licenseIssueDt;
	}
	public void setLicenseIssueDt(String licenseIssueDt) {
		this.licenseIssueDt = licenseIssueDt;
	}
	public String getLicenseOwnerName() {
		return licenseOwnerName;
	}
	public void setLicenseOwnerName(String licenseOwnerName) {
		this.licenseOwnerName = licenseOwnerName;
	}
	public String getLicenseImageName() {
		return licenseImageName;
	}
	public void setLicenseImageName(String licenseImageName) {
		this.licenseImageName = licenseImageName;
	}
	public String getLicenseNumber2() {
		return licenseNumber2;
	}
	public void setLicenseNumber2(String licenseNumber2) {
		this.licenseNumber2 = licenseNumber2;
	}
	public String getLicenseUserBirthday() {
		return licenseUserBirthday;
	}
	public void setLicenseUserBirthday(String licenseUserBirthday) {
		this.licenseUserBirthday = licenseUserBirthday;
	}
	public String getLicenseUserName() {
		return licenseUserName;
	}
	public void setLicenseUserName(String licenseUserName) {
		this.licenseUserName = licenseUserName;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getUrIdx() {
		return urIdx;
	}
	public void setUrIdx(String urIdx) {
		this.urIdx = urIdx;
	}
	
}
