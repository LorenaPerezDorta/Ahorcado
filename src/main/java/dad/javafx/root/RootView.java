package dad.javafx.root;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RootView extends TabPane {
	
	private Tab partida;
	private Tab palabras;
	private Tab puntuaciones;
	
	public RootView() {
		
		partida = new Tab("PARTIDA");
		palabras= new Tab("PALABRAS");
		puntuaciones = new Tab("PUNTUACIONES");
		setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		getTabs().addAll(partida, palabras, puntuaciones);
	}

	public Tab getPartida() {
		return partida;
	}

	public Tab getPalabras() {
		return palabras;
	}

	public Tab getPuntuaciones() {
		return puntuaciones;
	}

}
