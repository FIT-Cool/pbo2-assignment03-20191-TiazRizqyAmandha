package com.amandha.controller;

import com.amandha.Main;
import com.amandha.entity.Item;
import com.amandha.entity.Category;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public  class FormAwalController implements Initializable {

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Category> ComboCat;
    @FXML
    private DatePicker DateExp;
    @FXML
    private TableView<Item> tableMain;
    @FXML
    private TableColumn<Item, String> colID;
    @FXML
    private TableColumn<Item, String> colName;
    @FXML
    private TableColumn<Item, String> colCat;
    @FXML
    private TableColumn<Item, String> colExp;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;
    @FXML
    private ObservableList<Item> items;
    @FXML
    private ObservableList<Category> categories;
    @FXML
    private FormAwalController formawalController;
    private Item i;
    Alert error = new Alert(Alert.AlertType.ERROR);

    private void setAwalController(FormAwalController formAwalController)
    {
        this.formawalController = formAwalController;
        tableMain.setItems(formAwalController.getItems());
    }

    public ObservableList<Category> getCategories()
    {
        if(categories == null)
        {
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }

    private ObservableList<Item> getItems() {
        if(items == null)
        {
            items = FXCollections.observableArrayList();
        }
        return items;
    }


    @FXML
    private void SaveAction(ActionEvent actionEvent) {
        Item x = new Item();
        Boolean dpt = false;
        if(!txtID.getText().isEmpty() && !txtName.getText().isEmpty() && ComboCat.getValue() != null && DateExp.getValue() != null)
        {
            x.setName(txtName.getText().trim());
            x.setID(Integer.valueOf(txtID.getText()));
            x.setCategory(ComboCat.getValue());
            x.setDateExp(String.valueOf(DateExp.getValue()));

            for (Item i : items)
            {
                if(i.getName().equals(x.getName())) {
                    dpt = true;
                    error.setContentText("Duplicate Name");
                    error.showAndWait();
                    break;
                }
            }

            if(!dpt)
            {
                items.add(x);
            }
            txtID.clear();
            txtName.clear();
            ComboCat.setValue(null);
            DateExp.setValue(null);
        }
        else
        {
            error.setContentText("Please fill ID/ Name/ Category/ ExpiredDate");
            error.showAndWait();
        }
    }

    @FXML
    private void ResetAction(ActionEvent actionEvent) {
        txtName.clear();
        txtID.clear();
        ComboCat.getSelectionModel().select(-1);
        DateExp.getEditor().clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
    }

    @FXML
    private void UpdateAction(ActionEvent actionEvent)
    {
        Item i = tableMain.getSelectionModel().getSelectedItem();
        int hitung = 0;
        String tempName = i.getName();
        i.setID(Integer.valueOf(txtID.getText()));
        for(Item x : items)
        {
            if (x.getName().equals(txtID.getText())) {
                hitung += 1;
            }
        }
        if(hitung > 1)
        {
                error.setContentText("Duplicate ID");
                error.showAndWait();
                i.setName(tempName);
        }
        else {
                i.setName(txtName.getText());
                i.setCategory(ComboCat.getValue());
                i.setDateExp(String.valueOf(DateExp.getValue()));
        }
        tableMain.refresh();
    }


    @FXML
    private void ShowCategoryAction(ActionEvent actionEvent) {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/FormCategory.fxml"));
            BorderPane root = loader.load();
            FormCategoryController controller = loader.getController();
            controller.setFormAwalController(this);

            Stage AwalStage = new Stage();
            AwalStage.initModality(Modality.APPLICATION_MODAL);
            AwalStage.setTitle("Category Form");
            AwalStage.setScene(new Scene(root));
            AwalStage.show();


        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void CloseAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        ComboCat.setItems(categories);
        tableMain.setItems(items);
        colID.setCellValueFactory(data->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getID()));
        });
        colName.setCellValueFactory(data->{
            Item i = data.getValue();
            return new SimpleStringProperty(i.getName());
        });
        colExp.setCellValueFactory(data->{
            Item i = data.getValue();
            return new SimpleStringProperty(String.valueOf(i.getDateExp()));
        });
        colCat.setCellValueFactory(data->{
            Item i = data.getValue();
            return new SimpleStringProperty (i.getCategory().getName());
        });
    }

    @FXML
    private void tableClickedMain(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        Item i = tableMain.getSelectionModel().getSelectedItem();
        txtID.setText(String.valueOf(i.getID()));
        txtName.setText(i.getName());
        ComboCat.setValue(i.getCategory());
        DateExp.setValue(LocalDate.parse(i.getDateExp()));

    }
}
