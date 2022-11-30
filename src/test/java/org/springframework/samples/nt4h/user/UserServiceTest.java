package org.springframework.samples.nt4h.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {
    @Autowired
    protected UserService userService;

    @Test
    public void findIDTrue(){
        User user = userService.getUserById(1);
        assertNotNull(user);
        assertEquals("alesanfe", user.getUsername());
    }
    @Test
    public void findIDFalse(){
        User user = userService.getUserById(2);
        assertNotNull(user);
        assertNotEquals("No", user.getUsername());
    }
    @Test
    public void findNameTrue(){
        User user = userService.getUserByUsername("alesanfe");
        assertNotNull(user);
        assertEquals("DOKTOL",user.getAuthority());
    }
    @Test
    public void findNameFalse(){
        User user = userService.getUserByUsername("laurolmer");
        assertNotNull(user);
        assertNotEquals(Authority.DOKTOL,user.getAuthority());
    }
    @Test
    public void findByAuthority(){
        List<User> user = userService.getUserByAuthority("USER");
        assertNotNull(user);
        assertFalse(user.isEmpty());
        assertEquals(2,user.size());
    }
    @Test
    public void findByTier(){
        List<User> user = userService.getUserByTier(Tier.IRON);
        assertNotNull(user);
        assertFalse(user.isEmpty());
        assertEquals(7,user.size());
    }
    @Test
    public void findAll(){
        List<User> user = userService.getAllUsers();
        assertNotNull(user);
        assertFalse(user.isEmpty());
        assertEquals(7,user.size());
    }
    @Test
    public void shouldInsertUser(){
        Integer n = userService.getAllUsers().size();
        User user = new User();
        user.setUsername("Prueba");
        user.setPassword("Prueba");
        user.setDescription("Prueba");
        user.setBirthDate(LocalDate.now());
        userService.saveUser(user);
        assertEquals(user.getUsername(),userService.getUserById(n+1).getUsername());
    }
    @Test
    public void shouldUpdateUser(){
        User user = userService.getUserById(1);
        String oldUsername = user.getUsername();
        String newUsername = oldUsername+"X";
        user.setUsername(newUsername);
        userService.saveUser(user);
        assertEquals(newUsername,userService.getUserById(1).getUsername());
    }
    @Test
    public void deleteStatisticTest(){
        userService.deleteUserById(1);
        assertFalse(userService.userExists(1));
    }
}
