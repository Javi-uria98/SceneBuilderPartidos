package es.javier.logica;

import es.javier.models.Partido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Logica {
    private static Logica INSTANCE = null;
    private ObservableList<String> listaComboBox;
    //private LocalDate today = LocalDate.now();
    //private LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);

    private ObservableList<Partido> listaPartidos;


    private Logica() {
        listaPartidos = FXCollections.observableArrayList();
        listaComboBox = FXCollections.observableArrayList();
        listaComboBox.addAll("Primera", "Segunda", "Tercera");

        //listaPartidos.add(new Partido("Madrid","Barcelona", "2-1","Primera", today));
        //listaPartidos.add(new Partido("Betis","Sevilla", "3-4","Primera",firstDay_2014));
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
        int posicion = listaPartidos.indexOf(partidoM);
        listaPartidos.set(posicion, partidoM);
    }

    public ObservableList<String> getComboBox() {
        return listaComboBox;
    }

    public void cargarFichero() {
        try {
            ObservableList<Partido> listaObservable = Logica.getInstance().getListaPartidos();
            FileInputStream fis = new FileInputStream(new File("partidos.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Partido> listaNoObservable = new ArrayList<>(listaObservable);
            listaNoObservable = (ArrayList<Partido>) ois.readObject();
            listaObservable.addAll(listaNoObservable);
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("La primera vez que ejecutes no estará creado el fichero... ¡pero no hay problema alguno, pues se creará cuando cierres el programa! " +
                    "Así pues, nunca más volverás a ver este mensaje... (a no ser que borres el fichero)");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void crearFichero() {
        try {
            ObservableList<Partido> listaObservable = Logica.getInstance().getListaPartidos();
            FileOutputStream fos = new FileOutputStream(new File("partidos.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<Partido>(listaObservable));
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error al buscar el fichero");
        } catch (IOException e) {
            System.out.println("Error al inicializar el fichero");
            e.printStackTrace();
        }
    }
}
