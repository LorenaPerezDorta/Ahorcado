package dad.javafx.partida;

import java.util.ArrayList;
import java.util.Optional;
import dad.javafx.puntuaciones.Player;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;

public class JugadorDialog extends Dialog<Player> {

	private Button crearButton, okButton;
	private ListView<String> playerListaView;

	private ListProperty<String> nombreJugadores = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<String>()));
	private ObservableList<Player> playerLista;
	private ListProperty<Player> players;

	public JugadorDialog(ListProperty<Player> jugadores) {

		setTitle("Jugadores");
		setHeaderText("Seleccione un jugador");

		crearButton = new Button("CREAR JUGADOR");
		playerListaView = new ListView<String>();
		playerListaView.setPrefHeight(130);

		playerLista = jugadores.get();
		players = new SimpleListProperty<Player>(playerLista);
		
		for (Player x : players.get()) {
			
			nombreJugadores.add(x.getNombre());

		}
		
		playerListaView.itemsProperty().bind(nombreJugadores);

		players.bindBidirectional(jugadores);

		HBox rootJugadores = new HBox(5, playerListaView, crearButton);
		rootJugadores.setPadding(new Insets(10));
		rootJugadores.setFillHeight(false);

		getDialogPane().setContent(rootJugadores);

		ButtonType confirmarButton = new ButtonType("ACEPTAR", ButtonData.OK_DONE);
		ButtonType cancelarButton = new ButtonType("CANCELAR", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(confirmarButton, cancelarButton);

		okButton = (Button) getDialogPane().lookupButton(confirmarButton);
		okButton.disableProperty().bind(playerListaView.getSelectionModel().selectedItemProperty().isNull());

		setResultConverter(button -> {

			if (button.getButtonData() == ButtonData.OK_DONE) {

				String nombrePlayer = playerListaView.getSelectionModel().getSelectedItem();
				return players.stream().filter(jugador -> jugador.getNombre().equals(nombrePlayer)).findFirst().get();
				
			}

			return null;

		});
		
		crearButton.setOnAction(event -> crearJugador());

	}

	private void crearJugador() {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Jugador");
		dialog.setHeaderText("Nuevo jugador");

		Optional<String> nombreJugador = dialog.showAndWait();
		
		if( nombreJugador.isPresent() && !nombreJugador.get().isBlank() && !nombreJugador.get().isEmpty()) {
			
			Player j = new Player(nombreJugador.get(), 0);
			players.add(j); 
			
			nombreJugadores.add(nombreJugador.get());
			playerListaView.getSelectionModel().clearSelection();
			playerListaView.getSelectionModel().select(nombreJugador.get());
		}
		
	}
	
	
	
	
}
