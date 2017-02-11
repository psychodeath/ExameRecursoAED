package pt.ipleiria.estg.dei.examerecurso.modelo;

import pt.ipleiria.estg.dei.examerecurso.colecoes.ComparacaoLimite;

/**
 * Created by joaoramos on 2/1/17.
 */

enum ComparadorLimiteString implements ComparacaoLimite<String> {
    INSTANCIA;

    private static final String LIMITE = String.valueOf(Character.MAX_VALUE);


    @Override
    public String getLimite() {
        return LIMITE;
    }

    @Override
    public boolean isElementoValido(String elem) {
        return elem!=null && comparar(elem, LIMITE)<0;
    }

    @Override
    public int comparar(String o1, String o2) {
        return o1.compareTo(o2);
    }
}