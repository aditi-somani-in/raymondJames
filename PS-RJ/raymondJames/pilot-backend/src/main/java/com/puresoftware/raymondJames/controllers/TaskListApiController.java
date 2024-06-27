package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.service.TaskListApiService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Description: This task will fetch the details of the process assigned.
 *              Get a Task API used
 */

@RestController
@CrossOrigin(origins = "*") //Need to remove this before deploying this service
@RequestMapping("/tasklistApi")
public class TaskListApiController {
    @Autowired
    TaskListApiService taskListApiService;

    public TaskListApiController(TaskListApiService taskListApiService) {
        this.taskListApiService = taskListApiService;
    }

    /* Api for Get task details for provided taskid */
    @GetMapping("/getTaskDetails/{taskId}")
    public ResponseEntity<String> getTask(@PathVariable String taskId) throws IOException {
        return taskListApiService.getTask(taskId);
    }

    /* Api for Get form details for provided taskid */
    @GetMapping("/getFormDetails/{taskId}")
    public ResponseEntity<String> getForm(@PathVariable String taskId) throws IOException {
        return taskListApiService.getForm(taskId);
    }

    /* Api for Get all tasks from the tasklist */
    @PostMapping("/getAllTask")
    public ResponseEntity<String> getAllTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiService.searchTask(requestBody);
    }

    /* Api for Get task details as per request body from the task list for ex: state:"CREATED" */
    @PostMapping("/SearchTask")
    public ResponseEntity<String> searchTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiService.searchTask(requestBody);
    }

    /* Api for Search variable details from mentioned taskId and Variable */
    @PostMapping("/VariableSearch/{taskId}")
    public ResponseEntity<String> VariableSearch(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiService.variableSearch(taskId, requestBody);
    }

    /* Api for add draft variable in integer format form mentioned taskid */
    @PostMapping("/DraftVariable/{taskId}")
    public ResponseEntity<String> DraftVariable(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiService.draftVariable(taskId, requestBody);
    }
}


