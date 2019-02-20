public void handleCallHandoverEvent(CallTerminationEvent event) {
    simulationClock = event.time; // sync clock to event time
    BaseStation currentBaseStation = event.getBaseStation();
    
    // release the channel used in current Base Station
    currentBaseStation.releaseUsedChannel();
}