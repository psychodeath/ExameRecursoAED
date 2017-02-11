package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class Pilha<T> implements PilhaADT<T>, Serializable {

	private static final long serialVersionUID = -7330183067950152233L;
	private NoPilha noTopo;

	public Pilha() {
		noTopo = null;
	}

	@Override
	public void inserir(T elem) {
		new NoPilha(elem);
	}

	@Override
	public T remover() {
		if (isVazia()) {
			return null;
		}
		T aux = noTopo.elemento;
		noTopo = noTopo.seguinte;
		return aux;
	}

	@Override
	public T consultar() {
		return isVazia() ? null : noTopo.elemento;
	}

	@Override
	public boolean isVazia() {
		return noTopo == null;
	}

	public void limpar() {
		noTopo = null;
	}

	/**
	 * Lista todos os elementos da pilha desde o seu topo.
	 */
	@Override
	public String toString() {
		StringBuilder lista = new StringBuilder();
		lista.append("Pilha = {");
		NoPilha aux = noTopo;
		while (aux != null) {
			lista.append(aux.elemento);
			aux = aux.seguinte;
			if (aux != null) {
				lista.append(", ");
			}
		}
		lista.append("}\n");
		return lista.toString();
	}

	class NoPilha implements Serializable {

		private static final long serialVersionUID = 4122396925436865942L;
		T elemento;
		NoPilha seguinte;

		// Criação de nó com elem e inserção no topo da pilha
		public NoPilha(T elem) {
			elemento = elem;
			seguinte = noTopo;
			noTopo = this;
		}
	}

}
