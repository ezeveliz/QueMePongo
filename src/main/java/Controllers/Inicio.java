package Controllers;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Inicio {

    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
        }
        parametros.put("logged", request.session().attribute("logged"));
        parametros.put("section", "Inicio");
        return new ModelAndView(parametros, "Inicio.hbs");
    }

    public ModelAndView iniciarSesion(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
        }
        //Si ya estoy loggeado redirijo al home
        if(request.session().attribute("logged")){

            return new ModelAndView(parametros, "Home.hbs");

        //Si habia un error de loggeo,
        } else if(request.session().attribute("logError")){
            parametros.put("logError", true);
            parametros.put("oldUser", request.session().attribute("oldUser"));
            parametros.put("oldPass", request.session().attribute("oldPass"));

        } else {

            parametros.put("oldUser", "");
            parametros.put("oldPass", "");
        }
        parametros.put("section", "Iniciar sesion");
        return new ModelAndView(parametros, "IniciarSesion.hbs");
    }

    public ModelAndView login(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String user = params.get("user");
        String password = params.get("password");

        Map<String, Object> parametros = new HashMap<>();

        //TODO: hacer un login real
        if(user.equals("admin") && password.equals("admin")){

            request.session().attribute("logged", true);
            request.session().removeAttribute("logError");
            request.session().removeAttribute("oldUser");
            request.session().removeAttribute("oldPass");
            return new ModelAndView(parametros, "Home.hbs");

        //Redirigir a home
        } else {

            //Redirigir a Iniciar sesion
            request.session().attribute("logError", true);
            request.session().attribute("oldUser", user);
            request.session().attribute("oldPass", password);
            response.redirect("/login");
            //return new ModelAndView(parametros, "IniciarSesion.hbs");
        }
        return new ModelAndView(parametros, "");
    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs) {
            map.put(pair.getName(), pair.getValue());
        }
        return map;

    }
}
