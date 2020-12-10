package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoRequest;
import com.ohdocha.admin.domain.reserve.payment.DochaAdminPaymentInfoResponse;
import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DochaAdminPaymentInfoMapper {
    public List<DochaAdminPaymentInfoResponse> selectPaymentInfoList(DochaAdminPaymentInfoRequest reqParam);

    public DochaAdminPaymentInfoResponse selectPeriodPaymentInfo(DochaAdminPaymentInfoRequest reqParam);

    public int insertPaymentDetail(DochaAdminPaymentInfoRequest reqParam);

    // 예약 정보
    public List<DochaAdminPaymentInfoRequest> selectReserveInfo(DochaMap param);

    // 즉시 취소
    public int updateCancelReserve(DochaMap paramMap);

}
