package profiler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import profiler.models.businessLogic.BusinessLogic;
import profiler.models.entities.Car;
import profiler.models.entities.enums.Colors;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CarController implements Initializable {
    @FXML
    private TextField idTxt, nameTxt, findByNameTxt;

    @FXML
    private ComboBox<String> colorCmb;

    @FXML
    private DatePicker manDate;

    @FXML
    private Button saveBtn, editBtn, removeBtn;

    @FXML
    private TableView<Car> carTbl;

    @FXML
    private TableColumn <Car, String> idCol, nameCol, colorCol, dateCol;


    public static String save(String name, String color, LocalDate manDate) {
        try {
            Car car = Car.builder()
                    .name(name)
                    .color(Colors.valueOf(color))
                    .manDate(manDate)
                    .build();

            BusinessLogic.save(car);
            return "Car Saved!!\n" + car.toString();
        } catch (Exception e) {
            return "Error Save : " + e.getMessage();
        }
    }

    public static String edit(int id, String name, String color, LocalDate manDate) {
        try {
            Car car = Car.builder()
                    .id(id)
                    .name(name)
                    .color(Colors.valueOf(color))
                    .manDate(manDate)
                    .build();

            BusinessLogic.edit(car);
            return "Car Edited!!\n" + car.toString();
        } catch (Exception e) {
            return "Error Edit : " + e.getMessage();
        }
    }

    public static String remove(int id) {
        try {
            Car car = BusinessLogic.remove(id);
            return "Car removed!!\n" + car;
        } catch (Exception e) {
            return "Error Remove : " + e.getMessage();
        }
    }

    public static List<Car> findAll() {
        try {
            return BusinessLogic.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Car> findByName(String name) {
        try {
            return BusinessLogic.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static Car findByID(int id) {
        try {
            return BusinessLogic.findByID(id);
        } catch (Exception e) {
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------\\

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Colors value : Colors.values()) {
            colorCmb.getItems().add(value.name());
        }
        resetForm();

        saveBtn.setOnAction((event) -> {
            String message = save(
                    nameTxt.getText(),
                    colorCmb.getSelectionModel().getSelectedItem(),
                    manDate.getValue()
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
                    colorCmb.getSelectionModel().getSelectedItem(),
                    manDate.getValue()
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

        findByNameTxt.setOnKeyReleased((event) -> {
            refreshTable(findByName(findByNameTxt.getText()));
        });

        carTbl.setOnMouseReleased((event) -> {
            selectCar();
        });

        carTbl.setOnKeyReleased((event) -> {
            selectCar();
        });
    }

    public void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        findByNameTxt.clear();
        colorCmb.getSelectionModel().select(0);
        manDate.setValue(LocalDate.now());
        refreshTable(findAll());
    }

    public void refreshTable(List<Car> carList) {
        try {
            ObservableList<Car> observableList = FXCollections.observableList(carList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("manDate"));

            carTbl.setItems(observableList);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void selectCar() {
        Car car = carTbl.getSelectionModel().getSelectedItem();
        idTxt.setText(String.valueOf(car.getId()));
        nameTxt.setText(car.getName());
        colorCmb.getSelectionModel().select(car.getColor().name());
        manDate.setValue(car.getManDate());
    }
}
