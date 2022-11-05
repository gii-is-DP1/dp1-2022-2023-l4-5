package org.springframework.samples.petclinic.achievement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AchivementService {
    private final AchievementRepository achievementRepository;

    @Transactional(readOnly = true)
    public Achievement findAchievementByName(String name) {
        return achievementRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public Achievement findAchievementById(int id) {
        return achievementRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Achievement> findAllAchievements() {
        return achievementRepository.findAll();
    }

    @Transactional
    public void saveAchievement(Achievement achievement) {
        achievementRepository.save(achievement);
    }

    // TODO: Actualizar Achievement.

    @Transactional
    public void deleteAchievement(Achievement achievement) {
        achievementRepository.delete(achievement);
    }

    @Transactional
    public void deleteAchievementById(int id) {
        achievementRepository.deleteById(id);
    }
}
