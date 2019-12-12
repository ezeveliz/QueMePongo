package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
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
        return new ModelAndView(parametros, "Evento.hbs");
    }
}
