




// 템플릿 삭제
function deleteBplanData() {
    var deleteBplanIdxList = [];

    $("input:checkbox[name='deleteCheck']:checked").each(function () {
        deleteBplanIdxList.push(this.parentElement.parentElement.dataset.value);
    });

    if (deleteBplanIdxList.length === 0) {
        errorAlert('기본요금제 삭제', '요금제를 선택해주세요.');
        return;
    }

    var checkedBplanCount;

    var deleteAllYn = $('input:checkbox[id=deleteAllCar]').is(":checked");
    checkedBplanCount = deleteAllYn ? deleteBplanIdxList.length - 1 : deleteBplanIdxList.length;

    req = {
        'deletebaiscPlanIdxList': deleteBplanIdxList
        , 'modId': getLoginUser().urIdx
    };

    save_type = 'deleteDcBplanInfo';

    title = '요금제 삭제';
    text = "총 " + checkedBplanCount + " 건의 요금제가 선택되었습니다.\n삭제하시겠습니까?";
    icon = 'info';
    cancel_text = '취소하셨습니다.';

    call_before_save(title, text, icon, cancel_text, save_type, req);
}

// submit
function detailSubmit(save_type, req) {
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

    switch (save_type) {
        // case 'saveCarinfo':		// 차량정보
        //     if (CRUD_METHOD === 'insert') {
        //         target = 'insertDcCarInfo';
        //         method = 'insert';
        //     } else if (CRUD_METHOD === 'update') {
        //         target = 'updateDcCarInfo';
        //         method = 'update';
        //     }
        //     break;
        // case 'saveInsurance':	// 보험정보
        //     target = 'updateDcInsurance';
        //     method = 'update';
        //     break;
        // case 'savePaymentinfo':	// 기본요금
        //     // if (CRUD_METHOD === 'insert') {
        //     // 	target = 'insertDcCarPaymentInfo';
        //     // 	method = 'insert';
        //     // } else if (CRUD_METHOD === 'update') {
        //     target = 'updateDcPayment';
        //     method = 'update';
        //     // }
        //     break;
        case 'deleteDcBplanInfo':
            target = 'deleteDcBplanInfo'; // 요금제삭제
            method = 'delete';
            break;

    }// end switch

        //
        // $.ajax({
        //     url: '/api/v1.0/deleteDcBplanInfo.do',
        //     type: 'POST',
        //     dataType: 'json',
        //     data: JSON.stringify(req),
        //     contentType: 'application/json;charset=UTF-8',
        //     cache: false,
        //     async: false
        // }).done(function (data, textStatus, jqXHR) {
        //     console.log(data);
        // });


    fn_callApi(method, target, req, function (response) {
        let data = response;

        // 200이라면 페이징을 구한다.
        // if (res.code == 200) {

        if (data.res === 1) {
            if (save_type === 'deleteDcBplanInfo') {
                swal("삭제 성공", {icon: "success"});
                return;
            }
            swal("저장 성공", {icon: "success"});

            switch (save_type) {
                case 'saveCarinfo':		// 차량정보
                    if (CRUD_METHOD === 'insert') {
//						CRUD_METHOD = 'update';
                        let crIdx = data.crIdx;
                        $("#crIdx").val(crIdx);
                        // initDetailInfo(crIdx);
//						loadApi(drawTable, null, null);
                    }
                    break;
                case 'saveInsurance':	// 보험정보
//					loadApi(drawTable, null, null);
                    break;
                case 'savePaymentinfo':	// 기본요금
//					loadApi(drawTable, null, null);
                    break;
            }// end switch
        } else { // 200이 아닐때 empty처리 error처리 등을 기록한다.
            if (save_type === 'deleteDcCarInfo') {
                errorAlert('삭제 실패', '관리자에게 문의하세요.');
            }
            errorAlert('저장 실패', '관리자에게 문의하세요.');
        }
    });// end fn_callApi

}