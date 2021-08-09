//package org.projectzion.game.mmoconnector.configs.security;
//
//import org.projectzion.game.mmoconnector.persistence.entities.security.User;
//import org.projectzion.game.mmoconnector.persistence.repositories.UserRepository;
//import org.projectzion.game.mmoconnector.utils.UserPrincipal;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//class DatabaseUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException
//    {
//        UserDetails userDetails = null;
//        User userCredentials = userRepository.findByEmail(username);
//        UserPrincipal userPrincipal = new UserPrincipal(userCredentials);
//        return userDetails;
//    }
//}