package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface TabelaHashADT<C, V> extends Iterable<Associacao<C, V>> {

	/**
	 * Insere uma associação (chave, valor) sem duplicação
	 */
	boolean inserir(C chave, V valor);

	/**
	 * Remove a associação correspondente à chave
	 */
	boolean remover(C chave);

	/**
	 * Devolve o valor correspondente à chave ou null
	 */
	V consultar(C chave);

	/**
	 * Verifica se a tabela está sem elementos
	 */
	boolean isVazia();

	/**
	 * Verifica se chave pertence à tabela
	 */
	boolean contem(C chave);

	/**
	 * Devolve o número de elementos da tabela
	 */
	int getNumeroElementos();

	/**
	 * Devolve um iterador das associacões da tabela
	 */
	Iterador<Associacao<C, V>> iterador();

	/**
	 * Devolve um iterador das chaves da tabela
	 */
	Iterador<C> iteradorChaves();

	/**
	 * Devolve um iterador dos valores da tabela
	 */
	Iterador<V> iteradorValores();
}
