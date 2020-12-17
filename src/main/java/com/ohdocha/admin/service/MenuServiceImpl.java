package com.ohdocha.admin.service;

import com.ohdocha.admin.config.Properties;
import com.ohdocha.admin.domain.menu.*;
import com.ohdocha.admin.domain.reserve.matchingService.DochaAlarmTalkDto;
import com.ohdocha.admin.exception.BadRequestException;
import com.ohdocha.admin.mapper.DochaAdminMenuMapper;
import com.ohdocha.admin.util.DochaAlarmTalkMsgUtil;
import com.ohdocha.admin.util.DochaTemplateCodeProvider;
import com.ohdocha.admin.util.FileHelper;
import com.ohdocha.admin.util.ServiceMessage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class MenuServiceImpl extends ServiceExtension implements MenuService {

    @Autowired
    private final DochaAlarmTalkMsgUtil alarmTalk;
    private final Properties properties;
    private final DochaAdminMenuMapper menuMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void getMenuList(ServiceMessage message) {
        DochaAdminMenuRequest menuRequest = message.getObject("menuRequest", DochaAdminMenuRequest.class);

        List<DochaAdminMenuResponse> menuResponseList = menuMapper.selectMenuInfoList(menuRequest);

        message.addData("result", menuResponseList);
    }

    @Override
    public void getQuestionList(ServiceMessage message) {
        DochaAdminQuestionRequest questionRequest = new DochaAdminQuestionRequest();

        List<DochaAdminQuestionResponse> questionResponseList = menuMapper.selectQuestionList(questionRequest);

        message.addData("result", questionResponseList);
    }

    @Override
    public void getQuestionDetail(ServiceMessage message) {
        DochaAdminQuestionRequest questionRequest = new DochaAdminQuestionRequest();

        String quIdx = message.getString("quIdx");
        questionRequest.setQuIdx(Integer.parseInt(quIdx));

        List<DochaAdminQuestionResponse> questionResponseList = menuMapper.selectQuestionList(questionRequest);

        message.addData("result", questionResponseList);
    }

    @Override
    public void getNoticeList(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = new DochaAdminNoticeRequest();

        List<DochaAdminNoticeResponse> noticeResponseList = menuMapper.selectNoticeList(noticeRequest);

        message.addData("result", noticeResponseList);
    }

    @Override
    public void getNoticeDetail(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = new DochaAdminNoticeRequest();

        int ntIdx = message.getInt("ntIdx", 0);
        noticeRequest.setNtIdx(ntIdx);

        List<DochaAdminNoticeResponse> noticeResponseList = menuMapper.selectNoticeList(noticeRequest);

        message.addData("result", noticeResponseList);
    }

    @Override
    public void updateAnswer(ServiceMessage message) {
        DochaAdminQuestionRequest questionRequest = message.getObject("questionRequest", DochaAdminQuestionRequest.class);

        int res = menuMapper.updateAnswer(questionRequest);

        List<DochaAdminQuestionResponse> questionResponseList = menuMapper.selectQuestionList(questionRequest);

        if (questionRequest.getQuAnswerYn() != null) {
            if (res > 0 && questionRequest.getQuAnswerYn().equals("1")) {
                try {
                    // 문의 알림톡발송
                    DochaAlarmTalkDto dto = new DochaAlarmTalkDto();
                    dto.setPhone(questionResponseList.get(0).getQuestionerPhone());//알림톡 전송할 번호
                    dto.setTemplateCode(DochaTemplateCodeProvider.A000008.getCode());

                    HttpResponse<JsonNode> response = alarmTalk.sendAlramTalk(dto);
                    if (response.getStatus() == 200) {
                        logger.info("AlarmTalk Send Compelite");
                        logger.info(response.getBody().toPrettyString());
                    } else {
                        logger.info("AlarmTalk Send Fail");
                        logger.error(response.getBody().toPrettyString());
                    }
                } catch (Exception ex) {
                    //알림톡 발송을 실패하더라도 오류발생시키지 않고 결제처리 완료를 위해 오류는 catch에서 로깅처리만 함
                    logger.error("Error", ex);
                }
            }
        }

        message.addData("res", res);
    }

    @Override
    public void insertNotice(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = message.getObject("noticeRequest", DochaAdminNoticeRequest.class);

        int res = 0;

//        if (Integer.toString(noticeRequest.getNtIdx()).equals("")){
        if (noticeRequest.getNtIdx() == 0){
            res = menuMapper.insertNotice(noticeRequest);
        }else {
            res = menuMapper.updateNotice(noticeRequest);
        }

        message.addData("res",res);
    }

    @Override
    public void deleteNotice(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = message.getObject("noticeRequest", DochaAdminNoticeRequest.class);

        int res = menuMapper.deleteNotice(noticeRequest);

        message.addData("res",res);
    }

    @Override
    public void uploadQuestionImage(ServiceMessage message) {
        int quIdx = message.getInt("quIdx", 0);
        DochaAdminQuestionResponse questionResponse;

        Object uploadImageObj = message.get("uploadImage");
        if (!(uploadImageObj instanceof MultipartFile))
            throw new BadRequestException(IMAGE_NOT_MULTIPART_FILE, IMAGE_NOT_MULTIPART_FILE_MSG);

        MultipartFile uploadImage = (MultipartFile) uploadImageObj;

        if (uploadImage.isEmpty())
            throw new BadRequestException(IMAGE_IS_EMPTY, IMAGE_IS_EMPTY_MSG);

        String uploadImageName = uploadImage.getOriginalFilename();
        if (uploadImageName == null || uploadImageName.isEmpty())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 파일이름이 없습니다.)");

        String uploadImageMime = uploadImage.getContentType();
        if (uploadImageMime == null || uploadImageMime.isEmpty() || !uploadImageMime.contains("image/"))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 MIME 이 올바르지 않습니다.)");

        int extensionIndexOf = uploadImageName.lastIndexOf('.');
        if (extensionIndexOf == -1)
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(확장자가 존재하지 않습니다.)");

        String uploadImageExtension = uploadImageName.substring(extensionIndexOf).replaceAll("\\.", "").toLowerCase();
        if (!properties.getSupportImageExtension().contains(uploadImageExtension))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(지원하지 않는 이미지 확장자 입니다.)");

        long uploadImageSize = uploadImage.getSize();
        if (uploadImageSize > properties.getUploadImageSize())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 크기가 20MB를 초과 합니다.)");

        // 파일 랜덤 UUID 생성 (파일 명 중복시 파일 생성 안됌)
        String saveImgName = UUID.randomUUID().toString();
        File file = new File(properties.getTempFolderPath() + "question/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 문의 조회
        DochaAdminQuestionRequest questionRequest = new DochaAdminQuestionRequest();
        questionRequest.setQuIdx(quIdx);

        List<DochaAdminQuestionResponse> questionResponseList = menuMapper.selectQuestionList(questionRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        questionResponse = questionResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (questionResponse.getImgIdx() == null || questionResponse.getImgIdx().equals("")) {
            // 저장된 이미지가 없을 경우
            try {
                // 바로 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        } else {
            // 현재 DB에 이미지가 있으면
            File FileList = new File(properties.getTempFolderPath() + "question/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(questionResponse.getImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "question/" + questionResponse.getImgIdx());
                    // path에서 이미 있는 파일을 제거 후
                    deleteFile.delete();
                }
            }
            try {
                // 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        }

        DochaAdminQuestionRequest updateQuestionRequest = new DochaAdminQuestionRequest();

        // 저장 할 quIdx
        updateQuestionRequest.setQuIdx(questionRequest.getQuIdx());
        // 새로운 파일 명
        updateQuestionRequest.setImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateAnswer(updateQuestionRequest);
    }

    @Override
    public void uploadNoticeImage(ServiceMessage message) {
        String ntIdx = message.getString("ntIdx");
        DochaAdminNoticeResponse noticeResponse;

        Object uploadImageObj = message.get("uploadImage");
        if (!(uploadImageObj instanceof MultipartFile))
            throw new BadRequestException(IMAGE_NOT_MULTIPART_FILE, IMAGE_NOT_MULTIPART_FILE_MSG);

        MultipartFile uploadImage = (MultipartFile) uploadImageObj;

        if (uploadImage.isEmpty())
            throw new BadRequestException(IMAGE_IS_EMPTY, IMAGE_IS_EMPTY_MSG);

        String uploadImageName = uploadImage.getOriginalFilename();
        if (uploadImageName == null || uploadImageName.isEmpty())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 파일이름이 없습니다.)");

        String uploadImageMime = uploadImage.getContentType();
        if (uploadImageMime == null || uploadImageMime.isEmpty() || !uploadImageMime.contains("image/"))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 MIME 이 올바르지 않습니다.)");

        int extensionIndexOf = uploadImageName.lastIndexOf('.');
        if (extensionIndexOf == -1)
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(확장자가 존재하지 않습니다.)");

        String uploadImageExtension = uploadImageName.substring(extensionIndexOf).replaceAll("\\.", "").toLowerCase();
        if (!properties.getSupportImageExtension().contains(uploadImageExtension))
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(지원하지 않는 이미지 확장자 입니다.)");

        long uploadImageSize = uploadImage.getSize();
        if (uploadImageSize > properties.getUploadImageSize())
            throw new BadRequestException(IMAGE_PARSING_ERROR, IMAGE_PARSING_ERROR_MSG + "(이미지 크기가 20MB를 초과 합니다.)");

        // 파일 랜덤 UUID 생성 (파일 명 중복시 파일 생성 안됌)
        String saveImgName = UUID.randomUUID().toString();
        File file = new File(properties.getTempFolderPath() + "notice/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 공지사항 조회
        DochaAdminNoticeRequest noticeRequest = new DochaAdminNoticeRequest();
        noticeRequest.setNtIdx(Integer.parseInt(ntIdx));

        List<DochaAdminNoticeResponse> noticeResponseList = menuMapper.selectNoticeList(noticeRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        noticeResponse = noticeResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (noticeResponse.getImgIdx() == null || noticeResponse.getImgIdx().equals("")) {
            // 저장된 이미지가 없을 경우
            try {
                // 바로 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        } else {
            // 현재 DB에 이미지가 있으면
            File FileList = new File(properties.getTempFolderPath() + "notice/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(noticeResponse.getImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "notice/" + noticeResponse.getImgIdx());
                    // path에서 이미 있는 파일을 제거 후
                    deleteFile.delete();
                }
            }
            try {
                // 이미지 생성
                file.createNewFile();
                uploadImage.transferTo(file);
            } catch (Exception e) {
                throw new BadRequestException(UNKNOWN_EXCEPTION, "파일 생성 실패");
            }
        }

        DochaAdminNoticeRequest updateNoticeRequest = new DochaAdminNoticeRequest();

        // 저장 할 ntIdx
        updateNoticeRequest.setNtIdx(noticeRequest.getNtIdx());
        // 새로운 파일 명
        updateNoticeRequest.setImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateNotice(updateNoticeRequest);
    }

}
