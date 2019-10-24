package es.javier;

import es.javier.logica.Logica;
import es.javier.models.Partido;
import es.javier.views.MainWindowController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        cargarFichero();
        Parent root = FXMLLoader.load(getClass().getResource("views/MainWindow.fxml"));
        stage.setTitle("Pantalla principal");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                crearFichero();
            }
        });
    }

    public static void main(String args[])
    {
        launch(args);
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