package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {

	private final List<Set<Integer>> vecinos;
	private int cantidadAristas;

	public Grafo() {
		this(0);
	}

	public Grafo(int cantidadVertices) {
		if (cantidadVertices < 0)
			throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa: " + cantidadVertices);

		vecinos = new ArrayList<>();
		for (int i = 0; i < cantidadVertices; ++i)
			vecinos.add(new HashSet<Integer>());
	}

	//devuelve indice
	public int agregarVertice() {
		vecinos.add(new HashSet<Integer>());
		return vecinos.size() - 1;
	}

	//Comprueba arista si existe no hace nada
	public void agregarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		if (i == j)
			throw new IllegalArgumentException("No se permiten lazos: " + i);

		if (vecinos.get(i).add(j)) {
			vecinos.get(j).add(i);
			++cantidadAristas;
		}
	}

	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		return vecinos.get(i).contains(j);
	}

	public int cantidadVertices() {
		return vecinos.size();
	}

	public int cantidadAristas() {
		return cantidadAristas;
	}

	public int grado(int v) {
		verificarVertice(v);
		return vecinos.get(v).size();
	}

	// Vecinos de v
	public Set<Integer> vecinos(int v) {
		verificarVertice(v);
		return Collections.unmodifiableSet(vecinos.get(v));
	}

	public Set<Integer> vecindadCerrada(int v) {
		verificarVertice(v);
		Set<Integer> ret = new HashSet<>(vecinos.get(v));
		ret.add(v);
		return ret;
	}

	public boolean existeVertice(int v) {
		return v >= 0 && v < vecinos.size();
	}

	private void verificarVertice(int v) {
		if (!existeVertice(v))
			throw new IllegalArgumentException("El vertice " + v + " no existe");
	}
}
