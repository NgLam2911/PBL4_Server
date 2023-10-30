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
    private final String id;

    public Session(Socket clientSocket) {
        super();
        this.socket = clientSocket;
        this.id = UUID.randomUUID().toString();
        this.onCreate();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Session");
        DataInputStream in;
        DataOutputStream out;
        try {

            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());

            String number = in.readUTF();
            Server.getInstance().getLogger().debug("Received buffer (number): " + number);
            Translator translator;
            String eng, vie, fra;
            try{
                translator = new Translator(number);
                eng = translator.translate(Translator.Language.ENGLISH);
                Server.getInstance().getLogger().debug("Translated result (ENG): " + eng);
                vie = translator.translate(Translator.Language.VIETNAMESE);
                Server.getInstance().getLogger().debug("Translated result (VIE): " + vie);
                fra = translator.translate(Translator.Language.FRENCH);
                Server.getInstance().getLogger().debug("Translated result (FRA): " + fra);
            }catch (NumberFormatException nah){
                eng = vie = fra = "Invalid number";
                Server.getInstance().getLogger().debug("Translated result: " + eng);
            }
            out.writeUTF(eng);
            out.writeUTF(vie);
            out.writeUTF(fra);
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
}
