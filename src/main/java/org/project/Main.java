package org.project;

import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        CheckoutSystem system = new CheckoutSystem(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        RentalAgreement agreement = system.checkoutService("LADW", "07/02/2020",3, 10);
        agreement.displayAgreement();
    }
}