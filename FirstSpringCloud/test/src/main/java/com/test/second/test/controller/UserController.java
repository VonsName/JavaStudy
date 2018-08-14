package com.test.second.test.controller;

import com.netflix.discovery.DiscoveryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

   /* @Resource(name = "discoveryClient")
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {
        ServiceInstance serviceInstance = discoveryClient.get();
        LOGGER.info("/hello,host:" + serviceInstance.getHost() + ",service_id:" + serviceInstance.getServiceId());
        return "Hello World";
    }*/
}

