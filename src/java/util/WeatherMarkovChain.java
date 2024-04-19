package util;

import java.util.Random;

public class WeatherMarkovChain {
    private Matrix transitionMatrix;
    private String[] weatherStates;
    private Random random;

    public WeatherMarkovChain(Matrix transitionMatrix, String[] weatherStates) {
        this.transitionMatrix = transitionMatrix;
        this.weatherStates = weatherStates;
        this.random = new Random();
    }

    public String getNextWeather(String currentWeather) {
        int currentStateIndex = getIndexForState(currentWeather);
        double[] probabilities = new double[weatherStates.length];

        // Obtenir les probabilités de transition pour l'état météorologique actuel
        for (int i = 0; i < weatherStates.length; i++) {
            probabilities[i] = transitionMatrix.get(currentStateIndex, i);
        }

        // Sélectionner le prochain état météorologique de manière aléatoire en fonction des probabilités de transition
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return weatherStates[i];
            }
        }
        // Retourner le dernier état en cas d'erreurs numériques
        return weatherStates[probabilities.length - 1];
    }

    private int getIndexForState(String state) {
        // Trouver l'indice de l'état météorologique donné
        for (int i = 0; i < weatherStates.length; i++) {
            if (weatherStates[i].equals(state)) {
                return i;
            }
        }
        throw new IllegalArgumentException("État météorologique invalide");
    }
}
