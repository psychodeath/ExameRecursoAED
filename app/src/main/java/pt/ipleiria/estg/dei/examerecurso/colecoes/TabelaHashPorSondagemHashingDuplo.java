package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class TabelaHashPorSondagemHashingDuplo<C, V> extends TabelaHashPorSondagem<C, V> {

	private static final long serialVersionUID = -171435229872067082L;
	private Hashing<C> hashingIncremento;

	public TabelaHashPorSondagemHashingDuplo(int tamanho, Hashing<C> h, Hashing<C> hI) {
		super(tamanho, h);
		hashingIncremento = hI;
	}

	@Override
	// devolve a primeira posição livre ou a posição da chave
	protected int posicaoTabela(C chave) {
		int i = hashing.posicaoTabela(chave, tabela.length), pos = -1, inicial = i,
				inc = hashingIncremento.posicaoTabela(chave, tabela.length);
		while (tabela[i] != null && !tabela[i].getChave().equals(chave)) {
			if (!tabela[i].isAtivo()) {
				pos = i;
				break;
			}
			i = (i + inc) % tabela.length;
			if (i == inicial) {
				throw SONDAGEM_CIRCULAR;
			}
		}
		if (pos != -1) {
			do {
				i = (i + inc) % tabela.length;
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
