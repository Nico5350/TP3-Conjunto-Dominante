package negocio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CargadorDeGrafo {

	public Grafo cargar(String ruta) throws FileNotFoundException {
		try (Scanner lector = new Scanner(new File(ruta))) {
			if (!lector.hasNextInt())
				throw new IllegalArgumentException("el archivo debe comenzar con la cantidad de vertices");

			int cantVertices = lector.nextInt();
			if (cantVertices < 0)
				throw new IllegalArgumentException("la cantidad de vertices no puede ser negativa");

			Grafo grafo = new Grafo(cantVertices);
			while (lector.hasNext()) {
				if (!lector.hasNextInt())
					throw new IllegalArgumentException("el archivo contiene un valor que no es un entero");
				int origen = lector.nextInt();

				if (!lector.hasNextInt())
					throw new IllegalArgumentException("falta el segundo vertice de una arista");
				int destino = lector.nextInt();

				if (origen < 0 || origen >= cantVertices || destino < 0 || destino >= cantVertices)
					throw new IllegalArgumentException("La arista tiene un vertice inexistente");

				grafo.agregarArista(origen, destino);
			}
			return grafo;
		}
	}
}
