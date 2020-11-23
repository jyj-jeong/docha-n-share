package com.ohdocha.admin.domain.common;

import com.ohdocha.admin.domain.Criteria;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("addressInfoRequest")
public class DochaAdminAddressInfoRequest extends Criteria {

	private int AddIdx;        			 //지역구분 인덱스
	private String AddDo;      			 //도,광역시
	private String AddSi;      			 //시,구
	private String AddDong;    			 //읍,면,동
	private String AddLi;      			 //리

}