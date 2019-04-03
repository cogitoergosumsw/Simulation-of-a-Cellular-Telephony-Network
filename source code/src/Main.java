import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Simulator sim = new Simulator();
        double totalBlockedRate = 0;
        double totalDroppedRate = 0;

        // repeat the simulator for 100 times for better accuracy
        for (int i = 0; i < 100; ++i) {
            sim.init();
            sim.inputCallEvents();
            sim.beginSimulation();
            System.out.println("================================");
            System.out.println("--------Simulator Run: " + i + "--------");
            sim.printStatistics();
            System.out.println("================================");
            totalBlockedRate += (double) sim.blockedCallCount / (sim.initiationEventCount - sim.warmUpPeriod);
            totalDroppedRate += (double) sim.droppedCallCount / (sim.initiationEventCount - sim.warmUpPeriod);
        }
        System.out.println("Average Blocked Call Rate: " + totalBlockedRate + "%");
        System.out.println("Average Dropped Call Rate: " + totalDroppedRate + "%");
    }
}
