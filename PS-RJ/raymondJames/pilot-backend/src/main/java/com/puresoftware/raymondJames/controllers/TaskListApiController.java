package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.service.TaskListApiService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Description: This task will fetch the details of the process assigned.
 * Camunda APIs: get tasklist
 */

@RestController
public class TaskListApiController {
    @Autowired
    TaskListApiService taskListApiService;

    public TaskListApiController(TaskListApiService taskListApiService) {
        this.taskListApiService = taskListApiService;
    }

    @GetMapping("/tasklistApi/getTaskDetails/{taskId}")
    public String getTask(@PathVariable String taskId) throws IOException {
        return taskListApiService.getTask(taskId);
    }

    @GetMapping("/tasklistApi/getFormDetails/{taskId}")
    public String getForm(@PathVariable String taskId) throws IOException {
        return taskListApiService.getForm(taskId);
    }

    @PostMapping("/tasklistApi/getAllTask")
    public ResponseEntity<String> getAllTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiService.searchTask(requestBody);
    }

    @PostMapping("/tasklistApi/SearchTask")
    public ResponseEntity<String> searchTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiService.searchTask(requestBody);
    }

    @PostMapping("/tasklistApi/VariableSearch/{taskId}")
    public ResponseEntity<String> VariableSearch(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiService.variableSearch(taskId, requestBody);
    }

    @PostMapping("/tasklistApi/DraftVariable/{taskId}")
    public ResponseEntity<String> DraftVariable(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiService.draftVariable(taskId, requestBody);
    }

}
