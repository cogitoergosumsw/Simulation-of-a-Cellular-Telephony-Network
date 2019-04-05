public class BaseStation {

    private Integer id;
    private Integer numFreeChannels;
    private Integer numReservedChannel;

    public BaseStation(Integer id) {
        this.id = id;
//      all Base Stations are initialized with 10 free channels at the beginning
        this.numFreeChannels = 10;
//      reserve 1 channel for handover FCA Scheme
        this.numReservedChannel = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumFreeChannels() {
        return numFreeChannels;
    }

    public void setNumFreeChannels(Integer numFreeChannels) {
        this.numFreeChannels = numFreeChannels;
    }

    public Integer getNumReservedChannel() {
        return numReservedChannel;
    }

    public void setNumReservedChannel(Integer numReservedChannel) {
        this.numReservedChannel = numReservedChannel;
    }

    public void useOneChannel() {
        numFreeChannels--;
//        System.out.println("Base Station with id: " + id + " used up 1 channel || Number of remaining free channels = " + numFreeChannels);
    }

    public void releaseUsedChannel() {
        numFreeChannels++;
//        System.out.println("Base Station with id: " + id + " used up 1 channel || Number of remaining free channels = " + numFreeChannels);
    }

    public void useOneReservedChannel() {
        numReservedChannel--;
    }

    public void releaseUsedReservedChannel() {
        numReservedChannel++;
    }
}