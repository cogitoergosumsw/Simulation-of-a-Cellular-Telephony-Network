public class Main {

    public static void main(String[] args) {
        Simulator sim = new Simulator();
        double totalBlockedRate = 0;
        double totalDroppedRate = 0;
        int numSimulations = 100;
        
        // repeat the simulator for 100 times for better accuracy
        for (int i = 0; i < numSimulations; ++i) {
            sim.init();
            sim.readData(false);
            sim.beginSimulation();

            // printing statistics of everyone single simulation run
//            System.out.println("================================================================");
//            System.out.println("--------Simulator Run: " + i + "--------");
//            sim.printStatistics();
//            System.out.println("================================================================");

            totalBlockedRate += (double) sim.blockedCallCount / (sim.totalEventCount - sim.warmUpPeriod);
            totalDroppedRate += (double) sim.droppedCallCount / (sim.totalEventCount - sim.warmUpPeriod);
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.printf("========END OF %d SIMULATION RUNS========", numSimulations);
        System.out.println();
        System.out.print("Average Blocked Call Rate: ");
        System.out.printf("%.2f", totalBlockedRate);
        System.out.print("%");
        System.out.println();
        System.out.print("Average Dropped Call Rate: ");
        System.out.printf("%.2f", totalDroppedRate);
        System.out.print("%");
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
