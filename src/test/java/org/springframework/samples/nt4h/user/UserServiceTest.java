package org.springframework.samples.nt4h.user;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    protected UserService userService;

    @MockBean
    private Advise advise;

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
            .authority("DOKTOL")
            .birthDate(LocalDate.of(2000, 1, 1))
            .friends(Lists.newArrayList())
            .statistic(statistic)
            .sentMessages(Lists.newArrayList())
            .receivedMessages(Lists.newArrayList())
            .player(new Player())
            .build();
    }

    @Test
    public void findIDTrue() {
        Assertions.assertNotNull(user);
        Assertions.assertEquals("user", user.getUsername());
    }

    @Test
    public void findNameTrue() {
        Assertions.assertNotNull(user);
        Assertions.assertEquals("DOKTOL", user.getAuthority());
    }

    @Test
    public void findNameFalse() {
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(Authority.DOKTOL, user.getAuthority());
    }

    @Test
    @Disabled
    public void shouldInsertUser() {
        Integer n = this.userService.getAllUsers().size();
        User user = new User();
        user.setUsername("Prueba");
        user.setPassword("Prueba");
        user.setDescription("Prueba");
        user.setBirthDate(LocalDate.now());
        this.userService.saveUser(user);
        Assertions.assertEquals(user.getUsername(), this.userService.getUserById(n + 1).getUsername());
    }

    @Test
    @Disabled
    public void shouldUpdateUser() {
        String oldUsername = user.getUsername();
        String newUsername = oldUsername + "X";
        user.setUsername(newUsername);
        this.userService.saveUser(user);
        Assertions.assertEquals(newUsername, this.userService.getUserById(1).getUsername());
        this.userService.deleteUser(user);
    }

    @Test
    void testUppRank() {
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        userService.upRank(123);
        verify(userRepository).findById((Integer) any());
    }


}
