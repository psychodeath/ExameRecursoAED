package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
abstract public class TabelaHashPorSondagem<C, V> implements TabelaHashADT<C, V>, Serializable {

	private static final long serialVersionUID = -451873137286370124L;
	protected static final RuntimeException SONDAGEM_CIRCULAR = new RuntimeException(
			"Sondagem circular de toda a tabela");

	protected Entrada<C, V>[] tabela;
	protected Hashing<C> hashing;
	protected int numeroElementos, numeroElementosInativos;

	public TabelaHashPorSondagem(int tamanho, Hashing<C> h) {
		tabela = new Entrada[proximoPrimo(tamanho)];
		hashing = h;
		numeroElementos = numeroElementosInativos = 0;
	}

	public static int proximoPrimo(int n) {
		if (n < 2) {
			return 2;
		}
		if (n % 2 == 0) {
			++n;
		}
		for (int i;; n += 2) {
			for (i = 3; i * i <= n && n % i != 0; i += 2) {
				// empty
			}
			if (i * i > n) {
				return n;
			}
		}
	}

	// devolve a primeira posição livre ou a posição da chave
	protected abstract int posicaoTabela(C chave);

	@Override
	public boolean inserir(C chave, V valor) {
		int i = posicaoTabela(chave);
		if (tabela[i] != null) {
			if (tabela[i].getChave().equals(chave) && tabela[i].isAtivo()) {
				return false;
			}
			numeroElementosInativos--;
		}
		tabela[i] = new Entrada(chave, valor);
		numeroElementos++;
		if (fatorCarga() >= 0.5) {
			reHash();
		}
		return true;
	}

	private float fatorCarga() {
		return (numeroElementos + numeroElementosInativos) / (float) tabela.length;
	}

	private void reHash() {
		int tam = proximoPrimo(tabela.length * 2);
		Entrada<C, V>[] tabelaAntiga = tabela;
		tabela = new Entrada[tam];
		numeroElementos = numeroElementosInativos = 0;
		for (int i = 0; i < tabelaAntiga.length; i++) {
			if (tabelaAntiga[i] != null && tabelaAntiga[i].isAtivo()) {
				inserir(tabelaAntiga[i].getChave(), tabelaAntiga[i].getValor());
			}
		}
	}

	@Override
	public boolean remover(C chave) {
		int i = posicaoTabela(chave);
		if (tabela[i] != null && tabela[i].getChave().equals(chave) && tabela[i].isAtivo()) {
			tabela[i].setAtivo(false);
			numeroElementosInativos++;
			numeroElementos--;
			return true;
		}
		return false;
	}

	public void removerTodos() {
		for (int i = 0; i < tabela.length; i++) {
			tabela[i] = null;
		}
		numeroElementos = numeroElementosInativos = 0;
	}

	@Override
	public V consultar(C chave) {
		int i = posicaoTabela(chave);
		if (tabela[i] != null && tabela[i].getChave().equals(chave) && tabela[i].isAtivo()) {
			return tabela[i].getValor();
		}
		return null;
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
	public Iterator<Associacao<C, V>> iterator() {
		return iterador();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Tabela de tamanho ");
		s.append(tabela.length);
		s.append(" = {\n");
		for (int i = 0; i < tabela.length; i++) {
			if (tabela[i] != null) {
				s.append("Tabela[" + i + "]==> ");
				s.append(tabela[i]);
				s.append("\n");
			}
		}
		s.append("}\n");
		return s.toString();
	}

	@Override
	public IteradorTabelaHashPorSondagem iterador() {
		return new IteradorTabelaHashPorSondagem();
	}

	@Override
	public Iterador iteradorChaves() {
		return new IteradorChavesTabelaHashPorSondagem();
	}

	@Override
	public IteradorValoresTabelaHashPorSondagem iteradorValores() {
		return new IteradorValoresTabelaHashPorSondagem();
	}

	class Entrada<C, V> implements Serializable {

		Associacao<C, V> associacao;
		boolean ativo;

		Entrada(C chave, V valor) {
			associacao = new Associacao<>(chave, valor);
			ativo = true;
		}

		C getChave() {
			return associacao.getChave();
		}

		V getValor() {
			return associacao.getValor();
		}

		void setValor(V valor) {
			associacao.setValor(valor);
		}

		Associacao<C, V> getAssociacao() {
			return associacao;
		}

		boolean isAtivo() {
			return ativo;
		}

		void setAtivo(boolean estado) {
			ativo = estado;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(associacao);
			sb.append(isAtivo() ? " - Activo" : " - Inactivo");
			return sb.toString();
		}
	}

	class IteradorTabelaHashPorSondagem implements Iterador<Associacao<C, V>> {

		int corrente, proximo;

		IteradorTabelaHashPorSondagem() {
			reiniciar();
		}

		@Override
		public void reiniciar() {
			if (isVazia()) {
				corrente = proximo = tabela.length;
			} else {
				corrente = tabela.length;
				proximo = -1;
				proximo = proximoAtivo();
			}
		}

		@Override
		public Associacao<C, V> corrente() {
			if (corrente == tabela.length) {
				throw ELEMENTO_INVALIDO;
			}
			return tabela[corrente].getAssociacao();
		}

		private int proximoAtivo() {
			if (proximo == tabela.length) {
				return proximo;
			}
			while (++proximo != tabela.length && (tabela[proximo] == null || !tabela[proximo].isAtivo())) {
			}
			return proximo;
		}

		@Override
		public boolean podeAvancar() {
			return proximo != tabela.length;
		}

		@Override
		public Associacao<C, V> avancar() {
			if (podeAvancar()) {
				corrente = proximo;
				proximo = proximoAtivo();
				return tabela[corrente].getAssociacao();
			}
			throw ELEMENTO_INVALIDO;
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

	abstract class IteradorParcialTabelaHashPorSondagem<C_ou_V> implements Iterador<C_ou_V> {
		Iterador<Associacao<C, V>> iterador;

		IteradorParcialTabelaHashPorSondagem() {
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

	class IteradorChavesTabelaHashPorSondagem extends IteradorParcialTabelaHashPorSondagem<C> {

		@Override
		C getValor(Associacao<C, V> a) {
			return a.getChave();
		}
	}

	class IteradorValoresTabelaHashPorSondagem extends IteradorParcialTabelaHashPorSondagem<V> {

		@Override
		V getValor(Associacao<C, V> a) {
			return a.getValor();
		}
	}
}
