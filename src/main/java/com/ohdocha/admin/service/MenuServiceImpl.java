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
    public void getMainList(ServiceMessage message) {
        DochaAdminMainImgRequest mainImgRequest = new DochaAdminMainImgRequest();

        List<DochaAdminMainImgResponse> mainImgResponseList = menuMapper.selectMainImgList(mainImgRequest);

        message.addData("result", mainImgResponseList);
    }

    @Override
    public void getMainImg(ServiceMessage message) {
        DochaAdminMainImgRequest mainImgRequest = new DochaAdminMainImgRequest();

        Integer miIdx = message.getInt("miIdx");
        mainImgRequest.setMiIdx(miIdx);

        List<DochaAdminMainImgResponse> mainImgResponseList = menuMapper.selectMainImgList(mainImgRequest);

        message.addData("result", mainImgResponseList);
    }

    @Override
    public void insertMain(ServiceMessage message) {
        DochaAdminMainImgRequest mainImgRequest = message.getObject("mainImgRequest", DochaAdminMainImgRequest.class);

        int res = 0;

        if (mainImgRequest.getMiIdx() == 0) {
            res = menuMapper.insertMainImg(mainImgRequest);
        } else {
            res = menuMapper.updateMainImg(mainImgRequest);
        }

        message.addData("res", res);
    }

    @Override
    public void uploadMainImage(ServiceMessage message) {
        String miIdx = message.getString("miIdx");
        DochaAdminMainImgResponse mainImgResponse;

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
        File file = new File(properties.getTempFolderPath() + "main/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 메인화면 이미지 조회
        DochaAdminMainImgRequest mainImgRequest = new DochaAdminMainImgRequest();
        mainImgRequest.setMiIdx(Integer.parseInt(miIdx));

        List<DochaAdminMainImgResponse> mainImgResponseList = menuMapper.selectMainImgList(mainImgRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        mainImgResponse = mainImgResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (mainImgResponse.getMiImgIdx() == null || mainImgResponse.getMiImgIdx().equals("")) {
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
            File FileList = new File(properties.getTempFolderPath() + "main/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(mainImgResponse.getMiImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "main/" + mainImgResponse.getMiImgIdx());
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

        DochaAdminMainImgRequest updateMainImgRequest = new DochaAdminMainImgRequest();

        // 저장 할 miIdx
        updateMainImgRequest.setMiIdx(mainImgRequest.getMiIdx());
        // 새로운 파일 명
        updateMainImgRequest.setMiImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateMainImg(updateMainImgRequest);
    }

    @Override
    public void deleteMainImg(ServiceMessage message) {
        DochaAdminMainImgRequest mainImgRequest = message.getObject("mainImgRequest", DochaAdminMainImgRequest.class);

        int res = menuMapper.deleteMainImg(mainImgRequest);

        message.addData("res", res);
    }

    @Override
    public void getEventList(ServiceMessage message) {
        DochaAdminEventRequest eventRequest = new DochaAdminEventRequest();

        List<DochaAdminEventResponse> questionResponseList = menuMapper.selectEventList(eventRequest);

        message.addData("result", questionResponseList);
    }

    @Override
    public void insertEvent(ServiceMessage message) {
        DochaAdminEventRequest eventRequest = message.getObject("eventRequest", DochaAdminEventRequest.class);

        int res = 0;

        if (eventRequest.getEvIdx() == 0) {
            res = menuMapper.insertEvent(eventRequest);
        } else {
            res = menuMapper.updateEvent(eventRequest);
        }

        message.addData("res", res);
    }

    @Override
    public void uploadEventImage(ServiceMessage message) {
        int evIdx = message.getInt("evIdx", 0);
        DochaAdminEventResponse eventResponse;

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
        File file = new File(properties.getTempFolderPath() + "event/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 이벤트 조회
        DochaAdminEventRequest eventRequest = new DochaAdminEventRequest();
        eventRequest.setEvIdx(evIdx);

        List<DochaAdminEventResponse> eventResponseList = menuMapper.selectEventList(eventRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        eventResponse = eventResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (eventResponse.getEvImgIdx() == null || eventResponse.getEvImgIdx().equals("")) {
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
            File FileList = new File(properties.getTempFolderPath() + "event/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(eventResponse.getEvImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "event/" + eventResponse.getEvImgIdx());
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

        DochaAdminEventRequest updateEventRequest = new DochaAdminEventRequest();

        // 저장 할 evIdx
        updateEventRequest.setEvIdx(eventRequest.getEvIdx());
        // 새로운 파일 명
        updateEventRequest.setEvImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateEvent(updateEventRequest);
    }

    @Override
    public void uploadEventListImage(ServiceMessage message) {
        int evIdx = message.getInt("evIdx", 0);
        DochaAdminEventResponse eventResponse;

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
        File file = new File(properties.getTempFolderPath() + "event/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 이벤트 조회
        DochaAdminEventRequest eventRequest = new DochaAdminEventRequest();
        eventRequest.setEvIdx(evIdx);

        List<DochaAdminEventResponse> eventResponseList = menuMapper.selectEventList(eventRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        eventResponse = eventResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (eventResponse.getEvListImgIdx() == null || eventResponse.getEvListImgIdx().equals("")) {
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
            File FileList = new File(properties.getTempFolderPath() + "event/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(eventResponse.getEvImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "event/" + eventResponse.getEvImgIdx());
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

        DochaAdminEventRequest updateEventRequest = new DochaAdminEventRequest();

        // 저장 할 evIdx
        updateEventRequest.setEvIdx(eventRequest.getEvIdx());
        // 새로운 파일 명
        updateEventRequest.setEvListImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateEvent(updateEventRequest);
    }

    @Override
    public void uploadEventBannerImage(ServiceMessage message) {
        int evIdx = message.getInt("evIdx", 0);
        DochaAdminEventResponse eventResponse;

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
        File file = new File(properties.getTempFolderPath() + "event/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 이벤트 조회
        DochaAdminEventRequest eventRequest = new DochaAdminEventRequest();
        eventRequest.setEvIdx(evIdx);

        List<DochaAdminEventResponse> eventResponseList = menuMapper.selectEventList(eventRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        eventResponse = eventResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (eventResponse.getEvBannerImgIdx() == null || eventResponse.getEvBannerImgIdx().equals("")) {
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
            File FileList = new File(properties.getTempFolderPath() + "event/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(eventResponse.getEvImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "event/" + eventResponse.getEvImgIdx());
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

        DochaAdminEventRequest updateEventRequest = new DochaAdminEventRequest();

        // 저장 할 evIdx
        updateEventRequest.setEvIdx(eventRequest.getEvIdx());
        // 새로운 파일 명
        updateEventRequest.setEvBannerImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateEvent(updateEventRequest);
    }

    @Override
    public void getEventDetail(ServiceMessage message) {
        DochaAdminEventRequest eventRequest = new DochaAdminEventRequest();

        String evIdx = message.getString("evIdx");
        eventRequest.setEvIdx(Integer.parseInt(evIdx));

        List<DochaAdminEventResponse> eventResponseList = menuMapper.selectEventList(eventRequest);

        message.addData("result", eventResponseList);
    }

    @Override
    public void deleteEvent(ServiceMessage message) {
        DochaAdminEventRequest eventRequest = message.getObject("eventRequest", DochaAdminEventRequest.class);

        int res = menuMapper.deleteEvent(eventRequest);

        message.addData("res", res);
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
    public void insertNotice(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = message.getObject("noticeRequest", DochaAdminNoticeRequest.class);

        int res = 0;

        if (noticeRequest.getNtIdx() == 0) {
            res = menuMapper.insertNotice(noticeRequest);
        } else {
            res = menuMapper.updateNotice(noticeRequest);
        }

        message.addData("res", res);
    }

    @Override
    public void deleteNotice(ServiceMessage message) {
        DochaAdminNoticeRequest noticeRequest = message.getObject("noticeRequest", DochaAdminNoticeRequest.class);

        int res = menuMapper.deleteNotice(noticeRequest);

        message.addData("res", res);
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

    @Override
    public void getFAQList(ServiceMessage message) {
        DochaAdminFAQRequest faqRequest = new DochaAdminFAQRequest();

        List<DochaAdminFAQResponse> faqResponseList = menuMapper.selectFAQList(faqRequest);

        message.addData("result", faqResponseList);
    }

    @Override
    public void getFAQDetail(ServiceMessage message) {
        DochaAdminFAQRequest faqRequest = new DochaAdminFAQRequest();

        int faIdx = message.getInt("faIdx", 0);
        faqRequest.setFaIdx(faIdx);

        List<DochaAdminFAQResponse> faqResponseList = menuMapper.selectFAQList(faqRequest);

        message.addData("result", faqResponseList);
    }

    @Override
    public void insertFAQ(ServiceMessage message) {
        DochaAdminFAQRequest faqRequest = message.getObject("faqRequest", DochaAdminFAQRequest.class);

        int res = 0;

        if (faqRequest.getFaIdx() == 0){
            res = menuMapper.insertFAQ(faqRequest);
        }else {
            res = menuMapper.updateFAQ(faqRequest);
        }

        message.addData("res",res);
    }


    @Override
    public void uploadFAQImage(ServiceMessage message) {
        String faIdx = message.getString("faIdx");
        DochaAdminFAQResponse faqResponse;

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
        File file = new File(properties.getTempFolderPath() + "faq/" + saveImgName + "." + uploadImageExtension);
        FileHelper.makeFolder(file.getParentFile());

        // 기존의 공지사항 조회
        DochaAdminFAQRequest faqRequest = new DochaAdminFAQRequest();
        faqRequest.setFaIdx(Integer.parseInt(faIdx));

        List<DochaAdminFAQResponse> faqResponseList = menuMapper.selectFAQList(faqRequest);

        // 해당 모델의 정보를 가져옴 ( 이미지 파일 체크하기 위함 )
        faqResponse = faqResponseList.get(0);

        // 이미 DB에 img 정보가 있는지 여부
        if (faqResponse.getImgIdx() == null || faqResponse.getImgIdx().equals("")) {
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
            File FileList = new File(properties.getTempFolderPath() + "faq/");
            String[] fileList = FileList.list();
            for (int i = 0; i < fileList.length; i++) {
                // DB에서 파일 명을 가져와서 일치하는 것이 있는지 검사
                String FileName = fileList[i];

                if (FileName.contains(faqResponse.getImgIdx())) {
                    File deleteFile = new File(properties.getTempFolderPath() + "faq/" + faqResponse.getImgIdx());
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

        DochaAdminFAQRequest updateFAQRequest = new DochaAdminFAQRequest();

        // 저장 할 ntIdx
        updateFAQRequest.setFaIdx(faqRequest.getFaIdx());
        // 새로운 파일 명
        updateFAQRequest.setImgIdx(saveImgName + "." + uploadImageExtension);

        // 파일을 path에 저장 후, DB에 파일 명 저장
        menuMapper.updateFAQ(updateFAQRequest);
    }


    @Override
    public void deleteFAQ(ServiceMessage message) {
        DochaAdminFAQRequest faqRequest = message.getObject("faqRequest", DochaAdminFAQRequest.class);

        int res = menuMapper.deleteFAQ(faqRequest);

        message.addData("res",res);
    }
}
