package org.springframework.samples.nt4h.statistic;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Statistic getStatisticById(int id) {
        return statisticRepository.findById(id).orElseThrow(() -> new NotFoundException("Statistic not found"));
    }

    @Transactional(readOnly = true)
    public List<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    @Transactional
    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    @Transactional
    public void deleteStatistic(Statistic statistic) {
        statisticRepository.delete(statistic);
    }

    @Transactional
    public void deleteStatisticById(int id) {
        statisticRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean statisticExists(int id) {
        return statisticRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Integer getNumGamesByUser(int userId) { return statisticRepository.numPlayedGamesByUserId(userId); }

    @Transactional
    public Integer getNumMinutesPlayedByUser(int userId) { return statisticRepository.numMinutesPlayedByUserId(userId); }

    @Transactional
    public List<Integer> getNumGamesByAllNumPlayers() {
        return Lists.newArrayList(2,3,4);
    }

    @Transactional
    public Integer getNumGamesByNumPlayers(int numP){
        return statisticRepository.numPlayerPerGame(numP);
    }

    @Transactional
    public Integer getNumGoldByNumPlayers(int userId) { return statisticRepository.NumGoldByPlayerId(userId);}

    @Transactional
    public Integer getNumGloryByNumPlayers(int userId) { return statisticRepository.NumGloryByPlayerId(userId);}

    @Transactional
    public Integer getNumOrcsByNumPlayers(int userId) { return statisticRepository.NumOrcsByPlayerId(userId);}

    @Transactional
    public Integer getNumWarLordByNumPlayers(int userId) { return statisticRepository.NumWarLordByPlayerId(userId);}

    @Transactional
    public Integer getDamageByNumPlayers(int userId) { return statisticRepository.NumDamageByPlayerId(userId);}

    @Transactional
    public Integer getWonGamesByNumPlayers(int userId) { return statisticRepository.NumWonGamesByPlayerId(userId);}


    //---------------------------------------------------------------------------------
    @Transactional
    public Integer getMaxNumPlayedGames() {
        return listAllUsers().stream().mapToInt(user -> getNumGamesByUser(user.getId())).max().orElse(0);
    }

    @Transactional
    public Integer getMinNumPlayedGames() {
        return listAllUsers().stream().mapToInt(user -> getNumGamesByUser(user.getId())).filter(i -> i > 0).min().orElse(0);
    }

    @Transactional
    public double getAverageNumPlayedGames() {
        return listAllUsers().stream().mapToInt(user ->  getNumGamesByUser(user.getId())).average().orElse(0);
    }

    @Transactional
    public Integer getMaxNumMinutesPlayed() {
        return listAllUsers().stream().mapToInt(user -> getNumMinutesPlayedByUser(user.getId())).max().orElse(0);
    }
    @Transactional
    public Integer getMinNumMinutesPlayed() {
        return listAllUsers().stream().mapToInt(user -> getNumMinutesPlayedByUser(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageNumMinutesPlayed() {
        return listAllUsers().stream().mapToInt(user -> getNumMinutesPlayedByUser(user.getId())).average().orElse(0);
    }

    @Transactional
    public Integer getMaxNumGamesByNumPlayers(){
        return Collections.max(getNumGamesByAllNumPlayers());
    }

    @Transactional
    public Integer getMinNumGamesByNumPlayers(){
        return getNumGamesByAllNumPlayers().stream().filter(i -> i > 0).min(Comparator.naturalOrder()).orElse(0);
    }

    @Transactional
    public double getAverageNumGamesByNumPlayers(){
        return getNumGamesByAllNumPlayers().stream().mapToInt(i -> i).average().orElse(0);
    }

    @Transactional
    public int getMaxGoldByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGoldByNumPlayers(user.getId())).max().orElse(0);
    }

    @Transactional
    public int getMinGoldByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGoldByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageGoldByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGoldByNumPlayers(user.getId())).average().orElse(0);
    }

    @Transactional
    public int getMaxGloryByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGloryByNumPlayers(user.getId())).max().orElse(0);
    }

    @Transactional
    public int getMinGloryByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGloryByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageGloryByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumGloryByNumPlayers(user.getId())).average().orElse(0);
    }

    @Transactional
    public int getMaxOrcsByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumOrcsByNumPlayers(user.getId())).max().orElse(0);
    }

    @Transactional
    public int getMinOrcsByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumOrcsByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageOrcsByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumOrcsByNumPlayers(user.getId())).average().orElse(0);
    }

    @Transactional
    public int getMaxWarLordByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumWarLordByNumPlayers(user.getId())).max().orElse(0);
    }
    @Transactional
    public int getMinWarLordByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumWarLordByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageWarLordByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getNumWarLordByNumPlayers(user.getId())).average().orElse(0);
    }

    @Transactional
    public int getMaxDamageByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getDamageByNumPlayers(user.getId())).max().orElse(0);
    }

    @Transactional
    public int getMinDamageByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getDamageByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageDamage() {
        return listAllUsers().stream().mapToInt(user -> getDamageByNumPlayers(user.getId())).average().orElse(0);
    }

    @Transactional
    public int getMaxWonGamesByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getWonGamesByNumPlayers(user.getId())).max().orElse(0);
    }

    @Transactional
    public int getMinWonGamesByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getWonGamesByNumPlayers(user.getId())).filter(i -> i > 0).min().orElse(0);
    }
    @Transactional
    public double getAverageWonGamesByPlayer() {
        return listAllUsers().stream().mapToInt(user -> getWonGamesByNumPlayers(user.getId())).average().orElse(0);
    }
    public List<User> listAllUsers() {return userRepository.findAll(); }
}
