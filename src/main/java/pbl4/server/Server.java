package pbl4.server;

import pbl4.server.session.Session;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {

    // 0 -> 65535
    private int port;
    private ServerSocket server;
    private Logger logger;

    public Server(){
        this.port = 6996; // default port
    }

    public Server(int port){
        this.port = port;
    }

    public Logger getLogger(){
        return this.logger;
    }

    public void start() throws IOException {
        this.server = new ServerSocket(this.port);
        this.getLogger().info("Server started on port " + this.port);
        //Listening new connection
        Socket socket = null;
        while(true){
            try {
                socket = this.server.accept();
            } catch (IOException e) {
                this.getLogger().warning("Error while accepting new connection");
                continue;
            }
            new Session(socket).start();
            this.getLogger().info("New connection accepted: " + socket.getInetAddress().getHostAddress());
        }
    }

    public void stop() throws IOException {
        this.server.close();
    }
}
