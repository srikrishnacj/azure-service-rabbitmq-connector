package com.example.azureservicebusexample.controllers;

import com.example.azureservicebusexample.apiresponse.restresponse.OkRestResponse;
import com.example.azureservicebusexample.connector.api.BridgeStatus;
import com.example.azureservicebusexample.service.BridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bridge")
public class BridgeStatusCtrl {
    private final BridgeService bridgeService;

    public BridgeStatusCtrl(BridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }

    @GetMapping(path = "/status")
    public ResponseEntity status() {
        List<BridgeStatus> status = this.bridgeService.status();
        return OkRestResponse.ok(status).responseEntity();
    }
}
