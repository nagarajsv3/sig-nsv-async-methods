package com.nsv.service;

import com.nsv.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
@Service
public class GitHubLookupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder){
        restTemplate=restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String username) throws InterruptedException {
        LOGGER.info("Looking up {}",username);
        String url=String.format("https://api.github.com/users/%s",username);
        User object = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(object);
    }

/*    public User findUser(String username) throws InterruptedException {
        LOGGER.info("Looking up {}",username);
        String url=String.format("https://api.github.com/users/%s",username);
        User object = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000);
        return object;
    }*/



}
