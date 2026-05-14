package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if (name == null) {
            throw new IllegalArgumentException("Nie można wrzucić nulla");
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException("Trzeba wpisac nazwe");
        } else if (price == null) {
            throw new IllegalArgumentException("Cena nie może być null");
        } else if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cena nie może być ujemna");
        }
        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getExcise() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getPriceWithTax() {
        BigDecimal tax = this.price.multiply(this.taxPercent);
        return this.price.add(tax).add(getExcise());
    }
}