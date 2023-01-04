package org.springframework.samples.nt4h.turn;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttackEnemyService {

    private final PlayerService playerService;
}
