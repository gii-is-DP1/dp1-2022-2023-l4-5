package org.springframework.samples.nt4h.achievement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AchievementControllerTest {

    private final String ACHIEVEMENTS_LIST_VIEW = "achievements/achievementsList";
    private final String VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM = "achievements/createOrUpdateAchievementsForm";

    @Mock
    private AchievementService achievementService;
    @Autowired
    private MockMvc mockMvc;

    private AchievementController controller;
    private Achievement achievement;

    @BeforeEach
    void setUp() {
        achievement = Achievement.builder().description("Thats why he is the goat THE GOAT").threshold(100).build();
        achievement.setName("Goat");
        controller = new AchievementController(achievementService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldShowAllAchievements() throws Exception {
        mockMvc.perform(get("/achievements"))
            .andExpect(status().isOk())
            .andExpect(view().name(ACHIEVEMENTS_LIST_VIEW));
    }

    @Test
    void shouldShowAchievementCreateForm() throws Exception {
        mockMvc.perform(get("/achievements/new"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"));
    }

    @Test
    void shouldSaveAchievement() throws Exception {
        mockMvc.perform(post("/achievements/new")
                .param("name", "Test Achievement")
                .param("description", "Test Description"))
            .andExpect(status().isOk())
            .andExpect(redirectedUrl("/achievements"))
            .andExpect(flash().attributeExists("achievement"));
        verify(achievementService, times(1)).saveAchievement(any(Achievement.class));
    }

    @Test
    void shouldNotSaveAchievementWithErrors() throws Exception {
        mockMvc.perform(post("/achievements/new")
                .param("name", "")
                .param("description", ""))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"))
            .andExpect(model().hasErrors());
        verify(achievementService, times(0)).saveAchievement(any(Achievement.class));
    }

    @Test
    void shouldEditAchievement() throws Exception {
        when(achievementService.getAchievementById(1)).thenReturn(achievement);

        mockMvc.perform(get("/achievements/{achievementId}/edit", achievement.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"))
            .andExpect(model().attribute("achievement", achievement));
    }

    @Test
    void shouldUpdateAchievement() throws Exception {
        int id = 1;
        when(achievementService.getAchievementById(1)).thenReturn(achievement);

        mockMvc.perform(post("/achievements/{achievementId}/edit", id)
                .param("name", "Updated Achievement")
                .param("description", "Updated Description"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/achievements"))
            .andExpect(flash().attributeExists("message"));
        verify(achievementService, times(1)).saveAchievement(achievement);
    }

    @Test
    void shouldNotUpdateAchievementWithErrors() throws Exception {
        int id = 1;
        when(achievementService.getAchievementById(id)).thenReturn(achievement);
        mockMvc.perform(post("/achievements/{achievementId}/edit", id)
                .param("name", "")
                .param("description", ""))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"))
            .andExpect(model().hasErrors());

        verify(achievementService, times(0)).saveAchievement(achievement);
    }

    @Test
    void shouldDeleteAchievement() throws Exception {
        int id = 1;
        mockMvc.perform(get("/achievements/{achievementId}/delete", id))
            .andExpect(status().isOk())
            .andExpect(redirectedUrl("/achievements"))
            .andExpect(flash().attributeExists("message"));

        verify(achievementService, times(1)).deleteAchievementById(id);
    }

}
