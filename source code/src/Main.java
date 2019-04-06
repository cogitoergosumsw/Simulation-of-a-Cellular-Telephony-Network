import enums.FCA_Schemes;

public class Main {

    public static void main(String[] args) {
        Simulator sim = new Simulator();
        double totalBlockedRate = 0;
        double totalDroppedRate = 0;
        int numSimulations = 100;

        FCA_Schemes schemeUsed = FCA_Schemes.NINE_FREE_CHANNELS_ONE_RESERVED;
        
        // repeat the simulator for 100 times for better accuracy
        for (int i = 0; i < numSimulations; ++i) {
            sim.init(schemeUsed);
            sim.beginSimulation(true, schemeUsed);

//             printing statistics of everyone single simulation run
            System.out.println("================================================================");
            System.out.println("--------Simulator Run: " + i + "--------");
            sim.printStatistics();
            System.out.println("================================================================");

            totalBlockedRate += (double) sim.blockedCallCount / (sim.totalEventCount - sim.warmUpPeriod);
            totalDroppedRate += (double) sim.droppedCallCount / (sim.totalEventCount - sim.warmUpPeriod);
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.printf("================END OF %d SIMULATION RUNS================", numSimulations);
        System.out.println();
        System.out.print("Average Blocked Call Rate: ");
        System.out.printf("%.2f", totalBlockedRate / numSimulations * 100);
        System.out.print("%");
        System.out.println();
        System.out.print("Average Dropped Call Rate: ");
        System.out.printf("%.2f", totalDroppedRate / numSimulations * 100);
        System.out.print("%");
        System.out.println();
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
