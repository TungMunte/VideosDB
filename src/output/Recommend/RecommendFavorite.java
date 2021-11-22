package output.Recommend;

import fileio.*;
import java.util.*;
import output.*;
import output.Store.*;

public class RecommendFavorite extends Recommend {
    private Map<UserInputData, Map<ShowInput, ActionInputData>> unseenShowOfUser = new HashMap<>();
    private List<String> orderApprearance = new ArrayList<>();

    public List<String> getOrderApprearance() {
        return orderApprearance;
    }

    public void setOrderApprearance(List<String> orderApprearance) {
        this.orderApprearance = orderApprearance;
    }

    public Map<UserInputData, Map<ShowInput, ActionInputData>> getUnseenShowOfUser() {
        return unseenShowOfUser;
    }

    public void setUnseenShowOfUser(Map<UserInputData, Map<ShowInput, ActionInputData>> unseenShowOfUser) {
        this.unseenShowOfUser = unseenShowOfUser;
    }

    @Override
    public Result recommend(ActionInputData actionInputData, Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("FavoriteRecommendation result: ");
        List<String> unseenShow = new ArrayList<>();
        List<StoreRecommendFavorite> storeRecommendFavoriteList = new ArrayList<>();
        UserInputData currUser = null;

        for (UserInputData userInputData : input.getUsers()) {
            if (userInputData.getUsername().equals(actionInputData.getUsername())) {
                currUser = userInputData;
            }
        }

        for (String nameShow : this.orderApprearance) {
            if (currUser.getHistory().containsKey(nameShow) == false) {
                unseenShow.add(nameShow);
            }
        }

        for (String nameShow : unseenShow) {
            Integer sum = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (userInputData.getUsername().equals(currUser.getUsername()) == false) {
                    if (userInputData.getFavoriteMovies().contains(nameShow) == true) {
                        sum++;
                    }
                }
            }
            if (sum != 0) {
                storeRecommendFavoriteList.add(new StoreRecommendFavorite(nameShow, sum, orderApprearance.indexOf(nameShow)));
            }
        }

        Comparator<StoreRecommendFavorite> comparator = new Comparator<StoreRecommendFavorite>() {
            @Override
            public int compare(StoreRecommendFavorite o1, StoreRecommendFavorite o2) {
                int result = 0;
                if (!o1.getNumberFavorite().equals(o2.getNumberFavorite())) {
                    result = -o1.getNumberFavorite().compareTo(o2.getNumberFavorite());
                } else {
                    result = -o1.getPosition().compareTo(o2.getPosition());
                }
                return result;
            }
        };

        Collections.sort(storeRecommendFavoriteList, comparator);

        if (storeRecommendFavoriteList.isEmpty() || currUser.getSubscriptionType().equals("BASIC")) {
            result.setMessage(new StringBuffer("FavoriteRecommendation cannot be applied!"));
        } else {
            message.append(storeRecommendFavoriteList.get(0).getNameShow());
            result.setMessage(message);
        }

        result.setId(actionInputData.getActionId());
        return result;
    }
}
