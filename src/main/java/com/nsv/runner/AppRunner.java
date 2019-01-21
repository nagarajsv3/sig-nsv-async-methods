package com.nsv.runner;

import com.nsv.model.User;
import com.nsv.service.GitHubLookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService){
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long starttime = System.currentTimeMillis();
        log.info("start time {}",starttime);

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> user1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> user2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> user3 = gitHubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(user1,user2,user3).join();
        long endtime = System.currentTimeMillis();
        log.info("end time {}",endtime);
        log.info("time elapsed {}",(endtime-starttime));

        log.info("User 1 {}",user1.get());
        log.info("User 2 {}",user2.get());
        log.info("User 3 {}",user3.get());
    }
}
