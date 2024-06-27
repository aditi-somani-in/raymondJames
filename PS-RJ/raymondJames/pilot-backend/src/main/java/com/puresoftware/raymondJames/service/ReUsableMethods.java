package com.puresoftware.raymondJames.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.BEARER;
import static com.puresoftware.raymondJames.utils.GlobalUtils.*;

@Service
public class ReUsableMethods {

    @Autowired
    BearerTokenGeneratorService bearerTokenGeneratorService;

    @Value("${application.json}")
    private String applicationJson;

    public HttpHeaders addHeadersValue() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
        return headers;
    }

}
