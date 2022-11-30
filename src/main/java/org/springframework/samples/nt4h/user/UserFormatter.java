package org.springframework.samples.nt4h.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserFormatter {

    private final UserService userService;

    public String print(User user) {
        return user.getUsername();
    }

    public User parse(String text) {
        return userService.getUserByUsername(text);
    }

}
