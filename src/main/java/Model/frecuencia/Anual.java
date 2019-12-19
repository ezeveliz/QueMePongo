package Model.frecuencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "Anual")
public class Anual extends Frecuencia {

    public Anual(LocalDateTime inicio) {
        super(inicio);
    }

    public LocalDateTime proximaRepeticion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fecha = this.fechaActualSinHora();
        LocalDateTime diaSeleccionado = this.diaSeleccionado(fecha);
        LocalDateTime fechaCorregida = this.agregarHorasMinutosYSegundos(diaSeleccionado);
        if (ahora.isBefore(fechaCorregida)) {
            return fechaCorregida;
        } else {
            return fechaCorregida.plusYears(1);
        }
    }

    public Anual(){}

    private LocalDateTime diaSeleccionado(LocalDateTime fecha) {
        if (fecha.getDayOfMonth() == this.inicio.getDayOfMonth() &&
            fecha.getMonth() == this.inicio.getMonth()) {
            return fecha;
        } else {
            return this.diaSeleccionado(fecha.plusDays(1));
        }
    }
}