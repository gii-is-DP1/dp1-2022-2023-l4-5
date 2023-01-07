package org.springframework.samples.nt4h.card.product.exceptions;

public class NotInSaleException extends RuntimeException {
    public NotInSaleException() {
        super("The product is not in sale.");
    }
}
