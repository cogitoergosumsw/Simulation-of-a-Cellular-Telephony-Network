import enums.Direction;

public class Event {
    public Integer id;
    public BaseStation baseStation;
    public Double eventTime;
    public Direction direction;
    public Double speed;
    public Double callDuration; // callDuration of call
    public Double position;

    public BaseStation getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(BaseStation baseStation) {
        this.baseStation = baseStation;
    }

    public Double getEventTime() {
        return eventTime;
    }

    public void setEventTime(Double eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Double callDuration) {
        this.callDuration = callDuration;
    }

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Event id: ");
        sb.append(id);
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Base Station id: ");
        sb.append(baseStation.getId());
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Time: ");
        sb.append(eventTime);
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Car Direction: ");
        sb.append(direction.toString());
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Car Speed: ");
        sb.append(speed);
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Call Duration: ");
        sb.append(callDuration);
        sb.append(System.getProperty("line.separator"));
        sb.append("Event Car Position in Base Station: ");
        sb.append(position);
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }
}