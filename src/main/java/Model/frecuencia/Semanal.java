package Model.frecuencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "Semanal")
public class Semanal extends Frecuencia{

    private DayOfWeek dia;

    public Semanal(LocalDateTime inicio, DayOfWeek dia) {
        super(inicio);
        this.dia = dia;
    }

    public Semanal(){}
    public LocalDateTime proximaRepeticion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fecha = this.fechaActualSinHora();
        LocalDateTime diaSeleccionado = this.diaSeleccionado(fecha);
        LocalDateTime fechaCorregida = this.agregarHorasMinutosYSegundos(diaSeleccionado);
        if (ahora.isBefore(fechaCorregida)) {
            return fechaCorregida;
        } else {
            return fechaCorregida.plusWeeks(1);
        }
    }

    //--Agrego dias a la fecha actual hasta que sea el mismo dia que el seleccionado
    private LocalDateTime diaSeleccionado(LocalDateTime fecha) {
        if (fecha.getDayOfWeek().equals(this.dia)) {
            return fecha;
        } else {
            return this.diaSeleccionado(fecha.plusDays(1));
        }
    }
}
