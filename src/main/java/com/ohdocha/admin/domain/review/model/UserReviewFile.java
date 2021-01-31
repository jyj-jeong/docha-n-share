package com.ohdocha.admin.domain.review.model;

import org.apache.ibatis.type.Alias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Alias("userReviewFile")
public class UserReviewFile {
	
	private Integer idx;
	private Integer rvIdx;
	private String filePath;
	private String fileRealPath;
	private String fileNm;
	private String orgFilePath;
	private String regDt;
	private String modDt;
	private String modId;
	private String delYn;
}
