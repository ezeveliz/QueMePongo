package Controllers;

import Utils.Middlewares;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class GuardarropaController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        //Id del guardarropa a mostrar
        int id = Integer.parseInt(request.params("id"));
        parametros.put("section", "Guardarropa Pepe");
        //TODO: aca colocar la id del usuario en sesion
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        return new ModelAndView(parametros, "Guardarropa.hbs");
    }
}
