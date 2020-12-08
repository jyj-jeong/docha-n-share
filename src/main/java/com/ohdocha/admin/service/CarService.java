package com.ohdocha.admin.service;

import com.ohdocha.admin.util.ServiceMessage;

public interface CarService {

    void regCarList(ServiceMessage message);

    void getCarModelList(ServiceMessage message);

    void regCarAdd(ServiceMessage message);

    void insertRegCarOption(ServiceMessage message);

    void companyList(ServiceMessage message);

    void selectCarModelForSelectBox(ServiceMessage message);

    void selectCarModelDetailForSelectBox(ServiceMessage message);

    void insuranceTemplateinfoDetail(ServiceMessage message);

    void selectRegCarDetailOption(ServiceMessage message);

    void updateDcCarInfo(ServiceMessage message);

    void insertCarModelInfo(ServiceMessage message);

    void selectCarModelDetail(ServiceMessage message);

    void updateCarModelInfo(ServiceMessage message);

    void regCarDetail(ServiceMessage serviceMessage);

    void carCountryProperty(ServiceMessage message);

    void deleteProperty(ServiceMessage message);

    void insertCarPropertyCountry(ServiceMessage message);

    void carManufacturerProperty(ServiceMessage message);

    void insertCarPropertyManufacturer(ServiceMessage message);

    void insertCarPropertyCarType(ServiceMessage message);

    void carCarTypeProperty(ServiceMessage message);

    void insertCarPropertyOption(ServiceMessage message);

    void carOptionProperty(ServiceMessage message);

    void carFuelProperty(ServiceMessage message);

    void insertCarPropertyFuel(ServiceMessage message);

    void carColorProperty(ServiceMessage message);

    void insertCarPropertyColor(ServiceMessage message);

    void insertPlanSettingDetail(ServiceMessage message);

    void getPeriodPlanList(ServiceMessage message);

    void selectPeriodPlanDetail(ServiceMessage message);

    void updatePeriodPlan(ServiceMessage message);

    void insertBasicPlanInfo(ServiceMessage message);

    void getBasicPlanList(ServiceMessage message);

    void selectbasicPlanDetail(ServiceMessage message);

    void updateBasicPlanInfo(ServiceMessage message);

    void insertInsuranceTemplate(ServiceMessage message);

    void getInsuranceTemplateList(ServiceMessage message);

    void InsuranceTemplateDetail(ServiceMessage message);

    void updateInsuranceTemplate(ServiceMessage message);

    void selectReserveAmt(ServiceMessage message);

    void insertRegCarPayment(ServiceMessage message);

    void uploadCarImage(ServiceMessage message);

    void updateDcInsuranceInfo(ServiceMessage message);

    void updateDcPaymentInfo(ServiceMessage message);

    void insertRentAble(ServiceMessage message);

    void selectRentCompanyCarList(ServiceMessage message);
}
