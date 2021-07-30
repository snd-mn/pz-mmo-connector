package org.projectzion.game.mmoconnector.services;

import org.projectzion.game.mmoconnector.persistence.entities.security.User;
import org.projectzion.game.mmoconnector.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserByMail(String mail)
    {
        return userRepository.findByEmail(mail);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }
}
