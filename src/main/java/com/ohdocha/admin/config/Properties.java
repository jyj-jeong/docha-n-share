package com.ohdocha.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "com.ohdocha.admin")
public class Properties {

    private String serverHost;
    private String serverName;
    private String serverVersion;

    private long uploadImageSize;
    private List<String> supportImageExtension;
    private String tempFolderPath;

    /* iamport */
    private String impKey;
    private String impSecret;
    private String tokenUrl;

}
