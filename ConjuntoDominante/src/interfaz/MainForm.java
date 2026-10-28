package interfaz;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;

public class MainForm extends JFrame {
	
	public MainForm() {
		initialize();
	}
	
	private void initialize() {
		setTitle("TP3 - Conjunto Dominante");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panelAgregar = new JPanel();
        panelAgregar.setLayout(new BoxLayout(panelAgregar, BoxLayout.Y_AXIS));
        add(panelAgregar, BorderLayout.WEST);
        
        JButton botonAgregarVertice = new JButton("Agrega un vertice");
        panelAgregar.add(botonAgregarVertice);
        
        panelAgregar.add(Box.createVerticalStrut(7));
        
        JButton botonAgregarArista = new JButton("Agrega una arista");
        panelAgregar.add(botonAgregarArista);
        
        JPanel panelGrafo = new JPanel();
        add(panelGrafo, BorderLayout.CENTER);
        
	}
	
	public static void main(String[] args) {
		MainForm ventana = new MainForm();
		ventana.setVisible(true);
	}
	
	

}
