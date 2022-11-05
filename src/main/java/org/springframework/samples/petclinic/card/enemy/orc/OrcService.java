package org.springframework.samples.petclinic.card.enemy.orc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrcService {
    private final OrcRepository orcRepository;

    @Transactional(readOnly = true)
    public Orc findOrcByName(String name) {
        return orcRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Orc> findAllOrcs() {
        return orcRepository.findAll();
    }

    @Transactional
    public void saveOrc(Orc orc) {
        orcRepository.save(orc);
    }

    // TODO: Actualizar Orc.

    @Transactional
    public void deleteOrc(Orc orc) {
        orcRepository.delete(orc);
    }

    @Transactional
    public void deleteOrcById(int id) {
        orcRepository.deleteById(id);
    }
}
