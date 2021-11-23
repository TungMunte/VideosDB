package output.Recommend;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.UserInputData;
import java.util.*;
import output.Result;
import output.Store.StoreRecommendFavorite;

public final class RecommendFavorite extends Recommend {
    private Map<UserInputData, Map<ShowInput, ActionInputData>>
            unseenShowOfUser = new HashMap<>();
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
        StringBuffer message = new StringBuffer().
                append("FavoriteRecommendation result: ");
        List<String> unseenShow = new ArrayList<>();
        List<StoreRecommendFavorite> storeRecommendFavoriteList = new ArrayList<>();
        UserInputData currUser = null;

        for (UserInputData userInputData : input.getUsers()) {
            if (userInputData.getUsername().
                    equals(actionInputData.getUsername())) {
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
                if (!userInputData.getUsername().
                        equals(currUser.getUsername())) {
                    if (userInputData.getFavoriteMovies().
                            contains(nameShow)) {
                        sum++;
                    }
                }
            }
            if (sum != 0) {
                storeRecommendFavoriteList.add(new StoreRecommendFavorite(nameShow,
                        sum, orderApprearance.indexOf(nameShow)));
            }
        }

        Comparator<StoreRecommendFavorite>
                comparator = new Comparator<StoreRecommendFavorite>() {
            @Override
            public int compare(final StoreRecommendFavorite o1, final StoreRecommendFavorite o2) {
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

        if (storeRecommendFavoriteList.isEmpty()
                || currUser.getSubscriptionType().equals("BASIC")) {
            result.setMessage(new StringBuffer("FavoriteRecommendation cannot be applied!"));
        } else {
            message.append(storeRecommendFavoriteList.get(0).getNameShow());
            result.setMessage(message);
        }

        result.setId(actionInputData.getActionId());
        return result;
    }
}
