package com.ohdocha.admin.mapper;

import com.ohdocha.admin.util.DochaMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DochaAdminDashboardMapper {
	List<DochaMap> selectDochaDashboardList(DochaMap param);
}
