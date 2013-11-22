package scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class Poll {
    
    private String name;
    private String byUser;
    private HashMap<String, String> participants;
    private HashMap<String, Integer> times;
    
    public Poll(String name, String byUser, ArrayList<String> participants, ArrayList<String> times) {
        this.name = name;
        this.byUser = byUser;
        this.participants = new HashMap<String, String>();
        for (String participant : participants) {
            this.participants.put(participant, "");
        }
        this.times = new HashMap<String, Integer>();
        for (String time : times) {
            this.times.put(time, 0);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getByUser() {
        return this.byUser;
    }
    
    public void vote(String participant, String time) {
        participants.put(participant, time);
        Integer votes = times.get(time);
        times.put(time, votes + 1);
    }
    
    public HashMap<String, String> getParticipants() {
        return this.participants;
    }
    
    public HashMap<String, Integer> getTimes() {
        return this.times;
    }
}
