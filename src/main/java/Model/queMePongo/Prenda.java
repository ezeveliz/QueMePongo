package Model.queMePongo;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import Model.tipoDePrenda.TipoPrenda;
import Model.tiposDeEvento.TipoEvento;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name="prenda")
public class Prenda {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	@Column(name="id")
	private int id;

	@Column(name="id_guardarropa")
	private int id_guardarropa;

	@Column(name="nombre")
	private String nombre;

	@ElementCollection
	private List<Categoria> categoriasDisponibles = new ArrayList<>();

	@Enumerated(EnumType.ORDINAL)
	private Categoria categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_prenda")
	private TipoPrenda tipoDePrenda;

	@Column(name="tipo_evento")
	private TipoEvento tipoEvento;

	@Column(name="tela")
	private String tela;

	@Column(name="color_primario")
	private String colorPrimario;

	@Column(name="color_secundario")
	private String colorSecundario;

	@Column(name="foto")
	private String foto;

	@Column(name="disponible")
	private Boolean disponible;

	public Prenda(){}
	
	public Prenda(String nombrePrenda, Categoria categoriaPrenda, TipoPrenda tipoPrenda, TipoEvento tipoEvento, String telaPrenda,
					String color1, String color2, String filePath) {
		this.nombre = nombrePrenda;
		this.categoria = categoriaPrenda;
		this.tipoDePrenda = tipoPrenda;
		this.tipoEvento = tipoEvento;
		this.tela = telaPrenda;
		this.colorPrimario = color1;
		this.colorSecundario = color2; //debe ser distinto del color1
		byte[] imagen;
		this.disponible = true;
		try {
			imagen = FileUtils.readFileToByteArray(new File(filePath)); //se guarda la foto en base 64
			this.foto = Base64.getEncoder().encodeToString(imagen); //despues para mostrarla hay que decodificar https://www.baeldung.com/java-base64-image-string
		} catch (IOException e) {
			System.out.println("La imagen no puede ser cargada.");
			//e.printStackTrace();
		}
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public int nivelDeCalor(){
		return tipoDePrenda.nivelDeCalor();
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	
	public boolean esDeAbrigoValido(int nivelDeAbrigo) {
		return this.tipoDePrenda.esDeAbrigoValido(nivelDeAbrigo);
	}

	public boolean esAccesorioValido (PreferenciasDTO preferencias) {
		return (tipoDePrenda.getCategoria().equals(Categoria.Cabeza) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorCabeza()) ||
				(tipoDePrenda.getCategoria().equals(Categoria.Cuello) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorCuello()) ||
				(tipoDePrenda.getCategoria().equals(Categoria.Manos) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorManos());

	}

	public void reservarPrenda() {
		this.disponible = false;
	}

	public void liberarPrenda() {
		this.disponible = true;
	}

	public String toString(){
		return this.nombre;
	}
}
