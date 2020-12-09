/*
 * logs.js
 * 로그 > 로그
 *
 * */
  
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
	 
	var target = 'userInfoList';
	var method = 'select';
    
    fn_callApi(method, target, req, function (response) {
    	 var res = response;
    	 
    	 //200이라면 페이징을 구한다.
    	 if(res.code == 200) {
    		 fnc(res.data, page, displayPageNum, division);
    	 }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
    		 alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
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

		{ "name": "urIdx", "title": "회원번호" },
		{ "name": "userId", "title": "아이디" ,
			"formatter" : function(value, options, rowData){
				var seq = rowData.urIdx;
				return '<a href="javascript:locationDetail(' + "'" +seq + "'" +');"  >'+value+'</a>';               
			}
		},
		{ "name": "userName", "title": "이름", "breakpoints": "xs"},
		{ "name": "userBirthday", "title": "생년월일", "breakpoints": "xs",
			"formatter" : function(value, options, rowData){
				var setText = dateFormatter(value,'.');   
				return setText;
			}  
		},
		{ "name": "userContact1", "title": "연락처", "breakpoints": "xs sm" },
		{ "name": "userRole", "title": "등급", "breakpoints": "xs sm md" },
		{ "name": "rentCount", "title": "대여횟수", "breakpoints": "xs sm md" },
		{ "name": "regDt", "title": "가입일시", "breakpoints": "xs sm md" ,
			"formatter" : function(value, options, rowData){ 
				return regDtFormatter(value);  
			}
		}
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
	
	var menuUrl = '/static/viewContents/logs/logsDtail.html';
	var menuNm = '로그 상세'
	var depthFullName = ' 로그 > 로그 > 상세';
	//$('.app-main__inner').empty();
	

	$('#detailWrapper').load(menuUrl, function(){     
		showDetail();  
		$('#detailTitle').text(menuNm);
		initDetailInfo(seq);
	});
	
}





/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){
	
	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
	if(true){
		return;
	}
	
	$('#detailForm')[0].reset();     
	var urIdx = seq;
	// call api module and draw plz ^^;
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