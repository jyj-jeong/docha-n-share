package com.ohdocha.admin.domain.calculateMnt;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("calculateResponse")
public class DochaAdminCalculateResponse extends CommonResponseDto {

	private String rowNum;
	private String totalRowCount;
	private String rmIdx;
	private String pdIdx;
	//private String rentGbnDt;
	private String accountExpDt;
	private String totalFee;
	private String totalAmount;
	private String dochaDisFee;
	private String accountExpAmt;
	private String successAmount;
	private String onMissFee;
	private String disCountFee;
	private String companyCount;
	private String rmCount;
	
	//COMPANY INFO
	private String reserveCount;
	private String companyName;
	private String branchName;
	private String companyRegistrationName;
	private String successFee;
	private String settlementAmount;
	private String estimatedFee;
	private String accountBank;
	private String accountNumber;
	private String paymentAmount;
	private String paymentDate;

	//RESERVE INFO
	private String rentStartDay;
	private String rentEndDay;
	private String rentStartTime;
	private String rentEndTime;
	private String userName;
    
	//CAR INFO
	private String modelName;
	private String modelDetailName;
	private String carNumber;
	private String year;
	private String rtIdx;
	private String fuelCode;
	private String fuelName;

	
	
}
