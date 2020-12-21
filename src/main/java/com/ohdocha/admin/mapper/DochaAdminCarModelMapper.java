package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.car.model.DochaAdminCarModelDetailRequest;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelDetailResponse;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelRequest;
import com.ohdocha.admin.domain.car.model.DochaAdminCarModelResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminCarModelMapper {

    //차량모델 list 조회
    public List<DochaAdminCarModelResponse> selectCarModelInfo(DochaAdminCarModelRequest reqParam);

    //차량-차량모델 상세
    public List<DochaAdminCarModelDetailResponse> selectCarModelDetail(DochaAdminCarModelDetailRequest reqParam);

    public DochaAdminCarModelDetailResponse selectCarModelImg(int mdIdx);

    public int updateCarModelImg(DochaAdminCarModelDetailRequest carModelDetailRequest);


    public List<DochaAdminCarModelDetailResponse> selectCarModelForSelectBox(DochaAdminCarModelDetailRequest reqParam);

    public List<DochaAdminCarModelDetailResponse> selectYearForCarModelSelectBox(DochaAdminCarModelDetailRequest reqParam);

    public List<DochaAdminCarModelDetailResponse> selectCarModelDetailForSelectBox(DochaAdminCarModelDetailRequest reqParam);

    //차량-차량모델  수정
    public int updateCarModelInfo(DochaAdminCarModelDetailRequest reqParam);

    //차량-차량모델  신규
    public int insertCarModelInfo(DochaAdminCarModelDetailRequest reqParam);

    public int deleteCarModelInfo(DochaAdminCarModelDetailRequest carModelDetailRequest);
}
