package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends Product {
    private static final BigDecimal EXCISE = new BigDecimal("5.56");

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.30"));
    }

    @Override
    public BigDecimal getExcise() {
        return EXCISE;
    }
}