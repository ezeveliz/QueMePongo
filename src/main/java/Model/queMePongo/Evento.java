package Model.queMePongo;

import Model.frecuencia.Frecuencia;
import Model.frecuencia.Unica;
import Model.tiposDeEvento.TipoEvento;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Entity
@Table(name="evento")
public class Evento implements Command {

    //TODO: agregar un campo que represente si el atuendo elegido es invalido porque la temperatura cambio,
    // esto debe verificarse en cada inicio de sesion del usuario

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name="descripcion")
    private String descripcion;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="tipo")
    private TipoEvento tipoDeEvento;

    @Column(name="temperatura")
    private Double temperatura;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_atuendo")
    private Atuendo atuendo = null;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_frecuencia", referencedColumnName = "id")
    private Frecuencia frecuencia;

    //--Constructor
    public Evento(){}

    public Evento(String descripcion, TipoEvento tipoEvento, Frecuencia frecuencia){
        this.descripcion = descripcion;
        this.tipoDeEvento = tipoEvento;
        this.frecuencia = frecuencia;
    }

    //--Obtengo las sugerencias para el evento
    public HashMap<Guardarropas, List<Atuendo>> obtenerSugerencias(List<Guardarropas> guardarropas, PreferenciasDTO preferencias) {
        return (new Sugerencias()).obtenerSugerencias(guardarropas, this, preferencias);
    }

    public void cambiarFrecuenciaA(Unica f) {
        this.setFrecuencia(f);
    }

    public void setAtuendo(Atuendo atuendo){
        if(this.atuendo!=null){
            this.atuendo.liberarAtuendo();
        }
        atuendo.reservarAtuendo();
        this.atuendo = atuendo;
    }

    //--Pido las sugerencias para un evento en particular (sirve para la nueva verificacion en caso de cambio abrupto)
    public HashMap<Guardarropas, List<Atuendo>> pedirSugerencias(Boolean actualizacion) {
        if (!actualizacion) {
            usuario.serNotificado(usuario.getNombre() + " " + usuario.getApellido() + ": Se han generado las sugerencias para el evento: " + descripcion + ". Accedé a la aplicación para elegir tu atuendo.");
        }

        HashMap<Guardarropas, List<Atuendo>> hashmapVacio = Sugerencias.hashMapVacia(new ArrayList<>());
        //verificar que haya al menos un guardarropa
        List<Guardarropas> guardarropasDisponibles = usuario.getGuardarropas();
        if(guardarropasDisponibles.size() != 0) {
            return this.obtenerSugerencias(guardarropasDisponibles, usuario.getPreferencias());
        } else {
            return hashmapVacio;
        }
    }


    //--Actualizar la temperatura del evento, genera una nueva sugerencia y verifica el atuendo aceptado.
    public void actualizarSugerencia(){
            Atuendo atuendoOriginal = this.getAtuendo();
            HashMap<Guardarropas, List<Atuendo>> nuevaSugerencia = pedirSugerencias(true);//llamar y pedir nueva sugerencia, que a la vez me setea la nueva temperatura en el evento
            Sugerencias.verificarAtuendoValido(usuario, this, nuevaSugerencia, atuendoOriginal);
    }

    public void createInicioObject() {
        this.frecuencia.createInicioObject();
    }

    public boolean isInMonth(int finalMonthNumber, int finalYearNumber) {
        return this.frecuencia.isInMonth(finalMonthNumber, finalYearNumber);
    }

    //TODO: hacer esta funcion que compare la fecha de la frecuencia con la fecha pasada
    public boolean isAfter(LocalDateTime fechaAComparar){
        frecuencia.createInicioObject();

        return frecuencia.getInicioObject().isAfter(fechaAComparar);
    }

    public boolean isBefor(LocalDateTime fechaAComparar){
        frecuencia.createInicioObject();

        return frecuencia.getInicioObject().isBefore(fechaAComparar);
    }

    public boolean isSugerencia(LocalDateTime fechaAComparar){
        return this.isAfter(fechaAComparar) && (atuendo == null) && this.isBefor(fechaAComparar.plusDays(5));
    }
    public boolean isEvntoProximo(LocalDateTime fechaAComparar){
        return this.isAfter(fechaAComparar) && (atuendo != null);
    }
    public String toString(){
        return " Descripcion: " + descripcion + " Fecha:" + frecuencia.getInicio().toString();
    }


}