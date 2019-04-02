import java.io.*
import java.util.*

public class Simulator {
    private final int initiationEventCount = 8000;
    private final int warmUpPeriod = 3000;
    private PriorityQueue<Event> eventQueue;
    private double clock;
    private BaseStation[] baseStations;
    private int blockedCallCount;
    private int droppedCallCount;
    private int handoverCount;
    private static int eventCount; // counter to keep track of the warm-up period

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

    public void beginSimulation() {
        while (!eventQueue.isEmpty()) {
            Event e = eventQueue.peek();
            eventQueue.remove(e);
            handleEvent(e);
        }
    }

    private void handleEvent(Event e) {
        this.clock = e.getEventTime(); // advance simulation clock
        BaseStation currentStation = e.getBaseStation();
        System.out.println(e.toString());
        if (e instanceof CallInitiationEvent) {
            if (currentStation.getNumFreeChannels() > 0) {
                currentStation.useOneChannel();
                generateNextEvent((CallInitiationEvent) e);
            } else {
                this.blockedCallCount++;
            }
            Simulator.eventCount++;
            if (Simulator.eventCount == this.warmUpPeriod) {
                this.handoverCount = 0;
                this.blockedCallCount = 0;
                this.droppedCallCount = 0;
            }

        } else if (e instanceof CallTerminationEvent) {
            currentStation.releaseUsedChannel();
        } else if (e instanceof CallHandoverEvent) {
            // get the next Base Station
            BaseStation nextBaseStation;
            if (e.getDirection() == Direction.TO_STATION_ONE) {
                nextBaseStation = baseStations[e.getId() - 1];
            } else {
                nextBaseStation = baseStations[e.getId() + 1];
            }
            currentStation.releaseUsedChannel();
            if (nextBaseStation.getNumFreeChannels() > 0) {
                nextBaseStation.useOneChannel();
                generateNextEvent((CallHandoverEvent) e);
            } else {
                this.droppedCallCount++;
            }
            this.handoverCount++;
        }
    }

    private void generateNextEvent(CallInitiationEvent e) {
        Event nextEvent;
        // calculate time to reach the next base station
        double remainingDistance = 2000.0 - e.getPosition();
        // convert km/h to m/s
        double speedInMetersPerSecond = e.getSpeed() * 1000.0 / 3600.0;
        double remainingTimeInThisStation = Math.min(remainingDistance / speedInMetersPerSecond, e.getDuration());
        // calculate the new event duration
        double newEventDuration = e.getDuration() - remainingTimeInThisStation;
        if (
                e.getDuration() > remainingTimeInThisStation &&
                        e.getDirection() == Direction.TO_STATION_TWENTY &&
                        e.getBaseStation().getId() != 20
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
                    e.getId(),
                    this.clock + remainingTimeInThisStation,
                    e.getBaseStation(),
                    e.getSpeed(),
                    newEventDuration,
                    e.getDirection()
            );
        } else if (
                e.getDuration() > remainingTimeInThisStation &&
                        e.getDirection() == Direction.TO_STATION_ONE &&
                        e.getBaseStation().getId() != 1
        ) {
            nextEvent = new CallHandoverEvent(
                    e.id,
                    this.clock + remainingTimeInThisStation,
                    e.getBaseStation(),
                    e.getSpeed(),
                    newEventDuration,
                    e.getDirection()
            );
        } else {
            // call ended before reaching next base station
            nextEvent = new CallTerminationEvent(
                    e.getId(),
                    this.clock + remainingTimeInThisStation,
                    e.getBaseStation()
            );
        }
        eventQueue.add(nextEvent);
    }

}