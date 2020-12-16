package com.ohdocha.admin.util;

import com.ohdocha.admin.domain.reserve.matchingService.DochaAlarmTalkDto;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@Configuration
@EnableAsync
public class DochaAlarmTalkMsgUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
	@Value("${kakao.alert.talk.key}")
	private String kakao_alert_api_key;
	
	@Value("${kakao.alert.talk.id}")
	private String kakao_alert_api_id;
	
	@Value("${kakao.alert.callback.number}")
	private String kakao_alert_callback_number;
	
	@Value("${kakao.alert.fail.type}")
	private String kakao_alert_fail_type;
	*/


    /*
     * todo
     * 카카오 알림톡 발송용 템플릿을 생성
     * 단기/장기인경우
     * 코드템플릿별 분기처리
     *
     * */

    /**
     * sendAlramTalk
     * //	 * @param  HttpServletRequest
     * //	 * @param  HttpServletResponse
     * //	 * @param  imp_uid 본인인증 후 고유 imp_uid
     *
     * @return DochaUserInfoDto
     * @throws IOException
     */

    public HttpResponse<JsonNode> sendAlramTalk(DochaAlarmTalkDto dto) {

		/*
		logger.info("PHONE=" + dto.getPhone());
		logger.info("CALLBACK=" + dto.getCallBack());
		logger.info("REQDATE=" + dto.getRentDate());
		logger.info("MSG=" + dto.getMsg());
		logger.info("TEMPLATE_CODE=" + dto.getTemplateCode());
		logger.info("failed_type=" + dto.getFailedType());

		logger.info("failed_subject=" + dto.getFailedSubject());
		logger.info("failed_msg=" + dto.getMsg());
		logger.info("BTN_TYPES=" + dto.getBtnTypes());
		logger.info("BTN_TXTS=" + dto.getBtnTxts());
		logger.info("BTN_URLS1=" + dto.getBtnUrls1());
		*/

        String failed_subject = "";
        String msg = "";

        DochaTemplateCodeProvider template = DochaTemplateCodeProvider.valueOfCode(dto.getTemplateCode());

        //A000007	[고객] 취소 확정
        if (DochaTemplateCodeProvider.A000007 == template) {
            failed_subject = "[취소완료]";
            msg = cancelCompeliteAlarmTalk(dto);

        }
        //A000020	[관리자] 취소 확정
        if (DochaTemplateCodeProvider.A000020 == template) {
            failed_subject = "[취소완료]";
            msg = cancelCompeliteAlarmTalkForAdmin(dto);

        }
        //A000008	[고객] 문의 답변 완료
        if (DochaTemplateCodeProvider.A000008 == template) {
            failed_subject = "[문의]";
            msg = questionAnswerAlarmTalk(dto);

        }


        //application.properties 에서 받아온 정보로 처리할 경우 유니코드 관련 오류발생하여 일단 소스에 key및 app id, callback number을 넣어서 처리
        MultipartBody request = Unirest.post("http://api.apistore.co.kr/kko/1/msg/mobilityk")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .header("x-waple-authorization", "MTMzMDMtMTU5NDg2MzAwNjYzMy1hNmU1ZjIxYS04MWRiLTQ5NTItYTVmMi0xYTgxZGJhOTUyOTk=")
                .field("PHONE", dto.getPhone())
                .field("CALLBACK", "01099477228")
                .field("REQDATE", "")
                .field("MSG", msg)
                .field("TEMPLATE_CODE", dto.getTemplateCode())
                .field("failed_type", "lms")
                .field("failed_subject", failed_subject)
                .field("failed_msg", msg);

        if (dto.getBtnTypes() != null) {
            request.field("BTN_TYPES", dto.getBtnTypes());
        }

        if (dto.getBtnTxts() != null) {
            request.field("BTN_TXTS", dto.getBtnTxts());
        }

        if (dto.getBtnUrls1() != null) {
            request.field("BTN_URLS1", dto.getBtnUrls1());
        }

        if (dto.getBtnUrls2() != null) {
            request.field("BTN_URLS2", dto.getBtnUrls2());
        }

        HttpResponse<JsonNode> response = request.asJson();


        return response;
    }

    // 0007 취소 완료
    private String cancelCompeliteAlarmTalk(DochaAlarmTalkDto dto) {

        return String.format(DochaTemplateCodeProvider.A000007.getMsg()
                , dto.getBookDate()//예약일시
                , dto.getCancelDate()//취소일시
                , dto.getRentDate()//렌트시작일
                , dto.getReturnDate()//렌트종료일
                , dto.getCarName()//차량명
                , dto.getPayAmount()//결제금액
                , dto.getCancelAmount());//환불금액

    }

    // 0020 취소 완료
    private String cancelCompeliteAlarmTalkForAdmin(DochaAlarmTalkDto dto) {

        return String.format(DochaTemplateCodeProvider.A000020.getMsg()
                , dto.getBookDate()//예약일시
                , dto.getCancelDate()//취소일시
                , dto.getRentDate()//렌트시작일
                , dto.getReturnDate()//렌트종료일
                , dto.getCarName()//차량명
                , dto.getCarNumber()//차량명
                , dto.getPayAmount()//결제금액
                , dto.getCancelAmount());//환불금액

    }

    // 0008 취소 요청
    private String questionAnswerAlarmTalk(DochaAlarmTalkDto dto) {

        return String.format(DochaTemplateCodeProvider.A000008.getMsg()
                ,dto.getUserContact());

    }


}
