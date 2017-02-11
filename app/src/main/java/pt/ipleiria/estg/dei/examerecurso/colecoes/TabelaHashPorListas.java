package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class TabelaHashPorListas<C, V> implements TabelaHashADT<C, V>, Serializable {

	private static final long serialVersionUID = 4441261121758858942L;
	protected ListaSimplesCircularBaseLimiteDistinto<Associacao<C, V>>[] tabela;
	protected Hashing<C> hashing;
	protected int numeroElementos;

	public TabelaHashPorListas(int tamanho, Hashing<C> h, ComparacaoLimite<C> criterioChave) {
		tabela = new ListaSimplesCircularBaseLimiteDistinto[tamanho];
		hashing = h;
		ComparacaoLimite<Associacao<C, V>> cia = new ComparacaoLimiteAssociacoes(criterioChave);
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = new ListaSimplesCircularBaseLimiteDistinto<>(cia);
		}
		numeroElementos = 0;
	}

	@Override
	public boolean inserir(C chave, V valor) {
		if (consultar(chave) == null) {
			tabela[posicaoTabela(chave)].inserir(new Associacao<>(chave, valor));
			numeroElementos++;
			return true;
		}
		return false;
	}

	@Override
	public boolean remover(C chave) {
		if (tabela[posicaoTabela(chave)].remover(new Associacao<C, V>(chave, null))) {
			numeroElementos--;
			return true;
		}
		return false;
	}

	// Devolve o valor correspondente à chave
	@Override
	public V consultar(C chave) {
		Iterador<Associacao<C, V>> it = tabela[posicaoTabela(chave)].consultar(new Associacao<C, V>(chave, null));
		return it.podeAvancar() ? it.avancar().getValor() : null;
	}

	@Override
	public boolean isVazia() {
		return numeroElementos == 0;
	}

	@Override
	public int getNumeroElementos() {
		return numeroElementos;
	}

	@Override
	public boolean contem(C chave) {
		return consultar(chave) != null;
	}

	@Override
	public IteradorTabelaHashPorListas iterador() {
		return new IteradorTabelaHashPorListas();
	}

	@Override
	public IteradorChavesTabelaHashPorListas iteradorChaves() {
		return new IteradorChavesTabelaHashPorListas();
	}

	@Override
	public IteradorValoresTabelaHashPorListas iteradorValores() {
		return new IteradorValoresTabelaHashPorListas();
	}

	@Override
	public Iterator<Associacao<C, V>> iterator() {
		return iterador();
	}

	private int posicaoTabela(C chave) {
		return hashing.posicaoTabela(chave, tabela.length);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Tabela = {\n");
		for (int i = 0; i < tabela.length; i++) {
			if (!tabela[i].isVazia()) {
				s.append("Tabela[" + i + "]==> ");
				s.append(tabela[i]);
			}
		}
		s.append("}\n");
		return s.toString();
	}

	class ComparacaoLimiteAssociacoes implements ComparacaoLimite<Associacao<C, V>> {

		private ComparacaoLimite<C> criterioChave;
		private Associacao<C, V> LIMITE;

		public ComparacaoLimiteAssociacoes(ComparacaoLimite<C> criterioChave) {
			this.criterioChave = criterioChave;
			LIMITE = new Associacao<>(criterioChave.getLimite(), null);
		}

		@Override
		public int comparar(Associacao<C, V> o1, Associacao<C, V> o2) {
			return criterioChave.comparar(o1.getChave(), o2.getChave());
		}

		@Override
		public Associacao<C, V> getLimite() {
			return LIMITE;
		}

		@Override
		public boolean isElementoValido(Associacao<C, V> elem) {
			return comparar(elem, LIMITE) < 0;
		}
	}

	class IteradorTabelaHashPorListas implements Iterador<Associacao<C, V>> {

		int indiceActual;
		ListaSimplesCircularBaseLimiteDistinto<Associacao<C, V>>.IteradorListaSimplesCircularBaseLimiteDistinto iteradorListaAtual;

		IteradorTabelaHashPorListas() {
			reiniciar();
		}

		@Override
		public void reiniciar() {
			if (isVazia()) {
				indiceActual = tabela.length;
				iteradorListaAtual = null;
			} else {
				indiceActual = -1;
				iteradorListaAtual = proximoIteradorListaNaoVazia();
			}
		}

		ListaSimplesCircularBaseLimiteDistinto<Associacao<C, V>>.IteradorListaSimplesCircularBaseLimiteDistinto proximoIteradorListaNaoVazia() {
			if (indiceActual == tabela.length) {
				return null;
			}

			while (++indiceActual != tabela.length && tabela[indiceActual].isVazia()) {
			}

			return indiceActual != tabela.length ? tabela[indiceActual].iterador() : null;
		}

		@Override
		public Associacao<C, V> corrente() {
			if (iteradorListaAtual == null) {
				throw Iterador.ELEMENTO_INVALIDO;
			}
			return iteradorListaAtual.corrente();
		}

		@Override
		public boolean podeAvancar() {
			return iteradorListaAtual != null && (iteradorListaAtual.podeAvancar()
					|| (iteradorListaAtual = proximoIteradorListaNaoVazia()) != null);
		}

		@Override
		public Associacao<C, V> avancar() {
			if (podeAvancar()) {
				return iteradorListaAtual.avancar();
			}
			throw Iterador.ELEMENTO_INVALIDO;
		}

		@Override
		public boolean hasNext() {
			return podeAvancar();
		}

		@Override
		public Associacao<C, V> next() {
			return avancar();
		}

		@Override
		public void remove() {
			// NAO PERMITE A REMOCAO DE ELEMENTOS
		}
	}

	abstract class IteradorParcialTabelaHashPorListas<C_ou_V> implements Iterador<C_ou_V> {
		Iterador<Associacao<C, V>> iterador;

		public IteradorParcialTabelaHashPorListas() {
			super();
			this.iterador = iterador();
		}

		protected IteradorParcialTabelaHashPorListas(IteradorTabelaHashPorListas iterador) {
			iterador = iterador();
		}

		@Override
		public boolean podeAvancar() {
			return iterador.podeAvancar();
		}

		@Override
		public void reiniciar() {
			iterador.reiniciar();
		}

		abstract C_ou_V getValor(Associacao<C, V> a);

		@Override
		public C_ou_V avancar() {
			return getValor(iterador.avancar());
		}

		@Override
		public C_ou_V corrente() {
			return getValor(iterador.corrente());
		}

		@Override
		public boolean hasNext() {
			return podeAvancar();
		}

		@Override
		public C_ou_V next() {
			return avancar();
		}

		@Override
		public void remove() {
			// NAO PERMITE A REMOCAO DE ELEMENTOS
		}
	}

	class IteradorChavesTabelaHashPorListas extends IteradorParcialTabelaHashPorListas<C> {

		public IteradorChavesTabelaHashPorListas() {
			super();
		}

		protected IteradorChavesTabelaHashPorListas(IteradorTabelaHashPorListas iterador) {
			super(iterador);
		}

		@Override
		C getValor(Associacao<C, V> a) {
			return a.getChave();
		}
	}

	class IteradorValoresTabelaHashPorListas extends IteradorParcialTabelaHashPorListas<V> {

		public IteradorValoresTabelaHashPorListas() {
			super();
		}

		protected IteradorValoresTabelaHashPorListas(IteradorTabelaHashPorListas iterador) {
			super(iterador);
		}

		@Override
		V getValor(Associacao<C, V> a) {
			return a.getValor();
		}
	}
}