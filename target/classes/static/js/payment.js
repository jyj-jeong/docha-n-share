/*
 * payment.js
 * 예약 > 정기결제
 * 
 * 
 * */
  
var SEARCH_FORM_RELOAD_FLAG = true;
var GLOBAL_SELECT_COUNT = '';
var GLOBAL_SEARCH_KEYWORD = '';
var GLOBAL_SEARCH_INPUT_GBN = '';
var CURRENT_PAGE = 0;

function initializingPageData(){
	loadApi(drawTable, null);
	initSelectBox();
	bindEvent();
}

function loadApi (fnc, page) {

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(GLOBAL_SELECT_COUNT);

	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

    var req = {
    		'page': CURRENT_PAGE,
    		'displayPageNum': displayPageNum,
    		'searchKeyWord' : GLOBAL_SEARCH_KEYWORD.toUpperCase(),
    		'gbnInput' : GLOBAL_SEARCH_INPUT_GBN
    		
    };

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
		{ "name": "rmIdx", "title": "예약번호" ,
			"formatter" : function(value, options, rowData){
				return '<a href="javascript:locationDetail(' + "'" +value + "'" +');"  >'+value+'</a>';               
			}
		},  
		{ "name": "regDt", "title": "예약일시" , "breakpoints": "xs", 
			"formatter" : function(value, options, rowData){ 
				var displayText = '';
				displayText = regDtFormatter(value);
				return displayText;
			}
		},
		{ "name": "userName", "title": "회원</br>연락처", 
			"formatter" : function(value, options, rowData){
				var rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				var targetRowData = options.rows[rowIndex];
				var displayText = '';
				var userName = nullCheck(value);
				var contact = phoneFomatter(nullCheck(targetRowData.userContact1)); 
				displayText = userName + '<br/>' + contact;
				return displayText;
			}
		},
		{ "name": "rentStartDay", "title": "대여예정일시<br/>반납예정일시", "breakpoints": "xs sm",
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
		{ "name": "companyName", "title": "회원사<br/>지점", "breakpoints": "xs"}, 
		{ "name": "carNumber", "title": "차량번호<br/>모델", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				var rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);  
				var targetRowData = options.rows[rowIndex]; 
				var displayText = '<font color="blue">' + value+'</font>';      
				displayText += '<br/>';
				displayText += targetRowData.modelName + ' | <font color="red">' + targetRowData.fuelName+'</font>';  ;   
				return displayText;
			} 
		},  
		{ "name": "rentStartDay", "title": "사용기간", "breakpoints": "xs sm md" ,
			"formatter" : function(value, options, rowData){				
				var displayText = '';
				var rowIndex = (Number(rowData.rowNumber)-1);   
				var targetRowData = options.rows[rowIndex];    
				var stDate = targetRowData.rentStartDay+targetRowData.rentStartTime;
				var edDate = targetRowData.rentEndDay+targetRowData.rentEndTime;
				var getDateDiff = setDateTimeDiff(stDate,edDate); 
				var mm = getDateDiff[0] == 0 ? '' : getDateDiff[0] + '개월';
				var dd = getDateDiff[1] == 0 ? '' : getDateDiff[1] + '일';
				var hour = getDateDiff[2] == 0 ? '' : getDateDiff[2] + '시간';      
				var min = getDateDiff[3] ==  0 ? '' : getDateDiff[3] + '분'; 
				  
				displayText += mm+dd;         
				  
				return displayText; 
			}
		},
		{ "name": "paymentTotalAmount", "title": "총금액", "breakpoints": "xs sm md",
			"formatter" : function(value, options, rowData){
				var displayText = objectConvertToPriceFormat(value);
				return displayText + '원';
			}
		},  
		{ "name": "sumPaymentAmount", "title": "결제금액", "breakpoints": "xs sm md" ,
			"formatter" : function(value, options, rowData){
				var displayText = objectConvertToPriceFormat(value);
				return displayText+ '원';  
			}
		},
		{ "name": "payCount", "title": "회차", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				var displayText = '';
				var rowIndex = (Number(rowData.rowNumber)-1);   
				var targetRowData = options.rows[rowIndex];    
				var stDate = targetRowData.rentStartDay+targetRowData.rentStartTime;
				var edDate = targetRowData.rentEndDay+targetRowData.rentEndTime;
				var getDateDiff = setDateTimeDiff(stDate,edDate);
				
				var mm = getDateDiff[0];
				var dd = getDateDiff[1] == 0 ? 0 : 1;
				      
				var count = mm+dd; 
					
				return value + "/" + count;  
			}
		}, 
		{ "name": "nextPaymentDay", "title": "결제예정일", "breakpoints": "xs",
			/*
			"formatter" : function(value, options, rowData){
				return '개발중'; 
			}
			*/
		},  
		{ "name": "companyName", "title": "결제", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){ 
				var rowIndex = (Number(rowData.rowNumber)-1); 
				var targetRowData = options.rows[rowIndex];    
				var display = '<button class="col-md-12 mb-2 mr-2 btn btn-primary">카드변경</button>';   
				display += '<br/>';
				display += '<button class="col-md-12 mb-4 mr-2 btn btn-success" onClick="periodicPaymentValidation(' + "'" + targetRowData.rmIdx + "'" + ', this)">결제</button>';   
				return display;  
			}
		}, 
	];
	
	

	$('#paymentList').empty();
	$('#paymentList').footable({  
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
    setPageTotalText(totalCnt);
}

function initSelectBox(){
	
	var searchOption = '';
	searchOption += '<option value="" >선택</option>';
	searchOption += '<option value="NM" >이름</option>';
	searchOption += '<option value="CT" >연락처</option>';
	searchOption += '<option value="CM" >회원사</option>'; 
	searchOption += '<option value="CN" >차량번호</option>';
	
	var countOption = '';
	countOption += '<option selected value="10" >10개씩 보기</option>'; 
	countOption += '<option value="20" >20개씩 보기</option>';
	countOption += '<option value="30" >30개씩 보기</option>';
	countOption += '<option value="60" >60개씩 보기</option>';

	$('#showContents').append(countOption); 
	$('#searchSelectBox').append(searchOption);    
}

function bindEvent(){  
	
	
	$("#page").on('click', 'a', function(){  
		if($(this).attr('class') != 'active'){
			var clickPage = $(this).text();

			if(!isNaN(clickPage)) {
				CURRENT_PAGE = parseInt(clickPage);
			} else { //
				if(clickPage == '«Previous Over') {					
					CURRENT_PAGE = parseInt(CURRENT_PAGE) - 1;
				} if(clickPage == '»Next Over') {
					CURRENT_PAGE = parseInt(CURRENT_PAGE) + 1;
				}
			}
			
			loadApi(drawTable, CURRENT_PAGE);
		}
	});
	
	$("#searchInput").keydown(function(key) {
		if (key.keyCode == 13) {   
			$('#btnSearch').trigger('click');  
		}
	});  
	
	$('#showContents').change(function(){
		showContentsListener();
	}); 
} 

/*
function locationDetail(seq){  

	var menuUrl = '/static/viewContents/reservation/reserveDtail.html';
	var menuNm = '예약관리 상세'
	var depthFullName = ' 예약 > 예약관리 > 상세';
	
	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "info", });
	if(true){
		return;
	}
	
	
	$('#detailWrapper').load(menuUrl, function(){     
		showDetail();  
		$('#detailTitle').text(menuNm);
		initDetailInfo(seq);
	});
	 
	
}
*/


function setPageTotalText(sum){
	var sumText = '총 ['+sum+'] 건이 검색되었습니다.';
	$('.table_total_text').text(sumText);
}

/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){
	
	$('#detailForm')[0].reset();     
	var urIdx = seq;
	// call api module and draw plz ^^;
}


function fn_search(){   

	GLOBAL_SELECT_COUNT = nullCheck($('#showContents').val()) == '' ? '0' : $('#showContents').val();
	GLOBAL_SEARCH_KEYWORD = nullCheck($('#searchInput').val()) == '' ? '' : $('#searchInput').val();  
	GLOBAL_SEARCH_INPUT_GBN = $('#searchSelectBox').val();
	
	if(nullCheck(GLOBAL_SEARCH_INPUT_GBN) == '' ){
		loadApi( drawTable , null );
	}else{
		if( GLOBAL_SEARCH_KEYWORD.length <= 0 ){
			errorAlert('검색조건을 선택해 주세요.');
			return;
		}else{
			loadApi( drawTable , null );
		}
	}
	  
}

function showContentsListener(){
	GLOBAL_SELECT_COUNT = nullCheck($('#showContents').val()) == '' ? '0' : $('#showContents').val();
	GLOBAL_SEARCH_KEYWORD = nullCheck($('#searchInput').val()) == '' ? '' : $('#searchInput').val();  
	GLOBAL_SEARCH_INPUT_GBN = $('#searchSelectBox').val();
	loadApi( drawTable , null );
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

/* =========================== periodicPayment function start ======================================*/


function periodicPaymentValidation(seq, obj){
	
	let swal_title = "정기결제";
	let swal_text = "정기결제를 하시겠습니까?";
	let swal_icon = "info";
	
	//console.log($(obj).closest('tr'));
	
	//console.log($(obj).closest('tr').children(":eq(11)").text());
	
	//다음결제일
	let paymenScheduletDate = $(obj).closest('tr').children(":eq(11)").text();
	
	//총결제액
	let sumPaymentAmount = $(obj).closest('tr').children(":eq(9)").text();
	
	//총결제예정금액
	let paymentTotalAmount = $(obj).closest('tr').children(":eq(8)").text();
	
	sumPaymentAmount = nullCheck(sumPaymentAmount);
	paymentTotalAmount = nullCheck(paymentTotalAmount);
	
	let re1 = /원/gi;
	let re2 = /,/gi;
	
	sumPaymentAmount = sumPaymentAmount.replace(re1, "");
	paymentTotalAmount = paymentTotalAmount.replace(re1, "");
	
	sumPaymentAmount = sumPaymentAmount.replace(re2, "");
	paymentTotalAmount = paymentTotalAmount.replace(re2, "");
	
	sumPaymentAmount = parseInt(sumPaymentAmount, 10);
	paymentTotalAmount = parseInt(paymentTotalAmount, 10);
	
	//console.log(sumPaymentAmount);
	//console.log(paymentTotalAmount);
	
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
	
	swal({
		  title: swal_title,
		  text: swal_text,
		  icon: swal_icon,
		  buttons: true,
		  dangerMode: true,
		})
		.then(function(willSave) {
			if (willSave) { //ok
				periodicPayment(seq);
			} else {
				return;
				//swal(cancel_text);
			}
		});
	
	//periodicPayment(req);
}


function periodicPayment(seq){
	let target = 'periodicPaymentUpdate';
	let method = 'update';
	
	let req = {
    		'rmIdx' : seq
    }; 

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
				swal("정기결제처리가 완료 되었습니다", { icon: "success"});
				fn_search();
			}
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			console.log(res);
			if(res.data.result == 1){
				errorAlert('저장 실패', '해당 결제건 조회 중 오류가 발생했습니다. 재 조회 후 다시 시도 해 주십시오.');
			}else if(res.data.result == 2){
				errorAlert('저장 실패', '해당 결제건의 결제금액 확인 중 오류가 발생했습니다. 재 조회 후 다시 시도 해 주십시오.');
			}else if(res.data.result == 3){
				errorAlert('저장 실패', '해당 결제건의 결제금액이 0원입니다. 재 조회 후 다시 시도 해 주십시오.');
			}else if(res.data.result == 4){
				errorAlert('저장 실패', '결제저장 중 오류가 발생했습니다. 잠시 후 다시 시도 해 주십시오.');
			}else{
				errorAlert('저장 실패', '처리 중 오류가 발생했습니다. 관리자에게 문의 해 주세십시오.');
			}
		}
	});// end fn_callApi	
}

