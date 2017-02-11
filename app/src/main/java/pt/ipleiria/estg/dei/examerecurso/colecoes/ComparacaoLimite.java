package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface ComparacaoLimite<T> extends Comparacao<T> {

	// Exceção propagada quando um elemento é inválido na comparação limite
	RuntimeException ELEMENTO_INVALIDO = new RuntimeException();

	// define o limite do critério de comparação
	T getLimite();

	// verifica se o elemento elem respeita o critério de comparação limite
	boolean isElementoValido(T elem);
}
