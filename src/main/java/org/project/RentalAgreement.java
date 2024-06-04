package org.project;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Getter
public class RentalAgreement {
    private final String code;
    private final String type;
    private final String brand;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final double dailyRentalCharge;
    private final int chargeDays;
    private final BigDecimal preDiscountCharge;
    private final int discountPercent;
    private final BigDecimal discountAmount;
    @Getter
    private final BigDecimal finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        this.code = tool.getCode();
        this.type = tool.getType();
        this.brand = tool.getBrand();
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dailyRentalCharge = tool.getDailyCharge();
        this.discountPercent = discountPercent;
        this.dueDate = checkoutDate.plusDays(rentalDays);

        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);
        while (!currentDate.isAfter(dueDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            boolean isWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
            boolean isHoliday = isHoliday(currentDate);

            if ((isWeekday && tool.getWeekdayCharge() && !isHoliday) ||
                    (!isWeekday && tool.getWeekendCharge()) ||
                    (isHoliday && tool.getHolidayCharge())) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        this.chargeDays = chargeDays;

        this.preDiscountCharge = BigDecimal.valueOf(dailyRentalCharge).multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        this.discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        this.finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    private boolean isHoliday(LocalDate date) {
        LocalDate fourthOfJuly = LocalDate.of(date.getYear(), 7, 4);
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fourthOfJuly = fourthOfJuly.minusDays(1);
        } else if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fourthOfJuly = fourthOfJuly.plusDays(1);
        }

        return date.equals(fourthOfJuly) || date.equals(laborDay);
    }

    public void displayAgreement() {
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("Tool Code- " + code);
        System.out.println("Tool Type- " + type);
        System.out.println("Tool Brand- " + brand);
        System.out.println("Rental Days- " + rentalDays);
        System.out.println("Checkout Date- " + checkoutDate.format(DATE_FORMAT));
        System.out.println("Due Date- " + dueDate.format(DATE_FORMAT));
        System.out.println("Daily Rental Charge- $" + String.format("%.2f", dailyRentalCharge));
        System.out.println("Charge Days- " + chargeDays);
        System.out.println("Pre Discount Charge- $" + preDiscountCharge);
        System.out.println("Discount Percent- " + discountPercent + "%");
        System.out.println("Discount Amount- $" + discountAmount);
        System.out.println("Final Charge- $" + finalCharge);
    }
}
