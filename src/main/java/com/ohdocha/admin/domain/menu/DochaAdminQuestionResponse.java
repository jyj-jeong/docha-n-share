package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("questionResponse")
public class DochaAdminQuestionResponse {

	private String quIdx;
	private String quTitle;
	private String quContents;
	private String questionerId;
	private String questionDt;
	private String quAnswer;
	private String quAnswerYn;
	private String answererId;
	private String answerDt;
	private String imgIdx ;

}
