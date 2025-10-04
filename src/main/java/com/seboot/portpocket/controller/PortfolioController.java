package com.seboot.portpocket.controller;

import com.seboot.portpocket.MainApplication;
import com.seboot.portpocket.model.Active;
import com.seboot.portpocket.model.ActiveType;
import com.seboot.portpocket.model.Portfolio;
import com.seboot.portpocket.service.PortfolioService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PortfolioController {

    @FXML private TableView<Active> activeTable;
    @FXML private TableColumn<Active, String> colName;
    @FXML private TableColumn<Active, ActiveType> colType;
    @FXML private TableColumn<Active, Double> colPrice;
    @FXML private TableColumn<Active, Integer> colQuantity;
    @FXML private TableColumn<Active, Double> colValue;

    @FXML private Label lblTotal;

    @FXML private PieChart diversificationChart;
    @FXML private LineChart<String, Number> evolutionChart;

    private final PortfolioService service = new PortfolioService();

    private Stage stage;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().name()));
        colType.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().type()));
        colPrice.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().currentPrice()).asObject());
        colQuantity.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().quantity()).asObject());
        colValue.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().currentPrice() * cellData.getValue().quantity()).asObject()
        );
        activeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        activeTable.setItems(service.getPortfolio().getAllActives());
        lblTotal.setText(String.valueOf(service.getPortfolio().getAllInvested()));

        diversificationChart.setData(setDiversificationChartData(service.getPortfolio()));

        evolutionChart.getData().add(setEvolutionChartData());
    }

    @FXML
    public void openForm() {
        try {
            if (stage == null) {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-active.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 500, 400);

                ActiveController activeController = loader.getController();
                activeController.setPortfolioController(this);

                stage = new Stage();
                stage.setTitle("New Active");
                stage.setScene(scene);
                stage.setOnHidden(e -> stage = null);
                stage.show();
            } else {
                stage.toFront();
                stage.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveActive(Active active) {
        service.addActive(
            active.name(),
            active.type(),
            active.currentPrice(),
            active.quantity()
        );

        lblTotal.setText(String.valueOf(service.getPortfolio().getAllInvested()));
    }

    @FXML
    public void deleteSelected() {
        Active selected = activeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.deleteActive(selected);
            service.getPortfolio().getAllActives().remove(selected);
            lblTotal.setText(String.valueOf(service.getPortfolio().getAllInvested()));
        } else {
            System.out.println("No active selected");
        }
    }

    public ObservableList<PieChart.Data> setDiversificationChartData(Portfolio portfolio) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        portfolio.getDiversification()
            .forEach((ActiveType type, Double total) -> {
                pieData.add(new PieChart.Data(type.name(), total));
            });

        return pieData;
    }

    public XYChart.Series<String, Number> setEvolutionChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Value");

        series.getData().add(new XYChart.Data<>("Day 1", 1000));
        series.getData().add(new XYChart.Data<>("Day 2", 1200));
        series.getData().add(new XYChart.Data<>("Day 3", 1100));
        series.getData().add(new XYChart.Data<>("Day 4", 1400));

        return series;
    }

}
