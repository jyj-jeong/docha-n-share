package com.ohdocha.admin.domain.rentCompany;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("DcRentCompanyAbleareaResponse")
public class DochaAdminDcRentCompanyAbleareaResponse extends CommonResponseDto {
	private String raIdx;			/*배달지역idx*/
	private String rtIdx;			/*제휴사idx*/
	private String lat;				/*위도*/
	private String lng;				/*경도*/
	private String latlng;			/*위,경도 데이터*/
	private String addrDepth1;			/*도,시*/
	private String addrDepth2;			/*시,군,구*/
	private String addrDepth3;			/*동,읍,면*/
	private String addrDepth4;			/*리*/
	private String raGbnCode;		/*장단기구분코드*/
	private String raGbnLt;		    /*장단기 구분 카운트*/
	private String raGbnSt;		    /*장단기 구분 카운트*/
	private String regId;			/*등록자*/
	private String modId;			/*수정자*/
	private String delYn; 			/*삭제여부*/
	public String getRaIdx() {
		return raIdx;
	}
	public void setRaIdx(String raIdx) {
		this.raIdx = raIdx;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
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
	public String getLatlng() {
		return latlng;
	}
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}
	public String getRaGbnCode() {
		return raGbnCode;
	}
	public void setRaGbnCode(String raGbnCode) {
		this.raGbnCode = raGbnCode;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getAddrDepth1() {
		return addrDepth1;
	}

	public void setAddrDepth1(String addrDepth1) {
		this.addrDepth1 = addrDepth1;
	}

	public String getAddrDepth2() {
		return addrDepth2;
	}

	public void setAddrDepth2(String addrDepth2) {
		this.addrDepth2 = addrDepth2;
	}

	public String getAddrDepth3() {
		return addrDepth3;
	}

	public void setAddrDepth3(String addrDepth3) {
		this.addrDepth3 = addrDepth3;
	}

	public String getAddrDepth4() {
		return addrDepth4;
	}

	public void setAddrDepth4(String addrDepth4) {
		this.addrDepth4 = addrDepth4;
	}

	public String getRaGbnLt() {
		return raGbnLt;
	}

	public void setRaGbnLt(String raGbnLt) {
		this.raGbnLt = raGbnLt;
	}

	public String getRaGbnSt() {
		return raGbnSt;
	}

	public void setRaGbnSt(String raGbnSt) {
		this.raGbnSt = raGbnSt;
	}
}
