package com.puresoftware.raymondJames.service;

import org.springframework.http.ResponseEntity;


public interface TaskListApiService {

    String getTask(String taskId);

    ResponseEntity<String> draftVariable(String taskId, String requestBody);

    ResponseEntity<String> variableSearch(String taskId, String requestBody);

    ResponseEntity<String> searchTask(String requestBody);

    String getForm(String taskId);
}
