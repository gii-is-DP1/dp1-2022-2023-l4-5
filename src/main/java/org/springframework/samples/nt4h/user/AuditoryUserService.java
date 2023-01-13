package org.springframework.samples.nt4h.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuditoryUserService {

    private final AuditoryUserRepository auditoryUserRepository;

    @Transactional
    public void save(AuditoryUser auditoryUser) {
        auditoryUserRepository.save(auditoryUser);
    }

}
