package Model.queMePongo;

import Model.DAO.UsuarioDAO;
import Model.frecuencia.Unica;
import org.hibernate.Session;
import Model.tipoDePrenda.TipoPrendaBasico;
import Model.tiposDeEvento.TipoEvento;
import Model.tiposDeMedioDeNotificacion.Email;
import Model.tiposDeMedioDeNotificacion.MedioDeNotificacionEnum;
import Model.tiposDeMedioDeNotificacion.SMS;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import Model.hibernate.HibernateSessionFactory;
import java.util.List;


public class Main {
    public static void main(String[] args) throws ParseException, InterruptedException, URISyntaxException, SQLException {

        // obtengo la session a traves del SessionFactory
        //Session session = HibernateSessionFactory.getSession();
        // busco por primary key
        //Usuario d = (Usuario) session.get(Usuario.class, 2);
        // si lo encontre entonces muestro los datos
        //if (d != null) {
        //    System.out.print(d.getUsuario() + " " + d.getContrasenia());
        //)}
        // cierro la session
        //session.close();

        //System.out.print(System.getenv("JAWSDB_URL"));
        Usuario user ;
        UsuarioDAO u = new UsuarioDAO();
        user =u.getUsuario("admin@admin.com");

        Guardarropas guardarropaNuevo = new Guardarropas();
        guardarropaNuevo.setNombre("tuVieja");
        guardarropaNuevo.setDisponible(1);
        user.agregarGuardarropas(guardarropaNuevo);

        UsuarioDAO.modificarUsuario(user);

        //System.out.println((40-23)/6);
        //MainServiceOpenWeather clima = new MainServiceOpenWeather();
        //clima.getWeatherData(1, 3, new Sugerencias());

        //Usuario user = new Usuario();
        //Guardarropas gd = new Guardarropas();
        //user.agregarGuardarropas(gd);
        //user.agregarPrenda();

/*
        Evento ev = new Evento("bla", TipoEvento.CASUAL, LocalDateTime.now().plusDays(1));
        ev.obtenerSugerencias(new List<Guardarropas>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NotNull
            @Override
            public Iterator<Guardarropas> iterator() {
                return null;
            }

            @NotNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NotNull
            @Override
            public <T> T[] toArray(@NotNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Guardarropas guardarropas) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NotNull Collection<? extends Guardarropas> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NotNull Collection<? extends Guardarropas> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NotNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Guardarropas get(int index) {
                return null;
            }

            @Override
            public Guardarropas set(int index, Guardarropas element) {
                return null;
            }

            @Override
            public void add(int index, Guardarropas element) {

            }

            @Override
            public Guardarropas remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NotNull
            @Override
            public ListIterator<Guardarropas> listIterator() {
                return null;
            }

            @NotNull
            @Override
            public ListIterator<Guardarropas> listIterator(int index) {
                return null;
            }

            @NotNull
            @Override
            public List<Guardarropas> subList(int fromIndex, int toIndex) {
                return null;
            }
        }, new PreferenciasDTO());

        System.out.println(Pronostico.obtenerInstancia().valores);

        Pronostico pronostico = Pronostico.obtenerInstancia();

        String dt = "2019/07/26 05:00:00";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        Date dateFromString = sdf.parse(dt);


 */
        //System.out.println(pronostico.existePronosticoPara(dateFromString));

        /*
        byte[] imagen;
        String caca;
        try {
            imagen = FileUtils.readFileToByteArray(new File("src/resources/images/camisaBlanca.jpg")); //se guarda la foto en base 64
            caca = Base64.getEncoder().encodeToString(imagen); //despues para mostrarla hay que decodificar https://www.baeldung.com/java-base64-image-string
            System.out.println(caca);
        } catch (IOException e) {
            System.out.println("La imagen no puede ser cargada.");
            //e.printStackTrace();
        }

        java.util.Date time=new java.util.Date((long)1562371200*1000);
        System.out.println(time);
*/
        //System.out.println(file);

//        Usuario user = new Usuario("Juan", "Perez", "jpe@yahoo.com.ar", "+5491135613366", MedioDeNotificacionEnum.Email, MedioDeNotificacionEnum.SMS);
//        Guardarropas guardarropas1 = new Guardarropas("Tu vieja en tanga");
//        List<String> tiposTela = new ArrayList<>(Arrays.asList("Tela", "Algodon"));
//        List<String> colores = new ArrayList<>(Arrays.asList("Azul", "Rojo", "Amarillo"));
//        List<String> tiposEvento = new ArrayList<>(Arrays.asList("CASUAL"));
//        TipoPrendaBasico tipoPrenda = new TipoPrendaBasico(Categoria.Torso, tiposTela, colores, tiposEvento);
//        Prenda prendaTorso = new Prenda("Camisa blanca", Categoria.Torso, tipoPrenda, TipoEvento.CASUAL, "Algod�n", "Azul", "Azul", "src/resources/images/camisaBlanca.jpg");
//        Prenda prendaPiernas = new Prenda("Pantalon negro", Categoria.Piernas, tipoPrenda, TipoEvento.CASUAL, "Algod�n", "Negro", "Gris", "src/resources/images/pantalonNegro.jpg");
//        Prenda prendaPies = new Prenda("Zapas adidas superstar", Categoria.Pies, tipoPrenda, TipoEvento.CASUAL, "Algod�n", "Azul", "Azul", "src/resources/images/zapatillasAdidas.jpg");
//
//        user.agregarGuardarropas(guardarropas1);
//        user.agregarPrenda(prendaTorso, guardarropas1);
//        user.agregarPrenda(prendaPiernas, guardarropas1);
//        user.agregarPrenda(prendaPies, guardarropas1);
//
//        Unica unica = new Unica(LocalDateTime.now().plusDays(2).minusHours(1));
//        Evento evento = new Evento("Salida de joda", TipoEvento.CASUAL, unica);
//        PreferenciasDTO preferencias = new PreferenciasDTO();
//        Sugerencias sugerencias = new Sugerencias();
//        //sugerencias.iniciarSugerencia(user.guardarropasDisponibles(), evento, preferencias);
//
//
//        Unica unica2 = new Unica(LocalDateTime.now());
//        user.agregarEvento("Salida de joda", TipoEvento.CASUAL, unica2);
//        user.setPreferencias(preferencias);
//        user.pedirTodasLasSugerencias();

    }
}
