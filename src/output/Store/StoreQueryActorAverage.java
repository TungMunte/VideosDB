package output.Store;

import fileio.ShowInput;
import java.util.*;

public class StoreQueryActorAverage {
    private ShowInput showInput;
    private Double grade;

    public StoreQueryActorAverage(ShowInput showInput, Double grade) {
        this.showInput = showInput;
        this.grade = grade;
    }

    public ShowInput getShowInput() {
        return showInput;
    }

    public Double getGrade() {
        return grade;
    }
}
