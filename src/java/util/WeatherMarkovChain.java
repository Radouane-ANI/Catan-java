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

        // Randomly select the next weather state based on transition probabilities
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return weatherStates[i];
            }
        }
        // In case of numerical errors, return last state
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

    public static void main(String[] args) {
        double[][] transitionMatrix = {
            {0.6, 0.2, 0.2}, // Transition probabilities for "rain"
            {0.3, 0.5, 0.2}, // Transition probabilities for "sun"
            {0.2, 0.3, 0.5}  // Transition probabilities for "clouds"
        };

        String[] weatherStates = {"rain", "sun", "clouds"};

        WeatherMarkovChain weatherChain = new WeatherMarkovChain(transitionMatrix, weatherStates);

        String currentWeather = "sun";
        for (int i = 0; i < 12; i++) { // 12 turns in a year
            System.out.println("Turn " + (i + 1) + ": " + currentWeather);
            currentWeather = weatherChain.getNextWeather(currentWeather);
        }
    }
}
