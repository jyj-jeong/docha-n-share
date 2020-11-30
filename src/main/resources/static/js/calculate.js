/*
 * matchingService.js
 * 예약 > 매칭서비스
 * 
 * 2020-02-04 lws 최초생성
 * 
 * 
 * 
 * update history
 * =============================================
 * |date       |comment             | author   |
 * =============================================
 * |           |                    |          |
 * 
 * 
 * 
 * 
 * */
var GLOABL_DETAIL_GBN_DT = '';  
var CURRENT_PAGE = 0;

var MODAL_NAME = 'calculateDateCompanyModal';
var MODAL_TITLE = '회원사 상세';
var MODAL_WIDTH = 1500;
var MODAL_HEIGHT = 1500;
var CRUD = '';		// 회원사 저장 구분자
var MCRUD = '';		// 회원사스템 저장 구분자
var RMCRUD = '';	// 회원사 특정기간 저장 구분자
var HCRUD = '';		// 휴무일 저장 구분자

var OPTION = {};

var SEARCHDATE = '';


function initializingPageData(){
	
	OPTION = {
			radius : 5,
			padding : 20,
			closeButton : true,
			overlayClose : false,
			width  : MODAL_WIDTH,
			height : MODAL_HEIGHT,
			//title : MODAL_TITLE,
			headerColor : '#002e5b',
			backdrop : 'static',
			keyboard : false,
			onClosing : fnSearch
		} 
	
	$(".izModal").iziModal(OPTION);
	
	loadApi(drawTable, null, null);
	initSelectBox(); 
	
}

var fnSearch = function fnSearch(){
	loadApi(drawTable, null, null);
}

function loadApi (fnc, page, displayPageNum, division) {

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
	
	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
	
	let searchStartDt = $("#searchStartDt").val();
	let searchEndDt = $("#searchEndDt").val(); 
	
	//날짜포멧에 맞지 않을경우 ''로 검색조건 초기화(조건만 초기화, view는 바꾸지 않음)
	let re = /[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])/;
	
	searchStartDt = re.test(searchStartDt) ? searchStartDt : '';
	searchEndDt = re.test(searchEndDt) ? searchEndDt : '';

    var req = {
    		page : CURRENT_PAGE,
    		displayPageNum : displayPageNum,
    		searchStartDt : searchStartDt,
    		searchEndDt : searchEndDt
    		
    };  
    
	var target = 'calculateInfoList';   

	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
    	 }
     });//end 

}

var drawTable = function drawTable(res, page, displayPageNum){
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
		
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
	
	var data = res.result;

	var columns;
	columns = [  
  
		{ "name": "rowNumber", "id" : "rowNum" ,"title": "No" , "visible": false },
		{ "name": "accountExpDt", "title": "정산일", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){
				var displayText = '';  
				displayText = dateFormatter(value); 
				return displayText; 
			}
		},  
		{ "name": "companyCount", "title": "회원사" ,   
			"formatter" : function(value, options, rowData){    
				var seq = dateFormatter(rowData.rentGbnDt).replace(/\./g, '');
				return "<a href=# onclick='javascript:calDateCompanyInfo(" + 'this' + ");'  >"+value+" 건</a>";             
			}
		},
		{ "name": "rmCount", "title": "예약" ,
			"formatter" : function(value, options, rowData){
				var seq = dateFormatter(rowData.rentGbnDt).replace(/\./g, '');  
				return "<a href=# onclick='javascript:calDateReserveInfo(" + 'this' + ");'  >"+value+" 건</a>";        
			}
		}, 
		{ "name": "totalFee", "title": "결제금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},  
		{ "name": "disCountFee", "title": "할인금액", "breakpoints": "xs sm", 
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value); 
				return displayText;  
			}
		},
		{ "name": "totalAmount", "title": "총금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "dochaDisFee", "title": "두차수수료", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);  
				return displayText;  
			}
		},
		{ "name": "accountExpAmt", "title": "정산예정금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "successAmount", "title": "정산완료금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "onMissFee", "title": "미정산금액", "breakpoints": "xs sm",  
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		}, 
		{ "name": "companyName", "title": "정산", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){ 
				var rowIndex = (Number(rowData.rowNumber)-1); 
				var targetRowData = options.rows[rowIndex];    
				var display = "<button class='col-md-12 mr-2 btn btn-success' onClick='accountPaymentValidation(" + "this, 1" + ")'>정산</button>";   
				return display;  
			}
		} 
	];
	
	

	$('#custTable').empty();
	$('#custTable').footable({
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
    setPageTotalText(totalCnt, $("#custTableTotal")); 

}

function initSelectBox(){
	
	$('#showContents').empty();
	$('#searchSelectBox').empty();
	
	var searchOption = '';
	var countOption = '';
	
	searchOption += '<option>검색1</option>';
	searchOption += '<option>검색2</option>';
	searchOption += '<option>검색3</option>';
	
	//countOption += '<option>전체</option>';
	countOption += "<option value='10'>10개씩 보기</option>";
	countOption += "<option value='20'>20개씩 보기</option>";
	countOption += "<option value='30'>30개씩 보기</option>";
	countOption += "<option value='60'>60개씩 보기</option>";
	
	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);
	
	$("#searchSelectBox option:eq(0)").prop("selected", true);
	$("#showContents option:eq(0)").prop("selected", true);
	
	bindEvent();
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
 

function locationCompanyDetail(gbnDt){  
	GLOABL_DETAIL_GBN_DT = getPureText(gbnDt);   
	var menuUrl = '/static/viewContents/calculate/calculateCompanyDtail.html';
	var menuNm = ' 회원사 ';
	
	$('#detailWrapper').load(menuUrl, function(){     
		showDetail();  
		$('#detailTitle').text(menuNm);
		initializingDetailPageData();  
	});  
}


function locationCustDetail(gbnDt){
	GLOABL_DETAIL_GBN_DT = gbnDt;  
	var menuUrl = '/static/viewContents/calculate/calculateCustDtail.html';
	var menuNm = '고객별 상세'
	var depthFullName = ' 정산 > 고객별 상세';
	//$('.app-main__inner').empty();
	$('#detailWrapper').load(menuUrl, function(){     
		showDetail();   
		//$('#detailTitle').text(menuNm);
		initializingDetailPageData();
	});
}
  


function setPageTotalText(sum, obj){
	var sumText = '총 ['+sum+'] 건이 검색되었습니다.';
	$(obj).text(sumText);
}




/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){
	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
}

// validation
function detailValidation(){
	
	var updateFlag = false;   
	  
	$('#detailWrapper').find('input').each(function(){  
		var value = this.value;
		// 디테일팝업 모든 input 반복문 함수
		updateFlag = true;
	});
	
	alert('저장하시겠습니까?');
	if(updateFlag){
		detailPreSubmit();
	}
}

// 데이터 가공
function detailPreSubmit(){
	
	detailSubmit();
}

// submit
function detailSubmit(){
	
	// convert json
	var data = $('#detailForm').serialize();
	closeDetail();
}



/* =========================== detail function end ======================================*/

/* =========================== calculateDateCompanyModal load function start ======================================*/

function calDateCompanyInfo(obj){
	
	//상세화면 진입시 해당일자의 정산 데이터를 가져오기 위한 처리
	SEARCHDATE = $(obj).closest('tr').children(":eq(1)").text();
	
	//modal title를 유동적으로 처리하기 위해 받아온 data로 modal타이틀을 setting 
	let title = "정산 - 회원사별 ( 정산예정일 :  " + SEARCHDATE + ")";
	
	$("#calculateDateCompanyModal").iziModal('setTitle', title);
	$("#calculateDateCompanyModal").iziModal('open');
	
	loadCalculateDateCompany(calculateDateCompanyModalDrawTable, SEARCHDATE, null, null, null);
	initDateCompanySelectBox();
}

function loadCalculateDateCompany (fnc, date, page, displayPageNum, division) {

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
	
	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

	let searchType = $("#dateCompanySearchSelectBox").val();
	let searchKeyWord = $("#dateCompanySearchValue").val();
    
    var req = {
    		page: CURRENT_PAGE,
    		displayPageNum: displayPageNum,
    		accountExpDt : date,
    		searchKeyWord : searchKeyWord,
    		searchType : searchType
    };  
    
	var target = 'calculateDateCompanyList';   

	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
    	 }
     });//end 

}

var calculateDateCompanyModalDrawTable = function calculateDateCompanyModal(res, page, displayPageNum){
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
		
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
	
	var data = res.result;

	var columns;
	columns = [  
  
		{ "name": "rowNumber", "id" : "rowNum" ,"title": "No" , "visible": false },
		{ "name": "rtIdx", "id" : "rtIdx" ,"title": "No" , "visible": false },
		{ "name": "companyName", "title": "회원사", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){
				
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];
				let displayText = '';
				let companyName = nullCheck(value);
				let branchName = nullCheck(targetRowData.branchName); 
				if(branchName.length > 0){
					displayText = companyName + '<br/>(' + branchName + ')' ;
				}else{
					displayText = companyName;
				}
				 
				return displayText;
				
			}
		},  
		{ "name": "companyRegistrationName", "title": "법인명" ,   
			"formatter" : function(value, options, rowData){    
				return nullCheck(value);      
			}
		},
		{ "name": "accountBank", "title": "은행</br>계좌번호" ,
			"formatter" : function(value, options, rowData){
				
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];
				let displayText = '';
				let accountBank = nullCheck(value);
				let accountNumber = nullCheck(targetRowData.accountNumber); 
				
				displayText = accountBank + '<br/>' + accountNumber;
				 
				return displayText;   
			}
		},
		{ "name": "rmCount", "title": "예약", "breakpoints": "xs sm",
			/*
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
			*/
		},
		{ "name": "totalFee", "title": "결제금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},  
		{ "name": "disCountFee", "title": "할인금액", "breakpoints": "xs sm", 
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value); 
				return displayText;  
			}
		},
		{ "name": "totalAmount", "title": "총금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "dochaDisFee", "title": "두차수수료", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);  
				return displayText;  
			}
		},
		{ "name": "accountExpAmt", "title": "정산예정금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "successAmount", "title": "정산완료금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "onMissFee", "title": "미정산금액", "breakpoints": "xs sm",  
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "companyName", "title": "정산", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){ 
				var rowIndex = (Number(rowData.rowNumber)-1); 
				var targetRowData = options.rows[rowIndex];    
				var display = "<button class='col-md-12 mr-2 btn btn-success' onClick='accountPaymentValidation(" + "this, 2" + ")'>정산</button>";     
				return display;  
			}
		}
	];
	
	

	$('#dateCompanyTable').empty();
	$('#dateCompanyTable').footable({
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
    displayPageNum = res.paging.cri.displayPageNum;

    //page는 전역변수 사용
    var dateCompanyTablePagePrev = res.paging.prev; 
    var dateCompanyTablePageNext = res.paging.next;
	
    makePaging(totalCnt, perPageNum, displayPageNum, page, dateCompanyTablePagePrev, dateCompanyTablePageNext, $("#dateCompanyTablePage"));
    setPageTotalText(totalCnt, $("#custDateCompanyTableTotal")); 

}

function initDateCompanySelectBox(){
	
	$('#dateCompanyShowContents').empty();
	$('#dateCompanySearchSelectBox').empty();
	
	let searchOption = '';
	let countOption = '';
	
	searchOption += "<option></option>";
	searchOption += "<option value='CN'>회원사</option>";
	searchOption += "<option value='RN'>법인명</option>";
	
	countOption += "<option value='10'>10개씩 보기</option>";
	countOption += "<option value='20'>20개씩 보기</option>";
	countOption += "<option value='30'>30개씩 보기</option>";
	countOption += "<option value='60'>60개씩 보기</option>";
	
	$('#dateCompanyShowContents').append(countOption);
	$('#dateCompanySearchSelectBox').append(searchOption);
	
	$("#dateCompanyShowContents option:eq(0)").prop("selected", true);
	$("#dateCompanySearchSelectBox option:eq(0)").prop("selected", true);
	
	bindDateCompanyTablePage();
}


function bindDateCompanyTablePage(){  
	
	$("#dateCompanyTablePage").on('click', 'a', function() {
		if ($(this).attr('class') != 'active') {
			let clickPage = $(this).text();
			
			let displayPageNum = $("#dateCompanyShowContents").val();
			
			let current;
			
			if(!isNaN(clickPage)) { //숫자면 현재 페이지므로 
				current = parseInt(clickPage);
			} else { //
				if(clickPage == '«Previous Over') {					
					current = parseInt(CURRENT_PAGE) - 1;
				} if(clickPage == '»Next Over') {
					current = parseInt(CURRENT_PAGE) + 1;
				}
			}
			
			loadCalculateDateCompany(calculateDateCompanyModalDrawTable, SEARCHDATE, current, displayPageNum);

		}
	}); 	
} 

var dateCompanySearch = function dateCompanySearch(){
	loadCalculateDateCompany(calculateDateCompanyModalDrawTable, SEARCHDATE, null, null);
}

/* =========================== calculateDateCompanyModal load function end ======================================*/

/* =========================== calculateDateReserveModal load function start ======================================*/

function calDateReserveInfo(obj){
	
	//상세화면 진입시 해당일자의 정산 데이터를 가져오기 위한 처리
	SEARCHDATE = $(obj).closest('tr').children(":eq(1)").text();
	
	//modal title를 유동적으로 처리하기 위해 받아온 data로 modal타이틀을 setting 
	let title = "정산 - 예약별 ( 정산예정일 :  " + SEARCHDATE + ")";
	
	$("#calculateDateReserveModal").iziModal('setTitle', title);
	$("#calculateDateReserveModal").iziModal('open');
	
	loadCalculateDateReserve(calculateDateReserveModalDrawTable, SEARCHDATE, null, null, null);
	initDateReserveSelectBox();
}

function loadCalculateDateReserve (fnc, date, page, displayPageNum, division) {

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
	
	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

	let searchType = $("#dateReserveSearchSelectBox").val();
	let searchKeyWord = $("#dateReserveSearchValue").val();
    
    var req = {
    		page: CURRENT_PAGE,
    		displayPageNum: displayPageNum,
    		accountExpDt : date,
    		searchKeyWord : searchKeyWord,
    		searchType : searchType
    };
    
	var target = 'calculateDateReserveList';   

	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
    	 }
     });//end 

}

var calculateDateReserveModalDrawTable = function calculateDateReserveModal(res, page, displayPageNum){
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
		
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
	
	var data = res.result;

	var columns;
	columns = [  
  
		{ "name": "rowNumber", "id" : "rowNum" ,"title": "No" , "visible": false },
		{ "name": "rmIdx", "id" : "rmIdx" ,"title": "No" , "visible": false },
		{ "name": "rmIdx", "title": "예약번호", "breakpoints": "xs sm",
			/*
			"formatter" : function(value, options, rowData){
				
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];
				let displayText = '';
				let companyName = nullCheck(value);
				let branchName = nullCheck(targetRowData.branchName); 
				if(branchName.length > 0){
					displayText = companyName + '<br/>(' + branchName + ')' ;
				}else{
					displayText = companyName;
				}
				 
				return displayText;
				
			}
			*/
		},  
		{ "name": "rentStartDay", "title": "대여일시<br/>반납일시", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				var rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1); 
				var targetRowData = options.rows[rowIndex];     
				displayText += dateFormatter(targetRowData.rentStartDay,'-');   
				displayText += ' ';
				displayText += timeFormatter(targetRowData.rentStartTime , null);
				displayText += '<br/>';
				displayText += dateFormatter(targetRowData.rentEndDay, '-');
				displayText += ' ';  
				displayText += timeFormatter(targetRowData.rentEndTime , null);   
				return displayText;
			}
		},
		{ "name": "modelName", "title": "모델<br/>차량번호", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				var rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1); 
				var targetRowData = options.rows[rowIndex];   
				var displayText = '<font color="blue">' + nullCheck(value) + '</font><br/>';  
				displayText += nullCheck(targetRowData.modelDetailName) + ' | <font color="red">' + nullCheck(targetRowData.fuelName) + '</font>';    
				//console.log(targetRowData.modelDetailName);
				//console.log(targetRowData.fuelName);
				displayText += '<br/>';
				displayText += nullCheck(targetRowData.carNumber);   
				return displayText;
			} 
		}, 		
		{ "name": "totalFee", "title": "결제금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},  
		{ "name": "disCountFee", "title": "할인금액", "breakpoints": "xs sm", 
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value); 
				return displayText;  
			}
		},
		{ "name": "totalAmount", "title": "총금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "dochaDisFee", "title": "두차수수료", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);  
				return displayText;  
			}
		},
		{ "name": "accountExpAmt", "title": "정산예정금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "successAmount", "title": "정산완료금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "onMissFee", "title": "미정산금액", "breakpoints": "xs sm",  
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "companyName", "title": "정산", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){ 
				var rowIndex = (Number(rowData.rowNumber)-1); 
				var targetRowData = options.rows[rowIndex];    
				var display = "<button class='col-md-12 mr-2 btn btn-success' onClick='accountPaymentValidation(" + "this, 3" + ")'>정산</button>";   
				return display;  
			}
		}
	];
	
	

	$('#dateReserveTable').empty();
	$('#dateReserveTable').footable({
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
    displayPageNum = res.paging.cri.displayPageNum;

    //page는 전역변수 사용
    var dateCompanyTablePagePrev = res.paging.prev; 
    var dateCompanyTablePageNext = res.paging.next;
	
    makePaging(totalCnt, perPageNum, displayPageNum, page, dateCompanyTablePagePrev, dateCompanyTablePageNext, $("#dateReserveTablePage"));
    setPageTotalText(totalCnt, $("#custDateReserveTableTotal")); 

}

function initDateReserveSelectBox(){
	
	$('#dateReserveShowContents').empty();
	$('#dateReserveSearchSelectBox').empty();
	
	let searchOption = '';
	let countOption = '';
	
	searchOption += "<option></option>";
	searchOption += "<option value='RM'>예약번호</option>";
	searchOption += "<option value='MD'>모델명</option>";
	
	countOption += "<option value='10'>10개씩 보기</option>";
	countOption += "<option value='20'>20개씩 보기</option>";
	countOption += "<option value='30'>30개씩 보기</option>";
	countOption += "<option value='60'>60개씩 보기</option>";
	
	
	$('#dateReserveShowContents').append(countOption);
	$('#dateReserveSearchSelectBox').append(searchOption);
	
	
	$("#dateReserveShowContents option:eq(0)").prop("selected", true);
	$("#dateReserveSearchSelectBox option:eq(0)").prop("selected", true);
	
	bindDateReserveTablePage();
}


function bindDateReserveTablePage(){  
	
	$("#dateReserveTablePage").on('click', 'a', function() {
		if ($(this).attr('class') != 'active') {
			let clickPage = $(this).text();
			
			let displayPageNum = $("#dateReserveShowContents").val();
			
			let current;
			
			if(!isNaN(clickPage)) { //숫자면 현재 페이지므로 
				current = parseInt(clickPage);
			} else { //
				if(clickPage == '«Previous Over') {					
					current = parseInt(CURRENT_PAGE) - 1;
				} if(clickPage == '»Next Over') {
					current = parseInt(CURRENT_PAGE) + 1;
				}
			}
			
			loadCalculateDateReserve(calculateDateReserveModalDrawTable, SEARCHDATE, current, displayPageNum);

		}
	}); 	
} 

var dateReserveSearch = function dateReserveSearch(){
	loadCalculateDateReserve(calculateDateReserveModalDrawTable, SEARCHDATE, null, null);
}


/* =========================== calculateDateReserveModal load function end ======================================*/

function accountPaymentValidation(obj, cate){
	
	let swal_title = "정산처리";
	let swal_text = "정산처리를 하시겠습니까?";
	let swal_icon = "info";
	
	let idxData = $(obj).closest('tr').children(":eq(1)").text();
	//미정산금액이 0원미만일경우 처리
	let onmiss = $(obj).closest('tr').children(":last").prev().text();

	let re1 = /원/gi;
	let re2 = /,/gi;
	
	onmiss = onmiss.replace(re1, "");
	onmiss = onmiss.replace(re2, "");
	
	if(onmiss < 1){
		errorAlert('결제할 금액이 0원 미만입니다.');
		return false;
	}
	
	let callback = new Object();
	
	console.log(test);
	let req = {};
	
	if(cate == 2){
		req = {
	    		'rtIdx' : idxData, 
	    		accountExpDt : SEARCHDATE
	    };
		callback = dateCompanySearch;
	}else if(cate == 3){
		req = {
	    		'rmIdx' : idxData,
	    		accountExpDt : SEARCHDATE
	    };
		callback = dateReserveSearch;
	}else if(cate == 1){
		req = {
	    		accountExpDt : idxData
	    };
		callback = fnSearch;
	}
	

	
	//다음결제일
	//let paymenScheduletDate = $(obj).closest('tr').children(":eq(11)").text();
	
	//총결제액
	//let sumPaymentAmount = $(obj).closest('tr').children(":eq(9)").text();
	
	//총결제예정금액
	//let paymentTotalAmount = $(obj).closest('tr').children(":eq(8)").text();
	
	//sumPaymentAmount = nullCheck(sumPaymentAmount);
	//paymentTotalAmount = nullCheck(paymentTotalAmount);
	
	/*
	let re1 = /원/gi;
	let re2 = /,/gi;
	
	sumPaymentAmount = sumPaymentAmount.replace(re1, "");
	paymentTotalAmount = paymentTotalAmount.replace(re1, "");
	
	sumPaymentAmount = sumPaymentAmount.replace(re2, "");
	paymentTotalAmount = paymentTotalAmount.replace(re2, "");
	
	sumPaymentAmount = parseInt(sumPaymentAmount, 10);
	paymentTotalAmount = parseInt(paymentTotalAmount, 10);
	*/
	
	//console.log(sumPaymentAmount);
	//console.log(paymentTotalAmount);
	
	/*
	if(paymentTotalAmount - sumPaymentAmount < 1){
		errorAlert('결제할 금액이 0원 미만입니다.');
		return false;
	}
	

	
	//결제처리요청이 결제예정일보다 이전일경우 알림메세지 변경
	if(nullCheck(paymenScheduletDate).length > 0){
		let dateArray = paymenScheduletDate.split('-');
		
		let year = dateArray[0];
		let month = dateArray[1] - 1;
		let day = dateArray[2];
		let date = new Date();
		date.setFullYear(year, month, day); // 실제 사용하는 월에서 1을 빼고 셋팅을 해줘야 한다. Date 객체에서는 월의 값으로 0부터 11을 사용한다.
		let today = new Date(); 

		if(today.getTime() > date.getTime()) {
			swal_text = "해당 결제는 아직 정기결제일이 되기 전입니다. 결제 하시겠습니까?"
		}
	}
	*/
	
	swal({
		  title: swal_title,
		  text: swal_text,
		  icon: swal_icon,
		  buttons: true,
		  dangerMode: true,
		})
		.then(function(willSave) {
			if (willSave) { //ok
				accountPayment(req, callback);
			} else {
				return;
				//swal(cancel_text);
			}
		});
	
	//periodicPayment(req);
}


function accountPayment(req, callback){
	let target = 'calculateinertAccounts';
	let method = 'insert';

	if(isEmpty(target)) { //is not empty
		errorAlert('API ERROR api target이 존재하지 않습니다. 관리자에게 문의하세요');
	}
	
	if(isEmpty(method)) { //is not empty
		errorAlert('API ERROR api method가 존재하지 않습니다. 관리자에게 문의하세요');
	}
	
	fn_callApi(method, target, req, function(response) {
		let res = response;
		
		if (res.code == 200) {
			
			if(res.data.result == 0 ){
				swal("정산처리가 완료 되었습니다", { icon: "success"});
				callback();
			}
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			//console.log(res);
			if(res.data.result == 1){
				errorAlert('저장 실패', '해당 정산처리건 조회 중 오류가 발생했습니다. 재 조회 후 다시 시도 해 주십시오.');
			}else if(res.data.result == 2){
				errorAlert('저장 실패', '정산처리 저장 중 오류가 발생했습니다. 잠시 후 다시 시도 해 주십시오.');
			}else{
				errorAlert('저장 실패', '처리 중 오류가 발생했습니다. 관리자에게 문의 해 주세십시오.');
			}
		}
	});// end fn_callApi	
}

//날짜 마스킹 처리
Array.prototype.forEach.call(document.body.querySelectorAll("*[data-mask]"), applyDataMask);
function applyDataMask(field) {
    var mask = field.dataset.mask.split('');
    
    // For now, this just strips everything that's not a number
    function stripMask(maskedData) {
        function isDigit(char) {
            return /\d/.test(char);
        }
        return maskedData.split('').filter(isDigit);
    }
    
    // Replace `_` characters with characters from `data`
    function applyMask(data) {
        return mask.map(function(char) {
            if (char != '_') return char;
            if (data.length == 0) return char;
            return data.shift();
        }).join('')
    }
    
    function reapplyMask(data) {
        return applyMask(stripMask(data));
    }
    
    function changed() {   
        var oldStart = field.selectionStart;
        var oldEnd = field.selectionEnd;
        
        field.value = reapplyMask(field.value);
        
        field.selectionStart = oldStart;
        field.selectionEnd = oldEnd;
    }
    
    field.addEventListener('click', changed)
    field.addEventListener('keyup', changed)
}