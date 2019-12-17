package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
    public ModelAndView inicio(Request request, Response response) {
        Middlewares.authenticated(request, response);
        //Id del usuario a mostrar(el pasado por parametro)
        int id = Integer.parseInt(request.params("id"));
        //TODO: verificar que el id del usuario en la sesion y el id de la url sean el mismo, sino invalidar la sesion y mandar al login

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Inicio");
        parametros.put("idUser", id);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        return new ModelAndView(parametros, "Usuario.hbs");
    }
}
