package com.ohdocha.admin.mapper;

import com.ohdocha.admin.domain.reserve.payment.DochaScheduledDto;
import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DochaAdminScheduledMapper {

    public List<DochaScheduledDto> selectScheduledListForCancel(DochaMap param);

    public int updateCancelScheduleStatus(DochaMap param);

}
