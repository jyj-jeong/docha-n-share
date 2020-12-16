/*
 * carModel.js
 * 차량 > 차량모델
 *
 * */

var DEFAULT_PAGENUM = 15;
var MODAL_NAME = 'carModelDetail';
var MODAL_TITLE = '모델 상세';
var MODAL_WIDTH = 1500;
var MODAL_HEIGTH = 1000;
var CURRENT_PAGE = 0;
var CRUD = '';

var modelDetailNameList = [];

function initializingPageData(){
    loadApi(drawTable, null, null);
    initSelectBox();
    bindEvent();
}

function loadApi (fnc, page, displayPageNum, division) {

    let sel_search = $("#sel_search option:selected").val();
    let strSearchKeyWord = $("#searchKeyWord").val();
    let showContents = $("#showContents option:selected").val();

    showContents = isEmpty(showContents) ? 10 : showContents;

    CURRENT_PAGE = parseInt(page);
    displayPageNum = parseInt(displayPageNum);

    CURRENT_PAGE = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
    displayPageNum = isNaN(displayPageNum) ? showContents: (typeof displayPageNum === 'number') ? displayPageNum : showContents;

    let strReserveStatus = $("#reserveStatus option:selected").val();

    if(!isEmpty(showContents)) {
        displayPageNum = Number(showContents);
    }

    let req = {
        'page' 		     	: CURRENT_PAGE
        ,'displayPageNum' 	: displayPageNum
        ,'searchType' 	 	: sel_search
        ,'searchKeyWord'    : strSearchKeyWord
    };

    let target = 'carModelInfo';
    let method = 'select';

    fn_callApi(method, target, req, function (response) {
        let res = response;

    });//end fn_callApi

}

var drawTable = function drawTable(res, page, displayPageNum){
    page = parseInt(page);
    displayPageNum = parseInt(displayPageNum);

    page = isNaN(page) ? 1 : (typeof page === 'number') ? page : 1;
    displayPageNum = isNaN(displayPageNum) ? 10 : (typeof displayPageNum === 'number') ? displayPageNum : 10;

    let data = res.result;

    let rows = [];
    let columns;

    columns = [

        { "name": "rowNumber", "title": "No", "breakpoints": "all", "visible": false},
        { "name": "mdIdx", "title": "모델번호" ,
            "formatter" : function(value, options, rowData){
                return '<a href="javascript:initDetailInfo(' + "'" +value + "'" +');"  >'+value+'</a>';
            }
        },
        { "name": "countryName", "title": "국가" ,
            "formatter" : function(value, options, rowData){
                return value;
            }
        },
        { "name": "manufacturerName", "title": "제조사", "breakpoints": "xs"},
        { "name": "year", "title": "연식", "breakpoints": "xs",
            "formatter" : function(value, options, rowData){
                return value;
            }
        },
        { "name": "modelName", "title": "모델", "breakpoints": "xs"},
        { "name": "modelDetailName", "title": "모델 상세", "breakpoints": "xs"},
        { "name": "cartypeName", "title": "등급", "breakpoints": "xs sm",
            "formatter" : function(value, options, rowData){
                return value;
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

    let totalCnt = res.paging.totalCount;
    let perPageNum = res.paging.cri.perPageNum;
    let showDisplayPageNum = res.paging.cri.displayPageNum;

    //page는 전역변수 사용
    let prev = res.paging.prev;
    let next = res.paging.next;

    makePaging(totalCnt, perPageNum, showDisplayPageNum, page, prev, next, $("#page"));

    if(!isEmpty(totalCnt)) {
        $('#totalRowCount').text('총 [' + totalCnt + '] 건이 검색되었습니다.');
    } else {
        $("#totalRowCount").text('총 [0] 건이 검색되었습니다.');
    }

}

function initSelectBox(){

    let searchOption = '';
    let countOption = '';

    searchOption += '<option value="">선택</option>';
    searchOption += '<option value="mdIdx">모델번호</option>';
    searchOption += '<option value="country">국가</option>';
    searchOption += '<option value="manufacturer">제조사</option>';
    searchOption += '<option value="year">연식</option>';
    searchOption += '<option value="modelName">모델</option>';
    searchOption += '<option value="modelDetailName">모델상세</option>';


    countOption += '<option value="10">10개씩 보기</option>';
    countOption += '<option value="20">20개씩 보기</option>';
    countOption += '<option value="30">30개씩 보기</option>';
    countOption += '<option value="60">60개씩 보기</option>';

    var delYnItem = "";
    delYnItem += "<option value='N'>사용</option>";
    delYnItem += "<option value='Y'>미사용</option>";

    $('#delYn').empty();
    $('#delYn').append(delYnItem);

    $('#showContents').append(countOption);
    $('#sel_search').append(searchOption);

    $("#sel_search option:eq(0)").prop("selected", true);
    $("#showContents option:eq(0)").prop("selected", true);
    $("#delYn option:eq(0)").prop("selected", true);
}

function bindEvent(){

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

$("#searchKeyWord").keypress(function(e) {
    if (!isEmpty(sel_search)) {
        if (e.keyCode == 13) {
            fn_search();
        }
    }
});


$('#btnSearch').click(function(e) {
    fn_search();
});

function fn_search(){

    let searchKeyWord = $("#searchKeyWord").val();
    let sel_search = $("#sel_search option:selected").val();

    if( sel_search != '' ){
        if ( nullCheck(searchKeyWord) == '' ) {
            errorAlert('검색어', '검색어를 입력하세요.');
            return;
        } else {
            loadApi(drawTable, null, null);
        }
    }else{
        loadApi(drawTable, null, null);
    }

}

/*
 * 모아보기 선택시
 */
function changeList() {
    loadApi(drawTable, null, null);
}




/* =========================== detail function start ======================================*/

// init
function initDetailInfo(seq){

    var currentYear = new Date().getFullYear();

    var str = '';
    for(var i = 7; i >= 0; i--){
        str += "<option value = '" + (currentYear-i) + "'>" + (currentYear-i) + "</option>";
    }
    str += "<option value = '" + (currentYear+1) + "'>" + (currentYear+1) + "</option>";
    $('select[name="year"]').append(str);

    CRUD = 'update';

    let mdIdx = seq;

    let req = {
        mdIdx:mdIdx
    };


    let target = 'carModelDetail';
    let method = 'select';

    fn_callApi(method, target, req, function (response) {
        let data = response[0];

        let mdIdx 				= data.mdIdx            ; //모델idx
        let modelName 			= data.modelName        ; //모델명
        let country 	        = data.countryCode  	; //국가
        let year 	            = data.year 		    ; //연식
        let delYn 				= data.delYn     		; //삭제여부
        let imgIdx 				= data.imgIdx     		; //차량 이미지

        for (var i=0; i<response.length; i++){

            modelDetailNameList.push(response[i].modelDetailName);

            var strOption = '<div class="d-inline-block mr-2 alert alert-dark alert-dismissible fade show" role="alert">'
                + '<span id="'+response[i].mdIdx+'">' + response[i].modelDetailName + '</span>'
                + '<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
                + '<span aria-hidden="true">&times;</span>'
                + '</button>'
                + '</div>';

            $('#carModelDetailList').append(strOption);

        }

        initDetailSelectBox(data);

        $("#mdIdx").val(mdIdx);
        $("#modelName").val(modelName);
        $("#modelDetailName").val('');

        $('select[name="year"]').val(year.trim());

        $("#sel_countryCode").val(country).prop("sel_countryCode", true);

        // devleop
        // $('#car_img').attr('src','C:/ohdocha/data/temp/' + imgIdx);

        //product
        $('#car_img').attr('src','https://admin.docha.co.kr/img/car/' + imgIdx);

        if(!isEmpty(delYn)){
            $("#delYn").val(delYn).prop("delYn", true);
        }else {
            $("#delYn").val('N').prop("delYn", true);
        }


    });//end fn_callApi


}

// select box 가져오기
function initDetailSelectBox(data){
    let manufacturerCode 	= ''; //제조사code
    let manufacturerName 	= ''; //제조사명
    let countryCode 		= ''; //국가code
    let countryName 		= ''; //국가명
    let importCode 			= ''; //국내해외구분
    let importName 			= ''; //국내해외명
    let cartypeCode 		= '';	//차종
    let cartypeName 		= '';	//차종명
    let fuelCode 			= ''; //연료code
    let fuelName 			= ''; //연료명
    let transmissionCode 	= ''; //변속기구분code
    let transmissionName 	= ''; //변속기명
    let driveTypeCode 		= ''; //구동방식구분code
    let driveTypeName 		= ''; //구동방식구분명
    let driveLicenseCode 	= ''; //면허구분code
    let driveLicenseName 	= ''; //면허구분이름

    if(!isEmpty(data)){
        manufacturerCode 	= data.manufacturerCode ; //제조사code
        manufacturerName 	= data.manufacturerName ; //제조사명
        countryCode 		= data.countryCode      ; //국가code
        countryName 		= data.countryName      ; //국가명
        importCode 			= data.importCode       ; //국내해외구분
        importName 			= data.importName       ; //국내해외명
        cartypeCode 		= data.cartypeCode		;	//차종
        cartypeName 		= data.cartypeName		;	//차종명
        fuelCode 			= data.fuelCode         ; //연료code
        fuelName 			= data.fuelName         ; //연료명
        transmissionCode 	= data.transmissionCode ; //변속기구분code
        transmissionName 	= data.transmissionName ; //변속기명
        driveTypeCode 		= data.driveTypeCode    ; //구동방식구분code
        driveTypeName 		= data.driveTypeName    ; //구동방식구분명
        driveLicenseCode 	= data.driveLicenseCode ; //면허구분code
        driveLicenseName 	= data.driveLicenseName ; //면허구분이름
    }

    let target = 'commonCodeInfo';
    let method = 'select';
    let req = {};

    // 국가
    req = {
        rtCode : "CN",
        pCode : "CN"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_countryCode').empty();
        $('#sel_countryCode').append(strOption);

        //국가
        if(!isEmpty(countryCode)) {
            $("#sel_countryCode").val(countryCode).prop("sel_countryCode", true);
        }else{
            $("#sel_countryCode").val('0').prop("sel_countryCode", true);
        }// 국가

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 제조사
    req = {
        rtCode : "CR",
        pCode : "MF"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_manufacturerCode').empty();
        $('#sel_manufacturerCode').append(strOption);

        //제조사
        if(!isEmpty(manufacturerCode)) {
            $("#sel_manufacturerCode").val(manufacturerCode).prop("sel_manufacturerCode", true);
        }else{
            $("#sel_manufacturerCode").val('0').prop("sel_manufacturerCode", true);
        }// 제조사

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 차종
    req = {
        rtCode : "CR",
        pCode : "CTY"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_cartypeCode').empty();
        $('#sel_cartypeCode').append(strOption);

        //차종
        if(!isEmpty(cartypeCode)) {
            $("#sel_cartypeCode").val(cartypeCode).prop("sel_cartypeCode", true);
        }else{
            $("#sel_cartypeCode").val('0').prop("sel_cartypeCode", true);
        }// 차종

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 연료
    req = {
        rtCode : "CR",
        pCode : "FL"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_fuelCode').empty();
        $('#sel_fuelCode').append(strOption);

        //연료
        if(!isEmpty(fuelCode)) {
            $("#sel_fuelCode").val(fuelCode).prop("sel_fuelCode", true);
        }else{
            $("#sel_fuelCode").val('0').prop("sel_fuelCode", true);
        }// 연료

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 국내/외 구분
    req = {
        rtCode : "CR",
        pCode : "IM"
    };

    fn_callApi(method, target, req, function(response) {
        data =response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_importCode').empty();
        $('#sel_importCode').append(strOption);

        //연료
        if(!isEmpty(importCode)) {
            $("#sel_importCode").val(importCode).prop("sel_importCode", true);
        }else{
            $("#sel_importCode").val('0').prop("sel_importCode", true);
        }// 연료

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 변속 구분
    req = {
        rtCode : "CR",
        pCode : "TM"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_transmissionCode').empty();
        $('#sel_transmissionCode').append(strOption);

        //연료
        if(!isEmpty(transmissionCode)) {
            $("#sel_transmissionCode").val(transmissionCode).prop("sel_transmissionCode", true);
        }else{
            $("#sel_transmissionCode").val('0').prop("sel_transmissionCode", true);
        }// 연료

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 구동방식
    req = {
        rtCode : "CR",
        pCode : "DT"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_driveTypeCode').empty();
        $('#sel_driveTypeCode').append(strOption);

        //연료
        if(!isEmpty(driveTypeCode)) {
            $("#sel_driveTypeCode").val(driveTypeCode).prop("sel_driveTypeCode", true);
        }else{
            $("#sel_driveTypeCode").val('0').prop("sel_driveTypeCode", true);
        }// 연료

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

    // 구동방식
    req = {
        rtCode : "CR",
        pCode : "DL"
    };

    fn_callApi(method, target, req, function(response) {
        data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        let strOption = "";

        strOption += "<option value = '0'>선택하세요</option>";
        for ( var i in data) {
            if (data[i].codeValue) {
                strOption += "<option value = '" + data[i].code + "'>" + data[i].codeValue + "</option>";
            }
        }
        $('#sel_driveLicenseCode').empty();
        $('#sel_driveLicenseCode').append(strOption);

        //연료
        if(!isEmpty(driveLicenseCode)) {
            $("#sel_driveLicenseCode").val(driveLicenseCode).prop("sel_driveLicenseCode", true);
        }else{
            $("#sel_driveLicenseCode").val('0').prop("sel_driveLicenseCode", true);
        }// 연료

        // } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
        // 	errorAlert('API ERROR', '조회중 에러가 발생했습니다. \r\n 관리자에게 문의하세요.');
        // }
    });// end fn_callApi

}

// validation
function detailValidation(){

    let mdIdx = $("#mdIdx").val();												// 모델순번
    let year = $("#year option:selected").val();
    let modelName = $("#modelName").val();										// 모델명
    let manufacturerCode = $("#sel_manufacturerCode option:selected").val();	// 제조사
    let countryCode = $("#sel_countryCode option:selected").val();				// 국가
    let importCode = $("#sel_importCode option:selected").val();				// 국내/외구분
    let cartypeCode = $("#sel_cartypeCode option:selected").val();				// 등급
    let fuelCode = $("#sel_fuelCode option:selected").val();					// 연료
    let transmissionCode = $("#sel_transmissionCode option:selected").val();	// 변속
    let driveTypeCode = $("#sel_driveTypeCode option:selected").val();			// 구동방식
    let driveLicenseCode = $("#sel_driveLicenseCode option:selected").val();	// 면허구분
    let displacement = $("#displacement").val();								// 배기량
    let maximumPassenger = $("#maximumPassenger").val();						// 승차인원
    let delYn = $("#delYn option:selected").val();								// 삭제여부

    let inputCarModelDetails = $('#carModelDetailList #modelDetail');


    for (var i = 0; i < inputCarModelDetails.length; i++){
        modelDetailNameList.push(inputCarModelDetails[i].textContent);
    }

    if(isEmpty(countryCode) || countryCode === '0') {
        errorAlert('모델 정보', '국가는 필수 항목입니다.');
        return;
    }
    else if(isEmpty(manufacturerCode) || manufacturerCode === '0') {
        errorAlert('모델 정보', '제조사는 필수 항목입니다.');
        return;
    }
    else if(isEmpty(year)) {
        errorAlert('모델 정보', '연식은 필수 항목입니다.');
        return;
    }
    else if(isEmpty(modelName)) {
        errorAlert('모델 정보', '차종은 필수 항목입니다.');
        return;
    }else if(modelDetailNameList.length === 0) {
        errorAlert('모델 정보', '차종상세는 필수 항목입니다.');
        return;
    }
    else if(isEmpty(cartypeCode) || cartypeCode === '0') {
        errorAlert('모델 정보', '등급은 필수 항목입니다.');
        return;
    }


    let save_type = CRUD;
    let req = {
        'mdIdx' : mdIdx
        ,	'modelName' : modelName
        ,	'modelDetailNameList' : modelDetailNameList
        ,	'manufacturerCode' : manufacturerCode
        ,	'countryCode' : countryCode
        ,	'year' : year
        ,	'importCode' : importCode
        ,	'cartypeCode' : cartypeCode
        ,	'fuelCode' : fuelCode
        ,	'transmissionCode' : transmissionCode
        ,	'driveTypeCode' : driveTypeCode
        ,	'driveLicenseCode' : driveLicenseCode
        ,	'displacement' : displacement
        ,	'maximumPassenger' : maximumPassenger
        ,	'delYn' : delYn
        ,	'modId' : getLoginUser().urIdx
        ,	'regId' : getLoginUser().urIdx
    };

    let title = '모델정보 저장';
    let text = '저장하시겠습니까?'
    let icon = 'info';
    let cancel_text = '취소하셨습니다.';

    call_before_save(title, text, icon, cancel_text, save_type, req);

}


//submit
function detailSubmit(save_type, req){
    if (CRUD === 'insert') {
        target = 'insertCarModelInfo';
        method = 'insert';
    } else if (CRUD === 'update') {
        target = 'updateCarModelInfo';
        method = 'update';
    }

    fn_callApi(method, target, req, function(response) {
        let data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        if (data.res === 1) {
            swal("저장 성공", {icon : "success"});
            if(CRUD === 'insert') {

                let mdIdx = data.mdIdx;
                $("#mdIdx").val(mdIdx);
            }
        }
        else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
            errorAlert('저장 실패', '관리자에게 문의하세요.');
        }
    });// end fn_callApi
}

//모달 오픈
function initDetailData(){
    CRUD = 'insert';

    var currentYear = new Date().getFullYear();
    var str = '';
    for(var i = 7; i >= 0; i--){
        str += "<option value = '" + (currentYear-i) + "'>" + (currentYear-i) + "</option>";
    }
    str += "<option value = '" + (currentYear+1) + "'>" + (currentYear+1) + "</option>";
    $('select[name="year"]').append(str);

    initDetailSelectBox(null);

}

$(document).on('click', '#carModelDetailList button', function () {
    if (CRUD === 'update'){

        var deleteMdIdx = this.parentElement.children[0].id;
        var textContent = this.parentElement.children[0].textContent;

        for (var i = 0; i< modelDetailNameList.length; i++){

            if (modelDetailNameList[i].toString() === textContent){

                var url = '/api/v1.0/deleteCarModelInfo.do';
                var req = {
                    mdIdx : deleteMdIdx
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

                    var index = modelDetailNameList.indexOf(textContent);
                    modelDetailNameList.splice(index, 1);

                })
            }
        }

    }

});

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
        commentPath : 'carModel',
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

/* =========================== detail function end ======================================*/