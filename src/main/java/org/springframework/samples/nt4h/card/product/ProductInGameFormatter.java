package org.springframework.samples.nt4h.card.product;

import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class ProductInGameFormatter implements Formatter<ProductInGame> {

    private final ProductService productService;

    @Override
    public ProductInGame parse(String s, Locale locale) {
        return productService.getProductInGameById(Integer.parseInt(s.split("-")[0].trim()));
    }

    @Override
    public String print(ProductInGame productInGame, Locale locale) {
        return productInGame.getName() + "-" + productInGame.getId();
    }
}

