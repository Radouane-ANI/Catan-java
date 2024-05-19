package util;

public class Matrix {
    private final int rows;
    private final int columns;
    private double[][] matrix;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    public Matrix(int rows, int columns, double[][] initialValues) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
        initializeWithProvidedValues(initialValues);
    }

    private void initializeWithProvidedValues(double[][] initialValues) {
        if (initialValues.length != rows || initialValues[0].length != columns) {
            throw new IllegalArgumentException("Les dimensions de la matrice initiale ne correspondent pas.");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = initialValues[i][j];
            }
        }
    }

    public void set(int i, int j, double value) {
        if (i < 0 || i >= rows || j < 0 || j >= columns) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        matrix[i][j] = value;
    }

    public double get(int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= columns) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return matrix[i][j];
    }

    public Matrix times(Matrix M) {
        if (columns != M.rows) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication");
        }
        Matrix result = new Matrix(rows, M.columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < M.columns; j++) {
                double sum = 0;
                for (int k = 0; k < columns; k++) {
                    double product = matrix[i][k] * M.get(k, j);
                    sum = sum == 0 ? product : sum + product;
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }
}
