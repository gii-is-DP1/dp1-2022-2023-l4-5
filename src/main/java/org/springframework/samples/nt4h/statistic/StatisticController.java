package org.springframework.samples.nt4h.statistic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;
    private final PlayerService playerService;
    private final Advise advise;
    private final UserService userService;

    private static final String VIEW_STATISTIC = "redirect:/statistic";
    private static final String VIEW_EDIT_CREATE_STATISTIC = "statistic/statisticEdit";
    private static final String PAGE_WELCOME = "redirect:/welcome";
    private static final String PAGE_USER_DETAILS = "redirect:/users/details";

    @Autowired
    public StatisticController(StatisticService statisticService, PlayerService playerService, Advise advise, UserService userService) {
        this.playerService = playerService;
        this.statisticService = statisticService;
        this.userService = userService;
        this.advise = advise;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }




}

