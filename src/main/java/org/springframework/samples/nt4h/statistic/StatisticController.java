package org.springframework.samples.nt4h.statistic;


import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;
    private final UserService userService;


    private static final String VIEW_ALL_STATISTICS = "statistics/allStatistics";
    private static final String VIEW_DETAILED_STATISTICS = "statistics/detailedStatistics";

    @Autowired
    public StatisticController(StatisticService statisticService, UserService userService) {
        this.statisticService = statisticService;
        this.userService = userService;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return this.userService.getLoggedUser();
    }

    @ModelAttribute("statistic")
    public Statistic getStatistic() {
        return getLoggedUser().getStatistic();
    }

    @ModelAttribute("allStatistic")
    public List<Quartet<String, Double, Integer, Integer>> getAllStatistic() {
        return statisticService.getStatistics();
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{section}")
    public String specificStatistics(@PathVariable("section") int section, ModelMap model, HttpSession session) {
        List<Quartet<String, Double, Integer, Integer>> allStatistic = getAllStatistic();
        Quartet<String, Double, Integer, Integer> statistic = allStatistic.get(section);
        model.put("name", statistic.getValue0());
        model.put("average", statistic.getValue1());
        model.put("min", statistic.getValue2());
        model.put("max", statistic.getValue3());
        return VIEW_DETAILED_STATISTICS;
    }

    @GetMapping("/allStatistics")
    public String showStatistics() {
        return VIEW_ALL_STATISTICS;
    }



}

