package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class GuardarropaController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        int id = Integer.parseInt(request.params("id"));
        parametros.put("section", "Guardarropa Pepe");
        return new ModelAndView(parametros, "Guardarropa.hbs");
    }
}
