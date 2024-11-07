import java.util.ArrayList;

public class Pet {
    private String name;
    private int hunger;
    private int thirst;
    private int stamina;
    private int happiness;
    private boolean isGameDone;
    private boolean boost;
    private int boostDuration;
    private ArrayList<String> recentEvents;

    public Pet(String name) {
        this.name = name;
        this.hunger = 5; // Start hunger at 5
        this.thirst = 5; // Start thirst at 5
        this.stamina = 5; // Start stamina at 5
        this.happiness = 5; // Start happiness at 5
        this.isGameDone = false;
        this.boost = false;
        this.boostDuration = 0;
        this.recentEvents = new ArrayList<>();
    }

    // Basic Getters and Setters
    public String getName() { return name; }
    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = Math.max(0, hunger); }

    public int getThirst() { return thirst; }
    public void setThirst(int thirst) { this.thirst = Math.max(0, thirst); }

    public int getStamina() { return stamina; }
    public void setStamina(int stamina) { this.stamina = Math.max(0, stamina); }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = Math.max(0, happiness); }

    public boolean isGameDone() { return isGameDone; }
    public void setGameDone(boolean gameDone) { isGameDone = gameDone; }

    public boolean hasBoost() { return boost; }
    public void setBoost(boolean boost) {
        this.boost = boost;
        this.boostDuration = 3;
    }

    public int getBoostDuration() { return boostDuration; }
    public void reduceBoostDuration() {
        if (boostDuration > 0) boostDuration--;
        if (boostDuration == 0) boost = false;
    }

    public void addEvent(String event) {
        if (recentEvents.size() > 5) recentEvents.remove(0);
        recentEvents.add(event);
    }

    public ArrayList<String> getRecentEvents() { return recentEvents; }
}
