package com.activatecomputers.currencyreport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportGenerator {

    public List<String> generateSettledReport(List<Trade> trades, Action action, LocalDate reportDate) {
        List<String> result = new ArrayList<>();
        BigDecimal summed = new BigDecimal(0);

        String direction = action == Action.SELL ? "Incoming" : "Outgoing";

        List<BigDecimal> tradeAmounts = trades.stream()
                                                .filter(t -> t.getAction() == action)
                                                .filter(t -> t.getSettlementDate().equals(reportDate))
                                                .map(t -> t.getAmountOfTradeUsd()).collect(Collectors.toList());

        for(BigDecimal a : tradeAmounts) {
            summed = summed.add(a);
        }

        result.add(direction + " settled for " + reportDate.format(DateTimeFormatter.BASIC_ISO_DATE) + " " + summed + " USD");

        return result;
    }

    public List<String> generateRank(List<Trade> trades, Action action) {
        List<String> result = new ArrayList<>();

        String direction = action == Action.SELL ? "Incoming" : "Outgoing";

        result.add(direction + " trades rank");

        result.addAll(trades.stream()
                                .filter(t -> t.getAction() == action)
                                .sorted(Comparator.comparing(Trade::getAmountOfTradeUsd).reversed())
                                .map(t -> t.getEntityName()).collect(Collectors.toList()));

        return result;
    }

}
