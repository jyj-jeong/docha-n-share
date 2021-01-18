package com.ohdocha.admin.domain.review.model;



import org.apache.ibatis.type.Alias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @FileName : UserReviewDto.java
 * @author : <a href="">wanseok, park</a>
 * @Date : 2021.01.10
 * @description : ohdocha.DC_USER_REVIEW / 고객후기 
 */
@Data
@Getter
@Setter
@Alias("userReviewDto")
public class UserReviewDto {

	private String rvIdx;		/* sequence 	*/
	private String rmIdx;       /* 고객번호		*/
	private String comment;     /* 후기			*/
	private String rating;      /* 후기점수		*/
	private String regDt;       /* 등록일			*/
	private String regId;       /* 등록자			*/
	private String modDt;       /* 수정일			*/
	private String modId;       /* 수정자			*/
	private String delYn;       /* 삭제여부		*/
	
	private String crIdx;
	private String companyName;
	private String modelName;
	private String modelDetailName;
	private String carNumber;
	
	private String userId;
	private String userName;
	
	private Integer imgCount;
	private String companyZipCode;

}
