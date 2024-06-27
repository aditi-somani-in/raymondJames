package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.pojos.BearerTokenGeneratorDetails;
import com.puresoftware.raymondJames.service.BearerTokenGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Description: This service will generate the bearer token.
 * Usage: Embed this service at the time of token generation for an API.
 */

@RestController
public class BearerTokenGenerateController {
    @Autowired
    BearerTokenGeneratorService bearerTokenGeneratorService;

    @PostMapping("/generateToken")
    public ResponseEntity<String> completeTask() throws IOException {
        String response = bearerTokenGeneratorService.generateBearerToken();
        return ResponseEntity.ok(response);
    }
}
