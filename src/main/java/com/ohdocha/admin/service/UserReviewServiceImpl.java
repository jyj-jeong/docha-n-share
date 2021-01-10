package com.ohdocha.admin.service;


import org.springframework.stereotype.Service;
import com.ohdocha.admin.mapper.DochaUserReviewMapper;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserReviewServiceImpl.java
 *
 * @author <a href="">wanseok, park</a>
 * @version 1.0
 * @since Create : 2021. 01. 10.
 */

@Slf4j
@AllArgsConstructor
@Service
public class UserReviewServiceImpl implements UserReviewService {

	private final DochaUserReviewMapper userReviewMapper;
	
	@Override
	public void selectUserReviewList(ServiceMessage param) {
		// TODO Auto-generated method stub
		param.addData("result", userReviewMapper.selectUserReviewList(param));
	}

	@Override
	public void selectUserReviewInfo(ServiceMessage param) {
		// TODO Auto-generated method stub
		param.addData("fileList" , userReviewMapper.selectUserReviewFile(param));
		param.addData("result", userReviewMapper.selectUserReviewInfo(param));
	}

}
