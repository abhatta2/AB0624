package org.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalAgreementTest {
    private CheckoutSystem checkoutSystem;

    @BeforeEach
    public void setUp() {
        checkoutSystem = new CheckoutSystem(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @Test
    public void testCase1() {
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutSystem.checkoutService("JAKR", "09/03/2015",5, 101);
        });
    }

    @Test
    public void testCase2() {
        RentalAgreement agreement = checkoutSystem.checkoutService("LADW", "07/02/2020", 3, 10);
        assertEquals("$3.58", String.format("$%.2f", agreement.getFinalCharge()));
    }

    @Test
    public void testCase3() {
        RentalAgreement agreement = checkoutSystem.checkoutService("CHNS", "07/02/2015", 5, 25);
        assertEquals("$3.35", String.format("$%.2f", agreement.getFinalCharge()));
    }

    @Test
    public void testCase4() {
        RentalAgreement agreement = checkoutSystem.checkoutService("JAKD", "09/03/2015", 6, 0);
        assertEquals("$8.97", String.format("$%.2f", agreement.getFinalCharge()));
    }

    @Test
    public void testCase5() {
        RentalAgreement agreement = checkoutSystem.checkoutService("JAKR", "07/02/2015", 9, 0);
        assertEquals("$14.95", String.format("$%.2f", agreement.getFinalCharge()));
    }

    @Test
    public void testCase6() {
        RentalAgreement agreement = checkoutSystem.checkoutService("JAKR", "07/02/2020", 4, 50);
        assertEquals("$1.49", String.format("$%.2f", agreement.getFinalCharge()));
    }
}