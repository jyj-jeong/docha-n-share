package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBaiscPlanDetailRequest;
import com.ohdocha.admin.domain.car.plan.insuranceTemplate.DochaAdminInsuranceTemplateDetailRequest;
import com.ohdocha.admin.domain.car.regcar.DochaAdminRegCarDetailRequest;
import com.ohdocha.admin.domain.car.regcar.DochaAdminRegCarDetailResponse;
import com.ohdocha.admin.domain.car.regcar.DochaAdminRegCarRequest;
import com.ohdocha.admin.domain.car.regcar.DochaAdminRegCarResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminRegCarMapper {

    //등록차량 list 조회
    public List<DochaAdminRegCarResponse> selectRegCarInfo(DochaAdminRegCarRequest reqParam);

    //등록차량 상세 조회
    public List<DochaAdminRegCarDetailResponse> selectRegCarDetail(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 상세 옵션
    public List<DochaAdminRegCarMapper> selectRegCarDetailOption(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 저장
    public int insertDcCarInfo(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 사진 수정
    public int updateRegCarImg(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 사진 수정 ( 모델에서 수정 시 )
    public int updateRegCarImgByMdIdx(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 보험 추가
    public int insertRegCarInsurance(DochaAdminInsuranceTemplateDetailRequest reqParam);

    //등록차량 보험 조회 ( 차량 1:1 )
    public int countRegCarInsuranceInfo(DochaAdminInsuranceTemplateDetailRequest insuranceTemplateDetailRequest);

    //등록차량 보험 수정
    public int updateRegCarInsuranceInfo(DochaAdminInsuranceTemplateDetailRequest insuranceTemplateDetailRequest);

    //등록차량 요금 조회 ( 차량 1:1 )
    public int countRegCarPaymentInfo(DochaAdminBaiscPlanDetailRequest basicPlanDetailRequest);

    //등록차량 요금제 추가
    public int insertRegCarPayment(DochaAdminBaiscPlanDetailRequest reqParam);

    //등록차량 요금 수정
    public int updateRegCarPaymentInfo(DochaAdminBaiscPlanDetailRequest insuranceTemplateDetailRequest);

    //등록차량 수정
    public int updateDcCarInfo(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 요금계산기
    public List<DochaAdminRegCarDetailResponse> selectReserveAmt(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 휴차일등록
    public int insertDcCarInfoSuspend(DochaAdminRegCarDetailRequest reqParam);

}


