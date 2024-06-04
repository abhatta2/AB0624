package org.project;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CheckoutSystem {
    private static final Map<String, Tool> tools = new HashMap<>();

    static {
        tools.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        tools.put("LADW", new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        tools.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        tools.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
    }

    private final @NonNull DateTimeFormatter dateFormatter;

    public static Tool getTool(String code) {
        Tool tool = tools.get(code);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code: " + code);
        }
        return tool;
    }

    public RentalAgreement checkoutService(String toolCode, String checkoutDateString, int rentalDays, int discount) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day should be 1 or greater.");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Discount percent should be between 0 to 100.");
        }

        Tool tool = getTool(toolCode);
        LocalDate checkoutDate = LocalDate.parse(checkoutDateString, dateFormatter);

        return new RentalAgreement(tool, rentalDays, discount, checkoutDate);
    }
}
