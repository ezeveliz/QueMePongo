package Model.queMePongo;

import Model.retrofit.servicesOpenWeather.MainServiceOpenWeather;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static Model.queMePongo.EstadoDeSugerencia.EXITO;
import static Model.queMePongo.EstadoDeSugerencia.PROCESANDO;

@Data
public class Sugerencias {
    private List<Guardarropas> guardarropas = new ArrayList<>();
    private int nivelRequeridoDeAbrigo;
    private PreferenciasDTO preferencias;
    private Evento evento;
    private EstadoDeSugerencia estado = PROCESANDO;
    private Pronostico pronostico = Pronostico.obtenerInstancia();

    public void setEstado(EstadoDeSugerencia _estado) {
        estado = _estado;
    }

    public Pronostico getPronostico() {
        return this.pronostico;
    }

    /**
     * Setter de temperatura en la sugerencia, ademas setea el llamado con exito
     * @param temperatura, temperatura hallada para la fecha solicitada
     */
    public void setTemperatura(Double temperatura) {
        int temp = temperatura.intValue();

        if(temp > 30) {
            this.nivelRequeridoDeAbrigo = 0;
        } else if(temp < 0) {
            this.nivelRequeridoDeAbrigo = 6;
        } else {
            this.nivelRequeridoDeAbrigo = ((30-temp)/5);
        }

        try {
            this.nivelRequeridoDeAbrigo += this.preferencias.getModificadorDeCalor();
        }catch(NullPointerException e){}

        this.estado = EXITO;
    }

    /**
     * Genero una lista de atuendos posibles para un evento dado y una con una lista de guardarropas
     * @param guardarropasDisponibles, lista de guardarropas sobre los cuales voy a buscar un atuendo
     * @param evento, evento para el cual quiero buscar los atuendos
     * @param preferencias, preferencias del usuario
     * @return retorno un hashmap formado por el guardarropas como clave y la lista de atuendos que pude encontrar en
     * cada uno, si no pude hallar la temperatura para la fecha del evento retornara un hashmap en el cual la lista de
     * atuendos estara vacia
     */
    public HashMap<Guardarropas, List<Atuendo>> obtenerSugerencias(List<Guardarropas> guardarropasDisponibles, Evento evento, PreferenciasDTO preferencias) {
        if(this.iniciarSugerencia(guardarropasDisponibles, evento, preferencias)) {
            return this.generarSugerencias();
        } else {
            return hashMapVacia(guardarropasDisponibles);
        }
    }

    /**
     * Genero un hashmap sin lista de atuendos para cumplir con el return type de obtenerSugerencia
     * @param gs, guardarropas
     * @return retorno un hashmap vacio
     */
    public static HashMap<Guardarropas, List<Atuendo>> hashMapVacia(List<Guardarropas> gs) {
        HashMap<Guardarropas, List<Atuendo>> atuendosPorGuardarropa = new HashMap<>();

        List<Atuendo> emptyList = new ArrayList<>();

        gs.forEach( g -> atuendosPorGuardarropa.put(g, emptyList));

        return atuendosPorGuardarropa;
    }

    //--Consulto en la cache y la api por la temperatura en la fecha del evento
    private boolean iniciarSugerencia(List<Guardarropas> guardarropasDisponibles, Evento evento, PreferenciasDTO preferencias){

        int diasRestantes = evento.getFrecuencia().diasRestantes();

        MainServiceOpenWeather clima = new MainServiceOpenWeather();

        if(diasRestantes <= clima.getMaxCantDias()) {
            this.guardarropas = guardarropasDisponibles;
            this.preferencias = preferencias;
            this.evento = evento;

            Double temp = this.pronostico.obtenerPronosticoPara(evento.getFrecuencia().getDate());
            if(temp.equals(Pronostico.TEMP_NO_ENCONTRADA)) {
                //TODO: verificar que la diferencia no sea negativa
                int diferenciaDeHoras = evento.getFrecuencia().diferenciaEnHorasNoAbs();
                clima.getWeatherData(diasRestantes, diferenciaDeHoras, this);
            } else {
                this.setTemperatura(temp);
            }

            return inicializacionConcluida();

        } else {
            System.out.println("Solo se pueden obtenerSugerencias atuendos con un maximo de 5 dias de anticipacion.");
            return false;
        }
    }


    /**
     * Este metodo se llama recursivamente hasta que alguna de las llamadas a la API haya retornado algo o que ambas
     * hayan fallado
     * @return true o false segun si se pudo obtener la temperatura buscada o no
     */
    private boolean inicializacionConcluida() {
        if(this.estado != PROCESANDO) {
            return this.estado == EXITO;
        }
        try {
            TimeUnit.SECONDS.sleep(1);
            return inicializacionConcluida();
        } catch (InterruptedException e) {
            return false;
        }
    }

    //--Este metodo filtra la ropa de cada guardarropa segun la categoria , nivel de calor, etc. Genera la sugerencia
    // y me devuelve una coleccion de atuendos para cada guardarropa
    private HashMap<Guardarropas, List<Atuendo>> generarSugerencias() {
        HashMap<Guardarropas, List<Atuendo>> atuendosPorGuardarropa = new HashMap<>();

        this.guardarropas.forEach( g -> {
            List<Prenda> prendas = g.getPrendas().stream().filter(Prenda::getDisponible).collect(Collectors.toList());
            List<Prenda> prendasFiltradasPorEvento = prendas.stream().filter( p -> p.getTipoEvento().equals(this.evento.getTipoDeEvento())).collect(Collectors.toList());
            List<Prenda> prendasTorso = prendasFiltradasPorEvento.stream().filter(p -> (p.getCategoria() == Categoria.Torso && p.nivelDeCalor() == 0)).collect(Collectors.toList());
            List<Prenda> prendasPiernas = prendasFiltradasPorEvento.stream().filter(p -> (p.getCategoria() == Categoria.Piernas)).collect(Collectors.toList());
            List<Prenda> prendasPies = prendasFiltradasPorEvento.stream().filter(p -> (p.getCategoria() == Categoria.Pies)).collect(Collectors.toList());

            List<Prenda> prendasDeAbrigoPorEvento = prendasFiltradasPorEvento.stream().filter(p -> p.esDeAbrigoValido(this.nivelRequeridoDeAbrigo)).collect(Collectors.toList());
            List<Prenda> accesorios = prendasFiltradasPorEvento.stream().filter(p -> p.esAccesorioValido(preferencias)).collect(Collectors.toList());

            Collection<List<Prenda>> abrigo = combinacionesAbrigo(prendasDeAbrigoPorEvento);
            Collection<List<Prenda>> atuendoBasico = permutaciones(prendasTorso, prendasPiernas, prendasPies);

            atuendosPorGuardarropa.put(g, agregarExtras(atuendoBasico, accesorios,abrigo));
        });

        System.out.println("Sugerencias:\n");
        atuendosPorGuardarropa.forEach((key, value) -> {
            System.out.println(key.toString());
            System.out.println("Atuendos:\n");
            value.forEach((a) -> {
                System.out.println(a.toString());
            });
        });

        return atuendosPorGuardarropa;
    }

    //--Esto me devuelve una coleccion de listas de abrigos que cumplen con el nivel de calor solicitado
    private Collection<List<Prenda>> combinacionesAbrigo(List<Prenda> abrigos) {

        List<Set<Prenda>> powerSetAbrigosValidos = Sets.powerSet(new HashSet<>(abrigos)).stream().filter(set -> set.stream().mapToInt(Prenda::nivelDeCalor).sum() == nivelRequeridoDeAbrigo).collect(Collectors.toList());
        Collection<List<Prenda>> combinacionesDeAbrigo = new ArrayList<>();
        powerSetAbrigosValidos.forEach(listAbrigos -> combinacionesDeAbrigo.add(Lists.newArrayList(listAbrigos)));

        return combinacionesDeAbrigo;
    }

    //--Retorno las combinaciones posibles de atuendos basicos
    private Collection<List<Prenda>> permutaciones (List<Prenda> list1, List<Prenda> list2, List<Prenda> list3){
        Collection<List<Prenda>> combinacionesDeAtuendosBasicos = new ArrayList<>();
        for (Prenda prenda : list1) {
            for (Prenda value : list2) {
                for (Prenda item : list3) {
                    List<Prenda> combinacionAAgregar = new ArrayList<>();

                    combinacionAAgregar.add(prenda);
                    combinacionAAgregar.add(value);
                    combinacionAAgregar.add(item);

                    combinacionesDeAtuendosBasicos.add(combinacionAAgregar);
                }
            }
        }

        return combinacionesDeAtuendosBasicos;
    }

    //--Creo que el nombre no es el mejor, extras parece hacer referencia a los accesorios
    //--Este metodo fusiona las listas de abrigos con las listas de atuendos basicos
    private List<Atuendo> agregarExtras(Collection<List<Prenda>> coleccionDeAtuendosBasicos, List<Prenda> accesorios, Collection<List<Prenda>> coleccionDeAbrigos) {

        List<Atuendo> atuendos = new ArrayList<>();

        if(!coleccionDeAbrigos.isEmpty()){

            Iterator<List<Prenda>> it = coleccionDeAbrigos.iterator();
            List<Prenda> abrigo = it.next();
            for (List<Prenda> prendas: coleccionDeAtuendosBasicos) {
                Atuendo atuendo = new Atuendo();
                if(!accesorios.isEmpty()){
                    atuendo.setAccesorios(generarAccesorios(accesorios));
                }
                atuendo.setPrendasbasicas(prendas);
                atuendo.setPrendasDeAbrigo(abrigo);
                atuendos.add(atuendo);
            }

        } else {
            coleccionDeAtuendosBasicos.forEach(prendasBasicas -> {
                Atuendo atuendo = new Atuendo();
                atuendo.setPrendasbasicas(prendasBasicas);
                atuendos.add(atuendo);
            });
        }
        return atuendos;
    }

    private List<Prenda> generarAccesorios(List<Prenda> accesorios){
        Prenda cabeza = accesorios.stream().filter(prenda -> prenda.getCategoria().equals(Categoria.Cabeza)).findFirst().get();
        Prenda manos = accesorios.stream().filter(prenda -> prenda.getCategoria().equals(Categoria.Manos)).findFirst().get();
        Prenda cuello = accesorios.stream().filter(prenda -> prenda.getCategoria().equals(Categoria.Cuello)).findFirst().get();

        List<Prenda> accesoriosAAgregar = new ArrayList<>();

        accesoriosAAgregar.add(cabeza);
        accesoriosAAgregar.add(manos);
        accesoriosAAgregar.add(cuello);

        return accesoriosAAgregar;
    }

    public void setTemperaturaEvento(Double temperatura) {
        evento.setTemperatura(temperatura);
    }

    //TODO: WTF estos dos metodos de abajo?
	//--Verifica la inclusión del atuendo ya aceptado para el evento en la lista de nuevas sugerencias de atuendos.
	public static HashMap<Guardarropas, List<Atuendo>> verificarAtuendoValido(Usuario usuario, Evento evento, HashMap<Guardarropas, List<Atuendo>> nuevaSugerencia, Atuendo atuendoOriginal){
		if(!Sugerencias.existeAtuendoEnSugerencia(atuendoOriginal, nuevaSugerencia)){
			String mensaje = "ALERTA! Se detectó un cambio abrupto en el clima del evento:" + evento.getDescripcion() + ". Accedé a la aplicación para cambiar tu atuendo.";
		    System.out.println(mensaje);
			usuario.serNotificado(mensaje);
			return nuevaSugerencia;
		} else {
			return Sugerencias.hashMapVacia(usuario.getGuardarropas());
		}
	}

	private static boolean existeAtuendoEnSugerencia(Atuendo atuendo, HashMap<Guardarropas, List<Atuendo>> sugerencia){
		return sugerencia.values().stream().anyMatch(s -> s.contains(atuendo));
	}
}