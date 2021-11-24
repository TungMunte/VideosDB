package output.Query;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import java.util.*;
import output.Result;
import output.Store.StoreQueryActorAwards;

public final class QueryActorAwards extends Query {
    private final int specialPosition = 3;

    public int getSpecialPosition() {
        return specialPosition;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> actorsName = new ArrayList<>();
        List<StoreQueryActorAwards> storeQueryActorAwardsList = new ArrayList<>();
        for (ActorInputData actorInputData : input.getActors()) {
            int numberOfAwards = 0;
            int checkMatchAwards = 0;
            Map<String, Integer> actorAward = new HashMap<>();
            for (var entry : actorInputData.getAwards().entrySet()) {
                actorAward.put(String.valueOf(entry.getKey()), entry.getValue());
            }

            for (String nameAward : actionInputData.getFilters().get(getSpecialPosition())) {
                if (actorAward.containsKey(nameAward)) {
                    checkMatchAwards++;
                    numberOfAwards += actorAward.get(nameAward);
                }
            }
            if (checkMatchAwards == actionInputData.getFilters().get(getSpecialPosition()).size()) {
                storeQueryActorAwardsList.add(new StoreQueryActorAwards(
                        actorInputData.getName(), numberOfAwards));
            }
        }
        Comparator<StoreQueryActorAwards> comparator = new Comparator<StoreQueryActorAwards>() {
            @Override
            public int compare(final StoreQueryActorAwards o1, final StoreQueryActorAwards o2) {
                return o1.getNumberOfAwards().compareTo(o2.getNumberOfAwards());
            }
        };
        Collections.sort(storeQueryActorAwardsList, comparator);
        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(storeQueryActorAwardsList);
        }
        for (var entry : storeQueryActorAwardsList) {
            actorsName.add(entry.getNameOfActor());
        }
        for (int i = 0; i < actorsName.size(); i++) {
            if (i == 0) {
                message.append(actorsName.get(i));
            } else {
                message.append(", ").append(actorsName.get(i));
            }
        }
        message.append("]");
        result.setMessage(message);
        result.setId(actionInputData.getActionId());
        return result;
    }
}
