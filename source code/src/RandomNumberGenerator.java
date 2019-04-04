import enums.Direction;

import java.util.Random;

public class RandomNumberGenerator {
    private final double CAR_IAT_MEAN = 1.370;
    private final double CALL_DURATION_MEAN = 109.836;
    private final double CAR_SPEED_MEAN = 120.072;
    private final double CAR_SPEED_STANDARD_DEVIATION = 9.01905789789691;

    private Double baseStationMaxRadius = 2000.0;

    public Double randomCarInterArrival() {
        return ExponentialDistRandomNumber(CAR_IAT_MEAN);
    }

    public Integer randomBaseStation() {
        return (int) Math.ceil(UniformDistRandomNumber(0, 20));
    }

    public Double randomPositionInBaseStation() {
        return UniformDistRandomNumber(0, baseStationMaxRadius);
    }

    public double randomCallDuration() {
        return 10 + ExponentialDistRandomNumber(CALL_DURATION_MEAN);
    }

    public double randomCarSpeed() {
        return NormalDistRandomNumber(CAR_SPEED_MEAN, CAR_SPEED_STANDARD_DEVIATION);
    }

    public Direction getRandomDirection() {
        Direction direction;

        if (Math.random() >= 0.50) {
            direction = Direction.TO_STATION_ONE;
        } else {
            direction = Direction.TO_STATION_TWENTY;
        }

        return direction;
    }

    private double ExponentialDistRandomNumber(double beta) {
        // f(x) = (1/beta)*e^(-x/beta)
        // F(x) = 1-e^(-x/beta)
        // X = -beta*ln(1-U)
        double U = Math.random();
        double randomNumber = (-beta) * Math.log(1 - U);
        return randomNumber;
    }

    private Double UniformDistRandomNumber(double a, double b) {
        // f(x) = 1/(b-a)
        // F(x) = (x-a)/(b-a)
        // X = (b-a)*U+a
        double U = Math.random();
        double randomNumber = (b - a) * U + a;
        return randomNumber;
    }

    private Double NormalDistRandomNumber(double mean, double stdDev) {
        Random random = new Random();
        double randomNumber = random.nextGaussian();
        randomNumber = randomNumber * stdDev + mean;
        return randomNumber;
    }
}
