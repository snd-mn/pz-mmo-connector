package org.projectzion.game.mmoconnector.services;

import org.projectzion.game.mmoconnector.persistence.entities.Character;
import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class CharacterService {
    @Autowired
    UserRepository userRepository;

    public Character getCharacterForItemTransfer(User user) throws Exception {
        if(user.getCharacters().size() == 0)
        {
            throw new Exception("no characters for user " + user.getEmail());
        }

        AtomicReference<Character> character = new AtomicReference<>();
        user.getCharacters().forEach(tmpCharacter -> {
            if(tmpCharacter.isUsedForItemTransfer()){
                character.set(tmpCharacter);
            }
        });
        if(character.get() == null){
            character.set(user.getCharacters().iterator().next());
        }
        return character.get();
    }
}
