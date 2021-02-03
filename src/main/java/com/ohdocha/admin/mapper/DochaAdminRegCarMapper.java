package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.car.plan.basicplan.DochaAdminBaiscPlanDetailRequest;
import com.ohdocha.admin.domain.car.plan.insuranceTemplate.DochaAdminInsuranceTemplateDetailRequest;
import com.ohdocha.admin.domain.car.regcar.*;
import com.ohdocha.admin.domain.menu.DochaAdminFAQRequest;
import com.ohdocha.admin.domain.rentCompany.DochaHolidayDto;
import com.ohdocha.admin.domain.reserve.payment.DochaPaymentPeriodDto;
import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminRegCarMapper {

    //등록차량 list 조회
    public List<DochaAdminRegCarResponse> selectRegCarInfo(DochaAdminRegCarRequest reqParam);

    //등록차량 상세 조회
    public List<DochaAdminRegCarDetailResponse> selectRegCarDetail(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 상세 조회
    public List<DochaAdminRegCarDetailResponse> selectRentCompanyCarList(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 상세 옵션
    public List<DochaAdminDcCarInfoOption> selectRegCarDetailOption(DochaAdminRegCarDetailRequest reqParam);

    public int deleteRegCarDetailOption(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 저장
    public int insertDcCarInfo(DochaAdminRegCarDetailRequest reqParam);

    //등록차량 저장
    public int insertRegCarInfoOption(DochaAdminDcCarInfoOption reqParam);

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

    // 기간 요금제 검색
    public List<DochaPaymentPeriodDto> selectPeriodPaymentList(DochaMap paramMap);

    // 기간 요금제 요금 검색
    public List<DochaPaymentPeriodDto> selectPeriodPaymentListOnDaily(DochaMap paramMap);

    // 요금 계산에 필요한 휴무일 가져옴
    public List<DochaHolidayDto> selectHolidayList(DochaMap paramMap);

    // 기본 요금제 삭제
    int deleteBasicPayment(DochaAdminBaiscPlanDetailRequest deleteBasicPlanDetailRequest);

}


