package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface FilaADT<T> {
	/** Insere o elemento elem na cauda da fila.
	 * @param elem a inserir na cauda fila */
	void inserir(T elem);

	/** Remove o elemento da frente da fila e devolve-o.
	 * @return o elemento removido da frente da fila
     * ou null caso a fila esteja vazia */
	T remover();

	/** Devolve o elemento que está na frente da fila sem o remover.
	 * @return o elemento da frente da fila
     * ou null caso a fila esteja vazia */
	T consultar();

	/** Verifica se a fila está vazia.
	 * @return true caso a fila esteja vazia 
     * ou false no caso contrário */
	boolean isVazia();
}
