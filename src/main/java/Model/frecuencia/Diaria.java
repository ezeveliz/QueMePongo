package Model.frecuencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "Diaria")
public class Diaria extends Frecuencia{

    public Diaria(LocalDateTime inicio) {
        super(inicio);
    }

    public LocalDateTime proximaRepeticion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fecha = this.fechaActualSinHora();
        LocalDateTime fechaCorregida = this.agregarHorasMinutosYSegundos(fecha);
        if (ahora.isBefore(fechaCorregida)) {
            return fechaCorregida;
        } else {
            return fechaCorregida.plusDays(1);
        }
    }

    public Diaria(){}
}
