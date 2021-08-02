package org.projectzion.game.mmoconnector.utils.converters;

import org.projectzion.game.mmoconnector.configs.CharacterConfig;
import org.projectzion.game.mmoconnector.configs.NodeTypeAmountConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationPropertiesBinding
public class String2NodeTypeAmountConfigConverter implements Converter<String, List<NodeTypeAmountConfig>> {
    @Override
    public List<NodeTypeAmountConfig> convert(String s) {
        String[] splits = s.split(";");
        List<NodeTypeAmountConfig> configs = new ArrayList<>();
        NodeTypeAmountConfig config = new NodeTypeAmountConfig();

        for(int i = 0; i < splits.length; i++){
            if(i % 4 == 0){
                config.setNodeTypeId(Long.valueOf(splits[i]));
            }
            if(i % 4 == 1){
                config.setTargetSystemItemId(Long.valueOf(splits[i]));
            }
            if(i % 4 == 2){
                config.setAmount(Float.valueOf(splits[i]));
            }
            if(i % 4 == 3){
                config.setTargetSystemId(Long.valueOf(splits[i]));
                configs.add(config);
                config = new NodeTypeAmountConfig();
            }
        }

        return configs;
    }
}
