package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Alias("eventRequest")
public class DochaAdminLowcreditRequest {

    private int lcIdx;
    private String lcImgIdx ;
    private String lcListImgIdx ;
    private String lcBannerImgIdx ;
    private LocalDateTime lcStartDt;
    private LocalDateTime lcEndDt;
    private String lcTitle;
    private String lcContent;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

}
