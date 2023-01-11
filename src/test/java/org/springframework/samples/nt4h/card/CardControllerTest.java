package org.springframework.samples.nt4h.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CardController.class})
@ExtendWith(SpringExtension.class)
class CardControllerTest {
    @Autowired
    private CardController cardController;

    @Test
    void testGetViewBotonesCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/buttonsCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/buttonsCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/buttonsCards"));
    }

    @Test
    void testGetViewBotonesCards2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/buttonsCards", "Uri Vars");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/buttonsCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/buttonsCards"));
    }

    @Test
    void testGetViewExplorerCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/explorerCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/explorerCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/explorerCards"));
    }

    @Test
    void testGetViewExplorerCards2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/cards/explorerCards");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/explorerCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/explorerCards"));
    }

    @Test
    void testGetViewRogueCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/rogueCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/rogueCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/rogueCards"));
    }

    @Test
    void testGetViewRogueCards2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/cards/rogueCards");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/rogueCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/rogueCards"));
    }

    @Test
    void testGetViewStagesCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/stagesCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/stagesCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/stagesCards"));
    }

    @Test
    void testGetViewStagesCards2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/cards/stagesCards");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/stagesCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/stagesCards"));
    }

    @Test
    void testGetViewWarriorCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/warriorCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/warriorCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/warriorCards"));
    }

    @Test
    void testGetViewWarriorCards2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/cards/warriorCards");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/warriorCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/warriorCards"));
    }

    @Test
    void testGetViewWizardCards() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cards/wizardCards");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/wizardCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/wizardCards"));
    }

    @Test
    void testGetViewWizardCards2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/cards/wizardCards");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(cardController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("cards/wizardCards"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("cards/wizardCards"));
    }
}
