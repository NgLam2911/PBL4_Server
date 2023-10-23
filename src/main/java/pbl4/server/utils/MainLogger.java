package pbl4.server.utils;

public class MainLogger {

    public enum LogLevel{
        INFO, WARNING, ERROR, DEBUG
    }

    public boolean debug = false;

    public MainLogger(){

    }

    public void log(LogLevel level, String message){
        switch (level){
            case INFO:
                System.out.println(TextFormat.asciiFormat(TextFormat.Colors.CYAN, TextFormat.Style.NONE) + "[INFO] " + message);
                break;
            case WARNING:
                System.out.println(TextFormat.asciiFormat(TextFormat.Colors.YELLOW, TextFormat.Style.NONE) + "[WARNING] " + message);
                break;
            case ERROR:
                System.out.println(TextFormat.asciiFormat(TextFormat.Colors.RED, TextFormat.Style.NONE) + "[ERROR] " + message);
                break;
            case DEBUG:
                System.out.println(TextFormat.asciiFormat(TextFormat.Colors.GRAY, TextFormat.Style.NONE) + "[DEBUG] " + message);
                break;
        }
    }

    public void info(String message){
        this.log(LogLevel.INFO, message);
    }

    public void warning(String message){
        this.log(LogLevel.WARNING, message);
    }

    public void error(String message){
        this.log(LogLevel.ERROR, message);
    }

    public void debug(String message){
        if (this.debug){
            this.log(LogLevel.DEBUG, message);
        }
    }
}
