package org.projectzion.game.mmoconnector.scoped.request;

import org.projectzion.game.mmoconnector.utils.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class RequestScoped {

    @Bean
    @Scope("request")
    public UserPrincipal currentUserPrincipal() {
        UserPrincipal userPrincipal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated())
        {
            String currentUserName = authentication.getName();
            userPrincipal = (UserPrincipal) authentication.getPrincipal();
        }

        return userPrincipal;
    }
}
