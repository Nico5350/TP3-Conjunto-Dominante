package negocio;

import static org.junit.Assert.*;

import org.junit.Test;

public class CargadorDeGrafoTest {

	@Test
	public void CargarGrafoConCincoVerticesTest() throws Exception {
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		Grafo grafo = cargador.cargar("grafo.txt");
		assertEquals(5, grafo.cantidadVertices());
	}
	
	@Test
	public void CargarGrafoConCuatroAristasTest() throws Exception {
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		Grafo grafo = cargador.cargar("grafo.txt");
		assertEquals(4, grafo.cantidadAristas());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarArchivoVacioTest() throws Exception{
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		cargador.cargar("grafoVacio.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarArchivoConVerticesNegativosTest() throws Exception{
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		cargador.cargar("grafoNegativo.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarArchivoConAristaConVerticeInexistenteTest() throws Exception{
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		cargador.cargar("grafoAristaConVerticeInexistente.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarArchivoConCantidadImparDeEnterosTest() throws Exception{
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		cargador.cargar("grafoImpar.txt");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void cargarArchivoConTextoInvalidoTest() throws Exception{
		
		CargadorDeGrafo cargador = new CargadorDeGrafo();
		cargador.cargar("grafoTextoInvalido.txt");
	}
}
