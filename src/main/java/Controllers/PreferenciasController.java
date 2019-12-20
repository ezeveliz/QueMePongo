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

public class PreferenciasController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("section", "Preferencias");
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        parametros.put("tela", "algodon");
        parametros.put("color1", "verde");
        parametros.put("color2", "azul");
        return new ModelAndView(parametros, "Preferencias.hbs");
    }

    public Object actualizar(Request request, Response response) {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, String> params = toMap(pairs);
        String tela = params.get("tela");
        String color1 = params.get("color1");
        String color2 = params.get("color2");
        return params;
    }
}
