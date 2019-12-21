package Model.tipoDePrenda;


import Model.queMePongo.Categoria;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@DiscriminatorValue(value = "Basico")
public class TipoPrendaBasico extends TipoPrenda {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="nivel_calor")
    private int nivelDeCalor = 0;

    @ElementCollection
    private List<String> telasDisponibles  = new ArrayList<>();

    @ElementCollection
    private List<String> coloresDisponibles  = new ArrayList<>();

    @ElementCollection
    private List<String> eventosDisponibles  = new ArrayList<>();

    public TipoPrendaBasico(){}

    public TipoPrendaBasico( List<String> _telasDisponibles, List<String> _coloresDisponibles, List<String> _eventosDisponibles){
        telasDisponibles = _telasDisponibles;
        coloresDisponibles = _coloresDisponibles;
        eventosDisponibles = _eventosDisponibles;
    }

    public int nivelDeCalor(){
        return 0;
    }
    
	public boolean esDeAbrigoValido(int nivelDeAbrigo) {
		return false;
	}
}
