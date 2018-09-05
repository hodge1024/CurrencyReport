package com.activatecomputers.currencyreport;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class Trade {

    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public static final Integer SCALE = 2;

    public static final List<DayOfWeek> DEFAULT_WEEKEND = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    public static final List<DayOfWeek> ALT_WEEKEND = Arrays.asList(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);

    public static final List<Currency> ALT_CURRENCIES = Arrays.asList(Currency.getInstance("SAR"), Currency.getInstance("AED"));


    private String entityName;
    private Action action;
    private BigDecimal agreedFx;
    private Currency currency;
    private LocalDate instructionDate;
    private LocalDate settlementDate;
    private BigDecimal units;
    private BigDecimal pricePerUnit;

    public Trade(String entityName, Action action, BigDecimal agreedFx, Currency currency, LocalDate instructionDate, LocalDate settlementDate, BigDecimal units, BigDecimal pricePerUnit) {

        this.entityName = entityName;
        this.action = action;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        consolidateSettlementDate();
    }

    public BigDecimal getAmountOfTradeUsd() {

        BigDecimal amount = pricePerUnit.multiply(units).multiply(agreedFx);

        return amount.setScale(SCALE, ROUNDING_MODE);
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public Action getAction() {
        return action;
    }

    public String getEntityName() {
        return entityName;
    }

    /**
     * Change settlement date to nearses working day, based on currency value
     */
    private void consolidateSettlementDate(){

        List<DayOfWeek> weekend;

        if (ALT_CURRENCIES.contains(currency)) {
            weekend = ALT_WEEKEND;
        } else {
            weekend = DEFAULT_WEEKEND;
        }

        if (weekend.contains(settlementDate.getDayOfWeek())) {
            settlementDate = settlementDate.plusDays(2 - weekend.indexOf(settlementDate.getDayOfWeek()));
        }
    }

}
