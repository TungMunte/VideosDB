package output.Store;

public final class StoreQueryVideoFavorite {
    private String nameVideo;
    private Integer numberOfFavorite;

    public StoreQueryVideoFavorite(final String nameVideo,
                                   final Integer numberOfFavorite) {
        this.nameVideo = nameVideo;
        this.numberOfFavorite = numberOfFavorite;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public Integer getNumberOfFavorite() {
        return numberOfFavorite;
    }

}
