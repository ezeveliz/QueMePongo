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
        parametros.put("password", user.getContraseña());
        parametros.put("notiEmail", user.hayNotiEmail());
        parametros.put("notiSms", user.hayNotiSMS());
        parametros.put("notiWapp", user.hayNotiWapp());
        parametros.put("guardarropas", user.getGuardarropas());

        System.out.println("En perfil:");
        System.out.println(user.getGuardarropas());
        return new ModelAndView(parametros, "Perfil.hbs");
    }

    public Object actualizar(Request request, Response response) throws URISyntaxException, SQLException, IOException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = new ObjectMapper().readValue(request.body(), Map.class);

        Usuario user = request.session().attribute("usuario");

        user.setContraseña(params.get("contraseña"));
        user.setTelefono(params.get("telefono"));
        user.setEmail(params.get("email"));
        user.setContraseña(params.get("password"));
        user.modiciarNotiEmail(Boolean.parseBoolean(params.get("emailNoti")));
        user.modiciarNotiSMS(Boolean.parseBoolean(params.get("smsNoti")));
        user.modiciarNotiWapp(Boolean.parseBoolean(params.get("wappNoti")));


        UsuarioDAO.modificarUsuario(user);

        request.session().attribute("usuario",user);

        //TODO: si modifica el usuario tiene que modificarse en session
        return user;
    }
}
