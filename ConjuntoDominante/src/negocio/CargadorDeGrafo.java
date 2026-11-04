package negocio;

import java.io.*;
import java.util.*;

public class CargadorDeGrafo {
	
	public Grafo cargar(String ruta) throws FileNotFoundException {
		File archivo = new File(ruta);
		Scanner lector = new Scanner(archivo);
		if (!lector.hasNextInt()) {
			lector.close();
			throw new IllegalArgumentException("el archivo debe comenzar con la cantidad de vertices");
		}
		
		int cantVertices = lector.nextInt();
		
		if (cantVertices < 0) {
			lector.close();
			throw new IllegalArgumentException("la cantidad de vertices no puede ser negativa");
		}
		Grafo grafo = new Grafo(cantVertices);
		while (lector.hasNextInt()) {
			int origen = lector.nextInt();
			int destino = lector.nextInt();
			
			if(origen <0 || origen >= cantVertices || destino <0 || destino >= cantVertices) {
				lector.close();
				throw new IllegalArgumentException("La arista tiene un vertice inexistente");
			}
			
			grafo.agregarArista(origen, destino);
		}
		
		lector.close();
		return grafo;
	}
	

}
