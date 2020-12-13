package com.ohdocha.admin.domain.rentCompany;


import com.ohdocha.admin.domain.common.CommonResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("rentCompanyDetailResponse")
public class DochaAdminRentCompanyDetailResponse extends CommonResponseDto {
	
	//DC_RETNCOMPANYINFO
	private String rtIdx                     ; //제휴사idx
	private String rtPIdx                    ; //부모제휴사idx
	private String companyName               ; //제휴사명
	private String companyZipcode            ; //우편번호
	private String companyAddress            ; //주소
	private String companyAddressDetail      ; //상세주소
	private String lat                       ; //위도
	private String lng                       ; //경도
	private String establishedDate           ; //설립일
	private String companyRegistrationNumber ; //사업자등록번호
	private String companyRegistrationImg    ; //사업자등록증이미지idx
	private String accountBank               ; //정산계좌은행
	private String accountNumber             ; //정산계좌번호
	private String accountHolder             ; //정산계좌예금주
	private String taxEmail		             ; //세금계산서 이메일
	private String accountImgIdx             ; //정산통장이미지idx
	private String longtermRentYn            ; //장기렌트이용여부
	private String shorttermRentYn           ; //단기렌트이용여부
	private String alliance_status           ; //제휴상태
	private String branchAbleYn              ; //지점보유가능여부
	private String  carCount                  ; //보유차량대수
	private String etc                       ; //비고
	private String regId                     ; //등록자
	private String regDt                     ; //등록일시
	private String modId                     ; //수정자
	private String modDt                     ; //수정일시
	private String delYn                     ; //삭제여부
	private String companyContact1           ; //회사대표번호
	private String companyContact2           ; //회사번호
	private String alarmYn                   ; //알림톡수신여부
	private String companyRegistrationName   ; //법인명
	private String branchName                ; //지점명
	private String accessYn                  ; //접속허용여부
	
	//DC_RENTCOMPANY_STAFF 담당자정보
//	private String rsIdx                     ; //제휴사직원idsx
//	private String staffName                 ; //직원명
//	private String staffContact1             ; //연락처1
//	private String staffContact2             ; //연락처2
//	private String staffEmail                ; //이메일
//	private String staffTitle                ; //직위
//	private String ownerYn                   ; //대표여부
//	private String staffTypeCode             ; //직원분류code
	
	//DC_RENT_COMPANY_COMMISSION 수수료율
	private String commissionPer			 ; //수수료율
	private String taxInvoiceCode			 ; //세금계산서발행주체
	
	//DC_RENT_COMPANY_TIME	예약정보
	private String weekdayOpenStart				             ; //평일영업시작시간
	private String weekdayOpenEnd							 ; //평일영업종료시간
	private String weekendOpenStart                          ; //주말영업시작시간
	private String weekendOpenEnd                            ; //주말영업종료시간
	private String weekdayDeliveryStart                      ; //평일배달시작시간
	private String weekdayDeliveryEnd                        ; //평일배달종료시간
	private String weekendDeliveryStart			    		 ; //주말배달시작시간
	private String weekendDeliveryEnd						 ; //주말배달종료시간
	private String returnInspectionTime                      ; //반납정비시간
	private String visitAbleTime                             ; //방문예약가능시간
	private String deliveryAbleTime                          ; //배달예약가능시간
	private String weekendReserveMinimumTime                 ; //주말공휴일최소예약시간(분)
	private String weekdayAbleDeliveryTime                   ; //평일왕복배달가능시간
	private String weekendAbleDeliveryTime                   ; //주말왕복배달가능시간
	private String weekendReserveMinimumRate                 ; //주말/공휴일 할증율
	
	//DC_RENT_COMPANY_RESERVE_MIN 특정기간 최소 예약시간 설정
//	private String minIdx                                     ; // 특정기간idx
//	private String minimumStartDt                             ; // 최소예약시간시작일
//	private String minimumEndDt                               ; // 최소에약시간종료일
//	private String minimumTime                                  ; // 최소시간(분)
	
	
	private String raGbnLt                 					 ; // 배달가능지역 장기 카운트
	private String raGbnSt                 					 ; // 배달가능지역 단기 카운트
	private String regCarCount;


}                                                