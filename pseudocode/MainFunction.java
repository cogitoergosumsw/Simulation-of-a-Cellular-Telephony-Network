// Start

// Initialization
private Queue<Event> eventQ;
private double clock;
private BaseStation[] baseStationList;
private int totalNumCalls;
private int numWarmupCalls;
private int numBlockedCalls;
private int numDroppedCalls;
private int numHandovers;

public class MainFunction {
    void init() {
        Queue<Event> eventQ = new Queue<Event>();

        clock = 0;
        numBlockedCalls = 0;
        numDroppedCalls = 0;
        numHandovers = 0;
        totalNumCalls = 11000;
        numWarmupCalls = 1000;
        baseStationList = new BaseStation[20];
        for (int i = 0; i < 20; i++) {
                baseStationList[i] = new BaseStation(i);
        }
    }
    public static void main(String[] args) {
        String [] data = readDataFromExcelFile;
        for (int j = 0; j<data.length; j++) {
                // randomize the direction of the travelling car
                Direction direction;
                if (Math.random() >= 0.50) {
                        direction = Direction.ToStationOne;
                } else {
                        direction = Direction.ToStationTwenty;
                }
                // initialise the CallInitiationEvents from the Excel file
                // add them into the event queue
                CallInitiationEvent event = new CallInitiationEvent(data.id, data.eventTime, data.speed, data.baseStation, data.eventDuration, direction);
                eventQ.add(data[j]);
        }

        while (!eventList.isEmpty()) {
                Event e = eventQ.peek();
                if (e instanceof CallInitiationEvent) {
                        // call the Call Initiation event handling function
                        handleCallInitiationEvent(e);
                } else if (e instanceof CallTerminationEvent) {
                        // call the Call Termination event handling function
                        handleCallTerminationEvent(e);
                } else if (e instanceof CallHandoverEvent) {
                        // call the Call Handover event handling function
                        handleCallHandoverEvent(e);
                }
                eventQ.remove(e);
        }

        // tabulate the results
        double droppedCallRate = numDroppedCalls / (totalNumCalls - numWarmupCalls);
        double blockedCallRate = numBlockedCalls / (totalNumCalls - numWarmupCalls);

        // print the statistics for further analysis
        printStatistics();
    }

}
