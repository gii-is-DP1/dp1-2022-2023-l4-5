package org.springframework.samples.nt4h.achievement;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {AchievementController.class})
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AchievementController.class)
class AchievementControllerTest {

    @Autowired
    private AchievementController achievementController;

    private final String ACHIEVEMENTS_LIST_VIEW = "achievements/achievementsList";
    private final String VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM = "achievements/createOrUpdateAchievementsForm";

    @MockBean
    private AchievementService achievementService;

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

    //No hay redirecciones
    @Test
    void shouldSaveAchievement() throws Exception {
        mockMvc.perform(post("/achievements/new")
                .param("name", "Test Achievement")
                .param("description", "Test Description")
                .param("threshold","1"))
            .andExpect(status().isOk())
            .andExpect(redirectedUrl("/achievements"))
            .andExpect(flash().attributeExists("achievement"));
        verify(achievementService, times(1)).saveAchievement(any(Achievement.class));
    }

    //No hay binding o exceptions
    @Test
    void shouldNotSaveAchievementWithErrors() throws Exception {
        mockMvc.perform(post("/achievements/new")
                .param("name", "")
                .param("description", "Test")
                .param("threshold","1"))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"))
            .andExpect(model().hasErrors());
        verify(achievementService, times(0)).saveAchievement(any(Achievement.class));
    }

    @Test
    void shouldEditAchievement() throws Exception {
        when(achievementService.getAchievementById(1)).thenReturn(achievement);
        mockMvc.perform(get("/achievements/{achievementId}/edit", 1))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM))
            .andExpect(model().attributeExists("achievement"))
            .andExpect(model().attribute("achievement", achievement));
    }
    //Falta redirection
    @Test
    void shouldUpdateAchievement() throws Exception {
        int id = 1;
        when(achievementService.getAchievementById(1)).thenReturn(achievement);
        mockMvc.perform(post("/achievements/{achievementId}/edit", 1)
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
    //Falta redireccion
    @Test
    void shouldDeleteAchievement() throws Exception {
        int id = 1;
        mockMvc.perform(get("/achievements/{achievementId}/delete", id))
            .andExpect(status().isOk())
            .andExpect(redirectedUrl("/achievements"))
            .andExpect(flash().attributeExists("message"));
        verify(achievementService, times(1)).deleteAchievementById(id);
    }

    @Test
    void testCreateAchievement() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/achievements/new");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testCreateAchievement2() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/achievements/new");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testDeleteAchievement() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        doNothing().when(achievementService).deleteAchievementById(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/achievements/{achievementId}/delete",
            123);
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievements", "message"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/achievementsList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/achievementsList"));
    }

    @Test
    void testDeleteAchievement2() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        doNothing().when(achievementService).deleteAchievementById(anyInt());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/achievements/{achievementId}/delete", 123);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievements", "message"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/achievementsList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/achievementsList"));
    }

    @Test
    void testEditAchievement() throws Exception {
        Achievement achievement = new Achievement();
        achievement.setDescription("The characteristics of someone or something");
        achievement.setId(1);
        achievement.setImage("Image");
        achievement.setName("Name");
        achievement.setThreshold(1);
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        when(achievementService.getAchievementById(anyInt())).thenReturn(achievement);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/achievements/{achievementId}/edit",
            123);
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testSaveAchievement() throws Exception {
        Achievement achievement = new Achievement();
        achievement.setDescription("The characteristics of someone or something");
        achievement.setId(1);
        achievement.setImage("Image");
        achievement.setName("Name");
        achievement.setThreshold(1);
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        when(achievementService.getAchievementById(anyInt())).thenReturn(achievement);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/achievements/{achievementId}/edit",
            123);
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testSaveAchievement2() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/achievements/new");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testSaveAchievement3() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/achievements/new");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(postResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievement", "achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/createOrUpdateAchievementsForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/createOrUpdateAchievementsForm"));
    }

    @Test
    void testShowAllAchievements() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/achievements");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/achievementsList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/achievementsList"));
    }

    @Test
    void testShowAllAchievements2() throws Exception {
        when(achievementService.getAllAchievements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/achievements");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(achievementController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("achievements"))
            .andExpect(MockMvcResultMatchers.view().name("achievements/achievementsList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("achievements/achievementsList"));
    }
}
