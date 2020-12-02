package com.ohdocha.admin.mapper;

import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Mapper
public interface DochaAdminDashboardMapper {
	List<DochaMap> selectDochaDashboardList(DochaMap param);

	/* 메인화면 count */
	Integer calcDailySales(DochaMap dochaMap);

	Integer calcMonthlySales(DochaMap dochaMap);

	Integer cntDailyReserve(DochaMap dochaMap);

	Integer cntMontlyyReserve(DochaMap dochaMap);

    // todo 누적 월차

	Integer cntDailyCancel(DochaMap dochaMap);

	Integer cntMonthlyCancel(DochaMap dochaMap);

	Integer cntQnA(DochaMap dochaMap);

	/* 메인화면 count */
	Map<String,Object> salesGraph(DochaMap dochaMap);

	Map<String,Object> monthlySalesGraph(DochaMap dochaMap);

	Map<String,Object> newUserGraph(DochaMap dochaMap);

	Map<String,Object> cancelGraph(DochaMap dochaMap);
}
