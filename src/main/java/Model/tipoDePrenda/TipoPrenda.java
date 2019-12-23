package Model.tipoDePrenda;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tipo_prenda")
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TipoPrenda{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	public int nivelDeCalor(){return 1;};
	public boolean esDeAbrigoValido(int nivelDeAbrigo){return true;};


}


