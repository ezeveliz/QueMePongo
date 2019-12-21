package Model.queMePongo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name="guardarropa")
public class Guardarropas {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	public Guardarropas(String _nombre){
		nombre = _nombre;
	}


	@OneToMany (cascade = CascadeType.ALL)
	@JoinColumn(name="id_guardarropa")
	private List<Prenda> prendas = new ArrayList<>();

	@Column(name="nombre")
	private String nombre;
	//qué significan los demas atributos que aparecen en el diagrama de clases?

	@Column(name="disponible")
	private int disponible;

	public Guardarropas(){}

	public int cantidadDePrendas() {
		return prendas.size();
	}
	
	public void agregarPrenda(Prenda prenda) {
		this.prendas.add(prenda);
	}
	
	public void removerPrenda(Prenda prenda) {
		this.prendas.remove(prenda);
	}

	public String toString(){
		StringBuilder g = new StringBuilder("\n\nNombre de Guardarropa: " + nombre + "\n");
		g.append("....Prendas: ");
		for (Prenda prenda : prendas){
			g.append(prenda.getNombre()).append(", ");
		}
		String guardarropa = g.toString();
		return guardarropa.substring(0, guardarropa.length() - 2) + "\n\n";
	}
}
