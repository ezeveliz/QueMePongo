package Controllers;

import spark.Request;
import spark.Response;

public class Middlewares {
    public static void authenticated(Request request, Response response){
        if(request.session().isNew()){
            request.session(true);
            request.session().attribute("logged", false);
            request.session().attribute("logError", false);
            response.redirect("/login");
        }
    }
}
