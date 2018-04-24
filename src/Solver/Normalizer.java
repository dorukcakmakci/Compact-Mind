package Solver;

public class Normalizer {

    private double normalizedLow;
    private double normalizedHigh;
    private double dataLow;
    private double dataHigh;

    public Normalizer(double dataHigh, double dataLow, double normalizedHigh, double normalizedLow) {
        this.dataHigh = dataHigh;
        this.dataLow = dataLow;
        this.normalizedHigh = normalizedHigh;
        this.normalizedLow = normalizedLow;
    }

    public double normalize(double x) {
        return ((x - dataLow)
                / (dataHigh - dataLow))
                * (normalizedHigh - normalizedLow) + normalizedLow;
    }
}
