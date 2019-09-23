//Tiaz Rizqy Amandha - 1772052
package com.amandha.controller;

import com.amandha.entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class FormCategoryController implements Initializable {
    @FXML
    private TextField txtIDCat;
    @FXML
    private TextField txtNameCat;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Category> tableCat;
    @FXML
    private TableColumn<Category, String> colIDCategory;
    @FXML
    private TableColumn<Category, String> colNameCategory;
    private FormAwalController formAwalController;
    private int hitung;
    private ObservableList<Category> cat;
    Alert error = new Alert(Alert.AlertType.ERROR);


    public void setFormAwalController(FormAwalController formAwalController) {
        this.formAwalController = formAwalController;
        tableCat.setItems(formAwalController.getCategories());
    }

    @FXML
    private void SaveAction(ActionEvent actionEvent) {
        cat = formAwalController.getCategories();
        Category c = new Category();
        boolean dpt = false;
        c.setID(Integer.valueOf(txtIDCat.getText()));
        c.setName(txtNameCat.getText());

        if(txtIDCat.getText().isEmpty() && txtNameCat.getText().isEmpty())
        {
            error.setContentText("please fill ID/ Name Category");
            error.showAndWait();
        }
        else
        {
            for(Category y : cat)
            {
                if(y.getID() == c.getID()) {
                    dpt = true;
                    error.setContentText("Duplicate ID");
                    error.showAndWait();
                    break;
                }
            }
            if(!dpt)
            {
                cat.add(c);
            }
            txtIDCat.clear();
            txtNameCat.clear();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colIDCategory.setCellValueFactory(data->{
            Category c = data.getValue();
            return new SimpleStringProperty(String.valueOf(c.getID()));
        });
        colNameCategory.setCellValueFactory(data->{
            Category c = data.getValue();
            return new SimpleStringProperty(c.getName());
        });
    }
}
