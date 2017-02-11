package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface ListaDuplaADT<T> extends ListaSimplesADT<T> {

	@Override
	IteradorListaDupla<T> consultar(T elem);

	@Override
	IteradorListaDupla<T> iterador();
}
