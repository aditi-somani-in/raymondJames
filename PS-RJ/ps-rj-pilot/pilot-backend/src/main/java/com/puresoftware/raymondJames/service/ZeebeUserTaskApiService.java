package com.puresoftware.raymondJames.service;

import org.apache.hc.client5.http.entity.mime.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import static com.puresoftware.raymondJames.utils.BearerTokenUtils.BEARER;
import static com.puresoftware.raymondJames.utils.GlobalUtils.ACCEPT;
import static com.puresoftware.raymondJames.utils.GlobalUtils.AUTHORIZATION;
import static com.puresoftware.raymondJames.utils.GlobalUtils.CONTENT_TYPE;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@Slf4j
public class ZeebeUserTaskApiService {

	private static Logger logger = LoggerFactory.getLogger(ZeebeUserTaskApiService.class);

	@Value("${zeebe.api.url}")
	private String zeebeApiUrl;

	@Value("${application.json}")
	private String applicationJson;

	@Value("${zeebe.version}")
	private String zeebeVersion;

	@Autowired
	BearerTokenGeneratorService bearerTokenGeneratorService;

	@Autowired
	public RestTemplate restTemplate;

	// Zeebe Api for Assign User Task
	public ResponseEntity<String> assignZeebeTask(String taskId, String variableJson) throws IOException {
		logger.debug("Service for Assign Zeebe User Task..!!");
		String assignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignment";
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, applicationJson);
		headers.set(ACCEPT, applicationJson);
		headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(assignZeebeTaskUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	// Zeebe Api for UnAssign User Task
	public ResponseEntity<String> unAssignZeebeTask(String taskId, String variableJson) throws IOException {
		logger.debug("Service for UnAssign Zeebe User Task..!!");
		String unAssignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignee";
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, applicationJson);
		headers.set(ACCEPT, applicationJson);
		headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(unAssignZeebeTaskUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	// Zeebe Api for Update User Task
	public ResponseEntity<String> updateZeebeTask(String taskId, String variableJson) throws IOException {
		logger.debug("Service for Update Zeebe User Task..!!");
		String updateZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId;
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, applicationJson);
		headers.set(ACCEPT, applicationJson);
		headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(updateZeebeTaskUrl, HttpMethod.PATCH, entity, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	// Zeebe Api for Complete User Task
	public ResponseEntity<String> completeZeebeTask(String taskId, String variableJson) throws IOException {
		logger.debug("Service for Complete Zeebe User Task..!!");
		String completeZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/completion";
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, applicationJson);
		headers.set(ACCEPT, applicationJson);
		headers.set(AUTHORIZATION, BEARER + bearerTokenGeneratorService.generateBearerToken());
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(completeZeebeTaskUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}
}
