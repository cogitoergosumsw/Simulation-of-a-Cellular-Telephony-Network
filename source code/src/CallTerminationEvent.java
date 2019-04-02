import java.lang.*;

public class CallTerminationEvent extends Event {

    public CallTerminationEvent(Integer id, Double eventTime, BaseStation baseStation) {
        super();
        this.id = id;
        this.eventTime = eventTime;
        this.baseStation = baseStation;
    }

    @Override
    public String toString() {
        return "Call Termination Event || id: " + id + " || Event Time: " + eventTime + " || Base Station: " + baseStation.getId();
    }
}