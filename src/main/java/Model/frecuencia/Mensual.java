package Model.frecuencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "Mensual")
public class Mensual extends Frecuencia{

    private int dia;

    public Mensual(LocalDateTime inicio, int dia) {
        super(inicio);
        this.dia = dia;
        this.validarDia();
    }

    public Mensual(){}
    //--Valido que el dia ingresado se encuentre entre 1 y 31, caso contrario lo adapto a alguno de estos 2
    private void validarDia() {
        if(this.dia < 1){
            System.out.println("Has ingresado un numero menor a 1, el dia del mes sera seteado como 1.");
            this.dia = 1;
        } else if(this.dia > 31){
            System.out.println("Has ingresado un numero mayor a 31, el dia del mes sera seteado como 31 para los meses correspondientes.");
            this.dia = 31;
        }
    }

    public LocalDateTime proximaRepeticion() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fecha = this.fechaActualSinHora();
        LocalDateTime diaSeleccionado = this.diaSeleccionado(fecha);
        LocalDateTime fechaCorregida = this.agregarHorasMinutosYSegundos(diaSeleccionado);
        if (ahora.isBefore(fechaCorregida)) {
            return fechaCorregida;
        } else {
            return fechaCorregida.plusMonths(1);
        }
    }

    private LocalDateTime diaSeleccionado(LocalDateTime fecha) {
        if (fecha.getDayOfMonth() == this.dia) {
            return fecha;
        } else {
            return this.diaSeleccionado(fecha.plusDays(1));
        }
    }
}
