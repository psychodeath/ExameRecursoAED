package pt.ipleiria.estg.dei.examerecurso.modelo;

import pt.ipleiria.estg.dei.examerecurso.colecoes.ComparacaoLimite;
import pt.ipleiria.estg.dei.examerecurso.colecoes.Hashing;
import pt.ipleiria.estg.dei.examerecurso.colecoes.Iterador;
import pt.ipleiria.estg.dei.examerecurso.colecoes.ListaSimplesCircularBaseLimite;
import pt.ipleiria.estg.dei.examerecurso.colecoes.TabelaHashPorSondagemQuadratica;

/**
 * Created by joaomota on 11/02/2017.
 */

public class TabelaHashChavesOrdenadas<C, V> extends TabelaHashPorSondagemQuadratica<C,V> {
    public TabelaHashChavesOrdenadas(int tamanho, Hashing<C> h) {
        super(tamanho, h);
    }
    
    public Iterador<C> iteradorChaves(ComparacaoLimite criterio) {
        Iterador<C> it = super.iteradorChaves();
        ListaSimplesCircularBaseLimite<C> listaChavesOrdenada = new ListaSimplesCircularBaseLimite<C>(criterio);

        if(it == null){
            return null;
        }

        while(it.podeAvancar()){
            listaChavesOrdenada.inserir(it.avancar());
        }

        return listaChavesOrdenada.iterador();
    }
}
