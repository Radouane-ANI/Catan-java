package ai;

import java.awt.Color;
import util.Matrix;
import logic.Bank;
import logic.Player;

/**
 * Bot
 */
public class Bot extends Player {

    private Integer lastAction = null;
    private static int actionNumber;

    private Matrix actionMatrix;

    Bot(Bank bank, Matrix action) {
        super(true, "", bank, Color.BLACK);
        actionMatrix = action;
    }

    Bot(Bank bank) {
        this(bank, randomMatrix(actionNumber));
    }


    private static Matrix randomMatrix(int n) {
        Matrix res = new Matrix(n, n);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res.set(i, j, Math.random());
            }
        }

        return res;
    }

    private static Matrix mixAction(Matrix a, Matrix b, double mutationRate) {
        Matrix mat = new Matrix(0, 0);

        for (int i = 0; i < actionNumber; i++) {
            for (int j = 0; j < actionNumber; j++) {
                if (Math.random() > mutationRate) {
                    double x = a.get(i, j);
                    double y = a.get(i, j);
                    mat.set(i, j, (Math.random() > .5)? x : y);
                }
                else {
                    mat.set(i, j, Math.random());
                }
            }
        }
        
        return mat;
    }

    Bot reproduce(Bot a, double mutationRate) {
        Bot res = new Bot(getBank(), mixAction(actionMatrix, a.actionMatrix, mutationRate));

        return res;
    }
}