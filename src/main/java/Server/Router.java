package Server;

import Controllers.Inicio;
import Utils.BooleanHelper;
import Utils.HandlebarsTemplateEngineBuilder;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

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
        Inicio inicioController = new Inicio();

        //UsuarioController usuarioController = new UsuarioController();

        //Spark.get("/usuarios", usuarioController::mostrarTodos, Router.engine);

        //Spark.get("/usuario/:id", usuarioController::mostrar, Router.engine);

        //Spark.get("/usuario", usuarioController::crear, Router.engine);

        //Spark.post("/usuario/:id", usuarioController::modificar);

        //Spark.post("/usuario", usuarioController::guardar);

        //Spark.delete("/usuario/:id", usuarioController::eliminar);

        //Rutas
        get("/", inicioController::inicio, Router.engine);
        get("/login", inicioController::iniciarSesion, Router.engine);
        post("/login", inicioController::login, Router.engine);
        get("/register", inicioController::iniciarSesion, Router.engine);
        post("/register", inicioController::iniciarSesion, Router.engine);

        //get("/", (req, res) -> new Inicio().inicio());

        //Que carajo hace esto?
        //Spark.after((req, res) -> {
            //PerThreadEntityManagers.closeEntityManager();
        //});
    }
}
