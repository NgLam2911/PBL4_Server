package pbl4.server.session;

import pbl4.server.Server;
import pbl4.server.translator.Translator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class Session extends Thread {

    private final Socket socket;
    private String id;

    public Session(Socket clientSocket) {
        super();
        this.socket = clientSocket;
        this.id = UUID.randomUUID().toString();
        this.onCreate();
    }

    @Override
    public void run() {
        DataInputStream in;
        DataOutputStream out;
        try {
            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());

            String number = in.readUTF();
            Server.getInstance().getLogger().debug("Received buffer: " + number);
            Translator translator;
            String result;
            try{
                translator = new Translator(number);
                result = translator.translate(Translator.Language.VIETNAMESE); //TODO: Include language info in socket
                Server.getInstance().getLogger().debug("Translated result: " + result);
            }catch (NumberFormatException nah){
                result = "Invalid number";
                Server.getInstance().getLogger().debug("Translated result: " + result);
            }
            out.writeUTF(result);
            out.flush();
        } catch (IOException ignore) {
        } finally {
            try {
                this.socket.close();
                this.onClose();
                Server.getInstance().getLogger().info("Connection closed: " + this.socket.getInetAddress().getHostAddress());
            } catch (IOException ignore) {}
        }
    }

    public String getSessionID(){
        return this.id;
    }

    public Socket getSessionSocketInfo(){
        return this.socket;
    }

    public String getSessionIP(){
        return this.socket.getInetAddress().getHostAddress();
    }

    public int getSessionPort(){
        return this.socket.getPort();
    }

    public boolean isSessionClosed(){
        return this.socket.isClosed();
    }

    public void onCreate(){
        SessionManager.getInstance().addSession(this);
        Server.getInstance().getLogger().debug("New session created: " + this.id);
    }

    public void onClose(){
        SessionManager.getInstance().removeSession(this);
        Server.getInstance().getLogger().debug("Session closed: " + this.id);
    }

    public void onReceive(){
        //TODO: Handle this
    }
}
