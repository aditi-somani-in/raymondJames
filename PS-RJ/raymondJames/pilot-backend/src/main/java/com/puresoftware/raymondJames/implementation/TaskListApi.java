package com.puresoftware.raymondJames.implementation;

import com.puresoftware.raymondJames.service.BearerTokenGeneratorService;
import com.puresoftware.raymondJames.service.TaskListApiService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.puresoftware.raymondJames.utils.BearerTokenUtils.*;
import static com.puresoftware.raymondJames.utils.GlobalUtils.*;

@Service
@Slf4j
public class TaskListApi implements TaskListApiService {
    private static Logger logger = LoggerFactory.getLogger(TaskListApi.class);

    @Autowired
    BearerTokenGeneratorService bearerTokenGeneratorService;

    @Value("${camunda.api.url}")
    private String camundaApiUrl;

    @Value("${localhost.formapi.url}")
    private String formapiUrl;

    @Value("${tasklist.TaskSearchUrl}")
    private  String taskSearchUrl;

    @Value("${task.version}")
    private String taskVersion;

    @Value("${application.json}")
    private String applicationJson;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Autowired
    private RestTemplate restTemplate;

    //For get form details using taskId
    @Override
    public String getForm(String taskId) throws IOException {
        logger.debug("Service for GET A FORM FROM TASKLIST invoked..!!");
        String taskDetails = getTask(taskId);
        JSONObject jsonObject = new JSONObject(taskDetails);
        String processDefinitionKey = jsonObject.getString("processDefinitionKey");
        String formId = jsonObject.getString("formId");
        String url = formapiUrl+formId+"?processDefinitionKey="+processDefinitionKey;
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

    //For get task Search from tasklist
    @Override
    public ResponseEntity<String> searchTask(String requestBody) throws IOException{
        logger.debug("Service for Search A TASK FROM TASKLIST invoked..!!");
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(taskSearchUrl, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    //For get variable Search from tasklist
    @Override
    public ResponseEntity<String> variableSearch(String taskId, String requestBody) throws IOException{
        logger.debug("Service for Search A Variable FROM TASKLIST invoked..!!");
        String url = camundaApiUrl + taskVersion+ taskId+"/variables/search";
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public String getTask(String taskId) {
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
                logger.debug("Get of tasklist Service failed..!!");
            }
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<String> draftVariable(String taskId, String requestBody) throws IOException {
        logger.debug("Service for Draft Variables FROM TASKLIST invoked..!!");
        String url = camundaApiUrl + taskVersion+ taskId+"/variables";
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, applicationJson);
        headers.set(CONTENT_TYPE, applicationJson);
        headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }
}
