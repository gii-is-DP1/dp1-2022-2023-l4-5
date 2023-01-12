package org.springframework.samples.nt4h.user;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        Statistic statistic = new Statistic();
        statistic.setDamageDealt(0);
        statistic.setGold(0);
        statistic.setGlory(0);
        statistic.setNumWonGames(0);
        statistic.setNumPlayedGames(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        user = User.builder()
            .username("user")
            .password("pass")
            .enable("true")
            .avatar("http://example.com/avatar")
            .tier(Tier.BRONZE)
            .description("Description")
            .authority("USER")
            .birthDate(LocalDate.of(2000, 1, 1))
            .friends(Lists.newArrayList())
            .statistic(statistic)
            .sentMessages(Lists.newArrayList())
            .receivedMessages(Lists.newArrayList())
            .player(new Player())
            .build();

    }

    @Test
    void testUserProperties() {
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo("pass");
        assertThat(user.getEnable()).isEqualTo("true");
        assertThat(user.getAvatar()).isEqualTo("http://example.com/avatar");
        assertThat(user.getTier()).isEqualTo(Tier.BRONZE);
        assertThat(user.getDescription()).isEqualTo("Description");
        assertThat(user.getAuthority()).isEqualTo("USER");
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(user.getFriends()).isEmpty();
        assertThat(user.getStatistic()).isNotNull();
        assertThat(user.getSentMessages()).isEmpty();
        assertThat(user.getReceivedMessages()).isEmpty();
        assertThat(user.getPlayer()).isNotNull();
    }

    @Test
    void testUserConstraints() {
        // Test username constraints
        user.setUsername(null);
        assertThat(validator.validate(user)).isNotEmpty();
        user.setUsername("");
        assertThat(validator.validate(user)).isNotEmpty();
        user.setUsername(" ");
        assertThat(validator.validate(user)).isEmpty();
        user.setUsername("user");
        assertThat(validator.validate(user)).isEmpty();

        // Test password constraints
        user.setPassword(null);
        assertThat(validator.validate(user)).isNotEmpty();
        user.setPassword(" ");
        assertThat(validator.validate(user)).isEmpty();
        user.setPassword("pass");
        assertThat(validator.validate(user)).isEmpty();

        // Test enable constraints
        user.setEnable(null);
        assertThat(validator.validate(user)).isEmpty();
        user.setEnable("");
        assertThat(validator.validate(user)).isEmpty();
        user.setEnable(" ");
        assertThat(validator.validate(user)).isEmpty();
        user.setEnable("true");
        assertThat(validator.validate(user)).isEmpty();

        // Test avatar constraints
        user.setAvatar(null);
        assertThat(validator.validate(user)).isEmpty();
        user.setAvatar("");
        assertThat(validator.validate(user)).isEmpty();
        user.setAvatar(" ");
        assertThat(validator.validate(user)).isNotEmpty();
        user.setAvatar("http://example.com/avatar");
        assertThat(validator.validate(user)).isEmpty();

        // Test tier constraints
        user.setTier(null);
        assertThat(validator.validate(user)).isEmpty();
        user.setTier(Tier.BRONZE);
        assertThat(validator.validate(user)).isEmpty();

        // Test description constraints
        user.setDescription(null);
        assertThat(validator.validate(user)).isNotEmpty();
        user.setDescription("");
        assertThat(validator.validate(user)).isNotEmpty();
        user.setDescription(" ");
        assertThat(validator.validate(user)).isNotEmpty();
        user.setDescription("a".repeat(101));
        assertThat(validator.validate(user)).isNotEmpty();
        user.setDescription("Description");
        assertThat(validator.validate(user)).isEmpty();

        // Test authority constraints
        user.setAuthority(null);
        assertThat(validator.validate(user)).isEmpty();
        user.setAuthority("");
        assertThat(validator.validate(user)).isEmpty();
        user.setAuthority(" ");
        assertThat(validator.validate(user)).isEmpty();
        user.setAuthority("ROLE_USER");
        assertThat(validator.validate(user)).isEmpty();
        user.setAuthority("USER");
        assertThat(validator.validate(user)).isEmpty();


        // Test birthDate constraints
        user.setBirthDate(null);
        assertThat(validator.validate(user)).isNotEmpty();
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        assertThat(validator.validate(user)).isEmpty();
    }

    @Test
    void testUserLifecycle() {
        // Test saving a user
        userService.saveUser(user);
        assertThat(userService.getUserById(user.getId())).isNotNull();

        // Test updating an existing user
        user.setUsername("newUsername");
        userService.saveUser(user);
        assertThat(userService.getUserById(user.getId()).getUsername()).isEqualTo("newUsername");

        // Test deleting an existing user
        userService.deleteUser(user);
        assertThatThrownBy(() -> userService.getUserById(user.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testUserQueries() {
        // Test finding all users
        userService.saveUser(user);
        assertThat(userService.getAllUsers()).isNotEmpty();

        // Test finding a user by username
        assertThat(userService.getUserByUsername(user.getUsername())).isNotNull();

        // Test finding a user by id
        assertThat(userService.getUserById(user.getId())).isNotNull();
    }
}
