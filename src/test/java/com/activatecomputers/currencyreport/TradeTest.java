package com.activatecomputers.currencyreport;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.Assert.*;

public class TradeTest {

    @Test
    public void getAmountOfTradeUsdSingle() {
        Trade t = new Trade("Test 1", Action.BUY, new BigDecimal(1), Currency.getInstance("USD"), LocalDate.now(), LocalDate.now(), new BigDecimal(1), new BigDecimal(1));

        BigDecimal result = t.getAmountOfTradeUsd();

        assertEquals(result.toString(), "1.00");
    }

    @Test
    public void getAmountOfTradeUsdFractional() {
        Trade t = new Trade("Test 1", Action.BUY, new BigDecimal(0.5), Currency.getInstance("USD"), LocalDate.now(), LocalDate.now(), new BigDecimal(1), new BigDecimal(1));

        BigDecimal result = t.getAmountOfTradeUsd();

        assertEquals(result.toString(), "0.50");
    }

    @Test
    public void consolidationWorksForUSD() {
        Trade t = new Trade("Test 1", Action.BUY, new BigDecimal(0.5), Currency.getInstance("USD"), LocalDate.of(2018, 9, 1), LocalDate.of(2018, 9, 1), new BigDecimal(1), new BigDecimal(1));

        LocalDate result = t.getSettlementDate();

        assertEquals(DayOfWeek.MONDAY, result.getDayOfWeek());
    }

    @Test
    public void consolidationWorksForAED() {
        Trade t = new Trade("Test 1", Action.BUY, new BigDecimal(0.5), Currency.getInstance("AED"), LocalDate.of(2018, 9, 1), LocalDate.of(2018, 9, 1), new BigDecimal(1), new BigDecimal(1));

        LocalDate result = t.getSettlementDate();

        assertEquals(DayOfWeek.SUNDAY, result.getDayOfWeek());
    }

    @Test
    public void consolidationWorksForSAR() {
        Trade t = new Trade("Test 1", Action.BUY, new BigDecimal(0.5), Currency.getInstance("SAR"), LocalDate.of(2018, 9, 1), LocalDate.of(2018, 9, 1), new BigDecimal(1), new BigDecimal(1));

        LocalDate result = t.getSettlementDate();

        assertEquals(DayOfWeek.SUNDAY, result.getDayOfWeek());
    }


}