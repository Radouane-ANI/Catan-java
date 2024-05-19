package gui;

import util.Constante;
import util.WeatherMarkovChain;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class WeatherDisplay extends JPanel {
    private JLabel weatherLabel;
    private JLabel weatherIconLabel;
    private ImageIcon weatherIcon;
    private WeatherMarkovChain winterWeatherChain;
    private WeatherMarkovChain springWeatherChain;
    private WeatherMarkovChain summerWeatherChain;
    private WeatherMarkovChain autumnWeatherChain;
    private String[] seasons = {"Spring", "Summer", "Autumn", "Winter"};
    private String currentWeather;
    private int turnCounter;
    private Image backgroundImage;
    private String musicFilePath;
    private Clip clip;
    private boolean stopMusic;
    private JButton muteButton;
    private JButton unmuteButton;
    public boolean isMuted;

    public WeatherDisplay() {
        setOpaque(false);
        setLayout(null);

        String[] weatherStates = {"Pluie", "Soleil", "Nuageux", "Neige", "Vent"};
        winterWeatherChain = new WeatherMarkovChain(Constante.HIVER_MATRIX, weatherStates);
        springWeatherChain = new WeatherMarkovChain(Constante.PRINTEMPS_MATRIX, weatherStates);
        summerWeatherChain = new WeatherMarkovChain(Constante.ETE_MATRIX, weatherStates);
        autumnWeatherChain = new WeatherMarkovChain(Constante.AUTONME_MATRIX, weatherStates);

        weatherLabel = new JLabel("Nuageux");
        weatherLabel.setForeground(Color.BLACK);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        weatherLabel.setBounds(125, 20, 300, 50);
        add(weatherLabel);

        weatherIconLabel = new JLabel();
        weatherIconLabel.setBounds(100, 50, 100, 100);
        add(weatherIconLabel);

        muteButton = new JButton(new ImageIcon("src/ressources/son_mute.png"));
        muteButton.setBounds(10, 10, 30, 30);     
        muteButton.addActionListener(e -> {
            stopMusic = true;
            isMuted = true;
            muteButton.setEnabled(false);
            unmuteButton.setEnabled(true);
            stopCurrentMusic();
        });   
        add(muteButton);

        unmuteButton = new JButton(new ImageIcon("src/ressources/son_unmute.png"));
        unmuteButton.setBounds(45, 10, 30, 30);
        unmuteButton.addActionListener(e -> {
            stopMusic = false;
            isMuted = false;
            unmuteButton.setEnabled(false);
            muteButton.setEnabled(true);
            playMusic(getCurrentWeather());
        });
        unmuteButton.setEnabled(false);
        add(unmuteButton);
    }

    public String getCurrentWeather() {
        return weatherLabel.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundImage = new ImageIcon("src/ressources/meteo_fond.png").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void updateWeather(boolean changeMusic) {
        if (changeMusic) {
            stopCurrentMusic();
        }
        this.turnCounter++;

        int currentSeasonIndex = (this.turnCounter / 4) % 4;
        String currentSeason = seasons[currentSeasonIndex];
        currentWeather = weatherLabel.getText();

        String nextWeather;
        switch (currentSeason) {
            case "Spring":
                nextWeather = springWeatherChain.getNextWeather(currentWeather);
                break;
            case "Summer":
                nextWeather = summerWeatherChain.getNextWeather(currentWeather);
                break;
            case "Autumn":
                nextWeather = autumnWeatherChain.getNextWeather(currentWeather);
                break;
            case "Winter":
                nextWeather = winterWeatherChain.getNextWeather(currentWeather);
                break;
            default:
                nextWeather = "Soleil";
                break;
        }

        loadWeatherIcon(nextWeather);
        weatherLabel.setText(nextWeather);
        weatherIconLabel.setIcon(weatherIcon);

        if (!isMuted && changeMusic) {
            playMusic(nextWeather);
        }
    }

    private void loadWeatherIcon(String weather) {
        switch (weather) {
            case "Soleil":
                weatherIcon = new ImageIcon("src/ressources/meteo_soleil.png");
                break;
            case "Nuageux":
                weatherIcon = new ImageIcon("src/ressources/meteo_nuageux.png");
                break;
            case "Pluie":
                weatherIcon = new ImageIcon("src/ressources/meteo_pluie.png");
                break;
            case "Neige":
                weatherIcon = new ImageIcon("src/ressources/meteo_neige.png");
                break;
            case "Vent":
                weatherIcon = new ImageIcon("src/ressources/meteo_vent.png");
                break;
            default:
                weatherIcon = new ImageIcon("src/ressources/meteo_default.png");
                break;
        }
    }

    private void playMusic(String weather) {
        try {
            if (!stopMusic) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }
    
                switch (weather) {
                    case "Pluie":
                        musicFilePath = "src/ressources/son_pluie.wav";
                        break;
                    case "Soleil":
                        musicFilePath = "src/ressources/son_soleil.wav";
                        break;
                    case "Nuageux":
                        musicFilePath = "src/ressources/son_nuage.wav";
                        break;
                    case "Neige":
                        musicFilePath = "src/ressources/son_neige.wav";
                        break;
                    case "Vent":
                        musicFilePath = "src/ressources/son_vent.wav";
                        break;
                    default:
                        musicFilePath = "src/ressources/son_vent.wav";
                        break;
                }
    
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(musicFilePath));
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }    

    public void stopCurrentMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}