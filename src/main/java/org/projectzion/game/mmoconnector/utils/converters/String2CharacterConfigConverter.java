package org.projectzion.game.mmoconnector.utils.converters;

import org.apache.commons.lang3.BooleanUtils;
import org.projectzion.game.mmoconnector.configs.CharacterConfig;
import org.projectzion.game.mmoconnector.configs.TargetSystemConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class String2CharacterConfigConverter implements Converter<String, CharacterConfig> {
    @Override
    public CharacterConfig convert(String s) {
        CharacterConfig config = new CharacterConfig();
        String[] splits = s.split(",");
        config.setCharacterName(splits[0]);
        config.setUsedForItemTransfer(Boolean.parseBoolean(splits[1]));

        return config;
    }
}
