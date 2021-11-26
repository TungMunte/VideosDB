package output.Store;

public final class StoreQueryUserRating {
    private String userName;
    private Integer numberOfActive;

    public StoreQueryUserRating(final String userName, final Integer numberOfActive) {
        this.numberOfActive = numberOfActive;
        this.userName = userName;
    }

    public Integer getNumberOfActive() {
        return numberOfActive;
    }

    public String getUserName() {
        return userName;
    }
}
