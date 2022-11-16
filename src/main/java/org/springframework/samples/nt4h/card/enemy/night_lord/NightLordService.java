package org.springframework.samples.nt4h.card.enemy.night_lord;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class NightLordService {
    private final NightLordRepository nightLordRepository;

    @Transactional
    public NightLord getNightLordById(int id){ return nightLordRepository.findById(id);}

    @Transactional(readOnly = true)
    public NightLord getNightLordByName(String name) {
        return nightLordRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<NightLord> getAllNightLords() {
        return nightLordRepository.findAll();
    }

    @Transactional
    public void saveNightLord(NightLord nightLord) {
        nightLordRepository.save(nightLord);
    }

    @Transactional
    public void deleteNightLord(NightLord nightLord) {
        nightLordRepository.delete(nightLord);
    }

    @Transactional
    public void deleteNightLordById(int id) {
        nightLordRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean nightLordExists(int id) {
        return nightLordRepository.existsById(id);
    }
}
