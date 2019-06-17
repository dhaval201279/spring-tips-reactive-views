package com.its.springtipsreactiveviews;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

@Configuration
class WebsocketConfiguration {

    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler webSocketHandler) {
        /*return new SimpleUrlHandlerMapping(
            {
                super.setOrder
                setUrlMap(Map.of("/ws/greetings", webSocketHandler));
            }
        );*/

        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(1);
        simpleUrlHandlerMapping.setUrlMap(Map.of("/ws/greetings", webSocketHandler));
        return simpleUrlHandlerMapping;
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler(GreetingProducer greetingProducer) {
        return new WebSocketHandler() {
            @Override
            public Mono<Void> handle(WebSocketSession webSocketSession) {
                /*Flux<String> namesToGreet = webSocketSession
                                                .receive()
                                                .map(WebSocketMessage::getPayloadAsText);
                Flux<Greeting> responseGreeting = namesToGreet
                                                    .flatMap(greetingProducer::greet);

                Flux<String> stringResponses = responseGreeting
                                                    .map(Greeting::getMessage);

                Flux<WebSocketMessage> map = stringResponses
                                                .map(webSocketSession::textMessage);*/
                /**
                 * Refactored version of above 4 lines of commented code
                 * since its jdk 11, need to replace above return type to var as below
                 * */
                var greetings = webSocketSession
                                    .receive()
                                    .map(WebSocketMessage::getPayloadAsText)
                                    .flatMap(greetingProducer::greet)
                                    .map(Greeting::getMessage)
                                    .map(webSocketSession::textMessage);
                return webSocketSession.send(greetings);
            }
        };
    }
}
