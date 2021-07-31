package org.projectzion.game.mmoconnector.utils.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {
    protected DateDeserializer(Class<?> vc) {
        super(vc);
    }

    protected DateDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected DateDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    public DateDeserializer() {
        super(Date.class);

    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Date date = null;
        JsonNode node = jp.getCodec().readTree(jp);
        Long time = node.get("date_long").asLong();
        date = new Date(time);
        return date;
    }
}
