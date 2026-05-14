package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products;
    private Integer number;

    public Invoice() {
        this.number = InIDGenerator.generateNextId();
        this.products = new LinkedHashMap<>();
    }

    public Integer getNumber() {
        return number;
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null)
            throw new IllegalArgumentException("Produkt nie może być nullem");
        if (quantity <= 0)
            throw new IllegalArgumentException("Ilość musi być dodatnia");
        products.merge(product, quantity, Integer::sum);
    }

    public BigDecimal getSubtotal() {
        return products.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTax() {
        return products.entrySet().stream()
                .map(e -> e.getKey().getPrice()
                        .multiply(e.getKey().getTaxPercent())
                        .multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(e -> e.getKey().getPriceWithTax().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void printInvoice() {
        System.out.println("Invoice Number: " + getNumber());

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            BigDecimal lineTotal = p.getPriceWithTax().multiply(BigDecimal.valueOf(qty));

            System.out.println("Quantity: " + qty
                    + " | " + p.getName()
                    + " | Price: " + p.getPrice()
                    + " | Tax: " + p.getTaxPercent().multiply(new BigDecimal("100")) + "%"
                    + " | Price with Tax: " + p.getPriceWithTax()
                    + " | Line Total: " + lineTotal);
        }

        System.out.println("Total: " + getTotal());
    }
}