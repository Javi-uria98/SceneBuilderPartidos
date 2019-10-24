package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Partido;
import es.javier.views.filters.FilterDivision;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private FilterDivision filterequipoloc;

    @FXML
    private MenuItem menuSalvar;

    @FXML
    private MenuItem menuAbrir;

    @FXML
    private MenuItem menuAltaP;

    @FXML
    private MenuItem menuBorrar;

    @FXML
    private TextField filterequipoloctf;

    @FXML
    private TableView<Partido> tableViewPartidos;

    @FXML
    void modificarPartido(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogoPartido.fxml"));
            Parent root = fxmlLoader.load();
            DialogoPartidoController controller = fxmlLoader.getController();
            Partido partido = tableViewPartidos.getSelectionModel().getSelectedItem();
            controller.setPartidoModificar(partido);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 275));
            stage.showAndWait();
            filtrar();
        } catch (IOException e) {
            //prueba de commit
            e.printStackTrace();
        }
    }


    @FXML
    void borrarPartido(ActionEvent event) {
        Partido partido = tableViewPartidos.getSelectionModel().getSelectedItem();
        if (partido != null) {
            Logica.getInstance().borrarPartido(partido);
        } else {
            Alert alerterror = new Alert(Alert.AlertType.ERROR);
            alerterror.setTitle("Ventana de error");
            alerterror.setHeaderText("Error al borrar el partido");
            alerterror.setContentText("No ha seleccionado ningún partido para ser borrado");
            alerterror.show();
        }
    }

    /**
     * Así es como se documentan métodos. El atajo de teclado es /**Intro
     *
     * @param event
     */
    @FXML
    void altaNuevoPartido(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DialogoPartido.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 275));
            stage.showAndWait();
            filtrar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salvarPartido(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Este paso es opcional, para dejar al usuario solo seleccionar ciertos tipos de archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

    }

    @FXML
    void abrirPartido(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Este paso es opcional, para dejar al usuario solo seleccionar ciertos tipos de archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewPartidos.setItems(Logica.getInstance().getListaPartidos());
        filterequipoloc = new FilterDivision(Logica.getInstance().getListaPartidos());
        //Nos subscribimos a cambios en la propiedad text del textfield
        filterequipoloctf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tableViewPartidos.setItems(filterequipoloc.filtrar(newValue));
            }
        });
    }

    private void filtrar() {
        tableViewPartidos.setItems(filterequipoloc.filtrar(filterequipoloctf.getText()));
    }
}
