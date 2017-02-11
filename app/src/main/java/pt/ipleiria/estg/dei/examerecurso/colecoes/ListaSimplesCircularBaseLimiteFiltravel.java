package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * Created by joaoramos on 15/11/2016.
 */
public class ListaSimplesCircularBaseLimiteFiltravel<T> extends ListaSimplesCircularBaseLimite<T> implements Filtravel<T> {

    private Filtro<T> filtro;
    private Filtro<T> aceitaTudo = new Filtro<T>() {
        @Override
        public boolean aceitar(T t) {
            return true;
        }
    };

    public ListaSimplesCircularBaseLimiteFiltravel(ComparacaoLimite<T> criterio) {
        super(criterio);
        filtro = aceitaTudo;
    }

    @Override
    public void setFiltro(Filtro<T> filtro) {
        this.filtro = filtro != null ? filtro : aceitaTudo;
    }

    @Override
    public boolean isFiltrado() {
        return this.filtro != aceitaTudo;
    }

    @Override
    public IteradorListaSimplesCircularBaseLimite iterador() {
        if (filtro == aceitaTudo) {
            return super.iterador();
        }
        return new IteradorListaSimplesCircularBaseLimiteFiltravel();
    }

    @Override
    public T consultar(int indice) {
        if (filtro == aceitaTudo) {
            return super.consultar(indice);
        }

        if (indice < 0) {
            return null;
        }

        int n = 0;

        for (T t : this) {
            if (n == indice) {
                return t;
            }
            n++;
        }
        return null;
    }

    @Override
    public int getNumeroElementos() {
        if (filtro == aceitaTudo) {
            return super.getNumeroElementos();
        }

        int n = 0;
        for (T t : this) {
            n++;
        }
        return n;
    }

    private class IteradorListaSimplesCircularBaseLimiteFiltravel extends IteradorListaSimplesCircularBaseLimite {
        NoListaSimplesCircularBaseLimite proximoAceite;

        @Override
        public boolean podeAvancar() {
            if (proximoAceite == corrente) {
                proximoAceite = corrente.seguinte;
                while (proximoAceite != base) {
                    if (filtro.aceitar(proximoAceite.elemento)) {
                        return true;
                    }
                    proximoAceite = proximoAceite.seguinte;
                }
            }
            return proximoAceite != base;
        }

        @Override
        public T avancar() {
            if (podeAvancar()) {
                corrente = proximoAceite;
                return corrente.elemento;
            }
            throw ELEMENTO_INVALIDO;
        }

        @Override
        public void reiniciar() {
            super.reiniciar();
            proximoAceite = corrente;
        }
    }
}
