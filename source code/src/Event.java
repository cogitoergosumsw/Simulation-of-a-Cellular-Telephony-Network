public class Event {
    private BaseStation baseStation;
    private Double eventTime;
    private Integer callHandoverCount;
    private Integer id;

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

    public Integer getCallHandoverCount() {
        return callHandoverCount;
    }

    public void setCallHandoverCount(Integer callHandoverCount) {
        this.callHandoverCount = callHandoverCount;
    }
}