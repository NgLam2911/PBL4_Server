package pbl4.server;

import java.util.Scanner;

public class InputThread extends Thread{

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String input = scanner.nextLine();
            //Handle command
            String command = input.split(" ")[0];
            String[] args = input.substring(command.length()).trim().split(" ");
            switch (command){
                case "stop":
                    Server.getInstance().stop();
                    break;
                case "say":
                    Server.getInstance().getLogger().info("[Server]" + String.join(" ", args));
                    break;
                case "help":
                    Server.getInstance().getLogger().info("Available commands: stop, say, help");
                    break;
                case "":
                    break;
            }
            scanner.reset();
        }
    }
}
