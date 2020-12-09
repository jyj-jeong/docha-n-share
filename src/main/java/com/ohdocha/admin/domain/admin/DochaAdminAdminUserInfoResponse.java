package com.ohdocha.admin.domain.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Data
@Getter
@Setter
@Alias("adminUserInfoResponse")
public class DochaAdminAdminUserInfoResponse {

    private String adIdx;
    private String adminId;
    private String adminPassword;
    private String adminName;
    private String userContact1;
    private String userContact2;
    private String userRole;
    private String rtIdx;
    private String regDt;
    private String regId;
    private String modDt;
    private String modId;
    private String delYn;

    private String role;

}
