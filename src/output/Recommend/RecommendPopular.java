package output.Recommend;

import fileio.*;
import java.util.*;
import output.*;
import output.Store.*;

public class RecommendPopular extends Recommend {
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
        StringBuffer message = new StringBuffer().append("PopularRecommendation result: ");
        List<String> unseenShow = new ArrayList<>();
        List<StoreRecommendPopular> storeRecommendPopularList = new ArrayList<>();
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
                    if (userInputData.getHistory().containsKey(nameShow) == true) {
                        sum += userInputData.getHistory().get(nameShow);
                    }
                }
            }
            storeRecommendPopularList.add(new StoreRecommendPopular(nameShow, sum, orderApprearance.indexOf(nameShow)));
        }

        Comparator<StoreRecommendPopular> comparator = new Comparator<StoreRecommendPopular>() {
            @Override
            public int compare(StoreRecommendPopular o1, StoreRecommendPopular o2) {
                int result = 0;
                if (!o1.getNumberOfView().equals(o2.getNumberOfView())) {
                    result = -o1.getNumberOfView().compareTo(o2.getNumberOfView());
                } else {
                    result = -o1.getPosition().compareTo(o2.getPosition());
                }
                return result;
            }
        };

        Collections.sort(storeRecommendPopularList, comparator);

        if (storeRecommendPopularList.isEmpty() || currUser.getSubscriptionType().equals("BASIC")) {
            result.setMessage(new StringBuffer("PopularRecommendation cannot be applied!"));
        } else {
            message.append(storeRecommendPopularList.get(0).getNameShow());
            result.setMessage(message);
        }

        result.setId(actionInputData.getActionId());
        return result;
    }
}
