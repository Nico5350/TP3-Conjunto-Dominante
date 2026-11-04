package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SolverBacktracking implements SolverConjuntoDominante {

	@Override
	public Set<Integer> resolver(Grafo grafo) {
		if (grafo == null)
			throw new IllegalArgumentException("El grafo no puede ser null");

		Set<Integer> cotaInicial = new SolverGoloso().resolver(grafo);
		return new Busqueda(grafo, cotaInicial).ejecutar();
	}

	private static class Busqueda {

		private final int n;
		private final List<int[]> vecindades;
		private final int[] cobertura; 
		private final TreeSet<Integer> actual;
		private Set<Integer> mejor;

		Busqueda(Grafo grafo, Set<Integer> cotaInicial) {
			n = grafo.cantidadVertices();
			vecindades = new ArrayList<>();
			for (int v = 0; v < n; ++v) {
				int[] vecindad = new int[grafo.grado(v) + 1];
				int i = 0;
				for (int u : new TreeSet<>(grafo.vecindadCerrada(v)))
					vecindad[i++] = u;
				vecindades.add(vecindad);
			}
			cobertura = new int[n];
			actual = new TreeSet<>();
			mejor = new TreeSet<>(cotaInicial);
		}

		Set<Integer> ejecutar() {
			buscar();
			return mejor;
		}

		private void buscar() {
			int v = primerNoDominado();

			if (v == -1) {
				if (actual.size() < mejor.size())
					mejor = new TreeSet<>(actual);
				return;
			}

			if (actual.size() + 1 >= mejor.size())
				return;

			for (int u : vecindades.get(v)) {
				agregar(u);
				buscar();
				quitar(u);
			}
		}

		private int primerNoDominado() {
			for (int v = 0; v < n; ++v)
				if (cobertura[v] == 0)
					return v;
			return -1;
		}

		private void agregar(int u) {
			actual.add(u);
			for (int w : vecindades.get(u))
				++cobertura[w];
		}

		private void quitar(int u) {
			actual.remove(u);
			for (int w : vecindades.get(u))
				--cobertura[w];
		}
	}
}
