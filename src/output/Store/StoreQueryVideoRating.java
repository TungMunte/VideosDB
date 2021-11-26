package output.Store;

public final class StoreQueryVideoRating {
    private String nameShow;
    private Double grade;

    public StoreQueryVideoRating(final String nameShow, final Double grade) {
        this.nameShow = nameShow;
        this.grade = grade;
    }

    public Double getGrade() {
        return grade;
    }

    public String getNameShow() {
        return nameShow;
    }
}
