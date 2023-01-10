package org.springframework.samples.nt4h.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditoryUserControllerTest {

    @Mock
    private AuditoryUserRepository auditoryUserRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuditoryUserService auditoryUserService;

    @InjectMocks
    private AuditoryUserController auditoryUserController;

    @Test
    public void testModAuditoryUser() {
        // Given
        int userId = 1;
        AuditoryUser expectedAuditoryUser = new AuditoryUser();
        expectedAuditoryUser.setNewCreator("username");
        expectedAuditoryUser.setModDate(LocalDateTime.now());
        expectedAuditoryUser.setUser(userService.getLoggedUser());
        expectedAuditoryUser.setUserMod(userService.getUserById(userId));

        List<AuditoryUser> auditoryUsers = new ArrayList<>();
        auditoryUsers.add(expectedAuditoryUser);

        when(userService.getLoggedUser().getUsername()).thenReturn("username");
        when(userService.getUserById(anyInt())).thenReturn(expectedAuditoryUser.getUserMod());
        when(auditoryUserRepository.save(expectedAuditoryUser)).thenReturn(expectedAuditoryUser);

        // When
        AuditoryUser actualAuditoryUser = auditoryUserController.modAuditoryUser(userId);

        // Then
        assertThat(actualAuditoryUser).isEqualTo(expectedAuditoryUser);
    }
}




