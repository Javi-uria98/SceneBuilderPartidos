package es.javier.views.filters;

import es.javier.models.Partido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilterDivision {
    private ObservableList<Partido> listaPartidos;
    private ObservableList<Partido> listaFiltrada;

    public FilterDivision(ObservableList<Partido> listaPartidos) {
        this.listaPartidos = listaPartidos;
        listaFiltrada = FXCollections.observableArrayList();
    }

    public ObservableList<Partido> filtrar(String equipoLocalFiltrar)
    {
        if (equipoLocalFiltrar!=null && !"".equals(equipoLocalFiltrar))
        {
            //Necesitamos filtrar
            listaFiltrada.clear();
            for (Partido partido : listaPartidos)
            {
                if (partido.getEquipoloc().contains(equipoLocalFiltrar))
                    listaFiltrada.add(partido);
            }
            return listaFiltrada;
        }
        else
        {
            //Tenemos que mostrar todos los registros
            return listaPartidos;
        }

    }
}
