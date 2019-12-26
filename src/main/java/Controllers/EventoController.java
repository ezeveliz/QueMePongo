package Controllers;

import Model.DAO.EventoDAO;
import Model.DAO.GuardarropaDAO;
import Model.frecuencia.Frecuencia;
import Model.frecuencia.Unica;
import Model.queMePongo.Atuendo;
import Model.queMePongo.Evento;
import Model.queMePongo.Prenda;
import Model.queMePongo.Usuario;
import Model.tiposDeEvento.TipoEvento;
import Utils.Middlewares;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventoController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        Usuario user = request.session().attribute("usuario");

        //Id del evento a mostrar
        int id = Integer.parseInt(request.params("id"));

        try {
            Evento evento = EventoDAO.getEvento(id);
            String descripcion = evento.getDescripcion();
            Frecuencia idFrecuencia = evento.getFrecuencia();
            LocalDateTime fechaLDT = idFrecuencia.getInicioObject();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            String fecha = fechaLDT.format(formatter) + " hs.";
            TipoEvento tipo = evento.getTipoDeEvento();

            parametros.put("section", descripcion + " | " + fecha + " | " + tipo);
            parametros.put("idUser", user.getId());
            parametros.put("nombre", user.getNombre());
            parametros.put("apellido", user.getApellido());
            parametros.put("guardarropas", user.getGuardarropas());
            List<Atuendo> atuendos = evento.obtenerSugerencias(user.getGuardarropas(), user.getPreferencias()).values().stream().filter(x -> x.size()>0).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
            parametros.put("atuendos", atuendos);
            parametros.put("idEvento", id);
            return new ModelAndView(parametros, "Evento.hbs");
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
            response.redirect("/usuario/"+user.getId());
            return new ModelAndView(parametros, "");
        }


    }

    public Boolean asignarAtuendo(Request request, Response response) throws URISyntaxException, SQLException, IOException {
        Middlewares.authenticated(request, response);
        Map<String, Object> params = new ObjectMapper().readValue(request.body(), Map.class);
        int id = Integer.parseInt(params.get("id_evento").toString());

        Evento evento = null;
        Atuendo atuendo = new Atuendo();
        ArrayList<Object> listPrendas = (ArrayList<Object>) params.get("prendas");
        for(int i = 0; i < listPrendas.size(); i++){

            Prenda prenda = GuardarropaDAO.getPrenda(Integer.parseInt(listPrendas.get(i).toString()));

            if(prenda.getTipoDePrenda().nivelDeCalor()== 0){
                atuendo.getPrendasbasicas().add(prenda);
            }else{
                atuendo.getPrendasDeAbrigo().add(prenda);
            }
        }

        try {
            evento = EventoDAO.getEvento(id);
            evento.setAtuendo(atuendo);
            EventoDAO.modificarEvento(evento);
            return true;
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        /**
         * Obtengo los eventos del mes
         * @param request
         * @param response
         * @return json con los eventos del mes
         */
    public Object getEventos(Request request, Response response){
        Middlewares.authenticated(request, response);
        int monthNumber = Integer.parseInt(request.params("month"));
        int yearNumber = Integer.parseInt(request.params("year"));
        if (monthNumber < 0 || monthNumber > 12 || yearNumber < 0){
            monthNumber = 0;
            yearNumber = 2020;
        }
        Usuario user = request.session().attribute("usuario");
        List<Evento> eventos = new ArrayList<>();
        try {
            eventos = EventoDAO.getEvento(user);
        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }

        List<Evento> filteredEvents;
        if (!eventos.isEmpty()){
            int finalMonthNumber = monthNumber;
            int finalYearNumber = yearNumber;
            filteredEvents = eventos.stream().filter(evento -> {
                evento.createInicioObject();
                return evento.isInMonth(finalMonthNumber, finalYearNumber);
            }).collect(Collectors.toList());
        } else {
            filteredEvents = eventos;
        }
        //Esta cosa fea de aca abajo hace que no se serialize el campo usuario al transformar al evento en un json, evitando las referencias circulares
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaringClass() == Evento.class && field.getName().equals("usuario");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(strategy)
                .create();

        return gson.toJson(filteredEvents);
    }

    /**
     * Agrego un nuevo evento
     * @param request
     * @param response
     * @return confirmacion
     */
    public Object nuevoEvento(Request request, Response response) throws IOException {
        Map<String, String> params = new ObjectMapper().readValue(request.body(), Map.class);
        String fecha = params.get("fecha");
        String tipo = params.get("tipo");
        String descripcion = params.get("descripcion");
        Usuario user = request.session().attribute("usuario");


        Frecuencia frecuencia = new Unica(fecha);
        TipoEvento tipoEvento;

        switch (tipo) {
            case "casual":
                tipoEvento = TipoEvento.CASUAL;
                break;
            case "formal":
                tipoEvento = TipoEvento.FORMAL;
                break;
            case "fiesta":
                tipoEvento = TipoEvento.FIESTA;
                break;
            case "deportes":
                tipoEvento = TipoEvento.DEPORTES;
                break;
            default:
                tipoEvento = TipoEvento.CASUAL;
        }

        Evento evento = new Evento(descripcion, tipoEvento, frecuencia);
        evento.setUsuario(user);
        try{
            EventoDAO.agregarEvento(evento);
            return true;
        } catch (SQLException | URISyntaxException e) {
            System.out.println(e);
            return false;
        }
    }
}
