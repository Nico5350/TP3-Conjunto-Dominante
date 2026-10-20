package negocio;

import java.util.Set;
import java.util.TreeSet;

 //Algoritmo goloso en cada paso elige el vertice cuya vecindad cerradacubre la mayor cantidad de vertices todavia no dominados, gana el vertice de menor indice
 
public class SolverGoloso implements SolverConjuntoDominante {

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		if (grafo == null)
			throw new IllegalArgumentException("El grafo no puede ser null");

		int n = grafo.cantidadVertices();
		boolean[] dominado = new boolean[n];
		int sinDominar = n;
		Set<Integer> resultado = new TreeSet<>();

		while (sinDominar > 0) {
			int mejor = -1;
			int mejorCobertura = -1;

			for (int v = 0; v < n; ++v) {
				int cobertura = nuevosDominados(grafo, v, dominado);
				if (cobertura > mejorCobertura) {
					mejor = v;
					mejorCobertura = cobertura;
				}
			}

			resultado.add(mejor);
			for (int u : grafo.vecindadCerrada(mejor))
				dominado[u] = true;
			sinDominar -= mejorCobertura;
		}

		return resultado;
	}

	private int nuevosDominados(Grafo grafo, int v, boolean[] dominado) {
		int cantidad = 0;
		for (int u : grafo.vecindadCerrada(v))
			if (!dominado[u])
				++cantidad;
		return cantidad;
	}
}
