package com.dbyuvaraj.springboot.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class APIScheduler {

    private static final List<String> API_LIST = new ArrayList<String>() {{
        add("https://cat-fact.herokuapp.com/facts/591f98803b90f7150a19c229");
        add("https://cat-fact.herokuapp.com/facts/5b1b411d841d9700146158d9");
        add("https://cat-fact.herokuapp.com/facts/58e009550aac31001185ed12");
        add("https://cat-fact.herokuapp.com/facts/58e008340aac31001185ecfb");
        add("https://cat-fact.herokuapp.com/facts/599f87db9a11040c4a16343f");
    }};

    @Scheduled(fixedDelay = 10000)
    public void callApi() {
        log.info("Scheduler started");
        API_LIST.forEach(api ->
                CompletableFuture.runAsync(() -> {
                    try {
                        log.info("Calling api {}", api);
                        Content content = Request.Get(api).execute().returnContent();
                        log.info("The response from API {}", api);
                        String responseString = content.asString();
                        log.info(responseString.substring(0, 100));
                    } catch (IOException e) {
                        log.error("Error while processing the api {}", api, e);
                    }
                })
        );
    }

}
