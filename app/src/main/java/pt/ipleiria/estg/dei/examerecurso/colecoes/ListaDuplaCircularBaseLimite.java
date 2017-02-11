package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;
import java.util.Iterator;


/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class ListaDuplaCircularBaseLimite<T> implements ListaDuplaADT<T>, Serializable {

	private static final long serialVersionUID = -7652360575224197789L;
	private NoListaDuplaCircularBaseLimite base;
	private ComparacaoLimite<T> criterio;
	private int numeroElementos;

	// Criação de uma lista dupla vazia com critério de comparação limite cpl
	public ListaDuplaCircularBaseLimite(ComparacaoLimite<T> cpl) {
		criterio = cpl;
		base = new NoListaDuplaCircularBaseLimite(cpl.getLimite());
		numeroElementos = 0;
	}

	// Criação de uma lista dupla a partir da listaDuplaCircularBaseLimite
	public ListaDuplaCircularBaseLimite(ListaDuplaCircularBaseLimite<T> listaDuplaCircularBaseLimite) {
		this(listaDuplaCircularBaseLimite.criterio);

		for (T elem : listaDuplaCircularBaseLimite) {
			new NoListaDuplaCircularBaseLimite(elem, base);
		}
		numeroElementos = listaDuplaCircularBaseLimite.numeroElementos;
	}

	private NoListaDuplaCircularBaseLimite pesquisar(T elem) {
		if (!criterio.isElementoValido(elem)) {
			throw ComparacaoLimite.ELEMENTO_INVALIDO;
		}
		NoListaDuplaCircularBaseLimite cor = base.seguinte;
		while (criterio.comparar(elem, cor.elemento) > 0) {
			cor = cor.seguinte;
		}
		return cor;
	}

	private NoListaDuplaCircularBaseLimite pesquisarAPartirDoFim(T elem) {
		if (!criterio.isElementoValido(elem)) {
			throw ComparacaoLimite.ELEMENTO_INVALIDO;
		}
		NoListaDuplaCircularBaseLimite cor = base.anterior;
		while (cor != base && criterio.comparar(elem, cor.elemento) < 0) {
			cor = cor.anterior;
		}
		return cor;
	}

	@Override
	public void inserir(T elem) {
		new NoListaDuplaCircularBaseLimite(elem, pesquisar(elem));
		numeroElementos++;
	}

	/** Remove após consultar */
	@Override
	public boolean remover(T elem) {
		NoListaDuplaCircularBaseLimite cor = pesquisar(elem);
		while (criterio.comparar(elem, cor.elemento) == 0 && !cor.elemento.equals(elem)) {
			cor = cor.seguinte;
		}
		if (!cor.elemento.equals(elem)) {
			return false;
		}
		cor.anterior.seguinte = cor.seguinte;
		cor.seguinte.anterior = cor.anterior;
		numeroElementos--;
		return true;
	}

	@Override
	public boolean remover(int indice) {
		if (indice < 0 || indice >= numeroElementos) {
			return false;
		}
		NoListaDuplaCircularBaseLimite ant = base;
		while (indice-- != 0) {
			ant = ant.seguinte;
		}
		ant.seguinte = ant.seguinte.seguinte;
		ant.seguinte.anterior = ant;
		numeroElementos--;
		return true;
	}

	@Override
	public boolean removerPorReferencia(T elem) {
		NoListaDuplaCircularBaseLimite cor = pesquisar(elem);
		while (criterio.comparar(elem, cor.elemento) == 0 && cor.elemento != elem) {
			cor = cor.seguinte;
		}
		if (cor.elemento != elem) {
			return false;
		}
		cor.anterior.seguinte = cor.seguinte;
		cor.seguinte.anterior = cor.anterior;
		numeroElementos--;
		return true;
	}

	@Override
	public IteradorListaDupla<T> consultar(T elem) {
		return new IteradorSubListaDuplaCircularBaseLimite(elem);
	}

	@Override
	public T consultar(int indice) {
		if (indice < 0 || indice >= numeroElementos) {
			return null;
		}
		NoListaDuplaCircularBaseLimite cor = base.seguinte;
		while (indice-- != 0) {
			cor = cor.seguinte;
		}
		return cor.elemento;
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
		NoListaDuplaCircularBaseLimite cor = pesquisar(elem);
		while (criterio.comparar(elem, cor.elemento) == 0 && !cor.elemento.equals(elem)) {
			cor = cor.seguinte;
		}
		return cor.elemento.equals(elem);
	}

	@Override
	public ComparacaoLimite<T> getComparador() {
		return criterio;
	}

	@Override
	public IteradorListaDuplaCircularBaseLimite iterador() {
		return new IteradorListaDuplaCircularBaseLimite();
	}

	@Override
	public Iterator<T> iterator() {
		return iterador();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Lista DCBLim = {\n");
		NoListaDuplaCircularBaseLimite aux = base.seguinte;
		while (aux != base) {
			s.append(aux.elemento).append("\n");
			aux = aux.seguinte;
		}
		s.append("}\n");
		return s.toString();
	}

	class NoListaDuplaCircularBaseLimite implements Serializable {

		private static final long serialVersionUID = -9031831270039417801L;
		T elemento;
		NoListaDuplaCircularBaseLimite anterior, seguinte;

		// Criação do nó base
		NoListaDuplaCircularBaseLimite(T lim) {
			elemento = lim;
			anterior = this;
			seguinte = this;
		}

		// Criação de nó com elem e inserção deste antes do nó seg
		NoListaDuplaCircularBaseLimite(T elem, NoListaDuplaCircularBaseLimite seg) {
			if (!criterio.isElementoValido(elem)) {
				throw ComparacaoLimite.ELEMENTO_INVALIDO;
			}
			elemento = elem;
			anterior = seg.anterior;
			seguinte = seg;
			seg.anterior = this;
			anterior.seguinte = this;
		}
	}

	public class IteradorListaDuplaCircularBaseLimite implements IteradorListaDupla<T> {

		protected NoListaDuplaCircularBaseLimite corrente;

		IteradorListaDuplaCircularBaseLimite() {
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
			if (podeAvancar()) {
				corrente = corrente.seguinte;
				return corrente.elemento;
			}
			throw ELEMENTO_INVALIDO;
		}

		@Override
		public boolean podeRecuar() {
			return corrente.anterior != base;
		}

		@Override
		public T recuar() {
			if (podeRecuar()) {
				corrente = corrente.anterior;
				return corrente.elemento;
			}
			throw ELEMENTO_INVALIDO;
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

	class IteradorSubListaDuplaCircularBaseLimite extends IteradorListaDuplaCircularBaseLimite {

		NoListaDuplaCircularBaseLimite anteriorAoPrimeiro;
		NoListaDuplaCircularBaseLimite ultimo;

		public IteradorSubListaDuplaCircularBaseLimite(T elemInicial, T elemFinal) {
			if (!criterio.isElementoValido(elemInicial) || !criterio.isElementoValido(elemFinal)
					|| criterio.comparar(elemInicial, elemFinal) > 0) {
				throw ELEMENTO_INVALIDO;
			}
			anteriorAoPrimeiro = pesquisar(elemInicial).anterior;
			ultimo = pesquisarAPartirDoFim(elemFinal);
			reiniciar();
		}

		public IteradorSubListaDuplaCircularBaseLimite(T elemento) {
			this(elemento, elemento);
		}

		@Override
		public T corrente() {
			if (corrente == anteriorAoPrimeiro) {
				throw ELEMENTO_INVALIDO;
			}
			return super.corrente();
		}

		@Override
		public boolean podeAvancar() {
			return corrente != ultimo;
		}

		@Override
		public boolean podeRecuar() {
			if (corrente == anteriorAoPrimeiro) {
				return ultimo != anteriorAoPrimeiro;
			}
			return corrente.anterior != anteriorAoPrimeiro;
		}

		@Override
		public T recuar() {
			if (corrente == anteriorAoPrimeiro && podeRecuar()) {
				corrente = ultimo;
				return corrente.elemento;
			}
			return super.recuar();
		}

		@Override
		public void reiniciar() {
			corrente = anteriorAoPrimeiro;
		}
	}

}