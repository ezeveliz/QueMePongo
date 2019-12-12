package Server;

import Controllers.*;
import Utils.BooleanHelper;
import Utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class Router {

    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){

        //Instancio los controladores necesarios
        InicioController inicio = new InicioController();
        UsuarioController usuario = new UsuarioController();
        CalendarioController calendario = new CalendarioController();
        GuardarropaController guardarropa = new GuardarropaController();
        EventoController evento = new EventoController();

        //UsuarioController usuarioController = new UsuarioController();

        //Spark.get("/usuarios", usuarioController::mostrarTodos, Router.engine);

        //Spark.get("/usuario/:id", usuarioController::mostrar, Router.engine);

        //Spark.get("/usuario", usuarioController::crear, Router.engine);

        //Spark.post("/usuario/:id", usuarioController::modificar);

        //Spark.post("/usuario", usuarioController::guardar);

        //Spark.delete("/usuario/:id", usuarioController::eliminar);

        //Rutas
        get("/", inicio::inicio, Router.engine);
        get("/login", inicio::loginView, Router.engine);
        post("/login", inicio::login);
        post("/logout", inicio::logout);
        get("/register", inicio::registerView, Router.engine);
        post("/register", inicio::register);

        get("/usuario/:id", usuario::inicio, Router.engine);

        get("/calendar", calendario::calendarView, Router.engine);
        get("/calendar/:month/:year", calendario::customizedCalendarView, Router.engine);

        get("/guardarropa/:id", guardarropa::mostrar, Router.engine);

        get("/evento/:id", evento::mostrar, Router.engine);

        get("/offline", inicio::offline, Router.engine);
        get("/404", inicio::notFound, Router.engine);
        notFound((req, res) -> {
            res.redirect("/404");
            return "{\"message\":\"Custom 404\"}";
        });

        //get("/", (req, res) -> new Inicio().inicio());

        //Que carajo hace esto?
        //Spark.after((req, res) -> {
            //PerThreadEntityManagers.closeEntityManager();
        //});
    }
}
