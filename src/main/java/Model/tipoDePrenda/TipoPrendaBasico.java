package Model.tipoDePrenda;


import Model.queMePongo.Categoria;

import java.util.List;
import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Basico")
public class TipoPrendaBasico extends TipoPrenda {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Enumerated(EnumType.ORDINAL)
    private Categoria categoria;

    @Column(name="nivel_calor")
    private int nivelDeCalor;

    @ElementCollection
    private List<String> telasDisponibles;

    @ElementCollection
    private List<String> coloresDisponibles;

    @ElementCollection
    private List<String> eventosDisponibles;

    public TipoPrendaBasico(){}

    public TipoPrendaBasico( Categoria _categoria, List<String> _telasDisponibles, List<String> _coloresDisponibles, List<String> _eventosDisponibles){
        categoria = _categoria;
        telasDisponibles = _telasDisponibles;
        coloresDisponibles = _coloresDisponibles;
        eventosDisponibles = _eventosDisponibles;
    }
    public Categoria getCategoria(){
        return categoria;
    }

    public int nivelDeCalor(){
        return 0;
    }
    
	public boolean esDeAbrigoValido(int nivelDeAbrigo) {
		return false;
	}
}
