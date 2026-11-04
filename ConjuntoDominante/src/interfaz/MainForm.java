package interfaz;

import negocio.Grafo;
import negocio.SolverConjuntoDominante;
import negocio.SolverGoloso;
import negocio.CargadorDeGrafo;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.io.*;

public class MainForm extends JFrame {
	
	private Grafo grafo;
	private JLabel lblInfoGrafo;
	private JTextArea txtResultados;

	public MainForm() {
		grafo = new Grafo();
		initialize();
	}
	
	private void initialize() {
		setTitle("TP3 - Conjunto Dominante Minimo");
		setSize(600, 400); // 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
        
		
		JPanel panelAgregar = new JPanel();
		panelAgregar.setLayout(new BoxLayout(panelAgregar, BoxLayout.Y_AXIS));
		panelAgregar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(panelAgregar, BorderLayout.WEST);
        
		JButton botonAgregarVertice = new JButton("Agregar un Vertice");
		botonAgregarVertice.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgregar.add(botonAgregarVertice);
		panelAgregar.add(Box.createVerticalStrut(10));
        
		JButton botonAgregarArista = new JButton("Agregar una Arista");
		botonAgregarArista.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgregar.add(botonAgregarArista);
		panelAgregar.add(Box.createVerticalStrut(30));
		
		JButton botonCargarGrafo = new JButton("Cargar grafo");
		botonCargarGrafo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelAgregar.add(botonCargarGrafo);
		panelAgregar.add(Box.createVerticalStrut(30));

		JButton botonCalcular = new JButton("Calcular Conjunto Dominante");
		botonCalcular.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonCalcular.setBackground(new Color(173, 216, 230));
		panelAgregar.add(botonCalcular);

		// panel central
		JPanel panelCentro = new JPanel(new BorderLayout());
		lblInfoGrafo = new JLabel("Grafo actual: 0 vertices, 0 aristas");
		lblInfoGrafo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelCentro.add(lblInfoGrafo, BorderLayout.NORTH);

		txtResultados = new JTextArea();
		txtResultados.setEditable(false);
		panelCentro.add(new JScrollPane(txtResultados), BorderLayout.CENTER);
		add(panelCentro, BorderLayout.CENTER);

		// --- EVENTOS ---
		
		botonAgregarVertice.addActionListener(e -> {
			int nuevoVertice = grafo.agregarVertice();
			actualizarInfoGrafo();
			txtResultados.append("¡Vertice " + nuevoVertice + " Agregado!\n");
		});

		botonAgregarArista.addActionListener(e -> {
			if (grafo.cantidadVertices() < 2) {
				JOptionPane.showMessageDialog(this, "¡se necesitan al menos 2 vertices para crear una arista!");
				return;
			}
			try {
				String v1Str = JOptionPane.showInputDialog(this, "Ingrese el primer Vertice (0 a " + (grafo.cantidadVertices()-1) + "):");
				if (v1Str == null) return;
				
				String v2Str = JOptionPane.showInputDialog(this, "Ingrese el segundo Vertice (0 a " + (grafo.cantidadVertices()-1) + "):");
				if (v2Str == null) return;

				int v1 = Integer.parseInt(v1Str);
				int v2 = Integer.parseInt(v2Str);
				
				grafo.agregarArista(v1, v2);
				actualizarInfoGrafo();
				txtResultados.append("Arista agregada entre " + v1 + " y " + v2 + ".\n");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "¡Debe ingresar numeros enteros validos!");
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});

		botonCalcular.addActionListener(e -> {
			if (grafo.cantidadVertices() == 0) {
				JOptionPane.showMessageDialog(this, "El grafo este vacio...");
				return;
			}
			
			SolverConjuntoDominante solver = new SolverGoloso();
			
			// mide tiempo de ejecucion
			long startTime = System.nanoTime();
			Set<Integer> conjuntoDominante = solver.resolver(grafo);
			long endTime = System.nanoTime();
			
			double tiempoMs = (endTime - startTime) / 1_000_000.0;
			
			txtResultados.append("\n=== RESULTADO GOLOSO ===\n");
			txtResultados.append("Conjunto Dominante: " + conjuntoDominante.toString() + "\n");
			txtResultados.append("Tamaño del conjunto: " + conjuntoDominante.size() + "\n");
			txtResultados.append("Tiempo de ejecución: " + String.format("%.4f", tiempoMs) + " ms\n\n");
		});
		
		botonCargarGrafo.addActionListener(e -> {
			JFileChooser selector = new JFileChooser();
			int resultado = selector.showOpenDialog(this);
			
			if (resultado == JFileChooser.APPROVE_OPTION) {
				File archivo = selector.getSelectedFile();
				CargadorDeGrafo cargador = new CargadorDeGrafo();
				
				try {
					grafo = cargador.cargar(archivo.getAbsolutePath());
					actualizarInfoGrafo();
					JOptionPane.showMessageDialog(this, "grafo cargado");

				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(this, "no se encontro el archivo");
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage());
				}
			}
		});
	}

	private void actualizarInfoGrafo() {
		lblInfoGrafo.setText("Grafo actual: " + grafo.cantidadVertices() + " vertices, " + grafo.cantidadAristas() + " aristas");
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainForm ventana = new MainForm();
			ventana.setVisible(true);
		});
	}
}
