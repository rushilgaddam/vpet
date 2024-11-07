import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class App extends JFrame {
    private Pet pet;
    private JLabel statusLabel, dayLabel, weatherLabel;
    private JTextArea eventHistoryArea;
    private int day = 1;
    private final int maxDays = 10;
    private Random random = new Random();

    public App() {
        setTitle("Enhanced Pet Survival Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pet creation
        String petName = JOptionPane.showInputDialog(this, "What would you like to name your pet?");
        pet = new Pet(petName);

        // UI Elements
        JPanel topPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("Status: Alive");
        dayLabel = new JLabel("Day: 1 / " + maxDays);
        weatherLabel = new JLabel("Weather: Sunny");

        topPanel.add(statusLabel, BorderLayout.WEST);
        topPanel.add(dayLabel, BorderLayout.CENTER);
        topPanel.add(weatherLabel, BorderLayout.EAST);

        JPanel actionPanel = new JPanel();
        JButton feedButton = new JButton("Feed Pet");
        JButton hydrateButton = new JButton("Give Water");
        JButton playButton = new JButton("Play with Pet");

        feedButton.addActionListener(new FeedAction());
        hydrateButton.addActionListener(new HydrateAction());
        playButton.addActionListener(new PlayAction());

        actionPanel.add(feedButton);
        actionPanel.add(hydrateButton);
        actionPanel.add(playButton);

        eventHistoryArea = new JTextArea(10, 50);
        eventHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventHistoryArea);

        add(topPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        updateStatus();
    }

    private class FeedAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pet.setHunger(pet.getHunger() + 2);
            pet.addEvent("Fed the pet. Hunger increased.");
            checkDayProgression();
        }
    }

    private class HydrateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pet.setThirst(pet.getThirst() + 2);
            pet.addEvent("Gave the pet water. Thirst decreased.");
            checkDayProgression();
        }
    }

    private class PlayAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pet.setHappiness(pet.getHappiness() + 1);
            pet.setStamina(pet.getStamina() - 1);
            pet.addEvent("Played with the pet. Happiness increased but stamina decreased.");
            checkDayProgression();
        }
    }

    private void checkDayProgression() {
        day++;
        if (day > maxDays || pet.isGameDone()) {
            endGame();
            return;
        }

        dayLabel.setText("Day: " + day + " / " + maxDays);
        applyRandomEvent();
        updateStatus();
    }

    private void applyRandomEvent() {
        int event = random.nextInt(5);
        String weather = randomWeather();

        if (event == 0) {
            pet.setBoost(true);
            pet.addEvent("A rare boost! " + pet.getName() + " has extra resilience.");
        } else if (event == 1) {
            pet.setHunger(pet.getHunger() - 2);
            pet.setThirst(pet.getThirst() - 2);
            pet.addEvent("Oh no! A sudden storm drained " + pet.getName() + "'s hunger and thirst.");
        } else if (event == 2) {
            pet.setHappiness(pet.getHappiness() - 2);
            pet.addEvent("Your pet is feeling down due to gloomy weather.");
        } else {
            pet.addEvent("Nothing special happened today.");
        }

        weatherLabel.setText("Weather: " + weather);
    }

    private String randomWeather() {
        String[] weathers = {"Sunny", "Rainy", "Stormy", "Cloudy", "Windy"};
        return weathers[random.nextInt(weathers.length)];
    }

    private void updateStatus() {
        statusLabel.setText("Status - Hunger: " + pet.getHunger() + ", Thirst: " + pet.getThirst() +
                ", Happiness: " + pet.getHappiness() + ", Stamina: " + pet.getStamina());
        eventHistoryArea.setText(String.join("\n", pet.getRecentEvents()));
        if (pet.getHunger() <= 0 || pet.getThirst() <= 0) {
            pet.setGameDone(true);
        }
    }

    private void endGame() {
        if (pet.isGameDone() || day > maxDays) {
            JOptionPane.showMessageDialog(this, "Game Over! " + pet.getName() + " survived " + day + " days.");
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! You and " + pet.getName() + " made it to day 10!");
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
