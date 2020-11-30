package com.ohdocha.admin.domain.calculateMnt;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("calculateRequest")						   	
public class DochaAdminCalculateRequest  implements Serializable {
	
	public DochaAdminCalculateRequest() {
		
	}
	
	private static final long serialVersionUID = 1L;
	
    private int page;				//시작페이지
    private int displayPageNum;		//몇개를 보여줄 것인가
    private int totalRowCount; 		//총 row 갯수
//    @NotNull(message ="테스트중입니다")
    private String gbnDt;
    private String accountExpDt;
    
    private String acDt;
    private String rtIdx;
    private String rmIdx;
    private String pdIdx;
    private String acPay;
    private String regId;
    private String modId;
    
    private String searchStartDt;
    private String searchEndDt;
    private String searchType;
    private String searchKeyWord;
  	
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
	public String getGbnDt() {
		return gbnDt;
	}
	public void setGbnDt(String gbnDt) {
		this.gbnDt = gbnDt;
	}
	public String getAccountExpDt() {
		return accountExpDt;
	}
	public void setAccountExpDt(String accountExpDt) {
		this.accountExpDt = accountExpDt;
	}
	public String getAcDt() {
		return acDt;
	}
	public void setAcDt(String acDt) {
		this.acDt = acDt;
	}
	public String getRtIdx() {
		return rtIdx;
	}
	public void setRtIdx(String rtIdx) {
		this.rtIdx = rtIdx;
	}
	public String getRmIdx() {
		return rmIdx;
	}
	public void setRmIdx(String rmIdx) {
		this.rmIdx = rmIdx;
	}
	public String getPdIdx() {
		return pdIdx;
	}
	public void setPdIdx(String pdIdx) {
		this.pdIdx = pdIdx;
	}
	public String getAcPay() {
		return acPay;
	}
	public void setAcPay(String acPay) {
		this.acPay = acPay;
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
	public String getSearchStartDt() {
		return searchStartDt;
	}
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	public String getSearchEndDt() {
		return searchEndDt;
	}
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchKeyWord() {
		return searchKeyWord;
	}
	public void setSearchKeyWord(String searchKeyWord) {
		this.searchKeyWord = searchKeyWord;
	}    
    
    
}
