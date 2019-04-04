import java.util.Random;

public class RandomNumberGenerator {
    private final double carInterArrivalTimeMean = 1.370;
    private final double callDurationMean = 109.836;
    private final double carSpeedMean = 120.072;
    private final double carSpeedStandardDeviation = 9.01905789789691;

    public double randomCarInterArrival() {
        return ExponentialDistRandomNumber(carInterArrivalTimeMean);
    }

    public int randomBaseStation() {
        return (int) Math.ceil(UniformDistRandomNumber(0, 20));
    }

    public double randomPositionInBaseStation() {
        return UniformDistRandomNumber(0, 2000);
    }

    public double randomCallDuration() {
        return 10 + ExponentialDistRandomNumber(callDurationMean);
    }

    public double randomCarSpeed() {
        return NormalDistRandomNumber(carSpeedMean, carSpeedStandardDeviation);
    }

    private double ExponentialDistRandomNumber(double beta) {
        // f(x) = (1/beta)*e^(-x/beta)
        // F(x) = 1-e^(-x/beta)
        // X = -beta*ln(1-U)
        double U = Math.random();
        double randomNumber = (-beta) * Math.log(1 - U);
        return randomNumber;
    }

    private double UniformDistRandomNumber(double a, double b) {
        // f(x) = 1/(b-a)
        // F(x) = (x-a)/(b-a)
        // X = (b-a)*U+a
        double U = Math.random();
        double randomNumber = (b - a) * U + a;
        return randomNumber;
    }

    private double NormalDistRandomNumber(double mean, double stdDev) {
        Random random = new Random();
        double randomNumber = random.nextGaussian();
        randomNumber = randomNumber * stdDev + mean;
        return randomNumber;
    }
}
