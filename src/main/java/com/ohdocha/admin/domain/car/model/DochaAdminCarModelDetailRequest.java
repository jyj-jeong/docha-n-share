package com.ohdocha.admin.domain.car.model;

import com.ohdocha.admin.domain.common.CommonRequestDto;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("carModelDetailRequest")
public class DochaAdminCarModelDetailRequest extends CommonRequestDto {
	
	private int mdIdx            ; //모델idx
	private String modelName        ; //모델명
	private String modelDetailName  ; //모델상세명
	private String[] modelDetailNameList  ; //모델상세명리스트
	private String manufacturerCode ; //제조사code
	private String manufacturerName ; //제조사명
	private String countryCode      ; //국가code
	private String countryName      ; //국가명
	private String importCode       ; //국내해외구분
	private String importName       ; //국내해외명
	private String cartypeCode		;	//차종
	private String cartypeName		;	//차종명
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
	public int getMdIdx() {
		return mdIdx;
	}
	public void setMdIdx(int mdIdx) {
		this.mdIdx = mdIdx;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelDetailName() {
		return modelDetailName;
	}
	public void setModelDetailName(String modelDetailName) {
		this.modelDetailName = modelDetailName;
	}
	public String getManufacturerCode() {
		return manufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setConturyCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getConturyName() {
		return countryName;
	}
	public void setConturyName(String countryName) {
		this.countryName = countryName;
	}
	public String getImportCode() {
		return importCode;
	}
	public void setImportCode(String importCode) {
		this.importCode = importCode;
	}
	public String getImportName() {
		return importName;
	}
	public void setImportName(String importName) {
		this.importName = importName;
	}
	public String getCartypeCode() {
		return cartypeCode;
	}
	public void setCartypeCode(String cartypeCode) {
		this.cartypeCode = cartypeCode;
	}
	public String getCartypeName() {
		return cartypeName;
	}
	public void setCartypeName(String cartypeName) {
		this.cartypeName = cartypeName;
	}
	public String getFuelCode() {
		return fuelCode;
	}
	public void setFuelCode(String fuelCode) {
		this.fuelCode = fuelCode;
	}
	public String getFuelName() {
		return fuelName;
	}
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}
	public String getTransmissionCode() {
		return transmissionCode;
	}
	public void setTransmissionCode(String transmissionCode) {
		this.transmissionCode = transmissionCode;
	}
	public String getTransmissionName() {
		return transmissionName;
	}
	public void setTransmissionName(String transmissionName) {
		this.transmissionName = transmissionName;
	}
	public String getDriveTypeCode() {
		return driveTypeCode;
	}
	public void setDriveTypeCode(String driveTypeCode) {
		this.driveTypeCode = driveTypeCode;
	}
	public String getDriveTypeName() {
		return driveTypeName;
	}
	public void setDriveTypeName(String driveTypeName) {
		this.driveTypeName = driveTypeName;
	}
	public String getDriveLicenseCode() {
		return driveLicenseCode;
	}
	public void setDriveLicenseCode(String driveLicenseCode) {
		this.driveLicenseCode = driveLicenseCode;
	}
	public String getDriveLicenseName() {
		return driveLicenseName;
	}
	public void setDriveLicenseName(String driveLicenseName) {
		this.driveLicenseName = driveLicenseName;
	}
	public String getMaximumPassenger() {
		return maximumPassenger;
	}
	public void setMaximumPassenger(String maximumPassenger) {
		this.maximumPassenger = maximumPassenger;
	}
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getPyIdx() {
		return pyIdx;
	}
	public void setPyIdx(String pyIdx) {
		this.pyIdx = pyIdx;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getImgIdx() {
		return imgIdx;
	}
	public void setImgIdx(String imgIdx) {
		this.imgIdx = imgIdx;
	}
}
