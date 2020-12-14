/*
 * rentCompany.js
 * 회원 > 회원사
 *
 * */

var MODAL_NAME = 'rentCompanyDetail';
var MODAL_TITLE = '회원사 상세';
var MODAL_WIDTH = 1000;
var MODAL_HEIGHT = 1500;
var CRUD = '';		// 회원사 저장 구분자
var MCRUD = '';		// 회원사스템 저장 구분자
var RMCRUD = '';	// 회원사 특정기간 저장 구분자
var HCRUD = '';		// 휴무일 저장 구분자
var CURRENT_PAGE = 0;

var GLOBAL_LOGIN_USER_IDX;
var GLOBAL_LOGIN_USER_ROLE;

var today = new Date();

function initializingPageData() {
	loadApi(drawTable, null, null);
	initSelectBox();
	bindEvent();
}

function loadApi(fnc, page, displayPageNum, division) {
	let searchSelectBox = $("#searchSelectBox option:selected").val();
	let strSearchKeyWord = $("#searchKeyWord").val();
	let showContents = $("#showContents option:selected").val();
	showContents = isEmpty(showContents) ? 10 : showContents;

	CURRENT_PAGE = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? showContents : (typeof displayPageNum === 'number') ? displayPageNum : showContents;

	let _rtIdx = '';
	GLOBAL_LOGIN_USER_ROLE = 'RA';

	if (GLOBAL_LOGIN_USER_ROLE != 'RA') {
		_rtIdx = getLoginUser().rtIdx;
		$("#btnCalculate").hide();
	}

}

var drawTable = function drawTable(res, page, displayPageNum) {
	page = parseInt(page);
	displayPageNum = parseInt(displayPageNum);

	page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
	displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

	let data = res.result;
	let rows = [];
	let columns;

	columns = [{
		"name": "rtIdx", "title": "번호",
		"formatter": function (value, options, rowData) {
			let seq = rowData.rtIdx;

			return '<a href="javascript:initDetailInfo(' + "'" + seq
				+ "'" + ');"  >' + value + '</a>';
		}
	}, {
		"name": "companyName", "title": "회사명", "breakpoints": "xs sm",
		"formatter": function (value, options, rowData) {
			let setText = value;

			if (!isEmpty(value)) { // 값이 있으면
				setText = value;
			} else {
				setText = '미확인';
			}

			return setText;
		}
	}, {
		"name": "branchName", "title": "지점", "breakpoints": "xs",
		"formatter": function (value, options, rowData) {
			let setText = value;

			if (!isEmpty(value)) { // 값이 있으면
				setText = value;
			} else {
				setText = '미확인';
			}

			return setText;
		}
	}, {
		"name": "companyAddress", "title": "지역", "breakpoints": "xs",
		"formatter": function (value, options, rowData) {
			let setText = '';
			let addressDetail = value.split(' ');

			if (!isEmpty(value)) { // 값이 있으면
				setText = addressDetail[0];
			} else {
				setText = '미확인';
			}

			return setText;
		}
	}, {
		"name": "companyAddress", "title": "주소", "breakpoints": "xs sm",
		"formatter": function (value, options, rowData) {
			let displayText = value;

			return displayText;
		}
	}, {
		"name": "staffName", "title": "관리자", "breakpoints": "xs sm md",
		"formatter": function (value, options, rowData) {
			let setText = nullCheck(value);

			if (!isEmpty(setText)) { // 값이 있으면
				setText = value;
			} else {
				setText = '미확인';
			}

			return setText;
		}
	}, {
		"name": "staffContact1", "title": "연락처", "breakpoints": "xs sm md",
		"formatter": function (value, options, rowData) {
			let setText = '';

			if (!isEmpty(value)) { // 값이 있으면

				if (value.length > 4) {
					setText = phoneFomatter(value);
				} else {
					setText = '미확인';
				}
			} else {
				setText = '미확인';
			}

			return setText;
		}
	}, {
		"name": "carCount", "title": "보유", "breakpoints": "xs sm md"
	}, {"name": "regCarCount", "title": "등록", "breakpoints": "xs sm md"}
	];

	$('#listTable').empty();
	$('#listTable').footable({
		'calculateWidthOverride': function () {
			return {width: $(window).width()};
		},
		'on': {
			'postinit.ft.table': function (e, ft) {

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

	makePaging(totalCnt, perPageNum, showDisplayPageNum, page, prev, next, $("#page"));

	if (!isEmpty(totalCnt)) {
		$('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
	} else {
		$("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
	}
}

function initSelectBox() {

	let searchOption = '';
	let countOption = '';

	searchOption += '<option value="">선택</option>';
	searchOption += '<option value="rtIdx">회원사번호</option>';
	searchOption += '<option value="companyName">회사명</option>';
	searchOption += '<option value="branchName">지점</option>';
	searchOption += '<option value="staffName">관리자</option>';
	searchOption += '<option value="staffContact1">연락처</option>';

	countOption += '<option value="10" >10개씩 보기</option>';
	countOption += '<option value="20" >20개씩 보기</option>';
	countOption += '<option value="30" >30개씩 보기</option>';
	countOption += '<option value="60" >60개씩 보기</option>';

	$('#showContents').append(countOption);
	$('#searchSelectBox').append(searchOption);

	$("#searchSelectBox option:eq(0)").prop("selected", true);
	$("#showContents option:eq(0)").prop("selected", true);
}

function initModalSelectBox(DetailData) {

	// 신규시 input box 초기화
	if (DetailData == null) {
		initInput();
	}
	// 은행
	let req = {
		rtCode: "BK",
		pCode: "KB"
	};

	let target = 'commonCodeInfo';
	let method = 'select';

	fn_callApi(method, target, req, function (response) {
		let data = response;

		// 200이라면 페이징을 구한다.
		// if (res.code == 200) {

		// let data = res.data.result;

		let strOption = "";

		strOption += "<option value = '0'>선택</option>";

		for (let i in data) {
			if (data[i].codeValue) {
				strOption += "<option value = '" + data[i].code + "'>"
					+ data[i].codeValue + "</option>";
			}
		}

		$('#sel_bank').empty();
		$('#sel_bank').append(strOption);

		if (CRUD == 'modify') {
			if (isEmpty(DetailData.accountBank)) {
				$("#sel_bank").val(0).prop("selected", true);
			} else {
				$("#sel_bank").val(DetailData.accountBank).prop("selected", true);
			}
		} else if (CRUD == 'insert') {
			$("#sel_bank").val(0).prop("selected", true);
		}

		// } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
		// 	errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		// }
	});// end fn_callApi

	let strOption = "<option value = '0'>선택</option>";

	// 반납정비시간
	// 48시간 단위로 설정한다.
	for (let i = 1; i <= 24; i++) {
		strOption += "<option value = '" + i + "'>" + i + "시간 이후 </option>";
	}

	// 반납 정비 시간
	$('#sel_returnInspectionTime').empty();
	$('#sel_returnInspectionTime').append(strOption);

	// 평일 왕복배달가능 시작 시간대 설정
	$('#sel_weekdayAbleDeliveryTime').empty();
	$('#sel_weekdayAbleDeliveryTime').append(strOption);

	// 주말 왕복배달가능 시간 시간대 설정
	$('#sel_weekendAbleDeliveryTime').empty();
	$('#sel_weekendAbleDeliveryTime').append(strOption);

	let strOptionwrm = "<option value = '0'>선택</option>";

	// 주말/공휴일 최소 예약 시간, 특정 기간 최소 예약 시간 최소시간
	// 48시간 단위로 설정한다.
	for (let i = 36; i <= 72; i += 3) {
		strOptionwrm += "<option value = '" + i + "'>" + i + "시간 이상 </option>";
	}

	// 주말/공휴일 최소 예약 시간
	$('#sel_weekendReserveMinimumTime').empty();
	$('#sel_weekendReserveMinimumTime').append(strOptionwrm);

	let sel_minimumTimestrOption = "<option value = '0'>선택</option>";

	$('#sel_minimumTime').empty();
	$('#sel_minimumTime').append(strOptionwrm);

	let strOptionhh = "<option value = ''>선택</option>";

	// 주말/공휴일 최소 예약 시간, 특정 기간 최소 예약 시간 최소시간
	// 48시간 단위로 설정한다.
	let M00 = "";
	let M30 = "";

	for (let i = 0; i < 24; i++) {
		if (i <= 9) {
			M00 = "0" + i + ":00";
			M30 = "0" + i + ":30";
		} else {
			M00 = i + ":00";
			M30 = i + ":30";
		}
		strOptionhh += "<option value = '" + M00.replace(':', '') + "'>" + M00 + "</option>";
		strOptionhh += "<option value = '" + M30.replace(':', '') + "'>" + M30 + "</option>";
	}
	strOptionhh += "<option value = '" + '24:00'.replace(':', '') + "'>" + '24:00' + "</option>";
	// 평일 영업시간
	$('#sel_weekdayOpenStart').empty();
	$('#sel_weekdayOpenStart').append(strOptionhh);
	$("#sel_weekdayOpenStart").val("0000").prop("selected", true);

	$('#sel_weekdayOpenEnd').empty();
	$('#sel_weekdayOpenEnd').append(strOptionhh);
	$("#sel_weekdayOpenEnd").val("0000").prop("selected", true);
	// 주말/공휴일 영업시간
	$('#sel_weekendOpenStart').empty();
	$('#sel_weekendOpenStart').append(strOptionhh);
	$("#sel_weekendOpenStart").val("0000").prop("selected", true);

	$('#sel_weekendOpenEnd').empty();
	$('#sel_weekendOpenEnd').append(strOptionhh);
	$("#sel_weekendOpenEnd").val("0000").prop("selected", true);
	// 평일 왕복배달가능 시간
	$('#sel_weekdayDeliveryStart').empty();
	$('#sel_weekdayDeliveryStart').append(strOptionhh);
	$("#sel_weekdayDeliveryStart").val("0000").prop("selected", true);

	$('#sel_weekdayDeliveryEnd').empty();
	$('#sel_weekdayDeliveryEnd').append(strOptionhh);
	$("#sel_weekdayDeliveryEnd").val("0000").prop("selected", true);

	$('#sel_weekendDeliveryStart').empty();
	$('#sel_weekendDeliveryStart').append(strOptionhh);
	$("#sel_weekendDeliveryStart").val("0000").prop("selected", true);

	$('#sel_weekendDeliveryEnd').empty();
	$('#sel_weekendDeliveryEnd').append(strOptionhh);
	$("#sel_weekendDeliveryEnd").val("0000").prop("selected", true);
}

function bindEvent() {

	$("#page").on('click', 'a', function () {
		if ($(this).attr('class') != 'active') {
			let clickPage = $(this).text();

			let displayPageNum = $("#showContents").val();

			if (!isNaN(clickPage)) { //숫자면 현재 페이지므로
				CURRENT_PAGE = parseInt(clickPage);
			} else { //
				if (clickPage == '«Previous Over') {
					CURRENT_PAGE = parseInt(CURRENT_PAGE) - 1;
				}
				if (clickPage == '»Next Over') {
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

$("#searchKeyWord").keypress(function (e) {
	let searchSelectBox = $("#searchSelectBox option:selected").val();
	if (!isEmpty(searchSelectBox)) {
		if (e.keyCode == 13) {
			fn_search();
		}
	}
});


$('#btnSearch').click(function (e) {
	fn_search();
});

function fn_search() {

	let searchKeyWord = $("#searchKeyWord").val();
	let searchSelectBox = $("#searchSelectBox option:selected").val();

	if (!isEmpty(searchSelectBox)) {
		if (isEmpty(searchKeyWord)) {
			errorAlert('검색어', '검색어를 입력하세요.');
		} else {
			loadApi(drawTable, null, null);
		}
	} else {
		loadApi(drawTable, null, null);
	}

}

/* =========================== detail function start ===================================== */

// init
function initDetailInfo(seq) {

//	swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
//	if(true){
//		return;
//	}
	// input box 초기화
	initInput();

	let _rtIdx = '';
	_rtIdx = seq;

	let req = {
		rtIdx: _rtIdx
	};

	let target = 'rentCompanyDetailInfo';
	let method = 'select';


	fn_callApi(method, target, req, function (response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code === 200) {

			if (getLoginUser().userRole !== 'RA'){
				$('#companyName').attr('readonly', true);
				$('#branchName').attr('readonly', true);
				$('#companyContact1').attr('readonly', true);
				$('#companyRegistrationName').attr('readonly', true);
				$('#companyRegistrationNumber').attr('readonly', true);
				$('#companyAddress').attr('readonly', true);
				$('#companyAddressDetail').attr('readonly', true);
				$('#commissionPer').attr('readonly', true);
			}

//			CRUD = 'modify';
			MCRUD = 'insert';
			RMCRUD = 'insert';
			HCRUD = 'insert';

			let data = res.result[0];

			let rtIdx = data.rtIdx;
			let companyName = data.companyName;
			let branchName = data.branchName;
			let companyRegistrationName = data.companyRegistrationName;
			let companyRegistrationNumber = data.companyRegistrationNumber;
			let companyAddress = data.companyAddress;
			let companyAddressDetail = data.companyAddressDetail;
			let accountBank = data.accountBank;
			let accountNumber = data.accountNumber;
			let taxEmail = data.taxEmail;
			let companyContact1 = data.companyContact1;
			let regCarCount = data.regCarCount;
			let carCount = data.carCount;
			let accountHolder = data.accountHolder;
			let alramYn = data.alarmYn;
//			let taxInvoiceCode = data.taxInvoiceCode;
			let commissionPer = isEmpty(data.commissionPer) ? 15.0 : data.commissionPer;
			let accessYn = data.accessYn;

			let ownerYn = data.ownerYn;

//			// 현재 시간
//			let today = new Date();
//
//			let hours = today.getHours(); // 시
//			let minutes = today.getMinutes(); // 분
//
//			let min = minutes.toString();
//
//			if (isEmpty(minutes)) {
//				min = "00";
//			} else {
//
//				if (min.length > 2) {
//					min = min + "0";
//				} else if (min.length == 2) {
//
//					if (min.charAt(min.length - 1) != '0') {
//						min = min.charAt(0) + "0";
//					} else {
//						min = min;
//					}
//				}
//			}
//
			let todayTime = ''; // hours + ":" + min;

			let weekdayOpenStart = data.weekdayOpenStart; // 평일영업시작시간
			let weekdayOpenEnd = data.weekdayOpenEnd; // 평일영업종료시간
			let weekendOpenStart = data.weekendOpenStart; // 주말영업시작시간
			let weekendOpenEnd = data.weekendOpenEnd; // 주말영업종료시간
			let weekdayDeliveryStart = data.weekdayDeliveryStart; // 평일배달시작시간
			let weekdayDeliveryEnd = data.weekdayDeliveryEnd; // 평일배달종료시간
			let weekendDeliveryStart = data.weekendDeliveryStart; // 주말배달시작시간
			let weekendDeliveryEnd = data.weekendDeliveryEnd; // 주말배달종료시간

			let returnInspectionTime = data.returnInspectionTime; // 반납정비시간
//			let visitAbleTime = data.visitAbleTime; // 방문예약가능시간
//			let deliveryAbleTime = data.deliveryAbleTime; // 배달예약가능시간
			let weekdayAbleDeliveryTime = data.weekdayAbleDeliveryTime; // 평일왕복배달가능시간
			let weekendAbleDeliveryTime = data.weekendAbleDeliveryTime; // 주말왕복배달가능시간
			let weekendReserveMinimumRate = data.weekendReserveMinimumRate; // 주말/공휴일 할증율
			let weekendReserveMinimumTime = data.weekendReserveMinimumTime; // 주말공휴일최소예약시간(분)

			let minIdx = data.minIdx; // 특정기간idx
			let minimumStartDt = data.minimumStartDt !== null? data.minimumStartDt : today; // 최소예약시간시작일
			let minimumEndDt = data.minimumEndDt!== null? data.minimumEndDt : today; // 최소에약시간종료일
			let minimumTime = data.minimumTime; // 최소시간(분)
			let raGbnLt = data.raGbnLt;
			if (raGbnLt > 0) {
				$("#raGbnLt").attr("placeholder", "설정완료");
			} else {
				$("#raGbnLt").attr("placeholder", "미설정");
			}
			let raGbnSt = data.raGbnSt;
			if (raGbnSt > 0) {
				$("#raGbnSt").attr("placeholder", "설정완료");
			} else {
				$("#raGbnSt").attr("placeholder", "미설정");
			}
			initModalSelectBox(data);

			$('#rentShopTitle').text('회원사 - '+companyName + ' / ' + branchName);
			$('#rtIdx').val(rtIdx);
			// 회사명
			$('#companyName').val(companyName);
			// 지저명
			$('#branchName').val(branchName);
			// 법인명
			$('#companyRegistrationName').val(companyRegistrationName);
			// 회사 대표번호
			$('#companyContact1').val(companyContact1);

			// 사업자등록번호
			if (!isEmpty(companyRegistrationNumber)) {
				companyRegistrationNumber = companyRegistrationNumberFormatter(companyRegistrationNumber,1);
			} else {
				companyRegistrationNumber = '';
			}

			$('#companyRegistrationNumber').val(companyRegistrationNumber);
			// 주소
			$('#companyAddress').val(companyAddress);

			$('#companyAddressDetail').val(companyAddressDetail);

			// 예금주
			$('#accountHolder').val(accountHolder);
			// 계좌번호
			$('#accountNumber').val(accountNumber);
			// 세금 계산서 이메일
			$('#taxEmail').val(taxEmail);

			// 알림톡 설정 여부
			if (!isEmpty(alramYn) && alramYn != 0) {
				$("#sel_alarmYn").val(alramYn).prop("selected", true);
			} else {
				$("#sel_alarmYn").val(0).prop("selected", true);
			}

			// 접속허용여부
			if (!isEmpty(accessYn) && accessYn != 0) {
				$("#sel_accessYn").val(accessYn).prop("selected", true);
			} else {
				$("#sel_accessYn").val(0).prop("selected", true);
			}

			// 보유차량대수가 0이면 등록차량대수로 보여준다.
			if (isEmpty(carCount) || carCount == 0) {
				carCount = regCarCount;
			}

			// END 회원사 정보
			$('#carCount').val(carCount);
			// 등록 차량 대수
			$('#regCarCount').val(regCarCount);

			// 회사 대표번호
			if (!isEmpty(companyContact1)) {
				companyContact1 = phoneFomatter(companyContact1);
			} else {
				companyContact1 = '';
			}

			$('#companyContact1').val(companyContact1);

			// 스탭프 정보
			staffListGrid(rtIdx);

			$('#commissionPer').val(commissionPer);
//			$('#taxInvoiceCode').val(taxInvoiceCode);

			$("#sel_weekdayOpenStart").val(weekdayOpenStart).prop("selected", true);
			$("#sel_weekdayOpenEnd").val(weekdayOpenEnd).prop("selected", true);
			$("#sel_weekendOpenStart").val(weekendOpenStart).prop("selected", true);
			$("#sel_weekendOpenEnd").val(weekendOpenEnd).prop("selected", true);
			$("#sel_weekdayDeliveryStart").val(weekdayDeliveryStart).prop("selected", true);
			$("#sel_weekdayDeliveryEnd").val(weekdayDeliveryEnd).prop("selected", true);
			$("#sel_weekendDeliveryStart").val(weekendDeliveryStart).prop("selected", true);
			$("#sel_weekendDeliveryEnd").val(weekendDeliveryEnd).prop("selected", true);

//			let strOption = "<option value = '0'>선택</option>";
//
//			// 반납정비시간
//			// 48시간 단위로 설정한다.
//			for (let i = 1; i <= 48; i++) {
//				strOption += "<option value = '" + i + "'>" + i + "시간 이후 </option>";
//			}
//
//			// 반납 정비 시간
//			$('#sel_returnInspectionTime').empty();
//			$('#sel_returnInspectionTime').append(strOption);

			if (!isEmpty(returnInspectionTime)) {
				$("#sel_returnInspectionTime").val(returnInspectionTime).prop("sel_returnInspectionTime", true);
			} else {
				$("#sel_returnInspectionTime").val(0).prop("sel_returnInspectionTime", true);
			}


			if (!isEmpty(weekdayAbleDeliveryTime)) {
				$("#sel_weekdayAbleDeliveryTime").val(weekdayAbleDeliveryTime).prop("sel_weekdayAbleDeliveryTime", true);
			}

			if (!isEmpty(weekendAbleDeliveryTime)) {
				$("#sel_weekendAbleDeliveryTime").val(weekendAbleDeliveryTime).prop("sel_weekendAbleDeliveryTime", true);
			}

			if (!isEmpty(weekendReserveMinimumTime)) {
				$("#sel_weekendReserveMinimumTime").val(weekendReserveMinimumTime).prop("sel_weekendReserveMinimumTime", true);
			}
			$('#weekendReserveMinimumRate').val(weekendReserveMinimumRate);

			// 예약정보(선택입력) 정보 - 특정 기간 최소 예약 시간 최소시간
			rentReserveMinListGrid(rtIdx);
			// 예약정보(선택입력) 정보 - 휴무일 정보
			holidayListGrid(rtIdx);

			// 예약정보(선택사항) 설정
			$('#minIdx').val(minIdx);

			CRUD = 'modify';
//			openIziModal(MODAL_NAME);
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
		}
	});// end fn_callApi
}

function staffListGrid(_rtIdx) {
	// 스텝리스트를 조회한다.
	let target = 'rentCompanyStaffList';
	let method = 'select';

	let req = {
		rtIdx: _rtIdx
	};

	fn_callApi(method, target, req, function (response) {
		let res = response;
		let rows = [];

		if (res.code == 200) {
			for (var i = 0; i< res.result.length; i++){
				var data = res.result[i];

				var ownerYn = data.ownerYn === 1 ? 'Y' : 'N';

				$('#staffurIdx').val(data.urIdx);
				$('#rsIdx').val(data.rsIdx);
				$('#staffEmail').val(data.staffEmail);
				$('#staffName').val(data.staffName);
				$('#staffTitle').val(data.staffTitle);
				$("#sel_ownerYn").val(ownerYn).prop("selected", "selected");
				$('#staffContact1').val(data.staffContact1);

				var staff = {
					"rsIdx": data.rsIdx,
					"staffName" :data.staffName,
					"staffContact1" :data.staffContact1,
					"staffEmail" : data.staffEmail,
					"staffTitle" : data.staffTitle,
					"ownerYn" : data.ownerYn,
					"urIdx" : data.urIdx,
					"deleteStaff" : '삭제'
				};

				rows.push(staff);
			}

			let columns;
			columns = [
				{"name": "rsIdx", "id": "rsIdx", "title": "직원번호"},
				{"name": "staffName", "id": "staffName" ,"title": "직원이름"},
				{
					"name": "staffContact1", "id":"staffContact1","title": "직원연락처", "breakpoints": "xs",
					"formatter": function (value, options, rowData) {
						let setText = '';

						if (!isEmpty(value)) {
							if (value.length > 4) {
								setText = phoneFomatter(value);
							} else {
								setText = '미확인';
							}
						} else {
							setText = '미확인';
						}

						return setText;
					}
				},
				{"name": "staffEmail", "id":"staffEmail", "title": "직원이메일", "breakpoints": "xs"},
				{"name": "staffTitle", "id":"staffTitle", "title": "직위", "breakpoints": "xs"},
				{
					"name": "ownerYn", "id":"ownerYn","title": "대표여부", "breakpoints": "xs",
					"formatter": function (value, options, rowData) {
						let setText = '';
						if (!isEmpty(value)) {
							if (value == 'Y') {
								setText = '예';
							} else {
								setText = '아니요';
							}
						} else {
							setText = '미확인';
						}

						return setText;
					}
				},
				{"name": "urIdx", "id": "urIdx", "title": "회원순번", "visible": false},
				{"name": "deleteStaff", "id":"deleteStaff", "title": "삭제", "type": "text", "formatter" : function () {
						var actions = $('<div/>');

						var btnDeleteStaff = ($('<button/>', {'class':'btn btn-outline-danger'})
							.append('<span/>','삭제')
							.on("click", this, deleteStaff))
							.appendTo(actions);

						return actions;
				}}
			];


			function deleteStaff(){
				var tr = $(this).closest('tr').children();
				var rsIdx = tr[0].textContent;
				var deleteYn = confirm("해당 담당자를 삭제하시겠습니까?");
				if (deleteYn){

					var url = '/api/v1.0/deleteDcRentCompanyStaff.do';
					var req = {
						rsIdx : rsIdx
					};

					$.ajax({
						url: url,
						type: 'POST',
						data: JSON.stringify(req),
						contentType: 'application/json',
						cache: false,
						acync : false,
						timeout: 10000
					}).done(function (data, textStatus, jqXHR) {

						if( data.code == 400 ){
							alert(data.errMsg);
						}
						if( data.code == 200 ){
							location.reload();
						}

					});

				}
			}

			$('#rentStaffList').empty();
			$('#rentStaffList').footable({
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

		}// end 200 check
	});// end fn_callApi
	// end 회원사 직원 리스트
}

function rentReserveMinListGrid(_rtIdx) {
	// 특정시간 리스트를 조회한다.
	let target = 'rentCompanyReserveMinList';
	let method = 'select';

	let req = {
		rtIdx: _rtIdx
	};

	fn_callApi(method, target, req, function (response) {
		let res = response;
		let rows = [];

		if (res.code == 200) {

			for (var i = 0; i< res.result.length; i++){
				var data = res.result[i];

				var min = {
					"minIdx": data.minIdx,
					"minimumStartDt": data.minimumStartDt,
					"minimumEndDt": data.minimumEndDt,
					"minimumTime": data.minimumTime,
					"deleteStaff" : '삭제'
				};

				rows.push(min);
			}

			let columns;
			columns = [
				{"name": "minIdx", "id": "minIdx", "title": "특정기간번호"},
				{"name": "minimumStartDt", "title": "최소예약시간시작일", "breakpoints": "xs"},
				{"name": "minimumEndDt", "title": "최소예약시간종료일", "breakpoints": "xs"},
				{"name": "minimumTime", "title": "최소시간", "breakpoints": "xs"},
				{"name": "deleteStaff", "id":"deleteStaff", "title": "삭제", "type": "text", "formatter" : function () {
						var actions = $('<div/>');

						var btnDeleteMin = ($('<button/>', {'class':'btn btn-outline-danger'})
							.append('<span/>','삭제')
							.on("click", this, deleteReserveMin))
							.appendTo(actions);

						return actions;
					}}
			];


			function deleteReserveMin(){
				var tr = $(this).closest('tr').children();
				var minIdx = tr[0].textContent;
				var deleteYn = confirm("해당 최소예약시간을 삭제하시겠습니까?");
				if (deleteYn){

					var url = '/api/v1.0/deleteDcRentCompanyReserveMin.do';
					var req = {
						minIdx : minIdx
					};

					$.ajax({
						url: url,
						type: 'POST',
						data: JSON.stringify(req),
						contentType: 'application/json',
						cache: false,
						acync : false,
						timeout: 10000
					}).done(function (data, textStatus, jqXHR) {

						if( data.code == 400 ){
							alert(data.errMsg);
						}
						if( data.code == 200 ){
							location.reload();
						}

					});

				}
			}

			$('#rentReserveMinList').empty();
			$('#rentReserveMinList').footable({
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

		}// end 200 check
	});// end fn_callApi
	// end 회원사 직원 리스트
}

function holidayListGrid(_rtIdx) {
	// 휴무일을 조회한다.
	let target = 'selectRentCompanyHoliday';
	let method = 'select';

	let req = {
		rtIdx: _rtIdx
	};

	fn_callApi(method, target, req, function (response) {
		let res = response;
		let rows = [];

		if (res.code == 200) {

			for (var i = 0; i < res.result.length; i++){
				let data = res.result[i];

				var holiday = {
					"holIdx": data.holIdx,
					"holidayStartDt": data.holidayStartDt,
					"holidayEndDt": data.holidayEndDt,
					"holidayName": data.holidayName,
					"deleteStaff" : '삭제'
				};

				rows.push(holiday);
			}

			let columns;
			columns = [
				{"name": "holIdx", "id": "holIdx", "title": "휴무일번호"},
				{"name": "holidayStartDt", "title": "휴무일시작일", "breakpoints": "xs"},
				{"name": "holidayEndDt", "title": "휴무일종료일", "breakpoints": "xs"},
				{"name": "holidayName", "title": "휴무일명", "breakpoints": "xs"},
				{"name": "deleteStaff", "id":"deleteStaff", "title": "삭제", "type": "text", "formatter" : function () {
						var actions = $('<div/>');

						var btnDeleteHoliday = ($('<button/>', {'class':'btn btn-outline-danger'})
							.append('<span/>','삭제')
							.on("click", this, deleteReserveHoliday))
							.appendTo(actions);

						return actions;
					}}
			];


			function deleteReserveHoliday(){
				var tr = $(this).closest('tr').children();
				var holIdx = tr[0].textContent;
				var deleteYn = confirm("해당 휴무일을 삭제하시겠습니까?");
				if (deleteYn){

					var url = '/api/v1.0/deleteRentCompanyHoliday.do';
					var req = {
						holIdx : holIdx
					};

					$.ajax({
						url: url,
						type: 'POST',
						data: JSON.stringify(req),
						contentType: 'application/json',
						cache: false,
						acync : false,
						timeout: 10000
					}).done(function (data, textStatus, jqXHR) {

						if( data.code == 400 ){
							alert(data.errMsg);
						}
						if( data.code == 200 ){
							location.reload();
						}

					});

				}
			}
			$('#holidayList').empty();
			$('#holidayList').footable({
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

		}// end 200 check
	});// end fn_callApi
	// end 회원사 직원 리스트
}

/*
 * detailValidation
 *
 *
 */
function detailValidation(save_type) {

	let title, text, icon, cancel_text;
	let req = {};
	let target = '';
	let method = '';

	var _rtIdx = $('#rtIdx').val().trim();

	var loginUser = getLoginUser();

	GLOBAL_LOGIN_USER_IDX = loginUser.urIdx;
	GLOBAL_LOGIN_USER_ROLE = loginUser.userRole;


	if (save_type !== 'saveRentCompanyInfo' && isEmpty(_rtIdx)) {
		errorAlert('회원사정보', '회원사 정보를 먼저 저장해 주세요.');
		return;
	}

	if (!isEmpty(save_type)) { // is not empty
		switch (save_type) {
			case 'saveRentCompanyInfo':// 회원사정보
				let companyName = $('#companyName').val();
				let branchName = $('#branchName').val();
				let companyZipcode = $('#companyZipcode').val();
				let companyAddress = $('#companyAddress').val();
				let companyAddressDetail = $('#companyAddressDetail').val();
				let companyRegistrationName = $('#companyRegistrationName').val();
				let companyRegistrationNumber = $('#companyRegistrationNumber').val();
				let companyContact1 = $('#companyContact1').val();

				let accountBank = $("#sel_bank option:selected").val();
				let accountNumber = $('#accountNumber').val();
				let accountHolder = $('#accountHolder').val();
				let carCount = $('#carCount').val();
				let regCarCount = $('#regCarCount').val();
				let taxEmail = $('#taxEmail').val();


				let accessYn = $("#sel_accessYn option:selected").val();
				let alarmYn = $("#sel_alarmYn option:selected").val();


				if (isEmpty(companyName)) { // is not empty
					errorAlert('회원사', '회사명 필드를 확인하세요.');
					$('#companyName').focus();
					return;
				}

				if (isEmpty(branchName)) { // is not empty
					errorAlert('회원사', '지점명 필드를 확인하세요.');
					$('#branchName').focus();
					return;
				}

				if (isEmpty(companyRegistrationName)) { // is not empty
					errorAlert('회원사', '법인명 필드를 확인하세요.');
					$('#companyRegistrationName').focus();
					return;
				}

				if (isEmpty(companyContact1)) { // is not empty
					errorAlert('대표번호', '대표번호 필드를 확인하세요.');
					$('#companyContact1').focus();
					return;
				}

				if (!isEmpty(companyRegistrationNumber)) {
					companyRegistrationNumber = removeHypen(companyRegistrationNumber);
				}


				if (isEmpty(accountBank) || accountBank == 0) {
					accountBank = '0';
				}

				if(!isValidEmail(taxEmail)) { //is not Valid
					errorAlert('세금 계산서 이메일', '세금 계산서 이메일은 이메일 형식으로 입력해주세요.');
					$('#taxEmail').focus();
					return;
				}

				if (isEmpty(accessYn) || accessYn == 0 || accessYn == 'N') {
					accessYn = 'N';
				} else {
					accessYn = 'Y';
				}

				if (isEmpty(alarmYn) || alarmYn == 0 || alarmYn == 'N') {
					alarmYn = 'N';
				}

				if (CRUD === 'insert') {
					if (!isEmpty(_rtIdx)) {
						errorAlert('API ERROR', '이미 존재하는 RTIDX 입니다.');
					}

					req = {};
					req = {
						rtIdx: _rtIdx,
						companyName: companyName,
						companyContact1: companyContact1,
						companyZipcode: companyZipcode,
						companyAddress: companyAddress,
						companyAddressDetail: companyAddressDetail,
						companyRegistrationNumber: companyRegistrationNumber,
						accountBank: accountBank,
						accountNumber: accountNumber,
						accountHolder: accountHolder,
						taxEmail : taxEmail,
						accessYn: accessYn,
						companyRegistrationName: companyRegistrationName,
						branchName: branchName,
						carCount: carCount,
						regCarCount: regCarCount,
						regId: GLOBAL_LOGIN_USER_IDX,
						regDt: today,
						alarmYn: alarmYn
					}
				} else if (CRUD === 'modify') {
					req = {};
					req = {
						rtIdx: _rtIdx,
						companyName: companyName,
						companyContact1: companyContact1,
						companyZipcode: companyZipcode,
						companyAddress: companyAddress,
						companyAddressDetail: companyAddressDetail,
						companyRegistrationNumber: companyRegistrationNumber,
						accountBank: accountBank,
						accountNumber: accountNumber,
						accountHolder: accountHolder,
						taxEmail : taxEmail,
						accessYn: accessYn,
						carCount: carCount,
						regCarCount: regCarCount,
						companyRegistrationName: companyRegistrationName,
						branchName: branchName,
						modId: GLOBAL_LOGIN_USER_IDX,
						modDt: today,
						alarmYn: alarmYn
					};
				}

				title = '회원사정보 저장';
				text = '저장하시겠습니까?';
				icon = 'info';
				cancel_text = '취소하셨습니다.';

				call_before_save(title, text, icon, cancel_text, save_type, req);
				break;

			case 'saveStaff':// 담당자 정보
				// if($("#btnstaffEmail").attr('disabled') != 'disabled'){
				// 	errorAlert('회원검색', '회원검색을 먼저 하여 주세요');
				// 	return;
				// }
				let rsIdx = $('#rsIdx').val(); 							// 관리자순번
				let staffurIdx = $('#staffurIdx').val(); 				// 직원회원순번
				let staffName = $('#staffName').val(); 					// 직원명
				let staffContact1 = getPureText($('#staffContact1').val()); 			// 연락처1
				let staffEmail = $('#staffEmail').val(); 				// 이메일
				let staffTitle = $('#staffTitle').val(); 				// 직위
				let ownerYn = $("#sel_ownerYn option:selected").val();	// 대표여부
				let staffTypeCode = $('#staffTypeCode').val(); 			// 직원분류code

				if (isEmpty(staffName)) { // is not empty
					errorAlert('직원이름', '직원이름은 필수 입력값 입니다.\n\r회원검색을 하여주세요');
					return;
				}

				if (isEmpty(staffContact1)) { // is not empty
					errorAlert('직원 연락처', '직원 연락처는 필수 입력값 입니다.\n\r회원검색을 하여주세요');
					return;
				}

				if (isEmpty(staffEmail)) { // is not empty
					errorAlert('직원 이메일', '직원 이메일은 필수 입력값 입니다.');
					return;
				}

				if (!isValidEmail(staffEmail)) {
					errorAlert("이메일", "올바른 이메일 주소가 아닙니다");
					return;

				}

				// 중복검사
				target = 'rentCompanyStaffList';
				method = 'select';

				req = {};
				req = {
					rtIdx: _rtIdx,
					urIdx: staffurIdx,
					rsIdx: rsIdx,
					staffName: staffName,
					staffEmail: staffEmail,
					staffTitle: staffTitle,
					staffContact1: staffContact1,
					regId: GLOBAL_LOGIN_USER_IDX,
					regDt: today,
					modId: GLOBAL_LOGIN_USER_IDX,
					modDt: today,
					ownerYn: ownerYn

				};

				fn_callApi(method, target, req, function (response) {
					let res = response;
					let data = res.result;
					//TODO 중복검사

					// 	if (data.length == 0) {
					if (MCRUD == 'insert') {
						req = {};
						req = {
							rtIdx: _rtIdx,
							urIdx: staffurIdx,
							staffName: staffName,
							staffEmail: staffEmail,
							staffTitle: staffTitle,
							staffContact1: staffContact1,
							regId: GLOBAL_LOGIN_USER_IDX,
							regDt: today,
							ownerYn: ownerYn
						};
					}

					title = '스텝정보 저장';
					text = '저장하시겠습니까?'
					icon = 'info';
					cancel_text = '취소하셨습니다.';

					call_before_save(title, text, icon, cancel_text, save_type, req);
//

				});// end fn_callApi

				break;
			case 'updateCommission': // 수수료 정보 업데이트
				let commissionPer = $('#commissionPer').val(); // 부가세 포함 수수료

				if (isEmpty(commissionPer)) { // is not empty
					errorAlert('부가세 포함 수수료', '부가세 포함 수수료는 필수 입력값 입니다.');
					return;
				}

				req = {};

				if (CRUD === 'insert'){
					req = {
						rtIdx: _rtIdx,
						commissionPer: commissionPer,
						regId : GLOBAL_LOGIN_USER_IDX,
						regDt : today
					};
				}else if(CRUD === 'modify'){
					req = {
						rtIdx: _rtIdx,
						commissionPer: commissionPer,
						modId : GLOBAL_LOGIN_USER_IDX,
						modDt : today
					};
				}

				title = '수수료정보 저장';
				text = '저장하시겠습니까?';
				icon = 'info';
				cancel_text = '취소하셨습니다.';

				call_before_save(title, text, icon, cancel_text, save_type, req);
				break;

			case 'saveRentCompanyTime': // 예약정보 - 운영시간, 배달시간 등
				let weekdayOpenStart = getPureText($("#sel_weekdayOpenStart option:selected").val());									// 평일 영업시간 시작
				let weekdayOpenEnd = getPureText($("#sel_weekdayOpenEnd option:selected").val());									// 평일 영업시간 종료
				let weekendOpenStart = getPureText($("#sel_weekendOpenStart option:selected").val());									// 주말/공휴일 영업시간 시작
				let weekendOpenEnd = getPureText($("#sel_weekendOpenEnd option:selected").val());									// 주말/공휴일 영업시간 종료
				let weekdayDeliveryStart = getPureText($("#sel_weekdayDeliveryStart option:selected").val());								// 평일 왕복배달가능 시간 시작
				let weekdayDeliveryEnd = getPureText($("#sel_weekdayDeliveryEnd option:selected").val());								// 평일 왕복배달가능 시간 종료
				let weekendDeliveryStart = getPureText($("#sel_weekendDeliveryStart option:selected").val());								// 주말/공휴일 왕복배달가능시간 시작
				let weekendDeliveryEnd = getPureText($("#sel_weekendDeliveryEnd option:selected").val());								// 주말/공휴일 왕복배달가능시간 종료

				let weekdayAbleDeliveryTime = $("#sel_weekdayAbleDeliveryTime option:selected").val();		// 평일 왕복배달가능 시작 시간대 설정
				let weekendAbleDeliveryTime = $("#sel_weekendAbleDeliveryTime option:selected").val();		// 주말 왕복배달가능 시간 시간대 설정

				let returnInspectionTime = $("#sel_returnInspectionTime option:selected").val();			// 반납정비 시간 시간대 설정
				let weekendReserveMinimumTime = $("#sel_weekendReserveMinimumTime option:selected").val();	// 주말/공휴일 최소 예약 시간
				let weekendReserveMinimumRate = getPureText($("#weekendReserveMinimumRate").val());						// 주말/공휴일 할증율

				if (isEmpty(weekdayOpenStart)) { // is not empty
					errorAlert('평일 영업시간', '평일 영업시간 시작는 필수 선택값 입니다.');
					return;
				}

				if (isEmpty(weekdayOpenEnd)) { // is not empty
					errorAlert('평일 영업시간', '평일 영업시간 종료는 필수 선택값 입니다.');
					return;
				}

				if (weekdayOpenStart > weekdayOpenEnd) {
					errorAlert('평일 영업시간', '평일 영업시간 시작이 종료보다 클 수 없습니다..');
					return;
				}

				if (isEmpty(weekendOpenStart)) { // is not empty
					errorAlert('주말/공휴일 영업시간', '주말/공휴일 영업시간 시작는 필수 선택값 입니다.');
					return;
				}

				if (isEmpty(weekendOpenEnd)) { // is not empty
					errorAlert('주말/공휴일 영업시간', '주말/공휴일 영업시간 종료는 필수 선택값 입니다.');
					return;
				}

				if (weekendOpenStart > weekendOpenEnd) {
					errorAlert('주말/공휴일 영업시간', '주말/공휴일 영업시간 시작이 종료보다 클 수 없습니다..');
					return;
				}

				if (isEmpty(weekdayDeliveryStart)) { // is not empty
					errorAlert('평일 왕복배달가능 시간', '평일 왕복배달가능 시간 시작는 필수 선택값 입니다.');
					return;
				}

				if (isEmpty(weekdayDeliveryEnd)) { // is not empty
					errorAlert('평일 왕복배달가능 시간', '평일 왕복배달가능 시간 종료는 필수 선택값 입니다.');
					return;
				}

				if (weekdayDeliveryStart > weekdayDeliveryEnd) {
					errorAlert('평일 왕복배달가능 시간', '평일 왕복배달가능 시간 시작이 종료보다 클 수 없습니다..');
					return;
				}

				if (isEmpty(weekendDeliveryStart)) { // is not empty
					errorAlert('주말/공휴일 왕복배달가능 시간', '주말/공휴일 왕복배달가능 시간 시작는 필수 선택값 입니다.');
					return;
				}

				if (isEmpty(weekendDeliveryEnd)) { // is not empty
					errorAlert('주말/공휴일 왕복배달가능 시간', '주말/공휴일 왕복배달가능 시간 종료는 필수 선택값 입니다.');
					return;
				}

				if (weekendDeliveryStart > weekendDeliveryEnd) {
					errorAlert('주말/공휴일 왕복배달가능 시간', '주말/공휴일 왕복배달가능 시간 시작이 종료보다 클 수 없습니다..');
					return;
				}

				if (isEmpty(weekdayAbleDeliveryTime) || weekdayAbleDeliveryTime == '0') { // is not empty
					errorAlert('평일 왕복배달가능 시간대 설정', '평일 왕복배달가능 시간대 설정은 필수 입력값 입니다.');
					return;
				}

				if (isEmpty(weekendAbleDeliveryTime) || weekendAbleDeliveryTime == '0') { // is not empty
					errorAlert('주말 왕복배달가능 시간대 설정', '주말 왕복배달가능 시간대 설정은 필수 입력값 입니다.');
					return;
				}

				if (isEmpty(returnInspectionTime) || returnInspectionTime == '0') { // is not empty
					errorAlert('반납정비 시간 시간대 설정', '반납정비 시간 시간대 설정은 필수 입력값 입니다.');
					return;
				}

				if (isEmpty(weekendReserveMinimumRate)) { // is not empty
					weekendReserveMinimumRate = "15";
				}

				req = {};

				if (CRUD === 'insert'){
					req = {
						rtIdx: _rtIdx,
						weekdayOpenStart: weekdayOpenStart,
						weekdayOpenEnd: weekdayOpenEnd,
						weekendOpenStart: weekendOpenStart,
						weekendOpenEnd: weekendOpenEnd,
						weekdayDeliveryStart: weekdayDeliveryStart,
						weekdayDeliveryEnd: weekdayDeliveryEnd,
						weekendDeliveryStart: weekendDeliveryStart,
						weekendDeliveryEnd: weekendDeliveryEnd,
						weekdayAbleDeliveryTime: weekdayAbleDeliveryTime,
						weekendAbleDeliveryTime: weekendAbleDeliveryTime,
						returnInspectionTime: returnInspectionTime,
						weekendReserveMinimumTime: weekendReserveMinimumTime,
						weekendReserveMinimumRate: weekendReserveMinimumRate,
						regId : GLOBAL_LOGIN_USER_IDX,
						regDt : today
					};
				}else if (CRUD === 'modify'){
					req = {
						rtIdx: _rtIdx,
						weekdayOpenStart: weekdayOpenStart,
						weekdayOpenEnd: weekdayOpenEnd,
						weekendOpenStart: weekendOpenStart,
						weekendOpenEnd: weekendOpenEnd,
						weekdayDeliveryStart: weekdayDeliveryStart,
						weekdayDeliveryEnd: weekdayDeliveryEnd,
						weekendDeliveryStart: weekendDeliveryStart,
						weekendDeliveryEnd: weekendDeliveryEnd,
						weekdayAbleDeliveryTime: weekdayAbleDeliveryTime,
						weekendAbleDeliveryTime: weekendAbleDeliveryTime,
						returnInspectionTime: returnInspectionTime,
						weekendReserveMinimumTime: weekendReserveMinimumTime,
						weekendReserveMinimumRate: weekendReserveMinimumRate,
						modId : GLOBAL_LOGIN_USER_IDX,
						modDt : today
					};
				}

				title = '예약정보 저장';
				text = '저장하시겠습니까?';
				icon = 'info';
				cancel_text = '취소하셨습니다.';

				call_before_save(title, text, icon, cancel_text, save_type, req);
				break;
			case 'saveRentCompanyMin':// 회원사정보 - 특정기간
				let minIdx = $('#minIdx').val(); 								// 특정기간 순번
				let minimumStartDt = formatDate(getMinimumStartDt()); 			// 특정기간 최소 예약  시간 시작
				let minimumEndDt = formatDate(getMinimumEndDt());               // 특정기간 최소 예약  시간 종료
				let minimumTime = $("#sel_minimumTime option:selected").val();	// 특정기간 최소시간

				if (isEmpty(minimumStartDt) && chkValDate(minimumStartDt) == null) { // is not empty
					errorAlert('특정 기간 최소 예약 시간', '시작일은 필수 입력값 입니다');
					return;
				}

				if (isEmpty(minimumEndDt) && chkValDate(minimumEndDt) == null) { // is not empty
					errorAlert('특정 기간 최소 예약 시간', '종료일은 필수 입력값 입니다.');
					return;
				}

				if (isEmpty(minimumTime)) { // is not empty
					errorAlert('특정 기간 최소 예약 시간', '최소시간는 필수 입력값 입니다.');
					return;
				}

				if (minIdx === "") {
					RMCRUD = 'insert';
				}else RMCRUD = 'modify';

				// 중복검사
				target = 'rentCompanyReserveMinList';
				method = 'select';

				req = {};
				req = {
					rtIdx: _rtIdx,
					minIdx: minIdx,
					minimumStartDt: minimumStartDt,
					minimumEndDt: minimumEndDt
				};

				fn_callApi(method, target, req, function (response) {
					let res = response;
					if (res.code === 400) {
						let data = res.result;
						if (RMCRUD == 'modify') {
							if (!isEmpty(_rtIdx)) { //If This is not empty,
								RMCRUD = 'modify';

								req = {};
								req = {
									rtIdx: _rtIdx,
									minIdx: minIdx,
									minimumStartDt: minimumStartDt,
									minimumEndDt: minimumEndDt,
									minimumTime: minimumTime,
									modDt: today,
									modId: GLOBAL_LOGIN_USER_IDX
								};

								title = '특정시간정보 저장';
								text = '저장하시겠습니까?'
								icon = 'info';
								cancel_text = '취소하셨습니다.';

								call_before_save(title, text, icon, cancel_text, save_type, req);
							}
						} else if (RMCRUD == 'insert') {
							req = {};
							req = {
								rtIdx: _rtIdx,
								minimumStartDt: minimumStartDt,
								minimumEndDt: minimumEndDt,
								minimumTime: minimumTime,
								regId: GLOBAL_LOGIN_USER_IDX,
								regDt: today
							};

							title = '특정시간정보 저장';
							text = '저장하시겠습니까?';
							icon = 'info';
							cancel_text = '취소하셨습니다.';

							call_before_save(title, text, icon, cancel_text, save_type, req);

						} else {
							errorAlert('특정 기간 최소 예약 시간', '중복된 날짜가 존재하여 저장 할 수 없습니다.');
						}
					}
				}); //fn_callApi rentCompanyReserveMinList
				break;
			case 'saveRentCompanyHoliday':// 회원사정보 - 휴무일정보
				let holIdx = $('#holIdx').val(); 								// 휴무일 순번
				let holidayStartDt = formatDate(getHolidayStartDt()); 			// 휴무일 시작일
				let holidayEndDt = formatDate(getHolidayEndDt());				// 휴무일 종료일
				let holidayName = $("#holidayName").val();						// 휴무일명


				if (parseInt((new Date(holidayStartDt).getTime() / (1000 * 60 *60 )) /24) < parseInt((new Date().getTime() / (1000 * 60 *60 )) /24)){
					errorAlert('휴무일 시작일','휴무일은 과거가 될 수 없습니다.');
					return;
				}

				if (isEmpty(holidayStartDt) && chkValDate(holidayStartDt) == null) { // is not empty
					errorAlert('휴무일 시작일', '시작일은 필수 입력값 입니다');
					return;
				}

				if (isEmpty(holidayEndDt) && chkValDate(holidayEndDt) == null) { // is not empty
					errorAlert('휴무일 종료일', '휴무일 종료일은 필수 입력값 입니다.');
					return;
				}

				if (holidayStartDt > holidayEndDt) {
					errorAlert('휴무일', '휴무일 시작일은 종료일 보다 클수 없습니다.');
					return;
				}

				if (holIdx === "") {
					HCRUD = 'insert';
				}else HCRUD = 'modify';

				// 중복검사
				target = 'selectRentCompanyHoliday';
				method = 'select';

				req = {};
				req = {
					rtIdx: _rtIdx,
					holidayStartDt: holidayStartDt,
					holidayEndDt: holidayEndDt
				};

				fn_callApi(method, target, req, function (response) {

					let res = response;
					if (res.code == 400) {
						let data = res.result;
						if (data == null) {

							if (HCRUD == 'modify') {
								if (!isEmpty(_rtIdx)) { //If This is not empty,
									HCRUD = 'modify';

									req = {};
									req = {
										rtIdx: _rtIdx,
										holIdx: holIdx,
										holidayStartDt: holidayStartDt,
										holidayEndDt: holidayEndDt,
										holidayName: holidayName,
										modDt: today,
										modId: GLOBAL_LOGIN_USER_IDX
									};

									title = '휴무일정보 저장';
									text = '저장하시겠습니까?'
									icon = 'info';
									cancel_text = '취소하셨습니다.';

									call_before_save(title, text, icon, cancel_text, save_type, req);
								}
							} else if (HCRUD == 'insert') {
								req = {};
								req = {
									rtIdx: _rtIdx,
									holidayStartDt: holidayStartDt,
									holidayEndDt: holidayEndDt,
									holidayName: holidayName,
									regId: GLOBAL_LOGIN_USER_IDX,
									regDt: today
								};

								title = '휴무일정보 저장';
								text = '저장하시겠습니까?'
								icon = 'info';
								cancel_text = '취소하셨습니다.';

								call_before_save(title, text, icon, cancel_text, save_type, req);
							}

						}
					}else {
						errorAlert('휴무일정보', '중복된 날짜가 존재하여 저장 할 수 없습니다.');
					}
				}); //fn_callApi saveRentCompanyHoliday
				break;

		}// end switch
	}// end isEmpty(value)

}// end detailValidation

/*
 * detailSubmit todo work ajax
 *
 */
function detailSubmit(save_type, req) {
	let target = '';
	let method = '';
	let _rtIdx = $("#rtIdx").val();

	if (isEmpty(save_type)) { // save_type
		errorAlert('API ERROR', 'Save Type이 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}

	if (isEmpty(req)) { // req array
		errorAlert('API ERROR', '전송가능한 파라메터가 존재하지 않습니다. 관리자에게 문의하세요');
		return;
	}

	switch (save_type) {
		case 'saveRentCompanyInfo':// 회원사정보
			if (CRUD == 'insert') {
				target = 'insertDcRentCompany';
				method = 'insert';
			} else if (CRUD == 'modify') {
				target = 'updateDcRentCompany';
				method = 'update';
			}
			break;
		case 'saveStaff':// 스텝정보
			if (MCRUD == 'insert') {
				target = 'insertDcRentCompanyStaff';
				method = 'insert';
			} else if (MCRUD == 'modify') {
				target = 'updateDcRentCompanyStaff';
				method = 'update';
			}
			break;
		case 'updateCommission':// 수수료율
			target = 'updateDcRentCompanyCommission';
			method = 'update';
			break;
		case 'saveRentCompanyTime':// 영업시간
			target = 'updateDcRentCompanyTime';
			method = 'update';
			break;
		case 'saveRentCompanyMin':// 특정기간
			if (RMCRUD == 'insert') {
				target = 'insertDcRentCompanyReserveMin';
				method = 'insert';
			} else if (RMCRUD == 'modify') {
				target = 'updateDcRentCompanyReserveMin';
				method = 'update';
			}
			break;
		case 'saveRentCompanyHoliday':// 휴무일
			if (HCRUD == 'insert') {
				target = 'insertRentCompanyHoliday';
				method = 'insert';
			} else if (HCRUD == 'modify') {
				target = 'updateRentCompanyHoliday';
				method = 'update';
			}
			break;

	}// end switch

	if (isEmpty(target)) { // is not empty
		errorAlert('API ERROR', 'api target이 존재하지 않습니다. 관리자에게 문의하세요');
	}
	if (isEmpty(method)) { // is not empty
		errorAlert('API ERROR', 'api method가 존재하지 않습니다. 관리자에게 문의하세요');
	}

	fn_callApi(method, target, req, function (response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code === 200) {

			// if (res.data.result == 1) {
			swal("저장 성공", {icon: "success"});

			switch (save_type) {
				case 'saveStaff':// 스텝정보
					staffListGrid(_rtIdx);
					break;
				case 'saveRentCompanyInfo':// 회원사정보
					if (CRUD === 'insert') {
						_rtIdx = res.rtIdx;
						$("#rtIdx").val(_rtIdx);
						initDetailInfo(_rtIdx);
//						$("#"+MODAL_NAME).iziModal('close');
//						loadApi(drawTable, null, null);
					}
					break;
				case 'saveRentCompanyMin':// 특정기간정보
					rentReserveMinListGrid(_rtIdx);
					break;
				case 'saveRentCompanyHoliday':// 휴무일정보
					holidayListGrid(_rtIdx);
					break;
				default:
					loadApi(drawTable, null, null);
					break;
			}// end switch
			// }
		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			var errMsg = res.errMsg;
			errorAlert('저장 실패', errMsg);
		}
	});// end fn_callApi

}// end detailSubmit

/*
 * 스텝 그리드 onclick
 *
 */
function initStaffDetail(seq) {

	let target = 'rentCompanyStaffList';
	let method = 'select';

	let req = {
		rsIdx: seq
	};

	fn_callApi(method, target, req, function (response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code == 200) {

			let data = res.result[0];

			// $('#staffName').val(data.staffName);
			$('#staffContact1').val(data.staffContact1);
			$('#staffContact2').val(data.staffContact2);
			$('#staffEmail').val(data.staffEmail);
			$('#staffTitle').val(data.stafftitle);
			$('#ownerYn').val('');
			$('#rsIdx').val(data.rsIdx);

			let ownerYn = data.ownerYn;
			$("#sel_alarmYn option:selected").val();

			$("#sel_alarmYn").val(ownerYn).prop("selected", true);

			let staffTypeCode = data.staffTypeCode;

			$('#staffTypeCode').val('');
		}
	});// end fn_callApi
}

/*
 * 생성버튼 클릭시
 *
 */
function openCreateRentCompany() {
	$("#rtIdx").val('');

	CRUD = 'insert';
	// 변수 초기화
	$('#companyName').val(''); // 회사명
	$('#branchName').val(''); // 지저명
	$('#companyRegistrationName').val(''); // 법인명
	$('#companyRegistrationNumber').val(''); // 사업자등록번호
	$('#companyAddress').val(''); // 주소
	$('#accountHolder').val(''); // 예금주
	$('#accountNumber').val(''); // 계좌번호
	$('#carCount').val('');
	$('#companyContact1').val('');
//	$('#taxInvoiceCode').val('');

	// 스텝 초기화
	MCRUD = 'insert';

	$("#rsIdx").val('');
	$("#staffurIdx").val('');
	$("#staffName").val('');
	$("#staffTitle").val('');
	$("#ownerYn").val('');
	$("#staffContact1").val('');
	$("#staffEmail").val('');

	// 특정시간 초기화
	RMCRUD = 'insert';

	// 휴무일정보
	HCRUD = 'insert';

	// 상세 내 그리드 초기화
	staffListGrid('%');
	rentReserveMinListGrid('%');
	holidayListGrid('%');

	initModalSelectBox(null);

}

$('#rentStaffList tbody > tr').click(function () {

	let str = ""
	let tdArr = new Array(); // 배열 선언
	let tr = $(this);
	if (tr.text() == '조회 결과가 없습니다.') {
		return;
	}

	MCRUD = 'modify';
	// $("#btnstaffEmail").attr("disabled", false);

	let td = tr.children();

	let rsIdx = td.eq(1).text();
	let satffName = td.eq(2).text();
	let staffContact1 = td.eq(3).text();
	let staffEmail = td.eq(4).text();
	let staffTitle = td.eq(5).text();
	let ownerYn = td.eq(6).text();
	let satffurIdx = td.eq(7).text();
	let staffdelYn = td.eq(8).text();

	$("#staffurIdx").val(satffurIdx);
	$("#rsIdx").val(rsIdx);
	$("#staffName").val(satffName);
	$("#staffTitle").val(staffTitle);
	$("#ownerYn").val(ownerYn);
	$("#staffContact1").val(staffContact1);
	$("#staffEmail").val(staffEmail);
//	alert(rsIdx+'<>'+satffName+'<>'+staffContact1+'<>'+staffEmail+'<>'+staffTitle+'<>'+ownerYn+'<>'+satffurIdx);

	if (!isEmpty(ownerYn)) {
		if (ownerYn == "예") {
			$("#sel_ownerYn").val("Y").prop("selected", true);
		} else {
			$("#sel_ownerYn").val("N").prop("selected", true);
		}
	} else {
		$("#sel_ownerYn").val("N").prop("selected", true);
	}


});

$('#rentReserveMinList').on("click", "tbody tr ", function () {

	let str = ""
	let tdArr = new Array(); // 배열 선언
	let tr = $(this);
	if (tr.text() == '조회 결과가 없습니다.') {
		return;
	}

	RMCRUD = 'modify';

	let td = tr.children();

	let minIdx = td.eq(1).text();
	let minimumStartDt = td.eq(2).text();
	let minimumEndDt = td.eq(3).text();
	let minimumTime = td.eq(4).text();
	let mindelYn = td.eq(5).text();


	initDatePicker('minimumStartDt',minimumStartDt);
	initDatePicker('minimumEndDt',minimumEndDt);
	$("#minimumTime").val(minimumTime);
	$("#sel_minimumTime").val(minimumTime).prop("selected", true);

	if (isEmpty(mindelYn)) {
		$("#sel_mindelYn").val("N").prop("selected", true);
	} else {
		$("#sel_mindelYn").val(mindelYn).prop("selected", true);
	}

});

$('#holidayList').on("click", "tbody tr ", function () {

	let str = ""
	let tdArr = new Array(); // 배열 선언
	let tr = $(this);
	if (tr.text() == '조회 결과가 없습니다.') {
		return;
	}

	HCRUD = 'modify';

	let td = tr.children();

	let holIdx = td.eq(1).text();
	let holidayStartDt = td.eq(2).text();
	let holidayEndDt = td.eq(3).text();
	let holidayName = td.eq(4).text();

	$("#holIdx").val(holIdx);
	initDatePicker('holidayStartDt' , holidayStartDt);
	initDatePicker('holidayEndDt' , holidayEndDt);
	$("#holidayName").val(holidayName);


});

/*
* 대여위치, 반납위치 선택 */
function selectLocation(type) {

	var req = {};

	var target = 'addressDivision';
	var method = 'select';

	fn_callApi(method, target, req, function (response) {

		var data = response;

		var strOption = "";
		for ( var i=0; i<data.length; i++ ) {
			let addDo = data[i].addDo;

			strOption += "<option value = '" + addDo + "'>" + addDo + "</option>";
		}
		$('select[name=sel_addDo]').empty();
		$('select[name=sel_addDo]').append(strOption);
	});

	$('#selectedLocationTable tbody tr').empty();

	$('select[name=sel_addSi]').empty();
	$('select[name=sel_addDong]').empty();

	if(type === 'S'){
		$('#selectLocationModal').modal('show');
	}else if (type === 'L'){
		$('#selectLocationModal2').modal('show');

	}
}


function selectLocationDetail(type) {

	var req = {};
	var addLi;

	if (type === 'selectDo'){

		$('select[id=sel_addDong]').empty();
		$('select[id=sel_addLi]').empty();

		req = {
			addDo: $('select[id=sel_addDo]').val()
		}
	}else if(type === 'selectSi'){

		$('select[id=sel_addLi]').empty();

		req = {
			addSi: $('select[id=sel_addSi]').val()
		}
	}else if(type === 'selectDong'){
		req = {
			addDong: $('select[id=sel_addDong]').val()
		}
	}else if (type === 'selectDo2'){

		$('select[id=sel_addDong2]').empty();
		$('select[id=sel_addLi2]').empty();

		req = {
			addDo: $('select[id=sel_addDo2]').val()
		}
	}else if(type === 'selectSi2'){

		$('select[id=sel_addLi2]').empty();

		req = {
			addSi: $('select[id=sel_addSi2]').val()
		}
	}else if(type === 'selectDong2'){
		req = {
			addDong: $('select[id=sel_addDong2]').val()
		}
	}

	var target = 'addressDetailDivision';
	var method = 'select';

	fn_callApi(method, target, req, function (response) {

		var data = response;

		var strOption = "";
		for ( var i=0; i<data.length; i++ ) {
			var addressDivision;

			if(type === 'selectDo' || type === 'selectDo2'){
				addressDivision = data[i].addSi === '' ? '전체' : data[i].addSi;
			}else if (type === 'selectSi' || type === 'selectSi2'){
				addressDivision = data[i].addDong === ''? '전체' : data[i].addDong;
			}else if (type === 'selectDong' || type === 'selectDong2'){
				addressDivision = data[i].addLi === '' ? '전체' : data[i].addLi;
			}

			strOption += "<option value = '" + addressDivision + "'>" + addressDivision + "</option>";
		}

		if(type === 'selectDo'){
			$('select[id=sel_addSi]').empty();
			$('select[id=sel_addSi]').append(strOption);
		}else if(type === 'selectSi'){
			$('select[id=sel_addDong]').empty();
			$('select[id=sel_addDong]').append(strOption);
		}else if(type === 'selectDong'){
			$('select[id=sel_addLi]').empty();
			$('select[id=sel_addLi]').append(strOption);
		}else if(type === 'selectDo2'){
			$('select[id=sel_addSi2]').empty();
			$('select[id=sel_addSi2]').append(strOption);
		}else if(type === 'selectSi2'){
			$('select[id=sel_addDong2]').empty();
			$('select[id=sel_addDong2]').append(strOption);
		}else if(type === 'selectDong2'){
			$('select[id=sel_addLi2]').empty();
			$('select[id=sel_addLi2]').append(strOption);
		}
	});

}

function saveDeliveryLocation(type){
	var req = [];
	var size = $('#selectedLocationTable > tbody')[0].children.length;
	var deliveryLocation = $('#selectedLocationTable tbody tr');

	for (var i = 0 ; i < size; i++) {
		var selectedText = deliveryLocation.eq(i).text();
		selectedText = selectedText.substr(0, selectedText.length - 1);
		var list = selectedText.split(' ');

		var addDo = list[0];
		var addSi = list[1];
		var addDong = list[2];
		var addLi = list[3];
		var fullLocation = selectedText;


		var data = {
			rtIdx: $('#rtIdx').val().trim(),
			addrDepth1: addDo,
			addrDepth2: addSi,
			addrDepth3: addDong,
			addrDepth4: addLi,
			raGbnCode: type,
			regId: getLoginUser().urIdx,
			regDt: today
			// raGbnLt:,
			// raGbnSt:,
		};

		req.push(data);
	}

	var target = 'insertDcRentCompanyAblearea';
	var method = 'insert';

	fn_callApi(method, target, req, function (response) {

		var data = response;
		if (data.code === 200){
			swal("저장 성공", {icon : "success"});

			if (type === 'S'){
				$('#selectLocationModal').modal('hide');
			}else if(type === 'L'){
				$('#selectLocationModal2').modal('hide');
			}

		}else {
			errorAlert('저장 실패', '관리자에게 문의하세요.');

		}

	});

}

// input box auto hypen
$("input#staffContact1").click(function () {

	let num = $("#staffContact1").val();
	num = getOnlyNumber(num);

	$("#staffContact1").val(num);
});

$("input#staffContact1").blur(function () {

	var num = $("#staffContact1").val();

	autoHyphenFromNumber('phone', 'staffContact1', num);

});


$("input#staffEmail").keyup(function (e) {
	if ($("input#staffEmail").val().length >= 1) {
		// $("#btnstaffEmail").attr("disabled", false);
	} else {
		// $("#btnstaffEmail").attr("disabled", true);
	}
});

// input box auto hypen
$("input#companyContact1").click(function () {
	let num = $("#companyContact1").val();
	num = getOnlyNumber(num);

	$("#companyContact1").val(num);
});


/*
 * 입력값을 취소합니다.
 * */
function cancelData(cancel_type) {
	switch (cancel_type) {
		case 'saveRentCompanyInfo':// 회원사정보
			break;

		case 'saveStaff':// 회원사정보 - 관리자정보
			MCRUD = 'insert';
			$("#rsIdx").val('');
			$("#staffurIdx").val('');
			$("#staffName").val('');
			$("#staffTitle").val('');
			$("#ownerYn").val('');
			$("#staffContact1").val('');
			$("#staffEmail").val('');
			// $("#btnstaffEmail").attr("disabled", true);

			break;
		case 'updateCommission': // 수수료 정보 업데이트

			break;
		case 'saveRentCompanyTime':// 회원사정보 - 운영, 배달 정보

			$("#sel_weekdayOpenStart").val('');
			$("#sel_weekdayOpenEnd").val('');
			$("#sel_weekendOpenStart").val('');
			$("#sel_weekendOpenEnd").val('');
			$("#sel_weekdayDeliveryStart").val('');
			$("#sel_weekdayDeliveryEnd").val('');
			$("#sel_weekendDeliveryStart").val('');
			$("#sel_weekendDeliveryEnd").val('');

			$("#sel_weekdayAbleDeliveryTime").val('0');
			$("#sel_weekendAbleDeliveryTime").val('0');
			$("#sel_returnInspectionTime").val('0');
			$("#sel_weekendReserveMinimumTime").val('0');

			$("#weekendReserveMinimumRate").val('');
			break;

		case 'saveRentCompanyMin': // 특정시간 정보 업데이트
			RMCRUD = 'insert';
			$("#minIdx").val('');
			$("#minimumStartDt").val('');
			$("#minimumEndDt").val('');
			$("#sel_minimumTime").val('0');
			$("#sel_mindelYn").val('N');
			break;

		case 'saveRentCompanyHoliday': // 휴무일 정보 업데이트
			HCRUD = 'insert';
			$("#holIdx").val('');
			$("#holidayStartDt").val('');
			$("#holidayEndDt").val('');
			$("#holidayName").val('');
			break;
	}

}

/*
 * 회원검색(회원id검색)
 */
function searchUserInfo() {
	let staffEmail = $("#staffEmail").val();

	let req = {
		userId: staffEmail
	};

	let target = 'userInfoListDetail';
	let method = 'select';

	// Detail정보 조회
	fn_callApi(method, target, req, function (response) {
		let res = response;

		// 200이라면 페이징을 구한다.
		if (res.code === 200) {
			var data = res.result[0];
			if (isEmpty(data)) {
				errorAlert('회원정보', '회원정보가 존재하지 않습니다.');
				cancelData('saveStaff');
			} else {
				// 기본정보 셋팅
				let urIdx = data.urIdx;
				let userId = data.userId;
				let userName = data.userName;
				let userContact1 = phoneFomatter(data.userContact1);

				$("#staffurIdx").val(urIdx);
				$("#staffEmail").val(userId);
				$("#staffName").val(userName);
				$("#staffContact1").val(userContact1);
				// $("#btnstaffEmail").attr("disabled", true);
				swal("회원정보 조회 완료", {icon: "success"});
			}

		} else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
			errorAlert('API ERROR', '전체 메일을 입력 후 검색을 해야합니다.');
		}
	});// end fn_callApi
}

function movieMenu(goMenu) {
	$("#" + MODAL_NAME).iziModal('close');
	switch (goMenu) {
		case 'calculate':	// 정산관리
			opener.location.href = '/settlement';
			window.close();
			// GLOBAL_LINK_RTIDX = "";
			// left_location('/static/viewContents/calculate/calculate.html', '정산', 'link');
			break;
		case 'regCar':		// 등록차량
			opener.location.href = '/car';
			window.close();
			// GLOBAL_LINK_RTIDX = $('#rtIdx').val();
			// left_location('/static/viewContents/car/regCar.html', '등록차량', 'link');
			break;
		case 'reserveMnt':		// 예약내역
			opener.location.href = '/reserve';
			window.close();
			// GLOBAL_LINK_RTIDX = $('#rtIdx').val();
			// left_location('/static/viewContents/reservation/reservation_list.html', '예약관리', 'link');
			break;
		case 'review':
			swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
			break;
	}
}

/*
 * input box 초기화
 */
function initInput() {

	// input box 초기화
	$("#" + MODAL_NAME).find('input:text').each(function () {
		$(this).val('');
	});
	// input 태그의 자동완성 기능 해제
	$("#" + MODAL_NAME).find('input:text').each(function () {
		$(this).attr('autocomplete', 'off');
	});
	$("#" + MODAL_NAME).find('select').each(function () {
		$(this).find('option').eq(0).prop('selected', true);
	});

}

var chkValDate = function (date) {
	if (date.length == 8) {
		return date.match(/[0-9]{8}/g);
	} else if (date.length == 10) {
		return date.match(/[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}/g);
	}
};

function setDeliveryLocation(ragbncode) {
	let _rtIdx = $('#rtIdx').val();
	let companyAddress = "";
	if (!isEmpty($('#companyAddressDetail').val())) {
		companyAddress = $('#companyAddress').val() + ' ' + $('#companyAddressDetail').val();
	} else {
		companyAddress = $('#companyAddress').val();
	}
	sessionStorage.setItem('companyAddress', companyAddress);
	sessionStorage.setItem('raGbnCode', ragbncode);
	sessionStorage.setItem('rtIdx', _rtIdx);
	sessionStorage.setItem('adminidx', GLOBAL_LOGIN_USER_IDX);

	var url = "/static/viewContents/member/rentCompanydaumPolygon.html";
	var name = "popup test";
	var option = "width = 1024, height = 768, top = 100, left = 200, location = no"
	window.open(url, name, option);

}

function companyRegistrationAutoHyphen() {
	let num = $("#companyRegistrationNumber").val();

	autoHyphenFromNumber('companyRegistration', 'companyRegistrationNumber', num);
}

// 댓글 등록
function comment() {

	var commentMsg = $('#commentMsg').val().trim();

	if (isEmpty(commentMsg)){
		errorAlert('댓글', '댓글을 입력해주세요.');
		$('#commentMsg').focus();
	}

	var url = '/api/v1.0/insertComment.json';

	var req = {
		rtIdx : getLoginUser().rtIdx,
		commentMsg : commentMsg,
		commentPath : 'rentCompany',
		regId : getLoginUser().urIdx
	};

	$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(req),
		contentType: 'application/json',
		cache: false,
		async : false,
		timeout: 10000
	}).done(function (data, textStatus, jqXHR) {

		if (data.res === 1){
			swal("댓글 등록 성공", {icon : "success"});
		}
	})
}
/* =========================== detail function end ====================================== */