package pbl4.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainLogger {

    public enum LogLevel{
        INFO, WARNING, ERROR, DEBUG
    }

    public boolean debug = true;

    public MainLogger(){
    }

    public void log(LogLevel level, String message){
        String threadName = Thread.currentThread().getName();
        if (Objects.equals(threadName, "main")){
            threadName = "Server";
        }
        String logmsg = switch (level) {
            case INFO ->
                    TextFormat.asciiFormat(TextFormat.Colors.CYAN, TextFormat.Style.NONE) + this.timee() + "[" + threadName + " Thread/INFO] " + message;
            case WARNING ->
                    TextFormat.asciiFormat(TextFormat.Colors.YELLOW, TextFormat.Style.NONE) + this.timee() + "[" + threadName + " Thread/WARNING] " + message;
            case ERROR ->
                    TextFormat.asciiFormat(TextFormat.Colors.RED, TextFormat.Style.NONE) + this.timee() + "[" + threadName + " Thread/ERROR] " + message;
            case DEBUG ->
                    TextFormat.asciiFormat(TextFormat.Colors.GRAY, TextFormat.Style.NONE) + this.timee() + "[" + threadName + " Thread/DEBUG] " + message;
        };
        System.out.println(logmsg);
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

    private String timee(){
        //Get current date time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + now.format(formatter) + "]";
    }
}
