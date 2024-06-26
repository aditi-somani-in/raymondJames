package com.puresoftware.raymondJames.service;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.Collections;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.GlobalUtils.CONTENT_TYPE;

@Service
@Slf4j
public class BearerTokenGeneratorService {
    private static Logger logger = LoggerFactory.getLogger(BearerTokenGeneratorService.class);

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${grant.type}")
    private String grantType;

    @Value("${bearer.token.url}")
    private String bearerTokenUrl;

    @Value("${x.www.form.urlencoded}")
    private String contentTypeEncoded;

    public String generateBearerToken() throws IOException {

        logger.debug("Generating Bearer Token....!!");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> tokenRequestBody = new LinkedMultiValueMap<String, String>();

        HttpEntity request = new HttpEntity(tokenRequestBody, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(GRANT_TYPE,grantType);
        headers.set(CLIENT_ID, clientId);
        headers.set(CLIENT_SECRET_KEY,clientSecret);
        headers.set(CONTENT_TYPE,contentTypeEncoded);
        tokenRequestBody.add(GRANT_TYPE, grantType);
        tokenRequestBody.add(CLIENT_ID, clientId);
        tokenRequestBody.add(CLIENT_SECRET_KEY,clientSecret);

        ResponseEntity<String> response = restTemplate.exchange(bearerTokenUrl, HttpMethod.POST, request, String.class);
        JSONObject bearerTokenAccessKey = new JSONObject(response.getBody());

        try {
            return bearerTokenAccessKey.getString("access_token");
        } catch (Exception e) {
            throw new IOException("Bearer Token Generator Service failed to generate access_token..!!");
        }
    }
}
