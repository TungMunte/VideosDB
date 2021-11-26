package output.Store;

public final class StoreQueryActorAverage {
    private String nameShow;
    private StringBuffer nameActor;
    private Double grade;

    public StoreQueryActorAverage(final StringBuffer nameActor, final Double grade) {
        this.nameActor = nameActor;
        this.grade = grade;
    }

    public StoreQueryActorAverage(final String nameShow, final Double grade) {
        this.nameShow = nameShow;
        this.grade = grade;
    }

    public String getNameShow() {
        return nameShow;
    }

    public StringBuffer getNameActor() {
        return nameActor;
    }

    public Double getGrade() {
        return grade;
    }
}
