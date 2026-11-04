package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class SolverBacktrackingTest {

	private SolverConjuntoDominante solver;

	@Before
	public void setUp() {
		solver = new SolverBacktracking();
	}

	private static Grafo camino(int n) {
		Grafo g = new Grafo(n);
		for (int i = 0; i < n - 1; ++i)
			g.agregarArista(i, i + 1);
		return g;
	}

	@Test(expected = IllegalArgumentException.class)
	public void grafoNullTest() {
		solver.resolver(null);
	}

	@Test
	public void grafoVacioTest() {
		assertTrue(solver.resolver(new Grafo()).isEmpty());
	}

	@Test
	public void unSoloVerticeTest() {
		assertEquals(1, solver.resolver(new Grafo(1)).size());
	}

	@Test
	public void verticesAisladosTest() {
		assertEquals(3, solver.resolver(new Grafo(3)).size());
	}

	@Test
	public void grafoCompletoTest() {
		Grafo g = new Grafo(5);
		for (int i = 0; i < 5; ++i)
			for (int j = i + 1; j < 5; ++j)
				g.agregarArista(i, j);

		assertEquals(1, solver.resolver(g).size());
	}

	@Test
	public void estrellaTest() {
		Grafo g = new Grafo(6);
		for (int i = 1; i < 6; ++i)
			g.agregarArista(0, i);

		Set<Integer> resultado = solver.resolver(g);
		assertEquals(1, resultado.size());
		assertTrue(resultado.contains(0));
	}

	@Test
	public void caminoDeCincoTest() {
		assertEquals(2, solver.resolver(camino(5)).size());
	}

	@Test
	public void caminoDeSieteTest() {
		assertEquals(3, solver.resolver(camino(7)).size());
	}

	@Test
	public void figura1DelEnunciadoTest() {
		Grafo g = new Grafo(6);
		g.agregarArista(5, 3);
		g.agregarArista(3, 4);
		g.agregarArista(3, 2);
		g.agregarArista(4, 0);
		g.agregarArista(4, 1);
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);

		Set<Integer> resultado = solver.resolver(g);
		assertEquals(2, resultado.size());
		assertTrue(Verificador.esDominante(g, resultado));
	}

	@Test
	public void mejoraAlGolosoTest() {
		// camino 3-1-0-2-4: el goloso devuelve {0, 1, 2} y el optimo es {1, 2}
		Grafo g = new Grafo(5);
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(1, 3);
		g.agregarArista(2, 4);

		assertEquals(3, new SolverGoloso().resolver(g).size());
		assertEquals(2, solver.resolver(g).size());
	}

	@Test
	public void grafoDesconectadoTest() {
		Grafo g = new Grafo(6);
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);
		g.agregarArista(3, 4);
		g.agregarArista(4, 5);

		assertEquals(2, solver.resolver(g).size());
	}

	@Test
	public void resultadoSiempreEsDominanteTest() {
		Random azar = new Random(1);
		for (int caso = 0; caso < 50; ++caso) {
			Grafo g = grafoAleatorio(azar, 10);
			assertTrue(Verificador.esDominante(g, solver.resolver(g)));
		}
	}

	@Test
	public void nuncaEsPeorQueElGolosoTest() {
		Random azar = new Random(2);
		for (int caso = 0; caso < 50; ++caso) {
			Grafo g = grafoAleatorio(azar, 12);
			assertTrue(solver.resolver(g).size() <= new SolverGoloso().resolver(g).size());
		}
	}

	@Test
	public void coincideConFuerzaBrutaTest() {
		Random azar = new Random(3);
		for (int caso = 0; caso < 100; ++caso) {
			Grafo g = grafoAleatorio(azar, 9);
			assertEquals(tamanoOptimoPorFuerzaBruta(g), solver.resolver(g).size());
		}
	}

	@Test
	public void noModificaAlGrafoTest() {
		Grafo g = camino(4);
		solver.resolver(g);
		assertEquals(4, g.cantidadVertices());
		assertEquals(3, g.cantidadAristas());
	}

	@Test
	public void sePuedeReutilizarElSolverTest() {
		assertEquals(2, solver.resolver(camino(5)).size());
		assertEquals(2, solver.resolver(camino(5)).size());
		assertEquals(3, solver.resolver(camino(7)).size());
	}

	// grafo con 50% de aristas en promedio
	private static Grafo grafoAleatorio(Random azar, int maxVertices) {
		int n = 1 + azar.nextInt(maxVertices);
		Grafo g = new Grafo(n);
		for (int i = 0; i < n; ++i)
			for (int j = i + 1; j < n; ++j)
				if (azar.nextBoolean())
					g.agregarArista(i, j);
		return g;
	}

	// prueba los 2^n subconjuntos y se queda con el dominante mas chico
	private static int tamanoOptimoPorFuerzaBruta(Grafo g) {
		int n = g.cantidadVertices();
		int mejor = n;
		for (int mascara = 0; mascara < (1 << n); ++mascara) {
			Set<Integer> conjunto = new TreeSet<>();
			for (int v = 0; v < n; ++v)
				if ((mascara & (1 << v)) != 0)
					conjunto.add(v);
			if (conjunto.size() < mejor && Verificador.esDominante(g, conjunto))
				mejor = conjunto.size();
		}
		return mejor;
	}
}