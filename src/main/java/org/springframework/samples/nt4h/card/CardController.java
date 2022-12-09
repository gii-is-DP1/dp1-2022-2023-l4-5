package org.springframework.samples.nt4h.card;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {

    // Constantes.
    private static final String VIEW_BOTONES_CARDS = "cards/buttonsCards";
    private static final String VIEW_WARRIOR_CARDS = "cards/warriorCards";
    private static final String VIEW_EXPLORER_CARDS="cards/explorerCards";
    private static final String VIEW_WIZARD_CARDS="cards/wizardCards";
    private static final String VIEW_ROGUE_CARDS="cards/rogueCards";
    private static final String VIEW_STAGES_CARDS="cards/stagesCards";



    @GetMapping(value = "/buttonsCards")
    public String getViewBotonesCards() {
        // Los datos para el formulario.
        System.out.println("HOLA MAMA SALGO EN YT");
        return VIEW_BOTONES_CARDS;
    }


    @GetMapping(value = "/warriorCards")
    public String getViewWarriorCards() {
        // Los datos para el formulario.
        return VIEW_WARRIOR_CARDS;
    }

    @GetMapping(value = "/explorerCards")
    public String getViewExplorerCards() {
        // Los datos para el formulario.
        return VIEW_EXPLORER_CARDS;
    }

    @GetMapping(value = "/wizardCards")
    public String getViewWizardCards() {
        // Los datos para el formulario.
        return VIEW_WIZARD_CARDS;
    }
    @GetMapping(value = "/rogueCards")
    public String getViewRogueCards() {
        // Los datos para el formulario.
        return VIEW_ROGUE_CARDS;
    }
    @GetMapping(value = "/stagesCards")
    public String getViewStagesCards() {
        // Los datos para el formulario.
        return VIEW_STAGES_CARDS;
    }

}
