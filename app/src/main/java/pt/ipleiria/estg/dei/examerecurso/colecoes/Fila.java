package pt.ipleiria.estg.dei.examerecurso.colecoes;

import java.io.Serializable;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public class Fila<T> implements FilaADT<T>, Serializable {

	private static final long serialVersionUID = 8208402283763244915L;
	private NoFila base;

	public Fila() {
		base = new NoFila();
	}

	@Override
	public void inserir(T elem) {
		new NoFila(elem); // sempre na cauda da fila
	}

	@Override
	public T remover() {
		if (isVazia()) {
			return null;
		}
		NoFila frente = base.seguinte;
		frente.seguinte.anterior = base;
		base.seguinte = frente.seguinte;
		return frente.elemento;
	}

	@Override
	public T consultar() {
		return isVazia() ? null : base.seguinte.elemento;
	}

	@Override
	public boolean isVazia() {
		return base.seguinte == base;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Fila = {\n");
		NoFila aux = base.seguinte;
		while (aux != base) {
			s.append(aux.elemento).append("\n");
			aux = aux.seguinte;
		}
		s.append("}\n");
		return s.toString();
	}

	class NoFila implements Serializable {

		private static final long serialVersionUID = -1359812833586072638L;
		T elemento;
		NoFila anterior, seguinte;

		// Criação do nó base (fila vazia)
		NoFila() { 
			elemento = null;
			anterior = this;
			seguinte = this;
		}

		// Criação de nó com elemento elem e inserção na cauda da fila
		NoFila(T elem) {
			elemento = elem;
			anterior = base.anterior;
			seguinte = base;
			base.anterior = this;
			anterior.seguinte = this;
		}
	}
}
