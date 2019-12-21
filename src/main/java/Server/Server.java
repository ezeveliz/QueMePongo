package Server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {

    public static void main(String[] args) {
        Spark.port(getHerokuAssignedPort());
        Router.init();
        DebugScreen.enableDebugScreen();
    }

    static int getHerokuAssignedPort() {
        //ProcessBuilder processBuilder = new ProcessBuilder();
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));

        }
        return 4597; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
