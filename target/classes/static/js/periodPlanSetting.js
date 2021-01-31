/*
 * periodPlanSetting.js
 * 차량 > 요금제 > 기간요금제설정
 *
 * */

var modalName = 'periodPlanSettingDetail';
var modalTitle = '기간요금설정 상세';
var modalWidth = 500;
var DEFAULT_PAGENUM = 10;
var CURRENT_PAGE = 0;
var CRUD_METHOD = '';

var GLOBAL_LOGIN_USER_ROLE;
var GLOBAL_LOGIN_RT_IDX;
var GLOBAL_LOGIN_USER_IDX;

function initializingPageData(){
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi (fnc, page, displayPageNum, division) {

	CRUD_METHOD = '';

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

	let target = 'periodPlanSettingInfo';
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
		{ "name": "perIdx", "title": "기간요금번호" ,
			"formatter" : function(value, options, rowData){
				let seq = rowData.perIdx;
				return '<a href="javascript:initDetailInfo(' + "'" +seq + "'" +');"  >'+value+'</a>';
			}
		},
		{ "name": "companyName", "title": "회원사<br>(지점)" ,
			"formatter" : function(value, options, rowData){
				let displayText = nullCheck(value);

				let companyName = nullCheck(rowData.companyName);
				let branchName = nullCheck(rowData.branchName);

				if(!isEmpty(branchName)){
					displayText = companyName + '<br>'+ '(' +  branchName + ')';
				}else{
					displayText = companyName;
				}

				return displayText;
			}
		},
		{ "name": "periodEtc", "id":"periodEtc", "title": "제목", "breakpoints": "xs sm md" },
		{ "name": "periodStartDt", "title": "시작일<br>종료일", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){

				let displayText = nullCheck(value);

				let periodStartDt = nullCheck(rowData.periodStartDt);
				let periodEndDt = nullCheck(rowData.periodEndDt);

				displayText = periodStartDt + '<br>' + periodEndDt;
				return displayText;
			}

		},
		{ "name": "extrachargePay", "title": "할증금액/일", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){

				let displayText = nullCheck(value);
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];

				if(targetRowData.discountExtrachargeCode == 'E'){
					displayText = nullCheck(targetRowData.periodPay) + ' 원';
				} else {
					displayText = '0 원';
				}

				return displayText;
			}

		},
		{ "name": "periodPay", "title": "할인금액/일", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){

				let displayText = nullCheck(value);
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];

				if(targetRowData.discountExtrachargeCode == 'D'){
					displayText = nullCheck(targetRowData.periodPay) + ' 원';
				} else {
					displayText = '0 원';
				}

				return displayText;
			}

		},
		{ "name": "settingCarCnt", "title": "설정차량대수", "breakpoints": "xs sm md" },
		{ "name": "branchName", "id":"branchName", "title": "내용", "breakpoints": "xs sm md" , "visible": false },
		{ "name": "periodEndDt", "id":"periodEndDt", "title": "내용", "breakpoints": "xs sm md" , "visible": false }
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
	var delYnItem = "";

	searchOption += '<option value="" >선택</option>';
	searchOption += '<option value="perIdx" >기간요금제번호</option>';
	searchOption += '<option value="companyName" >회원사</option>';
	searchOption += '<option value="branchName" >지점</option>';
	searchOption += '<option value="perEtc" >내용</option>';

	countOption += '<option value="10">10개씩 보기</option>';
	countOption += '<option value="20">20개씩 보기</option>';
	countOption += '<option value="30">30개씩 보기</option>';
	countOption += '<option value="60">60개씩 보기</option>';

	delYnItem += "<option value='N'>사용</option>";
	delYnItem += "<option value='Y'>미사용</option>";

	$('#delYn').empty();
	$('#delYn').append(delYnItem);

	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);

	$("#searchSelectBox option:eq(0)").prop("selected", true);
	$("#showContents option:eq(0)").prop("selected", true);
	$("#delYn option:eq(0)").prop("selected", true);
	$("input:radio[name='rd_discountExtrachargeCode']:radio[value='D']").prop('checked', true);
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

	GLOBAL_LOGIN_USER_ROLE = getLoginUser().userRole;
	GLOBAL_LOGIN_RT_IDX = getLoginUser().rtIdx;
	GLOBAL_LOGIN_USER_IDX = getLoginUser().urIdx;

	// input box 초기화
	initInput();

	let perIdx = '';

	let target = 'periodPlanSettingDetail';
	let method = 'select';

	perIdx = seq;

	let req = {
		perIdx : perIdx
	};

	fn_callApi(method, target, req, function(response) {
		let data = response.result[0];

		let strOption = "";

		let companyName 				= nullCheck(data.companyName);
		let branchName 				    = nullCheck(data.branchName);
		let perIdx                      = nullCheck(data.perIdx);
		let rtIdx                       = nullCheck(data.rtIdx);
		let periodStartDt               = nullCheck(data.periodStartDt);
		let periodEndDt                 = nullCheck(data.periodEndDt);
		let discountExtrachargeCode     = nullCheck(data.discountExtrachargeCode);
		let periodPay                   = nullCheck(data.periodPay);
		let periodEtc                   = nullCheck(data.periodEtc);
		let regId                       = nullCheck(data.regId);
		let regDt                       = nullCheck(data.regDt);
		let modId                       = nullCheck(data.modId);
		let modDt                       = nullCheck(data.modDt);
		let delYn				        = nullCheck(data.delYn);

		// 선택된 차량 정보
		let crIdx                       = nullCheck(data.crIdx);
		var selectedCar = crIdx.split(' ');

		// 회원사 별 차량 리스트
		$('#rtIdx').val(rtIdx);
		getCompanyCarList(selectedCar);

		if(!isEmpty(nullCheck(branchName))){
			companyName = companyName + "(" + nullCheck(branchName) + ")";
		}else{
			companyName = companyName;
		}

		let companyNameItem = "<option selected value='" + rtIdx + "'>"+companyName + "</option>";
		$('#companyName').empty();
		$('#companyName').append(companyNameItem);
		$('#companyName').attr('disabled', true);

		$('#periodStartDt').val(periodStartDt);
		$('#periodEndDt').val(periodEndDt);
		$('#perIdx').val(perIdx);

		let delYnItem = "";
		if(delYn == "N"){
			delYnItem = delYnItem + "<option selected value='N'>사용</option>";
			delYnItem = delYnItem + "<option value='Y'>미사용</option>";
		}else{
			delYnItem = delYnItem + "<option value='N'>사용</option>";
			delYnItem = delYnItem + "<option selected value='Y'>미사용</option>";
		}
		$('#delYn').empty();
		$('#delYn').append(delYnItem);

		//할증구분 설정
		if(discountExtrachargeCode == '할증') { //할증
			//할증버튼 선택
			$("input:radio[name='rd_discountExtrachargeCode']:radio[value='할증']").prop('checked', true); // 선택하기
			periodPay = objectConvertToPriceFormat(periodPay);
		} else if(discountExtrachargeCode == '할인') { //할인
			$("input:radio[name='rd_discountExtrachargeCode']:radio[value='할인']").prop('checked', true); // 선택하기
			periodPay = objectConvertToPriceFormat(periodPay);
		} else {
			periodPay = '0';
		}

		$('#periodPay').val(periodPay);

		//내용
		$('#periodEtc').val(periodEtc);

		CRUD_METHOD = 'update';

	});// end fn_callApi

}

//모달 오픈
function initDetailData(data){
	if(data === 'insert'){
		CRUD_METHOD = data;
	}else{
		CRUD_METHOD = '';
	}

	GLOBAL_LOGIN_USER_ROLE = getLoginUser().userRole;
	GLOBAL_LOGIN_RT_IDX = getLoginUser().rtIdx;
	GLOBAL_LOGIN_USER_IDX = getLoginUser().urIdx;



	initDetailSelectBox(data);
}

function initDetailSelectBox(_data){

	if(CRUD_METHOD === 'insert'){
		$('#companyName').empty();
		$('#modelName').empty();

		// input box 초기화
		initInput();

		$('#periodStartDt').val('');
		$('#periodEndDt').val('');
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

		// 최고관리자 권한이 아닐 경우 회원사 지정.
		if (CRUD_METHOD === 'insert' && getLoginUser().userRole !== 'RA'){

			$('select[id=companyName]').val(getLoginUser().rtIdx).prop("selected",true);
			$('#companyName').attr('disabled', true);

			// 회원사 별 차량 리스트
			$('#rtIdx').val(getLoginUser().rtIdx);
			getCompanyCarList();
		}else {
			$('#companyName').attr('disabled', false);
		}

	});// end fn_callApi


}

function companyChanged() {
	var rtIdx = $("#companyName option:selected").val();

	$('#rtIdx').val(rtIdx);

	getCompanyCarList();
}

function getCompanyCarList(selectedCar) {

	var rtIdx = $('#rtIdx').val();

	var url = '/api/v1.0/selectRentCompanyCarList.json';
	var req = {
		rtIdx : rtIdx
	};

	$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(req),
		contentType: 'application/json',
		cache: false,
		acync : false,
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


		for (var i in selectedCar){
			$('input:checkbox[id="'+selectedCar[i]+'"]').prop("checked", true);
		}

	});
}

function makeCheckBox(data, target, all) {
	var strOption = "<label class='d-inline-block mr-3 checkbox-inline'>" +
		"<input id='" + target + "All' type='checkbox' value='" + all + "' name='carList'/> " + all + "전체</label><br>";

	for ( var i=0; i< data.length; i++) {
		strOption += "<label class='d-inline-block mr-3 checkbox-inline'>" +
			"<input id='" + data[i].crIdx + "' type='checkbox' value='" + data[i].modelName + "' name='carList'/> "
			+ "("+ data[i].carNumber +") "+ data[i].year + " "+ data[i].modelName + " " +  data[i].modelDetailName + " (" + data[i].fuelCode + ")"
			+ "</label><br>";
	}

	$('#' + target).empty();
	$('#' + target).append(strOption).addClass('mr-3 checkbox-inline');
}

// validation
function detailValidation(){
	let title, text, icon, cancel_text;

	let companyName 			=  getPureText($('#companyName').val());
	let perIdx                  =  getPureText($('#perIdx').val());
	let periodStartDt           =  $('#periodStartDt').val();
	let periodEndDt             =  $('#periodEndDt').val();
	let discountExtrachargeCode =  $("input:radio[name='rd_discountExtrachargeCode']:checked").val();
	let periodPay               =  getPureText($('#periodPay').val());
	let periodEtc               =  getPureText($('#periodEtc').val());
	let delYn				    =  getPureText($('#delYn').val());

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
	}else if(isEmpty(periodStartDt)){
		errorAlert('일정 시작', '일정 시작일을 선택해주세요.');
		$('#periodStartDt').focus();
		return;
	}else if(isEmpty(periodEndDt)){
		errorAlert('일정 종료', '일정 종료일을 선택해주세요.');
		$('#periodEndDt').focus();
		return;
	}else if(periodStartDt > periodEndDt){
		errorAlert('일정', '일정 시작 시작일은 종료일 보다 클수 없습니다.');
		return;
	}else if(isEmpty(discountExtrachargeCode)) {
		errorAlert('기간요금', '할인 / 할증을 선택해주세요.');
		$('#periodPay').focus();
		return;
	}else if(isEmpty(periodPay)) {
		errorAlert('기간요금', '기간요금을 입력해주세요.');
		$('#periodPay').focus();
		return;
	}else if(!isEmpty(periodPay) && !$.isNumeric(periodPay)){
		errorAlert('기간요금', '기간요금은 숫자만 입력 가능합니다.');
		$('#periodPay').focus();
		return;
	}else if(isEmpty(periodEtc)){
		errorAlert('내용', '내용을 입력해 주세요.');
		$('#periodEtc').focus();
		return;
	}

	let rtIdx = companyName;

	let req = {
		'rtIdx'	: rtIdx
		,   'crIdx'	: carList
		,	'perIdx' : perIdx
		,	'periodEtc' : periodEtc
		,	'periodStartDt' : periodStartDt
		,	'periodEndDt' : periodEndDt
		,	'discountExtrachargeCode' : discountExtrachargeCode
		,	'periodPay' : periodPay
		,	'delYn' : delYn
		,	'modId' : GLOBAL_LOGIN_USER_IDX
		,	'regId' : GLOBAL_LOGIN_USER_IDX
	};

	let method = '';
	if( CRUD_METHOD === 'insert' ){
		method = 'insertPlanSettingDetail';
	}else{
		method = 'updatePlanSettingDetail';
	}

	title = '기간요금제 저장';
	text = '저장하시겠습니까?';
	icon = 'info';
	cancel_text = '취소하셨습니다.';

	call_before_save(title, text, icon, cancel_text, method, req);

}

//submit
function detailSubmit(method, reqParam){

	let _target = method;
	let _method = CRUD_METHOD;
	let param = reqParam;

	fn_callApi(_method, _target, param, function(response) {
		let data = response;

		// if(response.code == 200 && response.data.result == 1){
		if(data.res === 1) {
			swal("저장 성공", { icon: "success"});
		}else{
			errorAlert('저장 실패', '관리자에게 문의하세요.');
		}
	});

}
var chkValDate = function( date ){
	if (date.length == 8) {
		return date.match(/[0-9]{8}/g);
	}else if  (date.length == 10) {
		return date.match(/[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}/g);
	}
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