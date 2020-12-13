package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("noticeResponse")
public class DochaAdminNoticeResponse {

	private String ntIdx;
	private String ntTitle;
	private String ntContent;
	private String imgIdx;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;

}
