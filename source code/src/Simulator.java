import java.io.*;
import java.util.*;

public class Simulator {
    public final int initiationEventCount = 8000;
    public final int warmUpPeriod = 3000;
    private PriorityQueue<Event> eventQueue;
    public double clock;
    public BaseStation[] baseStations;
    public int blockedCallCount;
    public int droppedCallCount;
    public int handoverCount;

    private final Integer COLUMN_ARRIVAL_TIME = 0;
    private final Integer COLUMN_BASE_STATION = 1;
    private final Integer COLUMN_CALL_DURATION = 2;
    private final Integer COLUMN_CAR_SPEED = 3;

    private int eventCount; // counter to keep track of the warm-up period

    // comparator to make event with earlier event time to be dequeued first
    private static Comparator<Event> eventComparator = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            if (e1.getEventTime() < e2.getEventTime())
                return -1;
            if (e1.getEventTime() > e2.getEventTime())
                return 1;
            else
                return 0;
        }
    };

    public void init() {
        // initialize the variables
        eventQueue = new PriorityQueue<Event>(1, eventComparator);
        clock = 0;
        baseStations = new BaseStation[20];
        blockedCallCount = 0;
        droppedCallCount = 0;
        handoverCount = 0;
        eventCount = 0;

        for (int i = 0; i < 20; ++i) {
            baseStations[i] = new BaseStation(i);
        }
    }

    public void inputCallEvents() {
        FileReader in = null;
        try {
            in = new FileReader(
                    "D:/EdveNTUre & Data/Year 3 sem 2/CZ4015/Homework/PCS_TEST_DETERMINSTIC_S21314.csv");
        } catch (Exception e) { }

        for (int i = 1; i <= this.initiationEventCount; i++) {
            String[] row = in.readOneRow();
            CallInitiationEvent event = new CallInitiationEvent(i,
                    Double.parseDouble(row[COLUMN_ARRIVAL_TIME]),
                    baseStations[Integer.parseInt(row[COLUMN_BASE_STATION])],
                    Double.parseDouble(row[COLUMN_CAR_SPEED]),
                    Double.parseDouble(row[COLUMN_CALL_DURATION]),
                    ,

                    );
            //System.out.println(event.toString());
            eventQueue.add(event);
        }
    }

    public void beginSimulation() {
        while (!eventQueue.isEmpty()) {
            Event e = eventQueue.peek();
            eventQueue.remove(e);
            handleEvent(e);
        }
    }

    private void handleEvent(Event event) {
        this.clock = event.getEventTime(); // advance simulation clock
        BaseStation currentStation = event.getBaseStation();
        System.out.println(event.toString());
        if (event instanceof CallInitiationEvent) {
            if (currentStation.getNumFreeChannels() > 0) {
                currentStation.useOneChannel();
                generateNextEvent(event);
            } else {
                this.blockedCallCount++;
            }
            eventCount++;
            if (eventCount == this.warmUpPeriod) {
                this.handoverCount = 0;
                this.blockedCallCount = 0;
                this.droppedCallCount = 0;
            }

        } else if (event instanceof CallHandoverEvent) {
            // get the next Base Station
            BaseStation nextBaseStation;
            if (event.getDirection() == Direction.TO_STATION_ONE) {
                nextBaseStation = baseStations[event.getId() - 1];
            } else {
                nextBaseStation = baseStations[event.getId() + 1];
            }
            currentStation.releaseUsedChannel();
            event.setBaseStation(nextBaseStation);

            if (nextBaseStation.getNumFreeChannels() > 0) {
                nextBaseStation.useOneChannel();
                generateNextEvent(event);
            } else {
                this.droppedCallCount++;
            }
            this.handoverCount++;
        } else if (event instanceof CallTerminationEvent) {
            currentStation.releaseUsedChannel();
        }
    }

    private void generateNextEvent(Event event) {
        Event nextEvent;
        // calculate time to reach the next base station
        double remainingDistance = 2000.0 - event.getPosition();
        // convert km/h to m/s
        double speedInMetersPerSecond = event.getSpeed() * 1000.0 / 3600.0;
        double remainingTimeInThisStation = Math.min(remainingDistance / speedInMetersPerSecond, event.getDuration());
        // calculate the new event duration
        double newEventDuration = event.getDuration() - remainingTimeInThisStation;
        if (
                event.getDuration() > remainingTimeInThisStation &&
                        event.getDirection() == Direction.TO_STATION_TWENTY &&
                        event.getBaseStation().getId() != 20
        ) {
            // instantiate a new instance of Call Handover Event
            // pass the current event attributes to the next event
            // 1. event ID
            // 2. new event time
            // 3. speed of current car
            // 4. next Base Station
            // 5. updated call duration after passing this Base Station
            // 6. direction that the car is moving towards
            nextEvent = new CallHandoverEvent(
                    event.getId(),
                    this.clock + remainingTimeInThisStation,
                    event.getBaseStation(),
                    event.getSpeed(),
                    newEventDuration,
                    event.getDirection()
            );
        } else if (
                event.getDuration() > remainingTimeInThisStation &&
                        event.getDirection() == Direction.TO_STATION_ONE &&
                        event.getBaseStation().getId() != 1
        ) {
            nextEvent = new CallHandoverEvent(
                    event.id,
                    this.clock + remainingTimeInThisStation,
                    event.getBaseStation(),
                    event.getSpeed(),
                    newEventDuration,
                    event.getDirection()
            );
        } else {
            // call ended before reaching next base station
            nextEvent = new CallTerminationEvent(
                    event.getId(),
                    this.clock + remainingTimeInThisStation,
                    event.getBaseStation()
            );
        }
        eventQueue.add(nextEvent);
    }

    public void printStatistics() {
        System.out.println("Blocked Call Count: " + blockedCallCount);
        System.out.println("Blocked Call Percentage: " + (double) blockedCallCount / (initiationEventCount - warmUpPeriod) * 100 + "%");
        System.out.println("Dropped Call Count: " + droppedCallCount);
        System.out.println("Dropped Call Percentage:" + (double) droppedCallCount / (initiationEventCount - warmUpPeriod) * 100 + "%");
        System.out.println("Handover Count:" + handoverCount);
    }

}