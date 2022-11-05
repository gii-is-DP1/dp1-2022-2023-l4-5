package org.springframework.samples.petclinic.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFormatter {

    private final UserRepository userRepository;

    public String print(User user) {
        return user.getUsername();
    }

    public User parse(String text) {
        return userRepository.findByUsername(text);
    }

}
