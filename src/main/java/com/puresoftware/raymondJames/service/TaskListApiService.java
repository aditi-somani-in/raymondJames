package com.puresoftware.raymondJames.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.GlobalUtils.ACCEPT;
import static com.puresoftware.raymondJames.utils.GlobalUtils.AUTHORIZATION;

@Service
@Slf4j
public class TaskListApiService {
    private static Logger logger = LoggerFactory.getLogger(TaskListApiService.class);

    @Autowired
    BearerTokenGeneratorService bearerTokenGeneratorService;

    @Value("${camunda.api.url}")
    private String camundaApiUrl;

    @Value("${task.version}")
    private String taskVersion;

    @Value("${application.json}")
    private String applicationJson;

    private final OkHttpClient okHttpClient = new OkHttpClient();
 
    /*TODO: Need to check if rest template are required*/

    //For get task details using taskId
    public String getTask(String taskId) throws IOException {
        System.out.println("Test");
        logger.debug("Service for GET A TASK FROM TASKLIST invoked..!!");
        String url = camundaApiUrl + taskVersion + taskId;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT,applicationJson)
                .addHeader(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken())
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if(!response.isSuccessful()){
                throw new IOException("Get of tasklist Service failed..!!");
            }
            return response.body().string();
        }
    }


}
