package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface Iterador<T> extends Iterator<T> {

	/**
	 * Exceção propagada quando se acede a elemento inválido.
	 */
	NoSuchElementException ELEMENTO_INVALIDO = new NoSuchElementException();

	/**
	 * Devolve o elemento atual. Caso não exista lança a excepção
	 * ELEMENTO_INVALIDO
	 *
	 * @return o elemento atual
	 */
	T corrente();

	/**
	 * Verifica se pode avançar para o próximo elemento.
	 *
	 * @return true caso ainda existam elementos a percorrer; false caso
	 *         contrário
	 */
	boolean podeAvancar();

	/**
	 * Devolve o próximo elemento. Caso não exista lança a excepção
	 * ELEMENTO_INVALIDO
	 *
	 * @return o próximo elemento
	 */
	T avancar();

	/**
	 * Coloca o iterador na base da lista
	 */
	void reiniciar();
}
