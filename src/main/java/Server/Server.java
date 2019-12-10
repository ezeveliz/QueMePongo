package Server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {

    public static void main(String[] args) {
        Spark.port(4567);
        Router.init();
        DebugScreen.enableDebugScreen();
    }
}
