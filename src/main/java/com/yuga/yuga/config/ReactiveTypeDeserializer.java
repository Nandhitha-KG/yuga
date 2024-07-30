package com.yuga.yuga.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class ReactiveTypeDeserializer extends StdDeserializer<Object> {

    protected ReactiveTypeDeserializer() {
        super(Object.class);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        if (node.isArray()) {
            return deserializeFlux(node, deserializationContext);
        } else {
            return deserializeMono(node, deserializationContext);
        }
    }

    private Mono<?> deserializeMono(JsonNode node, DeserializationContext deserializationContext) throws IOException {
        Object value = node.traverse(deserializationContext.getParser().getCodec()).readValueAs(Object.class);
        return Mono.justOrEmpty(value);
    }

    private Flux<?> deserializeFlux(JsonNode node, DeserializationContext deserializationContext) throws IOException {
        JsonParser parser = node.traverse(deserializationContext.getParser().getCodec());
        Flux<Object> flux = Flux.fromArray(parser.readValueAs(Object[].class));
        return flux;
    }
}