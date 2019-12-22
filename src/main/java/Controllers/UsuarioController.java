package Controllers;

import Model.queMePongo.Usuario;
import Utils.Middlewares;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
    public ModelAndView inicio(Request request, Response response) {
        Middlewares.authenticated(request, response);
        int id = Integer.parseInt(request.params("id"));

        Map<String, Object> parametros = new HashMap<>();
        Usuario user = request.session().attribute("usuario");

        if (user != null){
            if (user.getId() == id) {
                parametros.put("section", "Inicio");
                parametros.put("idUser", id);
                parametros.put("nombre", user.getNombre());
                parametros.put("apellido", user.getApellido());
                parametros.put("guardarropas", user.getGuardarropas());
                parametros.put("eventos_proximos", user.getEventosProximos());
                parametros.put("sugerencias", user.getSugerencias());
                parametros.put("eventos_pasados", user.getEventosPasados());

                return new ModelAndView(parametros, "Usuario.hbs");
            } else {
                response.redirect("/usuario/"+user.getId());
                return new ModelAndView(parametros, "");
            }

        } else {
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
            response.redirect("/login");
            return new ModelAndView(parametros, "");
        }


    }
}
