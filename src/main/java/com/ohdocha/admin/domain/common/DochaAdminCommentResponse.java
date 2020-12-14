package com.ohdocha.admin.domain.common;

import com.ohdocha.admin.domain.Criteria;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("commentResponse")
public class DochaAdminCommentResponse extends Criteria {

	private int coIdx;
	private String rtIdx;
	private String commentMsg;
	private String commentPath;
	private String regId;
	private String regDt;

}