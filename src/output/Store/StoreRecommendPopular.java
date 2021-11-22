package output.Store;

public class StoreRecommendPopular {
    private String nameShow;
    private Integer numberOfView;
    private Integer position;

    public StoreRecommendPopular() {
    }

    public StoreRecommendPopular(String nameShow, Integer numberOfView, Integer position) {
        this.nameShow = nameShow;
        this.numberOfView = numberOfView;
        this.position = position;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setNameShow(String nameShow) {
        this.nameShow = nameShow;
    }

    public Integer getNumberOfView() {
        return numberOfView;
    }

    public void setNumberOfView(Integer numberOfView) {
        this.numberOfView = numberOfView;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "StoreRecommendPopular{" +
                "nameShow='" + nameShow + '\'' +
                ", numberOfView=" + numberOfView +
                ", position=" + position +
                '}';
    }
}