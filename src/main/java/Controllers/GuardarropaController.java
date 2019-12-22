package Controllers;

import Model.DAO.GuardarropaDAO;
import Model.DAO.UsuarioDAO;
import Model.queMePongo.Categoria;
import Model.queMePongo.Guardarropas;
import Model.queMePongo.Prenda;
import Model.queMePongo.Usuario;
import Model.tipoDePrenda.TipoPrendaAbrigo;
import Model.tipoDePrenda.TipoPrendaBasico;
import Utils.Middlewares;

import com.google.common.collect.Lists;
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
            List<Prenda> prendas = guardarropa.getPrendas();
            List<List<Prenda>> splittedPrendas = Lists.partition(prendas, 4);
            Boolean sinPrendas = prendas.isEmpty();

            parametros.put("section", guardarropa.getNombre());
            parametros.put("idUser", user.getId());
            parametros.put("nombre", user.getNombre());
            parametros.put("apellido", user.getApellido());
            parametros.put("idGuardarropa", id);
            parametros.put("guardarropas", guardarropas);
            parametros.put("sinPrendas", sinPrendas);
            parametros.put("splittedPrendas", splittedPrendas);

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
    public Object agregarPrenda(Request request, Response response) throws IOException, URISyntaxException, SQLException {
        List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(), Charset.defaultCharset());
        Map<String, Object> params = new ObjectMapper().readValue(request.body(), Map.class);

        Prenda prenda = new Prenda();
        prenda.setNombre(params.get("nombre").toString());
        prenda.setCategoria(Categoria.valueOf(params.get("ubicacion").toString()));
        if(params.get("tipoPrenda").equals("abrigo")){
            TipoPrendaAbrigo tipoPrenda = new TipoPrendaAbrigo();
            tipoPrenda.setNivelDeCalor(params.get("nivelAbrigo").toString());
            prenda.setTipoDePrenda(tipoPrenda);
        }else{
            TipoPrendaBasico tipoPrenda = new TipoPrendaBasico();
            prenda.setTipoDePrenda(tipoPrenda);
        }
        Boolean a = Boolean.valueOf(params.get("casual").toString());
        prenda.tipoDePendaCasual(a);
        prenda.tipoDePendaFormal(Boolean.valueOf(params.get("formal").toString()));
        prenda.tipoDePendaFiesta(Boolean.valueOf(params.get("fiesta").toString()));
        prenda.tipoDePendaDeportiva(Boolean.parseBoolean(params.get("deportes").toString()));
        prenda.setTela(params.get("tela").toString());
        prenda.setColorPrimario(params.get("color1").toString());
        prenda.setColorSecundario(params.get("color2").toString());
        // Imagen en Base64
        prenda.setFoto(params.get("imagen").toString());

        prenda.setId_guardarropa(Integer.parseInt(params.get("idGuardarropa").toString()));
        Guardarropas guardarropas = GuardarropaDAO.getGuardarropas(Integer.parseInt(params.get("idGuardarropa").toString()));
        guardarropas.agregarPrenda(prenda);

        GuardarropaDAO.modificarGuardarropas(guardarropas);

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
