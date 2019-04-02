import java.lang.*;

public class CallInitiationEvent extends Event {

    private Double speed;
    private Double duration; // duration of call
    private Direction direction;
    private Double position;

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
        this.duration = duration;
        this.direction = direction;
        this.position = position;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }
}
