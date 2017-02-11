package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class ListaSimplesCircularBaseLimite<T> implements ListaSimplesADT<T>, Serializable {

	private static final long serialVersionUID = -1655453545980600357L;
	protected NoListaSimplesCircularBaseLimite base;
	protected ComparacaoLimite<T> criterio;
	protected int numeroElementos;

	// Criação de uma lista simples vazia com critério de comparação limite cpl
	public ListaSimplesCircularBaseLimite(ComparacaoLimite<T> cpl) {
		criterio = cpl;
		base = new NoListaSimplesCircularBaseLimite(cpl.getLimite());
		numeroElementos = 0;
	}

	// Criação de uma lista simples a partir da listaSimplesCircularBaseLimite
	public ListaSimplesCircularBaseLimite(ListaSimplesCircularBaseLimite<T> listaSimplesCircularBaseLimite) {
		this(listaSimplesCircularBaseLimite.criterio);

		NoListaSimplesCircularBaseLimite anterior = base;
		for (T elem : listaSimplesCircularBaseLimite) {
			anterior = new NoListaSimplesCircularBaseLimite(elem, anterior);
		}
		numeroElementos = listaSimplesCircularBaseLimite.numeroElementos;
	}

	protected NoListaSimplesCircularBaseLimite pesquisar(T elem) {
		if (!criterio.isElementoValido(elem)) {
			throw ComparacaoLimite.ELEMENTO_INVALIDO;
		}
		NoListaSimplesCircularBaseLimite ant = base;
		while (criterio.comparar(elem, ant.seguinte.elemento) > 0) {
			ant = ant.seguinte;
		}
		return ant;
	}

	@Override
	public void inserir(T elem) {
		new NoListaSimplesCircularBaseLimite(elem, pesquisar(elem));
		numeroElementos++;
	}

	/**
	 * Remove após consultar
	 */
	@Override
	public boolean remover(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		while (criterio.comparar(elem, ant.seguinte.elemento) == 0 && !ant.seguinte.elemento.equals(elem)) {
			ant = ant.seguinte;
		}
		if (!ant.seguinte.elemento.equals(elem)) {
			return false;
		}
		ant.seguinte = ant.seguinte.seguinte;
		numeroElementos--;
		return true;
	}

	@Override
	public boolean remover(int indice) {
		if (indice < 0 || indice >= numeroElementos) {
			return false;
		}
		NoListaSimplesCircularBaseLimite ant = base;
		while (indice-- != 0) {
			ant = ant.seguinte;
		}
		ant.seguinte = ant.seguinte.seguinte;
		numeroElementos--;
		return true;
	}

	@Override
	public boolean removerPorReferencia(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		while (criterio.comparar(elem, ant.seguinte.elemento) == 0 && ant.seguinte.elemento != elem) {
			ant = ant.seguinte;
		}
		if (ant.seguinte.elemento != elem) {
			return false;
		}
		ant.seguinte = ant.seguinte.seguinte;
		numeroElementos--;
		return true;
	}

	@Override
	public Iterador<T> consultar(T elem) {
		return new IteradorSubListaSimplesCircularBaseLimite(elem);
	}

	@Override
	public T consultar(int indice) {
		if (indice < 0 || indice >= numeroElementos) {
			return null;
		}
		NoListaSimplesCircularBaseLimite aux = base.seguinte;
		while (indice-- != 0) {
			aux = aux.seguinte;
		}
		return aux.elemento;
	}

	@Override
	public boolean isVazia() {
		return base.seguinte == base;
	}

	@Override
	public int getNumeroElementos() {
		return numeroElementos;
	}

	@Override
	public boolean contem(T elem) {
		NoListaSimplesCircularBaseLimite ant = pesquisar(elem);
		while (criterio.comparar(elem, ant.seguinte.elemento) == 0 && !ant.seguinte.elemento.equals(elem)) {
			ant = ant.seguinte;
		}
		return ant.seguinte.elemento.equals(elem);
	}

	@Override
	public ComparacaoLimite<T> getComparador() {
		return criterio;
	}

	@Override
	public IteradorListaSimplesCircularBaseLimite iterador() {
		return new IteradorListaSimplesCircularBaseLimite();
	}

	@Override
	public Iterator<T> iterator() {
		return iterador();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Lista SCBLim = {\n");
		NoListaSimplesCircularBaseLimite aux = base.seguinte;
		while (aux != base) {
			s.append(aux.elemento).append("\n");
			aux = aux.seguinte;
		}
		s.append("}\n");
		return s.toString();
	}

	protected class NoListaSimplesCircularBaseLimite implements Serializable {

		private static final long serialVersionUID = 5811664704713121075L;
		public T elemento;
		public NoListaSimplesCircularBaseLimite seguinte;

		// Criação do nó base
		NoListaSimplesCircularBaseLimite(T lim) {
			elemento = lim;
			seguinte = this;
		}

		// Criação de nó com elemento elem e inserção após o nó ant
		NoListaSimplesCircularBaseLimite(T elem, NoListaSimplesCircularBaseLimite ant) {
			if (!criterio.isElementoValido(elem)) {
				throw ComparacaoLimite.ELEMENTO_INVALIDO;
			}
			elemento = elem;
			seguinte = ant.seguinte;
			ant.seguinte = this;
		}
	}

	protected class IteradorListaSimplesCircularBaseLimite implements Iterador<T> {

		protected NoListaSimplesCircularBaseLimite corrente;

		protected IteradorListaSimplesCircularBaseLimite() {
			reiniciar();
		}

		@Override
		public T corrente() {
			if (corrente == base) {
				throw ELEMENTO_INVALIDO;
			}
			return corrente.elemento;
		}

		@Override
		public boolean podeAvancar() {
			return corrente.seguinte != base;
		}

		@Override
		public T avancar() {
			if (!podeAvancar()) {
				throw ELEMENTO_INVALIDO;
			}
			corrente = corrente.seguinte;
			return corrente.elemento;
		}

		@Override
		public void reiniciar() {
			corrente = base;
		}

		/**
		 * Compatibilidade com as coleções Java. Faz o mesmo que o
		 * podeAvancar().
		 *
		 * @return true caso ainda existam elementos a percorrer; false caso
		 *         contrário
		 */
		@Override
		public boolean hasNext() {
			return podeAvancar();
		}

		/**
		 * Compatibilidade com as coleções Java. Faz o mesmo que o avancar().
		 *
		 * @return o próximo elemento
		 */
		@Override
		public T next() {
			return avancar();
		}

		/** Compatibilidade com as coleções Java */
		@Override
		public void remove() {
			// NAO PERMITE A REMOCAO DE ELEMENTOS
		}
	}

	class IteradorSubListaSimplesCircularBaseLimite extends IteradorListaSimplesCircularBaseLimite {
		T elementoFinal;
		NoListaSimplesCircularBaseLimite anteriorAoPrimeiro;

		public IteradorSubListaSimplesCircularBaseLimite(T elemInicial, T elemFinal) {
			if (!criterio.isElementoValido(elemFinal) || criterio.comparar(elemInicial, elemFinal) > 0) {
				throw ELEMENTO_INVALIDO;
			}
			elementoFinal = elemFinal;
			anteriorAoPrimeiro = pesquisar(elemInicial);
			reiniciar();
		}

		public IteradorSubListaSimplesCircularBaseLimite(T elem) {
			this(elem, elem);
		}

		@Override
		public T corrente() {
			if (corrente == anteriorAoPrimeiro) {
				throw ELEMENTO_INVALIDO;
			}
			return corrente.elemento;
		}

		@Override
		public boolean podeAvancar() {
			return criterio.comparar(corrente.seguinte.elemento, elementoFinal) <= 0;
		}

		@Override
		public void reiniciar() {
			super.reiniciar();
			if (anteriorAoPrimeiro != null) {
				corrente = anteriorAoPrimeiro;
			}
		}
	}

}
