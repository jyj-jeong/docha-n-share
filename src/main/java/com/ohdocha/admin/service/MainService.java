package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface MainService {

    void summaryRentCompanyInfo(ServiceMessage message);

    void selectCommonCodeInfo(ServiceMessage message);

    void selectAddressDivisionInfo(ServiceMessage message);

    void selectAddressDetailDivisionInfo(ServiceMessage message);
}
