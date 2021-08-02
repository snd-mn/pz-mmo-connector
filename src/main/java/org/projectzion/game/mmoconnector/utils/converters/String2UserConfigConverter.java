package org.projectzion.game.mmoconnector.utils.converters;

import org.projectzion.game.mmoconnector.configs.UserConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class String2UserConfigConverter implements Converter<String, UserConfig> {
    @Override
    public UserConfig convert(String s) {
        UserConfig config = new UserConfig();
        String[] splits = s.split(",");
        config.setEmail(splits[0]);
        config.setPassword(splits[1]);

        return config;
    }
}
