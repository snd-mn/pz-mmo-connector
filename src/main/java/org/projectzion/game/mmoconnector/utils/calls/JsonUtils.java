package org.projectzion.game.mmoconnector.utils.calls;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    @Bean
    public static ObjectMapper getObjectMapper(){
        //may same configuraiton stuff
        return new ObjectMapper();
    }
}
