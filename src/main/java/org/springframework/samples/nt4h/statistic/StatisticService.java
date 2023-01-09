package org.springframework.samples.nt4h.statistic;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserRepository;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public Integer getMaxNumPlayedGames() {
        List<Integer> aux = new ArrayList<>();
        List<User> ls = listAllUsers();
        for (User user: ls){
            aux.add(getNumGamesByUser(user.getId()));
        }
        return aux.stream().max(Comparator.naturalOrder()).get();
    }
    @Transactional
    public Integer getMinNumPlayedGames() {
        List<Integer> aux = new ArrayList<>();
        List<User> ls = listAllUsers();
        for (User user: ls){
            aux.add(getNumGamesByUser(user.getId()));
        }
        return aux.stream().min(Comparator.naturalOrder()).get();
    }
    @Transactional
    public Integer getAverageNumPlayedGames() {
        Integer t = 0;
        List<User> ls = listAllUsers();
        for (User user: ls){
           Integer a = getNumGamesByUser(user.getId());
            t += a;
        }
        return t/ls.size();
    }

    @Transactional
    public Integer getMaxNumMinutesPlayed() {
        List<Integer> aux = new ArrayList<>();
        List<User> ls = listAllUsers();
        for (User user: ls){
            aux.add(getNumMinutesPlayedByUser(user.getId()));
        }
        return aux.stream().max(Comparator.naturalOrder()).get();
    }
    @Transactional
    public Integer getMinNumMinutesPlayed() {
        List<Integer> aux = new ArrayList<>();
        List<User> ls = listAllUsers();
        for (User user: ls){
            aux.add(getNumMinutesPlayedByUser(user.getId()));
        }
        return aux.stream().min(Comparator.naturalOrder()).get();
    }
    @Transactional
    public Integer getAverageNumMinutesPlayed() {
        Integer t = 0;
        List<User> ls = listAllUsers();
        for (User user: ls){
            Integer a = getNumMinutesPlayedByUser(user.getId());
            t += a;
        }
        return t/ls.size();
    }
    public List<User> listAllUsers() {return userRepository.findAll(); }
}
