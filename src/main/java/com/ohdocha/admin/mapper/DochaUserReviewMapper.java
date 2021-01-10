package com.ohdocha.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ohdocha.admin.domain.review.model.UserReviewDto;
import com.ohdocha.admin.util.ServiceMessage;

/**
 * @FileName : DochaUserReviewMapper.java
 * @author : <a href="#">wanseok, park</a>
 * @Date : 2021. 01. 10
 * @description : 고객후기 mapper
 */

@Mapper
public interface DochaUserReviewMapper {

    /*
     * 고객후기 List
     * */
	public List<UserReviewDto> selectUserReviewList(ServiceMessage param);
	
    /*
     * 고객후기 단일정보
     * */
	public UserReviewDto selectUserReviewInfo(ServiceMessage param);
	
    /*
     * 파일정보
     * */
	public List<String> selectUserReviewFile(ServiceMessage param);
	
}
