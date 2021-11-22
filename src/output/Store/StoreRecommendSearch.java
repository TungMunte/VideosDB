package output.Store;

public class StoreRecommendSearch {
    private String nameShow;
    private Double grade;

    public StoreRecommendSearch(String nameShow, Double grade) {
        this.nameShow = nameShow;
        this.grade = grade;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setNameShow(String nameShow) {
        this.nameShow = nameShow;
    }
}
