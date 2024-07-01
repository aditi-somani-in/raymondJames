package com.puresoftware.raymondJames.implementation;

import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import com.puresoftware.raymondJames.service.TasklistApiService;
import com.puresoftware.raymondJames.service.ZeebeApiService;
import com.puresoftware.raymondJames.config.HeaderConfig;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.HashMap;

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

	@Autowired
	TasklistApiImpl tasklistApiImpl;

	// Zeebe Api for Assign User Task
	@Override
	public ResponseEntity<String> assignZeebeTask(String taskId, String variableJson)  {
		logger.debug("Service for Assign Zeebe User Task..!!");
		String assignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignment";
		ResponseEntity<String> taskDetails = tasklistApiImpl.getTask(taskId);
		JSONObject jsonObject = new JSONObject(taskDetails.getBody());
		String assignee = jsonObject.get("assignee").toString();
		String taskState = jsonObject.getString("taskState");
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			if(assignee.equals("null")) {
				response = restTemplate.exchange(assignZeebeTaskUrl, HttpMethod.POST, entity, String.class);
			}
			else{
				return new ResponseEntity<>("Please check task is already Assign to "+ assignee + " user", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Task Assigned successfully", HttpStatus.OK);
	}

	// Zeebe Api for UnAssign User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> unAssignZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for UnAssign Zeebe User Task..!!");
		String unAssignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignee";
		ResponseEntity<String> taskDetails = tasklistApiImpl.getTask(taskId);
		JSONObject jsonObject = new JSONObject(taskDetails.getBody());
		String assignee = jsonObject.get("assignee").toString();
		String taskState = jsonObject.getString("taskState");
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			if(!assignee.equals("null")) {
				response = restTemplate.exchange(unAssignZeebeTaskUrl, HttpMethod.DELETE, entity, String.class);
			}else{
				return new ResponseEntity<>("Please check task is already assigned or completed",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Task UnAssigned successfully", HttpStatus.OK);
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
			new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Task details updated successfully", HttpStatus.OK);
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
			return new ResponseEntity<>("Please check task is already completed or mentioned taskId is not correct", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Task Completed successfully", HttpStatus.OK);
	}
}
