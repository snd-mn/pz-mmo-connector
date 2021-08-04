package org.projectzion.game.mmoconnector.utils.converters;

import org.projectzion.game.mmoconnector.configs.TargetSystemConfig;
import org.projectzion.game.mmoconnector.configs.UserConfig;
import org.projectzion.game.mmoconnector.persistence.entities.TargetSystem;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class String2TargetSystemConfigConverter implements Converter<String, TargetSystemConfig> {
    @Override
    public TargetSystemConfig convert(String s) {
        TargetSystemConfig config = new TargetSystemConfig();
        String[] splits = s.split(",");
        config.setId(Long.valueOf(splits[0]));
        config.setIp(splits[1]);
        config.setPort(splits[2]);

        return config;
    }
}
