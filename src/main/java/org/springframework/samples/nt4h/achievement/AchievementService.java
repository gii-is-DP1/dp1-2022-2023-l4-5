package org.springframework.samples.nt4h.achievement;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Achievement getAchievementById(int id) {
        return achievementRepository.findById(id).orElseThrow(() -> new NotFoundException("Achievement not found"));
    }

    @Transactional(readOnly = true)
    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }


    @Transactional
    public List<Achievement> getAllAchievementsByType(AchievementType type){return achievementRepository.findAchievementByType(type);}

    @Transactional
    public void deleteAchievementById(int id) {
        achievementRepository.deleteById(id);
    }

    @Transactional
    public void saveAchievement(Achievement achievement) { achievementRepository.save(achievement); }
}
