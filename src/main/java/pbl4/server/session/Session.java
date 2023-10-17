package pbl4.server.session;

import pbl4.server.translator.Translator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {

    private final Socket socket;

    public Session(Socket clientSocket) {
        super();
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());

            String number = in.readUTF();
            Translator translator = null;
            String result = "";
            try{
                translator = new Translator(number);
                result = translator.translate(Translator.Language.ENGLISH); //TODO: Include language info in socket
            }catch (NumberFormatException nah){
                result = "Invalid number";
            }
            out.writeUTF(result);
        } catch (IOException ignore) {
        } finally {
            try {
                this.socket.close();
            } catch (IOException ignore) {}
        }
    }
}
