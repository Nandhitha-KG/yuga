package com.yuga.yuga.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class ReactiveTypeSerializer extends StdSerializer<Object> {

    protected ReactiveTypeSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value instanceof Mono) {
            serializeMono((Mono<?>) value, jsonGenerator);
        } else if (value instanceof Flux) {
            serializeFlux((Flux<?>) value, jsonGenerator);
        } else {
            throw new IllegalArgumentException("Unsupported reactive type: " + value.getClass().getName());
        }
    }

    private void serializeMono(Mono<?> mono, JsonGenerator jsonGenerator) throws IOException {
        mono.subscribe(monoValue -> {
            try {
                jsonGenerator.writeObject(monoValue);
            } catch (IOException e) {
                throw new RuntimeException("Failed to serialize Mono", e);
            }
        });
    }

    private void serializeFlux(Flux<?> flux, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeStartArray();
        flux.subscribe(fluxValue -> {
            try {
                jsonGenerator.writeObject(fluxValue);
            } catch (IOException e) {
                throw new RuntimeException("Failed to serialize Flux", e);
            }
        });
        jsonGenerator.writeEndArray();
    }
}
