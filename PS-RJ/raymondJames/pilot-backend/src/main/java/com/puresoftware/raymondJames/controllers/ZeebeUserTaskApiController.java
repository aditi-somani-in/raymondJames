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
import com.puresoftware.raymondJames.implementation.ZeebeUserTaskApi;

/**
 * Description: This Controller for all Zeebe User Task Apis
 * Camunda APIs: Assign, Unassign, Update, Complete
 */

@RestController
public class ZeebeUserTaskApiController {

	@Autowired
	ZeebeUserTaskApi zeebeUserTaskApi;
	
	@PostMapping("/zeebeApi/assign/{taskId}")
	public ResponseEntity<String> assignZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApi.assignZeebeTask(taskId, variableJson);
	}
	
	@DeleteMapping("/zeebeApi/unAssign/{taskId}")
	public ResponseEntity<String> unAssignZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApi.unAssignZeebeTask(taskId, variableJson);
	}
	
	@PatchMapping("/zeebeApi/update/{taskId}")
	public ResponseEntity<String> updateZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeUserTaskApi.updateZeebeTask(taskId, variableJson);
	}
	
	@PostMapping("/zeebeApi/complete/{taskId}")
	public ResponseEntity<String> completeZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		System.out.println("Test");
		 return zeebeUserTaskApi.completeZeebeTask(taskId, variableJson);
	}
	
}
