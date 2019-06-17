package com.its.springtipsreactiveviews;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;


@Controller
@RequiredArgsConstructor
public class ThymeleafViewGreetingsController {

    private final GreetingProducer greetingProducer;

    @GetMapping(value = "/greetings.do")
    String renderInitialView() {
        return "greetings";
    }

    @GetMapping(value = "/greetings-updates.do", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    String renderUpdatedView(@RequestParam("name") String name, Model model) {

        var rddcv = new ReactiveDataDriverContextVariable(this.greetingProducer.greet(name),1);

        var greetings = model.addAttribute("greetings", rddcv);

        return "greetings :: #greetings-block";
    }

}
