package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Inicio {

    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session().attribute("logged", false);
        }
        parametros.put("logged", request.session().attribute("logged"));
        return new ModelAndView(parametros, "Inicio.hbs");
    }

    public ModelAndView iniciarSesion(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session().attribute("logged", false);
        }
        parametros.put("logged", request.session().attribute("logged"));
        return new ModelAndView(parametros, "IniciarSesion.hbs");
    }
}
