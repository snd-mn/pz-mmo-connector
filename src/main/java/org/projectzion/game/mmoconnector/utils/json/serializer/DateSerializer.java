package org.projectzion.game.mmoconnector.utils.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Date;

public class DateSerializer extends StdSerializer<Date> {
    public DateSerializer(Class<Date> t) {
        super(t);
    }

    protected DateSerializer(JavaType type) {
        super(type);
    }

    protected DateSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected DateSerializer(StdSerializer<?> src) {
        super(src);
    }

    //TODO does not get called...
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("date_long", value.getTime());
        gen.writeEndObject();
    }
}
