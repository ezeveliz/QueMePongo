package Model.tipoDePrenda;

import Model.queMePongo.Categoria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="tipo_prenda")
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TipoPrenda{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private int id;

	public Categoria getCategoria(){return Categoria.Cabeza;};

	public int nivelDeCalor(){return 1;};
	public boolean esDeAbrigoValido(int nivelDeAbrigo){return true;};


}


