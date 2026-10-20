package negocio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class VerificadorTest {

	private static Set<Integer> conjunto(Integer... vertices) {
		return new HashSet<>(Arrays.asList(vertices));
	}

	private Grafo camino4() {
		Grafo g = new Grafo(4);
		g.agregarArista(0, 1);
		g.agregarArista(1, 2);
		g.agregarArista(2, 3);
		return g;
	}

	@Test
	public void conjuntoDominanteTest() {
		assertTrue(Verificador.esDominante(camino4(), conjunto(1, 2)));
	}

	@Test
	public void conjuntoNoDominanteTest() {
		assertFalse(Verificador.esDominante(camino4(), conjunto(0)));
	}

	@Test
	public void conjuntoVacioEnGrafoNoVacioTest() {
		assertFalse(Verificador.esDominante(camino4(), conjunto()));
	}

	@Test
	public void conjuntoVacioEnGrafoVacioTest() {
		assertTrue(Verificador.esDominante(new Grafo(), conjunto()));
	}

	@Test
	public void todosLosVerticesSiempreDominanTest() {
		assertTrue(Verificador.esDominante(camino4(), conjunto(0, 1, 2, 3)));
	}

	@Test
	public void verticeAisladoDebeEstarEnElConjuntoTest() {
		Grafo g = new Grafo(3);
		g.agregarArista(0, 1);
		assertFalse(Verificador.esDominante(g, conjunto(0)));
		assertTrue(Verificador.esDominante(g, conjunto(0, 2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void verticeInexistenteEnElConjuntoTest() {
		Verificador.esDominante(camino4(), conjunto(9));
	}

	@Test(expected = IllegalArgumentException.class)
	public void grafoNullTest() {
		Verificador.esDominante(null, conjunto());
	}
}
