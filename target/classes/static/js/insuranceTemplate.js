/*
 * insuranceTemplate.js
 * 차량 > 요금제 > 보험템플릿
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

var modalName = 'insuranceTemplateDetail';
var modalTitle = '보험템플릿 상세';
var modalWidth = 900;
var DEFAULT_PAGENUM = 10;
var CURRENT_PAGE = 0;
var CRUD_METHOD = ''

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
	 
	var target = 'insuranceTemplateinfo';
	var method = 'select';
    
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
		{ "name": "ciIdx", "title": "보험템플릿번호" ,
			"formatter" : function(value, options, rowData){
				var seq = rowData.ciIdx;
				return '<a href="javascript:initDetailInfo(' + "'" +seq + "'" +');"  >'+value+'</a>';               
			}
		},
		{ "name": "companyName", "title": "회원사<br>(지점)" ,
			"formatter" : function(value, options, rowData){
				let displayText = '';
				
				let companyName = nullCheck(value);
				let branchName = nullCheck(rowData.branchName);
				if(!isEmpty(branchName)){
					displayText = companyName + '<br>'+ '(' +  branchName + ')'; 
				}else{
					displayText = companyName; 
				}
                
				return displayText;
			}  
		},
		{ "name": "ciEtc", "title": "제목", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				
				let displayText = '';
				
				displayText = nullCheck(value);
				return displayText;
			}  
			
		},
		{ "name": "personalCover", "title": "대인", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				
				let displayText = '';
				displayText = objectConvertToPriceFormat(value) + (nullCheck(value) == '무한' ? '' : ' 원');
				
				return displayText;
			}  
			
		},
		{ "name": "propertyDamageCover", "title": "대물", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				
				let displayText = '';
				displayText = objectConvertToPriceFormat(value) + ' 원';
				
				return displayText;
			}  
			
		},
		{ "name": "onselfDamageCover", "title": "자손", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				
				let displayText = '';
				displayText = objectConvertToPriceFormat(value) + ' 원';
				
				return displayText;
			}  
		},
		{ "name": "settingCarCnt", "title": "설정차량대수", "breakpoints": "xs sm md" },
		{ "name": "branchName", "title": "지점", "breakpoints": "xs sm md", "visible":false }

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
	searchOption += '<option value="ciIdx" >보험템플릿번호</option>';
	searchOption += '<option value="companyName" >회원사</option>';
	searchOption += '<option value="branchName" >지점</option>';
	searchOption += '<option value="ciEtc" >제목</option>';

	countOption += '<option selected value="10">10개씩 보기</option>';
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
	if ($("carDamage1Yn").is(":checked")) {
		$("carDamage1Yn").val();
	}else{
		$("carDamage1Yn").val("N");
	}

	$("input:checkbox[name='carDamage1Yn']:checkbox[value='Y']").prop('checked', true);
	$("input:checkbox[name='carDamage2Yn']:checkbox[value='Y']").prop('checked', true);
	$("input:checkbox[name='carDamage3Yn']:checkbox[value='Y']").prop('checked', true);
	$("input:checkbox[name='carDamage4Yn']:checkbox[value='Y']").prop('checked', true);
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

	let ciIdx = '';
	
	let target = 'insuranceTemplateinfoDetail';
	let method = 'select';

	ciIdx = seq;
	
	let req = {
			ciIdx : ciIdx
	};
	
	fn_callApi(method, target, req, function(response) {
		// let res = response;

		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {

			let data = response[0];
			
			let strOption = "";
			
			let companyName 				= nullCheck(data.companyName);
			let branchName 				    = nullCheck(data.branchName);
			let ciIdx                      	= nullCheck(data.ciIdx);
			let rtIdx                       = nullCheck(data.rtIdx);
			let onselfDamageCover           = nullCheck(data.onselfDamageCover) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.onselfDamageCover));;
			let personalCover               = nullCheck(data.personalCover) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.personalCover));;
			let propertyDamageCover     	= nullCheck(data.propertyDamageCover) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.propertyDamageCover));;
			let carDamageCover              = nullCheck(data.carDamageCover) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.carDamageCover));
			let insuranceCopayment          = nullCheck(data.insuranceCopayment) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.insuranceCopayment));
			let carDamageCover2             = nullCheck(data.carDamageCover2) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.carDamageCover2));
			let insuranceCopayment2         = nullCheck(data.insuranceCopayment2) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.insuranceCopayment2));
			let carDamageCover3             = nullCheck(data.carDamageCover3) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.carDamageCover3));
			let insuranceCopayment3         = nullCheck(data.insuranceCopayment3) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.insuranceCopayment3));
			let carDamageCover4             = nullCheck(data.carDamageCover4) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.carDamageCover4));
			let insuranceCopayment4         = nullCheck(data.insuranceCopayment4) == '' ? '' : objectConvertToPriceFormat(nullCheck(data.insuranceCopayment4));
			let carDamage1Yn                = nullCheck(data.carDamage1Yn) == '' ? 'N' : nullCheck(data.carDamage1Yn);
			let carDamage2Yn                = nullCheck(data.carDamage2Yn) == '' ? 'N' : nullCheck(data.carDamage2Yn);
			let carDamage3Yn                = nullCheck(data.carDamage3Yn) == '' ? 'N' : nullCheck(data.carDamage3Yn);
			let carDamage4Yn                = nullCheck(data.carDamage4Yn) == '' ? 'N' : nullCheck(data.carDamage4Yn);
			let ciEtc                		= nullCheck(data.ciEtc);
			let delYn				        = nullCheck(data.delYn);
			
			if(!isEmpty(nullCheck(branchName))){
				companyName = companyName + "(" + nullCheck(branchName) + ")";
			}else{
				companyName = companyName;
			}

			let companyNameItem = "<option selected value='" + rtIdx + "'>"+companyName + "</option>";
			$('#companyName').empty();
			$('#companyName').append(companyNameItem); 
			$('#companyName').attr('disabled', true);

			$('#ciIdx').val(ciIdx);
			$('#ciEtc').val(ciEtc);

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

			$('#personalCover').val(personalCover);
			$('#onselfDamageCover').val(onselfDamageCover);
			$('#propertyDamageCover').val(propertyDamageCover);
			
			$('#carDamageCover').val(carDamageCover);
			$('#insuranceCopayment').val(insuranceCopayment);
			$('#carDamageCover2').val(carDamageCover2);
			$('#insuranceCopayment2').val(insuranceCopayment2);
			$('#carDamageCover3').val(carDamageCover3);
			$('#insuranceCopayment3').val(insuranceCopayment3);
			$('#carDamageCover4').val(carDamageCover4);
			$('#insuranceCopayment4').val(insuranceCopayment4);

			if(carDamage1Yn == 'Y') { 
				$("input:checkbox[name='carDamage1Yn']:checkbox[value='Y']").prop('checked', true); // 선택하기
			} else { 
				$("input:checkbox[name='carDamage1Yn']:checkbox[value='N']").prop('checked', true); // 선택하기
			}
			if(carDamage2Yn == 'Y') { 
				$("input:checkbox[name='carDamage2Yn']:checkbox[value='Y']").prop('checked', true); // 선택하기
			} else { 
				$("input:checkbox[name='carDamage2Yn']:checkbox[value='N']").prop('checked', true); // 선택하기
			}
			if(carDamage3Yn == 'Y') { 
				$("input:checkbox[name='carDamage3Yn']:checkbox[value='Y']").prop('checked', true); // 선택하기
			} else { 
				$("input:checkbox[name='carDamage3Yn']:checkbox[value='N']").prop('checked', true); // 선택하기
			}
			if(carDamage4Yn == 'Y') { 
				$("input:checkbox[name='carDamage4Yn']:checkbox[value='Y']").prop('checked', true); // 선택하기
			} else { 
				$("input:checkbox[name='carDamage4Yn']:checkbox[value='N']").prop('checked', true); // 선택하기
			}
			
			// openIziModal(modalName);
			CRUD_METHOD = 'update';
			
		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		//
		// }
	});// end fn_callApi
//	openIziModal(modalName);
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
	
	if(CRUD_METHOD == 'insert'){
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
		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {
		// 	let data = res.data.result.result;
			let strOption = "<option value=''>선택</option>";     
			for ( var i=0; i<data.length; i++ ) {   
				let companyName = '';
				if(!isEmpty(data[i].branchName)){
					companyName = data[i].companyName + "(" + nullCheck(data[i].branchName) + ")";
				}else{
					companyName = data[i].companyName;
				}
				strOption += "<option value = '" + data[i].rtIdx + "'>" + companyName + "</option>";  
			} 				 
			$('#companyName').append(strOption);
			$('#companyName').attr('disabled', false);
		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		// }
	});// end fn_callApi

	// company init
	if( CRUD_METHOD == 'insert' ){ 
		// openIziModal(modalName);
	}
	
}

// validation
function detailValidation(){
	
	let title, text, icon, cancel_text;

	let companyName 			=  getPureText($('#companyName').val());
	let ciIdx                   =  getPureText($('#ciIdx').val());
	let personalCover           =  getPureText($('#personalCover').val());
	let onselfDamageCover       =  getPureText($('#onselfDamageCover').val());
	let propertyDamageCover     =  getPureText($('#propertyDamageCover').val());
	let carDamageCover     		=  getPureText($('#carDamageCover').val());
	let insuranceCopayment     	=  getPureText($('#insuranceCopayment').val());
	let carDamageCover2     	=  getPureText($('#carDamageCover2').val());
	let insuranceCopayment2     =  getPureText($('#insuranceCopayment2').val());
	let carDamageCover3    		=  getPureText($('#carDamageCover3').val());
	let insuranceCopayment3     =  getPureText($('#insuranceCopayment3').val());
	let carDamageCover4     	=  getPureText($('#carDamageCover4').val());
	let insuranceCopayment4     =  getPureText($('#insuranceCopayment4').val());

	let carDamage1Yn 			=  $('input:checkbox[id=carDamage1Yn]:checked').val();
	let carDamage2Yn 			=  insuranceCopayment2 == '' ? 'N' : $('input:checkbox[id=carDamage2Yn]:checked').val();
	let carDamage3Yn 			=  insuranceCopayment3 == '' ? 'N' : $('input:checkbox[id=carDamage3Yn]:checked').val();
	let carDamage4Yn 			=  insuranceCopayment4 == '' ? 'N' : $('input:checkbox[id=carDamage4Yn]:checked').val();
	let ciEtc               	=  getPureText($('#ciEtc').val());
	let delYn				    =  getPureText($('#delYn').val());

	if(isEmpty(companyName)){
		errorAlert('회원사', '회원사를 선택하여 주세요.');
		$('#companyName').focus();
		return;
	}
	else if(isEmpty(ciEtc)){
		errorAlert('제목', '제목을 입력해 주세요.');
		$('#ciEtc').focus();
		return;
	}
	else if(isEmpty(personalCover)){
		errorAlert('대인', '대인 입력 하여주세요.');
		$('#personalCover').focus();
		return;
	}else if(isEmpty(propertyDamageCover)){
		errorAlert('대물', '대물 입력 하여주세요.');
		$('#propertyDamageCover').focus();
		return;
	}else if(isEmpty(onselfDamageCover)){
		errorAlert('자손', '자손 입력 하여주세요.');
		$('#onselfDamageCover').focus();
		return;
	}else if(isEmpty(insuranceCopayment)){
		errorAlert('자차보험 1', '자차 보험 요금/일 입력 하여주세요.');
		$('#insuranceCopayment').focus();
		return;
	}else if(isEmpty(carDamageCover)){
		errorAlert('자차보험1', '자차보험 면책금 입력 하여주세요.');
		$('#carDamageCover').focus();
		return;
	}else if(!isEmpty(insuranceCopayment) && !$.isNumeric(insuranceCopayment)){
		errorAlert('자차보험 1', '자차 보험 요금/일 숫자만 입력 가능합니다.');
		$('#insuranceCopayment').focus();
		return;
	}else if(!isEmpty(carDamageCover) && !$.isNumeric(carDamageCover)){
		errorAlert('자차보험 1', '자차보험 면책금 숫자만 입력 가능합니다.');
		$('#carDamageCover').focus();
		return;
	}else if(!isEmpty(insuranceCopayment2) && !$.isNumeric(insuranceCopayment2)){
		errorAlert('자차보험 2', '자차 보험 요금/일 숫자만 입력 가능합니다.');
		$('#insuranceCopayment2').focus();
		return;
	}else if(!isEmpty(carDamageCover2) && !$.isNumeric(carDamageCover2)){
		errorAlert('자차보험 2', '자차보험 면책금 숫자만 입력 가능합니다.');
		$('#carDamageCover2').focus();
		return;
	}else if(!isEmpty(insuranceCopayment3) && !$.isNumeric(insuranceCopayment3)){
		errorAlert('자차보험 3', '자차 보험 요금/일 숫자만 입력 가능합니다.');
		$('#insuranceCopayment3').focus();
		return;
	}else if(!isEmpty(carDamageCover3) && !$.isNumeric(carDamageCover3)){
		errorAlert('자차보험 3', '자차보험 면책금 숫자만 입력 가능합니다.');
		$('#carDamageCover3').focus();
		return;
	}else if(!isEmpty(insuranceCopayment4) && !$.isNumeric(insuranceCopayment4)){
		errorAlert('자차보험 4', '자차 보험 요금/일 숫자만 입력 가능합니다.');
		$('#insuranceCopayment4').focus();
		return;
	}else if(!isEmpty(carDamageCover4) && !$.isNumeric(carDamageCover4)){
		errorAlert('자차보험 4', '자차보험 면책금 숫자만 입력 가능합니다.');
		$('#carDamageCover4').focus();
		return;
	}

	let rtIdx = companyName;
	
	let req = {
		    'rtIdx'	: rtIdx
		,	'ciIdx' : ciIdx
		,	'personalCover' : personalCover
		,	'onselfDamageCover' : onselfDamageCover
		,	'propertyDamageCover' : propertyDamageCover  
		,	'carDamageCover' : carDamageCover  
		,	'insuranceCopayment' : insuranceCopayment
		,	'carDamageCover2' : carDamageCover2
		,	'insuranceCopayment2' : insuranceCopayment2
		,	'carDamageCover3' : carDamageCover3  
		,	'insuranceCopayment3' : insuranceCopayment3
		,	'carDamageCover4' : carDamageCover4  
		,	'insuranceCopayment4' : insuranceCopayment4
		,	'carDamage1Yn' : carDamage1Yn
		,	'carDamage2Yn' : carDamage2Yn
		,	'carDamage3Yn' : carDamage3Yn
		,	'carDamage4Yn' : carDamage4Yn
		,	'ciEtc' : ciEtc
		,	'delYn' : delYn
		// ,	'modId' : GLOBAL_LOGIN_USER_IDX
		// ,	'regId' : GLOBAL_LOGIN_USER_IDX
	}

	let method = '';
	if( CRUD_METHOD == 'insert' ){
		method = 'insertInsuranceTemplate';
	}else{
		method = 'updateInsuranceTemplate';
	}

	title = '보험료템플릿 저장';
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
		let data = response
		$("#"+modalName).iziModal('close');

		if(data.res === 1){
			swal("저장 성공", { icon: "success"});

			loadApi(drawTable, null);  
		}else{
			errorAlert('저장 실패', response.error.errorMessage);
			//errorAlert('저장 실패', '관리자에게 문의하세요.');
		} 
	});

}

$("#" + modalName).iziModal({
	radius: 5,
	padding: 20,
	closeButton: true,
	overlayClose: false,
	width: modalWidth,
	height:1500,
	title: modalTitle,
	headerColor: '#002e5b',
    backdrop: 'static',
    keyboard: false
});

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