package com.ohdocha.admin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ohdocha.admin.domain.*;
import com.ohdocha.admin.util.DochaMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface DochaPaymentService {

	public void paymentOne(DochaMap paramMap, String url, String impKey, String impSecret) throws JsonMappingException, JsonProcessingException, Exception;

}
