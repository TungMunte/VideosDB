package output.Store;

import fileio.*;

public class StoreQueryMostViewed {
    private String nameShow;
    private Integer numberOfView;

    public StoreQueryMostViewed(String nameShow, Integer numberOfView) {
        this.nameShow = nameShow;
        this.numberOfView = numberOfView;
    }

    public String getNameShow() {
        return nameShow;
    }

    public Integer getNumberOfView() {
        return numberOfView;
    }

}
