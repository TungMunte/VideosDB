package output.Recommend;

import fileio.*;
import java.util.*;
import output.*;
import output.Store.*;

public class RecommendBestUnseen extends Recommend {
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
        StringBuffer message = new StringBuffer().append("BestRatedUnseenRecommendation result: ");
        UserInputData currUser = null;
        Map<ShowInput, ActionInputData> unseenShow = new HashMap<>();
        List<StoreRecommendBestUnseen> storeRecommendBestUnseenList = new ArrayList<>();

        for (UserInputData userInputData : input.getUsers()) {
            if (userInputData.getUsername().equals(actionInputData.getUsername())) {
                currUser = userInputData;
            }
        }

        for (var entry : this.unseenShowOfUser.entrySet()) {
            if (entry.getKey().getUsername().equals(currUser.getUsername())) {
                unseenShow.putAll(entry.getValue());
                break;
            }
        }

        for (var entry : unseenShow.entrySet()) {
            storeRecommendBestUnseenList.add(new StoreRecommendBestUnseen(entry.getKey().getTitle(),
                    entry.getValue().getGrade(),
                    this.orderApprearance.indexOf(entry.getKey().getTitle())));
        }

        Comparator<StoreRecommendBestUnseen> comparator = new Comparator<StoreRecommendBestUnseen>() {
            @Override
            public int compare(StoreRecommendBestUnseen o1, StoreRecommendBestUnseen o2) {
                int result = 0;
                if (!o1.getGrade().equals(o2.getGrade())) {
                    result = o1.getGrade().compareTo(o2.getGrade());
                } else {
                    result = o1.getPosition().compareTo(o2.getPosition());
                }
                return result;
            }
        };

        Collections.sort(storeRecommendBestUnseenList, comparator);

        if (storeRecommendBestUnseenList.isEmpty()) {
            result.setMessage(new StringBuffer("BestRatedUnseenRecommendation cannot be applied!"));
        } else {
            message.append(storeRecommendBestUnseenList.get(0).getNameShow());
            result.setMessage(message);
        }
        result.setId(actionInputData.getActionId());
        return result;
    }
}