package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CostomLogoutSuccesHandler implements LogoutSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByUsername(principal.getUsername());
        user.setIsConnected(false);
        userService.saveUser(user);
        response.sendRedirect("/");
    }
}
