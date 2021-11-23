package output.Store;

public final class StoreRecommendBestUnseen {
    private String nameShow;
    private Double grade;
    private Integer position;

    public StoreRecommendBestUnseen() {
    }

    public StoreRecommendBestUnseen(final String nameShow,
                                    final Double grade,
                                    final Integer position) {
        this.nameShow = nameShow;
        this.grade = grade;
        this.position = position;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(final double grade) {
        this.grade = grade;
    }

    public String getNameShow() {
        return nameShow;
    }

    public Integer getPosition() {
        return position;
    }
}
