package dad.javafx.partida;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.javafx.puntuaciones.Player;
import dad.javafx.root.RootController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class PartidaController implements Initializable {

	private static final int NUMVIDAS = 9;
	private static final int PUNTOSINICIAL = 0;
	private static final int PERFECT = 10;

	private RootController rootController;

	private ArrayList<String> palabrasLista;

	private Player jugadorActual;

	private int palabraActual = 0;

	// VIEW
	@FXML
	private GridPane view;
	@FXML
	private ImageView imagenView;
	@FXML
	private Label resolverLabel, letrasLabel, puntosLabel, vidasLabel;
	@FXML
	private TextField resolverText;
	@FXML
	private Button letraButton;
	@FXML
	private Button resolverButton;

	private PartidaModel model = new PartidaModel();
	

	public PartidaController(RootController root) throws IOException {

		this.rootController = root;

		FXMLLoader load = new FXMLLoader(getClass().getResource("/FXML/partidaView.fxml"));
		load.setController(this);
		load.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		imagenView.imageProperty().bind(model.imagenesProperty());
		puntosLabel.textProperty().bindBidirectional(model.puntosProperty(), new NumberStringConverter());
		vidasLabel.textProperty().bindBidirectional(model.vidaProperty(), new NumberStringConverter());
		letrasLabel.textProperty().bind(model.letrasIntentoProperty());
		resolverLabel.textProperty().bind(model.palabraAdivinarProperty());
		model.palabraProperty().bind(resolverText.textProperty());

		letraButton.setOnAction(event -> nuevaLetra());
		resolverButton.setOnAction(event -> intentoAdivinar());
		model.vidaProperty().addListener((v, ov, nv) -> cambioImagen());

		empezarPartida();

	}

	private void cambioImagen() {

		if (model.getVida() > 0) {
			model.setImagenes(new Image(getClass().getResource(String.format("/imagenes/%d.png", 10 - model.getVida())).toString()));
		}

	}

	private void empezarPartida() {

		model.setVida(NUMVIDAS);
		model.setPuntos(PUNTOSINICIAL);

		palabrasLista = rootController.obtenerListaPalabras();
		palabraActual = (int) (Math.random() * palabrasLista.size());
		palabraAdivinar();

	}

	private void palabraAdivinar() {

		final String palabraOrigen = palabrasLista.get(palabraActual);
		
		StringBuilder palabraAdivinar = new StringBuilder(palabraOrigen.replaceAll("[a-z,A-Z]", "_"));
		
		model.setPalabraAdivinar(palabraAdivinar.toString());

	}

	private void nuevaLetra() {

		TextInputDialog dialogo = new TextInputDialog();
		dialogo.setTitle("Letra");
		dialogo.setHeaderText("Introduce una letra");

		Optional<String> letra = dialogo.showAndWait();
		Character ch = ' ';
		if (letra.isPresent() && !letra.get().isEmpty() && !letra.get().isBlank()) {

			ch = letra.get().charAt(0);

			if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
				
				lanzarError();
				return;
			}

		} else {

			lanzarError();
			return;

		}

		String palabraOrigen = palabrasLista.get(palabraActual);

		if (model.getLetrasIntento().indexOf(ch) != -1) {
			return;
		}

		if (model.getPalabraAdivinar().indexOf(ch) != -1) {

			int n, j, i, m;
			n = j = m = 0;

			while ((i = model.getPalabraAdivinar().indexOf(ch, j)) != -1) {
				j = i + 1;
				n++;
			}

			j = 0;
			while ((i = palabraOrigen.indexOf(ch, j)) != -1) {
				j = i + 1;
				m++;
			}

			if (m == n) {
				return;
			}

		}

		if (palabraOrigen.indexOf(ch) != -1) {

			model.setPuntos(model.getPuntos() + 1);
			StringBuilder palabra = new StringBuilder(model.getPalabraAdivinar());
			int i, j;
			j = 0;
			while ((i = palabraOrigen.indexOf(ch, j)) != -1) {
				palabra.setCharAt(i, ch);
				j = i + 1;
			}

			model.setPalabraAdivinar(palabra.toString());

			if (palabra.toString().compareTo(palabraOrigen) == 0) {
				acabarJuego(false);
			}

		} else {

			model.setVida(model.getVida() - 1);

			if (model.getVida() <= 0) {

				fin();

			} else {

				model.setLetrasIntento(model.getLetrasIntento() + ch);

			}
		}

	}

	private void fin() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("FIN DEL JUEGO");
		alert.setHeaderText("Se ha acabado el juego");

		if (jugadorActual.getPuntuacion() < model.getPuntos()) {

			alert.setContentText(
					"Su puntación total ha sido de " + model.getPuntos() + "." + "\nENHORABUENA, NEW RECORD!!");

			jugadorActual.setPuntuacion(model.getPuntos());
		}

		else {

			alert.setContentText("Su puntación total ha sido de " + model.getPuntos() + ".");

		}

		alert.showAndWait();

		rootController.finPartida();

	}

	private void acabarJuego(boolean b) {

		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("FIN");

		if (!b) {

			alerta.setHeaderText("PALABRA COMPLETADA");

		} else {

			alerta.setHeaderText("PALKABRA ADIVINADA");
			model.setPuntos(model.getPuntos() + PERFECT);
		}

		alerta.showAndWait();
		palabrasLista.remove(palabrasLista.get(palabraActual));

		if (palabrasLista.size() == 0) {

			fin();

		} else {

			palabraActual = (int) (Math.random() * palabrasLista.size());
			model.setLetrasIntento("");
			palabraAdivinar();
		}

	}

	private void lanzarError() {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Letra");
		alert.setHeaderText("Letra no válida");
		alert.setContentText("La letra introducida no es válida, vuelva a intentarlo");
		alert.showAndWait();

	}

	private void intentoAdivinar() {

		if (model.getPalabra().equalsIgnoreCase(palabrasLista.get(palabraActual))) {
			resolverText.textProperty().set("");
			acabarJuego(true);

		} else {

			model.setVida(model.getVida() - 1);

			if (model.getVida() <= 0) {

				fin();

			}
		}

	}

	public GridPane getView() {
		return view;
	}

	public Player getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(Player jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

}
