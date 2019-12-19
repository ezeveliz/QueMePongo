package Model.frecuencia;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("Unica")
public class Unica extends Frecuencia {

    //@Column(name="tipo")
    private String type;

    public Unica(){}
    public Unica(LocalDateTime inicio) {
        super(inicio);
    }

    public LocalDateTime proximaRepeticion() {
        return this.inicio;
    }

}
