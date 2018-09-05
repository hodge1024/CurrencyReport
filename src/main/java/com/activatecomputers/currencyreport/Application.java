package com.activatecomputers.currencyreport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private List<Trade> tradeList = new ArrayList<Trade>();
    private ReportGenerator gen = new ReportGenerator();

    public static final void main(String[] args) {
        if (args.length > 0 && !args[0].equalsIgnoreCase("injected")) {
            Application app = new Application();

            app.setTradeList(app.getDefaultList());
            app.produceReport();
        }
    }

    public void setTradeList(List<Trade> trades) {
        tradeList = trades;
    }

    public void produceReport() {
        final List<String> result = new ArrayList<>();
        List<LocalDate> settlementDates = tradeList.stream().map(n -> n.getSettlementDate()).distinct().collect(Collectors.toList());

        settlementDates.forEach(d -> {
            result.addAll(produceDailyReport(d));
            result.add("---");
        });
        result.add("-----------------------------------------------");
        result.addAll(gen.generateRank(tradeList, Action.BUY));

        result.add("-----------------------------------------------");
        result.addAll(gen.generateRank(tradeList, Action.SELL));

        result.forEach(line -> System.out.println(line));

    }

    private List<String> produceDailyReport(LocalDate settlementDay) {
        List<String> result;
        result = gen.generateSettledReport(tradeList, Action.BUY, settlementDay);
        result.addAll(gen.generateSettledReport(tradeList, Action.SELL, settlementDay));
        return result;
    }

    private List<Trade> getDefaultList() {
        List<Trade> list = new ArrayList<>();

        list.add(new Trade("Test 1", Action.BUY, new BigDecimal(0.5), Currency.getInstance("AED"), LocalDate.of(2018, 9, 1), LocalDate.of(2018, 9, 1), new BigDecimal(1), new BigDecimal(1)));
        list.add(new Trade("Test 2", Action.SELL, new BigDecimal(0.5), Currency.getInstance("AED"), LocalDate.of(2018, 9, 1), LocalDate.of(2018, 9, 1), new BigDecimal(1), new BigDecimal(1)));

        list.add(new Trade("Test 3", Action.BUY, new BigDecimal(0.25), Currency.getInstance("GBP"), LocalDate.of(2018, 9, 15), LocalDate.of(2018, 9, 15), new BigDecimal("0.25"), new BigDecimal(1)));
        list.add(new Trade("Test 4", Action.SELL, new BigDecimal(0.25), Currency.getInstance("GBP"), LocalDate.of(2018, 9, 15), LocalDate.of(2018, 9, 15), new BigDecimal("0.25"), new BigDecimal(1)));


        return list;
    }

}
