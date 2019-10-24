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

//aasasasadwaas

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Logica.getInstance().cargarFichero();
        Parent root = FXMLLoader.load(getClass().getResource("views/MainWindow.fxml"));
        stage.setTitle("Pantalla principal");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Logica.getInstance().crearFichero();
            }
        });
    }

    public static void main(String args[])
    {
        launch(args);
    }

}