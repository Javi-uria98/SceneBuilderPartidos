package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Partido;
import es.javier.views.filters.FilterDivision;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private FilterDivision filterDivision;

    @FXML
    private MenuItem menuSalvar;

    @FXML
    private MenuItem menuAbrir;

    @FXML
    private MenuItem menuAltaP;

    @FXML
    private MenuItem menuBorrar;

    @FXML
    private TextField filterDivisiontf;

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            FileChooser fileChooser = new FileChooser();
            //Este paso es opcional, para dejar al usuario solo seleccionar ciertos tipos de archivo
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de objetos (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                //Hacer lo que queramos con el archivo.
                System.out.println(file.getAbsolutePath());
            }

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 275));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirPartido(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            FileChooser fileChooser = new FileChooser();
            //Este paso es opcional, para dejar al usuario solo seleccionar ciertos tipos de archivo
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de objetos (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().add(extFilter);
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                //Hacer lo que queramos con el archivo.
                System.out.println(file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewPartidos.setItems(Logica.getInstance().getListaPartidos());
        filterDivision = new FilterDivision(Logica.getInstance().getListaPartidos());
        //Nos subscribimos a cambios en la propiedad text del textfield
        filterDivisiontf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tableViewPartidos.setItems(filterDivision.filtrar(newValue));
            }
        });
    }

    private void filtrar() {
        tableViewPartidos.setItems(filterDivision.filtrar(filterDivisiontf.getText()));
    }
}
