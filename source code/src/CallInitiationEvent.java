import enums.Direction;

import java.lang.*;

public class CallInitiationEvent extends Event {

    public CallInitiationEvent(Integer id,
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
}
