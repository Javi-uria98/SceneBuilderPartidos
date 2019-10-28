package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Partido;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DialogoPartidoController {

    private Partido partidoModificar;

    @FXML
    private Button altaButton;

    @FXML
    private TextField equipoLoctf;

    @FXML
    private TextField equipoVistf;

    @FXML
    private TextField resultadotf;

    @FXML
    private ComboBox<String> divisioncb = new ComboBox<String>(Logica.getInstance().getComboBox());

    @FXML
    private DatePicker fechadp;

    @FXML
    void altaModificarPartido(ActionEvent event) {
        if (partidoModificar != null) {
            partidoModificar.setEquipoloc(equipoLoctf.getText());
            partidoModificar.setEquipovis(equipoVistf.getText());
            partidoModificar.setResultado(resultadotf.getText());
            partidoModificar.setFecha(fechadp.getValue());
            partidoModificar.setDivision(divisioncb.getSelectionModel().getSelectedItem());
            Logica.getInstance().modificarPartido(partidoModificar);
        } else {
            Partido partido = new Partido(equipoLoctf.getText(), equipoVistf.getText(), resultadotf.getText(), divisioncb.getSelectionModel().getSelectedItem(), fechadp.getValue());
            Logica.getInstance().addPartido(partido);
        }
        //Como obtener un Stage desde un evento
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    public void setPartidoModificar(Partido partidoModificar) {
        this.partidoModificar = partidoModificar;
        equipoLoctf.setText(partidoModificar.getEquipoloc());
        equipoVistf.setText(partidoModificar.getEquipovis());
        resultadotf.setText(partidoModificar.getResultado());
        fechadp.setValue(partidoModificar.getFecha());
        divisioncb.setItems(Logica.getInstance().getComboBox());
    }

    /**
     * Metodo para hacer validaciones usando la libreria constrolsfx
     *
     * @param url
     * @param resourceBundle
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(equipoLoctf, Validator.createEmptyValidator("El equipo local está vacío"));
        validationSupport.registerValidator(equipoVistf, Validator.createEmptyValidator("El equipo visitante está vacío"));

        //Esto es para que no me deje hacer click en el boton aceptar hasta que el programa vea que no hay ningún error
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                altaButton.setDisable(newValue);
            }
        });
    }

}
