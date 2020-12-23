package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Alias("eventResponse")
public class DochaAdminEventResponse {

	private String evIdx;
	private String evImgIdx ;
	private LocalDateTime evStartDt;
	private LocalDateTime evEndDt;
	private String evTitle;
	private String evContent;
	private String regId;
	private LocalDateTime regDt;
	private String modId;
	private LocalDateTime modDt;

}
