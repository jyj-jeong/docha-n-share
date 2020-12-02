package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.car.property.DochaAdminCarPropertyRequest;
import com.ohdocha.admin.domain.car.property.DochaAdminCarPropertyResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminCarPropertyMapper {

    //차량속성 추가 (국가)
    public int insertCarProperty(DochaAdminCarPropertyRequest reqParam);


    //차량속성 조회 (국가)
    public List<DochaAdminCarPropertyResponse> selectCarCountryPropertyInfo(DochaAdminCarPropertyRequest reqParam);

	//차량속성 조회 (제조사)
	public List<DochaAdminCarPropertyResponse> selectCarCountryManufacturerInfo(DochaAdminCarPropertyRequest reqParam);

	//차량속성 조회 (등급)
	public List<DochaAdminCarPropertyResponse> selectCarTypePropertyInfo(DochaAdminCarPropertyRequest reqParam);

	//차량속성 조회 (옵션)
	public List<DochaAdminCarPropertyResponse> selectCarOptionPropertyInfo(DochaAdminCarPropertyRequest reqParam);

	//차량속성 조회 (연료)
	public List<DochaAdminCarPropertyResponse> selectCarFuelPropertyInfo(DochaAdminCarPropertyRequest reqParam);

	//차량속성 조회 (색상)
	public List<DochaAdminCarPropertyResponse> selectCarColorPropertyInfo(DochaAdminCarPropertyRequest reqParam);


    //차량속성 삭제
    public int deleteProperty(String codeIdx);

}
