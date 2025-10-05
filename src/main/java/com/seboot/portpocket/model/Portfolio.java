package com.seboot.portpocket.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Portfolio {

    private ObservableList<Active> actives = FXCollections.observableArrayList();

    public void addActive(Active active) {
        actives.add(active);
    }

    public ObservableList<Active> getAllActives() {
        return actives;
    }

    public double getAllInvested() {
        return actives.stream()
                .mapToDouble(
                    Active::getTotalValue
                )
                .sum();
    }

    public Map<ActiveType, Double> getDiversification() {
        double total = getAllInvested();
        return actives.stream()
            .collect(
                Collectors.groupingBy(
                    Active::type,
                    Collectors.summarizingDouble(Active::getTotalValue)
                )
            )
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                Map.Entry::getKey,
                e -> (e.getValue().getSum() / total) * 100.0
            ));
    }

    public Map<String, Double> getEvolutionValueData() {
        return actives.stream()
            .collect(Collectors.groupingBy(
                active -> active.getPurchaseDate().getMonth().name(),
                TreeMap::new,
                Collectors.summingDouble(Active::getTotalValue)
            ));
    }

}
