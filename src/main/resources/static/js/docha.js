
/**
 변수 선언
 */
var TEST_FLAG = false;


var GLOBAL_LINK_RTIDX = ''; // 회원사 링크 버튼 클릭시 이동전 저장 변수

/**

 * 데이터테이블 초기화
 h
 */
function initDataTables() {
    var table = $('#dataTable').DataTable( {
        language: {
            emptyTable: "데이터가 존재하지 않습니다.",
            lengthMenu: "페이지당 _MENU_ 개씩 보기",
            info: "현재 _START_ - _END_ / _TOTAL_건",
            infoEmpty: "0 건",
            infoFiltered: "( _MAX_건의 데이터에서 필터링됨 )",
            search: "검색: ",
            zeroRecords: "일치하는 데이터가 존재하지 않습니다.",
            loadingRecords: "로딩중...",
            processing:     "잠시만 기다려 주세요...",
            paginate: {
                next: "다음",
                previous: "이전"
            }
        },
        orderMulti: true,
        dom: '<\'row\'<\'col-md-6\'l><\'col-md-6\'f>><\'row\'<\'col-md-6\'B>><\'row\'<\'col-md-12\'t>><\'row\'<\'col-md-6\'i><\'col-md-6\'p>>',
        buttons: [{
            extend: 'csvHtml5',
            text: '엑셀 다운로드',
            footer: true,
            className: 'exportBtn btn btn-primary'
        }]
    } );

    table.buttons().container().appendTo($('#btnExcel'));
}

/**

 * 입력값이  null 인지 체크한다

 */

function isNull(input){

    if (input.value == null || input.value == ""){

        return true;

    }else{

        return false;

    }

}


/**
 * 입력값에 특정 문자가 있는지 체크하는 로직이며

 * 특정문자를 허용하고 싶지 않을때 사용할수도 있다

 * if (containsChars(form.name, "!,*&^%$#@~;")){

 *       alert("특수문자를 사용할수 없습니다");

 * }

 */

function containsChars(input, chars){

    for (var i=0; i < input.value.length; i++){

        if (chars.indexOf(input.value.charAt(i)) != -1){

            return true;

        }

    }

    return false;

}

/**

 * 입력값이 특정 문자만으로 되어있는지 체크하며

 * 특정문자만을 허용하려 할때 사용한다.

 * if (containsChars(form.name, "ABO")){

 *    alert("혈액형 필드에는 A,B,O 문자만 사용할수 있습니다.");

 * }

 */

function containsCharsOnly(input, chars){

    for (var i=0; i < input.value.length; i++){

        if (chars.indexOf(input.value.charAt(i)) == -1){

            return false;

        }

    }

    return true;

}

/**

 * 입력값이 알파벳인지 체크

 * 아래 isAlphabet() 부터 isNumComma()까지의 메소드가 자주 쓰이는 경우에는

 * var chars 변수를 global 변수로 선언하고 사용하도록 한다.

 * var uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

 * var lowercase = "abcdefghijklmnopqrstuvwxyz";

 * var number = "0123456789";

 * function isAlphaNum(input){

 *       var chars = uppercase + lowercase + number;

 *    return containsCharsOnly(input, chars);

 * }

 */

function isAlphabet(input){

    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 알파벳 대문자인지 체크한다

 */

function isUpperCase(input){

    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 알파벳 소문자인지 체크한다

 */

function isLowerCase(input){

    var chars = "abcdefghijklmnopqrstuvwxyz";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 숫자만 있는지 체크한다.

 */

function isNumer(input){

    var chars = "0123456789";

    return containsCharsOnly(input, chars);

}

/**

 * 입려값이 알파벳, 숫자로 되어있는지 체크한다

 */

function isAlphaNum(input){

    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 숫자, 대시"-" 로 되어있는지 체크한다

 * 전화번호나 우편번호, 계좌번호에 -  체크할때 유용하다

 */

function isNumDash(input){

    var chars = "-0123456789";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 숫자, 콤마',' 로 되어있는지 체크한다

 */

function isNumComma(input){

    var chars = ",0123456789";

    return containsCharsOnly(input, chars);

}

/**

 * 입력값이 사용자가 정의한 포맷 형식인지 체크

 * 자세한 format 형식은 자바스크립트의 'reqular expression' 참고한다

 */

function isValidFormat(input, format){

    if (input.value.search(format) != -1){

        return true; // 올바른 포멧형식

    }

    return false;

}

/**

 * 입력값이 이메일 형식인지 체크한다

 * if (!isValidEmail(form.email)){

 *       alert("올바른 이메일 주소가 아닙니다");

 * }

 */

function isValidEmail(input){

    var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    if(exptext.test(input)==false){
        return false;
    }else {
        return true;
    }
}



/**

 * 입력값의 바이트 길이를 리턴한다.

 * if (getByteLength(form.title) > 100){

 *    alert("제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다");

 * }

 */

function getByteLength(input){

    var byteLength = 0;

    for (var inx = 0; inx < input.value.charAt(inx); inx++)     {

        var oneChar = escape(input.value.charAt(inx));

        if (oneChar.length == 1){

            byteLength++;

        }else if (oneChar.indexOf("%u") != -1){

            byteLength += 2;

        }else if (oneChar.indexOf("%") != -1){

            byteLength += oneChar.length / 3;

        }

    }

    return byteLength;

}

/**

 * 입력값에서 콤마를 없앤다

 */

function removeComma(input){

    return input.value.replace(/,/gi,"");

}

/**

 * 선택된 라디오버튼이 있는지 체크한다

 */

function hasCheckedRadio(input){

    if (input.length > 1){

        for (var inx = 0; inx < input.length; inx++){

            if (input[inx].checked) return true;

        }

    }else{

        if (input.checked) return true;

    }

    return false;

}

/**

 * 선택된 체크박스가 있는지 체크

 */

function hasCheckedBox(input){

    return hasCheckedRadio(input);

}

/**

 * 핸드폰 형식을 반환합니다.
 *
 * isNumer 함수를 사용하여 해당 컬럼이 숫자만 있는지 비교 후 아래 포맷터 사용
 * 테스트데이터 때문에 길이 체크가 필요하다면 길이 체크 후 사용한다.
 *
 * phoneFomatter('01000000000');   //010-0000-0000
 phoneFomatter('01000000000',0); //010-****-0000
 phoneFomatter('0100000000');    //010-000-0000
 phoneFomatter('0100000000',0);  //010-***-0000
 phoneFomatter('0200000000');    //02-0000-0000
 phoneFomatter('0200000000',0);  //02-****-0000
 phoneFomatter('0310000000');    //031-000-0000
 phoneFomatter('0310000000',0);  //031-***-0000
 phoneFomatter('16880000');      //1688-0000

 */

function phoneFomatter(num,type){

    var formatNum = '';
    if(nullCheck(num) != ''){

        if(num.length==11){
            if(type==0){
                formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
            }else{
                formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
            }
        }else if(num.length==8){
            formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
        }else{
            if(num.indexOf('02')==0){
                if(type==0){
                    formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
                }else{
                    formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
                }
            }else{
                if(type==0){
                    formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
                }else{
                    formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
                }
            }
        }
    }

    return formatNum;
}// end phoneFomatter



function showDetail(){
    $('#detailWrapper').show();
    $('#detailWrapper').addClass('show');
}

function closeDetail(){
    $('#detailWrapper').empty();
    $('#detailWrapper').removeClass('show');
}

function showDetail2(){
    $('#detailWrapper2').show();
    $('#detailWrapper2').addClass('show');
}

function closeDetail2(){
    $('#detailWrapper2').empty();
    $('#detailWrapper2').removeClass('show');
}

// 특수문자 제거
function getPureText(value){
    var text = nullCheck(value);
    var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
    var tmp = text.replace(regExp, "");
    return tmp;
}

// 자바스크립트 null check
function nullCheck(value){
    var resData = '';
    if( value == undefined || value == null || value == 'null' ){
        return resData;
    }else{
        return value;
    }
}

// yyyymmdd => yyyy + division + mm + division + dd 
function dateFormatter(value,division){
    var getText = nullCheck(getPureText(value));
    var serDivision = nullCheck(division);
    var dvs = serDivision == '' ? '-' : division;
    var resDateText = '';

    if( getText == '' ){
        resDateText = '미확인';
    }else{
        var yyyy = getText.substring(0,4);
        var mm = getText.substring(4,6);
        var dd = getText.substring(6,8);
        resDateText = yyyy+dvs+mm+dvs+dd;
    }

    return resDateText;
}

// regDt
// return yyyy.mm.dd hh:min:sec
function regDtFormatter(date){
    var getDate = nullCheck(date);
    var resDate = '';
    if( date == '' ){
        resDate = '미확인';
    }else{
        var yyyy = getDate.substring(0,4);
        var mm = getDate.substring(4,6);
        var dd = getDate.substring(6,8);
        var hh = getDate.substring(8,10);
        var min = getDate.substring(10,12);
        var ss = getDate.substring(12,14);
        resDate = yyyy+'-'+mm+'-'+dd+'<br/>'+hh+':'+min+':'+ss;
    }
    return resDate;
}

//yyyymmdd => yyyy + division + mm + division + dd 
function timeFormatter(value,division){
    var getText = nullCheck(getPureText(value));
    var serDivision = nullCheck(division);
    var dvs = serDivision == '' ? ':' : division;
    var resDateText = '';

    if( getText == '' ){
        resDateText = '미확인';
    }else{
        var hh = getText.substring(0,2);
        var mm = getText.substring(2,4);
        resDateText = hh+dvs+mm;
    }

    return resDateText;
}

/**
 * vlaue 공백 체크
 * [], {} 도 빈값
 * */
function isEmpty(value){
    if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
        return true;
    }else{
        return false;
    }
}


//paramType  : Date
//return [0] : 개월  
//return [1] : 일
//return [2] : 시간
//return [3] : 분
//precondition 파라미터는 무조건 yyyy/mm/dd hh:mm:ss 형태로 보내주세요    
//다음 브라우저의 프로토콜을 준수합니다. ( chrome , ie , safari )
function setDateTimeDiff(_stDateTime , _endDateTime){

    var _stDateTime = getPureText(_stDateTime);
    var _endDateTime = getPureText(_endDateTime);

    var stYYYY = _stDateTime.substring(0,4);
    var stMM = _stDateTime.substring(4,6);
    var stDD = _stDateTime.substring(6,8);
    var stHour = _stDateTime.substring(8,10);
    var stMin = _stDateTime.substring(10,12);
    var setStDate = stYYYY+'/'+stMM+'/'+stDD+ ' ' + stHour+':'+stMin;
    var stDateTime = new Date(setStDate);

    var endYYYY = _endDateTime.substring(0,4);
    var endMM = _endDateTime.substring(4,6);
    var endDD = _endDateTime.substring(6,8);
    var endHour = _endDateTime.substring(8,10);
    var endMin = _endDateTime.substring(10,12);
    var setEndDate = endYYYY+'/'+endMM+'/'+endDD+ ' ' + endHour+':'+endMin;
    var endDateTime = new Date(setEndDate);


    var rtValue = [];
    var month_valid = false;
    var day_valid = false;
    var is_same_day = false;
    var hour_valid = false;
    var min_valid = false;
    var start_is_lastDay_valid = false;
    var end_is_lastDay_valid = false;

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

    // 마지막날은 30일 이하여야함 ( 28~30 ) 두차정책
    var dayOfLast = Number(( new Date( endYear, endMonth+1, 0) ).getDate()) != 31 ? 30 : Number(( new Date( endYear, endMonth+1, 0) ).getDate());
    var startDate_dayOfLast = Number(( new Date( startYear, startMonth+1, 0) ).getDate());
    var endDate_dayOfLast = Number(( new Date( endYear, endMonth+1, 0) ).getDate());

    // 시간 차이 계산 => 밀리세컨드
    var diffMs = (endDateTime.getTime() - stDateTime.getTime());
    // 밀리세컨드를 date 객체로
    var timeGap = new Date(0,0,0,0,0,0,diffMs);

    var setMonth = Math.floor(( diffMs / ( 86400000  * 30))); // 개월
    var setDay = Math.floor(( diffMs % ( 86400000  * 30)) / (1000*60*60*24)); // 일수
    var setTime = Math.floor(diffMs/(1000*60*60)) % 24;
    var setMinute = Math.floor(diffMs/(1000*60)) % 60;

    //console.log("월 : " + setMonth +"\n일 : " + setDay + "\n 시간 : " + setTime + "\n 분 : " + setMinute + "\n 마지막일 : " + startDate_dayOfLast );

    rtValue.push(setMonth);
    rtValue.push(setDay);
    rtValue.push(setTime);
    rtValue.push(setMinute);

    return rtValue;
}

//금액 콤마찍기
function objectConvertToPriceFormat(obj) {

    var resValue = obj.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    if( obj.length == 0 ){
        resValue = 0;
    }

    return resValue;
}

//regDt
//parameter YYYYMMDD24MISS
//return yyyy.mm.dd hh:min:sec
function regDtFormatter2(_date){

    var date = getPureText(_date);
    var getDate = nullCheck(date);
    var resDate = '';

    if( getDate === ' ' ){
        resDate = '';
    }else{
        var yyyy = getDate.substring(0,4);
        var mm = getDate.substring(4,6);
        var dd = getDate.substring(6,8);
        var hh = getDate.substring(8,10);
        var min = getDate.substring(10,12);
        var ss = getDate.substring(12,14);

        resDate = yyyy+'-'+mm+'-'+dd+' '+hh+':'+min+':'+ss;
    }
    return resDate;
}

//birthdayFormatter('19990122')
//return 1999-01-22
function YMDFormatter(num){
    if(!num) return "";
    var formatNum = '';

    // 공백제거
    num=num.replace(/\s/gi, "");

    try{
        if(num.length == 8) {
            formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
        }

    } catch(e) {
        formatNum = num;
        console.log(e);
    }
    return formatNum;

}

//모달창 열기
// function openIziModal(modalName){
// 	$("#" + modalName).iziModal('open');
// }


/*function initDatePicker(_target,_date){   
	
	var date;
	if( _date == '0' ){
		date = '';  
	}else{
		date = nullCheck(_date) == '' ? String(moment().format("YYYY-MM-DD").toString().slice(0,-1))+'0' : _date;
	}
	
	new Picker(document.querySelector(_target), {  
		date : date ,  
		  container: '.js-super-picker-container',
		  format: 'YYYY-MM-DD',    
		  increment: {
		    minute: 10,
		  },
		  text: {
		    title: '연도월일시간',
		    cancel: '취소',
		    confirm: '선택',
		  },  
		  translate(type, text) {
		    const suffixes = {
		      year: '년',
		      month: '월',
		      day: '일',
		      hour: '시',
		      minute: '분', 
		    };
		    return Number(text) + suffixes[type];
		  },
		});
	  
	$(_target).val(date);

}*/

/*function initDateTimePicker(_target,_date){   
	
	var date = nullCheck(_date) == '' ? String(moment().format("YYYY-MM-DD HH:mm").toString().slice(0,-1))+'0' : _date;    
	
	new Picker(document.querySelector(_target), {  
		date : date , 
		  container: '.js-super-picker-container',
		  format: 'YYYY-MM-DD HH:mm',    
		  increment: {
		    minute: 10,
		  },
		  text: {
		    title: '연도월일시간',
		    cancel: '취소',
		    confirm: '선택',
		  },  
		  translate(type, text) {
		    const suffixes = {
		      year: '년',
		      month: '월',
		      day: '일',
		      hour: '시',
		      minute: '분', 
		    };
		    return Number(text) + suffixes[type];
		  },
		});
	  
	$(_target).val(date);

}*/

/*
 * 시간형식의 Timepicker를 open합니다.
 * 
 * initTimePicker('#weekdayOpenStart', '2020-02-17 : 14:22');
 * initTimePicker('#weekdayOpenStart', '');
 * 10분단위로 MINUTES은 설정됩니다.
 * 
 * */
/*function initTimePicker(_target,_date){
	
	let _timeSetting  = '';
	
	if(isEmpty(_date)) {
		var _date = moment().format("YYYY-MM-DD HH:mm");
		let _time = _date.split(' ');
		_timeSetting= _time[1].slice(0,-1) + '0';	
	} else {
		_timeSetting = _date;
	} 

	new Picker(document.querySelector(_target), {
		  date : _timeSetting , 
		  container: '.js-super-picker-container',
		  format: 'HH:mm',    
		  increment: {
		    minute: 10,
		  },
		  text: {
		    title: '시간',
		    cancel: '취소',
		    confirm: '선택',
		  },
		  headers:true,
		  translate(type, text) {
		    const suffixes = {
		      hour: '시',
		      minute: '분'
		    };
		    return Number(text) + suffixes[type];
		  },
		  
		});
	 
	$(_target).val(_date);

}*/

/*
 * 4자리, 3자리 시간 형식을 HH:MM형식으로 변환합니다.
 * 
 * */
function convertToTimeFormat(time){

    let res = '';

    if(isEmpty(time)) { //null이면
        res = '';
    } else {

        if(time.length == 3){

            let lastword = '';
            lastword = time.charAt(time.length-1);

            res = time.slice(0,-1) + ':' + '0' + lastword;
        }else if(time.length ==4) {
            let firstword = '';
            let lastword = '';
            firstword= time.substring(0,2);
            lastword = time.substring(2,4);

            res = firstword + ':' + lastword;
        }
    }

    return res;
}//convertToTimeFormat

/*
 * 사업자등록번호 정규식 체크함수
 * 
 * 
 * */
function checkCompanyRegistrationNumber(value) {
    let valueMap = value.replace(/-/gi, '').split('').map(function(item) {
        return parseInt(item, 10);
    });

    if (valueMap.length === 10) {
        let multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
        let checkSum = 0;

        for (var i = 0; i < multiply.length; ++i) {
            checkSum += multiply[i] * valueMap[i];
        }

        checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);
        return Math.floor(valueMap[9]) === ( (10 - (checkSum % 10)) % 10);
    }

    return false;
}

/*
 * 사업자등록번호 - 붙이기
 * companyRegistrationNumberFormatter('1234567890', '0')
 * 123-45-*****
 * companyRegistrationNumberFormatter('1234567890', '1')
 * 123-45-67890 
 * */
function companyRegistrationNumberFormatter(num, type) {
    let formatNum = '';
    try{
        if (num.length === 10) {
            if (type === 0) {
                formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-*****');
            } else {
                formatNum = num.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
            }
        }

    } catch(e) {
        formatNum = num;
        console.log(e);
    }
    return formatNum;

}


/*
 * 시간 → 분 convert 함수
 * 
 * */
function convertHoursToMin(hours) {

    var minutes = hours * 60;

    return minutes;
}

/*
 * makeRentTime('20200220','0859')
 * → 2020-02-20 08:59
 * 
 * */
function makeTimeFormat(day,time) {
    let dateTime = new Date(stringDateParse(day) + ' ' +time);
    dateTime = moment(dateTime).format("YYYY-MM-DD HH:mm");

    return dateTime;
}

/*
 * stringDateParse('20200220');
 * 
 * */
function stringDateFormatter(strDate, time) {
    let y = strDate.substr(0, 4);
    let m = strDate.substr(4, 2);
    let d = strDate.substr(6, 2);


    let h = time.substr(0,2);
    let min = time.substr(2,4);

    let date = y + '-' + m + '-' + d + ' ' + h + ':'+ min;

    return date;
}



// izimodal 스크롤 상단으로 초기화
function iziModalScrollInit(){
    setTimeout(function(){
        $(".iziModal-wrap").scrollTop(0);
    },1);
}


// 문자열에서 list 에 해당하는 것들 제거
function containsList(_str, list){

    var str = nullCheck(_str);
    var flag = false;

    if( str == '' ){
        return flag;
    }

    for( var i=0; i<list.length; i++ ){
        if( str.indexOf(list[i]) != -1 ){
            flag = true;
        }
    }
    return flag;
}


//주소검색 api ( 다음 kakao )
function openDaumAddrApi(target){

    var id = '#'+target;
    var addressTarget;
    var addressDetailTarget1;
    var addressDetailTarget2;

    var zonecode = ''; // 우편번호
    var address = '';  // 기본주소
    var roadAddress = ''; // 도로명 주소
    var roadAddressEnglish = ''; //영문 도로명 주소
    var jibunAddress = ''; // 지번 주소
    var jibunAddressEnglish = ''; // 영문 지번 주소

    new daum.Postcode({
        oncomplete: function(data) {

            zonecode = data.zonecode;
            address = data.address;
            roadAddress = data.roadAddress;
            roadAddressEnglish = data.roadAddressEnglish;
            jibunAddress = data.jibunAddress;
            jibunAddressEnglish = data.jibunAddressEnglish;

            $(id).val(jibunAddress);
        }
    }).open();

}

/*
 * 저장버튼을 클릭하면 save_type 별로 swal confirm을 띄워 확인 후
 * Yes면 detailSubmit 함수로 이동합니다.
 * swal_title 	: 메세지 타이틀
 * swal-text 	: 메세지 내용
 * swal_icon 	: 아이콘 종류
 * cancel_text 	: 취소했을 때 메세지 이름
 * save_type 	: 저장 종류를 구분해서 detailSubmit으로 이동함(저장하는 card가 여러개 일때 사용)
 * req 			: array parameter
 * 
 * example)
 * 
 * switch (save_type) {
		case 'updateRentCompanyInfo'://회원사정보
 				req = {
						   rtIdx			         : _rtIdx			        	
						 , companyName         		 : companyName         		
						 , companyZipcode      		 : companyZipcode      		
						 , companyAddress      		 : companyAddress      		
						 , companyAddressDetail		 : companyAddressDetail		
						 , companyRegistrationNumber : companyRegistrationNumber	
						 , accountBank  			 : accountBank  				
						 , accountNumber			 : accountNumber				
						 , accountHolder			 : accountHolder				
						 , accessYn 				 : accessYn 					
						 , companyRegistrationName 	 : companyRegistrationName 	
						 , branchName 				 : branchName 				
						 , alarmYn 					 : alarmYn 					
				 }

				  swal_title = '회원사정보 저장';
				  swal_text = '저장하시겠습니까?'
				  swal_icon = 'info';
				  cancel_text = '취소하셨습니다.';
				  
				  call_before_save(title, text, icon, cancel_text, save_type, req);
   		break;
 * 
 * 
 * */
function call_before_save(swal_title, swal_text, swal_icon, cancel_text, save_type, req) {
    swal({
        title: swal_title,
        text: swal_text,
        icon: swal_icon,
        buttons: true,
        dangerMode: true,
    })
        .then(function(willSave) {
            if (willSave) { //ok
                detailSubmit(save_type, req);
            } else {
                swal(cancel_text);
            }
        });
}//end call_before_save

/*
 * 저장전 validation을 체크하여 swal을 띄웁니다. 
 * 하나를 띄우면 return이 됩니다.
 * 
 * let swal_title = '직원 이메일';
 * let swal_text = '직원 이메일은 필수 입력값 입니다.';
 * 
 * 
 *   errorAlert(swal_title, swal_text);
 *   
 *   
 * */
function ConfirmAlert(swal_title, swal_text) {
    swal({
        title: swal_title,
        text: swal_text,
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then(function(willSave) {
            if (willSave) { //ok
                detailclose();
            } else {
                return;
            }
        });
}//end errorAlert

/*
 * 저장전 validation을 체크하여 swal을 띄웁니다. 
 * 하나를 띄우면 return이 됩니다.
 * 
 * let swal_title = '직원 이메일';
 * let swal_text = '직원 이메일은 필수 입력값 입니다.';
 * 
 * 
 *   errorAlert(swal_title, swal_text);
 *   
 *   
 * */
function errorAlert(swal_title, swal_text) {
    swal({
        title: swal_title,
        text: swal_text,
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
    return;
}//end errorAlert


function checkReg(value, type) {
    let regPhone = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;	//일반번호
    let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; //이메일
    let retValue = false;

    //일반전화
    if(type == 'phone') {
        retValue =  regPhone.test(value);
    }

    if(type == 'email') {
        retValue = regEmail.test(value);
    }

    return retValue;
}



/*
 * input bbox 숫자만 입력
 * <input type="text" onkeypress="inNumber();"/>  
 * */
function inNumber(){
    if(event.keyCode<48 || event.keyCode>57){
        event.returnValue=false;
    }
}



/*
 * 문자열에 입력된 하이픈(-)을 제거합니다.
   let str="185-51-671";
   removeHypen(str);  
 * */
function removeHypen(str) {
    return str=str.replace(/-/g,'');
}


/*
 * target : input id
 * num : only number
 * 
 * */
function autoHyphenFromNumber(type ,target ,num) {

    num = getOnlyNumber(num);
    var tmp = '';

    switch (type) {
        case 'phone':
            tmp += num.substr(0, 3);
            tmp += '-';
            tmp += num.substr(3, 4);
            tmp += '-';
            tmp += num.substr(7);
            break;
        case 'license':
            tmp += num.substr(0, 2);
            tmp += '-';
            tmp += num.substr(2, 6);
            tmp += '-';
            tmp += num.substr(8);
            break;
        case 'companyRegistration':
            tmp += num.substr(0, 3);
            tmp += '-';
            tmp += num.substr(3, 2);
            tmp += '-';
            tmp += num.substr(5, 5);
            break;
        default:
            break;
    }
    $("#" + target).val(tmp);
}

function getOnlyNumber(number){

    return number = number.replace(/[^0-9]/g, '');
}

/*
 * id, title을 입력받아 엑셀을 다운로드하는 함수
 * 
 * */
function fn_ExcelReport(id, title, sheetName) {

    var tab_text = '<html xmlns:x="urn:schemas-microsoft-com:office:excel">';
    tab_text = tab_text + '<head><meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">';
    tab_text = tab_text + '<xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
    tab_text = tab_text + '<x:Name>'+ sheetName +'</x:Name>';
    tab_text = tab_text + '<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>';
    tab_text = tab_text + '</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>';
    tab_text = tab_text + "<table border='1px'>";

    var exportTable = $('#' + id).clone();
    exportTable.find('input').each(function (index, elem) { $(elem).remove(); });

    tab_text = tab_text + exportTable.html();
    tab_text = tab_text + '</table></body></html>';

    var data_type = 'data:application/vnd.ms-excel';
    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");
    var fileName = title + '.xls';

    //Explorer 환경에서 다운로드
    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) {
        if (window.navigator.msSaveBlob) {
            var blob = new Blob([tab_text], {
                type: "application/csv;charset=utf-8;"
            });
            navigator.msSaveBlob(blob, fileName);
        }
    } else {

        var blob2 = new Blob([tab_text], {
            type: "application/csv;charset=utf-8;"
        });
        var filename = fileName;
        var elem = window.document.createElement('a');
        elem.href = window.URL.createObjectURL(blob2);
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
    }
}


function showToast(method,msg, title){

    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": true,
        "positionClass": "toast-top-center",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "3000",
        "hideDuration": "3000",
        "timeOut": "2000",
        "extendedTimeOut": "2000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };

    if( method == 'error' ){
        toastr.error(msg,title);
    }else{
        toastr.success(msg,title);
    }

}

Array.prototype.contains = function(element) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == element) {
            return true;
        }
    }
    return false;
}


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function preparingPage() {
    swal("페이지 준비중입니다.", { icon: "warning", });
}
