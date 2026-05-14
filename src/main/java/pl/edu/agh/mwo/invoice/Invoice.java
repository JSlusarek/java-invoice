package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products;
    private Integer number;

    public Invoice() {
        this.products = new ArrayList<Product>();
        this.number = InIDGenerator.generateNextId();
    }

    public Integer getNumber() {
        return number;
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produkt nie może być nullem !!!!");
        }
        this.products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Produkt nie może być nullem !!!!");
        }
        if (quantity == 0 || quantity < 0) {
            throw new IllegalArgumentException("Ilość nie może być równa 0 albo ujemna !!!!");
        }
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public BigDecimal getSubtotal() {
        if (this.products == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Product i : this.products) {
            total = total.add(i.getPrice());
        }
        System.out.println(total);
        return total;
    }

    public BigDecimal getTax() {
        if (products == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Product i : this.products) {
            BigDecimal taxValue = i.getPrice().multiply(i.getTaxPercent());
            total = total.add(taxValue);
        }
        System.out.println(total);
        return total;
    }

    public BigDecimal getTotal() {
        if (products == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Product i : this.products) {
            total = total.add(i.getPriceWithTax());
        }
        System.out.println(total);
        return total;
    }
    public void printInvoice() {
        System.out.println("Invoice Number: " + getNumber());

        Map<Product, Long> counted = products.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));

        for (Map.Entry<Product, Long> entry : counted.entrySet()) {
            Product p = entry.getKey();
            long qty = entry.getValue();
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