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

public class GuardarropaController {
    public ModelAndView mostrar(Request request, Response response) {
        Middlewares.authenticated(request, response);
        Map<String, Object> parametros = new HashMap<>();
        //Id del guardarropa a mostrar
        int id = Integer.parseInt(request.params("id"));
        parametros.put("section", "Guardarropa Pepe");
        //TODO: aca colocar la id del usuario en sesion
        parametros.put("idUser", 1);
        parametros.put("nombre", "Admin");
        parametros.put("apellido", "Admin");
        return new ModelAndView(parametros, "Guardarropa.hbs");
    }

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
        return params;
    }
}
