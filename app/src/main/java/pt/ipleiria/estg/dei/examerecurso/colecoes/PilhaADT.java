package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface PilhaADT<T> {

	/** Insere o elemento elem no topo da pilha.
	 * @param o elemento a colocar no topo */
	void inserir(T elem);

	/** Remove o elemento que está no topo da pilha e devolve-o.
	 * @return o elemento removido do topo da pilha ou null se estiver vazia */
	T remover();

	/** Devolve o elemento que está no topo da pilha sem o remover.
	 * @return o elemento que está no topo da pilha ou null se estiver vazia */
	T consultar();

	/** Verifica se a pilha está vazia.
	 * @return true caso a pilha se encontre vazia; false caso contrário */
	boolean isVazia();
}
