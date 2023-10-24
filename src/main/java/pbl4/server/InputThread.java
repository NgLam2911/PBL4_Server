package pbl4.server;

import java.util.Scanner;

public class InputThread extends Thread{

    @Override
    public void run() {
        Thread.currentThread().setName("Command");
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
                    Server.getInstance().getLogger().info("[Server] " + String.join(" ", args));
                    break;
                case "help":
                    this.helpCmd();
                    break;
                default:
                    Server.getInstance().getLogger().error("Unknown command. Type \"help\" for help.");
                    break;
            }
            scanner.reset();
        }
    }

    private void helpCmd(){
        Server.getInstance().getLogger().info("Available commands:");
        Server.getInstance().getLogger().info("stop: Stop the server");
        Server.getInstance().getLogger().info("say <message>: Send a message (for fun)");
        Server.getInstance().getLogger().info("help: Show this help message");
    }
}
