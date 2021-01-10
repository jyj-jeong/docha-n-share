package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

/**
 * 고객후기
 * 
 * @MethodName : UserReviewService
 * @Date : 2021.01.10
 * @author : wanseok, park
 */
public interface UserReviewService {

	/**
	* 고객후기 List 조회
	* 
	* @MethodName : selectUserReviewList
	* @Date : 2021. 01. 10.
	* @author : wanseok, park
	* @param 
	* @return UserReviewDto
	*/
	public void selectUserReviewList(ServiceMessage param);
	

	/**
	* 고객후기 정보 조회
	* 
	* @MethodName : selectUserReviewInfo
	* @Date : 2021. 01. 10.
	* @author : wanseok, park
	* @param 
	* @return UserReviewDto
	*/
	public void selectUserReviewInfo(ServiceMessage param);
}
