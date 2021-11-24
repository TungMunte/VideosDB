package output.Store;

public class StoreQueryVideoLongest {
    private String nameShow;
    private Integer numberOfTime;

    public StoreQueryVideoLongest(String nameShow, Integer numberOfTime) {
        this.nameShow = nameShow;
        this.numberOfTime = numberOfTime;
    }

    public Integer getNumberOfTime() {
        return numberOfTime;
    }

    public String getNameShow() {
        return nameShow;
    }
}
