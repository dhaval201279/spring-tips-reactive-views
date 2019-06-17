package com.its.springtipsreactiveviews;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class SseGreetingsController {
    private final GreetingProducer producer;

    @GetMapping(value = "/sse/greetings/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Publisher<String> greet(@PathVariable String name) {
        return producer
                .greet(name)
                .map(greeting -> greeting.getMessage());
    }

}
