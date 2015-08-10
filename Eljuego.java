package elJuego;

import java.awt.EventQueue;
import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;


import java.awt.GridLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Random;

import javax.swing.Action;

public class Eljuego {

	//Dimensiones del tablero
	private static final int dimensiones=100;
	private JFrame frame;
	private JButton[][] tablero;
	private JProgressBar progressBar;
	private Timer tim;
	private final Action action = new SwingAction();
	private float tiempo=0;
	private final Action action_1 = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Eljuego window = new Eljuego();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Eljuego() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("El juego de la vida");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmIniciarJuego = new JMenuItem("Iniciar Juego");
		mntmIniciarJuego.setAction(action);
		mnMenu.add(mntmIniciarJuego);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmInformacion = new JMenuItem("Informacion");
		mntmInformacion.setAction(action_1);
		mnHelp.add(mntmInformacion);
		
		progressBar = new JProgressBar();
		progressBar.setToolTipText("");
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.WHITE);
		frame.getContentPane().add(progressBar, BorderLayout.SOUTH);
		progressBar.setValue(0);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.ORANGE);
		panel_1.setBorder(new LineBorder(new Color(255, 200, 0), 5));
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(dimensiones,dimensiones));
		
		tablero=new JButton[dimensiones][dimensiones];
		
		//iniciamos todos los botones y los agregamos
		for(int i=0;i!=dimensiones;i++){
			for(int j=0;j!=dimensiones;j++){
				tablero[i][j]=new JButton("*");
				tablero[i][j].setBackground(Color.GRAY);
				panel_1.add(tablero[i][j]);
			}
		}
		
		tim = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		int cantidad;
            		
            		for(int x=0;x!=dimensiones;x++){
            			for(int y=0;y!=dimensiones;y++){
            				cantidad=0;
            				if(x-1>=0&&tablero[x-1][y].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(y-1>=0&&tablero[x][y-1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(x-1>=0&&y-1>=0&&tablero[x-1][y-1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(x+1<dimensiones&&tablero[x+1][y].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(y+1<dimensiones&&tablero[x][y+1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(x+1<dimensiones&&y+1<dimensiones&&tablero[x+1][y+1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(x-1>=0&&y+1<dimensiones&&tablero[x-1][y+1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		
                    		if(x+1<dimensiones&&y-1>=0&&tablero[x+1][y-1].getBackground()==Color.PINK){
                    			cantidad++;
                    		}
                    		if(tablero[x][y].getBackground()==Color.GRAY&&cantidad==3){
                    			tablero[x][y].setBackground(Color.PINK);
                    		}else if(cantidad==3||cantidad==2){
                    			
                    		}else{
                    			tablero[x][y].setBackground(Color.GRAY);
                    		}
                    	}
            		}
            		if(tiempo%(tiempo/100)==0){
            			progressBar.setValue(progressBar.getValue()+1);
            		}
            		tiempo--;
            		if(tiempo==0){
            			tim.stop();
            		}
            	}              
		});
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "NEW GAME");
			putValue(SHORT_DESCRIPTION, "Juego Nuevo");
		}
		public void actionPerformed(ActionEvent e) {
			float cant=0;
			int x,y;
			boolean legal;
			Random semilla=new Random();
			
			JOptionPane.showMessageDialog(frame, "Bienvenido a la mente del creador...");
			
			//Limpiar Panel
			for(int i=0;i!=dimensiones;i++){
				for(int j=0;j!=dimensiones;j++){
					tablero[i][j].setBackground(Color.GRAY);
				}
			}
			progressBar.setValue(0);
			tim.stop();
			
			//Crear un nuevo ambiente
			legal=true;
			do{
				try{
					cant=Float.parseFloat(JOptionPane.showInputDialog(frame,"Cantidad de celulas iniciales"));
					legal=true;
					if(cant<3||cant>dimensiones*dimensiones){
						legal=false;
						JOptionPane.showMessageDialog(frame, "Ingrese un numero adecuado");
					}
				}catch(Exception ex){
					legal=false;
					JOptionPane.showMessageDialog(frame, "Ingrese un numero adecuado");
				}
			}while(!legal);
				
			do{
				x=semilla.nextInt(dimensiones);
				y=semilla.nextInt(dimensiones);
				if(tablero[x][y].getBackground()==Color.GRAY){
					tablero[x][y].setBackground(Color.PINK);
					cant--;
				}
			}while(cant!=0);
			//Leer el timepo deseado para ejecutar
			legal=true;
			do{
				try{
					tiempo=Float.parseFloat(JOptionPane.showInputDialog(frame,"Tiempo en anos que desea que pase"));
					legal=true;
					if(tiempo<1||tiempo>dimensiones*dimensiones){
						legal=false;
						JOptionPane.showMessageDialog(frame, "Ingrese un numero adecuado");
					}
				}catch(Exception ex){
					legal=false;
					JOptionPane.showMessageDialog(frame, "Ingrese un numero adecuado");
				}
			}while(!legal);
			tiempo=tiempo*365;
			tim.start();
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Help");
			putValue(SHORT_DESCRIPTION, "Ayuda");
		}
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(frame, "Reglas:"
					+ "\n\n    1-Una celula nace si tiene 3\nexactamente 3 celulas que la \nrodean."
					+ "\n\n    2-Una celula muere por\nsobrepoblacion si la rodean\nmas de 3 celulas."
					+ "\n\n    3-Una celula muere por \nsoledad cuando tiene menos\nde 3 celulas que la rodean."
					+ "\n\n    4-La celulas vivas con 3 \ncelulas que las rodean vive."
					+ "\n\n    5-El juego termina si tu\nsistema se extingue o se\ntermina el tiempo indicado"
					+ "\n\nCreado por:\n    Alfredo Sebastian Rosadilla Ribeiro"
					+ "\n                                V1.0");
		}
	}
}