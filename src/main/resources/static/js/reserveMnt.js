/*
 * reserveMnt.js

 * 예약 > 예약관리
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

// 검색 SELECT BOX LIST

var CURRENT_PAGE = 0;
var carListData = [];		// 차량 select box 배열
var CRUD = '';		// 저장 구분자
var CALCULABLE_MOTH = 'N';	// 요금계산 적용 여부
var RESERVE_STATUS = '';	// 상세 화면 진입시 대여 상태 
var BROWSEYN = "";			// 브라우저에 따른 대여일시, 반납일시 입력 input 타입 변경 및 변수 값 처리

var GLOBAL_LOGIN_USER_IDX;
var GLOBAL_LOGIN_USER_ROLE;

var today = new Date();

function initializingPageData(){
    loadApi(drawTable, null, null );
    initSearchForm();
    bindEvent();

    /*
     * IE 브라우저인경우 data-mask="____-__-__ __:__" 사용
     * 이외 부라우저인 경우 type="datetime-local" 사용
     */
    var browse = navigator.userAgent.toLowerCase();
    if( (navigator.appName == 'Netscape' && browse.indexOf('trident') != -1) || (browse.indexOf("msie") != -1)) {
        $("input[name=rentStartDay]").remove();
        $("input[name=rentEndDay]").remove();
        BROWSEYN = "N";
    }else{
        $("input[name=rentStartDay2]").remove();
        $("input[name=rentEndDay2]").remove();
        BROWSEYN = "Y";
    }
}

// 검색 조건 SELECT BOX 초기화
function initSearchForm(){

    /* 예약 상태 */
    let target = 'commonCodeInfo';
    let method = 'select';
    let req = {
        rtCode : "RSV",
        pCode  : "RST"
    };

    // 상태코드
    fn_callApi(method, target, req, function(response) {
        let data = response;
        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
        // 	let data = res.result;
        let ignoreCode = '';
        if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
            ignoreCode = ['IG', 'FN', 'RE', 'AP'];
        }else{
            ignoreCode = ['IG', 'FN', 'RE'];
        }


        let strOption = '<option value="" >선택</option>';
        for ( var i=0; i<data.length; i++ ) {
            if(!ignoreCode.contains(data[i].code)){
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        // 검색 예약 상태
        $('#gbnStatus').empty();
        $('#gbnStatus').append(strOption);
        // 상세 페이지 예약 상태
        $('#sel_reserveStatusCode').empty();
        $('#sel_reserveStatusCode').append(strOption);
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    let gbnDayOption = '<option value="">선택</option>';
    gbnDayOption += '<option value="T">24시간 미만 대여</option>';
    gbnDayOption += '<option value="D">일단위 대여</option>';
    gbnDayOption += '<option value="M">월단위 대여</option>';
    gbnDayOption += '<option value="Y">연단위 대여</option>';

    let gbnLocationOption = '<option value="">선택</option>';
    gbnLocationOption += '<option value="IL">내륙</option>';
    //gbnLocationOption += '<option value="IS">제주를 제외한 섬</option>';
    gbnLocationOption += '<option value="JJ">제주</option>';

    let gbnReserveOption = '<option value="">선택</option>';
    gbnReserveOption += '<option value="AD">관리자 예약</option>';
    gbnReserveOption += '<option value="QT">견적 예약</option>';
    gbnReserveOption += '<option value="UR">사용자 예약</option>';


    $('#gbnDay').empty();
    $('#gbnDay').append(gbnDayOption);

    $('#gbnLocation').empty();
    $('#gbnLocation').append(gbnLocationOption);

    $('#gbnReserve').empty();
    $('#gbnReserve').append(gbnReserveOption);

    $('#sel_reserveTypeCode').empty();
    $('#sel_reserveTypeCode').append(gbnReserveOption);

    let searchOption = '';
    searchOption += '<option value="" >선택</option>';
    searchOption += '<option value="RM" >예약번호</option>';
    searchOption += '<option value="CT" >연락처</option>';
    searchOption += '<option value="MD" >모델</option>';
    searchOption += '<option value="CR" >차량번호</option>';
    searchOption += '<option value="CM" >회원사</option>';
    searchOption += '<option value="DL" >대여방법</option>';

    let countOption = '';
    countOption += '<option selected value="10" >10개씩 보기</option>';
    countOption += '<option value="20" >20개씩 보기</option>';
    countOption += '<option value="30" >30개씩 보기</option>';
    countOption += '<option value="60" >60개씩 보기</option>';

    $('#showContents').append(countOption);
    $('#searchSelectBox').append(searchOption);
}

function loadApi(fnc, page, displayPageNum, division){

    let gbnStatus = $("#gbnStatus option:selected").val();
    let gbnDay = $("#gbnDay option:selected").val();
    let gbnLocation = $("#gbnLocation option:selected").val();
    let gbnReserve = $("#gbnReserve option:selected").val();

    var loginUser = getLoginUser();
    GLOBAL_LOGIN_USER_IDX = loginUser.urIdx;
    GLOBAL_LOGIN_USER_ROLE = loginUser.userRole;

    let _rtIdx = '';

    if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
        _rtIdx = loginUser.rtIdx;
    }

    // 회원사 링크 버튼 클릭 후 변수 사용
    if(!isEmpty(GLOBAL_LINK_RTIDX)){
        _rtIdx = GLOBAL_LINK_RTIDX;
        GLOBAL_LINK_RTIDX = "";
    }

    var req = {
        'page': CURRENT_PAGE,
        'rtIdx' : _rtIdx,
        'gbnStatus' : gbnStatus,
        'gbnDay' : gbnDay,
        'gbnLocation' : gbnLocation,
        'gbnReserve' : gbnReserve
    };

    var target = 'reserveInfoList';
    var method = 'select';

    fn_callApi(method, target, req, function (response) {
        var res = response;

        //200이라면 페이징을 구한다.
        // if(res.code == 200) {
        fnc(res.result, page, displayPageNum);
        // }else { //200이 아닐때 empty처리 error처리 등을 기록한다.
        //  errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });//end

}

/*
 * 예약관리 리스트
 */
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
                return '<a href="javascript:initDetailInfo(' + "'" +value + "'" +');"  >'+value+'</a>';
            }
        },
        { "name": "flag", "title": "구분", "breakpoints": "xs" },
        { "name": "reserveDate", "title": "예약일시" , "breakpoints": "xs"},
        { "name": "reserveStatusName", "title": "상태", "breakpoints": "xs",
            "formatter" : function(value, options, rowData){
                let displayText = nullCheck(value) == '' ? '<font color="red">미확인</font>' : value;
                return displayText;
            }
        },
        { "name": "userName", "title": "회원</br>연락처", "breakpoints": "xs",
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
        { "name": "modelName", "title": "모델<br/>차량번호", "breakpoints": "xs",
            "formatter" : function(value, options, rowData){
                let displayText = '';
                let rowIndex = String((rowData.rowNumber)-1);
                rowIndex = rowIndex.charAt(rowIndex.length-1);
                let targetRowData = options.rows[rowIndex];

                displayText = '<font color="blue">' + nullCheck(value) + '</font><br/>';
                displayText += nullCheck(targetRowData.modelDetailName) + ' | <font color="red">' + nullCheck(targetRowData.fuelName) + '</font>';
                displayText += '<br/>';
                displayText += nullCheck(targetRowData.carNumber);
                return displayText;
            }
        },
        { "name": "rentStartDay", "title": "대여일시<br/>반납일시", "breakpoints": "xs sm",
            "formatter" : function(value, options, rowData){
                let displayText = '';
                let rowIndex = String((rowData.rowNumber)-1);
                rowIndex = rowIndex.charAt(rowIndex.length-1);
                let targetRowData = options.rows[rowIndex];

                displayText += nullCheck(targetRowData.rentStartDay);
                displayText += '<br/>';
                displayText += nullCheck(targetRowData.rentEndDay);
                return displayText;
            }
        },
        { "name": "periodDt", "title": "사용기간", "breakpoints": "xs sm md"},
        { "name": "companyName", "title": "회원사<br/>지점", "breakpoints": "xs",
            "formatter" : function(value, options, rowData){
                let displayText = '';
                let rowIndex = String((rowData.rowNumber)-1);
                rowIndex = rowIndex.charAt(rowIndex.length-1);
                let targetRowData = options.rows[rowIndex];

                displayText += nullCheck(targetRowData.companyName);
                displayText += '<br/>';
                displayText += nullCheck(targetRowData.branchName);
                return displayText;
            }
        },
        { "name": "deliveryTypeName", "title": "대여방법", "breakpoints": "xs"},
        { "name": "paymentTotalAmount", "title": "총금액", "breakpoints": "xs sm md",
            "formatter" : function(value, options, rowData){
                let displayText = objectConvertToPriceFormat(nullCheck(value));
                return displayText + '원';
            }
        },
        { "name": "paymentAmount", "title": "결제금액", "breakpoints": "xs sm md" ,
            "formatter" : function(value, options, rowData){
                let displayText = objectConvertToPriceFormat(nullCheck(value));
                return displayText+ '원';
            }
        }
    ];

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



}

/*
 * 결제정보 리스트
 */
function drawData(paymentList){
    var payment_list = paymentList;
    var columns;
    columns = [

        { "name": "paymentTypeName", "title": "결제수단" },
        { "name": "paymentKindName", "title": "결제구분" },
        { "name": "paymentDate",  "title": "결제일시" },
        { "name": "paymentAmount", "title": "결제금액" ,
            "formatter" : function(value, options, rowData){
                var displayText = objectConvertToPriceFormat(nullCheck(value));
                return displayText;
            }
        },
        { "name": "", "title": "정산금액", "breakpoints": "xs"},
        { "name": "", "title": "정산예정일", "breakpoints": "xs"},
        { "name": "", "title": "정산완료일시", "breakpoints": "xs"}
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
        "rows": payment_list
    });
}

/*
 * 모아보기 선택시
 */
function changeList() {
    loadApi(drawTable, null, null);
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
// LIST END ============================================================================================================================================================================

/*
 * 상세페이지 정보 셋팅
 */
function initDetailInfo(seq){

    var loginUser = getLoginUser();
    GLOBAL_LOGIN_USER_IDX = loginUser.urIdx;
    GLOBAL_LOGIN_USER_ROLE = loginUser.userRole;

    var browse = navigator.userAgent.toLowerCase();
    if( (navigator.appName == 'Netscape' && browse.indexOf('trident') != -1) || (browse.indexOf("msie") != -1)) {
        $("input[name=rentStartDay]").remove();
        $("input[name=rentEndDay]").remove();
        BROWSEYN = "N";
    }else{
        $("input[name=rentStartDay2]").remove();
        $("input[name=rentEndDay2]").remove();
        BROWSEYN = "Y";
    }

    let _rmIdx = seq;

    let req = {
        rmIdx : _rmIdx
    };

    let target = 'reserveInfo';
    let method = 'select';

    // Detail정보 조회
    fn_callApi(method,target, req, function(res){
        // if( res.code == 200 ){
        let data = res.result[0];
        let paymentList = res.paymentList;
        let dataSet = new Object();
        dataSet.reserveInfo = data;
        dataSet.paymentList = paymentList;

        /*
         * 회원사 select box
         * 연료  select box
         * 면허종류  select box
         * 예약 상태 select box
         * 차량별차종 select box
         */
        initDetailSelectBox(data);

        let urIdx = nullCheck(data.urIdx);
        let reserveUserName = nullCheck(data.reserveUserName);
        let reserveUserEmail = nullCheck(data.reserveUserEmail);
        let reserveUserContact1 = nullCheck(data.reserveUserContact1);
        let reserveUserBirthday =  nullCheck(data.reserveUserBirthday) != '' ? data.reserveUserBirthday : new Date();

        // 예약자 정보
        $("#urIdx").val(urIdx);
        $("#reserveUserName").val(reserveUserName);
        $("#reserveUserId").val(reserveUserEmail);
        $("#reserveUserContact1").val(reserveUserContact1);
        initDatePicker('reserveUserBirthday' , reserveUserBirthday);

        // 운전자 정보
//			let userFlag = nullCheck(data.;
        // 제1 운전자 정보
        let ulIdx1 = nullCheck(data.ulIdx1);
        let firstDriverName = nullCheck(data.firstDriverName);
        let firstDriverContact = nullCheck(data.firstDriverContact);
        let firstDriverBirthDay =  nullCheck(data.firstDriverBirthDay) != '' ? data.firstDriverBirthDay : new Date();;
        let firstDriverLicenseCode = nullCheck(data.firstDriverLicenseCode);
        let firstDriverLicenseNumber = nullCheck(data.firstDriverLicenseNumber);
        let firstDriverExpirationDate = nullCheck(data.firstDriverExpirationDate) == ''?'':dateFormatter(data.firstDriverExpirationDate);
        let firstDriverLicenseIsDate = nullCheck(data.firstDriverLicenseIsDate) == ''?'':dateFormatter(data.firstDriverLicenseIsDate);

        $("#ulIdx1").val(ulIdx1);
        $("#firstDriverName").val(firstDriverName);
        $("#firstDriverContact").val(firstDriverContact);
        initDatePicker('firstDriverBirthDay',firstDriverBirthDay);
        $("#sel_firstDriverLicenseCode").val(firstDriverLicenseCode);
        $("#firstDriverLicenseNumber").val(firstDriverLicenseNumber);
        initDatePicker('firstDriverExpirationDate',firstDriverExpirationDate);
        initDatePicker('firstDriverLicenseIsDate',firstDriverLicenseIsDate);

        // 제2 운전자 정보
        let ulIdx2 = nullCheck(data.ulIdx2);
        let secondDriverName = nullCheck(data.secondDriverName);
        let secondDriverContact = nullCheck(data.secondDriverContact);
        let secondDriverBirthDay = nullCheck(data.secondDriverBirthDay) != '' ? data.secondDriverBirthDay : new Date();
        let secondDriverLicenseCode = nullCheck(data.secondDriverLicenseCode);
        let secondDriverLicenseNumber = nullCheck(data.secondDriverLicenseNumber);
        let secondDriverExpirationDate = nullCheck(data.secondDriverExpirationDate) == ''?'':dateFormatter(data.secondDriverExpirationDate);
        let secondDriverLicenseIsDate = nullCheck(data.secondDriverLicenseIsDate) == ''?'':dateFormatter(data.secondDriverLicenseIsDate);

        $("#ulIdx2").val(ulIdx2);
        $("#secondDriverName").val(secondDriverName);
        $("#secondDriverContact").val(secondDriverContact);
        $("#secondDriverLicenseCode").val(secondDriverLicenseCode);
        initDatePicker('secondDriverBirthDay',secondDriverBirthDay);
        initDatePicker('secondDriverExpirationDate',secondDriverExpirationDate);
        initDatePicker('secondDriverLicenseIsDate',secondDriverLicenseIsDate);
        $("#secondDriverLicenseNumber").val(secondDriverLicenseNumber);

        // 예약정보
        let rmIdx = nullCheck(data.rmIdx);
        let reserveYmdt = nullCheck(data.reserveYmdt);
        let reserveChannel = nullCheck(data.reserveChannel);
        let landCode = nullCheck(data.landCode);
        let reserveDate = nullCheck(data.reserveDate);
        let reserveStatusCode = nullCheck(data.reserveStatusCode);
        let reserveTypeCode = nullCheck(data.reserveTypeCode);
        let deliveryTypeCode = nullCheck(data.deliveryTypeCode);
        let rentStartDay = dateFormatter(nullCheck(data.rentStartDay));
        let rentEndDay = dateFormatter(nullCheck(data.rentEndDay));
        let periodDt = nullCheck(data.periodDt);
        let deliveryAddr = nullCheck(data.deliveryAddr);
        let returnAddr = nullCheck(data.returnAddr);

        let gbn = reserveYmdt+reserveChannel+landCode;

        RESERVE_STATUS = reserveStatusCode;

        $("#rmIdx").val(rmIdx);
        $("#gbn").val(gbn);
        $("#reserveDate").val(reserveDate);
        $("#sel_reserveTypeCode").val(reserveTypeCode);
        $("#sel_reserveStatusCode").val(reserveStatusCode);

        if(deliveryTypeCode === 'OF'){
            deliveryTypeCode = '지점방문';
        }else if (deliveryTypeCode === 'DL'){
            deliveryTypeCode = '배달대여';
        }
        $("#sel_deliveryTypeCode").val(deliveryTypeCode);
        $("#rentStartDay").val(rentStartDay);

        $("#rentEndDay").val(rentEndDay);
        $("#periodDt").val(periodDt);
        $("#deliveryAddr").val(deliveryAddr);
        $("#returnAddr").val(returnAddr);

        if(reserveTypeCode == 'QT'){
            $("#rentcalAmt").hide();
        }else{
            $("#rentcalAmt").show();
        }
        // 회원사, 차량정보
        let rtIdx = nullCheck(data.rtIdx);
        let staffName = nullCheck(data.staffName);
        let staffContact1 = nullCheck(data.staffContact1);
        let crIdx = nullCheck(data.crIdx);
        let mdIdx = nullCheck(data.mdIdx);
        let onselfDamageCover = nullCheck(data.onselfDamageCover) == ''?'':objectConvertToPriceFormat(data.onselfDamageCover);
        let personalCover = nullCheck(data.personalCover) == ''?'':objectConvertToPriceFormat(data.personalCover);
        let propertyDamageCover = nullCheck(data.propertyDamageCover) == ''?'':objectConvertToPriceFormat(data.propertyDamageCover);
        let carDamageCover = nullCheck(data.carDamageCover) == ''?'':objectConvertToPriceFormat(data.carDamageCover);
        let insuranceCopayment = nullCheck(data.insuranceCopayment) == ''?'':objectConvertToPriceFormat(data.insuranceCopayment);
        let fuelCode = nullCheck(data.fuelCode);

        $("#rtIdx").val(rtIdx);
        $("#staffName").val(staffName);
        $("#staffContact1").val(staffContact1);
        $("#sel_modelName").val(crIdx).prop("selected", true);
        $("#mdIdx").val(mdIdx);
        $("#onselfDamageCover").val(onselfDamageCover);
        $("#personalCover").val(personalCover);
        $("#propertyDamageCover").val(propertyDamageCover);

        $("#carDamageCover").val(carDamageCover); // 예약시 면책금
        $("#insuranceCopayment").val(insuranceCopayment); // 예약시 보험료

        $("#sel_fuel").val(fuelCode).prop("selected", true);

        // 대여금액 정보
        let rentFee = nullCheck(data.rentFee) == ''?'':objectConvertToPriceFormat(data.rentFee);
        let insuranceFee = nullCheck(data.insuranceFee) == ''?'':objectConvertToPriceFormat(data.insuranceFee);
        let discountFee = nullCheck(data.discountFee) == ''?'':objectConvertToPriceFormat(data.discountFee);
        let deliveryFee = nullCheck(data.deliveryFee) == ''?'':objectConvertToPriceFormat(data.deliveryFee);
        let addFee = nullCheck(data.addFee) == ''?'':objectConvertToPriceFormat(data.addFee);
        let paymentTotalAmount = nullCheck(data.paymentTotalAmount) == ''?'':objectConvertToPriceFormat(data.paymentTotalAmount);
        let paymentAmount = nullCheck(data.paymentAmount) == ''?'':objectConvertToPriceFormat(data.paymentAmount);

        $("#rentFee").val(rentFee);
        $("#insuranceFee").val(insuranceFee);
        $("#discountFee").val(discountFee);
        $("#deliveryFee").val(deliveryFee);
        $("#addFee").val(addFee);
        $("#paymentTotalAmount").val(paymentTotalAmount);
        $("#paymentAmount").val(paymentAmount);

        $("#reserveUserName").val(reserveUserName);
        $("#reserveUserEmail").val(reserveUserEmail);
        $("#btnreserveUserEmail").hide();
        $("#reserveUserContact1").val(reserveUserContact1);
        initDatePicker('reserveUserBirthday' , reserveUserBirthday);

        let refundFee = nullCheck(data.refundFee) == ''?'':objectConvertToPriceFormat(data.refundFee);
        let miSu = nullCheck(data.miSu) == ''?'':objectConvertToPriceFormat(data.miSu);
        $("#lbl_rentmisu").text(" 미납금액:"+miSu);
        $("#lbl_refundFee").text(" 환불금액:"+refundFee);

        settingInputStatus();

        drawData(paymentList);


    });
}

/*
 * 상세페이지 콤보박스 정보 가져오기
 */
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
        data = response;
        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
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
        $('#companyName').empty();
        $('#companyName').append(strOption);

        if(!isEmpty(_data)){
            let rtIdx = nullCheck(_data.rtIdx) == ''?'':_data.rtIdx;
            $("#companyName").val(rtIdx).prop("selected", true);
        }else{
            let _rtIdx = '';

            if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
                _rtIdx = getLoginUser().rtIdx;
            }

            if(!isEmpty(_rtIdx)){
                $('#companyName').val(_rtIdx).prop("selected", true);
                if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
                    $('#companyName').attr('disabled', true);
                }
            }else{
                $("#companyName").val('').prop("selected", true);
            }
        }

        let _crIdx = "";
        if(!isEmpty(_data)){
            _crIdx = nullCheck(_data.crIdx) == ''?'':_data.crIdx;
        }
        selectCompany($("#companyName option:selected").val(), _crIdx);
        //
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 연료  select box
    req = {
        rtCode:'CR',
        pCode: 'FL'
    };

    target = 'commonCodeInfo';
    method = 'select';

    fn_callApi(method, target, req, function (response) {
        data = response;

        // if (res.code == 200) {

        strOption = "";
        strOption += "<option value = '0'>선택하세요</option>";

        for ( let i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '"+ data[i].code + "'>"+ data[i].codeValue + "</option>";
            }
        }

        $('#sel_fuel').empty();
        $('#sel_fuel').append(strOption);

        if(!isEmpty(_data)){
            let fuelCode = nullCheck(_data.fuelCode) == ''?'0':_data.fuelCode; //연료구분code
            $("#sel_fuel").val(fuelCode).prop("selected", true);
        }else{
            $("#sel_fuel").val('0').prop("selected", true);
        }
        // }
    });

    // 면허종류  select box
    req = {
        rtCode:'CR',
        pCode: 'DL'
    };

    target = 'commonCodeInfo';
    method = 'select';

    fn_callApi(method, target, req, function (response) {
        data = response;

        // if (res.code == 200) {

        strOption = "";
        strOption += "<option value='0'>선택하세요</option>";

        for ( let i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '"+ data[i].code + "'>"+ data[i].codeValue + "</option>";
            }
        }

        $('#sel_firstDriverLicenseCode').empty();
        $('#sel_firstDriverLicenseCode').append(strOption);
        if(!isEmpty(_data)){
            let firstDriverLicenseCode = nullCheck(_data.firstDriverLicenseCode) == ''?'0':_data.firstDriverLicenseCode;
            $("#sel_firstDriverLicenseCode").val(firstDriverLicenseCode).prop("selected", true);
        }else{
            $("#sel_firstDriverLicenseCode").val('0').prop("selected", true);
        }

        $('#sel_secondDriverLicenseCode').empty();
        $('#sel_secondDriverLicenseCode').append(strOption);
        if(!isEmpty(_data)){
            let secondDriverLicenseCode = nullCheck(_data.secondDriverLicenseCode) == ''?'0':_data.secondDriverLicenseCode;
            $("#sel_secondDriverLicenseCode").val(secondDriverLicenseCode).prop("selected", true);
        }else{
            $("#sel_secondDriverLicenseCode").val('0').prop("selected", true);
        }
        // }
    });

    target = 'commonCodeInfo';
    method = 'select';

    // 예약 select box
    req = {
        rtCode : "RSV",
        pCode  : "RST"
    };

    fn_callApi(method, target, req, function(response) {
        let data = response;
        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
        // 	let data = res.result;
        let ignoreCode = '';
        if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
            ignoreCode = ['IG', 'FN', 'RE', 'AP'];
        }else{
            ignoreCode = ['IG', 'FN', 'RE'];
        }


        let strOption = '<option value="" >선택</option>';
        for ( var i=0; i<data.length; i++ ) {
            if(!ignoreCode.contains(data[i].code)){
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        // 검색 예약 상태
        $('#gbnStatus').empty();
        $('#gbnStatus').append(strOption);
        // 상세 페이지 예약 상태
        $('#sel_reserveStatusCode').empty();
        $('#sel_reserveStatusCode').append(strOption);
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 배달방법  select box
    req = {
        rtCode:'QT',
        pCode: 'QDC'
    };

    target = 'commonCodeInfo';
    method = 'select';

    fn_callApi(method, target, req, function (response) {
        data = response;

        // if (res.code == 200) {

        strOption = "";

        strOption += "<option value='0'>선택하세요</option>";

        for ( let i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '"+ data[i].code + "'>"+ data[i].codeValue + "</option>";
            }
        }

        $('#sel_deliveryTypeCode').empty();
        $('#sel_deliveryTypeCode').append(strOption);
        if(!isEmpty(_data)){
            let deliveryTypeCode = nullCheck(_data.deliveryTypeCode) == ''?'0':_data.deliveryTypeCode;
            $("#sel_deliveryTypeCode").val(deliveryTypeCode).prop("selected", true);
        }else{
            $("#sel_deliveryTypeCode").val('0').prop("selected", true);
        }
        // }
    });

}

/*
 * 회원사별 차량 리스트
 */
function selectCompany(rtIdx, crIdx){
    let rentStartDayValue = getPureText($("#rentStartDay").val());
    let rentStartDay = rentStartDayValue.replace('____-__-__ __:__','').replace('T',' ').replace(' ','');
    let rentEndDayValue = getPureText($("#rentEndDay").val());
    let rentEndDay = rentEndDayValue.replace('____-__-__ __:__','').replace('T',' ').replace(' ','');
    let rmIdx = getPureText($("#rmIdx").val());

    // if(isEmpty(rentStartDay)){
    // 	errorAlert('대여일시를 먼저 입력하여 주세요');
    // 	$("#companyName").val('').prop("selected", true);
    // 	return;
    // }else if(isEmpty(rentEndDay)){
    // 	errorAlert('반납일시를 먼저 입력하여 주세요');
    // 	$("#companyName").val('').prop("selected", true);
    // 	return;
    // }

    let target = 'selectCompanyInfoAndCarInfo';
    let method = 'select';
    let req = {
        'rtIdx' : rtIdx
        ,'rmIdx' : rmIdx
        ,'rentStartDay' : rentStartDay
        ,'rentEndDay' : rentEndDay
    };

    fn_callApi(method, target, req, function(response) {
        let res = response;
        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
        let companyInfo = res.companyInfo;
        if( companyInfo != undefined && companyInfo != null){
            let staffContact = nullCheck(companyInfo.staffContact1);
            let staffName = nullCheck(companyInfo.staffName);
            let companyAddress = nullCheck(companyInfo.companyAddress);
            let companyAddressDetail = nullCheck(companyInfo.companyAddressDetail);
            let companyAddr = companyAddress + ' ' + companyAddressDetail;
            $('#staffContact1').val('');
            $('#staffName').val('');

            $('#staffContact1').val(staffContact);
            $('#staffName').val(staffName);

            let deliveryTypeCode = $("#sel_deliveryTypeCode option:selected").val();
            // 배달 지점 방문인 경우 회원사 주소 셋팅
            if(deliveryTypeCode == 'OF'){
                $('#returnAddr').val(companyAddr);
                $('#deliveryAddr').val(companyAddr);
            }

        }

        let carList = res.carList;
        carListData = carList;

        let strOption = "<option value=''>선택</option>";
        for ( var i=0; i<carList.length; i++ ) {
            strOption += "<option value = '" + carList[i].crIdx + "'>" + carList[i].modelName + '('+carList[i].carNumber+')' + "</option>";
        }

        $('#sel_modelName').empty();
        $('#sel_modelName').append(strOption);
        if(!isEmpty(crIdx)){
            $("#sel_modelName").val(crIdx).prop("selected", true);
            selectCarInfo(crIdx);
        }
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

}

/*
 * 차량정보 가져오기
 */
function selectCarInfo(crIdx){
    let crIdxIndex = $("#sel_modelName option:selected").index() - 1;
    if(crIdx == null){
        crIdx = $("#sel_modelName option:selected").val();
    }

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

    $('#mdIdx').val(mdIdx);
    $("#sel_fuel").val(fuelCode).prop("selected", true);
    $('#year').val(year);
    $('#colorName').val(colorName);
    $('#mileage').val(mileage);
    $('#onselfDamageCover').val(onselfDamageCover);
    $('#personalCover').val(personalCover);
    $('#propertyDamageCover').val(propertyDamageCover);
    $('#carTypeCode').val(carTypeCode);
    $('#optionCodeValue').val(optionCodeValue);

    let carDamageCover = nullCheck(carListData[crIdxIndex].carDamageCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover);
    let insuranceCopayment = nullCheck(carListData[crIdxIndex].insuranceCopayment) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment);
    let carDamageCover2 = nullCheck(carListData[crIdxIndex].carDamageCover2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover2);
    let insuranceCopayment2 = nullCheck(carListData[crIdxIndex].insuranceCopayment2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment2);
    let carDamageCover3 = nullCheck(carListData[crIdxIndex].carDamageCover3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover3);
    let insuranceCopayment3 = nullCheck(carListData[crIdxIndex].insuranceCopayment3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment3);
    let carDamageCover4 = nullCheck(carListData[crIdxIndex].carDamageCover4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover4);
    let insuranceCopayment4 = nullCheck(carListData[crIdxIndex].insuranceCopayment4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment4);

    let strOption = "";
    strOption += "<option value = ''>선택하세요</option>";

    if(!isEmpty(carDamageCover) && !isEmpty(insuranceCopayment)){
        strOption += "<option value = '"+insuranceCopayment+"'>" + "면책금:" + carDamageCover + "/보험금:" + insuranceCopayment + "</option>";
    }
    if(!isEmpty(carDamageCover2) && !isEmpty(insuranceCopayment2)){
        strOption += "<option value = '"+insuranceCopayment2+"'>" + "면책금:" + carDamageCover2 + "/보험금:" + insuranceCopayment2 + "</option>";
    }
    if(!isEmpty(carDamageCover3) && !isEmpty(insuranceCopayment3)){
        strOption += "<option value = '"+insuranceCopayment3+"'>" + "면책금:" + carDamageCover3 + "/보험금:" + insuranceCopayment3 + "</option>";
    }
    if(!isEmpty(carDamageCover4) && !isEmpty(insuranceCopayment4)){
        strOption += "<option value = '"+insuranceCopayment4+"'>" + "면책금:" + carDamageCover4 + "/보험금:" + insuranceCopayment4 + "</option>";
    }

    $('#sel_ciIdx').empty();
    $('#sel_ciIdx').append(strOption);

    let revinsuranceCopayment = $("#insuranceCopayment").val();
    let revcarDamageCover = $("#carDamageCover").val();

    if((isEmpty(revinsuranceCopayment) || revinsuranceCopayment == '0') && !isEmpty(revcarDamageCover) && revcarDamageCover > '0'){
        if (carDamageCover == revcarDamageCover){
            revinsuranceCopayment = insuranceCopayment;
        }else if (carDamageCover2 == revcarDamageCover){
            revinsuranceCopayment = insuranceCopayment2;
        }else if (carDamageCover3 == revcarDamageCover){
            revinsuranceCopayment = insuranceCopayment3;
        }else if (carDamageCover4 == revcarDamageCover){
            revinsuranceCopayment = insuranceCopayment4;
        }
    }

    if(!isEmpty(revinsuranceCopayment) && revinsuranceCopayment != '0' && !isEmpty(crIdx) ){
        $("#sel_ciIdx").val(revinsuranceCopayment).prop("selected", true);
    }else{
        $("#sel_ciIdx").val('').prop("selected", true);
    }

}

/*
 * 회원검색(회원id검색)
 */
function serachUserInfo(flag, urIdx) {
    let reserveUserEmail = '';

    if(flag == 'init'){
        reserveUserEmail = $("#reserveUserEmail").val();
    }else if(flag == 'first'){
        reserveUserEmail = $("#firstDriverId").val();
    }else if(flag == 'second'){
        reserveUserEmail = $("#secondDriverId").val();
    }

    if(isEmpty(reserveUserEmail)){
        errorAlert('이메일', '회원 가입시 등록한 이메일을 입력하여 주세요');
        return;
    }

    let req = {
        userId : reserveUserEmail
    };

    let target = 'userInfoListDetail';
    let method = 'select';

    // Detail정보 조회
    fn_callApi(method,target, req, function(response) {
        let res = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
        var data = res.data.result;
        if(isEmpty(data)){
            errorAlert('회원정보', '회원정보가 존재하지 않습니다.');
            $("#urIdx").val('');
        }else{
            // 기본정보 셋팅
            let urIdx 			= data.urIdx;
            let userName 		= data.userName;
            let userContact1 	= phoneFomatter(data.userContact1);
            let userId			= data.userId;
            let userBirthday	= data.userBirthday;
            userBirthday 		= dateFormatter(userBirthday);

            if(flag == 'init'){
                $("#urIdx").val(urIdx);
                $("#reserveUserName").val(userName);
                $("#reserveUserContact1").val(userContact1);
                $("#reserveUserEmail").val(userId);
                initDatePicker('reserveUserBirthday' , reserveUserBirthday);
            }else if(flag == 'first'){
                $("#firstDriverId").val(userId);
                $("#firstDriverName").val(userName);
                $('#firstDriverContact').val(userContact1);
                initDatePicker('firstDriverBirthDay' , userBirthday);
                DriverSetting(flag, urIdx);
            }else if(flag == 'second'){
                $("#secondDriverId").val(userId);
                $("#secondDriverName").val(userName);
                $('#secondDriverContact').val(userContact1);
                $('#secondDriverBirthDay').val(userBirthday);
                DriverSetting(flag, urIdx);
            }

//					swal("회원정보 조회 완료", {icon : "success"});
        }

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi
}

/*
 * 에약자와 동일 체크박스 선택시 회원 정보 가져오기
 */
function firstDriverSetting(target){

    let flag = $("#"+target).is(':checked');

    if(flag){
        $('#firstDriverDiv').find('input').each(function(){
            $(this).val('');
        });
        let urIdx =  $('#urIdx').val();

        let name = $('#reserveUserName').val();
        let contact1 = $('#reserveUserContact1').val();
        let birthDay = formatDate(getDatePickerValue('reserveUserBirthDay'));

        $('#firstDriverName').val(name);
        $('#firstDriverContact').val(contact1);
        initDatePicker('firstDriverBirthDay' , birthDay);

        if(!isEmpty(urIdx)){
            DriverSetting('first', urIdx);
        }


    }else{
        $('#ulIdx1').val('');
        $('#firstDriverName').val('');
        $('#firstDriverContact').val('');
        $('#firstDriverBirthDay').val('');

        $('#sel_firstDriverLicenseCode').val('0').prop("selected", true);
        $('#firstDriverLicenseNumber').val('');
        $('#firstDriverExpirationDate').val('');
        $('#firstDriverLicenseIsDate').val('');
    }
}
/*
 * 회원인 경우 운전면허 정보 가져오기
 */
function DriverSetting(flag, urIdx){

    let req = {
        urIdx : urIdx
    };

    let target = 'userLicenseInfo';
    let method = 'select';
    // Detail정보 조회
    fn_callApi(method,target, req, function(response) {
        let res = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        var data = res.result[0];

        let licenseCode  		= '';// 면허코드
        let licenseNumber  		= '';// 운전면허 번호
        let licenseExpiration  	= '';// 적성검사만료일
        let licenseIssueDt  	= '';// 면허 발급일
        let userLicenseOwnYn 	= '';
        let licenseLocation     = '';// 면허지역
        let useYn				= '';// 사용여부

        if(!isEmpty(data)) {
            ulIdx  				= data.ulIdx;
            licenseCode  		= data.licenseCode;
            licenseNumber  		= data.licenseNumber;
            licenseExpiration  	= dateFormatter(data.licenseExpiration);
            licenseIssueDt  	= dateFormatter(data.licenseIssueDt);
            userLicenseOwnYn 	= data.userLicenseOwnYn;
            licenseLocation     = data.licenseLocation;

            if(flag == 'first'){
                $('#ulIdx1').val(ulIdx);
                $('#sel_firstDriverLicenseCode').val(licenseCode);
                $('#firstDriverLicenseNumber').val(licenseNumber);
                $('#firstDriverExpirationDate').val(licenseExpiration);
                $('#firstDriverLicenseIsDate').val(licenseIssueDt);
            }else if(flag == 'second'){
                $('#ulIdx2').val(ulIdx);
                $('#sel_secondDriverLicenseCode').val(licenseCode);
                $('#secondDriverLicenseNumber').val(licenseNumber);
                $('#secondDriverExpirationDate').val(licenseExpiration);
                $('#secondDriverLicenseIsDate').val(licenseIssueDt);
            }
        }

        // }
    });

}

/*
 * 요금 계산
 */
function rentcal(){

    let crIdx = $("#sel_modelName option:selected").val();
    let calRentStartDt = $("#rentStartDay").val().replace('____-__-__ __:__','').replace('T',' ');
    let calRentEndDt = $("#rentEndDay").val().replace('____-__-__ __:__','').replace('T',' ');
    let selInsuranceFee = $("#sel_ciIdx option:selected").val().replace(',','');

    if(isEmpty(calRentStartDt)){
        errorAlert('요금계산', '대여일시를 입력해 주세요.');
        return;
    }
    if(isEmpty(calRentEndDt)){
        errorAlert('요금계산', '반납일시를 입력해 주세요.');
        return;
    }
    if(calDate(getPureText(calRentStartDt), getPureText(calRentEndDt)) == 0){
        if(isEmpty(selInsuranceFee)){
            errorAlert('요금계산', '자차보험을 선택해 주세요.');
            return;
        }
    }
//	alert(crIdx+'<>'+calRentStartDt+'<>'+calRentEndDt+'<>'+selInsuranceFee);
    let req = {
        crIdx : crIdx,
        calRentStartDt : calRentStartDt,
        calRentEndDt : calRentEndDt,
        insuranceCopayment : selInsuranceFee
    };

    let target = 'selectReserveAmt';
    let method = 'select';

    fn_callApi(method,target,req,function(response) {
        let res = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {
        let data = res.data.result[0];

        let rentFee				= data.rentFee; 			//대여금
        let disRentFee			= data.disRentFee; 			//할인 후 대여금 -->
        let insuranceFee		= data.insuranceFee; 		//보험료 -->
        let mmRentAmt			= data.mmRentAmt; 			//장기 월 대여요금 -->
        let mmLastRentAmt		= data.mmLastRentAmt; 		//장기 마지막월 대여요금 -->
        let commissionPer		= data.commissionPer; 		//회원사 수수료 -->
        let calcPeriodDt		= data.calcPeriodDt; 		//대여일수
        let carDeposit			= data.carDeposit; 			//월장기 보증금
        let deliveryStandardPay	= data.deliveryStandardPay; //배달기본요금
        let deliveryAddPay		= data.deliveryAddPay; 		//배달10KM단위추가요금
        let periodMm			= data.periodMm; 			//총 개월수

        if(rentFee == '0' && insuranceFee == '0'){
            errorAlert('요금계산', '보험료정보 또는 기본요금 정보를 확인하세요.');
            return;
        }

        let calRentFee = '0';
        let calInsuranceFee = '0';
        let calRentTotAmount = '0';
//			let calCarssumRate = '0';
        let calPaymentAmount = '0';
        let caldiscountFee = '0';
        let deliveryFee = '0';
        let calRentTotAmountEtc = '';

        let deliveryTypeCode = getPureText($('#sel_deliveryTypeCode option:selected').val());
        // 배달대여인 경우
        if(deliveryTypeCode == 'DL'){
            deliveryFee = deliveryStandardPay;
        }else{
            deliveryFee = '0';
        }

        calRentFee = rentFee;
        if(periodMm == 0){
            calInsuranceFee = insuranceFee;
            caldiscountFee = Number(rentFee) - Number(disRentFee);
            calRentTotAmountEtc = '';
        }else{
            calInsuranceFee = '0';
            caldiscountFee = '0';
            calRentTotAmountEtc = '월장기 총금액 : ' + objectConvertToPriceFormat(rentFee) + '원<br>';
            calRentTotAmountEtc += '월별 대여요금 : ' + objectConvertToPriceFormat(mmRentAmt) + '원 *' + objectConvertToPriceFormat(periodMm) +'개월수';
            if(mmLastRentAmt >= 0){
                calRentTotAmountEtc += '<br>남은 일수의 대여요금 : ' + objectConvertToPriceFormat(mmLastRentAmt) + '원';
            }
            if(deliveryFee != '0'){
                calRentTotAmountEtc += '<br>배달료 첫 결제에 포함 : ' + objectConvertToPriceFormat(deliveryFee) + '원';
            }
        }

        calRentTotAmount = Number(calRentFee) + Number(calInsuranceFee) - Number(caldiscountFee) + Number(deliveryFee);
//			calCarssumRate = calRentTotAmount*(Number(commissionPer)/100);
        if(periodMm == 0){
            // 대여금액 = (할인전)대여금 + 보험금 - (할인후)대여금 + 배달금액
            calPaymentAmount = calRentTotAmount;
        }else{
            // 대여금액 = 월장기기본요금 + 배달금액
            calPaymentAmount = Number(mmRentAmt) + Number(deliveryFee);
        }

        $("#rentFee").val(objectConvertToPriceFormat(calRentFee));
        $("#insuranceFee").val(objectConvertToPriceFormat(calInsuranceFee));
        $("#discountFee").val(objectConvertToPriceFormat(caldiscountFee));
        $("#revaddFee").val('0');
        $("#deliveryFee").val(objectConvertToPriceFormat(deliveryFee));
        $("#periodDt").val(calcPeriodDt);
        $("#carDeposit").val(objectConvertToPriceFormat(carDeposit));

        $("#paymentTotalAmount").val(objectConvertToPriceFormat(calRentTotAmount));
        $("#paymentAmount").val(objectConvertToPriceFormat(calPaymentAmount));

        $("#calRentTotAmountEtc").empty();
        $("#calRentTotAmountEtc").append(calRentTotAmountEtc);

//			$("#deliveryFee").val('0');
        CALCULABLE_MOTH = 'Y';
        // }
    });// end fn_callApi

}

/*
 * 상세페이지 오픈
 */
function initDetailData(data){

    if(data == 'insert'){
        CRUD = data;
    }

    initDetailSelectBox(null);
    settingInputStatus();

}
/*
 * 신규 등록, 상세 페이지 로딩시 프리폼컨트롤 초기화
 */
function settingInputStatus(){

    if(CRUD == 'insert'){
        $("#reserveMasterInfo").find('input').each(function(){
            $(this).val('');
            // input 태그의 자동완성 기능 해제
            $(this).attr('autocomplete','off');
        });

        $("#reserveMasterInfo").find('select').each(function(){
            $(this).find('option').eq(0).prop('selected',true);
        });

        $('#companyName').prop('disabled' , false);
        $('#reserveUserEmail').prop('readonly' , false);
        $('#reserveUserName').prop('readonly' , false);
        $('#reserveUserContact1').prop('readonly' , false);
        $('#reserveUserBirthday').prop('readonly' , false);

        $('#btnreserveUserEmail').show();

        let strOption = "<option value=''>선택하세요</option>";
        $('#sel_modelName').empty();
        $('#sel_modelName').append(strOption);
        $("#sel_modelName").val('').prop("selected", true);

        $('#sel_ciIdx').empty();
        $('#sel_ciIdx').append(strOption);
        $("#sel_ciIdx").val('').prop("selected", true);
        $('#sel_fuel').attr('disabled', true);

        $('#rentcalAmt').show();
        $("#sel_reserveTypeCode").val('AD').prop("selected", true);
        $("#sel_reserveTypeCode").attr('disabled', true);

        $("#sel_reserveStatusCode").val('RS').prop("selected", true);
        $("#sel_reserveStatusCode").attr('disabled', true);

        drawData(null);

    }else{
        $('#reserveUserName').attr('readonly', true);
        $('#reserveUserEmail').attr('readonly', true);
        $('#reserveUserContact1').attr('readonly', true);
        $('#reserveUserBirthday').attr('readonly', true);

        if(GLOBAL_LOGIN_USER_ROLE != 'RA'){
            $('#companyName').attr('disabled', true);
        }
        $('#sel_fuel').attr('disabled', true);
        $("#sel_reserveTypeCode").attr('disabled', true);

        $('#btnreserveUserEmail').hide();

        // 예약/대여 취소시 수정불가 처리
        if($("#sel_reserveStatusCode").val() == "RC"){
            $("#sel_reserveStatusCode").attr('disabled', true);
            $('#rentcalAmt').hide();
            $('#btnReserveSave').hide();
        }else{
            $("#sel_reserveStatusCode").attr('disabled', false);
            $('#rentcalAmt').show();
            $('#btnReserveSave').show();
        }
    }
}
/*
 * 예약저장 값 체크
 */
function detailValidation(){
//	swal("예약 저장 작업중...", { icon: "warning", });
//	if(true){
//		return;
//	}

    var loginUser = getLoginUser();
    GLOBAL_LOGIN_USER_IDX = loginUser.urIdx;
    GLOBAL_LOGIN_USER_ROLE = loginUser.userRole;

    let title, text, icon, cancel_text, save_type;
    let target = '';
    let method = '';

    // 회원 (예약자) 값 체크
    let urIdx = getPureText($('#urIdx').val());
    let reserveUserName = getPureText($('#reserveUserName').val());
    let reserveUserEmail = $('#reserveUserId').val();
    let reserveUserContact1 = getPureText($('#reserveUserContact1').val());
    let reserveUserBirthday = formatDate(getDatePickerValue('reserveUserBirthDay'));

    if (isEmpty(reserveUserName)) { // is not empty
        errorAlert('회원', '이름은 필수 입력값 입니다.');
        $('#reserveUserName').focus();
        return;
    }else if (isEmpty(reserveUserEmail)) { // is not empty
        errorAlert('회원', '이메일(아이디)는 필수 입력값 입니다.');
        $('#reserveUserId').focus();
        return;
    }else if (isEmpty(reserveUserContact1)) { // is not empty
        errorAlert('회원', '연락처는 필수 입력값 입니다.');
        $('#reserveUserContact1').focus();
        return;
    }else if (isEmpty(reserveUserBirthday)) { // is not empty
        errorAlert('회원', '생년월일은 필수 입력값 입니다.');
        $('#reserveUserBirthDay').focus();
        return;
    }

    // 제 1 운전자 값 체크
    let reserveTypeCode = getPureText($('#sel_reserveTypeCode option:selected').val());
    let ulIdx1 = getPureText($('#urIdx').val());
    let firstDriverName = getPureText($('#firstDriverName').val());
    let firstDriverContact = getPureText($('#firstDriverContact').val());
    let firstDriverBirthDay = formatDate(getDatePickerValue('firstDriverBirthDay'));
    let firstDriverLicenseCode = getPureText($('#sel_firstDriverLicenseCode option:selected').val());
    let firstDriverLicenseNumber = getPureText($('#firstDriverLicenseNumber').val());
    let firstDriverExpirationDate = formatDate(getDatePickerValue('firstDriverExpirationDate'));
    let firstDriverLicenseIsDate = formatDate(getDatePickerValue('firstDriverLicenseIsDate'));

    if (isEmpty(firstDriverName)) { // is not empty
        errorAlert('제1운전자', '이름은 필수 입력값 입니다.');
        $('#firstDriverName').focus();
        return;
    }else if (isEmpty(firstDriverContact)) { // is not empty
        errorAlert('제1운전자', '연락처는 필수 입력값 입니다.');
        $('#firstDriverContact').focus();
        return;
    }else if (isEmpty(firstDriverBirthDay)) { // is not empty
        errorAlert('제1운전자', '생년월일은 필수 입력값 입니다.');
        $('#firstDriverBirthDay').focus();
        return;
    }else if (isEmpty(firstDriverLicenseCode) && reserveTypeCode != 'QT') { // is not empty
        errorAlert('제1운전자', '면허종류는 필수 선택값 입니다.');
        $('#firstDriverLicenseCode').focus();
        return;
    }else if (isEmpty(firstDriverLicenseNumber) && reserveTypeCode != 'QT') { // is not empty
        errorAlert('제1운전자', '면허번호는 필수 입력값 입니다.');
        $('#firstDriverLicenseNumber').focus();
        return;
    }else if (isEmpty(firstDriverExpirationDate) && reserveTypeCode != 'QT') { // is not empty
        errorAlert('제1운전자', '적성검사 만료일은 필수 입력값 입니다.');
        $('#firstDriverExpirationDate').focus();
        return;
    }else if (isEmpty(firstDriverLicenseIsDate) && reserveTypeCode != 'QT') { // is not empty
        errorAlert('제1운전자', '발급일은 필수 입력값 입니다.');
        $('#firstDriverLicenseIsDate').focus();
        return;
    }

    let ulIdx2 = getPureText($('#ulIdx2').val());
    let secondDriverName = getPureText($('#secondDriverName').val());
    let secondDriverContact = getPureText($('#secondDriverContact').val());
    let secondDriverBirthDay = formatDate(getDatePickerValue('secondDriverBirthDay'));
    let secondDriverLicenseCode = getPureText($('#sel_secondDriverLicenseCode option:selected').val());
    let secondDriverLicenseNumber = getPureText($('#secondDriverLicenseNumber').val());
    let secondDriverExpirationDate = formatDate(getDatePickerValue('secondDriverExpirationDate'));
    let secondDriverLicenseIsDate = formatDate(getDatePickerValue('secondDriverLicenseIsDate'));
//	alert(ulIdx2+'\n'+secondDriverName+'\n'+secondDriverGender+'\n'+secondDriverContact+'\n'+secondDriverBirthDay+'\n'+secondDriverLicenseCode+'\n'+secondDriverLicenseNumber+'\n'+secondDriverExpirationDate+'\n'+secondDriverLicenseIsDate);


    if (CRUD === 'insert'){
        // 사업자 값 체크
        let reserveCompanyName = getPureText($('#reserveCompanyName').val());
        let companyRegistrationNumber = getPureText($('#companyRegistrationNumber').val());
        let staffName = getPureText($('#staffName').val());
        let staffContact = getPureText($('#staffContact').val());
        let companyAddress = $('#companyAddress').val();

        if (isEmpty(reserveCompanyName)) { // is not empty
            errorAlert('사업자', '회사명은 필수 입력값 입니다.');
            $('#reserveCompanyName').focus();
            return;
        }else if (isEmpty(companyRegistrationNumber)) { // is not empty
            errorAlert('사업자', '사업자 등록번호는 필수 입력값 입니다.');
            $('#companyRegistrationNumber').focus();
            return;
        }else if (isEmpty(staffName)) { // is not empty
            errorAlert('사업자', '담당자 이름은 필수 입력값 입니다.');
            $('#staffName').focus();
            return;
        }else if (isEmpty(staffContact)) { // is not empty
            errorAlert('사업자', '담당자 연락처는 필수 선택값 입니다.');
            $('#staffContact').focus();
            return;
        }else if (isEmpty(companyAddress)) { // is not empty
            errorAlert('사업자', '주소는 필수 선택값 입니다.');
            $('#companyAddress').focus();
            return;
        }
    }

    // 예약 값 체크
    let rmIdx = getPureText($('#rmIdx').val());
    let rentStartDay = getPureText($('#rentStartDay').val()).replace('T', ' ');
    let rentEndDay = getPureText($('#rentEndDay').val()).replace('T', ' ');
    let deliveryAddr = getPureText($('#deliveryAddr').val());
    let returnAddr = getPureText($('#returnAddr').val());
    let reserveStatusCode;
    let deliveryTypeCode;


    if (CRUD === 'insert'){
        reserveStatusCode = "예약";
        deliveryTypeCode = getPureText($('input:radio[name="deliveryTypeCode"]:checked').val());
    }else {
        reserveStatusCode = getPureText($('#sel_reserveStatusCode option:selected').val());
        deliveryTypeCode = getPureText($('#sel_deliveryTypeCode option:selected').val());
    }

    if(deliveryTypeCode == 'OF' && isEmpty(returnAddr)){
        returnAddr = deliveryAddr;
    }

    if (isEmpty(deliveryTypeCode) || deliveryTypeCode == '0') { // is not empty
        errorAlert('예약', '대여방법은 필수 선택값 입니다.');
        return;
    }else if (isEmpty(rentStartDay)) { // is not empty
        errorAlert('예약', '대여일시는 필수 입력값 입니다.');
        return;
    }else if (isEmpty(rentEndDay)) { // is not empty
        errorAlert('예약', '반납일시 필수 입력값 입니다.');
        return;
    }else if(rentStartDay >= rentEndDay) {
        errorAlert('예약', '대여일시가 반납일시 보다 크거나 같을 수 없습니다.');
        return;
    }

    var isVisit = $('input:radio[id="radioVisitCompany"]').is('checked');
    if (!isVisit){
        if (isEmpty(deliveryAddr)) { // is not empty
            errorAlert('예약', '대여위치는 필수 입력값 입니다.');
            return;
        }else if (isEmpty(returnAddr)) { // is not empty
            errorAlert('예약', '반납위치는 필수 입력값 입니다.');
            return;
        }
    }

    let sdt = rentStartDay.split(' ');
    rentStartDay = sdt[0];
    let rentStartTime = sdt[1];

    let edt = rentEndDay.split(' ');
    rentEndDay = edt[0];
    let rentEndTime = edt[1];


    let periodDt = $("#periodDt").val();
    let exp = /개월/;

    let longTermYn = '';
    if(exp.test(periodDt)){
        longTermYn = 'LT';
    }else{
        longTermYn = 'ST';
    }

    let rtIdx = getPureText($('#companyName option:selected').val());
    let mdIdx = getPureText($('#mdIdx').val());
    let crIdx = getPureText($('#sel_modelName option:selected').val()); // 차량순번
    let carDamageCover = getPureText($('#carDamageCover').val());
    let insuranceCopayment = getPureText($('#insuranceCopayment').val());
    let carTypeCode = getPureText($('#carTypeCode').val());

    if (isEmpty(rtIdx)) { // is not empty
        errorAlert('차량', '회사명(지점)은 필수 선택값 입니다.');
        return;
    }else if (isEmpty(crIdx)) { // is not empty
        errorAlert('차량', '모델(번호)는 필수 선택값 입니다.');
        return;
    }
    // todo 자차 고객부담금
    // else if (isEmpty(insuranceCopayment) && reserveTypeCode != 'QT') { // is not empty
    //     errorAlert('차량', '자차 고객부담금은 필수 선택값 입니다.');
    //     return;
    // }
    let rtIdxSplit = $('#companyName option:selected').text().split('(');
    let companyName = rtIdxSplit[0];

    let rentFee = getPureText($('#rentFee').val());
    let insuranceFee = getPureText($('#insuranceFee').val());
    let discountFee = getPureText($('#discountFee').val());
    let deliveryFee = getPureText($('#deliveryFee').val());
    let addFee = getPureText($('#revaddFee').val());
    let paymentTotalAmount = getPureText($('#paymentTotalAmount').val());
    let paymentAmount = getPureText($('#paymentAmount').val());
    let carDeposit = getPureText($('#carDeposit').val());

//	alert(rentFee+'\n'+insuranceFee+'\n'+discountFee+'\n'+deliveryFee+'\n'+addFee+'\n'+paymentTotalAmount+'\n'+paymentAmount);
    if ((isEmpty(rentFee) || rentFee == '0' || CALCULABLE_MOTH == 'N') && reserveStatusCode == 'RS') { // is not empty
        errorAlert('결제', '요금계산을 먼저 실행하여 주세요.');
        return;
    }

    var req = {
        'rmIdx' : rmIdx
        ,	'reserveStatusCode' : reserveStatusCode
        ,	'reserveTypeCode' : reserveTypeCode
        ,	'longTermYn' : longTermYn
        ,	'urIdx' : urIdx
        ,	'reserveUserName' : reserveUserName
        ,	'reserveUserEmail' : reserveUserEmail
        ,	'reserveUserContact1' : reserveUserContact1
        ,	'reserveUserBirthday' : reserveUserBirthday
        ,	'rentStartDay' : rentStartDay
        ,	'rentStartTime' : rentStartTime
        ,	'rentEndDay' : rentEndDay
        ,	'rentEndTime' : rentEndTime
        ,	'deliveryTypeCode' : deliveryTypeCode
        ,	'deliveryAddr' : deliveryAddr
        ,	'returnAddr' : returnAddr
        ,	'crIdx' : crIdx
        ,	'carTypeCode' : carTypeCode
        ,	'rtIdx' : rtIdx
        ,	'companyName' : companyName
        ,	'carDeposit' : carDeposit
        ,	'rentFee' : rentFee
        ,	'insuranceFee' : insuranceFee
        ,	'discountFee' : discountFee
        ,	'deliveryFee' : deliveryFee
        ,	'addFee' : addFee
        ,	'paymentTotalAmount' : paymentTotalAmount
        ,	'reserveChannel' : 'CA'
        ,	'ulIdx1' : ulIdx1
        ,	'firstDriverName' : firstDriverName
        ,	'firstDriverContact' : firstDriverContact
        ,	'firstDriverBirthDay' : firstDriverBirthDay
        ,	'firstDriverLicenseCode' : firstDriverLicenseCode
        ,	'firstDriverLicenseNumber' : firstDriverLicenseNumber
        ,	'firstDriverExpirationDate' : firstDriverExpirationDate
        ,	'firstDriverLicenseIsDate' : firstDriverLicenseIsDate
        ,	'ulIdx2' : ulIdx2
        ,	'secondDriverName' : secondDriverName
        ,	'secondDriverContact' : secondDriverContact
        ,	'secondDriverBirthDay' : secondDriverBirthDay
        ,	'secondDriverLicenseCode' : secondDriverLicenseCode
        ,	'secondDriverLicenseNumber' : secondDriverLicenseNumber
        ,	'secondDriverExpirationDate' : secondDriverExpirationDate
        ,	'secondDriverLicenseIsDate' : secondDriverLicenseIsDate
        ,	'insuranceCopayment' : insuranceCopayment
        ,	'carDamageCover' : carDamageCover
        ,	'modId' : GLOBAL_LOGIN_USER_IDX
        ,	'regId' : GLOBAL_LOGIN_USER_IDX
    }
//	var pp = JSON.stringify(req);
//	console.log(pp);
//	alert(pp);
    title = '예약정보 저장';
    text = '저장하시겠습니까?'
    icon = 'info';
    cancel_text = '취소하셨습니다.';
    save_type = CRUD;

    call_before_save(title, text, icon, cancel_text, save_type, req);

}

//submit
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

    if (CRUD == 'insert') {
        target = 'insertReserveInfo';
        method = 'insert';
    } else if (CRUD == 'update') {
        target = 'updateReserveInfo';
        method = 'update';
    }

    fn_callApi(method, target, req, function(response) {
        let res = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        RESERVE_STATUS = $("#sel_reserveStatusCode option:selected").val();
        if (res.result == 1) {
            swal("저장 성공", {icon : "success"});
            closeDetail();
            loadApi(drawTable, null, null);
        }else{
            let msg = res.reservemsg;
            errorAlert('저장 실패', msg);
        }
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('저장 실패', '관리자에게 문의하세요.');
        // }
    });// end fn_callApi

}

/*
 * 보험선택시 기존 보험료와 
 */
function selectInsuranceInfo() {
    let crIdxIndex = $("#sel_modelName option:selected").index() - 1;
    let ciIdxinsuranceCopayment = $("#sel_ciIdx option:selected").val();

    let carDamageCover = nullCheck(carListData[crIdxIndex].carDamageCover) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover);
    let insuranceCopayment = nullCheck(carListData[crIdxIndex].insuranceCopayment) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment);
    let carDamageCover2 = nullCheck(carListData[crIdxIndex].carDamageCover2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover2);
    let insuranceCopayment2 = nullCheck(carListData[crIdxIndex].insuranceCopayment2) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment2);
    let carDamageCover3 = nullCheck(carListData[crIdxIndex].carDamageCover3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover3);
    let insuranceCopayment3 = nullCheck(carListData[crIdxIndex].insuranceCopayment3) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment3);
    let carDamageCover4 = nullCheck(carListData[crIdxIndex].carDamageCover4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].carDamageCover4);
    let insuranceCopayment4 = nullCheck(carListData[crIdxIndex].insuranceCopayment4) == ''?'':objectConvertToPriceFormat(carListData[crIdxIndex].insuranceCopayment4);

    if(ciIdxinsuranceCopayment == insuranceCopayment){
        $("#carDamageCover").val(carDamageCover);
        $("#insuranceCopayment").val(insuranceCopayment);
    }else if(ciIdxinsuranceCopayment == insuranceCopayment2){
        $("#carDamageCover").val(carDamageCover2);
        $("#insuranceCopayment").val(insuranceCopayment2);
    }else if(ciIdxinsuranceCopayment == insuranceCopayment3){
        $("#carDamageCover").val(carDamageCover3);
        $("#insuranceCopayment").val(insuranceCopayment3);
    }else if(ciIdxinsuranceCopayment == insuranceCopayment4){
        $("#carDamageCover").val(carDamageCover4);
        $("#insuranceCopayment").val(insuranceCopayment4);
    }else{
        $("#carDamageCover").val('');
        $("#insuranceCopayment").val('');
    }
    CALCULABLE_MOTH = 'N';
}

function calDate(pStartDate, pEndDate) {
    var strSDT = new Date(pStartDate.substring(0,4),pStartDate.substring(4,6)-1,pStartDate.substring(6,8));
    var strEDT = new Date(pEndDate.substring(0,4),pEndDate.substring(4,6)-1,pEndDate.substring(6,8));
    var strTermCnt = 0;

    var days = strEDT.getDate() - strSDT.getDate();
    var months = strEDT.getMonth() - strSDT.getMonth();
    var years = strEDT.getFullYear() - strSDT.getFullYear();


    strTermCnt = years * 12 + months + (days >= 0 ? 0 : -1);

    return strTermCnt;
}
/* =========================== detail function start ======================================*/


$("#reserveMasterInfo").iziModal({
    radius: 5,
    padding: 20,
    closeButton: false,
    overlayClose: false,
    width: 1000,
    height :'auto',
    overflow : 'scroll',
    title: '예약상세',
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

$("#reserveMasterInfo2").iziModal({
    radius: 5,
    padding: 20,
    closeButton: true,
    overlayClose: false,
    width: 1000,
    height :'auto',
    overflow : 'scroll',
    title: '예약금액수정',
    subtitle : ' ',
    headerColor: '#002e5b',
    backdrop: 'static',
    keyboard: false,
    group:'groupA',
    loop : true,
    navigateCaption: false,
    navigateArrows: false,
    onClosing: function(){
        closeDetail();
    }
});

//모달창 닫기  전 상태 변경시 확인 창 출력
function closeDetail(){
    CRUD = '';
    let reserveStatusCode = $("#sel_reserveStatusCode option:selected").val();

    // 변경 상태 와 이전 상태가 틀리고 예약취소요청이 아니면 확인창
    if(reserveStatusCode != RESERVE_STATUS && reserveStatusCode != 'CR' && CRUD != 'insert'){
        ConfirmAlert('예약상태', '예약상태가 변경되었습니다. 종료하시겠습니까?');
        return;
    }else{
        detailclose();
    }
}

//모달창 닫기
function detailclose(){
    $("#sel_reserveStatusCode").val(RESERVE_STATUS).prop("selected", true);
    $("#reserveMasterInfo").iziModal('close');
    $("#reserveMasterInfo2").iziModal('close');
}

function initRepatmentModal(_value){

    /*
    RC   |예약취소/대여취소
    CR   |취소요청
    */
    let targetCode = [ 'CR' ];
    let str = nullCheck(_value);

    if( CRUD == 'insert' ){

    }else{
        if( containsList(str , targetCode) ){
            $('.iziModal-navigate-next').trigger('click');

            $("#reserveMasterInfo2").find('input').each(function(){
                $(this).val('0');
                // input 태그의 자동완성 기능 해제
                $(this).attr('autocomplete','off');
            });

            initReserveCancelWait();
        }
    }

}

// 취소요청 모달창 start =========================================================================================================================================================================
/*
 * 취소요청시 팝업 초기화
 */
function initReserveCancelWait(){
    let _rmIdx = $("#rmIdx").val();

    let req = {
        rmIdx : _rmIdx
    };

    let target = 'reserveInfo';
    let method = 'select';

    // Detail정보 조회
    fn_callApi(method,target, req, function(res){
        // if( res.code == 200 ){
        let data = res.data.result[0];

        // 예약정보
        let rmIdx = nullCheck(data.rmIdx);

        // 대여금액 정보
        let rentFee = nullCheck(data.rentFee) == ''?'':objectConvertToPriceFormat(data.rentFee);
        let insuranceFee = nullCheck(data.insuranceFee) == ''?'':objectConvertToPriceFormat(data.insuranceFee);
        let discountFee = nullCheck(data.discountFee) == ''?'':objectConvertToPriceFormat(data.discountFee);
        let deliveryFee = nullCheck(data.deliveryFee) == ''?'':objectConvertToPriceFormat(data.deliveryFee);
        let addFee = nullCheck(data.addFee) == ''?'':objectConvertToPriceFormat(data.addFee);
        let paymentTotalAmount = nullCheck(data.paymentTotalAmount) == ''?'':objectConvertToPriceFormat(data.paymentTotalAmount);
        let paymentAmount = nullCheck(data.paymentAmount) == ''?'':objectConvertToPriceFormat(data.paymentAmount);

        $("#rp_rentFee").val(rentFee);
        $("#rp_insuranceFee").val(insuranceFee);
        $("#rp_discountFee").val(discountFee);
        $("#rp_deliveryFee").val(deliveryFee);
        $("#rp_addFee").val(addFee);
        $("#rp_paymentTotalAmount").val(paymentTotalAmount);
        $("#rp_paymentAmount").val(paymentAmount);
        $("#return_refundFee").val(paymentTotalAmount);
        // }

    });

}

/*
 * 취소요청 창 닫기
 */
function closeRepatmentDetail(){
    $("#sel_reserveStatusCode option:selected").val(RESERVE_STATUS);
    $("#reserveMasterInfo2").iziModal('close');
    $("#reserveMasterInfo").iziModal('open');
}

function returnFeeSetting(){

    var repatmentFee = 0;
    $('.returnSum').each(function(){
        repatmentFee += Number(getPureText(nullCheck($(this).val())));
    });

    $('.returnMinus').each(function(){
        repatmentFee -= Number(getPureText(nullCheck($(this).val())));
    });

    var setCancelWaitFee = repatmentFee;
    var setFee = Number(getPureText(nullCheck($('#rp_paymentTotalAmount').val()))) - setCancelWaitFee;

    $('#return_paymentTotalAmount').val(objectConvertToPriceFormat(setCancelWaitFee));
    $('#return_paymentAmount').val(objectConvertToPriceFormat(setCancelWaitFee));
    $('#return_refundFee').val(objectConvertToPriceFormat(setFee));


}

function repatmentValidation(){
    swal({
        title: "저장하시겠습니까?",
        icon: "info",
        buttons: true,
        dangerMode: true,
    })
        .then(function(willSave) {
            if (willSave) { //ok
                repatmentSubmit();
            } else {
                return;
            }
        });
}

function repatmentSubmit(){

    let rmIdx = getPureText(nullCheck($('#rmIdx').val()));
    let reserveStatusCode = 'CR';
    let paymentTotalAmount = getPureText(nullCheck($('#return_paymentTotalAmount').val()));
    let deliveryFee = getPureText(nullCheck($('#return_deliveryFee').val()));
    let insuranceFee = getPureText(nullCheck($('#return_insuranceFee').val()));
    let rentFee = getPureText(nullCheck($('#return_rentFee').val()));
    let addFee = getPureText(nullCheck($('#return_addFee').val()));
    let discountFee = getPureText(nullCheck($('#return_discountFee').val()));
    let cancelFee = getPureText(nullCheck($('#return_cancelFee').val()));
    let paymentAmount = getPureText(nullCheck($('#return_paymentAmount').val()));
    let refundFee = getPureText(nullCheck($('#return_refundFee').val()));

    if(isEmpty(rmIdx)){
        errorAlert('수정', '수정시 정보가 부족합니다.');
        return;
    }

    let param = {
        'rmIdx' : rmIdx
        ,'reserveStatusCode' : reserveStatusCode
        ,'paymentTotalAmount' : paymentTotalAmount
        ,'deliveryFee' : deliveryFee
        ,'insuranceFee' : insuranceFee
        ,'rentFee' : rentFee
        ,'addFee' : addFee
        ,'discountFee' : discountFee
        ,'cancelFee' : cancelFee
        ,'refundFee' : refundFee
    };

    let _target = 'updateReserveInfo';
    let _method = 'update';

    fn_callApi(_method, _target, param, function(response) {
        let res = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        if (res.data.result == 1) {
            swal("저장 성공", {icon : "success"});
            closeDetail();
            loadApi(drawTable, null, null);
        }
        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('저장 실패', '관리자에게 문의하세요.');
        // }
    });// end fn_callApi


}
//취소요청 모달창 end =========================================================================================================================================================================

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
        CALCULABLE_MOTH = 'N';
        field.selectionStart = oldStart;
        field.selectionEnd = oldEnd;
    }

    field.addEventListener('click', changed)
    field.addEventListener('keyup', changed)
};

//input box auto hypen
function userContactAutoHyphen(id){
    let num = $("#" + id).val();

    autoHyphenFromNumber('phone',id, num);
}

// 카드 변경
function changeCardView(cardViewName) {
    let cards = $('.card-body');

    switch (cardViewName) {
        case 'userReviewCard':
            swal("상세화면은 순차적으로 오픈할 예정입니다.", { icon: "warning", });
        default:
            break;
    }
}

// 대여기간 계산
function calcPeriodDt() {

    var rtValue = [];

    var stDateTime =  new Date($('#rentStartDay').val());
    var endDateTime = new Date($('#rentEndDay').val());

    // 날짜 유효성 검사
    if(endDateTime <= stDateTime){
        errorAlert('반납일시','반납일시는 대여일시보다 미래여야 합니다.');
        return;
    }


    var startYear = stDateTime.getFullYear();
    var startMonth = stDateTime.getMonth();
    var startDate = stDateTime.getDate();
    var startHours = stDateTime.getHours();
    var startMinute = stDateTime.getMinutes();

    var endYear = endDateTime.getFullYear();
    var endMonth = endDateTime.getMonth();
    var endDate = endDateTime.getDate();
    var endHours = endDateTime.getHours();
    var endMinute = endDateTime.getMinutes();

    is_same_day = endDate == startDate ? true : false;

    // 마지막날은 30일 이하여야함 ( 28~30 ) 카썸정책
    var dayOfLast = Number((new Date(endYear, endMonth + 1, 0)).getDate()) != 31 ? 30 : Number((new Date(endYear, endMonth + 1, 0)).getDate());
    var startDate_dayOfLast = Number((new Date(startYear, startMonth + 1, 0)).getDate());
    var endDate_dayOfLast = Number((new Date(endYear, endMonth + 1, 0)).getDate());

    // 시간 차이 계산 => 밀리세컨드
    var diffMs = (endDateTime.getTime() - stDateTime.getTime());
    // 밀리세컨드를 date 객체로
    var timeGap = new Date(0, 0, 0, 0, 0, 0, diffMs);

    var setMonth = Math.floor((diffMs / (86400000 * 30))); // 개월
    var setDay = Math.floor((diffMs % (86400000 * 30)) / (1000 * 60 * 60 * 24)); // 일수
    var setTime = Math.floor(diffMs / (1000 * 60 * 60)) % 24;
    var setMinute = Math.floor(diffMs / (1000 * 60)) % 60;

    //console.log("월 : " + setMonth +"\n일 : " + setDay + "\n 시간 : " + setTime + "\n 분 : " + setMinute + "\n 마지막일 : " + startDate_dayOfLast );

    rtValue.push(setMonth);
    rtValue.push(setDay);
    rtValue.push(setTime);
    rtValue.push(setMinute);

    renderingSumTime(rtValue[0], rtValue[1], rtValue[2], rtValue[3]);

}

function renderingSumTime(strMm, strDd, strHh, strMin) {

    var setMm = isNaN(strMm) ? '0개월 ' : strMm + '개월 ';
    var setDd = isNaN(strDd) ? '0일 ' : strDd + '일 ';
    var setHh = isNaN(strHh) ? '0시 ' : strHh + '시 ';
    var setMin = isNaN(strMin) ? '0분 ' : strMin + '분';

    var periodDtText = setMm + setDd + setHh + setMin;

    $('#periodDt').val(periodDtText);
}

function cf_DisplayDay(strDate) {

    var strDate = strDate;
    var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;

    strDate = strDate.replace(regExp, '');

    var str_yyyy = strDate.substring(0, 4) + '/';
    var str_mm = strDate.substring(4, 6) + '/';
    var str_dd = strDate.substring(6, 8);
    var setDate = String(str_yyyy + str_mm + str_dd + ' 00:00:00');

    var week = ['일', '월', '화', '수', '목', '금', '토'];
    var getday = new Date(setDate).getDay();
    var day = week[getday];

    return day;
}

function cf_Display_AmPm_By_Times(time) {


    var date = new Date();
    var hours = time.substr(0, 2);
    var minutes = time.substr(0, 4);


    if (hours == '') {
        hours = date.getHours();
    } else if (minutes == '') {
        minutes = date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes();
    }

    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    var strTime = ampm;

    return strTime;
}

// 24hhmm 를 12hhmm
function convertTimeFormat12MIS(_time) {
    var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;

    var setTime = regExp.test(_time) ? _time.replace(regExp, '') : _time;
    var hh;
    var mm;
    if (setTime.length < 4) {
        hh = setTime.substring(0, 1);
        mm = setTime.substring(1, 3);
    } else {
        hh = setTime.substring(0, 2);
        mm = setTime.substring(2, 4);
    }

    if (Number(hh) == 12) {

    } else {
        hh = Number(hh % 12);
    }

    if (mm == 60) {
        mm = 0;
    }

    hh = String(hh).length == 1 ? '0' + hh : String(hh);
    mm = String(mm).length == 1 ? '0' + mm : String(mm);

    return hh + ':' + mm;
}
