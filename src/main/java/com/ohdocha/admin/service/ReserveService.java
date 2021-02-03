package com.ohdocha.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ohdocha.admin.util.DochaMap;
import com.ohdocha.admin.util.ServiceMessage;

public interface ReserveService {

    void getReserveInfoList(ServiceMessage serviceMessage);

    void addReserveInfo(ServiceMessage serviceMessage);

    void updateReserveInfo(ServiceMessage serviceMessage);

    void getReserveInfo(ServiceMessage serviceMessage);

    void selectCompanyInfo(ServiceMessage serviceMessage);

    void selectCompanyInfoAndCarInfo(ServiceMessage serviceMessage);


}
