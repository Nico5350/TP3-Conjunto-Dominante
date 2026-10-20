package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class GrafoTest {

	@Test
	public void grafoNuevoEstaVacioTest() {
		Grafo g = new Grafo();
		assertEquals(0, g.cantidadVertices());
		assertEquals(0, g.cantidadAristas());
	}

	@Test
	public void constructorConCantidadTest() {
		assertEquals(5, new Grafo(5).cantidadVertices());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorNegativoTest() {
		new Grafo(-1);
	}

	@Test
	public void agregarVerticeDevuelveIndiceTest() {
		Grafo g = new Grafo(2);
		assertEquals(2, g.agregarVertice());
		assertEquals(3, g.cantidadVertices());
	}

	@Test
	public void agregarAristaEsSimetricaTest() {
		Grafo g = new Grafo(3);
		g.agregarArista(0, 2);
		assertTrue(g.existeArista(0, 2));
		assertTrue(g.existeArista(2, 0));
		assertFalse(g.existeArista(0, 1));
	}

	@Test
	public void aristaRepetidaNoSeCuentaDosVecesTest() {
		Grafo g = new Grafo(3);
		g.agregarArista(0, 1);
		g.agregarArista(1, 0);
		assertEquals(1, g.cantidadAristas());
		assertEquals(1, g.grado(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void lazoTest() {
		new Grafo(3).agregarArista(1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void aristaConVerticeInexistenteTest() {
		new Grafo(3).agregarArista(0, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void aristaConVerticeNegativoTest() {
		new Grafo(3).agregarArista(-1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void gradoDeVerticeInexistenteTest() {
		new Grafo(3).grado(7);
	}

	@Test
	public void gradoTest() {
		Grafo g = new Grafo(4);
		g.agregarArista(0, 1);
		g.agregarArista(0, 2);
		g.agregarArista(0, 3);
		assertEquals(3, g.grado(0));
		assertEquals(1, g.grado(1));
	}

	@Test
	public void vecinosTest() {
		Grafo g = new Grafo(4);
		g.agregarArista(0, 1);
		g.agregarArista(0, 3);
		assertEquals(new HashSet<>(Arrays.asList(1, 3)), g.vecinos(0));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void vecinosNoSePuedeModificarTest() {
		Grafo g = new Grafo(3);
		g.vecinos(0).add(1);
	}

	@Test
	public void vecindadCerradaIncluyeAlVerticeTest() {
		Grafo g = new Grafo(3);
		g.agregarArista(0, 1);
		assertEquals(new HashSet<>(Arrays.asList(0, 1)), g.vecindadCerrada(0));
		assertEquals(new HashSet<>(Arrays.asList(2)), g.vecindadCerrada(2));
	}

	@Test
	public void vecindadCerradaEsUnaCopiaTest() {
		Grafo g = new Grafo(3);
		g.vecindadCerrada(0).add(2);
		assertEquals(0, g.grado(0));
	}

	@Test
	public void existeVerticeTest() {
		Grafo g = new Grafo(2);
		assertTrue(g.existeVertice(1));
		assertFalse(g.existeVertice(2));
		assertFalse(g.existeVertice(-1));
	}
}
