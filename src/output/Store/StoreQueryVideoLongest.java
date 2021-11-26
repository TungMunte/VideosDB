package output.Store;

public final class StoreQueryVideoLongest {
    private String nameShow;
    private Integer numberOfTime;

    public StoreQueryVideoLongest(final String nameShow, final Integer numberOfTime) {
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
