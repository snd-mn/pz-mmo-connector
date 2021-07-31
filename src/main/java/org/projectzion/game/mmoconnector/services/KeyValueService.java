package org.projectzion.game.mmoconnector.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.projectzion.game.mmoconnector.persistence.entities.misc.KeyValue;
import org.projectzion.game.mmoconnector.persistence.repositories.KeyValueRepository;
import org.projectzion.game.mmoconnector.utils.json.serializer.DateDeserializer;
import org.projectzion.game.mmoconnector.utils.json.serializer.DateSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class KeyValueService {

    @Autowired
    KeyValueRepository keyValueRepository;

    public ObjectMapper objectMapper;

    //TODO create enum for this KEY, SerializerInstance, DeserializerInstance
    //TODO craete ObjectMapper by values of enum
    public ObjectMapper getObjectMapper() {
        if(objectMapper == null)
        {
            objectMapper = new ObjectMapper();
            SimpleModule moduledateserializer = new SimpleModule();
            moduledateserializer.addSerializer(Date.class, new DateSerializer(Date.class));
            objectMapper.registerModule(moduledateserializer);

            SimpleModule moduledatedeserializer = new SimpleModule();
            DateDeserializer dateSerializer = new DateDeserializer();
            moduledatedeserializer.addDeserializer(Date.class, dateSerializer);
            objectMapper.registerModule(moduledatedeserializer);
        }
        return objectMapper;
    }

    public KeyValue save(String key, Object object) throws JsonProcessingException {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey(key);
        keyValue.setValue(this.getObjectMapper().writeValueAsString(object));
        return keyValueRepository.save(keyValue);
    }

    public Object read(String key, Class clazz) throws IOException {
        Object redValue = null;
        KeyValue keyValue = keyValueRepository.findByKey(key);
        if(keyValue == null)
        {
            return null;
        }
        redValue = getObjectMapper().readValue(keyValue.getValue(),clazz);
        return redValue;
    }
}
