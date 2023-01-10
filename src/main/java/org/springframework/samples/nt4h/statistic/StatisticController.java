package org.springframework.samples.nt4h.statistic;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementType;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;
    private final PlayerService playerService;
    private final Advise advise;
    private final UserService userService;


    private static final String PAGE_WELCOME = "redirect:/welcome";
    private static final String PAGE_USER_DETAILS = "redirect:/users/details";

    @Autowired
    public StatisticController(StatisticService statisticService, PlayerService playerService, Advise advise, UserService userService) {
        this.playerService = playerService;
        this.statisticService = statisticService;
        this.userService = userService;
        this.advise = advise;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return this.userService.getLoggedUser();
    }

    @ModelAttribute("statistic")
    public Statistic getStatistic() {
        return getLoggedUser().getStatistic();
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


}

