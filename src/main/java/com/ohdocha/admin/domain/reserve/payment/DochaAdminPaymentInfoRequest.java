package com.ohdocha.admin.domain.reserve.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Getter
@Setter
@Alias("reserveInfoRequest")						   	
public class DochaAdminPaymentInfoRequest  implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private int page;				//시작페이지
    private int displayPageNum;		//몇개를 보여줄 것인가
    private int totalRowCount; 		//총 row 갯수
    private String rmIdx;
    private String userId;
    private String rtIdx;
    private String userRole;
    private String crIdx;
    
    //DC_PAYMENT_DETAIL
    private String pdIdx;
    private String urIdx;
    private String pgCode;
    private String paymentTypeCode;
    private String paymentKindCode;
    private String paymentAmount;
    private String approvalNumber;
    private String paymentDate;
    private String etc;

    //DC_RESERVE_LOG
    
    private String reIdx;
	private String reserveStatusCode;
	private String rentStartDay;
	private String rentStartTime;
	private String rentEndDay;
	private String rentEndTime;
	private String rentFee;
	private String insuranceFee;
	private String carDeposit;
	private String discountFee;
	private String cancelFee;
	private String cancelAmount;
	private String reserveEtc;
	private String regId;
	private String regDt;

    
    private String searchKeyWord;
    private String gbnStatus;
    private String gbnDay;
    private String gbnLocation;
    private String gbnReserve;
    private String gbnInput;
    private String gbnCarType;


    // 알림톡 전송
    private String modelName;
    private String modelDetailName;
    private String sumPaymentAmount;
    private String firstDriverContact;
    private String carNumber;

    
}
