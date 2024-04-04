package gui;

import util.Constante;
import util.WeatherMarkovChain;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WeatherDisplay extends JPanel {
    private JLabel weatherLabel;
    private ImageIcon weatherIcon;
    private WeatherMarkovChain winterWeatherChain;
    private WeatherMarkovChain springWeatherChain;
    private WeatherMarkovChain summerWeatherChain;
    private WeatherMarkovChain autumnWeatherChain;
    private String[] seasons = {"Spring", "Summer", "Autumn", "Winter"};
    private String currentWeather;
    private int turnCounter;

    public WeatherDisplay() {
        super(new BorderLayout());
        setSize(200, 250);

        String[] weatherStates = {"Pluie", "Soleil", "Nuageux", "Neige", "Vent"};
        winterWeatherChain = new WeatherMarkovChain(Constante.HIVER_MATRIX, weatherStates);
        springWeatherChain = new WeatherMarkovChain(Constante.PRINTEMPS_MATRIX, weatherStates);
        summerWeatherChain = new WeatherMarkovChain(Constante.ETE_MATRIX, weatherStates);
        autumnWeatherChain = new WeatherMarkovChain(Constante.AUTONME_MATRIX, weatherStates);

        // Création et configuration du JLabel pour afficher la météo
        weatherLabel = new JLabel("Soleil", SwingConstants.CENTER);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Affichage de l'image de la météo
        loadWeatherIcon("Soleil");

        // Création du bouton pour passer au tour suivant
        JButton nextTurnButton = new JButton("Tour suivant");
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // No need for the button action here
                updateWeather(turnCounter);
            }
        });

        // Ajout du label et du bouton à la fenêtre
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(weatherLabel, BorderLayout.CENTER);
        panel.add(nextTurnButton, BorderLayout.SOUTH);
        add(panel);

        setVisible(true);
    }

    public void updateWeather(int turnCounter) {
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
    
        // mise à jour de l'affichage
        loadWeatherIcon(nextWeather);
        weatherLabel.setText(nextWeather);
        weatherLabel.setIcon(weatherIcon);
    }
    

    // Chargement de l'icône en fonction de la météo
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
