package negocio;

import java.io.*;
import java.util.*;

public class CargadorDeGrafo {
	
	public Grafo cargar(String ruta) throws FileNotFoundException {
		File archivo = new File(ruta);
		Scanner lector = new Scanner(archivo);
		
		int cantVertices = lector.nextInt();
		Grafo grafo = new Grafo(cantVertices);
		while (lector.hasNextInt()) {
			int origen = lector.nextInt();
			int destino = lector.nextInt();
			grafo.agregarArista(origen, destino);
		}
		
		lector.close();
		return grafo;
	}
	

}
