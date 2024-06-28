package com.puresoftware.raymondJames.headerConfig;

import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.BearerTokenUtils.CLIENT_SECRET_KEY;
import static com.puresoftware.raymondJames.utils.GlobalUtils.*;

@Service
public class HeaderConfig {

    @Autowired
    BearerTokenGeneratorConfig bearerTokenGeneratorConfig;

    @Value("${application.json}")
    private String applicationJson;

    @SneakyThrows
    public HttpHeaders addHeadersValue() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorConfig.generateBearerToken());
        return headers;
    }



}
