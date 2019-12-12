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

    /**
     * Muestro la pagina ppal
     * @param request request
     * @param response response
     * @return vista de la pagina ppal
     */
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

    /**
     * Muestro la vista para hacer el login
     * @param request request
     * @param response response
     * @return vista del login
     */
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

    /**
     * Verifico si los datos ingresados coinciden con algun usuario del sistema y redirijo a donde corresponda
     * @param request request
     * @param response response
     * @return vista del home si los datos corresponden, vista de login si no
     */
    public Object login(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String email = params.get("email");
        String password = params.get("password");

        //TODO: hacer un login real
        if(email.equals("admin@admin.com") && password.equals("admin")){

            request.session().attribute("logged", true);
            request.session().removeAttribute("logError");
            request.session().removeAttribute("oldEmail");
            request.session().removeAttribute("oldPass");
            //TODO: cargar al usuario en la sesion y redirigir a /usuario/id
            response.redirect("/usuario/1");

        //Redirigir a home
        } else {

            //Redirigir a Iniciar sesion
            request.session().attribute("logError", true);
            request.session().attribute("oldEmail", email);
            request.session().attribute("oldPass", password);
            response.redirect("/login");
        }
        return response;
    }

    /**
     * Delogueo al usuario en sesion
     * @param request request
     * @param response response
     * @return redirijo a la pagina de login
     */
    public Object logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/");
        return response;
    }

    /**
     * Muestro la vista para registrar un nuevo usuario
     * @param request request
     * @param response response
     * @return vista de registro
     */
    public ModelAndView registerView(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Registrarse");
        return new ModelAndView(parametros, "Register.hbs");
    }

    /**
     * Agrego al nuevo usuario a la DB
     * @param request request
     * @param response response
     * @return redirijo al home
     */
    public Object register(Request request, Response response) {
        return response;
    }

    /**
     * Si el usuario quiere navegar a una pagina que no existe, se redirije a una pagina especial que le informa
     * @param request request
     * @param response response
     * @return vista de error 404
     */
    public ModelAndView notFound(Request request, Response response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Error 404");
        if (request.session().isNew()){
            parametros.put("logged", false);
        } else if (!(Boolean)request.session().attribute("logged")){
            parametros.put("logged", false);
        } else {
            parametros.put("logged", true);
            //TODO: aca colocar la id del usuario en sesion
            parametros.put("idUser", 1);
        }

        return new ModelAndView(parametros, "404.hbs");
    }

    /**
     * Mapeo los parametros enviados como query string a un map con el que puedo trabajar
     * @param pairs pares de clave - valor
     * @return query string mapeado
     */
    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for (NameValuePair pair : pairs) {
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }
}
