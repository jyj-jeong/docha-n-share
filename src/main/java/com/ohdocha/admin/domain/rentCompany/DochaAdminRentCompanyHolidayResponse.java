package com.ohdocha.admin.domain.rentCompany;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Data
@Alias("RentCompanyHolidayResponse")
public class DochaAdminRentCompanyHolidayResponse extends CommonResponseDto {
	private String holIdx;			/*휴일idx*/
	private String rtIdx;			/*제휴사idx*/
	private String holidayStartDt;	/*공휴일시작일*/
	private String holidayEndDt;	/*공휴일종료일*/
	private String holidayName;		/*휴일명*/
	private String regId;			/*등록자*/
	private String regDt;			/*등록일자*/
	private String modId;			/*수정자*/
	private String modDt;			/*등록일자*/
	private String delYn; 			/*삭제여부*/
	private String tholIdx;			/*중복 검색시 휴일idx*/

}
