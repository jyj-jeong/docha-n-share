package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("mainImgResponse")
public class DochaAdminMainImgResponse {

	private int miIdx;
	private String miImgIdx;
	private String miStartDt;
	private String miEndDt;
	private String miTitle;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;

}
