package com.ohdocha.admin.domain.rentCompany;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("DcRentCompanyAbleareaRequest")
public class DochaAdminDcRentCompanyAbleareaRequest extends CommonRequestDto {
	private String raIdx;			/*배달지역idx*/
	private String rtIdx;			/*제휴사idx*/
	private String lat;				/*위도*/
	private String lng;				/*경도*/
	private String addrDepth1;
	private String addrDepth2;
	private String addrDepth3;
	private String addrDepth4;
	private String latlng;			/*위,경도 데이터*/
	private String raGbnCode;		/*장단기구분코드*/
	private String raGbnSt;
	private String raGbnLt;
	private String regId;			/*등록자*/
	private String regDt;			/*등록시간*/
	private String modId;			/*수정자*/
	private String modDt;			/*수정시간*/
	private String delYn; 			/*삭제여부*/
	private String dataNewYn; 		/*신규/추가 구분자 [N:신규, A:추가]*/
	
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
	public String getDataNewYn() {
		return dataNewYn;
	}
	public void setDataNewYn(String dataNewYn) {
		this.dataNewYn = dataNewYn;
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

	public String getRaGbnSt() {
		return raGbnSt;
	}

	public void setRaGbnSt(String raGbnSt) {
		this.raGbnSt = raGbnSt;
	}

	public String getRaGbnLt() {
		return raGbnLt;
	}

	public void setRaGbnLt(String raGbnLt) {
		this.raGbnLt = raGbnLt;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
}
