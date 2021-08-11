package com.example.azureservicebusexample.web;

import com.example.azureservicebusexample.apiresponse.restresponse.OkRestResponse;
import com.example.azureservicebusexample.bridge.MsgBridgeStatus;
import com.example.azureservicebusexample.service.MsgBridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bridge")
public class BridgeStatusCtrl {
    private final MsgBridgeService bridgeService;

    public BridgeStatusCtrl(MsgBridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }

    @GetMapping(path = "/status")
    public ResponseEntity status() {
        List<MsgBridgeStatus> status = this.bridgeService.status();
        return OkRestResponse.ok(status).responseEntity();
    }
}
