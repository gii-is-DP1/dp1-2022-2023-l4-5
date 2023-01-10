package org.springframework.samples.nt4h.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "users/createUserForm";
    private static final String VIEW_USER_LIST = "users/usersGameList";
    private static final String VIEW_USER_DETAILS = "users/userDetails";
    private static final String PAGE_WELCOME = "redirect:/welcome";
    private static final String PAGE_USER_DETAILS = "redirect:/users/details";

    @BeforeEach
    void setUp() {
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    void processCreationFormShouldSaveUser() {
        User user = new User();
        userController.processCreationForm(user, bindingResult);
        verify(userService).saveUser(user);
    }

    @Test
    void processCreationFormShouldReturnWelcomeView() {
        User user = new User();
        assertEquals(PAGE_WELCOME, userController.processCreationForm(user, bindingResult));
    }

    @Test
    void processCreationFormShouldReturnCreateUserView() {
        when(bindingResult.hasErrors()).thenReturn(true);
        User user = new User();
        assertEquals(VIEW_USER_CREATE_OR_UPDATE_FORM, userController.processCreationForm(user, bindingResult));
    }

    @Test
    void processCreationFormShouldThrowException() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.saveUser(any())).thenThrow(new RuntimeException());
        User user = new User();
        assertThrows(RuntimeException.class, () -> userController.processCreationForm(user, bindingResult));
    }

    @Test
    void initCreationFormShouldReturnCreateUserView() {
        assertEquals(VIEW_USER_CREATE_OR_UPDATE_FORM, userController.initCreationForm());
    }

    @Test
    void getUsersShouldReturnUsersListView() {
        assertEquals(VIEW_USER_LIST, userController.getUsers(0, new ModelMap()));
    }

    @Test
    void showOwnerShouldReturnUserDetailsView() {
        assertEquals(VIEW_USER_DETAILS, userController.showOwner());
    }
}
