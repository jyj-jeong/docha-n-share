package com.ohdocha.admin.controller;


import com.google.gson.Gson;
import com.ohdocha.admin.config.Properties;
import com.google.gson.JsonObject;
import com.ohdocha.admin.domain.menu.*;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.service.MenuService;
import com.ohdocha.admin.util.FileHelper;
import com.ohdocha.admin.util.ServiceMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Slf4j
@Controller
@AllArgsConstructor
public class MenuController extends ControllerExtension {

    private final MenuService menuService;
    private final Properties properties;

    /* 메뉴 리스트 조회 */
    @PostMapping(value = "/api/v1.0/menuInfoList.json")
    @ResponseBody
    public Object menuList(@RequestBody DochaAdminMenuRequest menuRequest, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("menuRequest", menuRequest);

        menuService.getMenuList(serviceMessage);

        return serviceMessage.get("result");
    }

    // region [ 사이트 메뉴 화면 ]

    /* 사이트 - 메인 화면 */
    @GetMapping(value = "/site/main")
    public String siteMainView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        menuService.getMainList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_main";
    }

    /* 사이트 - 메인 이미지 등록 화면 */
    @GetMapping(value = "/site/main/add")
    public String siteMainAddView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_main_detail";
    }

    /* 사이트 - 메인 이미지 상세 화면 */
    @GetMapping(value = "/site/main/{miIdx}")
    public String siteMainDetailView(@PathVariable int miIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("miIdx",miIdx);

        menuService.getMainImg(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_main_detail";
    }

    /* 사이트 - 메인 이미지 내용 등록 */
    @PostMapping(value = "/api/v1.0/insertMain.json")
    @ResponseBody
    public Object insertMain(@RequestBody DochaAdminMainImgRequest mainImgRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("mainImgRequest", mainImgRequest);

        menuService.insertMain(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 메인 이미지 등록 */
    @PostMapping(value = "/api/v1.0/uploadMainImage.do")
    @ResponseBody
    public Object uploadMainImage(@RequestParam("image") MultipartFile uploadImage, String miIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("miIdx", miIdx);

        menuService.uploadMainImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 메인 이미지 삭제 */
    @PostMapping(value = "/api/v1.0/deleteMainImg.json")
    @ResponseBody
    public Object deleteMainImg(@RequestBody DochaAdminMainImgRequest mainImgRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("mainImgRequest", mainImgRequest);

        menuService.deleteMainImg(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 쿠폰 화면 */
    @GetMapping(value = "/site/coupon")
    public String siteCouponView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_coupon";
    }

    /* 사이트 - 포인트 화면 */
    @GetMapping(value = "/site/point")
    public String sitePointView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_point";
    }

    /* 사이트 - 이용후기 화면 */
    @GetMapping(value = "/site/review")
    public String siteReviewView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        menuService.getEventList(serviceMessage);
        modelMap.addAllAttributes(serviceMessage);
        return "site/site_review";
    }

    /* 사이트 - 고객평점 화면 */
    @GetMapping(value = "/site/rating")
    public String cuView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_rating";
    }

    /* 사이트 - 이벤트 화면 */
    @GetMapping(value = "/site/event")
    public String siteEventView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        menuService.getEventList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_event";
    }

    /* 사이트 - 이벤트 등록 화면 */
    @GetMapping(value = "/site/event/add")
    public String siteEventAddView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_event_detail";
    }

    /* 사이트 - 메인 이벤트 내용 등록 */
    @PostMapping(value = "/api/v1.0/insertEvent.json")
    @ResponseBody
    public Object insertEvent(@RequestBody DochaAdminEventRequest eventRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("eventRequest", eventRequest);

        menuService.insertEvent(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 이벤트 이미지 등록 */
    @PostMapping(value = "/api/v1.0/uploadEventImage.do")
    @ResponseBody
    public Object uploadEventImage(@RequestParam("image") MultipartFile uploadImage, int evIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("evIdx", evIdx);

        menuService.uploadEventImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 이벤트 이미지 리스트 등록 */
    @PostMapping(value = "/api/v1.0/uploadEventListImage.do")
    @ResponseBody
    public Object uploadEventListImage(@RequestParam("image") MultipartFile uploadImage, int evIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("evIdx", evIdx);

        menuService.uploadEventListImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 이벤트 이미지 배너 등록 */
    @PostMapping(value = "/api/v1.0/uploadEventBannerImage.do")
    @ResponseBody
    public Object uploadEventBannerImage(@RequestParam("image") MultipartFile uploadImage, int evIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("evIdx", evIdx);

        menuService.uploadEventBannerImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 이벤트 상세 화면 */
    @GetMapping(value = "/site/event/{evIdx}")
    public String siteEventDetailView(@PathVariable String evIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("evIdx",evIdx);

        menuService.getEventDetail(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_event_detail";
    }

    /* 사이트 - 이벤트 삭제 */
    @PostMapping(value = "/api/v1.0/deleteEvent.json")
    @ResponseBody
    public Object deleteEvent(@RequestBody DochaAdminEventRequest eventRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("eventRequest", eventRequest);

        menuService.deleteEvent(serviceMessage);

        return serviceMessage;
    }


    /* 사이트 - 문의 화면 */
    @GetMapping(value = "/site/question")
    public String siteQuestionView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        menuService.getQuestionList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_question";
    }

    /* 사이트 - 문의 상세 화면 */
    @GetMapping(value = "/site/question/{quIdx}")
    public String siteQuestionDetailView(@PathVariable String quIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("quIdx",quIdx);

        menuService.getQuestionDetail(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_question_detail";
    }

    /* 사이트 - 문의 답변등록 */
    @PostMapping(value = "/api/v1.0/updateAnswer.json")
    @ResponseBody
    public Object updateAnswer(@RequestBody DochaAdminQuestionRequest questionRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("questionRequest", questionRequest);

        menuService.updateAnswer(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 문의 답변 이미지 등록 */
    @PostMapping(value = "/api/v1.0/uploadQuestionImage.do")
    @ResponseBody
    public Object uploadQuestionImage(@RequestParam("image") MultipartFile uploadImage, int quIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("quIdx", quIdx);

        menuService.uploadQuestionImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 공지사항 화면 */
    @GetMapping(value = "/site/notice")
    public String siteNoticeView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        menuService.getNoticeList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_notice";
    }

    /* 사이트 - 공지사항 등록 화면 */
    @GetMapping(value = "/site/notice/add")
    public String siteNoticeAddView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_notice_detail";
    }

    /* 사이트 - 공지사항 상세 화면 */
    @GetMapping(value = "/site/notice/{ntIdx}")
    public String siteNoticeDetailView(@PathVariable int ntIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("ntIdx",ntIdx);

        menuService.getNoticeDetail(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_notice_detail";
    }

    /* 사이트 - 공지사항 등록 */
    @PostMapping(value = "/api/v1.0/insertNotice.json")
    @ResponseBody
    public Object insertNotice(@RequestBody DochaAdminNoticeRequest noticeRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("noticeRequest", noticeRequest);

        menuService.insertNotice(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 공지사항 이미지 등록 */
    @PostMapping(value = "/api/v1.0/uploadNoticeImage.do")
    @ResponseBody
    public Object uploadNoticeImage(@RequestParam("image") MultipartFile uploadImage, String ntIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("ntIdx", ntIdx);

        menuService.uploadNoticeImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - 공지사항 삭제 */
    @PostMapping(value = "/api/v1.0/deleteNotice.json")
    @ResponseBody
    public Object deleteNotice(@RequestBody DochaAdminNoticeRequest noticeRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("noticeRequest", noticeRequest);

        menuService.deleteNotice(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - FAQ 화면 */
    @GetMapping(value = "/site/FAQ")
    public String siteFAQView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        menuService.getFAQList(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_FAQ";
    }

    /* 사이트 - FAQ 등록 화면 */
    @GetMapping(value = "/site/faq/add")
    public String siteFAQAddView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_FAQ_detail";
    }

    /* 사이트 - FAQ 상세 화면 */
    @GetMapping(value = "/site/faq/{faIdx}")
    public String siteFAQDetailView(@PathVariable int faIdx, HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("faIdx",faIdx);

        menuService.getFAQDetail(serviceMessage);

        modelMap.addAllAttributes(serviceMessage);
        return "site/site_FAQ_detail";
    }

    /* 사이트 - FAQ 등록 */
    @PostMapping(value = "/api/v1.0/insertFAQ.json")
    @ResponseBody
    public Object insertFAQ(@RequestBody DochaAdminFAQRequest faqRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("faqRequest", faqRequest);

        menuService.insertFAQ(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - FAQ 이미지 등록 */
    @PostMapping(value = "/api/v1.0/uploadFAQImage.do")
    @ResponseBody
    public Object uploadFAQImage(@RequestParam("image") MultipartFile uploadImage, String faIdx, HttpServletRequest request) {
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("uploadImage", uploadImage)
                .addData("faIdx", faIdx);

        menuService.uploadFAQImage(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - FAQ 삭제 */
    @PostMapping(value = "/api/v1.0/deleteFAQ.json")
    @ResponseBody
    public Object deleteFAQ(@RequestBody DochaAdminFAQRequest faqRequest, HttpServletRequest request){
        ServiceMessage serviceMessage = createServiceMessage(request);
        serviceMessage.addData("faqRequest", faqRequest);

        menuService.deleteFAQ(serviceMessage);

        return serviceMessage;
    }

    /* 사이트 - notice 섬머노트 이미지 */
    @PostMapping(value="/noticeuploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public String noticeuploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

       // String fileRoot = "C:\\docha_mobility-k-admin2020-12-21/src/main/resources";	//저장될 외부 파일 경로
        String fileRoot = "file:///C:/summer/";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명


        File file = new File(properties.getTempFolderPath() + "sumnotice/" + savedFileName); //서버일경우
      //  File file = new File(properties.getTempFolderPath() + "summernotelocal/" + savedFileName);  //로컬일경우

        FileHelper.makeFolder(file.getParentFile());

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            jsonObject.addProperty("url", "https://admin.docha.co.kr/img/sumnotice/" + savedFileName); //서버일경우
      //        jsonObject.addProperty("url", fileRoot + "summernotelocal/"+ savedFileName);  //로컬일경우

        } catch (Exception e) {
            throw new BadRequestException(9998, "파일 생성 실패");
        }


        return new Gson().toJson(jsonObject);
    }

    /* 사이트 - faq 섬머노트 이미지 */
    @PostMapping(value="/faquploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public String faquploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        // String fileRoot = "C:\\docha_mobility-k-admin2020-12-21/src/main/resources";	//저장될 외부 파일 경로
        String fileRoot = "file:///C:/summer/";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명


         File file = new File(properties.getTempFolderPath() + "sumfaq/" + savedFileName); //서버일경우
      //  File file = new File(properties.getTempFolderPath() + "summernotelocal/" + savedFileName);  //로컬일경우

        FileHelper.makeFolder(file.getParentFile());

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            jsonObject.addProperty("url", "https://admin.docha.co.kr/img/sumfaq/" + savedFileName); //서버일경우
        //    jsonObject.addProperty("url", fileRoot + "summernotelocal/"+ savedFileName);  //로컬일경우

        } catch (Exception e) {
            throw new BadRequestException(9998, "파일 생성 실패");
        }


        return new Gson().toJson(jsonObject);
    }

    /* 사이트 - 문의 섬머노트 이미지 */
    @PostMapping(value="/questionuploadSummernoteImageFile", produces = "application/json")
    @ResponseBody
    public String questionuploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {

        JsonObject jsonObject = new JsonObject();

        // String fileRoot = "C:\\docha_mobility-k-admin2020-12-21/src/main/resources";	//저장될 외부 파일 경로
        String fileRoot = "file:///C:/summer/";	//저장될 외부 파일 경로
        String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자

        String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명


        File file = new File(properties.getTempFolderPath() + "sumquestion/" + savedFileName); //서버일경우
        //  File file = new File(properties.getTempFolderPath() + "summernotelocal/" + savedFileName);  //로컬일경우

        FileHelper.makeFolder(file.getParentFile());

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
            jsonObject.addProperty("url", "https://admin.docha.co.kr/img/sumquestion/" + savedFileName); //서버일경우
            //    jsonObject.addProperty("url", fileRoot + "summernotelocal/"+ savedFileName);  //로컬일경우

        } catch (Exception e) {
            throw new BadRequestException(9998, "파일 생성 실패");
        }


        return new Gson().toJson(jsonObject);
    }

    // endregion

    // region [ 통계 ]

    @GetMapping(value = "/stats")
    public String statisticsView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "statistics";
    }

    // endregion

    // region [ 로그 ]

    @GetMapping(value = "/log")
    public String logView(HttpServletRequest request, ModelMap modelMap) {
        ServiceMessage serviceMessage = createServiceMessage(request);

        modelMap.addAllAttributes(serviceMessage);
        return "log";
    }

    // endregion
}
