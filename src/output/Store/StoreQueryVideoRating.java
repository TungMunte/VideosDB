package output.Store;

public class StoreQueryVideoRating {
    private String nameShow;
    private Double grade;

    public StoreQueryVideoRating(String nameShow, Double grade) {
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
