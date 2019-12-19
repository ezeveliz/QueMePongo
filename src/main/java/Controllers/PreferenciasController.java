package Controllers;

import Utils.Middlewares;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PreferenciasController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Preferencias");
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        parametros.put("tela", "algodon");
        parametros.put("color1", "verde");
        parametros.put("color2", "azul");
        return new ModelAndView(parametros, "Preferencias.hbs");
    }
}
