package com.ohdocha.admin.domain.reserve.reserveInfoMnt;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("reserveInfoRequest")
public class DochaAdminReserveInfoRequest  implements Serializable {


	public DochaAdminReserveInfoRequest() {

	}

	private static final long serialVersionUID = 1L;

	private int page;				//시작페이지
	private int displayPageNum;		//몇개를 보여줄 것인가
	private int totalRowCount; 		//총 row 갯수
	private String rmIdx;
	private String userId;
	private String rtIdx;
	private String rtPIdx;
	private String userRole;
	private String crIdx;
	private String searchKeyWord;
	private String gbnStatus;
	private String gbnDay;
	private String gbnLocation;
	private String gbnReserve;
	private String gbnInput;
	private String gbnCarType;

	private String rentStartDay;
	private String rentEndDay;

	public String getSearchKeyWord() {
		return searchKeyWord;
	}
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}
	public String getGbnStatus() {
		return gbnStatus;
	}
	public void setGbnStatus(String gbnStatus) {
		this.gbnStatus = gbnStatus;
	}
	public String getGbnDay() {
		return gbnDay;
	}
	public void setGbnDay(String gbnDay) {
		this.gbnDay = gbnDay;
	}
	public String getGbnLocation() {
		return gbnLocation;
	}
	public void setGbnLocation(String gbnLocation) {
		this.gbnLocation = gbnLocation;
	}
	public String getGbnReserve() {
		return gbnReserve;
	}
	public void setGbnReserve(String gbnReserve) {
		this.gbnReserve = gbnReserve;
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public String getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(String rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getRtPIdx() {
		return rtPIdx;
	}
	public void setRtPIdx(String rtPIdx) {
		this.rtPIdx = rtPIdx;
	}
	public String getCrIdx() {
		return crIdx;
	}
	public void setCrIdx(String crIdx) {
		this.crIdx = crIdx;
	}
	public String getGbnInput() {
		return gbnInput;
	}
	public void setGbnInput(String gbnInput) {
		this.gbnInput = gbnInput;
	}
	public String getGbnCarType() {
		return gbnCarType;
	}
	public void setGbnCarType(String gbnCarType) {
		this.gbnCarType = gbnCarType;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getRentStartDay() {
		return rentStartDay;
	}
	public void setRentStartDay(String rentStartDay) {
		this.rentStartDay = rentStartDay;
	}
	public String getRentEndDay() {
		return rentEndDay;
	}
	public void setRentEndDay(String rentEndDay) {
		this.rentEndDay = rentEndDay;
	}





}
