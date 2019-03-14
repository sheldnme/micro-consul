package com.example.colud.micro.consul.controller;

import com.example.colud.micro.consul.config.BaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dyj
 * @description
 * @date created in 20:16 2019/3/13
 * @modify history
 */
@RestController
@Slf4j
public class MicroController {

    @Autowired
    private Registration registration;

    @Autowired
    private BaseProperties baseProperties;

    @Autowired
    private Environment environment;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/hello")
    public String hello() {
        log.info("token: {}", baseProperties.getToken());
        return "hello consul value: " + environment.getProperty("micro.service.token");
    }

    @RequestMapping("/me")
    public ServiceInstance me() {
        return this.registration;
    }

    @RequestMapping("/lb")
    public ServiceInstance lb() {
        return this.loadBalancerClient.choose(environment.getProperty("spring.application.name"));
    }

    @RequestMapping("/instances")
    public List<ServiceInstance> instances() {
        return this.discoveryClient.getInstances(environment.getProperty("spring.application.name"));
    }

//
//    @RequestMapping("/rest")
//    public String rest() {
//        return this.restTemplate.getForObject("http://" + this.appName + "/me",
//                String.class);
//    }
//
//    @RequestMapping("/choose")
//    public String choose() {
//        return this.loadBalancer.choose(this.appName).getUri().toString();
//    }
//
//    @RequestMapping("/myenv")
//    public String env(@RequestParam("prop") String prop) {
//        return this.env.getProperty(prop, "Not Found");
//    }
//
//    @RequestMapping("/prop")
//    public String prop() {
//        return sampleProperties().getProp();
//    }
//

//
//    /*
//     * @Bean public SubtypeModule sampleSubtypeModule() { return new
//     * SubtypeModule(SimpleRemoteEvent.class); }
//     */
//
//    @RequestMapping("/feign")
//    public String feign() {
//        return this.sampleClient.choose();
//    }
//
//    @Bean
//    public SampleProperties sampleProperties() {
//        return new SampleProperties();
//    }
//
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    /*
//     * @Override public void onApplicationEvent(SimpleRemoteEvent event) {
//     * log.info("Received event: {}", event); }
//     */
//
//    @FeignClient("testConsulApp")
//    public interface SampleClient {
//
//        @RequestMapping(value = "/choose", method = RequestMethod.GET)
//        String choose();
//
//    }

}
