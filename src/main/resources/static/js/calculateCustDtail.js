/*
 * calculateCustDetail.js
 * 정산 > 회원정산
 *
 * */

function initializingDetail2PageData(){
	loadApi(drawTable, null, null);
	bindEvent(); 
}

function loadApi (fnc, page, displayPageNum, division) {

	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);
	
	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;
  
    var req = {
    		page: page,
    		displayPageNum: displayPageNum,  
    		gbnDt : GLOABL_DETAIL_GBN_DT
    };  
    
	var target = 'calculateCustInfoList';         

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
		{ "name": "accountNumber", "id" : "rowNum" ,"title": "No" , "visible": false },
		{ "name": "rmIdx", "title": "예약번호"	}, 
		{ "name": "accountBank", "title": "대여일시<br/>반납일시" , "breakpoints": "xs sm",  
			"formatter" : function(value, options, rowData){ 
				var displayText = '';
				var rowIndex = (Number(rowData.rowNumber)-1);   
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
		{ "name": "modelName", "title": "모델<br/>차량번호", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){
				var displayText = '';
				var rowIndex = (Number(rowData.rowNumber)-1);   
				var targetRowData = options.rows[rowIndex];  
				
				var displayText = '';
				displayText += value;
				displayText += '<br/>';
				displayText += targetRowData.carNumber; 
				return displayText;  
			}
		},  
		{ "name": "paymentAmount", "title": "결제금액", "breakpoints": "xs sm", 
			"formatter" : function(value, options, rowData){  
				var displayText = Number(value) - Number(rowData.disCountFee);
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
		{ "name": "totalFee", "title": "총 금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = Number(rowData.totalFee) + Number(rowData.disCountFee);
				displayText = objectConvertToPriceFormat(value);  
				return displayText;  
			}
		},
		{ "name": "dochaDisFee", "title": "두차 수수료", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				return objectConvertToPriceFormat(value);    
			}
		},
		{ "name": "accountExpAmt", "title": "정산예정금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				return objectConvertToPriceFormat(value);  
			}
		},
		{ "name": "successFee", "title": "정산완료금액", "breakpoints": "xs sm",
			"formatter" : function(value, options, rowData){  
				var displayText = '';
				displayText = objectConvertToPriceFormat(value);
				return displayText;  
			}
		},
		{ "name": "", "title": "미정산금액", "breakpoints": "xs sm",  
			"formatter" : function(value, options, rowData){  
				var displayText = objectConvertToPriceFormat(Number(rowData.accountExpAmt) - Number(rowData.successFee)); 
				var display = '<button class="col-md-12 mb-2 mr-2 btn btn-primary">'+displayText+'</button>';      
				return display; 
			}
		}  
	];
	
	
    
	$('#amountTable2').empty();
	$('#amountTable2').footable({
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
	
    //makePaging(totalCnt, perPageNum, displayPageNum, page, prev, next, $("#detailPage"));

}
  

