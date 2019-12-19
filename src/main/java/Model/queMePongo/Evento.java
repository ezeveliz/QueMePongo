package Model.queMePongo;

import Model.exceptions.EventoNoEncontradoException;
import Model.frecuencia.Frecuencia;
import Model.frecuencia.Unica;
import lombok.Data;
import Model.tiposDeEvento.TipoEvento;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name="descripcion")
    private String descripcion;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="tipo")
    private TipoEvento tipoDeEvento;

    @Column(name="horario")
    private LocalDateTime horario;

    //private Frecuencia frecuencia;

    @Column(name="temperatura")
    private Double temperatura;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_atuendo")
    private Atuendo atuendo;

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

}