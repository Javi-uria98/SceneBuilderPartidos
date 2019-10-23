package es.javier.logica;

import es.javier.models.Partido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.Month;

public class Logica {
    private static Logica INSTANCE = null;
    private ObservableList<String> listaComboBox;
    private LocalDate today = LocalDate.now();
    private LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);

    private ObservableList<Partido> listaPartidos;


    private Logica()
    {
        listaPartidos = FXCollections.observableArrayList();
        listaComboBox = FXCollections.observableArrayList();

        listaPartidos.add(new Partido("Madrid","Barcelona", "2-1","Primera", today));
        listaPartidos.add(new Partido("Betis","Sevilla", "3-4","Primera",firstDay_2014));
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }


    //Estructuras de datos de mi app


    public void addPartido(Partido partido) {
        listaPartidos.add(partido);
    }


    public ObservableList<Partido> getListaPartidos() {
        return listaPartidos;
    }

    public void borrarPartido(Partido partido) {
        listaPartidos.remove(partido);
    }

    public void modificarPartido(Partido partidoM) {
        int posicion=listaPartidos.indexOf(partidoM);
        listaPartidos.set(posicion,partidoM);
    }

    public ObservableList<String> getComboBox() {
        listaComboBox.addAll("Primera", "Segunda", "Tercera");
        return listaComboBox;
    }
}
