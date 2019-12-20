package Controllers;

import Utils.Middlewares;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Utils.DarkMagic.toMap;

public class PerfilController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Perfil");
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        parametros.put("telefono", 12345678);
        parametros.put("email", "admin@admin.com");
        parametros.put("notiEmail", true);
        parametros.put("notiSms", false);
        parametros.put("notiWapp", true);
        return new ModelAndView(parametros, "Perfil.hbs");
    }

    public Object actualizar(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String telefono = params.get("telefono");
        String email = params.get("email");
        String password = params.get("password");
        Boolean emailNoti = Boolean.valueOf(params.get("emailNoti"));
        Boolean smsNoti = Boolean.valueOf(params.get("smsNoti"));
        Boolean wappNoti = Boolean.valueOf(params.get("wappNoti"));
        return params;
    }
}
