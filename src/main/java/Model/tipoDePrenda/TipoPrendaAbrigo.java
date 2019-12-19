package Model.tipoDePrenda;


import Model.queMePongo.Categoria;

import java.util.List;
import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "Abrigo")
public class TipoPrendaAbrigo extends TipoPrenda {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    public TipoPrendaAbrigo(){}

    public TipoPrendaAbrigo(int _nivelDeCalor, Categoria _categoria, List<String> _telasDisponibles, List<String> _coloresDisponibles, List<String> _eventosDisponibles){
        categoria = _categoria;
        nivelDeCalor = _nivelDeCalor;
        telasDisponibles = _telasDisponibles;
        coloresDisponibles = _coloresDisponibles;
        eventosDisponibles = _eventosDisponibles;
    }
    public Categoria getCategoria(){
        return categoria;
    }

    public int nivelDeCalor(){
        return nivelDeCalor;
    }
    
	public boolean esDeAbrigoValido(int nivelDeAbrigo) {
		return this.nivelDeCalor() <= nivelDeAbrigo;
	}
}