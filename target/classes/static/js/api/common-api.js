/*
 * 2020-02-03 lws
 * common-api.js
 */

var apiVersion = "/api/v1.0/";

// API 타임아웃을 설정한다.
Timeout = 300000;
UploadResourceTimeout = 300000;

/*
 * method	: select, insert, update, delete 
 * target	: target Url division을 기록
 * reqParam	: json parameter
 * finish_callback : callback function
 * 
 * 
 * */
var fn_callApi = function fn_callApi(method, target, reqParam, finish_callback) {
	
	var url = "";
	var makeUrl = '';
		
	if(method === 'select'){
		makeUrl = apiVersion + target + '.json';
	}else if(method === 'insert'){
		makeUrl = apiVersion + target + '.do';
	}else if(method === 'update'){
		makeUrl = apiVersion + target + '.do';
	}else if(method === 'delete'){
		makeUrl = apiVersion + target + '.do';
	}else if(method === 'login'){
		makeUrl = apiVersion + target + '.do';
	}
	
	url = makeUrl;
	var response = {};

	
	if(reqParam == 'null') {
		$.ajax({
		    url: url,
		    type: 'POST',
		    dataType: 'json',
		    contentType: 'application/json;charset=UTF-8',
		    cache: false,
		    async: true,
		    timeout: Timeout
		    }).done(function (data, textStatus, jqXHR) {
		 		response = data;
		 	}).fail(function (jqXHR, textStatus, errorThrown) {
		 		//TODO..AJAX공통처리
		 		//response = ErrorUtil.AjaxFailResponse(jqXHR, textStatus, errorThrown, "CURRICULUMBYID_FIND");
		 	}).always(function () {
		 		finish_callback(response);
		 	});
	} else {  
		// 공통 파라미터
//		reqParam.urIdx = GLOBAL_LOGIN_USER_IDX;
//		reqParam.userRole = GLOBAL_LOGIN_USER_ROLE;
//		reqParam.rtIdx = GLOBAL_LOGIN_RT_IDX;
		
		var postData = JSON.stringify(reqParam);
		
		$.ajax({
		    url: url,
		    type: 'POST',
		    data: postData,
		    dataType: 'json',
		    contentType: 'application/json;charset=UTF-8',
		    cache: false,
		    async: true,
		    timeout: Timeout
		    
		
		 	}).done(function (data, textStatus, jqXHR) {
		 		response = data;
		 	}).fail(function (jqXHR, textStatus, errorThrown) {
		 		//TODO..AJAX공통처리
		 		//response = ErrorUtil.AjaxFailResponse(jqXHR, textStatus, errorThrown, "CURRICULUMBYID_FIND");
		 	}).always(function () {
		 		finish_callback(response);
		 	});
	}
	
	 return response;

}//end fn_callApi

