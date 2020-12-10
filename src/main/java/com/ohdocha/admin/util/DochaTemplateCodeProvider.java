package com.ohdocha.admin.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum DochaTemplateCodeProvider {


    A000007
            ("[취소완료]\n" +
                    "두차 렌터카 예약취소가 완료되었습니다.\n" +
                    "\n" +
                    "예약일시 : %s\r\n" +
                    "취소일시 : %s\r\n" +
                    "대여일시 : %s\r\n" +
                    "반납일시 : %s\r\n" +
                    "차량 : %s\r\n" +
                    "결제금액 : %s원\r\n" +
                    "환불금액 : %s원\r\n" +
                    "\n" +
                    "두차를 이용해주셔서 감사합니다.", "0007")//[고객] 예약취소

    , A000020
            ("[취소완료]\n" +
                    "두차 렌터카 예약취소가 완료되었습니다.\n" +
                    "\n" +
                    "예약일시 : %s\r\n" +
                    "취소일시 : %s\r\n" +
                    "대여일시 : %s\r\n" +
                    "반납일시 : %s\r\n" +
                    "차량 : %s\r\n" +
                    "차량번호 : %s\r\n" +
                    "결제금액 : %s원\r\n" +
                    "환불금액 : %s원\r\n" +
                    "\n" +
                    "두차를 이용해주셔서 감사합니다.", "0020");//[고객] 예약취소


    @Getter
    private String msg;

    @Getter
    private String code;

    private DochaTemplateCodeProvider(String msg, String code) {

        this.msg = msg;
        this.code = code;

    }

    public static DochaTemplateCodeProvider valueOfCode(String value) {

        List<DochaTemplateCodeProvider> enumList = Arrays.asList(values());
        DochaTemplateCodeProvider returnTemplate = null;

        for (DochaTemplateCodeProvider template : enumList) {

            if (template.code.equals(value)) {
                returnTemplate = template;
            }

        }

        if (returnTemplate == null) {
            throw new IllegalArgumentException("No enum constant Search Value");
        }

        return returnTemplate;

    }


}
