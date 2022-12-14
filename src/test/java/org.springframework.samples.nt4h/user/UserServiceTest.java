
package org.springframework.samples.nt4h.user;
/*
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
public class UserServiceTest {
    @Autowired
    protected UserService userService;

    public UserServiceTest() {
    }

    @Test
    public void findIDTrue() {
        User user = this.userService.getUserById(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("alesanfe", user.getUsername());
    }

    @Test
    public void findIDFalse() {
        User user = this.userService.getUserById(2);
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals("No", user.getUsername());
    }

    @Test
    public void findNameTrue() {
        User user = this.userService.getUserByUsername("alesanfe");
        Assertions.assertNotNull(user);
        Assertions.assertEquals("DOKTOL", user.getAuthority());
    }

    @Test
    public void findNameFalse() {
        User user = this.userService.getUserByUsername("laurolmer");
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(Authority.DOKTOL, user.getAuthority());
    }

    @Test
    public void findByAuthority() {
        List<User> user = this.userService.getUserByAuthority("USER");
        Assertions.assertNotNull(user);
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(2, user.size());
    }

    @Test
    public void findByTier() {
        List<User> user = this.userService.getUserByTier(Tier.IRON);
        Assertions.assertNotNull(user);
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(7, user.size());
    }

    @Test
    public void findAll() {
        List<User> user = this.userService.getAllUsers();
        Assertions.assertNotNull(user);
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(7, user.size());
    }

    @Test
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
    public void shouldUpdateUser() {
        User user = this.userService.getUserById(1);
        String oldUsername = user.getUsername();
        String newUsername = oldUsername + "X";
        user.setUsername(newUsername);
        this.userService.saveUser(user);
        Assertions.assertEquals(newUsername, this.userService.getUserById(1).getUsername());
    }

    @Test
    public void deleteStatisticTest() {
        this.userService.deleteUserById(1);
        Assertions.assertFalse(this.userService.userExists(1));
    }


}

 */
