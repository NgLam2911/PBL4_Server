package pbl4.server;

import pbl4.server.session.Session;
import pbl4.server.session.SessionManager;
import pbl4.server.translator.Translator;
import pbl4.server.utils.MainLogger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Properties;

public class Server {
    //Singleton Pattern
    private static Server instance;

    private Server() {
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    // 0 -> 65535
    private int port = 6996;
    private int maxConnection = 1000;
    private ServerSocket server;
    private MainLogger logger;


    public MainLogger getLogger() {
        return this.logger;
    }

    public void start() throws IOException {
        this.logger = new MainLogger("server.log");
        this.getLogger().info("Main logger initialized");
        this.getLogger().info("Loading properties");
        this.loadProperties();
        this.getLogger().debug("Properties loaded");
        this.getLogger().debug("Port set to " + this.port);
        this.server = new ServerSocket(this.port);
        this.getLogger().info("Server started on port " + this.port);
        new InputThread().start();
        this.getLogger().debug("Enabled input thread");
        try{
            this.test();
        }catch (Exception e){
            this.getLogger().error("Failed to run tests");
        }
        //Listening new connection
        Socket socket;
        while (true) {
            try {
                socket = this.server.accept();
            } catch (IOException e) {
                this.getLogger().warning("Error while accepting new connection");
                continue;
            }
            new Session(socket).start();
            this.getLogger().info("New connection accepted: " + socket.getInetAddress().getHostAddress());
            if (this.server.isClosed()){
                break;
            }
        }
    }

    private void loadProperties() {
        Properties prop = new Properties();
        String path = this.getCurrentPath() + File.separator + "server.properties";
        File file = new File(path);
        FileInputStream fin;
        try{
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            this.getLogger().info("server.properties not found, creating new one");
            try {
                this.createProperties();
            } catch (FileNotFoundException ex) {
                this.getLogger().error("Failed to create server.properties");
                this.stop();
            }
            this.loadProperties(); // Reload properties
            return;
        }
        try {
            prop.load(fin);
        } catch (IOException e) {
            this.getLogger().error("Failed to load server.properties");
            this.stop();
        }
        try {
            this.port = Integer.parseInt(prop.getProperty("port"));
        } catch (NumberFormatException e) {
            this.getLogger().error("Invalid port in server.properties");
            this.stop();
        }
        if (this.port > 65535 || this.port < 0) {
            this.getLogger().error("Invalid port in server.properties");
            this.stop();
        }

        try {
            this.maxConnection = Integer.parseInt(prop.getProperty("maxConnection"));
        } catch (NumberFormatException e) {
            this.getLogger().error("Invalid maxConnection in server.properties");
            this.stop();
        }
        if (this.maxConnection < 0) {
            this.getLogger().error("Invalid maxConnection in server.properties");
            this.stop();
        }
        this.getLogger().info("Max connection set to " + this.maxConnection);

        boolean debug;
        try {
            debug = Boolean.parseBoolean(prop.getProperty("debug"));
        } catch (NumberFormatException e) {
            this.getLogger().error("Invalid debug mode in server.properties");
            this.stop();
            return;
        }
        this.getLogger().debug = debug;
        if (debug) {
            this.getLogger().info("Debug mode enabled, make sure to disable it in production !");
        }
    }

    private void createProperties() throws FileNotFoundException {
        Properties prop = new Properties();
        prop.setProperty("port", "6996");
        prop.setProperty("maxConnection", "1000");
        prop.setProperty("debug", "true");
        File file = new File(this.getCurrentPath() + File.separator + "server.properties");
        FileOutputStream fout = new FileOutputStream(file);
        try {
            prop.store(fout, null);
        } catch (IOException e) {
            this.getLogger().error("Failed to create server.properties");
            this.stop();
        }
    }

    public void stop() {
        try{
            if (this.server != null) {
                if (!this.server.isClosed()) {
                    this.server.close();
                }
            }
        }catch (IOException ignore){}
        this.getLogger().info("Server stopped");
        this.getLogger().end();
        System.exit(0);
    }

    public int getPort() {
        return this.port;
    }

    public int getMaxConnection() {
        return this.maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public int getCurrentConnections() {
        return SessionManager.getInstance().getSessions().size();
    }

    public String getCurrentPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

    public void test(){
        this.getLogger().debug("Running tests...");
        this.getLogger().debug("Test 1: Translator");

        this.singleTest("123456789");
        this.singleTest("0");
        this.singleTest("1000000000");
        this.singleTest("1000001");
        this.singleTest("9433401");
        this.singleTest("32045");
        this.singleTest("123456789123456789");
        this.singleTest("35737459098456963592010596234333442");
        this.singleTest("-333222223444");
        this.singleTest("-123456789123456789");
        this.singleTest("1556");

        this.getLogger().debug("Test 1 done");
    }

    public void singleTest(String number){
        this.getLogger().debug("Number: " + number);
        Translator translator = new Translator(number);
        this.getLogger().debug("English: " + translator.translate(Translator.Language.ENGLISH));
        this.getLogger().debug("Vietnamese: " + translator.translate(Translator.Language.VIETNAMESE));
        this.getLogger().debug("French: " + translator.translate(Translator.Language.FRENCH));
    }
}
