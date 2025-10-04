package com.seboot.portpocket.controller;

import com.seboot.portpocket.model.Active;
import com.seboot.portpocket.model.ActiveType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActiveController {

    @FXML private TextField txtName;
    @FXML private ComboBox<ActiveType> cmbType;
    @FXML private TextField txtPrice;
    @FXML private TextField txtQuantity;

    private PortfolioController portfolioController;

    public void setPortfolioController(PortfolioController portfolioController) {
        this.portfolioController = portfolioController;
    }

    @FXML
    public void initialize() {
        cmbType.getItems().setAll(ActiveType.values());

        if(!cmbType.getItems().isEmpty())
            cmbType.getSelectionModel().selectFirst();
    }

    @FXML
    private void saveActive() {
        try {
            String name = txtName.getText();
            ActiveType type = cmbType.getValue();
            double price = Double.parseDouble(txtPrice.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            Active active = new Active(
                name, type, price, price, quantity
            );

            System.out.println(active);

            portfolioController.saveActive(active);

            closeForm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeForm() {
        Stage stage = (Stage) txtName.getScene().getWindow();
        stage.close();
    }
}
