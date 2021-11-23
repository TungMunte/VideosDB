package output.Store;

public final class StoreRecommendFavorite {
    private String nameShow;
    private Integer numberFavorite;
    private Integer position;

    public StoreRecommendFavorite(final String nameShow,
                                  final Integer numberFavorite,
                                  final Integer position) {
        this.nameShow = nameShow;
        this.numberFavorite = numberFavorite;
        this.position = position;
    }

    public String getNameShow() {
        return nameShow;
    }

    public Integer getNumberFavorite() {
        return numberFavorite;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "StoreRecommendFavorite{"
                + "nameShow='" + nameShow + '\''
                + ", numberFavorite=" + numberFavorite
                + ", position=" + position + '}';
    }
}
