public void handleCallHandoverEvent(CallHandoverEvent event) {
        simulationClock = event.time; // sync clock to event time
        numHandovers++;
        // get the next Base Station
        BaseStation nextBaseStation;
        if (event.getDirection() == Direction.ToStationOne) {
                nextBaseStation = baseStationList[currentBaseStation.getId() - 1];
        } else {
                nextBaseStation = baseStationList[currentBaseStation.getId() + 1];
        }
        // release the channel used in current Base Station
        currentBaseStation.releaseUsedChannel();

        // check if the next Base Station has any free channel
        if (nextBaseStation.numFreeStation > 0) {
                nextBaseStation.useOneChannel();
        } else {
                // no free channel, call is dropped
                numDroppedCalls++;
        }

        // get the distance of car before leaving current base station
        double distanceToNextStation = 2000 - event.getCurrentPos();
        // calculate time before reaching next base station
        double remainingTime = distanceToNextStation / event.getSpeed();

        Event nextEvent;

        // calculate the new event time
        double newEventTime = simulationClock + remainingTime;

        // calculate the new event duration
        double newEventDuration = event.getCallDuration() - remainingTime;

        // reach next base station before call ends
        if (event.getCallDuration() > remainingTime && event.getDirection() == Direction.ToStationTwenty && event.getBaseStation() != 20) {
                // instantiate a new instance of Call Handover Event
                // pass the current event attributes to the next event
                // 1. event ID
                // 2. new event time
                // 3. speed of current car
                // 4. next Base Station
                // 5. updated call duration after passing this Base Station
                // 6. direction that the car is moving towards
                nextEvent = new CallHandoverEvent(event.id, newEventTime, event.getSpeed(), event.getBaseStation(), newEventDuration, event.getDirection());
        } else if (event.getCallDuration() > remainingTime && event.getDirection() == Direction.ToStationOne && event.getBaseStation() != 1) {
                nextEvent = new CallHandoverEvent(event.id, newEventTime, event.getSpeed(), event.getBaseStation(), newEventDuration, event.getDirection());
        }
        else { // call ended before reaching next base station
                nextEvent = new CallTerminationEvent(event.id, newEventTime, event.getBaseStation());
        }
        // add the newly instantiated event to the event queue
        eventQ.add(nextEvent);
}
