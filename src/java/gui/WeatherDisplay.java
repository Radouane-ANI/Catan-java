package gui;

import util.Constante;
import util.WeatherMarkovChain;

import javax.swing.*;
import java.awt.*;

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

    public WeatherDisplay() {
        setOpaque(false);
        setLayout(null);

        String[] weatherStates = {"Pluie", "Soleil", "Nuageux", "Neige", "Vent"};
        winterWeatherChain = new WeatherMarkovChain(Constante.HIVER_MATRIX, weatherStates);
        springWeatherChain = new WeatherMarkovChain(Constante.PRINTEMPS_MATRIX, weatherStates);
        summerWeatherChain = new WeatherMarkovChain(Constante.ETE_MATRIX, weatherStates);
        autumnWeatherChain = new WeatherMarkovChain(Constante.AUTONME_MATRIX, weatherStates);

        weatherLabel = new JLabel("Soleil");
        weatherLabel.setForeground(Color.BLACK);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        weatherLabel.setBounds(125, 20, 300, 50);
        add(weatherLabel);

        weatherIconLabel = new JLabel();
        weatherIconLabel.setBounds(100, 50, 100, 100); 
        
        add(weatherIconLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundImage = new ImageIcon("src/ressources/meteo_fond.png").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void updateWeather() {
        this.turnCounter++;

        int currentSeasonIndex = (this.turnCounter / 4) % 4;
        String currentSeason = seasons[currentSeasonIndex];
        currentWeather = weatherLabel.getText();

        System.out.println("Current season: " + currentSeason);

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
}