package com.yuga.yuga.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.serializerByType(Mono.class, new ReactiveTypeSerializer());
            builder.deserializerByType(Mono.class, new ReactiveTypeDeserializer());
            builder.serializerByType(Flux.class, new ReactiveTypeSerializer());
            builder.deserializerByType(Flux.class, new ReactiveTypeDeserializer());
        };
    }
}

