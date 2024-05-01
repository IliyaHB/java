package profiler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import profiler.models.bl.BusinessLogic;
import profiler.models.entities.Product;
import profiler.models.entities.enums.ProductType;
import profiler.models.tools.Validator;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, priceTxt, findByTxt;

    @FXML
    private ComboBox<String> typeCmb;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private TableView<Product> productTbl;

    @FXML
    private TableColumn <Product, String> idCol, nameCol, typeCol, priceCol;

    public static String save(String name, String productType, double price) {
        try {
            Product product = Product.builder()
                    .name(Validator.validateName(name, "Invalid Name"))
                    .productType(ProductType.valueOf(productType))
                    .price(Validator.validatePrice(price, "Invalid"))
                    .build();

            BusinessLogic.save(product);
            return "Product saved!!\n" + product;
        } catch (Exception e) {
            return "Error Save : " + e.getMessage();
        }
    }

    public static String edit(int id, String name, String productType, double price) {
        try {
            Product product = Product.builder()
                    .id(id)
                    .name(Validator.validateName(name, "Invalid Name"))
                    .productType(ProductType.valueOf(productType))
                    .price(Validator.validatePrice(price, "Invalid"))
                    .build();

            BusinessLogic.edit(product);
            return "Product edited!!\n" + product;
        } catch (Exception e) {
            return "Error Save : " + e.getMessage();
        }
    }

    public static String remove(int id) {
        try {
            Product product = BusinessLogic.remove(id);
            return "Product removed!!\n" + product;
        } catch (Exception e) {
            return "Error Remove : " + e.getMessage();
        }
    }

    public static List<Product> findAll() {
        try {
            return BusinessLogic.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Product> findByName(String name) {
        try {
            return BusinessLogic.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Product> findByType(String name) {
        try {
            return BusinessLogic.findByType(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static Product findByID(int id) {
        try {
            return BusinessLogic.findByID(id);
        } catch (Exception e) {
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------\\

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (ProductType value : ProductType.values()) {
            typeCmb.getItems().add(value.name());
        }

        saveBtn.setOnAction((event) -> {
            String message = save(
                    nameTxt.getText(),
                    typeCmb.getSelectionModel().getSelectedItem(),
                    Double.parseDouble(priceTxt.getText())
            );

            resetForm();

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        editBtn.setOnAction((event) -> {
            String message = edit(
                    Integer.parseInt(idTxt.getText()),
                    nameTxt.getText(),
                    typeCmb.getSelectionModel().getSelectedItem(),
                    Double.parseDouble(priceTxt.getText())
            );

            resetForm();

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        removeBtn.setOnAction((event) -> {
            String message = remove(Integer.parseInt(idTxt.getText()));

            resetForm();

            if (message.startsWith("Info")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
                alert.show();
                resetForm();
            } else if (message.startsWith("Error")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, message);
                alert.show();
            }
        });

        findByTxt.setOnKeyReleased((event) -> {
            refreshTable(findByName(findByTxt.getText()));
        });

        productTbl.setOnMouseReleased((event) -> {
            selectProduct();
        });

        productTbl.setOnKeyReleased((event) -> {
            selectProduct();
        });
    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        findByTxt.clear();
        typeCmb.getSelectionModel().select(0);
        priceTxt.clear();
        refreshTable(findAll());
    }

    public void refreshTable(List<Product> productList) {
        try {
            ObservableList<Product> observableList = FXCollections.observableList(productList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productTbl.setItems(observableList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void selectProduct() {
        Product product = productTbl.getSelectionModel().getSelectedItem();
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        typeCmb.getSelectionModel().select(product.getProductType().name());
        priceTxt.setText(String.valueOf(product.getPrice()));
    }
}
