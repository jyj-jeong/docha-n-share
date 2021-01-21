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

function saveSettlementAmount(){

	var rtIdx = $('#rtIdx').val();
	var rmIdx = $('#rmIdx').val().trim();
	var reserveDate = $('#reserveDate').val();

	var modalOnMissFee = $('#modalOnMissFee').val();

	var successFee = $('#modalSuccessFee').val();
	var settlementAmount = $('#settlementAmount').val();

	if (isEmpty(settlementAmount)){
		errorAlert('정산금액', '정산금액을 입력해주세요.');
		return;
	}

	if (modalOnMissFee < settlementAmount){
		errorAlert('정산금액', '미정산 금액보다 정산금액이 클 수 없습니다.');
		return;
	}

	let title = '정산금액 저장';
	let text = '기존 정산 완료금액 ( ' + successFee + '원 )에 정산금액 ' + settlementAmount + '원을 추가하시겠습니까?';
	let icon = 'info';
	let cancel_text = '취소하셨습니다.';

	let save_type;
	let req = {};

	if (!isEmpty(rtIdx)){

		save_type = 'saveSettlementAmountToRentCompany';

		req = {
			rtIdx : rtIdx,
			reserveDate : reserveDate,
			settlementAmount : getPureText(settlementAmount)
		};

	}else if (!isEmpty(rmIdx)){

		save_type = 'saveSettlementAmount';

		req = {
			rmIdx : rmIdx,
			settlementAmount : getPureText(settlementAmount)
		};

	}

	call_before_save(title, text, icon, cancel_text, save_type, req);
}

function detailSubmit(save_type, req){
	let target = '';
	let method = '';

	if (save_type === 'saveSettlementAmountToRentCompany'){
		target = 'saveRentCompanySettlementAmount';
		method = 'update';
	}else if (save_type === 'saveSettlementAmount'){
		target = 'saveSettlementAmount';
		method = 'update';
	}

	if(isEmpty(target)) { //is not empty
		errorAlert('API ERROR ' + save_type, 'api target이 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}

	if(isEmpty(method)) { //is not empty
		errorAlert('API ERROR '  + save_type, 'api method가 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}

	fn_callApi(method, target, req, function(response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code === 1) {

			switch (save_type) {
				case 'saveSettlementAmountToRentCompany':

					swal({
						title: "저장 성공",
						icon: "success",
						buttons: [
							'확인',
							'취소'
						],
					}).then(function(isConfirm) {
						location.reload();

					});

					break;
				case 'saveSettlementAmount':
					swal({
						title: "저장 성공",
						icon: "success",
						buttons: [
							'확인',
							'취소'
						],
					}).then(function(isConfirm) {
						$('#enterSettlementAmountModal').modal('hide');

						rentCompanyTable.context[0].ajax.data({"rtIdx" : $('#rtIdx').val(), "accountExpDt" : accountExpDt});
						rentCompanyTable.ajax.reload();
					});

					break;
			}

		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('저장 실패', '관리자에게 문의하세요.');
		}
	});
}
  

