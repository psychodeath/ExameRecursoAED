package pt.ipleiria.estg.dei.examerecurso.modelo;

import pt.ipleiria.estg.dei.examerecurso.colecoes.Iterador;
import pt.ipleiria.estg.dei.examerecurso.colecoes.ListaSimplesCircularBaseLimite;

public enum ModeloDados {
    INSTANCIA;

    private final String[] exemplos = {"AAAAAAAAAAAA", "AAAABBBBCCCCDDDD", "ABBBAA", "TESTE"};

    private ListaSimplesCircularBaseLimite<String> listaExemplos;


    ModeloDados() {
        listaExemplos= new ListaSimplesCircularBaseLimite<String>(ComparadorLimiteString.INSTANCIA);

        for (String s:exemplos) {
            listaExemplos.inserir(s);
        }
    }


    public Iterador<String> getListaExemplos() {
        return listaExemplos.iterador();
    }

}
