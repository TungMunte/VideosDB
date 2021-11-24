package output.Store;

public class StoreQueryUserRating {
    private String userName;
    private Integer numberOfActive;

    public StoreQueryUserRating(String userName, Integer numberOfActive) {
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
