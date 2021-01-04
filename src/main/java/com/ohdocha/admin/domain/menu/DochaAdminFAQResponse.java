package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("faqRequest")
public class DochaAdminFAQResponse {

	private String faIdx;
	private String faTitle;
	private String faContent;
	private String imgIdx;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;

}
