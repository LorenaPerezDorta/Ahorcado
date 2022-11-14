package dad.javafx.root;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import dad.javafx.palabras.PalabrasController;
import dad.javafx.partida.InicioController;
import dad.javafx.partida.JugadorDialog;
import dad.javafx.partida.PartidaController;
import dad.javafx.puntuaciones.Player;
import dad.javafx.puntuaciones.PuntuacionesController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RootController {

	// VIEW
	// ----------------------------------------------------
	private RootView view;
	// ----------------------------------------------------
	// CONTROLERS
	// ----------------------------------------------------
	private PartidaController partidaController;
	private PalabrasController palabrasController;
	private PuntuacionesController puntuacionesController;
	private InicioController iniciocontroller;
	// ----------------------------------------------------
	
	private ObjectProperty<Node> juegoNode = new SimpleObjectProperty<>(); // La pantalla de inicio y de juego
	private ObjectProperty<Node> palabrasNode = new SimpleObjectProperty<>(); // La pantalla de palabras
	private ObjectProperty<Node> jugadoresNode = new SimpleObjectProperty<>(); // La pantalla de jugadores
	
	
	private BooleanProperty jugando = new SimpleBooleanProperty(false);

	public RootController () {
	
		try {
			palabrasController = new PalabrasController();
			puntuacionesController = new PuntuacionesController();
			iniciocontroller = new InicioController(this);

		} catch (IOException e) {

			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("ERROR");
			alerta.setHeaderText("La aplicaicon ha tenido un error.");
			alerta.showAndWait();
			Platform.exit();

		}


		view = new RootView();
		view.getPartida().contentProperty().bind(juegoNode);
		view.getPalabras().contentProperty().bind(palabrasNode);
		view.getPuntuaciones().contentProperty().bind(jugadoresNode);

		view.getPalabras().disableProperty().bind(jugando);
		
		juegoNode.set(iniciocontroller.getView());
		palabrasNode.set(palabrasController.getView());
		jugadoresNode.set(puntuacionesController.getView());

	}

	public void inicioPartida() {

		JugadorDialog dialogo = new JugadorDialog(puntuacionesController.listaPlayersProperty());
		Optional<Player> jugador = dialogo.showAndWait();

		if (jugador.isPresent()) {

			try {
				partidaController = new PartidaController(this);
				juegoNode.set(partidaController.getView());
				partidaController.setJugadorActual(jugador.get());
				
				jugando.set(true);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void finPartida() {

		jugando.set(false);

		try {

			iniciocontroller = new InicioController(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		juegoNode.set(iniciocontroller.getView());
		
	}

	public ArrayList<String> obtenerListaPalabras() {
		return new ArrayList<>(palabrasController.getListaPalabras());
	}

	public ArrayList<Player> obtenerListaJugadores() {
		return new ArrayList<>(puntuacionesController.getListaPlayers());
	}

	public void asignarListaJugadores(ArrayList<Player> listaJugadores) {
		puntuacionesController.setListaPlayers(FXCollections.observableArrayList(listaJugadores));
	}

	public void asignarListaPalabras(ArrayList<String> palabrasLista) {
		palabrasController.setListaPalabras(FXCollections.observableArrayList(palabrasLista));
	}
	
	

	public RootView getView() {
		
		return view;
	}

	public final ObjectProperty<Node> gameNodeProperty() {
		return this.juegoNode;
	}
	

	public final Node getGameNode() {
		return this.gameNodeProperty().get();
	}
	

	public final void setGameNode(final Node gameNode) {
		this.gameNodeProperty().set(gameNode);
	}
	

	public final ObjectProperty<Node> palabrasNodeProperty() {
		return this.palabrasNode;
	}
	

	public final Node getPalabrasNode() {
		return this.palabrasNodeProperty().get();
	}
	

	public final void setPalabrasNode(final Node palabrasNode) {
		this.palabrasNodeProperty().set(palabrasNode);
	}
	

	public final ObjectProperty<Node> jugadoresNodeProperty() {
		return this.jugadoresNode;
	}
	

	public final Node getJugadoresNode() {
		return this.jugadoresNodeProperty().get();
	}
	

	public final void setJugadoresNode(final Node jugadoresNode) {
		this.jugadoresNodeProperty().set(jugadoresNode);
	}
	

	public final BooleanProperty jugandoProperty() {
		return this.jugando;
	}
	

	public final boolean isJugando() {
		return this.jugandoProperty().get();
	}
	

	public final void setJugando(final boolean jugando) {
		this.jugandoProperty().set(jugando);
	}
	

}
