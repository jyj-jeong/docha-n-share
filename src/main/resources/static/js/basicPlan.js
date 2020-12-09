/*
 * integratedMember.js
 * 차량 > 요금제 > 기본요금제
 * 
 * 2020-02-04 lws 최초생성
 * 
 * 
 * 
 * update history
 * =============================================
 * |date       |comment             | author   |
 * =============================================
 * |2020-02-05 |ready 함수 제거             | pws      |
 * 
 * 
 * 
 * 
 * */

var modalName = 'basicPlanDetail';
var modalTitle = '기본요금제 상세';
var modalWidth = 500;
var DEFAULT_PAGENUM = 10;
var CURRENT_PAGE = 0;
var CRUD_METHOD = ''

function initializingPageData(){
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi (fnc, page, displayPageNum, division) {

//	CRUD_METHOD = '';
	let searchKeyword = nullCheck($('#searchInput').val()) == '' ? '' : $('#searchInput').val();
	let searchInputGbn = nullCheck($('#searchSelectBox').val()) == '' ? '' : $('#searchSelectBox').val();
	let showContents = $("#showContents option:selected").val();
	showContents = isEmpty(showContents) ? 10 : showContents;

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? showContents: (typeof displayPageNum === 'number') ? displayPageNum : showContents;

	let _rtIdx = '';

	if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
		_rtIdx = GLOBAL_LOGIN_RT_IDX;
	}

	var req = {
		'page': CURRENT_PAGE,
		'displayPageNum': displayPageNum,
		'rtIdx' : _rtIdx,
		'userRole' : GLOBAL_LOGIN_USER_ROLE,
		'searchKeyWord' : searchKeyword,
		'searchType' : searchInputGbn
	};

	let target = 'basicPlanInfo';
	let method = 'select';

	fn_callApi(method, target, req, function (response) {
		let res = response;

		//200이라면 페이징을 구한다.
		if(res.code == 200) {
			fnc(res.data, page, displayPageNum, division);
		}else { //200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		}
	});//end FindUserInfoList

}

var drawTable = function drawTable(res, page, displayPageNum){
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? DEFAULT_PAGENUM : (typeof displayPageNum === 'number') ? displayPageNum : DEFAULT_PAGENUM;

	let data = res.result;

	let rows = [];
	let columns;

	columns = [
		{ "name": "rowNumber", "id" : "rowNum" ,"title": "No" , "visible": false },
		{ "name": "pyTIdx", "title": "요금제번호" ,
			"formatter" : function(value, options, rowData){
				let seq = rowData.pyTIdx;
				return '<a href="javascript:initDetailInfo(' + "'" +seq + "'" +');"  >'+value+'</a>';
			}
		},
		{ "name": "companyName", "title": "회원사<br>(지점)" ,
			"formatter" : function(value, options, rowData){

				let displayText = '';
				let companyName = nullCheck(rowData.companyName);
				let branchName = rowData.branchName;
				if(!isEmpty(branchName)){
					displayText = companyName + '<br>'+ '(' +  branchName + ')';
				}else{
					displayText = companyName;
				}
				return displayText;
			}
		},
		{ "name": "pyEtc", "title": "제목", "breakpoints": "xs"},
		{ "name": "dailyStandardPay", "title": "일기본요금", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				let setText = objectConvertToPriceFormat(value,'.') + ' 원';
				return setText;
			}
		},
		{ "name": "dailyStandardPay", "title": "월기본요금", "breakpoints": "xs sm" ,
			"formatter" : function(value, options, rowData){
				let setText = objectConvertToPriceFormat(value,'.') + ' 원';
				return setText;
			}
		},
		{ "name": "settingCarCnt", "title": "설정차량대수", "breakpoints": "xs sm md" } ,
		{ "name": "branchName", "title": "설정차량대수", "breakpoints": "xs sm md" , "visible":false}
	];

	$('#listTable').empty();
	$('#listTable').footable({
		'calculateWidthOverride': function() {
			return { width: $(window).width() };
		},
		'on': {
			'postinit.ft.table': function(e, ft) {

			}
		},
		"columns": columns,
		"rows": data
	});

	var totalCnt = res.paging.totalCount;
	var perPageNum = res.paging.cri.perPageNum;
	var displayPageNum = res.paging.cri.displayPageNum;

	//page는 전역변수 사용
	var prev = res.paging.prev;
	var next = res.paging.next;

	makePaging(totalCnt, perPageNum, displayPageNum, page, prev, next, $("#page"));

	if(!isEmpty(totalCnt)) {
		$('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
	} else {
		$("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
	}

}

function initSelectBox(){

	var searchOption = '';
	var countOption = '';

	searchOption += '<option value="" >선택</option>';
	searchOption += '<option value="pyTIdx" >요금제번호</option>';
	searchOption += '<option value="companyName" >회원사</option>';
	searchOption += '<option value="branchName" >지점</option>';
	searchOption += '<option value="pyEtc" >제목</option>';

	countOption += '<option selected value="10">10개씩 보기</option>';
	countOption += '<option value="20">20개씩 보기</option>';
	countOption += '<option value="30">30개씩 보기</option>';
	countOption += '<option value="60">60개씩 보기</option>';

	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);

	$("#searchSelectBox option:eq(0)").prop("selected", true);
	$("#showContents option:eq(0)").prop("selected", true);
}

function bindEvent(){

	$("#page").on('click', 'a', function() {
		if ($(this).attr('class') != 'active') {
			var clickPage = $(this).text();

			var displayPageNum = $("#showContents").val();

			if(!isNaN(clickPage)) { //숫자면 현재 페이지므로 
				CURRENT_PAGE = parseInt(clickPage);
			} else { //
				if(clickPage == '«Previous Over') {
					CURRENT_PAGE = parseInt(CURRENT_PAGE) - 1;
				} if(clickPage == '»Next Over') {
					CURRENT_PAGE = parseInt(CURRENT_PAGE) + 1;
				}
			}

			loadApi(drawTable, CURRENT_PAGE, displayPageNum);

		}
	});

}

/*
 * 모아보기 선택시
 */
function changeList() {
	loadApi(drawTable, null, null);
}

$("#searchInput").keypress(function(e) {
	var searchKeyword = nullCheck($('#searchInput').val()) == '' ? '' : $('#searchInput').val();
	var searchInputGbn = $('#searchSelectBox').val();

	if (e.keyCode == 13){
		if(isEmpty(searchInputGbn)) {
			loadApi(drawTable, null, null);
		} else {
			if(isEmpty(searchKeyword)) {
				errorAlert('검색어', '검색어를 입력해 주세요.');
				return;
			}  else {
				loadApi(drawTable, null, null);
			}
		}
	}

});

function fn_search(){

	var searchKeyword = nullCheck($('#searchInput').val()) == '' ? '' : $('#searchInput').val();
	var searchInputGbn = $('#searchSelectBox').val();

	if(nullCheck(searchInputGbn) == '' ){
		loadApi( drawTable , null, null );
	}else{
		if( searchKeyword.length <= 0 ){
			errorAlert('검색어', '검색어를 입력하세요.');
			return;
		}else{
			loadApi( drawTable , null, null );
		}
	}

}


/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){

//	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
//	if(true){
//		return;
//	}
	// input box 초기화	
	initInput();

	let pyTIdx = '';

	let target = 'basicPlanDetail';
	let method = 'select';

	pyTIdx = seq;

	let req = {
		pyTIdx : pyTIdx
	};

	fn_callApi(method, target, req, function(response) {
		// let data = response;

		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {

		let data = response[0];

		let pyTIdx                = nullCheck(data.pyTIdx);
		let mdIdx                = nullCheck(data.mdIdx);
		let dailyStandardPay     = objectConvertToPriceFormat(nullCheck(data.dailyStandardPay));
		let monthlyStandardPay   = objectConvertToPriceFormat(nullCheck(data.monthlyStandardPay));
		let dailyMaxRate         = objectConvertToPriceFormat(nullCheck(data.dailyMaxRate));
		let monthlyMaxRate       = objectConvertToPriceFormat(nullCheck(data.monthlyMaxRate));
		let month3Deposit        = objectConvertToPriceFormat(nullCheck(data.month3Deposit));
		let month6Deposit        = objectConvertToPriceFormat(nullCheck(data.month6Deposit));
		let month9Deposit        = objectConvertToPriceFormat(nullCheck(data.month9Deposit));
		let month12Deposit       = objectConvertToPriceFormat(nullCheck(data.month12Deposit));
		let deliveryStandardPay  = objectConvertToPriceFormat(nullCheck(data.deliveryStandardPay));
		let deliveryAddPay       = objectConvertToPriceFormat(nullCheck(data.deliveryAddPay));
		let deliveryMaxRate      = objectConvertToPriceFormat(nullCheck(data.deliveryMaxRate));
		let rtIdx                = nullCheck(data.rtIdx);
		let pyEtc                = nullCheck(data.pyEtc);
		let companyName	         = nullCheck(data.companyName);
		let branchName	         = nullCheck(data.branchName);
		let settingCarCnt        = nullCheck(data.settingCarCnt);

		$('#pyEtc').val(pyEtc);
		$('#pyTIdx').val(pyTIdx);

		// 회원사별 차량 리스트
		$('#rtIdx').val(rtIdx);
		getCompanyCarList();

		$('#dailyStandardPay').val(dailyStandardPay + '원');
		$('#dailyMaxRate').val(dailyMaxRate + '%');
		$('#monthlyStandardPay').val(monthlyStandardPay + '원');
		$('#monthlyMaxRate').val(monthlyMaxRate + '%');
		$('#month3Deposit').val(month3Deposit + '원');
		$('#month6Deposit').val(month6Deposit + '원');
		$('#month9Deposit').val(month9Deposit + '원');
		$('#month12Deposit').val(month12Deposit + '원');

		$('#deliveryStandardPay').val(deliveryStandardPay + '원');
		$('#deliveryAddPay').val(deliveryAddPay + '원');
		$('#deliveryMaxRate').val(deliveryMaxRate + '%');

		if(!isEmpty(nullCheck(branchName))){
			companyName = companyName + "(" + nullCheck(branchName) + ")";
		}else{
			companyName = companyName;
		}
		let companyNameItem = "<option selected value='" + rtIdx + "'>"+companyName + "</option>";
		$('#companyName').empty();
		$('#companyName').append(companyNameItem);
		$('#companyName').attr('disabled', true);

		CRUD_METHOD = 'update';
		// openIziModal(modalName);

		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		//
		// }
	});// end fn_callApi
}

//모달 오픈
function initDetailData(data){
	if(data == 'insert'){
		CRUD_METHOD = data;
	}else{
		CRUD_METHOD = '';
	}

	initDetailSelectBox(data);
}

function initDetailSelectBox(_data){

	if(CRUD_METHOD === 'insert'){
		$('#companyName').empty();
		$('#modelName').empty();
		// input box 초기화	
		initInput();
	}

	let target = 'selectCompanyList';
	let method = 'select';
	let req = {};

	// 회사리스트
	fn_callApi(method, target, req, function(response) {
		let data = response;

		let strOption = "<option value=''>선택</option>";

		for ( var i=0; i<data.length; i++ ) {

			if(!isEmpty(data[i].branchName)){
				companyName = data[i].companyName + "(" + nullCheck(data[i].branchName) + ")";
			}else{
				companyName = data[i].companyName;
			}
			strOption += "<option value = '" + data[i].rtIdx + "'>" + companyName + "</option>";

		}

		$('#companyName').append(strOption);

		if (CRUD_METHOD === 'insert' && getLoginUser().userRole !== 'RA'){
			$('select[id=companyName]').val(getLoginUser().rtIdx).prop("selected",true);
			$('#companyName').attr('disabled', true);
		}else {
			$('#companyName').attr('disabled', false);
		}

	});// end fn_callApi

	// company init
	if( CRUD_METHOD === 'insert' ){
		// openIziModal(modalName);
	}

}

// validation
function detailValidation(){
	let title, text, icon, cancel_text;

	let companyName = getPureText($('#companyName').val());
	let pyTIdx = getPureText($('#pyTIdx').val());
	let pyEtc = getPureText($('#pyEtc').val());
	let dailyStandardPay = getPureText($('#dailyStandardPay').val()).replace("원","").replace(" ","");
	let dailyMaxRate = getPureText($('#dailyMaxRate').val()).replace("%","").replace(" ","");
	let monthlyStandardPay = getPureText($('#monthlyStandardPay').val()).replace("원","").replace(" ","");
	let monthlyMaxRate = getPureText($('#monthlyMaxRate').val()).replace("%","").replace(" ","");
	let month3Deposit = getPureText($('#month3Deposit').val()).replace("원","").replace(" ","");
	let month6Deposit = getPureText($('#month6Deposit').val()).replace("원","").replace(" ","");
	let month9Deposit = getPureText($('#month9Deposit').val()).replace("원","").replace(" ","");
	let month12Deposit = getPureText($('#month12Deposit').val()).replace("원","").replace(" ","");
	let deliveryStandardPay = getPureText($('#deliveryStandardPay').val()).replace("원","").replace(" ","");
	let deliveryAddPay = getPureText($('#deliveryAddPay').val()).replace("원","").replace(" ","");
	let deliveryMaxRate = getPureText($('#deliveryMaxRate').val()).replace("%","").replace(" ","");

	var selectedCarList = $('input[name=carList]:checked');
	var carList = '';

	if (selectedCarList.length !== 0){
		for (var i = 0; i < selectedCarList.length; i++){
			if (!selectedCarList[i].id.includes('All')){
				carList += selectedCarList[i].id;
				carList += ' ';
			}
		}
	}

	carList = carList.trim();

	if(isEmpty(companyName)){
		errorAlert('회원사', '회원사를 선택하여 주세요.');
		$('#companyName').focus();
		return;
	}else if(isEmpty(pyEtc.length)){
		errorAlert('제목', '제목을 입력해 주세요.');
		$('#pyEtc').focus();
		return;
	}else if(!isEmpty(dailyStandardPay) && !$.isNumeric(dailyStandardPay)){
		errorAlert('일 기본요금', '일 기본요금은 숫자만 입력 가능합니다.');
		$('#dailyStandardPay').focus();
		return;
	}else if(!isEmpty(dailyMaxRate) && !$.isNumeric(dailyMaxRate)){
		errorAlert('일대여 최대 할인율', '일대여 최대 할인율은 숫자만 입력 가능합니다.');
		$('#dailyMaxRate').focus();
		return;
	}else if(!isEmpty(monthlyStandardPay) && !$.isNumeric(monthlyStandardPay)){
		errorAlert('월 기본요금', '월 기본요금은 숫자만 입력 가능합니다.');
		$('#monthlyStandardPay').focus();
		return;
	}else if(!isEmpty(monthlyMaxRate) && !$.isNumeric(monthlyMaxRate)){
		errorAlert('월 최대 할인율', '월 최대 할인율은 숫자만 입력 가능합니다.');
		$('#monthlyMaxRate').focus();
		return;
	}else if(!isEmpty(month3Deposit) && !$.isNumeric(month3Deposit)){
		errorAlert('3개월 보증금', '3개월 보증금은 숫자만 입력 가능합니다.');
		$('#month3Deposit').focus();
		return;
	}else if(!isEmpty(month6Deposit) && !$.isNumeric(month6Deposit)){
		errorAlert('6개월 보증금', '6개월 보증금은 숫자만 입력 가능합니다.');
		$('#month6Deposit').focus();
		return;
	}else if(!isEmpty(month3Deposit) && !$.isNumeric(month9Deposit)){
		errorAlert('9개월 보증금', '9개월 보증금은 숫자만 입력 가능합니다.');
		$('#month9Deposit').focus();
		return;
	}else if(!isEmpty(month3Deposit) && !$.isNumeric(month12Deposit)){
		errorAlert('12개월 보증금', '12개월 보증금은 숫자만 입력 가능합니다.');
		$('#month12Deposit').focus();
		return;
	}

	let rtIdx = companyName;

	let req = {
		'rtIdx'	: rtIdx
		,	'pyTIdx' : pyTIdx
		,	'crIdx' : carList
		,	'pyEtc' : pyEtc
		,	'dailyStandardPay' : dailyStandardPay
		,	'dailyMaxRate' : dailyMaxRate
		,	'monthlyStandardPay' : monthlyStandardPay
		,	'monthlyMaxRate' : monthlyMaxRate
		,	'month3Deposit' : month3Deposit
		,	'month6Deposit' : month6Deposit
		,	'month9Deposit' : month9Deposit
		,	'month12Deposit' : month12Deposit
		,	'deliveryStandardPay' : deliveryStandardPay
		,	'deliveryAddPay' : deliveryAddPay
		,	'deliveryMaxRate' : deliveryMaxRate
		,	'modId' : getLoginUser().urIdx
		,	'regId' : getLoginUser().urIdx
	}

	let method = '';
	if( CRUD_METHOD == 'insert' ){
		method = 'insertBasicPlanInfo';
	}else{
		method = 'updateBasicPlanInfo';
	}

	title = '기본요금제 저장';
	text = '저장하시겠습니까?'
	icon = 'info';
	cancel_text = '취소하셨습니다.';

	call_before_save(title, text, icon, cancel_text, method, req);

}

// submit
function detailSubmit(method, reqParam){

	let _target = method;
	let _method = CRUD_METHOD;
	let param = reqParam;

	fn_callApi(_method, _target, param, function(response) {
		let data = response;

		swal("저장 성공", { icon: "success"});

	});

}

function companyChanged() {
	var rtIdx = $("#companyName option:selected").val();

	$('#rtIdx').val(rtIdx);

	getCompanyCarList();
}

function getCompanyCarList() {

	var rtIdx = $('#rtIdx').val();

	var url = '/api/v1.0/selectRentCompanyCarList.json';
	var req = {
		rtIdx: rtIdx
	};

	$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(req),
		contentType: 'application/json',
		cache: false,
		acync: false,
		timeout: 10000
	}).done(function (data, textStatus, jqXHR) {

		// 경차 리스트
		var subCompactCarList = data.rentCompanyCarList[0];
		makeCheckBox(subCompactCarList, "subCompactCarList", "경차");

		// 소형 리스트
		var compactCarList = data.rentCompanyCarList[1];
		makeCheckBox(compactCarList, "compactCarList", "소형");

		// 중형 리스트
		var mediumCarList = data.rentCompanyCarList[2];
		makeCheckBox(mediumCarList, "mediumCarList", "중형");

		// 대형 리스트
		var largeCarList = data.rentCompanyCarList[3];
		makeCheckBox(largeCarList, "largeCarList", "대형");

		// SUV 리스트
		var suvCarList = data.rentCompanyCarList[4];
		makeCheckBox(suvCarList, "suvCarList", "SUV");

		// 승합 리스트
		var vanList = data.rentCompanyCarList[5];
		makeCheckBox(vanList, "vanList", "승합");

	});
}

function makeCheckBox(data, target, all) {
	var strOption = "<label class='d-inline-block mr-3 checkbox-inline'>" +
		"<input id='" + target + "All' type='checkbox' value='" + all + "' name='carList'/> " + all + "전체</label>";

	for ( var i=0; i< data.length; i++) {
		strOption += "<label class='d-inline-block mr-3 checkbox-inline'>" +
			"<input id='" + data[i].crIdx + "' type='checkbox' value='" + data[i].modelName + "' name='carList'/> "
			+ data[i].modelName
			+ "</label>";
	}

	$('#' + target).empty();
	$('#' + target).append(strOption).addClass('mr-3 checkbox-inline');
}


/*
 * input box 초기화
 */
function initInput(){

	$("#" + modalName).find('input:text').each(function(){
		$(this).val('');
	});
	// input 태그의 자동완성 기능 해제
	$("#" + modalName).find('input:text').each(function(){
		$(this).attr('autocomplete','off');
	});

	$("#" + modalName).find('select').each(function(){
		$(this).find('option').eq(0).prop('selected',true);
	});

}

/* =========================== detail function end ======================================*/