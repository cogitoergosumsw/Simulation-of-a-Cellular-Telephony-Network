import enums.Direction;

import java.lang.*;

public class CallHandoverEvent extends Event {

    public CallHandoverEvent(Integer id,
                             Double eventTime,
                             BaseStation baseStation,
                             Double speed,
                             Double duration,
                             Direction direction,
                             Double position) {
        super();
        this.id = id;
        this.eventTime = eventTime;
        this.baseStation = baseStation;
        this.speed = speed;
        this.callDuration = duration;
        this.direction = direction;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Call Handover Event || id: " + id + " || Event Time: " + eventTime + " || Base Station: " + baseStation.getId();
    }
}
