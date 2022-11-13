package org.springframework.samples.nt4h.achievement;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;

    @Transactional(readOnly = true)
    public Achievement getAchievementByName(String name) {
        return achievementRepository.findByName(name).orElseThrow(() -> new NotFoundException("Achievement not found"));
    }

    @Transactional(readOnly = true)
    public Achievement getAchievementById(int id) {
        return achievementRepository.findById(id).orElseThrow(() -> new NotFoundException("Achievement not found"));
    }

    @Transactional(readOnly = true)
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    @Transactional
    public void saveAchievement(Achievement achievement) {
        achievementRepository.save(achievement);
    }

    @Transactional
    public void deleteAchievement(Achievement achievement) {
        achievementRepository.delete(achievement);
    }

    @Transactional
    public void deleteAchievementById(int id) {
        achievementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean achievementExists(int id) {
        return achievementRepository.existsById(id);
    }
}
