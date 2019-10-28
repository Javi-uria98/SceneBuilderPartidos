package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Partido;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class DialogoPartidoController implements Initializable {

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
        validationSupport.registerValidator(equipoLoctf, Validator.createRegexValidator("Solo pueden introducirse letras y espacios", Pattern.compile("^[a-zA-Z ]*$"), Severity.ERROR));
        validationSupport.registerValidator(equipoVistf, Validator.createEmptyValidator("El equipo visitante está vacío"));
        validationSupport.registerValidator(equipoVistf, Validator.createRegexValidator("Solo pueden introducirse letras y espacios", Pattern.compile("^[a-zA-Z ]*$"), Severity.ERROR));
        validationSupport.registerValidator(resultadotf, Validator.createEmptyValidator("El resultado está vacío"));
        validationSupport.registerValidator(resultadotf, Validator.createRegexValidator("Solo pueden introducirse numeros y guiones, y el formato ha de ser 'nº - nº'", Pattern.compile("^\\d+\\-?\\d+$"), Severity.ERROR));
        validationSupport.registerValidator(fechadp, Validator.createEmptyValidator("La fecha está vacía"));
        //validationSupport.registerValidator(fechadp, Validator.createRegexValidator("La fecha solo puede ir en formato MM/DD/YYYY", Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$"),Severity.ERROR));

        //Esto es para que no me deje hacer click en el boton aceptar hasta que el programa vea que no hay ningún error
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                altaButton.setDisable(newValue);
            }
        });

    }

}
