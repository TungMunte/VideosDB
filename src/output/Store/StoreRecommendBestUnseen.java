package output.Store;

public class StoreRecommendBestUnseen {
    private String nameShow;
    private Double grade;
    private Integer position;

    public StoreRecommendBestUnseen() {
    }

    public StoreRecommendBestUnseen(String nameShow, Double grade, Integer position) {
        this.nameShow = nameShow;
        this.grade = grade;
        this.position = position;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getNameShow() {
        return nameShow;
    }

    public void setNameShow(String nameShow) {
        this.nameShow = nameShow;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
