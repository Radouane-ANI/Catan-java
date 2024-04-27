package util;

public class Constante {
    
    private final static double[][] matriceHivernale = {{0.1,0.3,0.1,0.2,0.3},
                                                        {0.2,0.1,0.3,0.1,0.3},
                                                        {0.1,0.2,0.2,0.2,0.3},
                                                        {0.3,0.1,0.2,0.1,0.3},
                                                        {0.2,0.3,0.1,0.2,0.1}};

    private final static double[][] matricePrintemps = {{0.2, 0.2, 0.3, 0.1, 0.2},
                                                        {0.1, 0.2, 0.3, 0.1, 0.3},
                                                        {0.2, 0.1, 0.2, 0.2, 0.3},
                                                        {0.1, 0.2, 0.2, 0.2, 0.3},
                                                        {0.2, 0.3, 0.1, 0.1, 0.3}};
                     
                                        
    private final static double[][] matriceEstivale =  {{0.3, 0.1, 0.2, 0.1, 0.3},
                                                        {0.2, 0.3, 0.2, 0.1, 0.2},
                                                        {0.1, 0.2, 0.3, 0.2, 0.2},
                                                        {0.2, 0.1, 0.2, 0.3, 0.2},
                                                        {0.3, 0.2, 0.2, 0.1, 0.2}};
                                        
    private final static double[][] matriceAutomnale = {{0.2, 0.2, 0.3, 0.1, 0.2},
                                                        {0.3, 0.1, 0.2, 0.1, 0.3},
                                                        {0.2, 0.3, 0.1, 0.2, 0.2},
                                                        {0.1, 0.2, 0.2, 0.3, 0.2},
                                                        {0.2, 0.1, 0.3, 0.1, 0.3}};
                                                        
    public static final Matrix HIVER_MATRIX = new Matrix(5, 5,matriceHivernale);
    public static final Matrix PRINTEMPS_MATRIX = new Matrix(5, 5, matricePrintemps);
    public static final Matrix ETE_MATRIX = new Matrix(5, 5, matriceEstivale);
    public static final Matrix AUTONME_MATRIX = new Matrix(5, 5, matriceAutomnale);
}