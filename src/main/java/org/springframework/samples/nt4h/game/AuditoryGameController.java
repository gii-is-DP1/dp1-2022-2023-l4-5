package org.springframework.samples.nt4h.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class AuditoryGameController {


    private final AuditoryGameService auditoryGameService;
    private final UserService userService;

    @Autowired
    public AuditoryGameController(AuditoryGameService auditoryGameService, UserService userService) {
        this.auditoryGameService = auditoryGameService;
        this.userService = userService;
    }

    @GetMapping("AuditoryGame/get")
    public @ResponseBody AuditoryGame createAuditoryGame(){
    AuditoryGame AG= new AuditoryGame();
    AG.setCreator(userService.getLoggedUser().getUsername());
    AG.setCreateDate(LocalDateTime.now());
    AG.setUser(userService.getLoggedUser());
    AG.setGame(userService.getLoggedUser().getGame());
    auditoryGameService.saveGame(AG);
    return AG;
    }



}
