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

    private void handleEvent(Event event) {
        this.clock = event.getEventTime(); // advance simulation clock
        BaseStation currentStation = event.getBaseStation();
        System.out.println(event.toString());
        if (event instanceof CallInitiationEvent) {
            if (currentStation.getNumFreeChannels() > 0) {
                currentStation.useOneChannel();
                generateNextEvent((CallInitiationEvent) event);
            } else {
                this.blockedCallCount++;
            }
            Simulator.eventCount++;
            if (Simulator.eventCount == this.warmUpPeriod) {
                this.handoverCount = 0;
                this.blockedCallCount = 0;
                this.droppedCallCount = 0;
            }

        } else if (event instanceof CallTerminationEvent) {
            currentStation.releaseUsedChannel();
        } else if (event instanceof CallHandoverEvent) {

            // get the next Base Station
            BaseStation nextBaseStation;
            if (event.getDirection() == Direction.TO_STATION_ONE) {
                nextBaseStation = baseStations[event.getId() - 1];
            } else {
                nextBaseStation = baseStations[event.getId() + 1];
            }

            event.setBaseStation(nextBaseStation);

            currentStation.releaseUsedChannel();
            if (nextBaseStation.getNumFreeChannels() > 0) {
                nextBaseStation.useOneChannel();
                generateNextEvent((CallHandoverEvent) event);
            } else {
                this.droppedCallCount++;
            }
            this.handoverCount++;
        }
    }

    private void generateNextEvent(CallInitiationEvent event) {
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

    private void generateNextEvent(CallHandoverEvent event){
        Event nextEvent;
        // calculate time to reach the next base station
        double speedInMeterPerSecond = event.getSpeed() * 1000.0 / 3600.0;
        double remainingTimeInThisStation = Math.min(2000.0 / speedInMeterPerSecond, event.getDuration());
        if (event.getDuration() > remainingTimeInThisStation && event.getBaseStation().getId() < 18) // prevent overflow
        {
            // call will be handed over to next station
            // handover event will be generated
            nextEvent = new CallHandoverEvent(event.id, this.clock + remainingTimeInThisStation,
                    event.getSpeed(), baseStations[event.getBaseStation().getId() + 1], event.getDuration() - remainingTimeInThisStation, event.handoverCount+1);
        } else {
            // call will be terminated in this station
            nextEvent = new CallTerminationEvent(event.id, this.clock + remainingTimeInThisStation,
                    baseStations[event.getBaseStation().getId() + 1], event.handoverCount);
        }
        eventQueue.add(event);
    }

}