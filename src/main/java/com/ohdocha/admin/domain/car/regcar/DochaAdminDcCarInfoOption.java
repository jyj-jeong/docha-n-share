package com.ohdocha.admin.domain.car.regcar;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("dcCarInfoOption")
public class DochaAdminDcCarInfoOption {

	private String rtIdx;
	private String crIdx;
	private String optionDetailCode;
	private String commissionPer;
	private String taxInvoiceCode;
	private String regId;
	private String regDt;
	private String modId;
	private String modDt;
	private String delYn;
}
