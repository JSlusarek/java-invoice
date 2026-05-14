package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.Product;

public class ProductTest {
    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
    @Test
    public void testBottleOfWineTaxPercent() {
        Product product = new BottleOfWine("Merlot", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("0.30"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testBottleOfWinePriceWithTax() {
        Product product = new BottleOfWine("Merlot", new BigDecimal("100.0"));
        // 100 + 30% + 5.56 = 135.56
        Assert.assertThat(new BigDecimal("135.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testBottleOfWineExcise() {
        Product product = new BottleOfWine("Merlot", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getExcise()));
    }

    @Test
    public void testFuelCanisterTaxPercent() {
        Product product = new FuelCanister("Benzyna", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("0.30"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testFuelCanisterPriceWithTax() {
        Product product = new FuelCanister("Benzyna", new BigDecimal("100.0"));
        // 100 + 30% + 5.56 = 135.56
        Assert.assertThat(new BigDecimal("135.56"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testFuelCanisterExcise() {
        Product product = new FuelCanister("Benzyna", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getExcise()));
    }

    @Test
    public void testTaxFreeProductPriceWithTax() {
        Product product = new TaxFreeProduct("Woda", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testTaxFreeProductExcise() {
        Product product = new TaxFreeProduct("Woda", new BigDecimal("100.0"));
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(product.getExcise()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBottleOfWineWithNullName() {
        new BottleOfWine(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBottleOfWineWithEmptyName() {
        new BottleOfWine("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBottleOfWineWithNullPrice() {
        new BottleOfWine("Merlot", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBottleOfWineWithNegativePrice() {
        new BottleOfWine("Merlot", new BigDecimal("-1.00"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFuelCanisterWithNullName() {
        new FuelCanister(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFuelCanisterWithEmptyName() {
        new FuelCanister("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFuelCanisterWithNullPrice() {
        new FuelCanister("Benzyna", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFuelCanisterWithNegativePrice() {
        new FuelCanister("Benzyna", new BigDecimal("-1.00"));
    }
}
