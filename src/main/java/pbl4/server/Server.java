package pbl4.server;

import pbl4.server.session.Session;
import pbl4.server.session.SessionManager;
import pbl4.server.utils.MainLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    //Singleton Pattern
    private static Server instance;

    private Server(){}

    public static Server getInstance(){
        if (instance == null){
            instance = new Server();
        }
        return instance;
    }

    // 0 -> 65535
    private int port = 6996;
    private int maxConnection = 1000;
    private ServerSocket server;
    private MainLogger logger;



    public MainLogger getLogger(){
        return this.logger;
    }

    public void start(Integer port) throws IOException {
        this.logger = new MainLogger();
        this.getLogger().debug("Main logger initialized");

        if (port == null){
            this.port = 6996;
        } else {
            this.port = port;
        }
        this.getLogger().debug("Port set to " + this.port);
        this.server = new ServerSocket(this.port);
        this.getLogger().info("Server started on port " + this.port);
        //Listening new connection
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                socket = this.server.accept();
            } catch (IOException e) {
                this.getLogger().warning("Error while accepting new connection");
                continue;
            }
            new Session(socket).start();
            this.getLogger().info("New connection accepted: " + socket.getInetAddress().getHostAddress());

            //Handle command from input
            String command = scanner.nextLine();
            if (command.equals("stop")){
                this.stop();
                break;
            }
            scanner = scanner.reset();
        }
    }

    public void stop() throws IOException {
        this.server.close();
        this.getLogger().info("Server stopped");
    }

    public int getPort(){
        return this.port;
    }

    public int getMaxConnection(){
        return this.maxConnection;
    }

    public void setMaxConnection(int maxConnection){
        this.maxConnection = maxConnection;
    }

    public int getCurrentConnections(){
        return SessionManager.getInstance().getSessions().size();
    }
}
