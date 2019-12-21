package Controllers;

import Model.DAO.UsuarioDAO;
import Model.queMePongo.Guardarropas;
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
import java.util.Optional;

import static Utils.DarkMagic.toMap;

public class GuardarropaController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        Usuario user = request.session().attribute("usuario");

        //Id del guardarropa a mostrar
        int id = Integer.parseInt(request.params("id"));
        List<Guardarropas> guardarropas = user.getGuardarropas();
        Optional<Guardarropas> gr = guardarropas.stream().filter(g -> g.getId() == id).findFirst();

        if (gr.isPresent()){
            Guardarropas guardarropa = gr.get();
            parametros.put("section", guardarropa.getNombre());
            parametros.put("idUser", user.getId());
            parametros.put("nombre", user.getNombre());
            parametros.put("apellido", user.getApellido());
            parametros.put("idGuardarropa", id);
            parametros.put("guardarropas", guardarropas);

            return new ModelAndView(parametros, "Guardarropa.hbs");
        } else {
            response.redirect("/usuario/"+user.getId());
            return new ModelAndView(parametros, "");
        }

    }

    /**
     * Agrego una prenda a un guardarropa dado
     * @param request request
     * @param response response
     * @return
     */
    public Object agregarPrenda(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String nombre = params.get("nombre");
        String ubicacion = params.get("ubicacion");
        String tipoPrenda = params.get("tipoPrenda");
        String nivelAbrigo = params.get("nivelAbrigo");
        //TODO: verificar si los transforma a boolean
        Boolean casual = Boolean.valueOf(params.get("casual"));
        Boolean formal = Boolean.valueOf(params.get("formal"));
        Boolean fiesta = Boolean.valueOf(params.get("fiesta"));
        Boolean deportes = Boolean.valueOf(params.get("deportes"));
        String tela = params.get("tela");
        String color1 = params.get("color1");
        String color2 = params.get("color2");
        // Imagen en Base64
        String imagen = params.get("imagen");
        String idGuardarropa = params.get("idGuardarropa");
        return params;
    }

    /**
     * Agrego un guardarropa al usuario
     * @param request request
     * @param response response
     * @return
     */
    public Object agregar(Request request, Response response) throws URISyntaxException, SQLException, IOException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = new ObjectMapper().readValue(request.body(), Map.class);

        Usuario user = request.session().attribute("usuario");

        Guardarropas guardarropas = new Guardarropas();
        guardarropas.setNombre(params.get("nombre"));
        guardarropas.setDisponible(1);

        user.agregarGuardarropas(guardarropas);

        UsuarioDAO.modificarUsuario(user);

        return params;
    }
}
