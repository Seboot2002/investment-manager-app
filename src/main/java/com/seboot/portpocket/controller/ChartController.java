package com.seboot.portpocket.controller;

import com.seboot.portpocket.model.Active;
import com.seboot.portpocket.model.ActiveType;
import com.seboot.portpocket.service.PortfolioService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChartController {

    private final PortfolioService portfolioService = PortfolioService.getInstance();
    ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
    ObservableList<XYChart.Series<String, Number>> lineData = FXCollections.observableArrayList();

    @FXML private PieChart diversificationChart;
    @FXML private LineChart<String, Number> evolutionChart;

    @FXML
    public void initialize() {
        updateCharts();

        portfolioService.getPortfolio().getAllActives().addListener((ListChangeListener<Active>) change -> {
            updateCharts();
        });
    }

    public void updateCharts() {
        updateDiversificationChart();
        updateEvolutionChart();
    }

    private List<String> getLastMonths() {
        YearMonth currentMonth = YearMonth.now();
        List<String> yearMonths = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            yearMonths.add(YearMonth.of(currentMonth.getYear(), currentMonth.minusMonths(i).getMonth())
                    .format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH))
                    .toUpperCase(Locale.ENGLISH));
        }

        return yearMonths;
    }

    public void updateDiversificationChart() {
        diversificationChart.getData().clear();
        pieData.clear();

        portfolioService.getPortfolio().getDiversification()
                .forEach((ActiveType type, Double total) -> {
                    pieData.add(new PieChart.Data(type.name(), total));
                });

        diversificationChart.setData(pieData);
    }

    public void updateEvolutionChart() {
        evolutionChart.getData().clear();
        lineData.clear();

        Map<String, Double> valuesPerMonth = new LinkedHashMap<>();
        for (String month : getLastMonths()) {
            valuesPerMonth.put(month, 0.0);
        }

        portfolioService.getPortfolio().getEvolutionValueData()
            .forEach((month, value) -> {
                if (valuesPerMonth.containsKey(month)) {
                    valuesPerMonth.put(month, value);
                }
            });

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Portfolio Value");
        valuesPerMonth.forEach((month, value) -> {
            series.getData().add(new XYChart.Data<>(month, value));
        });

        lineData.add(series);
        evolutionChart.setData(lineData);
    }
}
