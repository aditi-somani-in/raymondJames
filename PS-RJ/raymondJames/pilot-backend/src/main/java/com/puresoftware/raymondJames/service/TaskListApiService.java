package com.puresoftware.raymondJames.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TaskListApiService {
    String getTask(String taskId);
    ResponseEntity<String> draftVariable(String taskId, String requestBody) throws IOException;
    ResponseEntity<String> variableSearch(String taskId, String requestBody) throws IOException;
    ResponseEntity<String> searchTask(String requestBody) throws IOException;
    String getForm(String taskId) throws IOException;
}
