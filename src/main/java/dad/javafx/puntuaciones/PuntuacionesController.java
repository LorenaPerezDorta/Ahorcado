package dad.javafx.puntuaciones;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class PuntuacionesController implements Initializable {
		
	// VIEW
	@FXML
	private GridPane view;
	@FXML
	private TableView<Player> tablaView;
	@FXML
	private TableColumn<Player, String> jugador;
	@FXML
	private TableColumn<Player, String> puntuacion;
	
	private ObservableList<Player> listaPuntuaciones = FXCollections.observableArrayList(new ArrayList<Player>());
	private ListProperty<Player> listaPlayers =  new SimpleListProperty<>(listaPuntuaciones);;

	

	public PuntuacionesController() throws IOException {

		FXMLLoader load = new FXMLLoader(getClass().getResource("/FXML/puntuacionesView.fxml"));
		load.setController(this);
		load.load();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tablaView.itemsProperty().bind(listaPlayers);
		jugador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		puntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));

	}
	
	public GridPane getView() {

		return view;
	}

	public final ListProperty<Player> listaPlayersProperty() {
		return this.listaPlayers;
	}
	

	public final ObservableList<Player> getListaPlayers() {
		return this.listaPlayersProperty().get();
	}
	

	public final void setListaPlayers(final ObservableList<Player> listaPlayers) {
		this.listaPlayersProperty().set(listaPlayers);
	}
	

	
	

}
