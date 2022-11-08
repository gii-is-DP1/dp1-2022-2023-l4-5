package org.springframework.samples.petclinic.effect;



import org.springframework.samples.petclinic.card.enemy.Enemy;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.player.Player;

import java.util.Random;


public abstract class Effect {

    private Phase phase;
    private Player player;
    public void robarCartas(Integer n){
        for(int i=0; i<n; i++){
            this.player.hand.add(this.player.deck.get(0));
            this.player.deck.remove(0);
        }
    }
    public void damageToEnemy(Integer damage, Enemy enemy){
        enemy.setHealth(enemy.getHealth-damage);
    }
    public void loseCards(Integer n){
        for(int i=0; i<n; i++){
            this.player.hand.remove(0);
        }
    }
    public void damageToHero(Enemy enemy){
       Integer n=enemy.getHealth();
        for(int i=0; i<n; i++){
            this.player.deck.remove(0);
            if(player.deck.isEmpty()){
                player.getHero().setHealth(player.getHero().health-1);
                if(player.getHero().health==0){
                    player.setHero(null);
                }
            }
        }
    }
    public void preventDamageEnemy(Enemy enemy){

    }
    public void shuffle(Player player){}
    public void conseguirGloria(Player player,Enemy enemy){}



}
