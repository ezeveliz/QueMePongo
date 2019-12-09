package Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Home {
    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
            response.redirect("/login");
        }
        parametros.put("section", "Inicio");
        return new ModelAndView(parametros, "Home.hbs");
    }
}
