package labs.apps;

import java.io.IOException;

import io.helidon.microprofile.server.Server;

public class App 
{
    public static void main(final String[] args) throws IOException {
        Server server = startServer();
        System.out.println("http://localhost:" + server.port() + "/test/hello");
    }

    static Server startServer() {
        return Server.create().start(); 
    }
}
