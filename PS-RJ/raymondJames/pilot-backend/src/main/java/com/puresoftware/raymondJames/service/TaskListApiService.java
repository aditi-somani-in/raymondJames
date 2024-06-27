package com.puresoftware.raymondJames.service;

import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class TaskListApiService {
    private static Logger logger = LoggerFactory.getLogger(TaskListApiService.class);

    @Autowired
    BearerTokenGeneratorService bearerTokenGeneratorService;

    @Value("${camunda.api.url}")
    private String camundaApiUrl;

    @Value("${localhost.formapi.url}")
    private String formapiUrl;

    @Value("${tasklist.TaskSearchUrl}")
    private String taskSearchUrl;

    @Value("${task.version}")
    private String taskVersion;

    @Value("${application.json}")
    private String applicationJson;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilityService utilityService;

    /*TODO: Need to check if rest template are required*/

    //For get task details using taskId
    public ResponseEntity<String> getTask(String taskId) throws IOException {
        logger.debug("Service for GET A TASK FROM TASKLIST invoked..!!");
        String url =  camundaApiUrl + taskVersion + taskId;
        HttpHeaders headers = utilityService.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(null, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (Exception ex) {
          logger.error(ex.toString());
        }
        return response;
    }

    //For get form details using taskId
    public ResponseEntity<String> getForm(String taskId) throws IOException {
        logger.debug("Service for GET A FORM FROM TASKLIST invoked..!!");
        ResponseEntity<String> taskDetails = getTask(taskId);
        JSONObject jsonObject = new JSONObject(taskDetails.getBody());
        String processDefinitionKey = jsonObject.getString("processDefinitionKey");
        String formId = jsonObject.getString("formId");
        String url = formapiUrl+formId+"?processDefinitionKey="+processDefinitionKey;
        HttpHeaders headers = utilityService.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(null, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        return response;
    }

    //For get task Search from tasklist
    public ResponseEntity<String> searchTask(String requestBody) throws IOException{
        logger.debug("Service for Search A TASK FROM TASKLIST invoked..!!");
        HttpHeaders headers = utilityService.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(taskSearchUrl, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
        }
        return response;
    }

    //For get variable Search from tasklist
    public ResponseEntity<String> variableSearch(String taskId, String requestBody) throws IOException{
        logger.debug("Service for Search A Variable FROM TASKLIST invoked..!!");
        String url = camundaApiUrl + taskVersion+ taskId+"/variables/search";
        HttpHeaders headers = utilityService.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
        }
        return response;
    }

    //For get variable Search from tasklist
    public ResponseEntity<String> draftVariable(String taskId, String requestBody) throws IOException{
        logger.debug("Service for Draft Variables FROM TASKLIST invoked..!!");
        String url = camundaApiUrl + taskVersion+ taskId+"/variables";
        HttpHeaders headers = utilityService.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
        }
        return response;
    }
}
