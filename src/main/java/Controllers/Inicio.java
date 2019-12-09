package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Inicio {

    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "Inicio.hbs");
    }
}
