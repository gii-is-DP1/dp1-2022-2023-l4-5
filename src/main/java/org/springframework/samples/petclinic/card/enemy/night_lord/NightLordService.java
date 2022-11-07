package org.springframework.samples.petclinic.card.enemy.night_lord;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NightLordService {
    private final NightLordRepository nightLordRepository;

    @Transactional(readOnly = true)
    public NightLord findNightLordByName(String name) {
        return nightLordRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<NightLord> findAllNightLords() {
        return nightLordRepository.findAll();
    }

    @Transactional
    public void saveNightLord(NightLord nightLord) {
        nightLordRepository.save(nightLord);
    }

    // TODO: Actualizar NightLord.

    @Transactional
    public void deleteNightLord(NightLord nightLord) {
        nightLordRepository.delete(nightLord);
    }

    @Transactional
    public void deleteNightLordById(int id) {
        nightLordRepository.deleteById(id);
    }
}
