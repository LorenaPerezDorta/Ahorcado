package dad.javafx.palabras;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class PalabrasController implements Initializable {
	
	
	@FXML
	private GridPane view;
	@FXML
	private ListView<String> palabrasList;
	@FXML
	private Button añadirButton,quitarButton;
	
	private ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());
	private ListProperty<String> listaPalabras =  new SimpleListProperty<>(observableList);
	
	private StringProperty palabraSelecciona = new SimpleStringProperty();
	
	
	public PalabrasController() throws IOException {
		
		FXMLLoader load = new FXMLLoader(getClass().getResource("/FXML/palabrasView.fxml"));
		load.setController(this);
		load.load();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		palabrasList.itemsProperty().bind(listaPalabras);
		palabraSelecciona.bind(palabrasList.getSelectionModel().selectedItemProperty());
		quitarButton.disableProperty().bind(palabrasList.getSelectionModel().selectedItemProperty().isNull());

		añadirButton.setOnAction(event -> onAñadirButton(event));
		quitarButton.setOnAction(event -> onQuitarButton(event));
		
		
	}


	private void onAñadirButton(ActionEvent event) {
		
		TextInputDialog dialogo = new TextInputDialog();
		dialogo.setTitle("Nueva palabra");
		dialogo.setHeaderText("Introduzca una nueva palabra");
		dialogo.setContentText("La palabra debe contener minimo 2 caracteres");
		
		Optional<String> palabra = dialogo.showAndWait();

		if( palabra.isPresent() && palabra.get().length() < 2) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Palabra incorrecta");
			alert.setHeaderText("La palabra debe contener un minimo de 2 caracteres");
			alert.showAndWait();
			
		} else if( palabra.isPresent() ) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Palabra correcta");
			alert.setHeaderText("La palabra se ha guardado");
			alert.showAndWait();
		}
		
		
	}

	private void onQuitarButton(ActionEvent event) {
		
		listaPalabras.remove(palabraSelecciona.get());
		
	}

	public GridPane getView() {

		return view;
	}

	public final ListProperty<String> listaPalabrasProperty() {
		return this.listaPalabras;
	}
	

	public final ObservableList<String> getListaPalabras() {
		return this.listaPalabrasProperty().get();
	}
	

	public final void setListaPalabras(final ObservableList<String> listaPalabras) {
		this.listaPalabrasProperty().set(listaPalabras);
	}
	

	public final StringProperty palabraSeleccionaProperty() {
		return this.palabraSelecciona;
	}
	

	public final String getPalabraSelecciona() {
		return this.palabraSeleccionaProperty().get();
	}
	

	public final void setPalabraSelecciona(final String palabraSelecciona) {
		this.palabraSeleccionaProperty().set(palabraSelecciona);
	}
	

	
	
	
	
	

}
