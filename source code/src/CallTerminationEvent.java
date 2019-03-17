import java.lang.*;
public class CallTerminationEvent extends Event {

    public CallTerminationEvent(Integer id,  Double eventTime, BaseStation baseStation, Integer callHandoverCount) {
        super();
        this.id = id;
        this.eventTime = id;
        this.baseStation = baseStation;
        this.callHandoverCount = callHandoverCount;
    }

    @Override
    public String toString() {
        return "Call Termination Event || id: " + id + " || Event Time: " + eventTime + " || Base Station: " + baseStation.getId() + " Call Handover Count: " + callHandoverCount;
    }
}