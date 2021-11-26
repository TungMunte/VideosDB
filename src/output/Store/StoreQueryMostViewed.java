package output.Store;

public final class StoreQueryMostViewed {
    private String nameShow;
    private Integer numberOfView;

    public StoreQueryMostViewed(final String nameShow, final Integer numberOfView) {
        this.nameShow = nameShow;
        this.numberOfView = numberOfView;
    }

    public String getNameShow() {
        return nameShow;
    }

    public Integer getNumberOfView() {
        return numberOfView;
    }

}
