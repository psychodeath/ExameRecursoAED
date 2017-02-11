package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class TabelaHashPorSondagemLinear<C, V> extends TabelaHashPorSondagem<C, V> {

	private static final long serialVersionUID = 7879303422787268838L;

	public TabelaHashPorSondagemLinear(int tamanho, Hashing<C> h) {
		super(tamanho, h);
	}

	@Override
	// devolve a primeira posição livre ou a posição da chave
	protected int posicaoTabela(C chave) {
		int i = hashing.posicaoTabela(chave, tabela.length), pos = -1, inicial = i;
		while (tabela[i] != null && !tabela[i].getChave().equals(chave)) {
			if (!tabela[i].isAtivo()) {
				pos = i;
				break;
			}
			i = (i + 1) % tabela.length;
			if (i == inicial) {
				throw SONDAGEM_CIRCULAR;
			}
		}
		if (pos != -1) {
			do {
				i = (i + 1) % tabela.length;
				if (i == inicial) {
					throw SONDAGEM_CIRCULAR;
				}
			} while (tabela[i] != null && !tabela[i].getChave().equals(chave));
		}
		if (tabela[i] == null && pos != -1) {
			return pos;
		}
		return i;
	}
}
