package org.springframework.samples.nt4h.card.enemy.orc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrcService {
    private final OrcRepository orcRepository;

    @Transactional(readOnly = true)
    public Orc getOrcByName(String name) {
        return orcRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Orc> getAllOrcs() {
        return orcRepository.findAll();
    }

    @Transactional
    public void saveOrc(Orc orc) {
        orcRepository.save(orc);
    }

    @Transactional
    public void deleteOrc(Orc orc) {
        orcRepository.delete(orc);
    }

    @Transactional
    public void deleteOrcById(int id) {
        orcRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean orcExists(int id) {
        return orcRepository.existsById(id);
    }
}