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
	Integer calcDailySales();

	int calcMonthlySales();

	int cntDailyReserve();

	int cntMontlyyReserve();

    // todo 누적 월차

	int cntDailyCancel();

	int cntMonthlyCancel();

	int cntQnA();

	/* 메인화면 count */
	List<Map<String,Object>> salesGraph(DochaMap dochaMap);
}
