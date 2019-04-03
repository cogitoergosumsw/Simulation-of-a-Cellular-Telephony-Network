public class BaseStation {

    private Integer id;
    private Integer numFreeChannels;

    public BaseStation(Integer id) {
        this.id = id;
//      all Base Stations are initialized with 10 free channels at the beginning
        this.numFreeChannels = 10;
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

    public void useOneChannel() {
        numFreeChannels--;
//        System.out.println("Base Station with id: " + id + " used up 1 channel || Number of remaining free channels = " + numFreeChannels);
    }

    public void releaseUsedChannel() {
        numFreeChannels++;
//        System.out.println("Base Station with id: " + id + " used up 1 channel || Number of remaining free channels = " + numFreeChannels);
    }
}