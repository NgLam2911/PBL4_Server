package pbl4.server.session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {

    private Socket socket;

    public Session(Socket clientSocket){
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
        } catch (IOException e) {
            return;
        }
        String input = null;
        try {
            input = in.readUTF();
        } catch (IOException e) {
            return;
        }
        //Handle Input
    }
}
