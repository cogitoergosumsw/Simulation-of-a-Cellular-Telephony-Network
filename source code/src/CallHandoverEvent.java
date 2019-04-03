import java.lang.*;

public class CallHandoverEvent extends Event {

    private Double speed;
    private Double duration; // remaining duration of call
    private Direction direction;

    public CallHandoverEvent(Integer id,
                             Double eventTime,
                             BaseStation baseStation,
                             Double speed,
                             Double duration,
                             Direction direction) {
        super();
        this.id = id;
        this.eventTime = eventTime;
        this.baseStation = baseStation;
        this.speed = speed;
        this.duration = duration;
        this.direction = direction;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Call Handover Event || id: " + id + " || Event Time: " + eventTime + " || Base Station: " + baseStation.getId();
    }
}
