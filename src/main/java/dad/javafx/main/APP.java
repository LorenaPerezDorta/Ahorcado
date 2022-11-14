package dad.javafx.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import dad.javafx.puntuaciones.Player;
import dad.javafx.root.RootController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class APP extends Application {
	
	RootController root = new RootController();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(root.getView(),640,480);
		primaryStage.setResizable(false);
		primaryStage.setTitle("AHORCADO");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		
		cargarJugadores();
		cargarPalabras();
		
	}
	
	private void cargarPalabras() {
		
		FileInputStream ficheroInput = null;
		InputStreamReader inReader = null;
		BufferedReader buffReader = null;
		ArrayList<String> listaPalabras = new ArrayList<>();
		
		try {
			
			ficheroInput = new FileInputStream(getClass().getResource("/ficheros/palabras.txt").getFile());
			inReader = new InputStreamReader(ficheroInput, StandardCharsets.UTF_8);
			buffReader = new BufferedReader(inReader);
			
			String linea;
			
			while( (linea = buffReader.readLine()) != null ) {
				
				linea.trim();
				listaPalabras.add(linea);
			}
			
			root.asignarListaPalabras(listaPalabras);
			
		} catch (IOException | NumberFormatException e) {
			
			lanzarErrorCargar("/ficheros/palabras.txt");
			
		} finally {
			
			try {	
				
				if( buffReader != null )
					buffReader.close();
				
				if( inReader != null ) {
					inReader.close();
				}
				
				if( ficheroInput != null )
					ficheroInput.close();
				
			} catch (IOException e) {
				
				lanzarErrorCargar("/ficheros/palabras.txt");
				
			}
		}
		
	}

	private void cargarJugadores() {
		
		
		FileInputStream ficheroInput = null;
		InputStreamReader inReader = null;
		BufferedReader buffReader = null;
		ArrayList<Player> listaJugadores = new ArrayList<>();
		
		try {
			
			ficheroInput = new FileInputStream(getClass().getResource("/ficheros/puntuaciones.txt").getFile());
			inReader = new InputStreamReader(ficheroInput, StandardCharsets.UTF_8);
			buffReader = new BufferedReader(inReader);
			
			String linea;
			
			while( (linea = buffReader.readLine()) != null ) {
				
				linea.trim();
				String [] jugadorTabla = linea.split(",");
				listaJugadores.add(new Player(jugadorTabla[0], Integer.parseInt(jugadorTabla[1])));
			}
			
			root.asignarListaJugadores(listaJugadores);
			
		} catch (IOException | NumberFormatException e) {
			
			lanzarErrorCargar("/ficheros/puntuaciones.txt");
			
		} finally {
			
			try {	
				
				if( buffReader != null )
					buffReader.close();
				
				if( inReader != null ) {
					inReader.close();
				}
				
				if( ficheroInput != null )
					ficheroInput.close();
				
			} catch (IOException e) {
				
				lanzarErrorCargar("/ficheros/puntuaciones.txt");
				
			}
		}
		
	}
	
	private void lanzarErrorCargar(String string) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText("Error al cargar el fichero:" + string);
		alert.setContentText("Compruebe que la ruta del fichero es correcta o si existe el fichero");
		alert.showAndWait();
		
		
	}

	@Override
	public void stop() throws Exception {
		
		super.stop();
		
		guardarPalabras();
		guardarJugadores();
	}

	private void guardarJugadores() {
		
		FileOutputStream ficheroOutput = null;
		OutputStreamWriter outWritter = null;
		BufferedWriter buffWriter = null;
		
		try {
			
			ficheroOutput = new FileOutputStream(getClass().getResource("/ficheros/puntuaciones.txt").getFile());
			outWritter = new OutputStreamWriter(ficheroOutput, StandardCharsets.UTF_8);
			buffWriter = new BufferedWriter(outWritter);
			
			for( Player jugador : root.obtenerListaJugadores()) {
				buffWriter.write(jugador.getNombre() + "," + jugador.getPuntuacion());
				buffWriter.newLine();
			}
			
		} catch (IOException e) {
			
			lanzarErrorGuardar("/ficheros/puntuaciones.txt");

		} finally {
			
			try {	
				if( buffWriter != null )
					buffWriter.close();
				
				if( outWritter != null )
					outWritter.close();
				
				if( ficheroOutput != null )
					ficheroOutput.close();
				
			} catch (IOException e) {
				
				lanzarErrorGuardar("/ficheros/puntuaciones.txt");
				
			}
		}
		
	}

	private void lanzarErrorGuardar(String string) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText("Error al guardar el fichero:" + string);
		alert.setContentText("Compruebe que la ruta del fichero es correcta o el fichero no este abierto por otro programa");
		alert.showAndWait();
		
	}

	private void guardarPalabras() {
		
		FileOutputStream ficheroOnput = null;
		OutputStreamWriter outWritter = null;
		BufferedWriter buffWriter = null;
		
		try {
			
			ficheroOnput = new FileOutputStream(getClass().getResource("/ficheros/palabras.txt").getFile());
			outWritter = new OutputStreamWriter(ficheroOnput, StandardCharsets.UTF_8);
			buffWriter = new BufferedWriter(outWritter);
			

			for( String palabras : root.obtenerListaPalabras()) {
				
				buffWriter.write(palabras.toUpperCase());
				buffWriter.newLine();
			}
			
		} catch (IOException e) {
			
			lanzarErrorGuardar("/ficheros/palabras.txt");
			
		} finally {
			
			try {	
				if( buffWriter != null )
					buffWriter.close();
				
				if( outWritter != null )
					outWritter.close();
				
				if( ficheroOnput != null )
					ficheroOnput.close();
				
			} catch (IOException e) {
				
				lanzarErrorGuardar("/ficheros/palabras.txt");
				
			}
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
