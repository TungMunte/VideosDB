package output.Store;

public final class StoreRecommendSearch {
    private String nameShow;
    private Double grade;

    public StoreRecommendSearch(final String nameShow, final Double grade) {
        this.nameShow = nameShow;
        this.grade = grade;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(final Double grade) {
        this.grade = grade;
    }

    public String getNameShow() {
        return nameShow;
    }

}
