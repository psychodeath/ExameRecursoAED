package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class ListaSimplesCircularBaseLimiteDistinto<T> extends ListaSimplesCircularBaseLimite<T> {

	private static final long serialVersionUID = 8280039415889452854L;

	public ListaSimplesCircularBaseLimiteDistinto(ComparacaoLimite<T> cpl) {
		super(cpl);
	}

	// Não permite inserção de 2 elementos iguais
	@Override
	public void inserir(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		if (criterio.comparar(elem, ant.seguinte.elemento) != 0) {
			new NoListaSimplesCircularBaseLimite(elem, ant);
			numeroElementos++;
		}
	}

	@Override
	public boolean remover(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		if (criterio.comparar(elem, ant.seguinte.elemento) == 0) {
			ant.seguinte = ant.seguinte.seguinte;
			numeroElementos--;
			return true;
		}
		return false;
	}

	@Override
	public boolean contem(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		return ant.seguinte.elemento.equals(elem);
	}

	@Override
	public IteradorListaSimplesCircularBaseLimiteDistinto iterador() {
		return new IteradorListaSimplesCircularBaseLimiteDistinto();
	}

	class IteradorListaSimplesCircularBaseLimiteDistinto extends IteradorListaSimplesCircularBaseLimite {

		public T proximo() {
			return podeAvancar() ? corrente.seguinte.elemento : null;
		}
	}
}
