package Controllers;

import Model.DAO.UsuarioDAO;
import Model.queMePongo.Usuario;
import Utils.Middlewares;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.DarkMagic.toMap;


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
        }  else if (!(Boolean)request.session().attribute("logged")){
            parametros.put("logged", false);
        } else {
            parametros.put("logged", true);
            //TODO: aca colocar la id del usuario en sesion
            parametros.put("idUser", 1);
        }
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
            Usuario user = request.session().attribute("usuario");
            response.redirect("/usuario/" + user.getId());

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
    public Object login(Request request, Response response) throws URISyntaxException, SQLException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String email = params.get("email");
        String password = params.get("password");

        Usuario userLogin = UsuarioDAO.getUsuario(params.get("email"));


        if(userLogin != null){
            if(email.equals(userLogin.getEmail()) && password.equals(userLogin.getContraseña())){

                request.session().attribute("logged", true);
                request.session().removeAttribute("logError");
                request.session().removeAttribute("oldEmail");
                request.session().removeAttribute("oldPass");
                request.session().attribute("usuario",userLogin);
                response.redirect("/usuario/"+ userLogin.getId());

                //Redirigir a home
            } else {

                //Redirigir a Iniciar sesion
                request.session().attribute("logError", true);
                request.session().attribute("oldEmail", email);
                request.session().attribute("oldPass", password);
                response.redirect("/login");
            }}else{
            //Redirigir a Iniciar sesion
            request.session().attribute("logError", true);
            request.session().attribute("oldEmail", email);
            request.session().attribute("oldPass", password);
            response.redirect("/login");

        }
        return response;
    }

    /**
     * Deslogueo al usuario en sesion
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
    public Object register(Request request, Response response) throws IOException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);

        Usuario user = new Usuario();

        user.setNombre(params.get("nombre").toString());
        user.setApellido(params.get("apellido").toString());
        user.setEmail(params.get("email").toString());
        user.setTelefono(params.get("telefono").toString());
        user.setUsuario(params.get("email").toString());
        user.setContraseña(params.get("nombre").toString());
        user.modiciarNotiEmail(Boolean.parseBoolean(params.get("emailNoti")));
        user.modiciarNotiSMS(Boolean.parseBoolean(params.get("smsNoti")));
        user.modiciarNotiWapp(Boolean.parseBoolean(params.get("wappNoti")));


        try{
            UsuarioDAO.registrarUsuario(user);
        }catch(Exception e){
            response.redirect("/");

        }

        response.redirect("/");
        return 0;
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

    public ModelAndView offline(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Pagina no cacheada");
        return new ModelAndView(parametros, "Offline.hbs");
    }
}
