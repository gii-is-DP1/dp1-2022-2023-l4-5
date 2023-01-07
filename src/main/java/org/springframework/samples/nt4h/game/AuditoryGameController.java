package org.springframework.samples.nt4h.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuditoryGameController {


    @Autowired
    AuditoryGameService auditoryGameService;
    @Autowired
    UserService userService;

    @GetMapping("AuditoryGame/get")
    public @ResponseBody AuditoryGame createAuditoryGame(){
    AuditoryGame AG= new AuditoryGame();
    AG.setUser(userService.getLoggedUser());
    AG.setGame(userService.getLoggedUser().getGame());
    auditoryGameService.saveGame(AG);
    return AG;
    }



}
