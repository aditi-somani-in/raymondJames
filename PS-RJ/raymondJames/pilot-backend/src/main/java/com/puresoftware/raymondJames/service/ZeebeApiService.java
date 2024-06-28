package com.puresoftware.raymondJames.service;

import org.springframework.http.ResponseEntity;

public interface ZeebeApiService {

    ResponseEntity<String> assignZeebeTask(String taskId, String variableJson);

    ResponseEntity<String> unAssignZeebeTask(String taskId, String variableJson);

    ResponseEntity<String> updateZeebeTask(String taskId, String variableJson);

    ResponseEntity<String> completeZeebeTask(String taskId, String variableJson);

}
