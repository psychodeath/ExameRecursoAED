package pt.ipleiria.estg.dei.examerecurso.colecoes;


/**
 * Created by joaoramos on 15/11/2016.
 */
public interface Filtravel<T> {
    void setFiltro(Filtro<T> filtro);

    boolean isFiltrado();
}
