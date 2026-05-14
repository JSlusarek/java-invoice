package pl.edu.agh.mwo.invoice;

public class InvoiceIdGenerator {
    private static int nextId = 1;

    public static int generateNextId() {
        return nextId++;
    }
}