module SceneBuilderPartidos {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    exports es.javier;
    exports es.javier.logica;
    exports es.javier.views;
    exports es.javier.models;
    exports es.javier.views.filters;

    opens es.javier.views to javafx.fxml;
}