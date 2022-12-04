package org.springframework.samples.nt4h.card;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cards")
public class CardController {

    // Constantes.
    private static final String VIEW_ALL_CARDS = "cards/Allcard";


    //Todas las cartas
    @GetMapping(value = "/Allcard")
    public String getViewAllCards() {
        // Los datos para el formulario.
        return VIEW_ALL_CARDS;
    }
}
