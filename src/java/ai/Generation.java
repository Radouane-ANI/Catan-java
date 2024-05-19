package ai;

import java.util.ArrayList;

import logic.Bank;

/**
 * Generation
 */
public class Generation {

    private final int ParentGroup = 4;
    private final int ChildGroup = 4;
    private final int size = (8 + 2*ChildGroup*(4*ParentGroup - 1))*ParentGroup;

    Bot[] population = new Bot[size];
    Bank bank;

    Generation(Bank bank, int step, String file) {
        this.bank = bank;

        setPopulation();

        for (int i = 0; i < step; i++) {
            population = select();
            population = reproduce();
        }
    }

    private void setPopulation() {
        for (int i = 0; i < size; i++) {
            population[i] = new Bot(bank);
        }
    }

    private Bot[] select() {
        Bot[] res = new Bot[8*ParentGroup];

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < res.length; j++) {
                if (res[j].getPoints() < population[i].getPoints()) {
                    insert(res, population[i], j);
                    break;
                }
            }
        }

        return res;
    }

    private void insert(Bot[] array, Bot a, int position) {
        for (int i = array.length-1; i > position; i--) {
            array[i] = array[i-1];
        }
        array[position] = a;
    }

    private Bot[] reproduce() {
        int counter = population.length;
        Bot[] res = new Bot[size];
        for (int i = 0; i < population.length; i++) {
            res[i] = population[i];
        }

        for (int i = 0; i < population.length; i++) {
            for (int j = i+1; j < population.length; j++) {
                for (int k = 0; k < ChildGroup; k++) {
                    res[counter++] = res[i].reproduce(res[j], k/ChildGroup);
                }
            }
        }

        return res;
    }
}