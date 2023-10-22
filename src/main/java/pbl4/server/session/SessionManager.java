package pbl4.server.session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    //Singleton pattern
    private static SessionManager instance;

    private SessionManager(){}

    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    private Map<String, Session> sessions = new HashMap<>();

    public Map<String, Session> getSessions(){
        return this.sessions;
    }

    public void addSession(Session session){
        this.sessions.put(session.getSessionID(), session);
    }

    public void removeSession(Session session){
        this.sessions.remove(session.getSessionID());
    }

    public void removeSession(String sessionID){
        this.sessions.remove(sessionID);
    }

    public Session getSession(String sessionID){
        return this.sessions.get(sessionID);
    }

    public Session getSession(Socket socket){
        for (Session session : this.sessions.values()){
            if (session.getSessionSocketInfo().equals(socket)){
                return session;
            }
        }
        return null;
    }

    public Session getSessionByIP(String ip){
        for (Session session : this.sessions.values()){
            if (session.getSessionIP().equals(ip)){
                return session;
            }
        }
        return null;
    }

    public boolean hasSession(String sessionID){
        return this.sessions.containsKey(sessionID);
    }

    public boolean hasSession(Session session){
        return this.sessions.containsKey(session.getSessionID());
    }

    public boolean hasSession(Socket socket){
        for (Session session : this.sessions.values()){
            if (session.getSessionSocketInfo().equals(socket)){
                return true;
            }
        }
        return false;
    }

    public boolean hasSessionByIP(String ip){
        for (Session session : this.sessions.values()){
            if (session.getSessionIP().equals(ip)){
                return true;
            }
        }
        return false;
    }













}
