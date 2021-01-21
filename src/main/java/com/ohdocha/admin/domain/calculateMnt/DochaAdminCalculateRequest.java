package com.ohdocha.admin.domain.calculateMnt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Getter
@Setter
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

    private String settlementAmount;
    private String reserveDate;

}
