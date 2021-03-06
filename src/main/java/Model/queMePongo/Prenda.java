package Model.queMePongo;

import Model.tipoDePrenda.TipoPrenda;
import Model.tiposDeEvento.TipoEvento;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

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

	@Enumerated(EnumType.ORDINAL)
	private Categoria categoria;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
	private Integer disponible;

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
		this.disponible = 1;
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
		return (this.getCategoria().equals(Categoria.Cabeza) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorCabeza()) ||
				(this.getCategoria().equals(Categoria.Cuello) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorCuello()) ||
				(this.getCategoria().equals(Categoria.Manos) && tipoDePrenda.nivelDeCalor() >= preferencias.getCalorManos());

	}

	public void reservarPrenda() {
		this.disponible = 0;
	}

	public void liberarPrenda() {
		this.disponible = 1;
	}

	public String toString(){
		return this.nombre;
	}

	public void tipoDePendaDeportiva(Boolean valor){
		if(valor) {
			tipoEvento = TipoEvento.DEPORTES;
		}
	}
	public void tipoDePendaCasual(Boolean valor){
		if(valor) {
			tipoEvento = TipoEvento.CASUAL;
		}
	}
	public void tipoDePendaFiesta(Boolean valor){
		if(valor) {
			tipoEvento = TipoEvento.FIESTA;
		}
	}
	public void tipoDePendaFormal(Boolean valor){
		if(valor) {
			tipoEvento = TipoEvento.FORMAL;
		}
	}

	public Boolean getDisponible(){
		return disponible == 1;
	}
}
