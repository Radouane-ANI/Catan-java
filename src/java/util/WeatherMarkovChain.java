package util;

import java.util.Random;

public class WeatherMarkovChain {
    private double[][] transitionMatrix;
    private String[] weatherStates;
    private Random random;

    public WeatherMarkovChain(double[][] transitionMatrix, String[] weatherStates) {
        this.transitionMatrix = transitionMatrix;
        this.weatherStates = weatherStates;
        this.random = new Random();
    }

    public String getNextWeather(String currentWeather) {
        int currentStateIndex = getIndexForState(currentWeather);
        double[] probabilities = transitionMatrix[currentStateIndex];

        // Select the next weather randomly based on transition probabilities
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return weatherStates[i];
            }
        }
        // Return the last state in case of numerical errors
        return weatherStates[probabilities.length - 1];
    }

    private int getIndexForState(String state) {
        for (int i = 0; i < weatherStates.length; i++) {
            if (weatherStates[i].equals(state)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid weather state");
    }

    // Method to handle the effects of weather on gameplay
    public void applyWeatherEffects(String weather) {
        switch (weather) {
            case "Pluie":
                // Implement rain effects
                break;
            case "Soleil":
                // Implement sunshine effects
                break;
            case "Nuageux":
                // Implement cloudy weather effects
                break;
            case "Neige":
                // Implement snow effects
                break;
            case "Brouillard":
                // Implement fog effects
                break;
            case "Tempête":
                // Implement storm effects
                break;
            case "Vent":
                // Implement wind effects
                break;
            case "Canicule":
                // Implement heatwave effects
                break;
            default:
                // Unknown weather, do nothing
                break;
        }
    }

    public static void main(String[] args) {
        double[][] transitionMatrix = {
            {0.5, 0.2, 0.1, 0.1, 0.0, 0.0, 0.1, 0.0}, // Transition probabilities for "Pluie"
            {0.2, 0.4, 0.2, 0.1, 0.0, 0.0, 0.1, 0.0}, // Transition probabilities for "Soleil"
            {0.1, 0.2, 0.4, 0.1, 0.0, 0.0, 0.2, 0.0}, // Transition probabilities for "Nuageux"
            {0.0, 0.1, 0.1, 0.5, 0.2, 0.0, 0.0, 0.1}, // Transition probabilities for "Neige"
            {0.0, 0.0, 0.0, 0.2, 0.6, 0.1, 0.0, 0.1}, // Transition probabilities for "Brouillard"
            {0.0, 0.0, 0.0, 0.0, 0.1, 0.7, 0.1, 0.1}, // Transition probabilities for "Tempête"
            {0.1, 0.1, 0.1, 0.0, 0.0, 0.1, 0.6, 0.0}, // Transition probabilities for "Vent"
            {0.0, 0.0, 0.0, 0.1, 0.1, 0.1, 0.0, 0.7}  // Transition probabilities for "Canicule"
        };
    
        String[] weatherStates = {"Pluie", "Soleil", "Nuageux", "Neige", "Brouillard", "Tempête", "Vent", "Canicule"};
    
        WeatherMarkovChain weatherChain = new WeatherMarkovChain(transitionMatrix, weatherStates);
    
        String currentWeather = "Soleil";
        for (int i = 0; i < 12; i++) { // 12 turns in a year
            System.out.println("Turn " + (i + 1) + ": " + currentWeather);
            weatherChain.applyWeatherEffects(currentWeather);
            currentWeather = weatherChain.getNextWeather(currentWeather);
        }
    }    
}
