package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface ListaSimplesADT<T> extends Iterable<T> {
	void inserir(T elem);

	boolean remover(T elem);

	boolean remover(int indice);

	boolean removerPorReferencia(T elem);

	Iterador<T> consultar(T elem);

	T consultar(int indice);

	boolean isVazia();

	int getNumeroElementos();

	boolean contem(T elem);

	Comparacao<T> getComparador();

	Iterador<T> iterador();
}
