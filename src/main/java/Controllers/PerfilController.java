package Controllers;

import Utils.Middlewares;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PerfilController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Perfil");
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        parametros.put("telefono", 12345678);
        parametros.put("email", "admin@admin.com");
        parametros.put("notiEmail", true);
        parametros.put("notiSms", false);
        parametros.put("notiWapp", true);
        return new ModelAndView(parametros, "Perfil.hbs");
    }
}
