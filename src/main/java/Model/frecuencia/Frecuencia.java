package Model.frecuencia;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Table(name="frecuencia")
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Frecuencia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="inicio")
    protected LocalDateTime inicio;

    public Frecuencia(){}

    public Frecuencia(LocalDateTime inicio){
        this.inicio = inicio;
    }


    //--Me indica si un evento es cercano a una fecha dada teniendo en cuenta si el mismo se repite y cuando lo hace
    public boolean esCercano(){
        return this.diferenciaEnHoras(LocalDateTime.now()) < 2;
    }

    //--Me da la distancia entre hoy y la fecha de inicio
    public int diasRestantes() {
        return (int)this.diferenciaEnDias(LocalDateTime.now());
    }

    //--Retorno la ultima repeticion de la frecuencia
    public abstract LocalDateTime proximaRepeticion();

    //--Obtengo la diferrencia de tiempo entre la fecha del evento y la fecha mas proxima calculada de la frecuencia
    private long diferenciaEnHoras(LocalDateTime fechaAProbar){
        return diferenciaEn(fechaAProbar, ChronoUnit.HOURS);
    }

    //--Obtengo la diferencia(No absoluta) entre la fecha del evento y la ultima repeticion en horas
    public int diferenciaEnHorasNoAbs(){
        return (int)LocalDateTime.now().until(this.proximaRepeticion(), ChronoUnit.HOURS);
    }

    //--Retorno la diferencia entre una fecha dada y la ultima repeticion en dias
    private long diferenciaEnDias(LocalDateTime fechaAProbar){
        return diferenciaEn(fechaAProbar, ChronoUnit.DAYS);
    }

    //--Retorno la diferencia entre una fecha dada y la de la ultima repeticion en la unidad indicada
    private long diferenciaEn(LocalDateTime fechaAProbar, ChronoUnit unidad){
        long diferencia = fechaAProbar.until(this.proximaRepeticion(), unidad);
        return Math.abs(diferencia);
    }

    private int hora(){
        return inicio.getHour();
    }

    private int minuto(){
        return inicio.getMinute();
    }

    private int segundo(){
        return inicio.getSecond();
    }

    private int nano(){
        return inicio.getNano();
    }

    protected LocalDateTime fechaActualSinHora(){
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime temp = ahora;
        temp = temp.minusHours(ahora.getHour());
        temp = temp.minusMinutes(ahora.getMinute());
        temp = temp.minusSeconds(ahora.getSecond());
        temp = temp.minusNanos(ahora.getNano());
        return temp;
    }

    protected LocalDateTime agregarHorasMinutosYSegundos(LocalDateTime fecha) {
        fecha = fecha.plusHours(this.hora());
        fecha = fecha.plusMinutes(this.minuto());
        fecha = fecha.plusSeconds(this.segundo());
        fecha = fecha.plusNanos(this.nano());
        return fecha;
    }

    //--Obtengo la fecha en formato Date TODO:tratar de reemplazar date por localDateTime
    public Date getDate() {
        return java.util.Date.from(this.proximaRepeticion().atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
