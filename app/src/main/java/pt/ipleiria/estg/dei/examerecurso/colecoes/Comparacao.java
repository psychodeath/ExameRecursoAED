package pt.ipleiria.estg.dei.examerecurso.colecoes;

/**
 * @author Actual code: Carlos Urbano<carlos.urbano@ipleiria.pt>, Catarina Reis
 *         <catarina.reis@ipleiria.pt> Marco Ferreira
 *         <marco.ferreira@ipleiria.pt> Original code: José Magno
 *         <jose.magno@ipleiria.pt>
 */
public interface Comparacao<T> {
	/*
	 * Devolve > 0 se a ordem do 1º elemento for superior do 2º, 0 se a ordem do
	 * 1º elemento for igual do 2º e < 0 se a ordem do 1º elemento for inferior
	 * do 2º.
	 */
	int comparar(T o1, T o2);
}