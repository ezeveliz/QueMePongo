package Controllers;

import Model.DAO.UsuarioDAO;
import Model.queMePongo.PreferenciasDTO;
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

public class PreferenciasController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();

        Usuario user = request.session().attribute("usuario");
        PreferenciasDTO preferencias = user.getPreferencias();

        parametros.put("section", "Preferencias");
        parametros.put("idUser", user.getId());
        parametros.put("nombre", user.getNombre());
        parametros.put("apellido", user.getApellido());
        parametros.put("tela",preferencias.getTela());
        parametros.put("color1", preferencias.getColor1());
        parametros.put("color2", preferencias.getColor2());
        parametros.put("guardarropas", user.getGuardarropas());
        return new ModelAndView(parametros, "Preferencias.hbs");
    }

    public Object actualizar(Request request, Response response) throws IOException, URISyntaxException, SQLException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = new ObjectMapper().readValue(request.body(), Map.class);

        Usuario user = request.session().attribute("usuario");
        PreferenciasDTO preferencias = user.getPreferencias();

        preferencias.setTela(params.get("tela"));
        preferencias.setColor1(params.get("color1"));
        preferencias.setColor2(params.get("color2"));

        UsuarioDAO.modificarUsuario(user);

        request.session().attribute("usuario",user);

        return params;
    }
}
