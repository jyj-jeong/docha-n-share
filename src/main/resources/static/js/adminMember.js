/*
 * adminMember.js
 * 회원 > 관리자 > 관리자관리
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

var modalName = 'adminMemberDetail';
var modalTitle = '관리자 상세';
var modalWidth = 600;


function initializingPageData(){
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi (fnc, page, displayPageNum, division) {

	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
	
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

    var req = {
    		page: page,
    		displayPageNum: displayPageNum
    };
	 
	var target = 'adminUserInfoList';
	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 alert('조회중 에러가 발생했습니다. \r\n관리자에게 문의하세요.');
    	 }
     });//end FindUserInfoList

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

		{ "name": "adIdx", "title": "회원번호" ,
			"formatter" : function(value, options, rowData){
				var seq = rowData.adIdx;
				return '<a href="javascript:locationDetail(' + "'" +seq + "'" +');"  >'+value+'</a>';               
			}
		},
		{ "name": "adminName", "title": "이름" },
		{ "name": "adminid", "title": "아이디", "breakpoints": "xs"},
		{ "name": "userContact1", "title": "연락처", "breakpoints": "xs"},
		{ "name": "regDt", "title": "가입일시", "breakpoints": "xs sm" },
		{ "name": "role", "title": "권한", "breakpoints": "xs sm md" }
	];
	
	var dataRowKey = [];
	
	for(var i=0; i<columns.length; i++){
		dataRowKey.push(columns[i].name);
	}
					
	for(var i=0; i<data.length; i++){		
		
		var aJson = new Object();
		
		for(var j=0; j<dataRowKey.length;j++){
			
			aJson[dataRowKey[j]] = data[i][dataRowKey[j]];
			
		}
		
		rows.push(aJson);
	} 
	
	$('.table').empty();
	
	$('.table').footable({

		 'calculateWidthOverride': function() {  
			    return { width: $(window).width() };
			  },
        'on': {
            'postinit.ft.table': function(e, ft) {

            }
        },
		"columns": columns,
		"rows": rows
	});
	
	var totalCnt = res.paging.totalCount;
    var perPageNum = res.paging.cri.perPageNum;
    var displayPageNum = res.paging.cri.displayPageNum;

    //page는 전역변수 사용
    var prev = res.paging.prev; 
    var next = res.paging.next;
	
    makePaging(totalCnt, perPageNum, displayPageNum, page, prev, next, $("#page"));

    
    $("#totalRowCount").text('[' + data[0].totalRowCount + ']');
}

function initSelectBox(){
	
	var searchOption = '';
	var countOption = '';
	
	searchOption += '<option>검색1</option>';
	searchOption += '<option>검색2</option>';
	searchOption += '<option>검색3</option>';
	
	countOption += '<option>전체</option>';
	countOption += '<option>10개씩 보기</option>';
	countOption += '<option>20개씩 보기</option>';
	countOption += '<option>30개씩 보기</option>';
	countOption += '<option>60개씩 보기</option>';
	
	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);
}

function bindEvent(){  
	
	$("#page").on('click', 'a', function(){
		if($(this).attr('class') != 'active'){
			var clickPage = $(this).text();
			var displayPageNum = $("#showContents").val();
			loadApi(drawTable, clickPage, displayPageNum);
		}
	});
	
	// add event  
	$('#listTable tbody').bind('click','tr',function() {	
		var seq = $(this).find('td').eq(0).text();
		locationDetail(seq);
	});
	
} 

function locationDetail(seq){ 
	
	//initDetailInfo(seq);
	
	var menuUrl = '/static/viewContents/member/admin/adminMemberDtail.html';
	var menuNm = '관리자관리 상세'
	var depthFullName = ' 회원 > 관리자 > 관리자관리 > 상세';

	//$('.app-main__inner').empty();
	
	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
	if(true){
		return;  
	}
	
	/*
	$('#detailWrapper').load(menuUrl, function(){     
		showDetail();  
		$('#detailTitle').text(menuNm);
		initDetailInfo(seq);
	});
	*/ 
	
}





/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){
	
	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
	if(true){
		return;
	}
	 
	//$('#detailForm')[0].reset();     
	var adIdx = seq;
	
	
	
	// 면허정보
	// START============================================================
	let req = {
		adIdx : adIdx,
	};
	
	var target = 'adminUserInfoDetail';
	var method = 'select';

	fn_callApi( method, target, req, function(response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code == 200) {

			let data = res.data.result;

			let strOption = "";

			let adminId = data.adminId;
			let adminName = data.adminName;
			let adminPassword = data.adminPassword;
			let userContact1 = phoneFomatter(data.userContact1);
			let userBirthday = data.userBirthday;
			let userRole  = data.userRole;

			let regDt = dateFormatter(data.regDt , "-");	
			
			$('#adminId').val(adminId);
			$("#adminName").val(adminName);
			$("#adminPassword").val(adminPassword);
			$("#userContact1").val(userContact1);
			$("#userBirthday").val(userBirthday);
			$("#regDt").val(regDt);
			
			// 권한셋팅
			// START============================================================

			if (userRole != null) {
				
				//DC_MENU_TEMPLATE에서 권한조회
				//DC_ADMIN_INFO == TEMPLATE_CD
				// → ROLE
				let target = 'adminMenuTemplate';
				let method = 'select';
				
				//let adminRole = '';


				fn_callApi(method, target, req, function(response) {
					let res = response;

					// 200이라면 페이징을 구한다.
					if (res.code == 200) {

						let data = res.data.result;
						
						strOption += "<option value = '0'>선택</option>";

						for ( var i in data) {
							if (data[i].templateCd) {
								strOption += "<option value = '" + data[i].templateCd + "'>" + data[i].templateNm + "</option>";
							}
						}

						
						$('#sel_userRole').empty();
						$('#sel_userRole').append(strOption);

						
						$("#sel_userRole").val(userRole).prop("selected", true);
		
						
						
							
					} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
						alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');

					}
				});// end fn_callApi
				
				
			
				/*req = {
					rtCode : "RL",
					pCode : "UR"
				};
				
				target = 'commonCodeInfo';
				method = 'select';


				fn_callApi(method, target, req, function(response) {
					let res = response;

					// 200이라면 페이징을 구한다.
					if (res.code == 200) {

						let data = res.data.result;

						let strOption = "";

						
	


					} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
						alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');

					}
				});// end fn_callApi
				 */
				
				$("#adminMemberDetail").iziModal('open');
			}
			// 권한셋팅
			// END============================================================

			
		} else { // 200이 아닐때 empty처리 error처리 등을
					// 기록한다.
			alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		}
	});// end fn_callApi
	// 면허정보
	// END============================================================
	
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

function createDetailInfo() {
     
	var menuUrl = '/static/viewContents/member/admin/adminMemberDtail.html';
	var menuNm = '관리자관리 상세'
	var depthFullName = ' 회원 > 관리자 > 관리자관리 > 상세';
	//$('.app-main__inner').empty();
	$('#detailWrapper').load(menuUrl, function(){     

	});
}


$("#" + modalName).iziModal({
	 radius: 5,
	 padding: 20,
	 closeButton: true,
	 overlayClose: false,
	 width: modalWidth,
	 title: modalTitle,
	 headerColor: '#002e5b',
     backdrop: 'static',
     keyboard: false
  
});



/* =========================== detail function end ======================================*/
