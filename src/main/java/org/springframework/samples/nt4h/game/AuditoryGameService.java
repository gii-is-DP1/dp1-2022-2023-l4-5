package org.springframework.samples.nt4h.game;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuditoryGameService {

    private final AuditoryGameRepository auditoryGameRepository;

    @Transactional
    public void saveGame(AuditoryGame auditoryGame) {
        auditoryGameRepository.save(auditoryGame);
    }
}
