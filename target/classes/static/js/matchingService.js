/*
 * matchingService.js

 * 예약 > 매칭서비스
 * 
 * 
 * */
  
// 검색 SELECT BOX LIST
var SEARCH_FORM_RELOAD_FLAG = true;
var GLOBAL_SEARCH_INPUT_GBN = '';
var GLOBAL_SELECT_CARTYPE_GBN = '';
var GLOBAL_SELECT_COUNT = '10';
var GLOBAL_SEARCH_KEYWORD = '';
var CURRENT_PAGE = 0;

function initializingPageData(){
	loadApi(drawTable, null, null );
	initSelectBox();
	bindEvent();
}

function initSelectBox(){
	
	let target = 'commonCodeInfo';
	let method = 'select';
	let req = {
			rtCode : "CR",
			pCode  : "CTY"
		};
	// 상태코드
	fn_callApi(method, target, req, function(response) {
		let res = response;  
		// 200이라면 페이징을 구한다.
		if (res.code == 200) { 
			let data = res.data.result;  
			let strOption = '<option value="" >선택</option>';   
			for ( var i=0; i<data.length; i++ ) { 
				strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
			}
			$('#gbnCarType').empty();
			$('#gbnCarType').append(strOption);
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		}
	});// end fn_callApi
	
	
	let searchOption = '';
	searchOption += '<option value="" >선택</option>';
	searchOption += '<option value="SEQ" >경매번호</option>';
	searchOption += '<option value="NM" >회원명</option>';
	searchOption += '<option value="CT" >연락처</option>';
	
	let countOption = '';
	countOption += '<option selected value="10" >10개씩 보기</option>';
	countOption += '<option value="20" >20개씩 보기</option>';
	countOption += '<option value="30" >30개씩 보기</option>';
	countOption += '<option value="60" >60개씩 보기</option>';
	
	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);

} 

//검색조건, 검색어 조회
function fn_search(){ 
	
	let searchKeyWord = $("#searchKeyWord").val();
	let searchSelectBox = $("#searchSelectBox option:selected").val();
	
	if(!isEmpty(searchSelectBox)){ 
		if (isEmpty(searchKeyWord)) {
			errorAlert('검색어', '검색어를 입력하세요.');
		} else {
			loadApi(drawTable, null, null);
		}
	}else{
		loadApi(drawTable, null, null);
	}
}

$("#searchKeyWord").keypress(function(e) {	
	let searchSelectBox = $("#searchSelectBox option:selected").val();
	if (!isEmpty(searchSelectBox)) {
		if (e.keyCode == 13) {
			fn_search();
		}
	} 
});

function loadApi(fnc, page, displayPageNum, division){

	let searchSelectBox = $("#searchSelectBox option:selected").val();
	let strSearchKeyWord = $("#searchKeyWord").val();	
	let showContents = $("#showContents option:selected").val();
	showContents = isEmpty(showContents) ? 10 : showContents;
	
	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? showContents: (typeof displayPageNum === 'number') ? displayPageNum : showContents;

	let gbnCarType = $("#gbnCarType option:selected").val(); 

	let req = {
    		'page': CURRENT_PAGE,
    		'displayPageNum': displayPageNum,  
    		'searchKeyWord' : strSearchKeyWord,  
    		'gbnCarType' : gbnCarType,
    		'gbnInput' : searchSelectBox
    };  

	let target = 'matchingServiceInfoList'; 
	let method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	let res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum); 
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
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
  
		{ "name": "rowNumber", "id" : "rowNum" ,"title": "No" , "visible": false ,   
			"formatter" : function(value, options, rowData){
				return 0;  
			}  
		},
		{ "name": "quIdx", "title": "경매번호" ,
			"formatter" : function(value, options, rowData){
				let displayText = nullCheck(value);
				return '<a href="javascript:locationDetail(' + "'" +displayText + "'" +');"  >'+displayText+'</a>';
			}
		},
		{ "name": "regDt", "title": "신청일시<br/>마감일시", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				let displayText = '';
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];       
				let targetEndDt = nullCheck(targetRowData.companyRegDt);  
				displayText += regDtFormatter2(targetRowData.regDt) + '~';    
				displayText += '<br/>';  
				displayText += regDtFormatter2(targetEndDt);    
				
				return displayText;  
			}
		},
		{ "name": "userName", "title": "회원</br>연락처",
			"formatter" : function(value, options, rowData){	
				let displayText = '';
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];
				let userName = nullCheck(value); 
				let contact = phoneFomatter(nullCheck(targetRowData.userContact1)); 
				displayText = userName + '<br/>' + contact;
				return displayText;
			}
		},
		{ "name": "rentStartDay", "title": "대여예정일시<br/>반납예정일시", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){ 
				let displayText = '';
				let rowIndex = String((rowData.rowNumber)-1);
				rowIndex = rowIndex.charAt(rowIndex.length-1);
				let targetRowData = options.rows[rowIndex];     
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

		{ "name": "periodDt", "title": "사용기간", "breakpoints": "xs sm md" },  
//			"formatter" : function(value, options, rowData){   
//				var displayText = '';  
//				var rowIndex = String((rowData.rowNumber)-1);
//				rowIndex = rowIndex.charAt(rowIndex.length-1);
//				var targetRowData = options.rows[rowIndex];     
//				var stDate = targetRowData.rentStartDay+targetRowData.rentStartTime;
//				var edDate = targetRowData.rentEndDay+targetRowData.rentEndTime;
//				var getDateDiff = setDateTimeDiff(stDate,edDate); 
//				var mm = getDateDiff[0] == 0 ? '' : getDateDiff[0] + '개월';
//				var dd = getDateDiff[1] == 0 ? '' : getDateDiff[1] + '일';
//				var hour = getDateDiff[2] == 0 ? '' : getDateDiff[2] + '시간';      
//				var min = getDateDiff[3] ==  0 ? '' : getDateDiff[3] + '분'; 
//				  
//				displayText += mm+dd;         
//				  
//				return displayText; 
//			}
//		},   
		{ "name": "carTypeListValue", "title": "신청등급", "breakpoints": "xs" ,
			"formatter" : function(value, options, rowData){ 
				return value;
			}
		}, 
		{ "name": "companyCount", "title": "수신회원사", "breakpoints": "xs" },
		{ "name": "qrCount", "title": "견적", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){ 
				return value + '건';       
			}
		},
		{ "name": "payAvg", "title": "견적평균가", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				let displayText = nullCheck(value);
				return objectConvertToPriceFormat(displayText);      
			}
		}  
	];


	$('#matchingTable').empty();
	$('#matchingTable').footable({
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

	// page는 전역변수 사용
	let prev = res.paging.prev;
	let next = res.paging.next;

	makePaging(totalCnt, perPageNum, showDisplayPageNum, page, prev, next,$("#page"));

	if (!isEmpty(totalCnt)) {
		$('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
	} else {
		$("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
	}

}

/*
 * 페이지 이동
 */
function bindEvent() {

	$("#page").on('click', 'a', function() {
		if ($(this).attr('class') != 'active') {
			let clickPage = $(this).text();
			
			let displayPageNum = $("#showContents").val();
			
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

//LIST END ============================================================================================================================================================================

function locationDetail(seq){ 

	CRUD_METHOD = 'update';
	
	let _quIdx = seq;

	let req = { 
			quIdx : _quIdx 
	};
	
	let target = 'selectMatchingServiceInfo';
	let method = 'select';
	
	// Detail정보 조회
	fn_callApi(method,target, req, function(res){  
		if( res.code == 200 ){
			let data = res.data.result[0];

			/*
			 * 회원사 select box
			 * 연료  select box
			 * 성별  select box
			 * 면허종류  select box
			 * 차량별차종 select box
			 */
			initDetailSelectBox(data);
			
			let quoteUserId = nullCheck(data.quoteUserId);
			let quoteUserName = nullCheck(data.quoteUserName);
			let quoteUserContact = nullCheck(data.quoteUserContact) == ''?'':phoneFomatter(data.quoteUserContact);
			let quoteUserGender = nullCheck(data.quoteUserGender);
			let quoteuserBirthday = nullCheck(data.quoteuserBirthday) == ''?'':dateFormatter(data.quoteuserBirthday);
			let quoteStartDate = nullCheck(data.quoteStartDate);
			let quoteEndDate = nullCheck(data.quoteEndDate);
			let rentStartDate = nullCheck(data.rentStartDate);
			let rentEndDate = nullCheck(data.rentEndDate);
			let periodDt = nullCheck(data.periodDt);
			let quIdx = nullCheck(data.quIdx);
			let deliveryAddr = nullCheck(data.deliveryAddr);
			let returnAddr = nullCheck(data.returnAddr);
			let carTypeListValue = nullCheck(data.carTypeListValue);
			let longtermYn  = nullCheck(data.longtermYn);

			$("#quoteUserId").val(quoteUserId);
			$("#quoteUserName").val(quoteUserName);
			$("#quoteUserContact").val(quoteUserContact);
			$("#sel_quoteUserGender").val(quoteUserGender).prop("selected", true);
			$("#quoteuserBirthday").val(quoteuserBirthday);
			$("#quoteStartDate").val(quoteStartDate);
			$("#quoteEndDate").val(quoteEndDate);
			$("#rentStartDate").val(rentStartDate);
			$("#rentEndDate").val(rentEndDate);
			$("#periodDt").val(periodDt);
			$("#quIdx").val(quIdx);
			$("#deliveryAddr").val(deliveryAddr);
			$("#returnAddr").val(returnAddr);
			$("#carTypeListValue").val(carTypeListValue);
			$("#longtermYn").val(longtermYn);
			
			var now = new Date();
			var todayAtMidn = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			var specificDate = new Date(rentStartDate);
			if(todayAtMidn.getTime() > specificDate.getTime()){
				$("#quoteCompanySave").hide();
			}
			// 회원사 견적
			quoteCompanyList(_quIdx);
			// 팝업 오픈
			initDetailInfo();
		}
		
	});

}

function quoteCompanyList(quIdx){
	let req = { 
			quIdx : quIdx 
	};

	let target = 'selectMatchingCompanyServiceInfo';
	let method = 'select';
	
	// Detail정보 조회
	fn_callApi(method,target, req, function(res){  

		if( res.code == 200 ){
			$("#MatchingCompanyServiceInfo").empty();

			let data = res.data.result;
			let qrIdx = "";
			let quIdx = "";
			let companyName = "";
			let staffName = "";
			let staffContact1 = "";
			let carNumber = "";
			let fuelName = "";
			let carRegDt = "";
			let colorName = "";
			let mileage = "";
			let personalCover = "";
			let peropertyDamageCover = "";
			let onselfDamageCover = "";
			let insuranceFee = "";
			let carDamageCover = "";
			let rentFee = "";
			let deliveryStandardPay = "";
			let paymentAmount = "";
			let caroptionListValue = "";
			let regDt = "";
			let rentStartDate = "";
			let html = "";
			let tstyle = "";

			for(let i=0; i<data.length; i++){
				qrIdx = nullCheck(data[i].qrIdx);
				quIdx = nullCheck(data[i].quIdx);
				companyName = nullCheck(data[i].companyName);
				staffName = nullCheck(data[i].staffName);
				staffContact1 = nullCheck(data[i].staffContact1) == ''?'':phoneFomatter(data[i].staffContact1);
				carNumber = nullCheck(data[i].carNumber);
				fuelName = nullCheck(data[i].fuelName);
				carRegDt = nullCheck(data[i].carRegDt);
				colorName = nullCheck(data[i].colorName);
				mileage = nullCheck(data[i].mileage) == ''?'':objectConvertToPriceFormat(data[i].mileage);
				personalCover = nullCheck(data[i].personalCover) == ''?'':objectConvertToPriceFormat(data[i].personalCover);
				propertyDamageCover = nullCheck(data[i].propertyDamageCover) == ''?'':objectConvertToPriceFormat(data[i].propertyDamageCover);
				onselfDamageCover = nullCheck(data[i].onselfDamageCover) == ''?'':objectConvertToPriceFormat(data[i].onselfDamageCover);
				insuranceFee = nullCheck(data[i].insuranceFee) == ''?'':objectConvertToPriceFormat(data[i].insuranceFee);
				carDamageCover = nullCheck(data[i].carDamageCover) == ''?'':objectConvertToPriceFormat(data[i].carDamageCover);
				rentFee = nullCheck(data[i].rentFee) == ''?'':objectConvertToPriceFormat(data[i].rentFee);
				deliveryStandardPay = nullCheck(data[i].deliveryStandardPay) == ''?'':objectConvertToPriceFormat(data[i].deliveryStandardPay);
				paymentAmount = nullCheck(data[i].paymentAmount) == ''?'':objectConvertToPriceFormat(data[i].paymentAmount);
				caroptionListValue = nullCheck(data[i].caroptionListValue);
				regDt = nullCheck(data[i].regDt);
				rentStartDate = nullCheck(data[i].rentStartDate);

				var now = new Date();
				var todayAtMidn = new Date(now.getFullYear(), now.getMonth(), now.getDate());
				var specificDate = new Date(rentStartDate);
				
				tstyle = "";
				if(todayAtMidn.getTime() >= specificDate.getTime()){
					tstyle = 'style="background-color:#ccc"';
				}

				html = "";
				html += '<div class="main-card mb-3 card paymentDiv" '+tstyle+'>';
				html += '	<div class="card-body"><h5 class="card-title">입찰시간('+regDt+')</h5>';
				
				// 현재시간이 대여일시보다 작을때 취소 및 견적 버튼 보여주기
				if(todayAtMidn.getTime() < specificDate.getTime()){
					$("#quoteCompanySave").show();
					
					html += '<div class="col-lg-12 ">';
					html += '<div class="p-b-20 p-r-40 text-right">';
					html += '	<button class="btn btn-primary" type="button" onclick="quoteCompanyCancel(\''+qrIdx+'\');">취소</button>';
					html += '</div>';
					html += '</div>';
				}
				
				html += '		<div class="col-lg-3 float-l flex-1" >';
				html += '			<label>회원사(지점)</label>';
				html += '			<input type="text" class="form-control" readonly value='+companyName+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;"> ';  
				html += '			<label>관리자</label>';
				html += '			<input type="text" class="form-control" readonly value='+staffName+'>';
				html += '		</div>';				
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';  
				html += '			<label>연락처</label>';
				html += '			<input type="text" class="form-control" readonly value='+staffContact1+'>';
				html += '		</div>'; 					
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>모델(차량번호)</label>';
				html += '			<input type="text" class="form-control" readonly value='+carNumber+'>';
				html += '		</div>'; 					
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';   
				html += '			<label>연료</label>';
				html += '			<input type="text" class="form-control" readonly value='+fuelName+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>출고연식</label>';
				html += '			<input type="text" class="form-control" readonly value='+carRegDt+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>색상</label>';
				html += '			<input type="text" class="form-control" readonly value='+colorName+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>누적Km</label>';
				html += '			<input type="text" class="form-control" readonly value='+mileage+'>';
				html += '		</div>'; 					
				html += '		<div class="col-lg-12 float-l" style="flex:1;">';   
				html += '			<label>옵션</label>';
				html += '			<input type="text" class="form-control" readonly value='+caroptionListValue+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>대인</label>';
				html += '			<input type="text" class="form-control" readonly value='+personalCover+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';   
				html += '			<label>대물</label>';
				html += '			<input type="text" class="form-control" readonly value='+propertyDamageCover+'>';
				html += '		</div>'; 					
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';   
				html += '			<label>자손</label>';
				html += '			<input type="text" class="form-control" readonly value='+onselfDamageCover+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>왕복배달금액</label>';
				html += '			<input type="text" class="form-control" readonly value='+deliveryStandardPay+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>차량대여료</label>';
				html += '			<input type="text" class="form-control" readonly value='+rentFee+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>자차보험금</label>';
				html += '			<input type="text" class="form-control" readonly value='+insuranceFee+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>자차고객부담금</label>';
				html += '			<input type="text" class="form-control" readonly value='+carDamageCover+'>';
				html += '		</div>';
				html += '		<div class="col-lg-3 float-l" style="flex:1;">';
				html += '			<label>총대여금</label>';
				html += '			<input type="text" class="form-control" readonly value='+paymentAmount+'>';
				html += '		</div>';
				html += '	</div>';
				html += '</div>';
				
				$("#MatchingCompanyServiceInfo").append(html);
			}
			
		}
		
	});
	
}


function quoteCompanyNew(){
	let quidx = $("#quIdx").val();

	$("#matchingInfo").iziModal('close');  
	$("#matchingCompanyInfo").iziModal('open');

}

function closeRepatmentDetail(){  
	$("#matchingCompanyInfo").iziModal('close');  
	$("#matchingInfo").iziModal('open');  
	let quidx = $("#quIdx").val();
	quoteCompanyList(quidx);
}

function initDetailSelectBox(_data){ 
	let target = '';
	let method = '';
	let req = {};
	let res;
	let data;
	let strOption = '';
	
	// 회원사  select box
	target = 'selectCompanyList';
	method = 'select';
	req = {};

	// 회사리스트
	fn_callApi(method, target, req, function(response) {
		res = response;   
		// 200이라면 페이징을 구한다.
		if (res.code == 200) {
			data = res.data.result.result;
			strOption = "<option value=''>선택</option>";     
			for ( var i=0; i<data.length; i++ ) { 
				let companyName = '';
				if(!isEmpty(data[i].branchName)){
					companyName = data[i].companyName + "(" + nullCheck(data[i].branchName) + ")";
				}else{
					companyName = data[i].companyName;
				}

				strOption += "<option value = '" + data[i].rtIdx + "'>" + companyName + "</option>";  
			} 			
			$('#sel_matchingRtIdx').empty();
			$('#sel_matchingRtIdx').append(strOption);
			
			let _rtIdx = '';
			
			if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
				_rtIdx = GLOBAL_LOGIN_RT_IDX;
			}

			if(!isEmpty(_rtIdx)){
				$("#sel_matchingRtIdx").attr("disabled", true)
				$("#sel_matchingRtIdx").val(_rtIdx).prop("selected", true);
				selectCompany();
			}else{
				$("#sel_matchingRtIdx").attr("disabled", false)
				$("#sel_matchingRtIdx").val('').prop("selected", true);
			}

		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		}
	});// end fn_callApi
	
	// 연료  select box
	req = {
	    		rtCode:'CR',
	      		pCode: 'FL'
	};
		 
	target = 'commonCodeInfo';
	method = 'select';

	fn_callApi(method, target, req, function (response) {
		res = response;
	    	 
			if (res.code == 200) {

				data = res.data.result;
				
				strOption = "";
				strOption += "<option value = '0'>선택하세요</option>";
				
				for ( let i in data) {
					if (data[i].codeValue) {
						strOption += "<option value = '"+ data[i].code + "'>"+ data[i].codeValue + "</option>";
					}
				}
				
				$('#sel_matchingFuel').empty();
				$('#sel_matchingFuel').append(strOption);
			}
	});

	// 성별  select box
	req = {
	    		rtCode:'CCD',
	      		pCode: 'GD'
	};
		 
	target = 'commonCodeInfo';
	method = 'select';

	fn_callApi(method, target, req, function (response) {
		res = response;
	    	 
			if (res.code == 200) {

				data = res.data.result;
				
				strOption = "";
				strOption += "<option value = '0'>선택하세요</option>";
				
				for ( let i in data) {
					if (data[i].codeValue) {
						strOption += "<option value = '"+ data[i].code + "'>"+ data[i].codeValue + "</option>";
					}
				}

				$('#sel_quoteUserGender').empty();
				$('#sel_quoteUserGender').append(strOption);
				
				if(!isEmpty(_data)){
					let quoteUserGender = nullCheck(_data.quoteUserGender) == ''?'0':_data.quoteUserGender;
					$("#sel_quoteUserGender").val(quoteUserGender).prop("selected", true);
				}else{
					$("#sel_quoteUserGender").val('0').prop("selected", true);
				}
			}
	});

}
/* =========================== detail function start ======================================*/
/*
 * 회원사별 차량 리스트
 */
function selectCompany(){
  
	$('#matchingStaffContact').val('');
	$('#matchingStaffName').val(''); 
	$("#sel_matchingFuel").val('0').prop("selected", true);
	$('#matchingCarRegDt').val('');
	$('#matchingColorName').val('');
	$('#matchingMileage').val('');
	$('#matchingOnselfDamageCover').val('');
	$('#matchingPersonalCover').val('');
	$('#matchingPropertyDamageCover').val('');
	$('#matchingCaroptionListValue').val('');

	let rtIdx = $("#sel_matchingRtIdx option:selected").val();

	let target = 'selectCompanyInfoAndCarInfo';
	let method = 'select';
	let req = { 
			'rtIdx' : rtIdx 
	};

	fn_callApi(method, target, req, function(response) {
		let res = response;
		// 200이라면 페이징을 구한다. 
		if (res.code == 200) {
			let companyInfo = res.data.result.companyInfo; 
			if( companyInfo != undefined && companyInfo != null){
				let staffContact = nullCheck(companyInfo.staffContact1);
				let staffName = nullCheck(companyInfo.staffName);
				let companyAddress = nullCheck(companyInfo.companyAddress);
				let companyAddressDetail = nullCheck(companyInfo.companyAddressDetail);
				let companyAddr = companyAddress + ' ' + companyAddressDetail;

				$('#matchingStaffContact').val('');
				$('#matchingStaffName').val(''); 
				
				$('#matchingStaffContact').val(staffContact);
				$('#matchingStaffName').val(staffName); 
			}
			
			let carList = res.data.result.carList;     
			carListData = carList;

			let strOption = "<option value=''>선택</option>";    
			for ( var i=0; i<carList.length; i++ ) {
				strOption += "<option value = '" + carList[i].crIdx + "'>" + carList[i].modelName + '('+carList[i].carNumber+')' + "</option>";  
			}

			$('#sel_matchingCrIdx').empty();
			$('#sel_matchingCrIdx').append(strOption);
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		} 
	});// end fn_callApi

}

/*
 * 차량정보 가져오기
 */
function selectCarInfo(){

	$("#sel_matchingFuel").val('0').prop("selected", true);
	$('#matchingCarRegDt').val('');
	$('#matchingColorName').val('');
	$('#matchingMileage').val('');
	$('#matchingOnselfDamageCover').val('');
	$('#matchingPersonalCover').val('');
	$('#matchingPropertyDamageCover').val('');
	$('#matchingCaroptionListValue').val('');
	$('#dailyStandardPay').val('');
	$('#monthlyStandardPay').val('');
	$('#longTermFee').val('');

	let crIdxIndex = $("#sel_matchingCrIdx option:selected").index() - 1;

	let mdIdx = nullCheck(carListData[crIdxIndex].mdIdx);
	let fuelCode = nullCheck(carListData[crIdxIndex].fuelCode);
	let year = nullCheck(carListData[crIdxIndex].year);
	let colorName = nullCheck(carListData[crIdxIndex].colorName);
	let mileage = nullCheck(carListData[crIdxIndex].mileage) == ''?'0':objectConvertToPriceFormat(carListData[crIdxIndex].mileage);
	let onselfDamageCover = nullCheck(carListData[crIdxIndex].onselfDamageCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].onselfDamageCover);
	let personalCover = nullCheck(carListData[crIdxIndex].personalCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].personalCover);
	let propertyDamageCover = nullCheck(carListData[crIdxIndex].propertyDamageCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].propertyDamageCover);
	let carTypeCode = nullCheck(carListData[crIdxIndex].carTypeCode);
	let optionCodeValue  = nullCheck(carListData[crIdxIndex].optionCodeValue);

	let dailyStandardPay  = nullCheck(carListData[crIdxIndex].dailyStandardPay);
	let monthlyStandardPay  = nullCheck(carListData[crIdxIndex].monthlyStandardPay);
	let longTermFee  = nullCheck(carListData[crIdxIndex].longTermFee);
	
	$("#sel_matchingFuel").val(fuelCode).prop("selected", true);
	$('#matchingCarRegDt').val(year);
	$('#matchingColorName').val(colorName);
	$('#matchingMileage').val(mileage);
	$('#matchingOnselfDamageCover').val(onselfDamageCover);
	$('#matchingPersonalCover').val(personalCover);
	$('#matchingPropertyDamageCover').val(propertyDamageCover);
	$('#matchingCaroptionListValue').val(optionCodeValue);

	$('#dailyStandardPay').val(dailyStandardPay);
	$('#monthlyStandardPay').val(monthlyStandardPay);
	$('#longTermFee').val(longTermFee);
	
////	$('#carTypeCode').val(carTypeCode);
//
//	let carDamageCover = nullCheck(carListData[crIdxIndex].carDamageCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover);
//	let insuranceCopayment = nullCheck(carListData[crIdxIndex].insuranceCopayment) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment);
//	let carDamageCover2 = nullCheck(carListData[crIdxIndex].carDamageCover2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover2);
//	let insuranceCopayment2 = nullCheck(carListData[crIdxIndex].insuranceCopayment2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment2);
//	let carDamageCover3 = nullCheck(carListData[crIdxIndex].carDamageCover3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover3);
//	let insuranceCopayment3 = nullCheck(carListData[crIdxIndex].insuranceCopayment3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment3);
//	let carDamageCover4 = nullCheck(carListData[crIdxIndex].carDamageCover4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover4);
//	let insuranceCopayment4 = nullCheck(carListData[crIdxIndex].insuranceCopayment4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment4);
//
//	let strOption = "";
//	strOption += "<option value = ''>선택하세요</option>";
// 
//	if(!isEmpty(carDamageCover) && !isEmpty(insuranceCopayment)){
//		strOption += "<option value = '"+insuranceCopayment+"'>" + "고객부담금:" + carDamageCover + "/보험금:" + insuranceCopayment + "</option>";
//	}
//	if(!isEmpty(carDamageCover2) && !isEmpty(insuranceCopayment2)){
//		strOption += "<option value = '"+insuranceCopayment2+"'>" + "고객부담금:" + carDamageCover2 + "/보험금:" + insuranceCopayment2 + "</option>";
//	}
//	if(!isEmpty(carDamageCover3) && !isEmpty(insuranceCopayment3)){
//		strOption += "<option value = '"+insuranceCopayment3+"'>" + "고객부담금:" + carDamageCover3 + "/보험금:" + insuranceCopayment3 + "</option>";
//	}
//	if(!isEmpty(carDamageCover4) && !isEmpty(insuranceCopayment4)){
//		strOption += "<option value = '"+insuranceCopayment4+"'>" + "고객부담금:" + carDamageCover4 + "/보험금:" + insuranceCopayment4 + "</option>";
//	}
//
//	$('#sel_ciIdx').empty();
//	$('#sel_ciIdx').append(strOption);
//	
//	let revinsuranceCopayment = $("#insuranceCopayment").val();
//	let revcarDamageCover = $("#carDamageCover").val();
//	
//	if((isEmpty(revinsuranceCopayment) || revinsuranceCopayment == '0') && !isEmpty(revcarDamageCover) && revcarDamageCover > '0'){
//		if (carDamageCover == revcarDamageCover){
//			revinsuranceCopayment = insuranceCopayment;
//		}else if (carDamageCover2 == revcarDamageCover){
//			revinsuranceCopayment = insuranceCopayment2;
//		}else if (carDamageCover3 == revcarDamageCover){
//			revinsuranceCopayment = insuranceCopayment3;
//		}else if (carDamageCover4 == revcarDamageCover){
//			revinsuranceCopayment = insuranceCopayment4;
//		}
//	}
//
//	if(!isEmpty(revinsuranceCopayment) && revinsuranceCopayment != '0' && !isEmpty(crIdx) ){
//		$("#sel_ciIdx").val(revinsuranceCopayment).prop("selected", true);
//	}else{
//		$("#sel_ciIdx").val('').prop("selected", true);
//	}
	
}
var CRUD_METHOD = '';

$("#matchingInfo").iziModal({
	radius: 5,
	padding: 20,
	closeButton: true,
	overlayClose: false,
	width: 1000,    
	height :'auto',     
	overflow : 'scroll', 
	title: '매칭상세',
	headerColor: '#002e5b',
    backdrop: 'static',
    keyboard: false,
    subtitle : ' ',   
    group:'groupA',
    loop : true,
    navigateCaption: false,
    navigateArrows: false,
    onClosing: function(){
    	closeDetail();
    }
}); 

$("#matchingCompanyInfo").iziModal({
	radius: 5,
	padding: 20,
	closeButton: true,
	overlayClose: false,
	width: 1000,    
	height :'auto',     
	overflow : 'scroll', 
	title: '입찰',
	headerColor: '#002e5b',
    backdrop: 'static',
    keyboard: false,
    subtitle : ' ',   
    group:'groupB',
    loop : true,
    navigateCaption: false,
    navigateArrows: false,
    onClosing: function(){
    	closeDetail();
    }
}); 

//모달창 닫기  
function closeDetail(){
	CRUD_METHOD = '';
	$("#matchingInfo").iziModal('close');
	$("#matchingCompanyInfo").iziModal('close');
}
  
// init
function initDetailInfo(){
	$("#matchingInfo").iziModal('open');
}

function returnFeeSetting(){ 

	var repatmentFee = 0; 
	$('.returnSum').each(function(){ 
		repatmentFee += Number(getPureText(nullCheck($(this).val())));  
	});
	
	var setCancelWaitFee = repatmentFee; 
	
	$('#paymentAmount').val(objectConvertToPriceFormat(setCancelWaitFee));

}

function quoteCompanyCancel(qrIdx){

	let req = { 
			qrIdx : qrIdx
			,delYn : 'Y' 
			,modId : GLOBAL_LOGIN_USER_IDX
			,regId : GLOBAL_LOGIN_USER_IDX
	};

	let title = '입찰 취소';
	let text = '저장하시겠습니까?'
	let icon = 'info';
	let cancel_text = '취소하셨습니다.';
	let save_type = 'delete';

	call_before_save(title, text, icon, cancel_text, save_type, req);

}

function matchingSave(){
	
	let quIdx = nullCheck($("#quIdx").val());
	let crIdx = nullCheck($("#sel_matchingCrIdx option:selected").val());
	let rentFee = nullCheck($("#rentFee").val());
	let insuranceFee = nullCheck($("#insuranceFee").val());
	let paymentAmount = nullCheck($("#paymentAmount").val());
	let rtIdx = nullCheck($("#sel_matchingRtIdx option:selected").val());

	let dailyStandardPay = nullCheck($('#dailyStandardPay').val());
	let monthlyStandardPay = nullCheck($('#monthlyStandardPay').val());
	let longTermFee = nullCheck($('#longTermFee').val());
	
	let longtermYn = nullCheck($('#longtermYn').val());
	if(longtermYn == "ST"){
		longTermFee = "0";
	}
	
	if(isEmpty(rtIdx)){
		errorAlert('차량', '회원사는 필수 선택해 주세요.');
		return;
	}
	if(isEmpty(crIdx)){
		errorAlert('차량', '차량은 필수 선택해 주세요.');
		return;
	}
	if(isEmpty(rentFee)){
		errorAlert('결제', '대여금액을 필수 입력해 주세요.');
		return;
	}
	if(isEmpty(insuranceFee)){
		errorAlert('차량', '보험금을 필수 입력해 주세요.');
		return;
	}
	
//	alert(quIdx+'\n'+crIdx+'\n'+rentFee+'\n'+insuranceFee+'\n'+paymentAmount+'\n'+rtIdx+'\n'+dailyStandardPay+'\n'+monthlyStandardPay+'\n'+longTermFee);
	let req = {
			quIdx : quIdx	
			,crIdx : crIdx	
			,rentFee : rentFee	
			,insuranceFee : insuranceFee	
			,paymentAmount : paymentAmount	
			,rtIdx : rtIdx	
			,urIdx : GLOBAL_LOGIN_USER_IDX	
			,deliveryStandardPay : dailyStandardPay	
			,monthlyStandardPay : monthlyStandardPay	
			,longTermFee : longTermFee	
			,userName : GLOBAL_LOGIN_USER_NAME
			,modId : GLOBAL_LOGIN_USER_IDX
			,regId : GLOBAL_LOGIN_USER_IDX
	};
	
	let title = '입찰 저장';
	let text = '저장하시겠습니까?'
	let icon = 'info';
	let cancel_text = '취소하셨습니다.';
	let save_type = 'insert';

	call_before_save(title, text, icon, cancel_text, save_type, req);
}

// submit
function detailSubmit(save_type, req){
	
	let target = '';
	let method = '';

	if (isEmpty(save_type)) { // save_type
		errorAlert('API ERROR', 'Save Type이 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}

	if (isEmpty(req)) { // req array
		errorAlert('API ERROR', '전송가능한 파라메터가 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}
	
	if (save_type == 'insert') {
		target = 'insertMatchingCompanyServiceInfo';
		method = 'insert';
	} else if (save_type == 'delete') {
		target = 'updateMatchingCompanyServiceInfo';
		method = 'update';
	}

	fn_callApi(method, target, req, function(response) {
		let res = response;
		
		// 200이라면 페이징을 구한다.
		if (res.code == 200) {
			if (res.data.result == 1) {
				if(save_type == 'insert'){
					swal("저장 성공", {icon : "success"});
					closeDetail();
					loadApi(drawTable, null, null);
				}else if (save_type == 'delete'){
					swal("저장 성공", {icon : "success"});
					closeRepatmentDetail();
				}
			}else{
				errorAlert('저장 실패', '관리자에게 문의하세요.');
			}
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('저장 실패', '관리자에게 문의하세요.');
		}
	});// end fn_callApi
}



/* =========================== detail function end ======================================*/