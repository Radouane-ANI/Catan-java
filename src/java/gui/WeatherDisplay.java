package gui;

import util.Constante;
import util.WeatherMarkovChain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherDisplay extends JFrame {
    private JLabel weatherLabel;
    private ImageIcon weatherIcon;
    private WeatherMarkovChain winterWeatherChain;
    private WeatherMarkovChain springWeatherChain;
    private WeatherMarkovChain summerWeatherChain;
    private WeatherMarkovChain autumnWeatherChain;

    public WeatherDisplay(String weather) {
        super("Weather Display");
        setSize(200, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] weatherStates = {"Pluie", "Soleil", "Nuageux", "Neige", "Vent"};
        winterWeatherChain = new WeatherMarkovChain(Constante.HIVER_MATRIX, weatherStates);
        springWeatherChain = new WeatherMarkovChain(Constante.PRINTEMPS_MATRIX, weatherStates);
        summerWeatherChain = new WeatherMarkovChain(Constante.ETE_MATRIX, weatherStates);
        autumnWeatherChain = new WeatherMarkovChain(Constante.AUTONME_MATRIX, weatherStates);

        // Création et configuration du JLabel pour afficher la météo
        weatherLabel = new JLabel(weather, SwingConstants.CENTER);
        weatherLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Affichage de l'image de la météo
        loadWeatherIcon(weather);

        // Création du bouton pour passer au tour suivant
        JButton nextTurnButton = new JButton("Tour suivant");
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour la météo pour le tour suivant
                updateWeather();
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

    // Méthode pour mettre à jour la météo pour le tour suivant
    private void updateWeather() {
        String currentWeather = weatherLabel.getText();
        String nextWeather;
        switch (currentWeather) {
            case "Pluie":
                nextWeather = winterWeatherChain.getNextWeather(currentWeather);
                break;
            case "Soleil":
                nextWeather = springWeatherChain.getNextWeather(currentWeather);
                break;
            case "Nuages":
                nextWeather = summerWeatherChain.getNextWeather(currentWeather);
                break;
            case "Neige":
                nextWeather = autumnWeatherChain.getNextWeather(currentWeather);
                break;
            case "Vent":
                nextWeather = winterWeatherChain.getNextWeather(currentWeather);
                break;
            default:
                nextWeather = "Soleil"; // Valeur par défaut
                break;
        }
        loadWeatherIcon(nextWeather);
        weatherLabel.setText(nextWeather);
        weatherLabel.setIcon(weatherIcon);
    }

    // Chargement de l'icône en fonction de la météo
    private void loadWeatherIcon(String weather) {
        switch (weather) {
            case "Soleil":
                weatherIcon = new ImageIcon("src/ressources/soleil.png");
                break;
            case "Nuageux":
                weatherIcon = new ImageIcon("src/ressources/nuageux.png");
                break;
            case "Pluie":
                weatherIcon = new ImageIcon("src/ressources/pluie.png");
                break;
            case "Neige":
                weatherIcon = new ImageIcon("src/ressources/neige.png");
                break;
            case "Vent":
                weatherIcon = new ImageIcon("src/ressources/vent.png");
                break;
            default:
                weatherIcon = new ImageIcon("src/ressources/default.png");
                break;
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de la classe WeatherDisplay
        WeatherDisplay display = new WeatherDisplay("Soleil");
    }
}
