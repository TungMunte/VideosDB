package output.Recommend;

import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;
import java.util.*;
import output.Result;

public final class RecommendStandard extends Recommend {
    private List<String> orderApprearance = new ArrayList<>();

    public void setOrderApprearance(final List<String> orderApprearance) {
        this.orderApprearance = orderApprearance;
    }

    @Override
    public Result recommend(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().
                append("StandardRecommendation result: ");
        List<String> recommendName = new ArrayList<>();
        UserInputData currUser = null;

        for (UserInputData userInputData
                : input.getUsers()) {
            if (userInputData.getUsername().
                    equals(actionInputData.getUsername())) {
                currUser = userInputData;
            }
        }

        for (String nameShow : this.orderApprearance) {
            if (!currUser.getHistory().
                    containsKey(nameShow)) {
                recommendName.add(nameShow);
            }
        }

        if (recommendName.isEmpty()) {
            result.setMessage(new StringBuffer(
                    "StandardRecommendation cannot be applied!"));
        } else {
            message.append(recommendName.get(0));
            result.setMessage(message);
        }
        result.setId(actionInputData.getActionId());
        return result;
    }
}
