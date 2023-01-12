package org.springframework.samples.nt4h.user;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
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

    @Test
    public void findIDTrue() {
        User user = this.userService.getUserById(1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("alesanfe", user.getUsername());
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
    public void findAll() {
        List<User> user = this.userService.getAllUsers();
        Assertions.assertNotNull(user);
        Assertions.assertFalse(user.isEmpty());
        Assertions.assertEquals(9, user.size());
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
        this.userService.deleteUser(user);
    }

    @Test
    public void userExistsTest() {
        User user = this.userService.getUserById(1);
        int userId = user.getId();
        Assertions.assertEquals(true, this.userService.userExists(userId));
    }

    @Test
    void testUppRank() {
        User user = this.userService.getUserById(1);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        userService.uppRank(123);
        verify(userRepository).findById((Integer) any());
    }


}
