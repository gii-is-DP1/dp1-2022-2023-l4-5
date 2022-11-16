package org.springframework.samples.nt4h.card.enemy.orc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrcService {
    private final OrcRepository orcRepository;

    @Transactional
    public Orc getOrcById(int id){return orcRepository.findById(id);}

    @Transactional(readOnly = true)
    public List<Orc> getOrcByName(String name) {
        return orcRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Orc> getAllOrcs() {
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
