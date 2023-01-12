package org.springframework.samples.nt4h.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CacheManager extends BaseCacheManager {

    private final String DEFEND = "defend"; // Defensa contra ataque de orcos.
    private final String ATTACK = "attack"; // Ataque contra orcos.
    private final String NEXT_URL = "nextUrl"; // Siguiente url a la que se redirige.
    private final String FIRST_SLASH = "firstSlash"; // Nos permite saber si es la primera vez que se ha utilizado espadazo.
    private final String ATTACKED_ENEMY = "attackedEnemy"; // El enemigo que ha sido seleccionado.
    private final String FIRST_TO_THE_HEART = "firstToTheHeart"; // Nos permite saber si es la primera vez que se ha utilizado el ataque al corazón.
    private final String FIRST_STEALTH_ATTACK = "firstStealthAttack"; // Nos permite saber si es la primera vez que se ha utilizado el ataque furtivo.
    private final String ALREADY_ATTACKED_WITH_PRECISE_BOW = "alreadyAttackedWithPreciseBow"; // Nos permite saber si ya se ha atacado con el arco preciso.
    private final String PREVENT_DAMAGE_FROM_ENEMIES = "preventDamageFromEnemies"; // Permite anular el ataque de varios enemigos.
    private final String CAPTURED_ENEMIES = "capturedEnemies"; // Enemigos capturados.
    private final String ENEMIES_ALSO_ATTACKED = "enemiesAlsoAttacked"; // Enemigos que también van a ser también atacados.
    private final String ENEMIES_THAT_RECEIVE_MORE_DAMAGE = "enemiesThatReceiveMoreDamage"; // Enemigos que van a recibir más daño.
    private final String ALREADY_ATTACKED_WITH_STAFF = "alreadyAttackedWithStaff"; // Nos permite saber si ya se ha atacado con el bastón.
    private final String HAS_TO_BE_DELETED_ABILITY = "hasToBeDeletedAbility"; // Nos permite saber si hay que eliminar una habilidad.
    private final String SHARPENING_STONE = "sharpeningStone"; // Nos permite saber si se ha utilizado la piedra amolar.
    private final String HAS_ADDED_LIFE_TO_ORCS = "hasAddedLifeToOrcs"; // Nos permite saber si se ha añadido vida a los orcos.
    private final EnemyService enemyService;

    public void deleteEndAttackHero(HttpSession session) {
        removeAttack(session);
        removeAttackedEnemy(session);
        removeEnemiesAlsoAttacked(session);
        removeHasToBeDeletedAbility(session);
        removeNextUrl(session);
    }

    public void deleteEndAttackEnemy(HttpSession session) {
        removeFirstSlash(session);
        removeFirstToTheHeart(session);
        removeFirstStealthAttack(session);
        removeAlreadyAttackedWithPreciseBow(session);
        removeEnemiesThatReceiveMoreDamage(session);
        removeAlreadyAttackedWithStaff(session);
        removeSharpeningStone(session);
        removeHasAddedLifeToOrcs(session);
        removePreventDamageFromEnemies(session);
        removeCapturedEnemies(session);
        removeHasAddedLifeToOrcs(session);
        removeDefend(session);


    }


    @Autowired
    public CacheManager(EnemyService enemyService) {
        this.enemyService = enemyService;
    }


    // Defensa.
    public void setDefend(HttpSession session, Integer defend) {
        addInteger(session, DEFEND, defend);
    }

    public Integer getDefend(HttpSession session) {
        return getInteger(session, DEFEND);
    }

    public void removeDefend(HttpSession session) {
        session.removeAttribute(DEFEND);
    }

    // Ataque.
    public void addAttack(HttpSession session, Integer attack) {
        addInteger(session, ATTACK, attack);
    }

    public Integer getAttack(HttpSession session) {
        return getInteger(session, ATTACK);
    }

    public void removeAttack(HttpSession session) {
        session.removeAttribute(ATTACK);
    }

    // Siguiente url
    public void setNextUrl(HttpSession session, String nextUrl) {
        session.setAttribute(NEXT_URL, nextUrl);
    }

    public Boolean hasNextUrl(HttpSession session) {
        return hasAttribute(session);
    }
    public Optional<String> getNextUrl(HttpSession session) {
        return getString(session);
    }

    public void removeNextUrl(HttpSession session) {
        session.removeAttribute(NEXT_URL);
    }

    // Primer espadazo?
    public void setFirstSlash(HttpSession session) {
        session.setAttribute(FIRST_SLASH, true);
    }

    public Boolean isFirstSlash(HttpSession session) {
        return !getBoolean(session, FIRST_SLASH);
    }

    public void removeFirstSlash(HttpSession session) {
        session.removeAttribute(FIRST_SLASH);
    }

    // Enemigo que ha sido seleccionado.
    public EnemyInGame getAttackedEnemy(HttpSession session) {
        return parseEnemy(session, enemyService::getEnemyInGameById);
    }

    public void setAttackedEnemy(HttpSession session, Integer attackedEnemy) {
        session.setAttribute(ATTACKED_ENEMY, attackedEnemy);
    }

    public void removeAttackedEnemy(HttpSession session) {
        session.removeAttribute(ATTACKED_ENEMY);
    }

    // Primer ataque al corazón?
    public void setFirstToTheHeart(HttpSession session) {
        session.setAttribute(FIRST_TO_THE_HEART, true);
    }

    public Boolean isFirstToTheHeart(HttpSession session) {
        return !getBoolean(session, FIRST_TO_THE_HEART);
    }

    public void removeFirstToTheHeart(HttpSession session) {
        session.removeAttribute(FIRST_TO_THE_HEART);
    }

    // Primer ataque furtivo?
    public void setFirstStealthAttack(HttpSession session) {
        session.setAttribute(FIRST_STEALTH_ATTACK, true);
    }

    public Boolean isFirstStealthAttack(HttpSession session) {
        return !getBoolean(session, FIRST_STEALTH_ATTACK);
    }

    public void removeFirstStealthAttack(HttpSession session) {
        session.removeAttribute(FIRST_STEALTH_ATTACK);
    }

    // Ya se ha atacado con el arco preciso?
    public void addAlreadyAttackedWithPreciseBow(HttpSession session) {
        addEnemies(session, ALREADY_ATTACKED_WITH_PRECISE_BOW, getAttackedEnemy(session), enemy -> hasAlreadyAttackedWithPreciseBow(session));
    }

    public List<EnemyInGame> getAlreadyAttackedWithPreciseBow(HttpSession session) {
        return parseEnemies(session, ALREADY_ATTACKED_WITH_PRECISE_BOW, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public void removeAlreadyAttackedWithPreciseBow(HttpSession session) {
        session.removeAttribute(ALREADY_ATTACKED_WITH_PRECISE_BOW);
    }

    public Boolean hasAlreadyAttackedWithPreciseBow(HttpSession session) {
        return getAlreadyAttackedWithPreciseBow(session).stream().anyMatch(enemy -> enemy.getId().equals(getAttackedEnemy(session).getId()));
    }

    // Permite anular el ataque de varios enemigos.
    public void addPreventDamageFromEnemies(HttpSession session) {
        addEnemies(session, PREVENT_DAMAGE_FROM_ENEMIES, getAttackedEnemy(session), enemy -> hasPreventDamageFromEnemies(session));
    }

    public void addAllInBattlePreventDamageFromEnemies(HttpSession session, Game game) {
        session.setAttribute(PREVENT_DAMAGE_FROM_ENEMIES,game.getActualOrcs().stream().map(enemy -> enemy.getId().toString()).collect(Collectors.joining(",")));
    }

    public List<EnemyInGame> getPreventDamageFromEnemies(HttpSession session) {
        return parseEnemies(session, PREVENT_DAMAGE_FROM_ENEMIES, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public void removePreventDamageFromEnemies(HttpSession session) {
        session.removeAttribute(PREVENT_DAMAGE_FROM_ENEMIES);
    }

    public Boolean hasPreventDamageFromEnemies(HttpSession session) {
        return getPreventDamageFromEnemies(session).stream().anyMatch(enemy -> enemy.getId().equals(getAttackedEnemy(session).getId()));
    }

    public Boolean hasPreventDamageFromEnemies(HttpSession session, EnemyInGame enemy) {
        return getPreventDamageFromEnemies(session).stream().anyMatch(enemyInGame -> enemyInGame.getId().equals(enemy.getId()));
    }

    // Enemigos capturados.
    public void addCapturedEnemies(HttpSession session) {
        addEnemies(session, CAPTURED_ENEMIES, getAttackedEnemy(session), enemy -> hasCapturedEnemies(session));
    }

    public void addCapturedEnemies(HttpSession session, EnemyInGame enemy) {
        addEnemies(session, CAPTURED_ENEMIES, enemy, enemyInGame -> hasCapturedEnemies(session, enemyInGame));
    }

    public List<EnemyInGame> getCapturedEnemies(HttpSession session) {
        return parseEnemies(session, CAPTURED_ENEMIES, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public void removeCapturedEnemies(HttpSession session) {
        session.removeAttribute(CAPTURED_ENEMIES);
    }

    public Boolean hasCapturedEnemies(HttpSession session) {
        return getCapturedEnemies(session).stream().anyMatch(enemy -> enemy.getId().equals(getAttackedEnemy(session).getId()));
    }

    public Boolean hasCapturedEnemies(HttpSession session, EnemyInGame enemy) {
        return getCapturedEnemies(session).stream().anyMatch(enemyInGame -> enemyInGame.getId().equals(enemy.getId()));
    }

    // Enemigos que también van a ser atacados.
    public void addEnemiesAlsoAttacked(HttpSession session) {
        addEnemies(session, ENEMIES_ALSO_ATTACKED, getAttackedEnemy(session), enemy -> hasEnemiesAlsoAttacked(session));
    }

    public void addAllEnemiesAlsoAttacked(HttpSession session, Game game) {
        session.setAttribute(ENEMIES_ALSO_ATTACKED,game.getActualOrcs().stream().map(enemy -> enemy.getId().toString()).collect(Collectors.joining(",")));
    }

    public List<EnemyInGame> getEnemiesAlsoAttacked(HttpSession session) {
        return parseEnemies(session, ENEMIES_ALSO_ATTACKED, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public void removeEnemiesAlsoAttacked(HttpSession session) {
        session.removeAttribute(ENEMIES_ALSO_ATTACKED);
    }

    public Boolean hasEnemiesAlsoAttacked(HttpSession session) {
        return getEnemiesAlsoAttacked(session).stream().anyMatch(enemy -> enemy.getId().equals(getAttackedEnemy(session).getId()));
    }

    public Boolean hasEnemiesAlsoAttacked(HttpSession session, EnemyInGame enemy) {
        return getEnemiesAlsoAttacked(session).stream().anyMatch(enemyInGame -> enemyInGame.getId().equals(enemy.getId()));
    }

    // Enemigos a los que va a hacer más daño.
    public void addEnemiesThatReceiveMoreDamage(HttpSession session) {
        addEnemies(session, ENEMIES_THAT_RECEIVE_MORE_DAMAGE, getAttackedEnemy(session));
    }

    public List<EnemyInGame> getEnemiesThatReceiveMoreDamage(HttpSession session) {
        return parseEnemies(session, ENEMIES_THAT_RECEIVE_MORE_DAMAGE, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public Integer getEnemiesThatReceiveMoreDamageForEnemy(HttpSession session, EnemyInGame enemy) {
        return Math.toIntExact(getEnemiesThatReceiveMoreDamage(session).stream().filter(enemyInGame -> enemyInGame.getId().equals(enemy.getId())).count());
    }

    public void removeEnemiesThatReceiveMoreDamage(HttpSession session) {
        session.removeAttribute(ENEMIES_THAT_RECEIVE_MORE_DAMAGE);
    }

    // Ya se ha atacado con el bastón?
    public void addAlreadyAttackedWithStaff(HttpSession session) {
        addEnemies(session, ALREADY_ATTACKED_WITH_STAFF, getAttackedEnemy(session), enemy -> hasAlreadyAttackedWithStaff(session));
    }

    public List<EnemyInGame> getAlreadyAttackedWithStaff(HttpSession session) {
        return parseEnemies(session, ALREADY_ATTACKED_WITH_STAFF, enemyId -> enemyService.getEnemyInGameById(Integer.parseInt(enemyId)));
    }

    public void removeAlreadyAttackedWithStaff(HttpSession session) {
        session.removeAttribute(ALREADY_ATTACKED_WITH_STAFF);
    }

    public Boolean hasAlreadyAttackedWithStaff(HttpSession session) {
        return getAlreadyAttackedWithStaff(session).stream().anyMatch(enemy -> enemy.getId().equals(getAttackedEnemy(session).getId()));
    }

    // Tenemos que borrar la habilidad?
    public void setHasToBeDeletedAbility(HttpSession session) {
        session.setAttribute(HAS_TO_BE_DELETED_ABILITY, Boolean.TRUE);
    }

    public void removeHasToBeDeletedAbility(HttpSession session) {
        session.removeAttribute(HAS_TO_BE_DELETED_ABILITY);
    }

    public Boolean hasToBeDeletedAbility(HttpSession session) {
        return session.getAttribute(HAS_TO_BE_DELETED_ABILITY) != null;
    }

    public void setSharpeningStone(HttpSession session) {
        session.setAttribute(SHARPENING_STONE, Boolean.TRUE);
    }

    public void removeSharpeningStone(HttpSession session) {
        session.removeAttribute(SHARPENING_STONE);
    }

    public Integer getSharpeningStone(HttpSession session) {
        return session.getAttribute(SHARPENING_STONE) == null ? 0 : 1;
    }

    // Ya se ha añadido vida a los orcos?
    public void setHasAddedLifeToOrcs(HttpSession session) {
        session.setAttribute(HAS_ADDED_LIFE_TO_ORCS, Boolean.TRUE);
    }

    public void removeHasAddedLifeToOrcs(HttpSession session) {
        session.removeAttribute(HAS_ADDED_LIFE_TO_ORCS);
    }

    public Boolean hasAddedLifeToOrcs(HttpSession session) {
        return session.getAttribute(HAS_ADDED_LIFE_TO_ORCS) != null;
    }

















}
