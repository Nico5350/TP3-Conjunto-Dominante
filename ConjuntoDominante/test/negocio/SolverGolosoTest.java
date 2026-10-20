package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SolverGolosoTest {

	private SolverConjuntoDominante solver;

	@Before
	public void setUp() {
		solver = new SolverGoloso();
	}

	private static Set<Integer> conjunto(Integer... vertices) {
		return new HashSet<>(Arrays.asList(vertices));
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
		assertEquals(conjunto(0), solver.resolver(new Grafo(1)));
	}

	@Test
	public void verticesAisladosTest() {
		assertEquals(conjunto(0, 1, 2), solver.resolver(new Grafo(3)));
	}

	@Test
	public void grafoCompletoTest() {
		Grafo g = new Grafo(4);
		for (int i = 0; i < 4; ++i)
			for (int j = i + 1; j < 4; ++j)
				g.agregarArista(i, j);

		assertEquals(1, solver.resolver(g).size());
	}

	@Test
	public void estrellaTest() {
		Grafo g = new Grafo(5);
		for (int i = 1; i < 5; ++i)
			g.agregarArista(0, i);

		assertEquals(conjunto(0), solver.resolver(g));
	}

	@Test
	public void caminoTest() {
		Grafo g = new Grafo(5);
		for (int i = 0; i < 4; ++i)
			g.agregarArista(i, i + 1);

		Set<Integer> resultado = solver.resolver(g);
		assertEquals(2, resultado.size());
		assertTrue(Verificador.esDominante(g, resultado));
	}

	@Test
	public void figura1DelEnunciadoTest() {
		// Vertices del enunciado 1..6 -> indices 0..5
		Grafo g = new Grafo(6);
		g.agregarArista(5, 3); // 6-4
		g.agregarArista(3, 4); // 4-5
		g.agregarArista(3, 2); // 4-3
		g.agregarArista(4, 0); // 5-1
		g.agregarArista(4, 1); // 5-2
		g.agregarArista(0, 1); // 1-2
		g.agregarArista(1, 2); // 2-3

		// {2, 4} en la numeracion del enunciado
		assertEquals(conjunto(1, 3), solver.resolver(g));
	}

	@Test
	public void grafoDesconectadoTest() {
		Grafo g = new Grafo(6);
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);
		g.agregarArista(3, 4);
		g.agregarArista(4, 5);

		Set<Integer> resultado = solver.resolver(g);
		assertEquals(conjunto(1, 4), resultado);
	}

	@Test
	public void resultadoSiempreEsDominanteTest() {
		Grafo g = new Grafo(8);
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(2, 3);
		g.agregarArista(3, 4);
		g.agregarArista(4, 5);
		g.agregarArista(5, 6);
		g.agregarArista(6, 0);

		assertTrue(Verificador.esDominante(g, solver.resolver(g)));
	}

	@Test
	public void noModificaAlGrafoTest() {
		Grafo g = new Grafo(3);
		g.agregarArista(0, 1);
		solver.resolver(g);
		assertEquals(3, g.cantidadVertices());
		assertEquals(1, g.cantidadAristas());
	}
}
