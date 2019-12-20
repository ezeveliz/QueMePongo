package Controllers;

import Model.DAO.EventoDAO;
import Model.frecuencia.Frecuencia;
import Model.frecuencia.Unica;
import Model.queMePongo.Evento;
import Model.queMePongo.Usuario;
import Model.tiposDeEvento.TipoEvento;
import Utils.Middlewares;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventoController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        //Id del evento a mostrar
        int id = Integer.parseInt(request.params("id"));
        parametros.put("section", "Evento 20/12/2019 - Formal");
        //TODO: aca colocar la id del usuario en sesion
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        return new ModelAndView(parametros, "Evento.hbs");
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
        if (monthNumber < 0 || monthNumber > 11 || yearNumber < 0){
            monthNumber = 0;
            yearNumber = 2020;
        }
        Usuario user = request.session().attribute("usuario");
        List<Evento> eventos = new ArrayList<>();
        try {
            eventos = EventoDAO.getEvento(user);
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }

        List<Evento> filteredEvents = new ArrayList<>();
        if (!eventos.isEmpty()){

        } else {
            filteredEvents = eventos;
        }

        //TODO: Obtener los eventos del mes
        return filteredEvents;
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

        LocalDateTime inicio = LocalDateTime.parse(fecha);
        Frecuencia frecuencia = new Unica(inicio);
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
        try{
            EventoDAO.agregarEvento(evento);
            return true;
        } catch (SQLException | URISyntaxException e) {
            System.out.println(e);
            return false;
        }
    }
}
