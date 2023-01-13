package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuditoryUserController {

    private final AuditoryUserRepository auditoryUserRepository;
    private final UserService userService;
    private final List<AuditoryUser> users= new ArrayList<>();

    @Autowired
    public AuditoryUserController(AuditoryUserRepository auditoryUserRepository, UserService userService) {
        this.auditoryUserRepository = auditoryUserRepository;
        this.userService = userService;
    }

    @GetMapping("AuditoryUser/mod/{Id}")
    public @ResponseBody AuditoryUser modAuditoryUser(@PathVariable Integer Id) {
        if(!users.contains(userService.getUserById(Id).getId())){
            AuditoryUser AU= new AuditoryUser();
            AU.setNewCreator(userService.getLoggedUser().getUsername());
            AU.setModDate(LocalDateTime.now());
            AU.setUser(userService.getLoggedUser());
            AU.setUserMod(userService.getUserById(Id));
            users.add(AU);
        }
        AuditoryUser AU = users.stream().filter(u -> u.getUserMod().getId().equals(Id)).findFirst().get();
        AU.setNewCreator(userService.getLoggedUser().getUsername());
        AU.setModDate(LocalDateTime.now());
        AU.setUser(userService.getLoggedUser());
        AU.setUserMod(userService.getUserById(Id));
        auditoryUserRepository.save(AU);
        return AU;
    }
}

