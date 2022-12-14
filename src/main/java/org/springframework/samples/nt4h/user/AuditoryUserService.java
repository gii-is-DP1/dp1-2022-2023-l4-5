package org.springframework.samples.nt4h.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AuditoryUserService {

    private final AuditoryUserRepository auditoryUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(AuditoryUser auditoryUser) {
        auditoryUserRepository.save(auditoryUser);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
