package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("eventRequest")
public class DochaAdminEventRequest {

	private int evIdx;
	private String evImgIdx ;
	private String evStartDt;
	private String evEndDt;
	private String evTitle;
	private String evContent;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;

}
