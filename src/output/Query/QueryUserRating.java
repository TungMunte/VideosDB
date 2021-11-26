package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import java.util.*;
import output.Result;
import output.Store.StoreQueryUserRating;

public final class QueryUserRating extends Query {
    private List<String> userNameActiv = new ArrayList<>();
    private List<ActionInputData> actionOfUser = new ArrayList<>();

    public void setActionOfUser(final List<ActionInputData> actionOfUser) {
        this.actionOfUser = actionOfUser;
    }

    public void setUserNameActiv(final List<String> userNameActiv) {
        this.userNameActiv = userNameActiv;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> nameList = new ArrayList<>();
        List<StoreQueryUserRating> storeQueryUserRatingList = new ArrayList<>();

        for (int i = 0; i < this.userNameActiv.size(); i++) {
            int countAppearance = 0;
            for (ActionInputData actionOfUser : this.actionOfUser) {
                if (actionOfUser.getUsername().equals(this.userNameActiv.get(i))) {
                    countAppearance++;
                }
            }
            if (countAppearance != 0) {
                storeQueryUserRatingList.add(new StoreQueryUserRating(
                        this.userNameActiv.get(i), countAppearance));
            }
        }
        Comparator<StoreQueryUserRating> comparator = new Comparator<StoreQueryUserRating>() {
            @Override
            public int compare(final StoreQueryUserRating o1,
                               final StoreQueryUserRating o2) {
                int result = 0;
                if (!o1.getNumberOfActive().equals(o2.getNumberOfActive())) {
                    result = o1.getNumberOfActive().compareTo(
                            o2.getNumberOfActive());
                } else {
                    result = o1.getUserName().compareTo(o2.getUserName());
                }
                return result;
            }
        };
        Collections.sort(storeQueryUserRatingList, comparator);
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(storeQueryUserRatingList);
        }
        for (var object : storeQueryUserRatingList) {
            nameList.add(object.getUserName());
        }
        if (actionInputData.getNumber() < storeQueryUserRatingList.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        } else {
            for (int i = 0; i < storeQueryUserRatingList.size(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        }

        message.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(message);
        return result;
    }
}
