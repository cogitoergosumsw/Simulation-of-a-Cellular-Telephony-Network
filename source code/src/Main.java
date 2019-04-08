import enums.FCA_Schemes;

public class Main {

    public static void main(String[] args) {
        Simulator sim = new Simulator();
        double totalBlockedRate = 0;
        double totalDroppedRate = 0;
        int numSimulations = 101;

        //pre-determined mean variables to calculate variances
        final Double predeterminedNoChannelReservationBlockedCallsMean = 0.0035;
        final Double predeterminedNoChannelReservationDroppedCallsMean = 0.0060;

        final Double predeterminedHandoverChannelReservationBlockedCallsMean = 0.0064;
        final Double predeterminedHandoverChannelReservationDroppedCallsMean = 0.0055;

        Double blockedCallsVariance = 0.0;
        Double droppedCallsVariance = 0.0;

        FCA_Schemes schemeUsed = FCA_Schemes.NO_CHANNEL_RESERVATION;

        // repeat the simulator for 100 times for better accuracy
        for (int i = 1; i <= numSimulations; i++) {
            sim.init(schemeUsed);
            sim.beginSimulation(false, schemeUsed);

            Double currentSimBlockedCallsRate = (double) sim.blockedCallCount / (sim.totalEventCount - sim.warmUpPeriod);
            Double currentSimDroppedCallsRate = (double) sim.droppedCallCount / (sim.totalEventCount - sim.warmUpPeriod);

//          printing statistics of everyone single simulation run
            System.out.println("================================================================");
            System.out.println("--------Simulator Run: " + i + "--------");
            sim.printStatistics();
            System.out.println("================================================================");

            totalBlockedRate += currentSimBlockedCallsRate;
            totalDroppedRate += currentSimDroppedCallsRate;

            if (schemeUsed == FCA_Schemes.NO_CHANNEL_RESERVATION) {
                blockedCallsVariance += Math.pow(currentSimBlockedCallsRate - predeterminedNoChannelReservationBlockedCallsMean, 2);
                droppedCallsVariance += Math.pow(currentSimDroppedCallsRate - predeterminedNoChannelReservationDroppedCallsMean, 2);
            } else if (schemeUsed == FCA_Schemes.NINE_FREE_CHANNELS_ONE_RESERVED) {
                blockedCallsVariance += Math.pow(currentSimBlockedCallsRate - predeterminedHandoverChannelReservationBlockedCallsMean, 2);
                droppedCallsVariance += Math.pow(currentSimDroppedCallsRate - predeterminedHandoverChannelReservationDroppedCallsMean, 2);
            }

        }
        Double aveBlockedRateMean = totalBlockedRate / numSimulations;
        Double aveDroppedRateMean = totalDroppedRate / numSimulations;

        blockedCallsVariance = blockedCallsVariance / (numSimulations - 1);
        droppedCallsVariance = droppedCallsVariance / (numSimulations - 1);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.printf("================END OF %d SIMULATION RUNS================\n", numSimulations);
        System.out.printf("Average Blocked Call Rate: %.2f%%\n", aveBlockedRateMean * 100);
        System.out.printf("Average Blocked Call Variance: %.6f%%\n", blockedCallsVariance * 100);
        System.out.printf("Average Dropped Call Rate: %.2f%%\n", aveDroppedRateMean * 100);
        System.out.printf("Average Dropped Call Variance: %.6f%%\n", droppedCallsVariance * 100);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }
}
