package output.Recommend;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.UserInputData;
import java.util.*;
import output.Result;
import output.Store.StoreRecommendPopular;

public final class RecommendPopular extends Recommend {
    private Map<UserInputData, Map<ShowInput, ActionInputData>> unseenShowOfUser = new HashMap<>();
    private List<String> orderApprearance = new ArrayList<>();

    public void setOrderApprearance(final List<String> orderApprearance) {
        this.orderApprearance = orderApprearance;
    }

    public void setUnseenShowOfUser(final Map<UserInputData,
            Map<ShowInput, ActionInputData>> unseenShowOfUser) {
        this.unseenShowOfUser = unseenShowOfUser;
    }

    @Override
    public Result recommend(final ActionInputData actionInputData, final Input input) {
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
            if (!currUser.getHistory().containsKey(nameShow)) {
                unseenShow.add(nameShow);
            }
        }

        for (String nameShow : unseenShow) {
            Integer sum = 0;
            for (UserInputData userInputData : input.getUsers()) {
                if (!userInputData.getUsername().equals(currUser.getUsername())) {
                    if (userInputData.getHistory().containsKey(nameShow)) {
                        sum += userInputData.getHistory().get(nameShow);
                    }
                }
            }
            storeRecommendPopularList.add(new StoreRecommendPopular(nameShow,
                    sum, orderApprearance.indexOf(nameShow)));
        }

        Comparator<StoreRecommendPopular> comparator = new Comparator<StoreRecommendPopular>() {
            @Override
            public int compare(final StoreRecommendPopular o1, final StoreRecommendPopular o2) {
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

        if (storeRecommendPopularList.isEmpty()
                || currUser.getSubscriptionType().equals("BASIC")) {
            result.setMessage(new StringBuffer(
                    "PopularRecommendation cannot be applied!"));
        } else {
            message.append(storeRecommendPopularList.
                    get(0).getNameShow());
            result.setMessage(message);
        }

        result.setId(actionInputData.getActionId());
        return result;
    }
}
