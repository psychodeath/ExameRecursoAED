package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class Associacao<C, V> implements Serializable {

	private static final long serialVersionUID = 5906011552417549444L;
	private C chave;
	private V valor;

	public Associacao(C chave, V valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public C getChave() {
		return chave;
	}

	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Chave: ").append(chave);
		sb.append(" Valor: ").append(valor);
		return sb.toString();
	}
}
