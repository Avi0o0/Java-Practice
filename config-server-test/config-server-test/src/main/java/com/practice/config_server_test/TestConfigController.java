package com.practice.config_server_test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestConfigController {

    @Value("${test.message:Default message}")
    private String testMessage;

    @GetMapping("/test-config")
    public String getTestMessage() {
        return testMessage;
    }
}
