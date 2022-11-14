package dad.javafx.partida;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class PartidaModel {
	
	private IntegerProperty puntos = new SimpleIntegerProperty();
	private IntegerProperty vida = new SimpleIntegerProperty();
	
	private StringProperty palabraResolver = new SimpleStringProperty();
	private StringProperty palabraAdivinar = new SimpleStringProperty();
	private StringProperty letrasUsadas = new SimpleStringProperty("");
	
	private ObjectProperty<Image> imagenes = new SimpleObjectProperty<>();

	public final IntegerProperty puntosProperty() {
		return this.puntos;
	}
	

	public final int getPuntos() {
		return this.puntosProperty().get();
	}
	

	public final void setPuntos(final int puntos) {
		this.puntosProperty().set(puntos);
	}
	

	public final IntegerProperty vidaProperty() {
		return this.vida;
	}
	

	public final int getVida() {
		return this.vidaProperty().get();
	}
	

	public final void setVida(final int vida) {
		this.vidaProperty().set(vida);
	}
	

	public final StringProperty palabraProperty() {
		return this.palabraResolver;
	}
	

	public final String getPalabra() {
		return this.palabraProperty().get();
	}
	

	public final void setPalabra(final String palabra) {
		this.palabraProperty().set(palabra);
	}
	

	public final StringProperty palabraAdivinarProperty() {
		return this.palabraAdivinar;
	}
	

	public final String getPalabraAdivinar() {
		return this.palabraAdivinarProperty().get();
	}
	

	public final void setPalabraAdivinar(final String palabraAdivinar) {
		this.palabraAdivinarProperty().set(palabraAdivinar);
	}
	

	public final StringProperty letrasIntentoProperty() {
		return this.letrasUsadas;
	}
	

	public final String getLetrasIntento() {
		return this.letrasIntentoProperty().get();
	}
	

	public final void setLetrasIntento(final String letrasIntento) {
		this.letrasIntentoProperty().set(letrasIntento);
	}
	

	public final ObjectProperty<Image> imagenesProperty() {
		return this.imagenes;
	}
	

	public final Image getImagenes() {
		return this.imagenesProperty().get();
	}
	

	public final void setImagenes(final Image imagenes) {
		this.imagenesProperty().set(imagenes);
	}
	

	
}
