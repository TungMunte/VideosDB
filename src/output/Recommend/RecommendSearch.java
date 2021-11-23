package output.Recommend;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import java.util.*;
import output.Result;
import output.Store.StoreRecommendSearch;

public final class RecommendSearch extends Recommend {
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
        StringBuffer message = new StringBuffer().
                append("SearchRecommendation result: [");
        UserInputData currUser = null;
        List<ShowInput> showInputListSameGerne = new ArrayList<>();
        List<StoreRecommendSearch>
                storeRecommendSearchList = new ArrayList<>();

        for (UserInputData userInputData
                : input.getUsers()) {
            if (userInputData.getUsername().
                    equals(actionInputData.getUsername())) {
                currUser = userInputData;
            }
        }

        for (MovieInputData movieInputData
                : input.getMovies()) {
            if (movieInputData.getGenres().
                    contains(actionInputData.getGenre())) {
                showInputListSameGerne.add(movieInputData);
            }
        }
        for (SerialInputData serialInputData
                : input.getSerials()) {
            if (serialInputData.getGenres().
                    contains(actionInputData.getGenre())) {
                showInputListSameGerne.add(serialInputData);
            }
        }

        Iterator<ShowInput> showInputIterator
                = showInputListSameGerne.iterator();
        while (showInputIterator.hasNext()) {
            ShowInput currShowInput = showInputIterator.next();
            if (currUser.getHistory().
                    containsKey(currShowInput.getTitle())) {
                showInputIterator.remove();
            }
        }
        for (var entry1 : this.unseenShowOfUser.
                entrySet()) {
            if (entry1.getKey().getUsername().
                    equals(currUser.getUsername())) {
                for (ShowInput showInput
                        : showInputListSameGerne) {
                    if (entry1.getValue().containsKey(showInput)) {
                        storeRecommendSearchList.add(new StoreRecommendSearch(
                                showInput.getTitle(),
                                entry1.getValue().get(showInput).
                                        getGrade()));
                    } else {
                        storeRecommendSearchList.add(new StoreRecommendSearch(
                                showInput.getTitle(), 0d));
                    }
                }
            }
        }

        Comparator<StoreRecommendSearch>
                comparator = new Comparator<StoreRecommendSearch>() {
            @Override
            public int compare(final StoreRecommendSearch o1, final StoreRecommendSearch o2) {
                int result = 0;
                if (!o1.getGrade().equals(o2.getGrade())) {
                    result = o1.getGrade().compareTo(o2.getGrade());
                } else {
                    result = o1.getNameShow().compareTo(o2.getNameShow());
                }
                return result;
            }
        };

        Collections.sort(storeRecommendSearchList, comparator);

        if (storeRecommendSearchList.isEmpty()
                || currUser.getSubscriptionType().equals("BASIC")) {
            result.setMessage(new StringBuffer(
                    "SearchRecommendation cannot be applied!"));
        } else {
            for (int i = 0; i < storeRecommendSearchList.size(); i++) {
                if (i == 0) {
                    message.append(storeRecommendSearchList.
                            get(i).getNameShow());
                } else {
                    message.append(", ").append(storeRecommendSearchList.
                            get(i).getNameShow());
                }
            }
            message.append("]");
            result.setMessage(message);
        }

        result.setId(actionInputData.getActionId());
        return result;
    }
}