package com.ohdocha.admin.domain.menu;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Alias("mainImgRequest")
public class DochaAdminMainImgRequest {

    private int miIdx;
    private String miImgIdx;
    private LocalDateTime miStartDt;
    private LocalDateTime miEndDt;
    private String miTitle;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;

}
