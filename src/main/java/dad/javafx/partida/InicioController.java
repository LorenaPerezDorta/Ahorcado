package dad.javafx.partida;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.root.RootController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InicioController implements Initializable {

	private RootController root;

	// VIEW
	@FXML
	private VBox view;
	@FXML
	private Label textoLabel;
	@FXML
	private Button empezarButton;

	public InicioController(RootController rootController) throws IOException {

		this.root = rootController;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/inicioView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		textoLabel.setText("ELEGIR O CREAR UN USUARIO PARA PODER JUGAR");

		empezarButton.setOnAction(event -> onIniciarAction());

	}

	private void onIniciarAction() {

		root.inicioPartida();

	}

	public VBox getView() {

		return view;
	}

}
