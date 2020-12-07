package com.ohdocha.admin.domain.car.model;

import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("carModelResponse")
public class DochaAdminCarModelResponse extends CommonResponseDto {
	
	private String mdIdx            ; //모델idx
	private String modelName        ; //모델명
	private String modelDetailName  ; //모델상세명
	private String manufacturerCode ; //제조사code
	private String manufacturerName ; //제조사명
	private String countryCode      ; //국가code
	private String countryName      ; //국가명
	private String importCode       ; //국내해외구분
	private String importName       ; //국내해외명
	private String carTypeCode      ; //차종코드
	private String carTypeName      ; //차종명
	private String fuelCode         ; //연료code
	private String fuelName         ; //연료명
	private String transmissionCode ; //변속기구분code
	private String transmissionName ; //변속기명
	private String driveTypeCode    ; //구동방식구분code
	private String driveTypeName    ; //구동방식구분명
	private String driveLicenseCode ; //면허구분code
	private String driveLicenseName ; //면허구분이름
	private String maximumPassenger ; //승차인원
	private String displacement     ; //배기량
	private String year             ; //연식
	private String colorName        ; //색상
	private String pyIdx            ; //요금IDX
	private String regId            ; //등록자
	private String regDt            ; //등록일시
	private String modId            ; //수정자
	private String modDt            ; //수정일시
	private String delYn            ; //삭제여부
	private String imgIdx           ; //이미지IDX
	private String carCount           ; //차량대수


}
