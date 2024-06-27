package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.service.TaskListApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
