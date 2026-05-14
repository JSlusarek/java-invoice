package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.*;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithTwoDifferentProducts() {
        Product onions = new TaxFreeProduct("Warzywa", new BigDecimal("10"));
        Product apples = new TaxFreeProduct("Owoce", new BigDecimal("10"));
        invoice.addProduct(onions);
        invoice.addProduct(apples);
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceSubtotalWithManySameProducts() {
        Product onions = new TaxFreeProduct("Warzywa", BigDecimal.valueOf(10));
        invoice.addProduct(onions, 100);
        Assert.assertThat(new BigDecimal("1000"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getTotal(), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getTotal()));
    }
    // --- BottleOfWine ---

    @Test
    public void testInvoiceWithBottleOfWineTotalIncludesExcise() {
        // 100 + 30% VAT + 5.56 akcyza = 135.56
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("135.56"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceWithBottleOfWineSubtotalExcludesExcise() {
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceWithBottleOfWineTaxIncludesExcise() {
        // 30 VAT + 5.56 akcyza = 35.56
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("35.56"), Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testInvoiceWithMultipleBottlesOfWine() {
        // 2 * 135.56 = 271.12
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")), 2);
        Assert.assertThat(new BigDecimal("271.12"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

// --- FuelCanister ---

    @Test
    public void testInvoiceWithFuelCanisterTotalIncludesExcise() {
        // 100 + 30% VAT + 5.56 akcyza = 135.56
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("135.56"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testInvoiceWithFuelCanisterSubtotalExcludesExcise() {
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(invoice.getSubtotal()));
    }

    @Test
    public void testInvoiceWithFuelCanisterTaxIncludesExcise() {
        // 30 VAT + 5.56 akcyza = 35.56
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("35.56"), Matchers.comparesEqualTo(invoice.getTax()));
    }

    @Test
    public void testInvoiceWithMultipleFuelCanisters() {
        // 3 * 135.56 = 406.68
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")), 3);
        Assert.assertThat(new BigDecimal("406.68"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

// --- Mieszane ---

    @Test
    public void testInvoiceWithExciseAndNonExciseProducts() {
        // BottleOfWine: 135.56, DairyProduct: 108.00, total: 243.56
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("100")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        Assert.assertThat(new BigDecimal("243.56"), Matchers.comparesEqualTo(invoice.getTotal()));
    }

    @Test
    public void testSubtotalPlusTaxEqualsTotal() {
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("100")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        Assert.assertThat(
                invoice.getSubtotal().add(invoice.getTax()),
                Matchers.comparesEqualTo(invoice.getTotal())
        );
    }

// --- Numer faktury ---

    @Test
    public void testInvoiceNumberIsNotNull() {
        Assert.assertNotNull(invoice.getNumber());
    }

    @Test
    public void testTwoInvoicesHaveDifferentNumbers() {
        Invoice second = new Invoice();
        Assert.assertNotEquals(invoice.getNumber(), second.getNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullProduct() {
        invoice.addProduct(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantityBottleOfWine() {
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantityBottleOfWine() {
        invoice.addProduct(new BottleOfWine("Merlot", new BigDecimal("100")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantityFuelCanister() {
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantityFuelCanister() {
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("100")), -1);
    }

}
