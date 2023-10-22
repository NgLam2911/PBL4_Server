package pbl4.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Start the server on default port (6996)
        //TODO: Custom port with config
        Server.getInstance().start(null); // Using default port
    }
}