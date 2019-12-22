package Model.queMePongo;

import Model.exceptions.EventoNoEncontradoException;
import Model.frecuencia.Frecuencia;
import Model.frecuencia.Unica;
import Model.tiposDeEvento.TipoEvento;
import Model.tiposDeMedioDeNotificacion.*;
import Model.tiposDeUsuario.Gratuito;
import Model.tiposDeUsuario.Premium;
import Model.tiposDeUsuario.TipoUsuario;
import Model.tiposDeUsuario.TipoUsuarioEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Data
//Sin esta forrada de aca abajo, se genera un stackOverflow mistico
@ToString(exclude = "eventos")
@Entity
@Table(name="usuario")
public class Usuario 
{

//<<<<<<<<<<<<<ATRIBUTOS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuario_guardarropa",
			joinColumns = {@JoinColumn(name = "id_usuario")},
			inverseJoinColumns = {@JoinColumn(name = "id_guardarropa")})
	private List<Guardarropas> guardarropas = new ArrayList<>();

	@Enumerated(EnumType.ORDINAL)
	private TipoUsuarioEnum tipo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id_usuario")
	private PreferenciasDTO preferencias;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id_usuario")
	private List<Evento> eventos = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "usuario_mediosenum")
	private List<MedioDeNotificacionEnum> mediosEnum = new ArrayList<>();

	@Column(name="telefono")
	private String telefono;

	@Column(name="email")
	private String email;

	@Column(name="nombre")
	private String nombre;

	@Column(name="apellido")
	private String apellido;

	@Column(name="usuario")
	private String usuario;

	@Column(name="contraseña")
	private String contraseña;



//<<<<<<<<<<<<<<METODOS>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public Usuario(){}

	public Usuario(String nombre, String apellido, String email, String telefono, MedioDeNotificacionEnum medio1, MedioDeNotificacionEnum medio2){
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
		requireNonNull(medio1, "Elegir el primer medio de notificación");
		mediosEnum.add(medio1);
		requireNonNull(medio2, "Elegir el segundo medio de notificación");
		mediosEnum.add(medio2);
		tipo = TipoUsuarioEnum.Gratuito;
		this.preferencias = null;
	}

	private TipoUsuario obtenerTipoUsuarioObjeto(TipoUsuarioEnum tipo){
		TipoUsuario resultado;

		if(tipo == TipoUsuarioEnum.Gratuito){

			resultado = new Gratuito();

		}else{
			resultado = new Premium();

		}

		return resultado;
	}

	public void setTipo(TipoUsuarioEnum tipo) {
		this.tipo = tipo;
	}

	public String getContrasenia(){
		return this.contraseña;
	}

	public void agregarGuardarropas(Guardarropas guardarropas) {
		this.guardarropas.add(guardarropas);
	}

	public void eliminarGuardarropas(Guardarropas guardarropas){
		this.guardarropas.remove(guardarropas);
	}
	
	public void agregarPrenda(Prenda prenda, Guardarropas guarda) {
		TipoUsuario tipoObjeto = obtenerTipoUsuarioObjeto(tipo);
		tipoObjeto.agregarPrenda(prenda, guarda);
	}

    public void moverPrenda(Prenda prenda, Guardarropas guardarropasOrigen, Guardarropas guardarropasDestino) {
		TipoUsuario tipoObjeto = obtenerTipoUsuarioObjeto(tipo);
		tipoObjeto.moverPrenda(prenda, guardarropasOrigen, guardarropasDestino);
    }

	public void setTelaPreferida(String tela) {
		preferencias.setTela(tela);
	}

	public void setColor1Preferido(String color) {
		preferencias.setColor1(color);
	}

	public void setColor2Preferido(String color) {
		preferencias.setColor2(color);
	}

	public void setPreferencias(PreferenciasDTO preferencias) {
		this.preferencias = preferencias;
	}

	public Evento getEvento(String descripcionEvento) throws EventoNoEncontradoException{
		if(!tieneEventoConDescripcion(descripcionEvento)){
			throw new EventoNoEncontradoException("Evento no encontrado en el calendario!");
		}
		return this.eventos.stream().filter(e -> e.getDescripcion().equals(descripcionEvento)).findFirst().get();
	}

	//--Devuelvo una lista de los eventos proximos(que esten a menos de dos dias en el futuro), antes tambien pedia
	// las sugerencias, pero prefiero que solo haga esto
	public List<Evento> eventosProximos() {
		return eventos.stream().filter(e -> e.getFrecuencia().esCercano()).collect(Collectors.toList());
	}

	//--Metodo del Command que encola las instancias de Evento
	public void agregarEvento(String descripcion, TipoEvento tipoEvento, Frecuencia frecuencia) {
		if (!this.tieneEventoConDescripcion(descripcion)){
			eventos.add(new Evento(descripcion, tipoEvento, frecuencia));
		} else {
			System.out.print("Ya existe un evento con la misma descripción. Modifiquela y vuelva a intentar.");
		}
	}

	private boolean tieneEventoConDescripcion(String descripcion) {
		return this.eventos.stream().anyMatch(e -> e.getDescripcion().equals(descripcion));
	}

	//--Metodo que pide las sugerencias de todos los eventos, no estoy seguro de que vaya porque van a romper
	// los eventos que sean a mas de 5 dias en el futuro o 2 en caso de que falle la api de openweather
	public void pedirTodasLasSugerencias() {
		eventos.forEach(evento -> {
			HashMap<Guardarropas, List<Atuendo>> sugerencias = evento.obtenerSugerencias(this.guardarropas, preferencias);
			System.out.println(sugerencias);
		});
	}

	//--Este metodo si genera las sugerencias para los eventos proximos
	public void sugerenciasDeEventosProximos() {
		this.eventosProximos().forEach(e -> e.obtenerSugerencias(this.guardarropas, preferencias));
	}

	//--Si soy gratuito, me hago premium
	public void hacersePremium(){
		this.tipo = TipoUsuarioEnum.Premium;
	}

	//--Si soy premium, me hago gratuito
	public void hacerseGratuito(){
		this.tipo = TipoUsuarioEnum.Gratuito;
	}

	public void compartirGuardarropas(Guardarropas guardarropas, Usuario usuarioParaCompartir) {
		usuarioParaCompartir.agregarGuardarropas(guardarropas);
		//Al usuario para compartir le podría aparecer un mensaje que diga que este usuario le compartira el guardarropas.
	}

	public void dejarDeCompartirGuardarropas(Guardarropas guardarropas, Usuario usuarioAEliminar) {
		usuarioAEliminar.eliminarGuardarropas(guardarropas);
		//Al usuario a eliminar le podría aparecer un mensaje que diga que este usuario le dejo de compartir el guardarropas.
	}

	private List<String> prendasAtuendo(Atuendo atuendo){
		List<String> prendasAtuendo = new ArrayList<>();
		String prenda;
		for(int i=0; i < atuendo.getPrendas().size(); i++){
			prenda = atuendo.getPrendas().get(i).getNombre();
			prendasAtuendo.add(prenda);
		}
		return prendasAtuendo;
	}

	public void aceptarAtuendo(Atuendo atuendo, String descripcionEvento) {
		try{
			Evento evento = this.getEvento(descripcionEvento);
			evento.setAtuendo(atuendo);
			String mensaje = "Aceptaste el atuendo: " + this.prendasAtuendo(atuendo).toString() + " para el evento: " + descripcionEvento;
			System.out.println(mensaje); //Para mostrar en consola
			this.serNotificado(mensaje);
		} catch (EventoNoEncontradoException e){
		System.out.println(e.getMessage());
		}
	}

	public void liberarAtuendo(Atuendo atuendo){
		atuendo.liberarAtuendo();
		String mensaje = "El atuendo: " + this.prendasAtuendo(atuendo).toString() + "fue liberado.";
		System.out.println(mensaje); //Para mostrar en consola
		//todos los usuarios que utilizan este guardarropas podrian ser notificados, para eso necesitariamos un observer
	}

	private void agregarMedioDeNotificacion(MedioDeNotificacionEnum medio) {
        this.mediosEnum.add(medio);
    }

    private void eliminarMedioDeNotificacion(MedioDeNotificacionEnum medio) {
        this.mediosEnum.remove(medio);
    }

    public void serNotificado(String mensaje){
		for (MedioDeNotificacionEnum medio : mediosEnum){
			MedioDeNotificacion a = null;
			switch (medio){
				case WhatsApp:
					a = new WhatsApp();
					break;
				case SMS:
					a = new SMS();
					break;
				case Email:
					a = new Email();
					break;
					default:
						break;
			}
			a.notificarUsuario(this, mensaje);
		};
	}

	public boolean hayNotiEmail(){
		return mediosEnum.contains(MedioDeNotificacionEnum.Email);
	}

	public boolean hayNotiSMS(){
		return mediosEnum.contains(MedioDeNotificacionEnum.SMS);
	}

	public boolean hayNotiWapp(){
		return mediosEnum.contains(MedioDeNotificacionEnum.WhatsApp);
	}

	//Si le pasas true la agrega si le pasas false la elimina
	public void modiciarNotiEmail(boolean valor){
		if(valor){
			if(!hayNotiEmail()){
				mediosEnum.add(MedioDeNotificacionEnum.Email);
			}
		}else{
			if(hayNotiEmail()){
				mediosEnum.remove(MedioDeNotificacionEnum.Email);
			}
		}
	}


	//Si le pasas true la agrega si le pasas false la elimina
	public void modiciarNotiSMS(boolean valor){
		if(valor){
			if(!hayNotiSMS()){
				mediosEnum.add(MedioDeNotificacionEnum.SMS);
			}
		}else{
			if(hayNotiSMS()){
				mediosEnum.remove(MedioDeNotificacionEnum.SMS);
			}
		}
	}


	//Si le pasas true la agrega si le pasas false la elimina
	public void modiciarNotiWapp(boolean valor){
		if(valor){
			if(!hayNotiWapp()){
				mediosEnum.add(MedioDeNotificacionEnum.WhatsApp);
			}
		}else{
			if(hayNotiWapp()){
				mediosEnum.remove(MedioDeNotificacionEnum.WhatsApp);
			}
		}
	}

	public List<Evento> getEventosProximos(){
		LocalDateTime horario = LocalDateTime.of(2019, Month.DECEMBER, 24, 10, 10, 30);
		List<Evento> listaEventos = eventos;
		return listaEventos.stream().filter( e -> e.isAfter(LocalDateTime.now())).collect(Collectors.toList());
	}

	public List<Evento> getEventosPasados(){
		LocalDateTime horario = LocalDateTime.of(2019, Month.DECEMBER, 21, 10, 10, 30);
		List<Evento> listaEventos = eventos;

		return listaEventos.stream().filter( e -> e.isBefor(LocalDateTime.now())).collect(Collectors.toList());
	}

	public List<Evento> getSugerencias(){
		LocalDateTime horario = LocalDateTime.of(2019, Month.DECEMBER, 23, 10, 10, 30);
		List<Evento> listaEventos = eventos;

		return listaEventos.stream().filter( e -> (e.isSugerencia(LocalDateTime.now()))).collect(Collectors.toList());
	}

}
