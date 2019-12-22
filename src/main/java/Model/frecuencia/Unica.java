package Model.frecuencia;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
@DiscriminatorValue("Unica")
public class Unica extends Frecuencia {

    @Column(name="tipo" , insertable = false, updatable = false)
    private String type;

    public Unica(){}
    public Unica(String  inicio) {
        super(inicio);
    }

    public LocalDateTime proximaRepeticion() {
        return this.inicioObject;
    }

}
