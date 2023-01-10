package org.springframework.samples.nt4h.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "admins/updateUserForm";

    @Test
    void testGetUsers() throws Exception {
        // Arrange
        when(userService.getAllUsers()).thenReturn(Arrays.asList(new User()));

        // Act and Assert
        mockMvc.perform(get("/admins/usersAdminList"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users"));
    }

    @Test
    void testShowOwner() throws Exception {
        // Arrange

        // Act and Assert
        mockMvc.perform(get("/admins/details"))
            .andExpect(status().isOk())
            .andExpect(view().name("users/userDetails"));
    }

    @Test
    void testInitUpdateUserForm() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1);
        when(userService.getUserById(1)).thenReturn(user);

        // Act and Assert
        mockMvc.perform(get("/admins/{userId}/edit", 1))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_USER_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", user));
    }

    @Test
    void testProcessUpdateUserForm() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1);
        when(userService.getUserById(1)).thenReturn(user);

        // Act and Assert
        mockMvc.perform(post("/admins/{userId}/edit", 1)
                .param("name", "jane")
                .param("surname", "doe")
                .param("email", "jane.doe@example.com"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).saveUser(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        // Assert that user properties are updated correctly
        assertEquals("jane", savedUser.getName());
        assertEquals("doe", savedUser.getSurname());
        assertEquals("jane.doe@example.com", savedUser.getEmail());
    }

    @Test
    void testProcessDeleteUser() throws Exception {
        // Arrange

        // Act and Assert
        mockMvc.perform(get("/admins/{userId}/delete", 1))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).deleteUserById(1);
    }
}
