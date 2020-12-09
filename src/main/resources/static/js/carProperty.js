/*
 * carProperty.js
 * 차량 > 속성 
 *
 * */

var CURRENT_PAGE = 0;
var CRUD = '';			// 저장 구분자

function initializingPageData(){
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi (fnc, page, displayPageNum, division) {

	CRUD = 'insert';

	let sel_search 		 = $("#sel_search option:selected").val();
	let showContents 	 = $("#showContents option:selected").val();
	let strSearchKeyWord = $("#searchKeyWord").val();
	showContents = isEmpty(showContents) ? 10 : showContents;
	
	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? showContents: (typeof displayPageNum === 'number') ? displayPageNum : showContents;

	if(typeof sel_search == "undefined"){
		sel_search = 'country';
	}

	if(!isEmpty(showContents)) {
		displayPageNum = Number(showContents);
	}
	
    var req = {
    			'page' 		     	: CURRENT_PAGE
			,	'displayPageNum' 	: displayPageNum
			,	'searchType' 	 	: sel_search
			,	'searchKeyWord'     : strSearchKeyWord
    }; 
		     
	 
	var target = 'carPropertyInfo';
	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
    	 }
     });//end fn_callApi

}

	var drawTable = function drawTable(res, page, displayPageNum){
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
		
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
	
	var data = res.result;
	
	var rows = [];
	var columns;
	
	columns = [

		{ "name": "codeIdx", "title": "codeIdx" , "visible": false },   
		{ "name": "code", "title": "코드"},
		{ "name": "codeValue", "title": "이름" ,
			"formatter" : function(value, options, rowData){
				var seq = rowData.codeIdx;

				return '<a href="javascript:locationDetail(' + "'" +seq + "'" +');"  >'+value+'</a>';               
			}
		},
		{ "name": "propertyCnt", "title": "차량등록 대수" }
	];
	
	let rtCode = data[0].rtCode;
	let pCode = data[0].pCode;
	$("#rtCode").val(rtCode);
	$("#pCode").val(pCode);
	

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
	
	let totalCnt = res.paging.totalCount;
	let perPageNum = res.paging.cri.perPageNum;
	let showDisplayPageNum = res.paging.cri.displayPageNum;

    //page는 전역변수 사용
	let prev = res.paging.prev; 
	let next = res.paging.next;
	
    makePaging(totalCnt, perPageNum, showDisplayPageNum, page, prev, next, $("#page"));

	if(!isEmpty(totalCnt)) {
		$('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
	} else {
		$("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
	}

}

function initSelectBox(){
	let searchOption = '';
	let countOption = '';
	
//	searchOption += '<option value="0">선택</option>';
	searchOption += '<option value="country">국가</option>';
	searchOption += '<option value="mf">제조사</option>';
	searchOption += '<option value="carType">등급</option>';
	//searchOption += '<option value="option">옵션</option>';
	searchOption += '<option value="fuel">연료</option>';
	
	countOption += '<option value="10">10개씩 보기</option>';
	countOption += '<option value="20">20개씩 보기</option>';
	countOption += '<option value="30">30개씩 보기</option>';
	countOption += '<option value="60">60개씩 보기</option>';
	
	$('#showContents').append(countOption);
	$('#sel_search').append(searchOption);
	
	$("#sel_search").val("country");
	
	$("#showContents option:eq(0)").prop("selected", true);
	let sel_text = $("#sel_search option:selected").text();
	$("#labtxt").text(sel_text);
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

function locationDetail(seq){ 
	initDetailInfo(seq);
}


/*
 * 모아보기 선택시
 */
function changeList() {
	loadApi(drawTable, null, null);
}


$("#searchKeyWord").keypress(function(e) {
	let searchKeyWord = $("#searchKeyWord").val();
	let sel_search = $("#sel_search option:selected").val();
	
	if(!isEmpty(sel_search)) {
	    if (e.keyCode == 13){
	    	if(isEmpty(sel_search)) {
				errorAlert('검색조건', '검색조건 선택해 주세요.');
	    	} else {
	    		loadApi(drawTable, null, null);
	    	}
	    }
	}else {
		if(isEmpty(sel_search)) {
			errorAlert('검색조건', '검색조건 선택해 주세요.'); 		
    	}
	}
});

$('#btnSearch').click(function(e){
	let searchKeyWord = $("#searchKeyWord").val();
	let sel_search = $("#sel_search option:selected").val();
	let sel_text = $("#sel_search option:selected").text();
	$("#labtxt").text(sel_text);
	
	if(!isEmpty(sel_search)) {
    	loadApi(drawTable, null, null);
	} else {
		errorAlert('검색조건', '검색조건 선택해 주세요.');
	}		

});

/*
 * 모아보기 선택시
 */
function commcodechang() {
	let sel_text = $("#sel_search option:selected").text();
	$("#labtxt").text(sel_text);
	loadApi(drawTable, null, null);
}

/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){
	
//	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
//	if(true){
//		return;
//	}
	//$('#detailForm')[0].reset();     
	let codeIdx = seq;
	
	//// call api module and draw plz ^^;
	var req = {
    		codeIdx: codeIdx,    
    
	};
	 
	var target = 'commonCodeInfo';
	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	
    	if(response.code == 200) {
    		var data = response.data.result[0];
	   		 
	   		var code = data.code;
	   		var codeValue = data.codeValue;
	   		 
			$("#codeIdx").val(codeIdx);
			$('#code').val(code);
			$("#codeValue").val(codeValue);
	   		CRUD = 'update';
    	}
     });//end FindUserInfoList
}

// validation
function detailValidation(){
	let save_type = CRUD;
	let codeIdx = $("#codeIdx").val();
	let rtCode = $("#rtCode").val();
	let pCode = $("#pCode").val();
	let code = $("#code").val();
	let codeValue = $("#codeValue").val();
	let sel_text = $("#sel_search option:selected").text();
	
	if(isEmpty(code)) {
		errorAlert(sel_text, '코드를 입력해 주세요.');
		return;
	}else if(isEmpty(codeValue)) {
		errorAlert(sel_text, '코드명를 입력해 주세요.');
		return;
	}	

	let req = '';
	let res = '';
    let target = 'commonCodeInfo';
    let method = 'select';
    
    if (save_type == 'insert'){
	    //면허정보갸 있는지 먼저 체크한다.
	    req = {
				'rtCode' : rtCode
				,	'pCode' : pCode
				,	'code' : code
		};
	
		fn_callApi( method, target, req, function(response) {
			res = response;
			if (res.code == 200) {
	    		var data = response.data.result[0];
	    		if(!isEmpty(data)){
	    			errorAlert(sel_text, '코드가 존재합니다.');
	    			return;
	    		}
			}
		});// end fn_callApi
    }
    
	req = {
			'codeIdx' : codeIdx
			,	'rtCode' : rtCode
			,	'pCode' : pCode
			,	'code' : code
			,	'codeValue' : codeValue
			,	'modId' : GLOBAL_LOGIN_USER_IDX
			,	'regId' : GLOBAL_LOGIN_USER_IDX
	}

	let title = sel_text + '정보 저장';
	let text = '저장하시겠습니까?'
	let icon = 'info';
	let cancel_text = '취소하셨습니다.';

	call_before_save(title, text, icon, cancel_text, save_type, req);

	//	var updateFlag = false;   
	  
	//$('#detailWrapper').find('input').each(function(){  
	//	var value = this.value;
	//	// 디테일팝업 모든 input 반복문 함수
	//	updateFlag = true;
	//});
	//
	//alert('저장하시겠습니까?');
	//if(updateFlag){
	//	detailPreSubmit();
	//}
}

// submit
function detailSubmit(save_type, req){
	if (CRUD == 'insert') {
		target = 'insertCommonCodeInfo';
		method = 'insert';
	} else if (CRUD == 'update') {
		target = 'updateCommonCodeInfo';
		method = 'update';
	}
	
	fn_callApi(method, target, req, function(response) {
		let res = response;
		
		// 200이라면 페이징을 구한다.
		if (res.code == 200) {

			if (res.data.result == 1) {
				$("#codeIdx").val('');
				$("#rtCode").val('');
				$("#pCode").val('');
				$("#code").val('');
				$("#codeValue").val('');
				swal("저장 성공", {icon : "success"});
				loadApi(drawTable, null, null);
				
			}
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('저장 실패', '관리자에게 문의하세요.');
		}
	});// end fn_callApi

}

/* =========================== detail function end ======================================*/