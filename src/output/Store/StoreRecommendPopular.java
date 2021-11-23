package output.Store;

public final class StoreRecommendPopular {
    private String nameShow;
    private Integer numberOfView;
    private Integer position;

    public StoreRecommendPopular() {
    }

    public StoreRecommendPopular(final String nameShow,
                                 final Integer numberOfView,
                                 final Integer position) {
        this.nameShow = nameShow;
        this.numberOfView = numberOfView;
        this.position = position;
    }

    public String getNameShow() {
        return nameShow;
    }

    public Integer getNumberOfView() {
        return numberOfView;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "StoreRecommendPopular{"
                + "nameShow='" + nameShow + '\''
                + ", numberOfView=" + numberOfView
                + ", position=" + position + '}';
    }
}