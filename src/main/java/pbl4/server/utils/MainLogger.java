package pbl4.server.utils;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainLogger {

    public enum LogLevel{
        INFO, WARNING, ERROR, DEBUG
    }

    public boolean debug = true;
    private FileWriter logFile;

    public MainLogger(String logFileName){
        try{
            this.logFile = new FileWriter("server.log", true);
        }catch (Exception e){
            this.error("Failed to open log file");
        }
    }

    public void log(LogLevel level, String message){
        String threadName = Thread.currentThread().getName();
        if (Objects.equals(threadName, "main")){
            threadName = "Server";
        }
        String unformatLog = switch (level){
            case INFO ->
                    this.timee() + "[" + threadName + " Thread/INFO] " + message;
            case WARNING ->
                    this.timee() + "[" + threadName + " Thread/WARNING] " + message;
            case ERROR ->
                    this.timee() + "[" + threadName + " Thread/ERROR] " + message;
            case DEBUG ->
                    this.timee() + "[" + threadName + " Thread/DEBUG] " + message;
        };
        String logmsg = switch (level) {
            case INFO ->
                    TextFormat.asciiFormat(TextFormat.Colors.CYAN, TextFormat.Style.NONE) + unformatLog;
            case WARNING ->
                    TextFormat.asciiFormat(TextFormat.Colors.YELLOW, TextFormat.Style.NONE) + unformatLog;
            case ERROR ->
                    TextFormat.asciiFormat(TextFormat.Colors.RED, TextFormat.Style.NONE) + unformatLog;
            case DEBUG ->
                    TextFormat.asciiFormat(TextFormat.Colors.GRAY, TextFormat.Style.NONE) + unformatLog;
        };
        System.out.println(logmsg);
        if (this.logFile != null){
            try{
                this.logFile.write(unformatLog + "\n");
                //Due to the format just for colors and in file, we cant read color ...
            }catch (Exception e){
                System.out.println("Failed to write to log file !");
                //HACK: Use System.out instead of error() to prevent loop back.
            }
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

    private String timee(){
        //Get current date time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + now.format(formatter) + "]";
    }

    public void end(){
        try{
            this.logFile.close();
        }catch (Exception e){
            this.error("Failed to close log file !");
        }
    }
}
