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

}
