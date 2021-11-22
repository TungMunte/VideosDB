package output.Store;

public class StoreRecommendFavorite {
    private String nameShow;
    private Integer numberFavorite;
    private Integer position;

    public StoreRecommendFavorite(String nameShow, Integer numberFavorite, Integer position) {
        this.nameShow = nameShow;
        this.numberFavorite = numberFavorite;
        this.position = position;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setNameShow(String nameShow) {
        this.nameShow = nameShow;
    }

    public Integer getNumberFavorite() {
        return numberFavorite;
    }

    public void setNumberFavorite(Integer numberFavorite) {
        this.numberFavorite = numberFavorite;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "StoreRecommendFavorite{" +
                "nameShow='" + nameShow + '\'' +
                ", numberFavorite=" + numberFavorite +
                ", position=" + position +
                '}';
    }
}
