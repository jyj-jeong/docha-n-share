package com.ohdocha.admin.domain.user;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Alias("userInfoDetailResponse")
public class DochaAdminUserInfoDetailResponse extends CommonResponseDto {
	
	@NotNull
    private String urIdx;						// 회원idx
	
	@NotNull
	private String userId;						// 회원ID
    private String userPassword;				// 비밀번호
    private String userStatusCode;				// 회원상태코드
    
    @NotNull
    private String userName;					// 이름
    
    @NotNull
    private String userBirthday;				// 생년월일
    
    @NotNull
    private String userContact1;				// 연락처1
    private String userContact2;				// 연락처2
    private String userGender;					// 성별CODE
    private String userZipcode;					// 우편번호
    private String userAddress;					// 주소
    private String userAddressDetail;			// 상세주소
    private String userIdentityAuthYn;			// 본인인증여부
    private String userIdentityAuthDate;		// 인증일자
    
    @NotNull
    private String userCi;						// CI
    private String userDi;						// DI
    private String userNationalCode;			// 내/외국인구분
    private String userCertType;				// 인증서유형
    private String userGradeCode;				// 회원등급
    private String userRole;					// 멤버역할(ROLE)
    private String rtIdx;						// 회원사idx
    private String userGroupCode;				// 회원분류코드
    private String userLicenseOwnYn;			// 면허소유여부
    private String userPayRegisterYn;			// 결제수단등록여부
    private String corporationIdx;				// 법인idx
    private String userPushAgreeYn;				// PUSH동의여부
    private String joinChannel;    				// 가입경로
    private String socialLoginPath;				// 연동로그인경로
    private String socialLoginEmail;			// 연동로그인메일
    private String userWithdrawDate;			// 회원탈퇴일시
    private int loginFailCount;					// 로그인시도횟수
    private String regDt;						// 등록일시
    private String regId;						// 등록자
    private String modDt;						// 수정일시
    private String modId;						// 수정자
    
    //면허정보dto 시작
    private String ulIdx             ;          // 회원iDX
    private String licenseNumber     ;          // 면허번호
    private String licenseCode       ;          // 면허종류CODE
    private String licenseLocation   ;          // 면허지역
    private String licenseExpiration ;          // 갱신기간
    private String licenseIssueDt   ;          // 발급일
    private String licenseOwnerName  ;          // 면허소유자명
    private String useYn;						//사용여부

    private int reserveCnt;

	public String getUrIdx() {
		return urIdx;
	}
	public void setUrIdx(String urIdx) {
		this.urIdx = urIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserStatusCode() {
		return userStatusCode;
	}
	public void setUserStatusCode(String userStatusCode) {
		this.userStatusCode = userStatusCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserZipcode() {
		return userZipcode;
	}
	public void setUserZipcode(String userZipcode) {
		this.userZipcode = userZipcode;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserAddressDetail() {
		return userAddressDetail;
	}
	public void setUserAddressDetail(String userAddressDetail) {
		this.userAddressDetail = userAddressDetail;
	}
	public String getUserIdentityAuthYn() {
		return userIdentityAuthYn;
	}
	public void setUserIdentityAuthYn(String userIdentityAuthYn) {
		this.userIdentityAuthYn = userIdentityAuthYn;
	}
	public String getUserIdentityAuthDate() {
		return userIdentityAuthDate;
	}
	public void setUserIdentityAuthDate(String userIdentityAuthDate) {
		this.userIdentityAuthDate = userIdentityAuthDate;
	}
	public String getUserCi() {
		return userCi;
	}
	public void setUserCi(String userCi) {
		this.userCi = userCi;
	}
	public String getUserDi() {
		return userDi;
	}
	public void setUserDi(String userDi) {
		this.userDi = userDi;
	}
	public String getUserNationalCode() {
		return userNationalCode;
	}
	public void setUserNationalCode(String userNationalCode) {
		this.userNationalCode = userNationalCode;
	}
	public String getUserCertType() {
		return userCertType;
	}
	public void setUserCertType(String userCertType) {
		this.userCertType = userCertType;
	}
	public String getUserGradeCode() {
		return userGradeCode;
	}
	public void setUserGradeCode(String userGradeCode) {
		this.userGradeCode = userGradeCode;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	public String getUserLicenseOwnYn() {
		return userLicenseOwnYn;
	}
	public void setUserLicenseOwnYn(String userLicenseOwnYn) {
		this.userLicenseOwnYn = userLicenseOwnYn;
	}
	public String getUserPayRegisterYn() {
		return userPayRegisterYn;
	}
	public void setUserPayRegisterYn(String userPayRegisterYn) {
		this.userPayRegisterYn = userPayRegisterYn;
	}
	public String getCorporationIdx() {
		return corporationIdx;
	}
	public void setCorporationIdx(String corporationIdx) {
		this.corporationIdx = corporationIdx;
	}
	public String getUserPushAgreeYn() {
		return userPushAgreeYn;
	}
	public void setUserPushAgreeYn(String userPushAgreeYn) {
		this.userPushAgreeYn = userPushAgreeYn;
	}
	public String getJoinChannel() {
		return joinChannel;
	}
	public void setJoinChannel(String joinChannel) {
		this.joinChannel = joinChannel;
	}
	public String getSocialLoginPath() {
		return socialLoginPath;
	}
	public void setSocialLoginPath(String socialLoginPath) {
		this.socialLoginPath = socialLoginPath;
	}
	public String getSocialLoginEmail() {
		return socialLoginEmail;
	}
	public void setSocialLoginEmail(String socialLoginEmail) {
		this.socialLoginEmail = socialLoginEmail;
	}
	public String getUserWithdrawDate() {
		return userWithdrawDate;
	}
	public void setUserWithdrawDate(String userWithdrawDate) {
		this.userWithdrawDate = userWithdrawDate;
	}
	public int getLoginFailCount() {
		return loginFailCount;
	}
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
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
	public int getReserveCnt() {
		return reserveCnt;
	}
	public void setReserveCnt(int reserveCnt) {
		this.reserveCnt = reserveCnt;
	}

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
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getuserBirthday() {
		return userBirthday;
	}
	public void setuserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
    
}
