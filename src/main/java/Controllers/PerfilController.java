package Controllers;

import Model.DAO.UsuarioDAO;
import Model.queMePongo.Usuario;
import Utils.Middlewares;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.DarkMagic.toMap;

public class PerfilController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        Usuario user = request.session().attribute("usuario");
        parametros.put("section", "Perfil");
        parametros.put("idUser", user.getId());
        parametros.put("nombre", user.getNombre());
        parametros.put("apellido", user.getApellido());
        parametros.put("telefono", user.getTelefono());
        parametros.put("email", user.getEmail());
        parametros.put("notiEmail", user.hayNotiEmail());
        parametros.put("notiSms", user.hayNotiSMS());
        parametros.put("notiWapp", user.hayNotiWapp());
        return new ModelAndView(parametros, "Perfil.hbs");
    }

    public Object actualizar(Request request, Response response) throws URISyntaxException, SQLException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        Usuario user = request.session().attribute("usuario");
        user.setTelefono(params.get("telefono"));
        user.setEmail(params.get("email"));
        user.setContraseña(params.get("password"));
        user.modiciarNotiEmail(Boolean.valueOf(params.get("emailNoti")));
        user.modiciarNotiSMS(Boolean.valueOf(params.get("smsNoti")));
        user.modiciarNotiWapp(Boolean.valueOf(params.get("wappNoti")));

        UsuarioDAO.modificarUsuario(user);

        return user;
    }
}
