package Model.tipoDePrenda;

import Model.queMePongo.Categoria;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue(value = "Abrigo")
public class TipoPrendaAbrigo extends TipoPrenda {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nivel_calor")
    private int nivelDeCalor;

    @ElementCollection
    private List<String> telasDisponibles  = new ArrayList<>();

    @ElementCollection
    private List<String> coloresDisponibles  = new ArrayList<>();

    @ElementCollection
    private List<String> eventosDisponibles  = new ArrayList<>();

    public TipoPrendaAbrigo(){}

    public TipoPrendaAbrigo(int _nivelDeCalor, Categoria _categoria, List<String> _telasDisponibles, List<String> _coloresDisponibles, List<String> _eventosDisponibles){
        nivelDeCalor = _nivelDeCalor;
        telasDisponibles = _telasDisponibles;
        coloresDisponibles = _coloresDisponibles;
        eventosDisponibles = _eventosDisponibles;
    }

    public int nivelDeCalor(){
        return nivelDeCalor;
    }
    
	public boolean esDeAbrigoValido(int nivelDeAbrigo) {
		return this.nivelDeCalor() <= nivelDeAbrigo;
	}

    public void setNivelDeCalor(int parametro){
        nivelDeCalor = parametro;
    }

	public void setNivelDeCalor(String parametro){
        nivelDeCalor = Integer.parseInt(parametro);
    }


}