package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.implementation.TaskListApi;
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
    TaskListApi taskListApi;

    public TaskListApiController(TaskListApi taskListApi) {
        this.taskListApi = taskListApi;
    }

    @GetMapping("/tasklistApi/getTaskDetails/{taskId}")
    public String getTask(@PathVariable String taskId) throws IOException {
        return taskListApi.getTask(taskId);
    }

    @GetMapping("/tasklistApi/getFormDetails/{taskId}")
    public String getForm(@PathVariable String taskId) throws IOException {
        return taskListApi.getForm(taskId);
    }

    @PostMapping("/tasklistApi/getAllTask")
    public ResponseEntity<String> getAllTask(@RequestBody String requestBody) throws  IOException{
        return taskListApi.searchTask(requestBody);
    }

    @PostMapping("/tasklistApi/SearchTask")
    public ResponseEntity<String> searchTask(@RequestBody String requestBody) throws  IOException{
        return taskListApi.searchTask(requestBody);
    }

    @PostMapping("/tasklistApi/VariableSearch/{taskId}")
    public ResponseEntity<String> VariableSearch(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApi.variableSearch(taskId, requestBody);
    }

    @PostMapping("/tasklistApi/DraftVariable/{taskId}")
    public ResponseEntity<String> DraftVariable(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApi.draftVariable(taskId, requestBody);
    }

}
