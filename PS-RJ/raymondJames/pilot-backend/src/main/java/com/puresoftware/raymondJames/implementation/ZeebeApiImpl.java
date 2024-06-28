package com.puresoftware.raymondJames.implementation;

import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import com.puresoftware.raymondJames.service.ZeebeApiService;
import com.puresoftware.raymondJames.headerConfig.HeaderConfig;
import lombok.SneakyThrows;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ZeebeApiImpl implements ZeebeApiService {

	private static Logger logger = LoggerFactory.getLogger(ZeebeApiImpl.class);

	@Value("${zeebe.api.url}")
	private String zeebeApiUrl;

	@Value("${application.json}")
	private String applicationJson;

	@Value("${zeebe.version}")
	private String zeebeVersion;

	@Autowired
	BearerTokenGeneratorConfig bearerTokenGeneratorConfig;

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private HeaderConfig headerConfig;

	// Zeebe Api for Assign User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> assignZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Assign Zeebe User Task..!!");
		String assignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignment";
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(assignZeebeTaskUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return response;
	}

	// Zeebe Api for UnAssign User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> unAssignZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for UnAssign Zeebe User Task..!!");
		String unAssignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignee";
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(unAssignZeebeTaskUrl, HttpMethod.DELETE, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return response;
	}

	// Zeebe Api for Update User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> updateZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Update Zeebe User Task..!!");
		String updateZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId;
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(updateZeebeTaskUrl, HttpMethod.PATCH, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return response;
	}

	// Zeebe Api for Complete User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> completeZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Complete Zeebe User Task..!!");
		String completeZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/completion";
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(completeZeebeTaskUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return response;
	}
}
