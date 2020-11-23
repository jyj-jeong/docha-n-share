package com.ohdocha.admin.domain.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("addressInfoResponse")
public class DochaAdminAddressInfoResponse extends CommonResponseDto {

	private int AddIdx;        			 //지역구분 인덱스
	private String AddDo;      			 //도,광역시
	private String AddSi;      			 //시,구
	private String AddDong;    			 //읍,면,동
	private String AddLi;      			 //리
}
