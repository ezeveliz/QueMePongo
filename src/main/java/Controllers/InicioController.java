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


public class InicioController {

    public ModelAndView inicio(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
        }
        parametros.put("logged", request.session().attribute("logged"));
        parametros.put("section", "Bienvenido");
        return new ModelAndView(parametros, "Inicio.hbs");
    }

    public ModelAndView loginView(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
        }
        //Si ya estoy loggeado redirijo al home
        if(request.session().attribute("logged")){

            response.redirect("/home");

        //Si habia un error de loggeo,
        } else if(request.session().attribute("logError")){
            parametros.put("logError", true);
            parametros.put("oldEmail", request.session().attribute("oldEmail"));
            parametros.put("oldPass", request.session().attribute("oldPass"));

        } else {

            parametros.put("oldEmail", "");
            parametros.put("oldPass", "");
        }
        parametros.put("section", "Iniciar sesion");
        return new ModelAndView(parametros, "IniciarSesion.hbs");
    }

    public ModelAndView login(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String email = params.get("email");
        String password = params.get("password");

        Map<String, Object> parametros = new HashMap<>();

        //TODO: hacer un login real
        if(email.equals("admin@admin.com") && password.equals("admin")){

            request.session().attribute("logged", true);
            request.session().removeAttribute("logError");
            request.session().removeAttribute("oldEmail");
            request.session().removeAttribute("oldPass");
            response.redirect("/home");

        //Redirigir a home
        } else {

            //Redirigir a Iniciar sesion
            request.session().attribute("logError", true);
            request.session().attribute("oldEmail", email);
            request.session().attribute("oldPass", password);
            response.redirect("/login");
        }
        return new ModelAndView(parametros, "");
    }

    public ModelAndView logout(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        request.session().invalidate();
        response.redirect("/");
        return new ModelAndView(parametros, "");
    }

    public ModelAndView registerView(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Registrarse");
        return new ModelAndView(parametros, "Register.hbs");
    }

    public ModelAndView register(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "");
    }

    public ModelAndView notFound(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Error 404");
        return new ModelAndView(parametros, "404.hbs");
    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs) {
            map.put(pair.getName(), pair.getValue());
        }
        return map;

    }
}
