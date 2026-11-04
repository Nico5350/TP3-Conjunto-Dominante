# Formato de los archivos de grafo

La aplicación puede cargar un grafo desde un archivo de texto plano (`.txt`) con el botón **Cargar grafo**. Este documento describe el formato que debe tener ese archivo.

## Estructura

```
<cantidad de vertices>
<origen> <destino>
<origen> <destino>
...
```

- El **primer valor** del archivo es la cantidad de vértices del grafo.
- A continuación viene una **arista por línea**, escrita como dos enteros separados por un espacio: `origen destino`.
- Los vértices se numeran desde **0** hasta **cantidad de vértices − 1**.
- Un archivo puede no tener aristas: en ese caso el grafo queda con todos los vértices aislados.


## Ejemplo válido

El grafo de la Figura 1 de la consigna, con los vértices 1 a 6 renumerados como 0 a 5:

```
6
5 3
3 4
3 2
4 0
4 1
0 1
1 2
```

Un grafo de 5 vértices en forma de camino:

```
5
0 1
1 2
2 3
3 4
```

## Mensajes de error

Si el archivo no cumple el formato, la aplicación muestra un cuadro de diálogo con el motivo y no modifica el grafo que ya estaba cargado:

| Situación | Mensaje |
|---|---|
| Archivo vacío o sin cantidad de vértices al inicio | el archivo debe comenzar con la cantidad de vertices |
| Cantidad de vértices negativa | la cantidad de vertices no puede ser negativa |
| Valor que no es un entero | el archivo contiene un valor que no es un entero |
| Arista sin segundo extremo | falta el segundo vertice de una arista |
| Vértice fuera de rango | La arista tiene un vertice inexistente |
| Lazo | No se permiten lazos: *n* |
| El archivo no existe | no se encontro el archivo |

## Archivos de prueba

Los tests de `CargadorDeGrafoTest` leen estos archivos desde la raíz del proyecto, así que deben estar presentes al ejecutarlos:

| Archivo | Qué prueba |
|---|---|
| `grafo.txt` | Grafo válido de 5 vértices y 4 aristas |
| `grafoVacio.txt` | Archivo sin contenido |
| `grafoNegativo.txt` | Cantidad de vértices negativa |
| `grafoAristaConVerticeInexistente.txt` | Arista con un vértice fuera de rango |
| `grafoImpar.txt` | Cantidad impar de enteros |
| `grafoTextoInvalido.txt` | Texto no numérico entre las aristas |
