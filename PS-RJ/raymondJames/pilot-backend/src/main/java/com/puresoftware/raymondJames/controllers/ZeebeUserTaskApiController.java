package com.puresoftware.raymondJames.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.puresoftware.raymondJames.service.ZeebeUserTaskApiService;

import okhttp3.Response;

/**
 * Description: This Controller for all Zeebe User Task Apis
 * Assign, Unassign, Update, Complete
 */

@RestController
public class ZeebeUserTaskApiController {

	@Autowired
	ZeebeUserTaskApiService zeebeUserTaskApiService;
	
	@PostMapping("/zeebeApi/assign/{taskId}")
	public ResponseEntity<String> assignZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApiService.assignZeebeTask(taskId, variableJson);
	}
	
	@DeleteMapping("/zeebeApi/unAssign/{taskId}")
	public ResponseEntity<String> unAssignZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApiService.unAssignZeebeTask(taskId, variableJson);
	}
	
	@PatchMapping("/zeebeApi/update/{taskId}")
	public ResponseEntity<String> updateZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApiService.updateZeebeTask(taskId, variableJson);
	}
	
	@PostMapping("/zeebeApi/complete/{taskId}")
	public ResponseEntity<String> completeZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		System.out.println("Test");
		 return zeebeUserTaskApiService.completeZeebeTask(taskId, variableJson);
	}
	
}
