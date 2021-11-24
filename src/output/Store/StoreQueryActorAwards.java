package output.Store;

public final class StoreQueryActorAwards {
    private String nameOfActor;
    private Integer numberOfAwards;

    public StoreQueryActorAwards(final String nameOfActor,
                                 final Integer numberOfAwards) {
        this.nameOfActor = nameOfActor;
        this.numberOfAwards = numberOfAwards;
    }

    public String getNameOfActor() {
        return nameOfActor;
    }

    public Integer getNumberOfAwards() {
        return numberOfAwards;
    }
}
