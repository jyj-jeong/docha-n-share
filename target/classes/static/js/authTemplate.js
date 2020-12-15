/*
 * adminMember.js
 * 회원 > 관리자 > 관리자관리
 *
 * */

var MODAL_NAME = "authTemplateDetail";
var MDOAL_TITLE = '권한템플릿';
var MODAL_WIDTH = 700;

function initializingPageData() {
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi(fnc, page, displayPageNum, division) {

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
		// if(res.code == 200) {
		fnc(res.data, page, displayPageNum, division);
		// }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	alert('조회중 에러가 발생했습니다. \r\n관리자에게 문의하세요.');
		// }
	});//end FindUserInfoList

}

var drawTable = function drawTable(res, page, displayPageNum) {
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

	var data = res.result;

	var rows = [];
	var columns;

	columns = [

		{
			"name": "adIdx", "title": "회원번호",
			"formatter": function (value, options, rowData) {
				var seq = String(rowData.adIdx);
				//return '<a href="javascript:initDetailInfo(' +seq + ');"  class="trigger" data-iziModal-open="#authTemplateDetail" >'+value+'</a>';
				return '<a href="javascript:initDetailInfo(' + "'" + seq + "'" + ');" >' + value + '</a>';
			}
		},
		{"name": "adminName", "title": "이름"},
		{"name": "adminid", "title": "아이디", "breakpoints": "xs"},
		{"name": "userContact1", "title": "연락처", "breakpoints": "xs"},
		{"name": "regDt", "title": "가입일시", "breakpoints": "xs sm"},
		{"name": "userRole", "title": "권한", "breakpoints": "xs sm md"}
	];

	var dataRowKey = [];

	for (var i = 0; i < columns.length; i++) {
		dataRowKey.push(columns[i].name);
	}

	for (var i = 0; i < data.length; i++) {

		var aJson = new Object();

		for (var j = 0; j < dataRowKey.length; j++) {

			aJson[dataRowKey[j]] = data[i][dataRowKey[j]];

		}

		rows.push(aJson);
	}

	$('.table').empty();

	$('.table').footable({

		'calculateWidthOverride': function () {
			return {width: $(window).width()};
		},
		'on': {
			'postinit.ft.table': function (e, ft) {

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


	if (!isEmpty(totalCnt)) {
		$('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
	} else {
		$("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
	}
}

function initSelectBox() {

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

function bindEvent() {

	$("#page").on('click', 'a', function () {
		if ($(this).attr('class') != 'active') {
			var clickPage = $(this).text();
			var displayPageNum = $("#showContents").val();
			loadApi(drawTable, clickPage, displayPageNum);
		}
	});

	// add event
	$('#listTable tbody').bind('click', 'tr', function () {
		var seq = $(this).find('td').eq(0).text();
		locationDetail(seq);
	});

}

/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq) {

	// swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });

	var adIdx = seq;
	let strOption = "";

	// 메뉴셋팅
	// START============================================================

	//DC_MENU_TEMPLATE에서 권한조회
	//DC_ADMIN_INFO == TEMPLATE_CD
	// → ROLE
	let target = 'menuInfoList';
	let method = 'select';

	let adminRole = '';

	let req = {};


	fn_callApi(method, target, req, function (response) {
		let data = response;

		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {
		strOption = "";

		for (var i = 0; i < data.length; i++) {
			let menuNm = '';
			menuNm = data[i].menuNm;

			strOption += " <div class=\"row\">\n" +
				"<div class=\"col-6 mb-3\">\n" +
				"<label class=\"form-label\">" + menuNm + "</label>\n" +
				"</div>\n" +
				"<div class=\"col-6 mb-3\">\n" +
				"<div class=\"form-check-inline\">\n" +
				"<label class=\"form-check\">\n" +
				"<input class=\"form-check-input va-field-value\" type=\"checkbox\" value=" + menuNm + " name=" + menuNm + 'read' + ">\n" +
				"<span class=\"form-check-label\">보기</span>\n" +
				"</label>\n" +
				"<label class=\"form-check\">\n" +
				"<input class=\"form-check-input va-field-value\" type=\"checkbox\" value=" + menuNm + " name=" + menuNm + 'write' + ">\n" +
				"<span class=\"form-check-label\">수정</span>\n" +
				"</label>\n" +
				"</div>\n" +
				"</div>\n" +
				"</div>";
		}

		$('#menuTemplate').empty();
		$('#menuTemplate').append(strOption);
		$('#menuTemplate').attr('disabled', false);
		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		//
		// }
	});// end fn_callApi

	// 권한셋팅
	// END============================================================

	// 권한셋팅
	// START============================================================

	//DC_MENU_TEMPLATE에서 권한조회
	//DC_ADMIN_INFO == TEMPLATE_CD
	// → ROLE
	target = 'adminMenuTemplate';
	method = 'select';

	adminRole = '';

	req = {};


	fn_callApi(method, target, req, function (response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {

		let data = res.result;

		strOption += "<option value = '0'>선택</option>";

		for (var i in data) {
			if (data[i].templateCd) {
				strOption += "<option value = '" + data[i].templateCd + "'>" + data[i].templateNm + "</option>";
			}
		}

		$('#sel_userRole').append(strOption);

		// if(userRole != null) {
		// 	$("#sel_userRole").val(userRole).prop("selected", true);
		// } else {
		// 	$("#sel_userRole").val(0).prop("selected", true);
		// }

		//
		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	alert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		//
		// }
	});// end fn_callApi

	// 권한셋팅
	// END============================================================

}

// validation
function detailValidation(save_type) {

	let title, text, icon, cancel_text;
	let req = {};

	let target = '';
	let method = '';

	// TODO crud 수정
	CRUD = 'insert';

	if (!isEmpty(save_type)) {
		switch (save_type) {
			case '':

				break;
			case 'saveAuthTemplate':
				if (CRUD === 'insert') {

					req = {};

					target = 'menuInfoList';
					method = 'select';

					let adminRole = '';

					req = {};

					fn_callApi(method, target, req, function (response) {
						let data = response;

						for (var i = 0; i < data.length; i++) {
							let menuNm = '';
							menuNm = data[i].menuNm;

							var readCheck = [];
							var writeCheck = [];

							var readNm = menuNm + 'read';
							var writeNm = menuNm + 'write';

							readCheck += $("input[name='" + readNm + "']:checked").val();
							writeCheck += $("input[name='" + writeNm + "']:checked").val();

							let templateNm = $('#templateNm').val();
							let templateMemo = $('#templateMemo').val();

							if (isEmpty(templateNm)) { // is not empty
								errorAlert('템플릿명', '템플릿명은 필수 입력값 입니다.\n');
								$('#templateNm').focus();
								return;
							}

							req = {
								templateNm: templateNm,
								templateMemo: templateMemo

							};

							title = '권한 템플릿 저장';
							text = '저장하시겠습니까?';
							icon = 'info';
							cancel_text = '취소하셨습니다.';

							call_before_save(title, text, icon, cancel_text, save_type, req);
						}
					});

				} else if (CRUD === 'modify') {
					req = {};
				}
				break;
			case 'deleteAuthTemplate':

				var templateCdList = [];

				$("input[type=checkbox]:checked").each(function () {
					var templateCd = $(this).parent().parent().parent().parent().data("value");
					templateCdList.push(templateCd);
				});

				req = {
					templateCdList: templateCdList
				};

				title = '권한 템플릿 삭제';
				text = '삭제하시겠습니까?';
				icon = 'info';
				cancel_text = '취소하셨습니다.';

				call_before_save(title, text, icon, cancel_text, save_type, req);

				break;
			default:
				break;
		}
	}
}

// submit
function detailSubmit(save_type, req) {
	let target = '';
	let method = '';

	switch (save_type) {
		case 'saveAuthTemplate':
			target = 'insertAdminMenuTemplate';
			method = 'insert';
			break;
		case 'deleteAuthTemplate':
			target = 'deleteAuthTemplate';
			method = 'delete';
			break;
		default:
			break;
	}

	fn_callApi(method, target, req, function(response) {
		let res = response;

		if (method === 'delete'){
			swal("삭제 성공", {icon : "success"});
		}else {
			swal("저장 성공", {icon : "success"});
		}

		switch (save_type) {
			case 'saveAuthTemplate':
				break;
			case 'deleteAuthTemplate':
				break;
			default:
				break;
		}

	});
}


/* =========================== detail function end ======================================*/
