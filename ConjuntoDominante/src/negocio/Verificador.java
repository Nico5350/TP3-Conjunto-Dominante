package negocio;

import java.util.Set;

// Comprueba si un conjunto de vertices es dominante en un grafo
public class Verificador {

	public static boolean esDominante(Grafo grafo, Set<Integer> conjunto) {
		if (grafo == null || conjunto == null)
			throw new IllegalArgumentException("Grafo y conjunto no pueden ser null");

		for (int v : conjunto)
			if (!grafo.existeVertice(v))
				throw new IllegalArgumentException("El vertice " + v + " no existe");

		for (int v = 0; v < grafo.cantidadVertices(); ++v)
			if (!estaDominado(grafo, v, conjunto))
				return false;
		return true;
	}

	private static boolean estaDominado(Grafo grafo, int v, Set<Integer> conjunto) {
		if (conjunto.contains(v))
			return true;
		for (int u : grafo.vecinos(v))
			if (conjunto.contains(u))
				return true;
		return false;
	}
}
