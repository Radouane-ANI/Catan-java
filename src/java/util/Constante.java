package util;

public class Constante {
    
    private final static double[][] matriceHivernale = {
        {0.2, 0.1, 0.4, 0.2, 0.1},
        {0.1, 0.3, 0.3, 0.2, 0.1},
        {0.2, 0.1, 0.4, 0.2, 0.1},
        {0.1, 0.2, 0.2, 0.4, 0.1},
        {0.2, 0.1, 0.3, 0.1, 0.3}
    };

    private final static double[][] matricePrintemps = {
        {0.3, 0.2, 0.3, 0.0, 0.2},
        {0.2, 0.3, 0.3, 0.0, 0.2},
        {0.2, 0.2, 0.4, 0.0, 0.2},
        {0.1, 0.2, 0.1, 0.1, 0.5},
        {0.2, 0.2, 0.3, 0.0, 0.3}
    };

    private final static double[][] matriceEstivale = {
        {0.4, 0.2, 0.3, 0.0, 0.1},
        {0.2, 0.4, 0.2, 0.0, 0.2},
        {0.1, 0.2, 0.5, 0.0, 0.2},
        {0.0, 0.1, 0.0, 0.0, 0.9},
        {0.2, 0.2, 0.2, 0.0, 0.4}
    };

    private final static double[][] matriceAutomnale = {
        {0.3, 0.2, 0.3, 0.1, 0.1},
        {0.2, 0.3, 0.2, 0.2, 0.1},
        {0.1, 0.2, 0.4, 0.1, 0.2},
        {0.1, 0.2, 0.2, 0.3, 0.2},
        {0.2, 0.2, 0.2, 0.1, 0.3}
    };
    
    public static final Matrix HIVER_MATRIX = new Matrix(5, 5, matriceHivernale);
    public static final Matrix PRINTEMPS_MATRIX = new Matrix(5, 5, matricePrintemps);
    public static final Matrix ETE_MATRIX = new Matrix(5, 5, matriceEstivale);
    public static final Matrix AUTONME_MATRIX = new Matrix(5, 5, matriceAutomnale);
}