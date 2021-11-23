package output.Query;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;

public final class QueryActorAwards extends Query {
    private final int specialPosition = 3;

    public int getSpecialPosition() {
        return specialPosition;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        Map<String, Integer> actorList = new HashMap<>();
        Map<String, Integer> arrangedActorList;
        List<String> actorsName = new ArrayList<>();

        for (ActorInputData actorInputData : input.getActors()) {
            if (actorInputData.getAwards().size() == actionInputData.
                    getFilters().get(getSpecialPosition()).size()) {
                int numberOfAwards = 0;
                int checkMatchAwards = 0;
                for (String nameAward : actionInputData.getFilters().get(getSpecialPosition())) {
                    if (actorInputData.getAwards().containsKey(nameAward)) {
                        checkMatchAwards++;
                    }
                }
                if (checkMatchAwards == actorInputData.getAwards().size()) {
                    for (int i = 0; i < actorInputData.getAwards().size(); i++) {
                        numberOfAwards += actorInputData.getAwards().
                                get(actionInputData.getFilters().get(getSpecialPosition()).get(i));
                    }
                }
                actorList.put(actorInputData.getName(), numberOfAwards);
            }
        }
        if (actionInputData.getSortType().equals("asc")) {
            arrangedActorList = actorList.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedActorList = actorList.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        for (var entry : arrangedActorList.entrySet()) {
            actorsName.add(entry.getKey());
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